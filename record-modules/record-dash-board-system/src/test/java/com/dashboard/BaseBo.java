package com.dashboard;

import java.io.Serializable;

/**
 * 业务基类
 * @author xiangzhu
 * @date 2019/4/9
 */
public class BaseBo implements Serializable {

    private static final long serialVersionUID = 4518191199348710205L;

    private String id;            // ID

    private Short isValid;        // 是否有效

    private Long createTime;      // 创建时间

    private String createUserId;  // 创建人ID

    private Long opTime;          // 操作时间

    private Integer lastVer;      // 版本号

    private String opUserId;      // 操作人ID

    private String opUserName;    // 操作人名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Short getIsValid() {
        return isValid;
    }

    public void setIsValid(Short isValid) {
        this.isValid = isValid;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Long getOpTime() {
        return opTime;
    }

    public void setOpTime(Long opTime) {
        this.opTime = opTime;
    }

    public Integer getLastVer() {
        return lastVer;
    }

    public void setLastVer(Integer lastVer) {
        this.lastVer = lastVer;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }
}
