package com.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dashboard.entity.MessageInfo;
import com.dashboard.entity.ToDoDetails;
import com.dashboard.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-24
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 查询当前用户的ToDoDeatils信息
     * @return
     */
    List<ToDoDetails> searchMeToDo();

    /**
     * 查询访问用户数据
     * @param userIdentity
     * @return
     */
    List<ToDoDetails> viewToDo(Long userIdentity);

    /**
     * 插入待办数据
     * @param toDoDetails
     */
    void submitAll(List<Map> toDoDetails);

    /**
     * 查询留言记录
     * @return
     */
    List<MessageInfo> searchMessageInfo();

    /**
     * 查询访问用户留言记录
     * @param userIdentity
     * @return
     */
    List<MessageInfo> searchMessageViewInfo(Long userIdentity);

    void deleteToDoDetail(ToDoDetails toDoDetails);

}
