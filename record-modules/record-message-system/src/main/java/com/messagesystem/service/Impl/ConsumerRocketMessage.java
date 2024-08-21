package com.messagesystem.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "my-group",topic = "test-topic-1")
public class ConsumerRocketMessage implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("处理消息:"+s);
    }
}
