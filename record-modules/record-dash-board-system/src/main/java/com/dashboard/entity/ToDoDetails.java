package com.dashboard.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lw
 */
@Data
@TableName("tb_userTodo_details")
public class ToDoDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增Id
     */
    private Long todoId;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 待办标题
     */
    private String toDo;

    /**
     * 待办详细
     */
    private String toDoDetail;

    /**
     *进度
     */
    private Long progress;

    /**
     * 宽度
     */
    private String width;

    /**
     * 颜色
     */
    private String backgroundColor;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 预计完成时间
     */
    private String expectTime;

    /**
     * 持续时间
     */
    private Long daysLeft;

    /**
     * 状态
     */
    private String status;

}
