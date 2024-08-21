package com.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dashboard.entity.Blog;
import com.dashboard.entity.UserToDo;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IBlogService extends IService<Blog> {

    /**
     * 修改待办进度条
     * @param userToDo
     */
    void changeProgress(UserToDo userToDo);

    /**
     * 推送留言
     * @param userToDo
     */
    void messagePush(UserToDo userToDo) throws IOException;

    /**
     * 确认已读信息
     * @param userToDo
     */
    void confirmMessage(UserToDo userToDo);
}
