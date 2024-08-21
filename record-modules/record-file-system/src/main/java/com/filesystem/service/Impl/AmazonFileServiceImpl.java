package com.filesystem.service.Impl;

import com.filesystem.utils.AlibabaUtils;
import com.filesystem.utils.BASE64DecodedMultipartFile;
import com.publicapi.AmazonFileService;
import com.publicapi.domain.SysFile;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lw
 */
@Service
@DubboService(interfaceClass = AmazonFileService.class, version = "1.0.0")
public class AmazonFileServiceImpl implements AmazonFileService {

    private static final Logger logger = LoggerFactory.getLogger(AmazonFileServiceImpl.class);

    @Resource
    AlibabaUtils alibabaUtils;

    @Override
    public List<String> getAmazonFilePath(List<String> filePath) {
        long begin = System.currentTimeMillis();
        List<String> result=new ArrayList<>();
        filePath.forEach(path->{
           result.add(alibabaUtils.getObjectURL(path));
        });
        logger.info("获取文件总时间 :{}",System.currentTimeMillis() - begin);
        return result;
    }

    @Override
    public SysFile uploadFile(String base64Image, String preFix) throws IOException {
        // 上传并返回访问地址
        long begin = System.currentTimeMillis();
        MultipartFile file= BASE64DecodedMultipartFile.base64ToMultipart(base64Image);
        String url = alibabaUtils.uploadFile(file,preFix);
        String viewUrl = alibabaUtils.getObjectURL(url);
        SysFile sysFile = new SysFile();
        sysFile.setUrl(url);
        sysFile.setViewUrl(viewUrl);
        logger.info("上传文件总时长 :{}",System.currentTimeMillis() - begin);
        return sysFile;
    }

    @Override
    public String getAmazonFilePath(String filePath) {
        if("".equals(filePath)){
            return "";
        }
        String result=alibabaUtils.getObjectURL(filePath);
        return result;
    }

    @Override
    public List<SysFile> getAmazonFilePath() {
        List<SysFile> sysFiles=new ArrayList<>();
        SysFile sysFile=new SysFile();
        sysFile.setUrl("dawdwadwada");
        sysFile.setName("dasadasdadad");
        sysFile.setViewUrl("gawdawdawdawdawdwad");
        sysFiles.add(sysFile);
        return sysFiles;
    }
}
