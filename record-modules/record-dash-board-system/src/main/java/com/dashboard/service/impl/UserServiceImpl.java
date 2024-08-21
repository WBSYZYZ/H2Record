package com.dashboard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashboard.dto.LoginFormDTO;
import com.dashboard.dto.Result;
import com.dashboard.dto.UserDTO;
import com.dashboard.entity.User;
import com.dashboard.mapper.UserMapper;
import com.dashboard.service.IUserService;
import com.dashboard.utils.RegexUtils;
import com.dashboard.utils.SendSmsUtils;
import com.dashboard.utils.SnowFlakeIdWorker;
import com.publicapi.AmazonFileService;
import com.record.basic.core.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.dashboard.utils.RedisConstants.*;
import static com.dashboard.utils.SystemConstants.USER_NICK_NAME_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-07-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "IAmazonFileService")
    private AmazonFileService amazonFileService;

    @Resource
    private UserMapper userMapper;

    @Resource
    SendSmsUtils sendSmsUtils;

    @Override
    public Result sendCode(String phone, HttpSession session) {
        //校验手机号是否非法
        if(RegexUtils.isPhoneInvalid(phone)){
            return  Result.fail("非法手机号请重新输入！");
        }
        //合法生成验证码
        String randomCode= RandomUtil.randomNumbers(6);
        //保存到redis
        if(stringRedisTemplate.hasKey(LOGIN_CODE_KEY+phone)){
            Long ttl =stringRedisTemplate.getExpire(LOGIN_CODE_KEY+phone);
            return Result.fail(String.valueOf(ttl));
        }
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+phone,randomCode,LOGIN_CODE_TTL, TimeUnit.MINUTES);
        //sendSmsUtils.sendSMS(phone,"",randomCode);
        //发送验证码
        logger.info("发送验证码成功---》"+randomCode);
        return  Result.ok(randomCode);
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //校验手机号
        String phone=loginForm.getPhone();
        if(RegexUtils.isPhoneInvalid(phone)){
            return  Result.fail("非法手机号请重新输入！");
        }
        //校验验证码
        String cacheCode=stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY+phone);
        String code=loginForm.getCode();
        //判断校验码是否正确
        if(cacheCode ==null || !cacheCode.equals(code)){
            return Result.fail("验证码错误！");
        }
        //一致根据手机号查询对应用户
        User user =query().eq("phone",phone).one();
        //判断用户是否存在
        if(user == null){
            user=createUserWithPhone(phone);
        }
        String token=UUID.randomUUID().toString(true);
        UserDTO userDTO=BeanUtil.copyProperties(user, UserDTO.class);
        Map<String,Object> userMap=BeanUtil.beanToMap(userDTO);
        userMap.put("userId",String.valueOf(userMap.get("userId")));
        //保存用户信息到redis中
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY+token,userMap);
        //设置有效期
        stringRedisTemplate.expire(LOGIN_USER_KEY+token,LOGIN_USER_TTL,TimeUnit.MINUTES);
        Map<String,String> userToken=new HashMap<>();
        userToken.put("token",token);
        userToken.put("nickName",userDTO.getNickName());
        userToken.put("userIdentity",String.valueOf(userDTO.getUserId()));
        return Result.ok(userToken);
    }

    private User createUserWithPhone(String phone) {
        User user=new User();
        SnowFlakeIdWorker snowFlakeIdWorker=new SnowFlakeIdWorker(0,0);
        user.setUserId(snowFlakeIdWorker.nextId());
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX+RandomUtil.randomString(10));
        save(user);
        return user;
    }

    @Override
    public User queryUserBasicInfo() {
        Long userId= SecurityUtils.getUserId();
        User user=query().eq("user_id",userId).one();
        user.setIcon(amazonFileService.getAmazonFilePath(user.getIcon()));
        return user;
    }

    @Override
    public User queryViewUserBasicInfo(Long userIdentity) {
        User user=query().eq("user_id",userIdentity).one();
        List<String> arr=new ArrayList<>();
        if(!"".equals(user.getIcon())){
            arr.add(user.getIcon());
            List<String> result=amazonFileService.getAmazonFilePath(arr);
            user.setIcon(result.get(0));
        }
        return user;
    }

    @Override
    public void updateUserBasicInfo(User user) {
        Long userId=SecurityUtils.getUserId();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        update(user, updateWrapper);
    }

    @Override
    public Boolean checkUserNickName(String nickName) {
        Long userId=SecurityUtils.getUserId();
        Map map=new HashMap<>();
        map.put("nickName",nickName);
        map.put("userId",userId);
        Integer num=userMapper.checkUserNickName(map);
        if(num>0){
            return true;
        }
        return false;
    }

    @Override
    public List<User> searchUserInfo(String searchParam) {
        List<User> result=userMapper.searchUserInfo(searchParam);
        result.stream().forEach(item->{
            if(!"".equals(item.getIcon())){
                item.setIcon(amazonFileService.getAmazonFilePath(item.getIcon()));
            }
        });
        return result;
    }
}
