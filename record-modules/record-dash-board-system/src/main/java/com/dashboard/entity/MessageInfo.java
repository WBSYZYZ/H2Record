package com.dashboard.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@TableName("tb_message_push")
public class MessageInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userName;
    private Long targetUserName;
    private String messagePush;
    private Long clickStar;
    private String nickName;
    private String icon;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    private Integer messageReceive;
}
