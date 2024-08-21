package com.recordmq.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author bingningmeng
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckroomDetailMessageDTO implements Serializable{
    private String detailId;

    /**
     * 商品id
     */
    private String menuId;

    /**
     * 寄存数量
     */
    private Double num;
}
