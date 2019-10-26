
### 前言

消息中间件作用围绕着解耦、冗余(存储)、扩展性、削峰、可恢复性、顺序保证、异步通信等等，实例项目贯穿交换机的4中模式中进行代码展示。所有知识点全部在读者熟悉RabbitMq概念后可直接进行参考，比如Broker、channel等，需要有写理论知识。


### 项目整体模块

--ituac-rabbitmq：项目名称父工程<br/>
----rabbitmq-consumer-samples：消费者模块父工程<br/>
--------rabbitmq-consumer-samples-spring：spring消费者版本<br/>
--------rabbitmq-consumer-samples-springboot：springboot消费者版本<br/>
----rabbitmq-interfaces： 公共实体、配置<br/>
----rabbitmq-produce-samples：生产者模块父工程<br/>
--------rabbitmq-produce-samples-spring：spring生产者版本<br/>
--------rabbitmq-produce-samples-springboot：springboot生产者版本


### 功能提供

V1.0.0（19-10）
- 1、[通用] 四种交换机模式使用（Direct、Fanout、Topic、Headers）
- 2、[进阶] 死信队列、AE备份交换机过程
- 3、[版本] 提供spring、springboot版本代码实现





















