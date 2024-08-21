package com.publicapi;

import com.publicapi.domain.SysFile;

import java.io.IOException;
import java.util.List;

/**
 * @author lw
 */
public interface AmazonFileService {

    /**
     * 获取aws签名访问路径
     * @param filePath
     * @return
     */
    List<String> getAmazonFilePath(List<String> filePath);

    /**
     * 上传图片文件至s3
     * @param base64Image
     * @param preFix
     * @return
     * @throws IOException 抛出文件上传异常信息
     * @throws Exception
     */
    SysFile uploadFile(String base64Image, String preFix) throws Exception;

    /**
     * 获取aws单个签名访问路径
     * @param filePath
     * @return
     */
    String getAmazonFilePath(String filePath);

    List<SysFile> getAmazonFilePath();
}
