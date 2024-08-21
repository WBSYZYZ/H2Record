package com.dashboard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lw
 */
@Component
@ServerEndpoint("/websocket/{terminalId}")
@Slf4j
public class WebSocketService {


    /**
     * 保存连接信息
     */
    private static final Map<String, Session> CLIENTS = new ConcurrentHashMap<>();
    private static final Map<String, AtomicInteger> TERMINAL_IDS = new HashMap<>();

    @OnOpen
    public void onOpen(@PathParam("terminalId") String terminalId, Session session) throws Exception {
        log.info(session.getRequestURI().getPath() + "，打开连接开始：" + session.getId());

        //当前连接已存在，关闭
        if (CLIENTS.containsKey(terminalId)) {
            onClose(CLIENTS.get(terminalId));
        }

        TERMINAL_IDS.put(terminalId, new AtomicInteger(0));
        CLIENTS.put(terminalId, session);

        log.info(session.getRequestURI().getPath() + "，打开连接完成：" + session.getId());
    }

    @OnClose
    public void onClose(@PathParam("terminalId") String terminalId, Session session) throws Exception {
        log.info(session.getRequestURI().getPath() + "，关闭连接开始：" + session.getId());

        CLIENTS.remove(terminalId);
        TERMINAL_IDS.remove(terminalId);

        log.info(session.getRequestURI().getPath() + "，关闭连接完成：" + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (TERMINAL_IDS.containsKey(message)) {
            //重置当前终端心跳次数
            TERMINAL_IDS.get(message).set(0);
            return;
        }

        sendMessage("收到消息：" + message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.toString());
    }

    public void onClose(Session session) {
        //判断当前连接是否在线
//        if (!session.isOpen()) {
//            return;
//        }

        try {
            session.close();
        } catch (IOException e) {
            log.error("金斗云关闭连接异常：" + e);
        }
    }

    public void heartbeat() {
        //检查所有终端心跳次数
        for (String key : TERMINAL_IDS.keySet()) {
            //心跳3次及以上的主动断开
            if ((TERMINAL_IDS.get(key).intValue() >= 3)) {
                log.info("心跳超时，关闭连接：" + key);
                onClose(CLIENTS.get(key));
            }
        }

//        for (String key : CLIENTS.keySet()) {
//            //记录当前终端心跳次数
//            TERMINAL_IDS.get(key).incrementAndGet();
//            sendMessage("{'recall':'pong'}", CLIENTS.get(key));
//        }
    }

    public void sendMessage(String message, Session session) {
        try {
            session.getAsyncRemote().sendText(message);

            log.info("推送成功：" + message);
        } catch (Exception e) {
            log.error("推送异常：" + e);
        }
    }

    public boolean sendMessage(String terminalId, String message) {
        try {
            Session session = CLIENTS.get(terminalId);
            session.getAsyncRemote().sendText(message);

            log.info("推送成功：" + message);
            return true;
        } catch (Exception e) {
            log.error("推送异常：" + e);
            return false;
        }
    }
}
