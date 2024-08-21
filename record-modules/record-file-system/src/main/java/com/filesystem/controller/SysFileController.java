package com.filesystem.controller;


import com.filesystem.entity.R;
import com.filesystem.utils.AjaxResult;
import com.filesystem.utils.AlibabaUtils;
import com.publicapi.AmazonFileService;
import com.publicapi.domain.SysFile;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件请求处理
 * 
 * @author ruoyi
 */
@RestController
public class SysFileController
{
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Resource
    AlibabaUtils alibabaUtils;

    @Resource
    AmazonFileService amazonFileService;

    @Value("${alibaba.s3.replacePath}")
    private String replacePath;

    @Value("${alibaba.s3.preFix}")
    private String preFix;

    /**
     * 文件上传请求
     */
    @PostMapping("/uploadAmazonFile")
    public R<SysFile> uploadAmazonFile(String base64Image, String preFix)
    {
        try
        {
            SysFile sysFile=amazonFileService.uploadFile(base64Image,preFix);
            return R.ok(sysFile);
        }
        catch (Exception e)
        {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("/getAmazonFilePathUrl")
    public R<List<String>> getAmazonFilePathUrl(@RequestParam List<String> filePath){
        List<String> result;
        try {
             result=amazonFileService.getAmazonFilePath(filePath);
        }catch (Exception e){
            return  R.fail("Amazon文件查询失败！"+e);
        }
        return R.ok(result);
    }

    List<Map<String,String>> fileMigrationList=new ArrayList<>();
    @RequestMapping("/fileMigration")
    public AjaxResult fileMigration(@RequestParam(value="path") String path){
        fileMigrationList.clear();
        this.getFiles(path);
        List<List<Map<String,String>>> partitionList= ListUtils.partition(fileMigrationList,25);
        log.info("分组后队列长度 : {}"+partitionList.size());
        List<List<List<Map<String,String>>>> partitionDealList=ListUtils.partition(partitionList,200);
        log.info("处理队列分批长度 :{}"+partitionDealList.size());
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
                8,
                16,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(Integer.MAX_VALUE),
                new ThreadPoolExecutor.CallerRunsPolicy());
        AtomicInteger atomicInteger=new AtomicInteger();
        do{
            atomicInteger.incrementAndGet();
            this.fileMigrationThreadPool(threadPoolExecutor,partitionDealList.get(atomicInteger.get()-1),atomicInteger.get(),partitionDealList.size());
        }while (atomicInteger.get()<partitionDealList.size());
        return AjaxResult.success();
    }

    public void fileMigrationThreadPool(ThreadPoolExecutor threadPoolExecutor,List<List<Map<String,String>>> partitionDealList,Integer position,Integer ListSize){
        long startTime=System.currentTimeMillis();
        for(List<Map<String,String>> lst:partitionDealList){
            threadPoolExecutor.execute(()->{
                lst.forEach(map ->{
                    try {
                        alibabaUtils.fileMigration(map.get("filePath"),map.get("fileName"),preFix,replacePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        }
        boolean allThreadsIsDone=false;
        while(!allThreadsIsDone){
            allThreadsIsDone=threadPoolExecutor.getTaskCount() == threadPoolExecutor.getCompletedTaskCount();
        }
        log.info("批量迁移第"+position+"批次共花费 :{}"+(System.currentTimeMillis()-startTime));
        if(allThreadsIsDone){
            if(position.equals(ListSize)){
                threadPoolExecutor.shutdown();
                while (!threadPoolExecutor.isTerminated()) {
                    // pass
                }
                log.info("迁移完成,线程池已经关闭!!!");
            }
        }
    }

    public void getFiles(String path){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                Map map=new HashMap();
                map.put("filePath",listOfFiles[i].getPath());
                map.put("fileName",listOfFiles[i].getName());
                fileMigrationList.add(map);
            } else if (listOfFiles[i].isDirectory()) {
                getFiles(listOfFiles[i].getPath());
            }
        }
    }

}