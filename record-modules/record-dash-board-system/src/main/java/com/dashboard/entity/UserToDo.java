package com.dashboard.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author lw
 */
@Data
public class UserToDo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long todoId;
    private Long userId;
    private String toDo;
    private String toDoDetail;
    private Long progress;
    private String status;
    private String backgroundColor;
    private String createTime;
    private String expectTime;
    private String messageInput;
    private String messagePush;
    private Long targetUserId;
    private String userName;
    private List<Long> confirmMessageItems;

}
