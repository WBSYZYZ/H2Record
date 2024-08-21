package com.dashboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashboard.entity.User;
import com.dashboard.mapper.UserMapper;
import com.publicapi.AmazonFileService;
import com.publicapi.domain.SysFile;
import com.record.basic.core.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lw
 */
@Service("IAmazonFileService")
@Slf4j
public class UploadFileImpl extends ServiceImpl<UserMapper, User>  implements AmazonFileService {

    @DubboReference(
            //服务接口名
            interfaceClass = AmazonFileService.class,
            //服务版本，与服务提供者的版本一致
            version = "1.0.0",
            //服务方法调用超时时间(毫秒)
            timeout = 5000,
            retries = 3)

    private AmazonFileService amazonFileService;

    @Override
    public List<String> getAmazonFilePath(List<String> filePath) {
        List<String> result=amazonFileService.getAmazonFilePath(filePath);
        return result;
    }

    @Override
    public SysFile uploadFile(String base64Image, String preFix) throws Exception {
        SysFile avatarResult;
        try{
            avatarResult= amazonFileService.uploadFile(base64Image,preFix);
            Long userId= SecurityUtils.getUserId();
            User user=new User();
            user.setIcon(avatarResult.getUrl());
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id",userId);
            update(user, updateWrapper);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new Exception("上传失败");
        }
        return avatarResult;
    }

    @Override
    public String getAmazonFilePath(String filePath) {
        if("".equals(filePath)){
            return "";
        }
        String result=amazonFileService.getAmazonFilePath(filePath);
        return result;
    }

    @Override
    public List<SysFile> getAmazonFilePath() {
        List<SysFile> amazonFilePath = amazonFileService.getAmazonFilePath();
        return amazonFilePath;
    }
}
