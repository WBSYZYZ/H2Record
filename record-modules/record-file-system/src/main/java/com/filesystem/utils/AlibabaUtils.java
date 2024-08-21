package com.filesystem.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;

/**
 * @author lw
 */
@Component
public class AlibabaUtils {

    private static final Logger logger = LoggerFactory.getLogger(AlibabaUtils.class);

    @Resource
    AlibabaConnectionUtils alibabaConnectionUtils;

    public String uploadFile(MultipartFile multipartFile, String preFix){

        String filePathName=preFix+FileUploadUtils.extractFilename(multipartFile);
        try {
            InputStream inputStream = multipartFile.getInputStream();
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(alibabaConnectionUtils.getBucket(), filePathName, inputStream);
            // 创建PutObject请求。
            alibabaConnectionUtils.getOssClient().putObject(putObjectRequest);
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.error("Error Message:" + oe.getErrorMessage());
            logger.error("Error Code:" + oe.getErrorCode());
            logger.error("Request ID:" + oe.getRequestId());
            logger.error("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            logger.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.error("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePathName;
    }
    public String getObjectURL(String fileName) {

        Date expiration = new Date(System.currentTimeMillis() + 1440 * 1000);
        String url = alibabaConnectionUtils.getOssClient().generatePresignedUrl(alibabaConnectionUtils.getBucket(), fileName, expiration).toString();
        return url;
    }

    public String fileMigration(String filePath,String fileName,String preFix,String replacePath) throws IOException {
        String filePathName=preFix+filePath.replace(replacePath,"");
        MultipartFile multipartFile=this.getMultipartFile(filePath,fileName);
        InputStream inputStream = multipartFile.getInputStream();
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(alibabaConnectionUtils.getBucket(), filePathName, inputStream);
        // 创建PutObject请求。
        alibabaConnectionUtils.getOssClient().putObject(putObjectRequest);
        return filePathName;
    }

    public  MultipartFile getMultipartFile(String filePath, String fileName) throws FileNotFoundException {
        InputStream fis = new FileInputStream(filePath);
        FileItem fileItem = createFileItem(fis, fileName);
        return new CommonsMultipartFile(fileItem);
    }

    public  FileItem createFileItem(InputStream inputStream, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem fileItem = factory.createItem("file", MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
        int read = 0;
        OutputStream os = null;
        byte[] buffer = new byte[10 * 1024 * 1024];
        try {
            os = fileItem.getOutputStream();
            while ((read = inputStream.read(buffer, 0, 4096)) != -1) {
                os.write(buffer, 0, read);
            }
            inputStream.close();
        } catch (IOException e) {
            logger.error("os write exception", e);
            throw new IllegalArgumentException("文件流输出失败");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("stream os close exception", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("stream inputStream close exception", e);
                }
            }
        }
        return fileItem;
    }
}
