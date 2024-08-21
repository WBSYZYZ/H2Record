package com.recordmq.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author bingningmeng
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckroomMessageDTO implements Serializable {

    /**
     * 店铺实体ID
     */
    private String entityId;

    /**
     * 内部流水号
     */
    private String code;

    /**
     * 流水 id
     */
    private String historyId;

    /**
     * 寄存类型
     * <p>
     * 1: 寄存
     * 2: 取出
     */
    private Integer opType;

    /**
     * 操作人 id
     */
    private String opUserId;

    /**
     * 寄存时间
     */
    private long checkinTime;

    /**
     * 详情列表
     */
    private List<CheckroomDetailMessageDTO> detailList;
}
