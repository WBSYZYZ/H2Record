package com.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dashboard.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户名称是否存在
     * @param map
     * @return
     */
    Integer checkUserNickName(Map map);

    /**
     * 查询用户列表
     * @param searchParam
     * @return
     */
    List<User> searchUserInfo(String searchParam);
}
