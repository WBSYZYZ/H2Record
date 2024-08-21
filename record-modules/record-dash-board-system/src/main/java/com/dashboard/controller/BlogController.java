package com.dashboard.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dashboard.dto.Result;
import com.dashboard.entity.Blog;
import com.dashboard.entity.MessageInfo;
import com.dashboard.entity.User;
import com.dashboard.entity.UserToDo;
import com.dashboard.mapper.BlogMapper;
import com.dashboard.service.IBlogService;
import com.dashboard.service.IUserService;
import com.dashboard.utils.SystemConstants;
import com.record.basic.core.utils.SecurityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private IBlogService blogService;
    @Resource
    private IUserService userService;

    @Resource
    BlogMapper blogMapper;

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    RedisTemplate redisTemplate;

    @PostMapping
    public Result saveBlog(@RequestBody Blog blog) {
        // 获取登录用户
        Long userId= SecurityUtils.getUserId();
        blog.setUserId(userId);
        // 保存探店博文
        blogService.save(blog);
        // 返回id
        return Result.ok(blog.getId());
    }

    @PutMapping("/like/{id}")
    public Result likeBlog(@PathVariable("id") Long id) {
        // 修改点赞数量
        blogService.update()
                .setSql("liked = liked + 1").eq("id", id).update();
        return Result.ok();
    }

    @GetMapping("/of/me")
    public Result queryMyBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        Long userId= SecurityUtils.getUserId();
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .eq("user_id", userId).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        return Result.ok(records);
    }

    @GetMapping("/hot")
    public Result queryHotBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .orderByDesc("liked")
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        // 查询用户
        records.forEach(blog ->{
            Long userId = blog.getUserId();
            User user = userService.getById(userId);
            blog.setName(user.getNickName());
            blog.setIcon(user.getIcon());
        });
        return Result.ok(records);
    }
    @RequestMapping("/changeProgress")
    public Result changeProgress(@RequestBody UserToDo userToDo){
        blogService.changeProgress(userToDo);
        return Result.ok();
    }
    @RequestMapping("/messagePush")
    public Result messagePush(@RequestBody UserToDo userToDo) throws IOException {
        blogService.messagePush(userToDo);
        return Result.ok();
    }
    @RequestMapping("/confirmMessage")
    public Result confirmMessage(@RequestBody UserToDo userToDo) {
        blogService.confirmMessage(userToDo);
        return Result.ok();
    }
    @RequestMapping("/batchUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(@RequestBody  List<MessageInfo> messageInfoList) {
            jdbcTemplate.batchUpdate("delete from tb_message_push  where id=?", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    for(MessageInfo messageInfo:messageInfoList){
                        ps.setLong(1,messageInfo.getUserId());
                        ps.setString(2,messageInfo.getMessagePush());
                        ps.setLong(3,messageInfo.getId());
                        ps.addBatch();
                    }
                }
                @Override
                public int getBatchSize() {
                    return messageInfoList.size();
                }
            });
            messageInfoList.get(0).getIcon().toString();
    }

    @RequestMapping("/deleteMsg")
    public void deleteMsg(@RequestBody  List<MessageInfo> messageInfoList){
        Boolean b = redisTemplate.opsForValue().setIfAbsent("aaaaa", "aaa");
        if(b==false){
            redisTemplate.delete("aaaaa");
            return;
        }
        Thread thread=new Thread(()->{
            int a=0;
            while (redisTemplate.opsForValue().get("aaaaa")!=null){
                if(a==10){
                    break;
                }
                System.out.println("aaaaaaaaaaaa");
                a=a+1;
            }
        });
        thread.start();
        Integer result=blogMapper.deleteMsg(messageInfoList);
        System.out.println(result);
    }
}
