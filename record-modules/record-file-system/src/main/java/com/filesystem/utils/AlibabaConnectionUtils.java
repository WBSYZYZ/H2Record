package com.filesystem.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author lw
 */
@Component
public class AlibabaConnectionUtils {

    @Value("${alibaba.s3.alibabaAddress}")
    private String alibabaAddress;

    @Value("${alibaba.s3.accessKey}")
    private String accessKey;

    @Value("${alibaba.s3.secretKey}")
    private String secretKey;

    @Value("${alibaba.s3.region}")
    private String region;

    @Value("${alibaba.s3.bucket}")
    private String bucket;

    private OSS ossClient;

    public OSS getOssClient() {
        return ossClient;
    }

    public void setOssClient(OSS ossClient) {
        this.ossClient = ossClient;
    }

    public String getAlibabaAddress() {
        return alibabaAddress;
    }

    public void setAlibabaAddress(String alibabaAddress) {
        this.alibabaAddress = alibabaAddress;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Bean
    public void ossClient(){
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKey, secretKey);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(alibabaAddress, credentialsProvider);
        setOssClient(ossClient);
    }
}
