package com.publicapi.domain;

import java.io.Serializable;

/**
 * 文件信息
 * 
 * @author ruoyi
 */
public class SysFile implements Serializable
{
    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String url;

    /**
     * 访问url
     */
    private String viewUrl;

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SysFile{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", viewUrl='" + viewUrl + '\'' +
                '}';
    }
}
