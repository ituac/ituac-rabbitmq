package org.ituac.rabbitmq.config;

import org.itauc.rabbitmq.config.BaseMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * Rabbit 队列配置类
 * @author ituac
 */

@Configuration
@Order(1)	//配置加载顺序优先级
public class RabbitQueueConfig extends BaseMqConfig {
	
	@Resource
    private RabbitTemplate rabbitTemplate;
	
	@Bean
    public AmqpTemplate amqpTemplate() {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
        // 使用jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        // 开启returncallback     yml 需要 配置    publisher-returns: true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        // 消息确认  yml 需要配置   publisher-returns: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback ack: {} correlationData: {} cause: {}", ack, correlationData, cause);
            if (ack) {
                log.info("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.info("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }
	
	/* ---------------------------------------------------- Direct 形式 -------------------------------------------- */
	
	// direct模式消息队列
	@Bean
	public Queue issueMessageDirectQueue() {
		//return new Queue(DIRECT_QUEUE_MESSAGE_ISSUE);						//声明一个队列
		return QueueBuilder.durable(DIRECT_QUEUE_MESSAGE_ISSUE).build();	// 声明一个队列并进行持久化
	}
	
	// direct模式交换机
	@Bean("directMessageExchange")
    public Exchange directMessageExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE_MESSAGE_ISSUE).durable(true).build();		// 声明一个直连交换机，并进行持久化
    }
	
	@Bean
    public Binding directBinding(@Qualifier("issueMessageDirectQueue") Queue queue, @Qualifier("directMessageExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_MESSAGE_ISSUE).noargs();
    }
	
	
	
	
	
	/* ---------------------------------------------------- Topic 形式 -------------------------------------------- */
	// topic订阅模式消息队列
	@Bean("issueUserTopicQueue")
	public Queue issueUserTopicQueue() {
		//return new Queue(TOPIC_QUEUE_USER_ISSUE);						//声明一个队列
		return QueueBuilder.durable(TOPIC_QUEUE_USER_ISSUE).build();	// 声明一个队列并进行持久化
	}
	
	// topic订阅模式消息队列
	@Bean("issueAllUserTopicQueue")
	public Queue issueAllUserTopicQueue() {
		//return new Queue(TOPIC_QUEUE_USER_ISSUE);						//声明一个队列
		return QueueBuilder.durable(TOPIC_QUEUE_ALL_USER_ISSUE).build();	// 声明一个队列并进行持久化
	}
	
	// topic订阅模式交换机
	@Bean("topicUserExchange")
    public Exchange topicUserExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_USER_ISSUE).durable(true).build();		// 声明一个订阅交换机，并进行持久化
    }
	
	@Bean
    public Binding topicUserBinding(@Qualifier("issueUserTopicQueue") Queue queue, @Qualifier("topicUserExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_USER_ISSUE).noargs();
    }
	
	// 关注*（user交换机）
	@Bean
    public Binding topicAllUserBinding(@Qualifier("issueAllUserTopicQueue") Queue queue, @Qualifier("topicUserExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_XXXX_ISSUE).noargs();
    }
	
	
	
	
	/* -------------------------------------------- fanout 广播形式 ------------------------------------------- */
	// fanout 广播模式消息队列 北京
	@Bean("issueBjFanoutQueue")
	public Queue issueBjFanoutQueue() {
		//return new Queue(TOPIC_QUEUE_USER_ISSUE);						//声明一个队列
		return QueueBuilder.durable(FANOUT_QUEUE_BJ_ISSUE).build();	// 声明一个队列并进行持久化
	}
	
	//上海
	@Bean("issueShFanoutQueue")
	public Queue issueShFanoutQueue() {
		//return new Queue(TOPIC_QUEUE_USER_ISSUE);						//声明一个队列
		return QueueBuilder.durable(FANOUT_QUEUE_SH_ISSUE).build();	// 声明一个队列并进行持久化
	}
	
	//广州
	@Bean("issueGzFanoutQueue")
	public Queue issueGzFanoutQueue() {
		//return new Queue(TOPIC_QUEUE_USER_ISSUE);						//声明一个队列
		return QueueBuilder.durable(FANOUT_QUEUE_GZ_ISSUE).build();	// 声明一个队列并进行持久化
	}
	
	// topic订阅模式交换机
	@Bean("fanoutCityExchange")
    public Exchange fanoutCityExchange() {
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_CITY_ISSUE).durable(true).build();		// 配置广播路由器
    }
	
	// 绑定北京
	@Bean
    Binding bindingExchangeBj(@Qualifier("issueBjFanoutQueue") Queue queue, @Qualifier("fanoutCityExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ALL_CITY_ISSUE).noargs();
    }
	
	// 绑定上海
	@Bean
    Binding bindingExchangeSh(@Qualifier("issueShFanoutQueue") Queue queue, @Qualifier("fanoutCityExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ALL_CITY_ISSUE).noargs();
    }
	
	// 绑定广州
	@Bean
	Binding bindingExchangeGz(@Qualifier("issueGzFanoutQueue") Queue queue, @Qualifier("fanoutCityExchange") Exchange exchange) {
	    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ALL_CITY_ISSUE).noargs();
	}

	
	
}
