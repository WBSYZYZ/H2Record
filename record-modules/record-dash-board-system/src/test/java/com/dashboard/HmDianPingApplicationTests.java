package com.dashboard;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class HmDianPingApplicationTests{

     public static void main(String[] args) throws IOException, URISyntaxException {
         String url="http://119.23.39.24:4158/api/InterfaceDevice/ControlSensor";
        HmDianPingApplicationTests hmDianPingApplicationTests=new HmDianPingApplicationTests();
        Map map=new HashMap();
        map.put("UserName","ST230517CS");
        map.put("Password","123456");
        map.put("DeviceId","8ef82dbf-2660-4047-84ba-75aa64cb3539");
        map.put("DTUCode","402A8F22CFEC");
        map.put("DeviceCode","1");
        map.put("SensorCode","1");
        map.put("vvv","0");
         //log.info(hmDianPingApplicationTests.unicodeDecode(hmDianPingApplicationTests.httpGetMethod(url,map)));
         JSONObject json=new JSONObject(map);
         String ms=hmDianPingApplicationTests.unicodeDecode(hmDianPingApplicationTests.httpPostMethodRestFul(url,map));

         //String ms=hmDianPingApplicationTests.unicodeDecode(hmDianPingApplicationTests.httpGetMethod(url,map));
         //DeviceSensorOutPutDataBase result=JSONObject.parseObject(ms,DeviceSensorOutPutDataBase.class);
         log.info(ms);
     }
     //Get请求restful格式接口
    public String httpGetMethod(String url, Map<String,String> params) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        String resultContent="";
        List<NameValuePair> pairs = new ArrayList<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            URI uri = new URIBuilder(new URI(url))
                    .addParameters(pairs)
                    .build();
            httpGet.setUri(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try (CloseableHttpClient httpclient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                // 获取响应信息
                resultContent = EntityUtils.toString(entity);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return resultContent;
    }

    public String httpPostMethod(String url, String data) throws IOException {
        HttpPost httpPost=new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(data, Charset.forName("UTF-8")));
        String resultContent="";
        try {
            URI uri = new URIBuilder(new URI(url))
                    .build();
            httpPost.setUri(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try (CloseableHttpClient httpclient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                // 获取响应信息
                resultContent = EntityUtils.toString(entity);
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
        return resultContent;
    }

    //Post请求restful格式接口
    public String httpPostMethodRestFul(String url, Map<String,String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(params.toString(), Charset.forName("UTF-8")));
        List<NameValuePair> pairs = new ArrayList<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        String resultContent="";
        try {
            URI uri = new URIBuilder(new URI(url))
                    .addParameters(pairs)
                    .build();
            httpPost.setUri(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try (CloseableHttpClient httpclient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                // 获取响应信息
                resultContent = EntityUtils.toString(entity);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return resultContent;
    }

    public String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }
}