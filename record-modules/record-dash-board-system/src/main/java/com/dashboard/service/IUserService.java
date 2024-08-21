package com.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dashboard.dto.LoginFormDTO;
import com.dashboard.dto.Result;
import com.dashboard.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IUserService extends IService<User> {

    /**
     * 发送验证码
     * @param phone
     * @param session
     * @return
     */
    Result sendCode(String phone, HttpSession session);

    /**
     * 登录
     * @param loginForm
     * @param session
     * @return
     */
    Result login(LoginFormDTO loginForm, HttpSession session);

    /**
     * 查询用户基本信息
     * @return
     */
    User queryUserBasicInfo();

    /**
     * 查询访问用户基本信息
     * @param userIdentity
     * @return
     */
    User queryViewUserBasicInfo(Long userIdentity);

    /**
     * 修改用户基本信息
     * @param user
     */
    void updateUserBasicInfo(User user);

    /**
     * 查看是否已存在用户
     * @param nickName
     * @return
     */
    Boolean checkUserNickName(String nickName);

    /**
     * 查询用户
     * @param searchParam
     * @return
     */
    List<User> searchUserInfo(String searchParam);
}
