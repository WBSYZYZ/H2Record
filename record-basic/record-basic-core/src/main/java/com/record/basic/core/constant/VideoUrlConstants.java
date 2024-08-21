package com.record.basic.core.constant;

/**
 * @Name: 视频通讯地址
 * @Author: zj
 * @CreatTime: 2022/7/19 13:13
 */
public class VideoUrlConstants {


    /**
     * 获取视频点播地址前缀(调用时需拼接)
     */
    public static final String linkhost="http://10.202.10.144:5443/pcms/external/bunchPlantingDetail/";
    //public static final String linkhost=" https://36.112.8.22:15443/pcms/external/bunchPlantingDetail/";

    /**
     * 同步用户、组织host地址
     */
    private static final String host="http://10.202.10.144:5443";
    //private static final String host="https://36.112.8.22:15443";
    /**
     * 添加用户
     */
    public static final String addUserUrl = host+"/pcms/external/account/add";
    /**
     * 删除用户
     */
    public static final String deleteUserUrl = host+"/pcms/external/account/delete";
    /**
     * 修改用户
     */
    public static final String updateUserUrl = host+"/pcms/external/account/update";
    /**
     * 添加组织
     */
    public static final String addDeptUrl = host+"/pcms/external/frame/add";
    /**
     * 删除组织
     */
    public static final String deleteDeptrUrl = host+"/pcms/external/frame/delete";
    /**
     * 修改组织
     */
    public static final String updateDeptUrl = host+"/pcms/external/frame/update";
}
