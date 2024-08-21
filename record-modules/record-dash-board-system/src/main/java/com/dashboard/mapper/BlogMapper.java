package com.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dashboard.entity.Blog;
import com.dashboard.entity.MessageInfo;
import com.dashboard.entity.UserToDo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 修改todo进度
     * @param userToDo
     */
    void changeProgress(UserToDo userToDo);

    /**
     * 推送消息
     * @param userToDo
     */
    void messagePush(UserToDo userToDo);

    /**
     * 确认已读信息
     * @param userToDo
     */
    void confirmMessage(UserToDo userToDo);

    Integer deleteMsg(List<MessageInfo> messageInfoList);
}
