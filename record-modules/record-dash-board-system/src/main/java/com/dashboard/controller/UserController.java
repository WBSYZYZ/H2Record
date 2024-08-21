package com.dashboard.controller;


import com.dashboard.dto.LoginFormDTO;
import com.dashboard.dto.Result;
import com.dashboard.entity.MessageInfo;
import com.dashboard.entity.ToDoDetails;
import com.dashboard.entity.User;
import com.dashboard.entity.UserInfo;
import com.dashboard.service.IUserInfoService;
import com.dashboard.service.IUserService;
import com.dashboard.utils.RedisUtils;
import com.publicapi.AmazonFileService;
import com.publicapi.domain.SysFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String  AVATAR_PREFIX="avatar/";

    @Resource
    private IUserService userService;

    @Resource
    private IUserInfoService userInfoService;

    @Resource
    private RedisUtils redisUtils;

    @Resource(name = "IAmazonFileService")
    private AmazonFileService amazonFileService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送手机验证码
     */
    @PostMapping("code")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {

        return userService.sendCode(phone,session);
    }

    /**
     * 登录功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session){

        return userService.login(loginForm,session);
    }

    /**
     * 登出功能
     * @return 无
     */
    @PostMapping("/logout")
    public Result logout(){
        // TODO 实现登出功能
        return Result.fail("功能未完成");
    }

    @GetMapping("/me")
    public Result me(){
//        UserDTO user=UserHolder.getUser();
        return Result.ok();
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId){
        // 查询详情
        UserInfo info = userInfoService.getById(userId);
        if (info == null) {
            // 没有详情，应该是第一次查看详情
            return Result.ok();
        }
        info.setCreateTime(null);
        info.setUpdateTime(null);
        // 返回
        return Result.ok(info);
    }

    /**
     * 查询当前用户的ToDoDeatils信息
     */
    @GetMapping("/meToDo")
    public Result meToDo(){
        Map result=new HashMap();
        List<ToDoDetails> toDoDetails=userInfoService.searchMeToDo();
        //1：计划中，2：进行中
        Stream<ToDoDetails> inProgress=toDoDetails.stream().filter(details-> "2".equals(details.getStatus()));
        Stream<ToDoDetails> inExplain=toDoDetails.stream().filter(details-> "1".equals(details.getStatus()));
        result.put("toDoDetails",toDoDetails);
        result.put("inProgress",inProgress.count());
        result.put("inExplain",inExplain.count());
        result.put("total",toDoDetails.size());
        return Result.ok(result);
    }

    /**
     * 查询访问用户的ToDoDeatils信息
     */
    @GetMapping("/viewToDo")
    public Result viewToDo(@RequestParam Long userIdentity) throws IOException {
        Map result=new HashMap();
        List<ToDoDetails> toDoDetails=userInfoService.viewToDo(userIdentity);
        //1：进行中，2：计划中
        Stream<ToDoDetails> inProgress=toDoDetails.stream().filter(details-> "1".equals(details.getStatus()));
        Stream<ToDoDetails> inExplain=toDoDetails.stream().filter(details-> "2".equals(details.getStatus()));
        result.put("toDoDetails",toDoDetails);
        result.put("inProgress",inProgress.count());
        result.put("inExplain",inExplain.count());
        result.put("total",toDoDetails.size());
        return Result.ok(result);
    }

    @RequestMapping("/submitAll")
    public Result submitAll(@RequestBody List<Map> toDoDetails){
        userInfoService.submitAll(toDoDetails);
        return  Result.ok();
    }

    @GetMapping("/messageInfo")
    public Result messageInfo(){
        List<MessageInfo> messageInfo=userInfoService.searchMessageInfo();
        return Result.ok(messageInfo);
    }

    @GetMapping("/messageViewInfo")
    public Result messageViewInfo(@RequestParam Long userIdentity){
        List<MessageInfo> messageInfo=userInfoService.searchMessageViewInfo(userIdentity);
        return Result.ok(messageInfo);
    }

    @RequestMapping("/deleteToDoDetail")
    public Result deleteToDoDetail(@RequestBody ToDoDetails toDoDetails){
        userInfoService.deleteToDoDetail(toDoDetails);
        return Result.ok();
    }

    @RequestMapping("/queryUserBasicInfo")
    public Result queryUserBasicInfo(){
        User user=userService.queryUserBasicInfo();
        return Result.ok(user);
    }
    @RequestMapping("/queryViewUserBasicInfo")
    public Result queryViewUserBasicInfo(@RequestParam Long userIdentity) throws IOException {
        User user=userService.queryViewUserBasicInfo(userIdentity);
        return Result.ok(user);
    }

    @RequestMapping("/checkUserNickName")
    public Result checkUserNickName(@RequestParam String nickName){
        boolean hasExist=userService.checkUserNickName(nickName);
        if(hasExist==true){
            return Result.fail("用户名"+nickName+"已存在");
        }
        return Result.ok("修改成功");
    }

    @RequestMapping("/updateUserBasicInfo")
    public Result updateUserBasicInfo(@RequestBody User user){
        userService.updateUserBasicInfo(user);
        return Result.ok();
    }

    @RequestMapping("/uploadAvatarInfo")
    public Result uploadAvatarInfo(@RequestBody User user) throws Exception {
        if(user.getIcon()==null || user.getIcon()==""){
            return Result.fail("头像信息为空");
        }
        SysFile avatarResult= amazonFileService.uploadFile(user.getIcon(),AVATAR_PREFIX);
        return Result.ok(avatarResult.getViewUrl());
    }
    @RequestMapping("/pushRocketMq")
    public Result pushRocketMq(){
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        return Result.ok();
    }
    @RequestMapping("/searchUserInfo")
    public Result searchUserInfo(@RequestParam String searchParam){

        return Result.ok(userService.searchUserInfo(searchParam));
    }

    @RequestMapping("/getAll")
    public Result getAll(){
        return Result.ok(amazonFileService.getAmazonFilePath());
    }
}
