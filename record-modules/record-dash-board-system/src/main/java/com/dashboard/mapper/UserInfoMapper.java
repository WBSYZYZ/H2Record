package com.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dashboard.entity.MessageInfo;
import com.dashboard.entity.ToDoDetails;
import com.dashboard.entity.User;
import com.dashboard.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-24
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 查询当前用户的ToDoDeatils信息
     * @param  userId
     * @return
     */
    List<ToDoDetails> searchMeToDo(Long userId);

    /**
     * 查询访问用户的ToDoDeatils信息
     * @param  userId
     * @return
     */
    List<ToDoDetails> searchViewToDo(Long userId);

    /**
     * 插入用户待办
     * @param toDoDetails
     */
    void submitAll(List<Map> toDoDetails);

    /**
     * 查询消息记录
     * @param userId
     * @return
     */
    List<MessageInfo> searchMessageInfo(Long userId);

    /**
     * 删除待办
     * @param toDoDetails
     */
    void deleteToDoDetail(ToDoDetails toDoDetails);

    /**
     * 查询用户个人信息
     * @param userId
     * @return
     */
    User queryUserBasicInfo(Long userId);
}
