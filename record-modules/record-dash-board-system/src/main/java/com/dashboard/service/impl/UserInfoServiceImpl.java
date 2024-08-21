package com.dashboard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashboard.entity.MessageInfo;
import com.dashboard.entity.ToDoDetails;
import com.dashboard.entity.UserInfo;
import com.dashboard.mapper.UserInfoMapper;
import com.dashboard.service.IUserInfoService;
import com.publicapi.AmazonFileService;
import com.record.basic.core.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-24
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Resource
    UserInfoMapper userInfoMapper;

    @Resource(name = "IAmazonFileService")
    private AmazonFileService amazonFileService;

    @Override
    public List<ToDoDetails> searchMeToDo() {
        Long userId= SecurityUtils.getUserId();
        List<ToDoDetails> toDoDetails=userInfoMapper.searchMeToDo(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate now = LocalDate.now();
        toDoDetails.stream().forEach(map ->{
            LocalDate startDate = LocalDate.parse(map.getCreateTime(),formatter);
            map.setDaysLeft(startDate.until(now, ChronoUnit.DAYS));
        });
        return toDoDetails;
    }

    @Override
    public List<ToDoDetails> viewToDo(Long userIdentity) {
        List<ToDoDetails> toDoDetails=userInfoMapper.searchViewToDo(userIdentity);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate now = LocalDate.now();
        toDoDetails.stream().forEach(map ->{
            LocalDate startDate = LocalDate.parse(map.getCreateTime(),formatter);
            map.setDaysLeft(startDate.until(now, ChronoUnit.DAYS));
        });
        return toDoDetails;
    }

    @Override
    public void submitAll(List<Map> toDoDetails) {
        toDoDetails.stream().forEach(map -> {
            map.put("userId",SecurityUtils.getUserId());
        });
        userInfoMapper.submitAll(toDoDetails);
    }

    @Override
    public List<MessageInfo> searchMessageInfo() {
        Long userId=SecurityUtils.getUserId();
        List<MessageInfo> messageInfo=userInfoMapper.searchMessageInfo(userId);
        messageInfo.stream().forEach(item->{
            if(!"".equals(item.getIcon())){
                item.setIcon(amazonFileService.getAmazonFilePath(item.getIcon()));
            }
        });
        return messageInfo;
    }

    @Override
    public List<MessageInfo> searchMessageViewInfo(Long userIdentity) {
        List<MessageInfo> messageInfo=userInfoMapper.searchMessageInfo(userIdentity);
        return messageInfo;
    }

    @Override
    public void deleteToDoDetail(ToDoDetails toDoDetails) {
         userInfoMapper.deleteToDoDetail(toDoDetails);
    }

}
