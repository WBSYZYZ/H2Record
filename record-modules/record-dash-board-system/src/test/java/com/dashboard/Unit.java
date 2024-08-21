package com.dashboard;

import java.io.Serializable;


public class Unit extends BaseBo implements Serializable {

    private String entityId;

    private String selfEntityId;

    private String name;

    private Integer sortCode;

    private static final long serialVersionUID = 1L;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId == null ? null : entityId.trim();
    }

    public String getSelfEntityId() {
        return selfEntityId;
    }

    public void setSelfEntityId(String selfEntityId) {
        this.selfEntityId = selfEntityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

}