## 平台简介

* 基于ruoyi框架实现,适合初学者学习使用的简单功能

* 采用前后端分离的模式，微服务版本前端。

* 后端采用Spring Boot、Spring Cloud & Alibaba。

* 注册中心、配置中心选型Nacos，权限认证使用Redis。

* 流量控制框架选型Sentinel，分布式事务选型Seata。


## 系统模块

    com.record     
    ├── record-gateway         // 网关模块
    ├── record-public-api       // 接口模块
    ├── record-modules         // 业务模块
    │       └── record-dash-board-system                  // 系统模块
    │       └── record-message-systen                     // 消息模块
    │           └── record-mq-system                          // 消息模块
    │       └── record-file                               // 文件服务
    ├──pom.xml                // 公共依赖

## 架构图

# H2Record 业务功能

1.添加删除代办

2.支持拖动更新进度

3.websocket支持用户互相留言并收到提醒

4.支持用户搜索

5.rocketmq作为消息中间件

6.redis作为缓存

7.阿里云文件服务开发
