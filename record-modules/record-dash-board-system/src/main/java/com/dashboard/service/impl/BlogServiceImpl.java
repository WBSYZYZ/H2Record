package com.dashboard.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashboard.entity.Blog;
import com.dashboard.entity.MessageInfo;
import com.dashboard.entity.User;
import com.dashboard.entity.UserToDo;
import com.dashboard.mapper.BlogMapper;
import com.dashboard.mapper.UserInfoMapper;
import com.dashboard.service.IBlogService;
import com.dashboard.service.WebSocketService;
import com.record.basic.core.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Resource
    BlogMapper blogMapper;

    @Resource
    UserInfoMapper userInfoMapper;

    @Resource
    WebSocketService webSocketService;

    @Override
    public void changeProgress(UserToDo userToDo) {
        blogMapper.changeProgress(userToDo);
    }

    @Override
    public void messagePush(UserToDo userToDo){
        Long userId= SecurityUtils.getUserId();
        userToDo.setUserId(userId);
        if(userToDo.getTargetUserId()==null){
            userToDo.setTargetUserId(userId);
        }else{
            userToDo.setUserId(SecurityUtils.getUserId());
        }
        blogMapper.messagePush(userToDo);
        MessageInfo messageInfo=new MessageInfo();
        User user=userInfoMapper.queryUserBasicInfo(userId);
        messageInfo.setMessagePush(userToDo.getMessagePush());
        messageInfo.setNickName(user.getNickName());
        messageInfo.setIcon(user.getIcon());
        String json = JSON.toJSONString(messageInfo);
        webSocketService.sendMessage(String.valueOf(userToDo.getTargetUserId()),json);
    }

    @Override
    public void confirmMessage(UserToDo userToDo) {
        blogMapper.confirmMessage(userToDo);
    }

}
