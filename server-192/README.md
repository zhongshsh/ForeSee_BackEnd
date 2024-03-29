# server-192说明

## 模块介绍

本模块主要用于数据访问，处理相关业务，并返回所需数据。

### 模块结构


```
├── java
│   └── com
│       └── ForeSee
│           └── ForeSee
│               ├── config           --配置
│               ├── controller       --接受并发送请求
│               ├── dao              --数据访问层
│               │   ├── MongoDBDao
│               │   ├── Neo4jDao
│               │   └── RedisDao
│               ├── ServerMain.java
│               ├── service          --需求类
│               └── util             --工具类，数据库连接
└── resources
    ├── FrontEndData                 --各service返回数据示例
    ├── MongoDB                      --Mongo数据导入和结构说明
    ├── Neo4j                        --Neo4j数据导入、查询、索引构建
    └── Redis                        --Redis数据导入和结构说明

```

### 配置说明

主配置文件

```yaml
spring:
  profiles:
    active: dev  # 指定配置文件，本地测试使用dev文件，服务器部署使用prod文件
  application:
     name: springcloud-foresee # 微服务名称
```

开发环境配置文件：用于本地测试

```yaml
server:
  port: 8287 # 微服务占用端口，微服务地址127.0.0.1:8287
eureka: 
  client:
    service-url:
      defaultZone: http://localhost:8888/eureka/ # 把微服务注册到服务注册中心
  instance:
    instance-id: springcloud-foresee-8288 # 微服务实例id，这里只有一个微服务实例
    prefer-ip-address: true  # 注册时使用ip地址注册
    ip-address: 127.0.0.1 # 微服务实例的ip地址
    # 向服务注册中心注册服务名为springcloud-foresee的微服务
    # 目前只注册了该微服务的一个实例springcloud-foresee-8288
```

生产环境配置文件：用于服务器部署

```yaml
server:
  port: 8288 # 微服务占用端口，微服务地址192.168.1.103:8288
eureka:
  client:
    service-url:
      defaultZone: http://222.200.184.74:8888/eureka/ # 注册到linux集群上的服务注册中心

  instance:
    instance-id: springcloud-foresee-8288 # 注册的实例id
    prefer-ip-address: true # 注册时使用ip地址注册
    ip-address: 192.168.1.103 # 实例所在ip地址
    # 运行在192.168.1.103:8288的微服务实例springcloud-foresee-8288被注册到222上的服务注册中心
    # 可以通过222上的路由网关发现该微服务实例，并将请求转发给该实例
```


python模块

/data/prj2020/EnterpriseSpider/searchEngine

qu_module.py
rank_module.py
