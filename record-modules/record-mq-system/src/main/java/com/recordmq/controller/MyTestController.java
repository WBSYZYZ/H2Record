package com.recordmq.controller;

import com.alibaba.fastjson.JSON;
import com.recordmq.domain.CheckroomMessageDTO;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("aa")
public class MyTestController {

    @Resource
    RocketMQTemplate rocketMQTemplate;

    @RequestMapping("bb")
    public void test (String msg) throws IOException, MQBrokerException, RemotingException, InterruptedException, MQClientException {
        CheckroomMessageDTO checkroomMessageDTO=new CheckroomMessageDTO();
        checkroomMessageDTO.setCode("123");
        checkroomMessageDTO.setCheckinTime(1231231);
        checkroomMessageDTO.setDetailList(null);
        checkroomMessageDTO.setOpType(1);
        checkroomMessageDTO.setOpUserId("123");
        checkroomMessageDTO.setEntityId("3123213");
        checkroomMessageDTO.setHistoryId("1231233123");

        rocketMQTemplate.send("deposit:deposit_goods", MessageBuilder.withPayload(JSON.toJSONString(checkroomMessageDTO)).build());

    }
}
