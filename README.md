# 项目简介
内容主要包含：

| 微服务角色                 | 对应的技术选型                              |
| --------------------- | ------------------------------------ |
| 注册中心(Register Server) | Eureka                               |
| 服务提供者                 | spring mvc、spring-data-jpa、h2等       |
| 服务消费者                 | Ribbon/Feign消费服务提供者的接口               |
| 熔断器                   | Hystrix，包括Hystrix Dashboard以及Turbine |
| 配置服务                  | Spring Cloud Config Server           |
| API Gateway           | Zuul                                 |
| admin 管理后台                | spring boot admin|
| 服务追踪                |zipkin|


# 准备

## 环境准备：

| 工具    | 版本或描述                |
| ----- | -------------------- |
| JDK   | 1.8                  |
| IDE   | IntelliJ IDEA |
| Maven | 3.x                  |

## 主机名配置：

| 主机名配置（/etc/hosts文件） |
| ---------------------------------------- |
| 127.0.0.1 discovery config-server gateway movie user feign ribbon |

## 主机规划：

| 项目名称                                     | 端口   | 描述                     | URL             |
| ---------------------------------------- | ---- | ---------------------- | --------------- |
| microservice-api-gateway                 | 8070 | API Gateway            | 详见文章            |
| microservice-config-client               | 8041 | 配置服务的客户端               | 详见文章            |
| microservice-config-server               | 8040 | 配置服务                   | 详见文章            |
| microservice-config-client-eureka        | 8051 | 注册中心、配置服务结合的客户端               | 详见文章            |
| microservice-config-server-eureka        | 8050 | 注册中心、配置服务结合服务                   | 详见文章            |
| microservice-consumer-movie-feign        | 8020 | Feign Demo             | /feign/1        |
| microservice-consumer-movie-feign-with-hystrix | 8021 | Feign Hystrix Demo     | /feign/1        |
| microservice-consumer-movie-feign-with-hystrix-stream | 8022 | Hystrix Dashboard Demo | /feign/1        |
| microservice-consumer-movie-ribbon       | 8010 | Ribbon Demo            | /ribbon/1       |
| microservice-consumer-movie-ribbon-with-hystrix | 8011 | Ribbon Hystrix Demo    | /ribbon/1       |
| microservice-discovery-eureka            | 8761 | 注册中心                   | /               |
| microservice-hystrix-dashboard           | 8030 | hystrix监控              | /hystrix.stream |
| microservice-hystrix-turbine             | 8031 | turbine                | /turbine.stream |
| microservice-provider-user               | 8000 | 服务提供者                  | /1              |
|                                          |      |                        |                 |
| microservice-admin                       | 11007 | 微服务管理后台                 | /1              |
| microservice-zipkin-server               | 11008 | 跟踪服务服务端                 | /1              |
| microservice-zipkin-client               | 11009 | 跟踪服务客户端                | /1              |
| microservice-zipkin-client-backend       | 11010 | 跟踪服务客户端被调用方               | /1              |
| microservice-zipkin-stream-server        | 11020 | 以mq形式传递的跟踪服务服务端                | /1              |
| microservice-zipkin-stream-client        | 11021 | 以mq形式传递的跟踪服务客户端                 | /1              |
| microservice-zipkin-stream-client-backend  | 11022 | 以mq形式传递的跟踪服务客户端被调用方                  | /1              |


