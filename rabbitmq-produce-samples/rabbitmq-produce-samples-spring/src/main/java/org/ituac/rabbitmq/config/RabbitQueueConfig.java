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

	/* ---------------------------------------------------- 死信形式 -------------------------------------------- */

	//创建正常业务队列
	@Bean
	public Queue mailQueue() {
		//创建正常队列
		//return new Queue(TT_ITUAC_MAIL_QUEUE_LETTER,true, false, false, map);
		return QueueBuilder.durable(TT_ITUAC_MAIL_QUEUE_LETTER)
				//以下是重点：当变成死信队列时，会转发至 路由为x-dead-letter-exchange及x-dead-letter-routing-key的队列中
				.withArgument("x-dead-letter-exchange", TT_DEAD_EXCHANGE_LETTER)
				.withArgument("x-dead-letter-routing-key", ROUTING_KEY_DEAD_LETTER_KEY)
				.build();
	}

	// 创建业务交换机
	@Bean
	public FanoutExchange mailExchange() {
		return new FanoutExchange(TT_ITUAC_MAIL_EXCHANGE_LETTER, true, false);
	}

	// 绑定交换机
	@Bean
	Binding bindingExchangeMail(@Qualifier("mailQueue")Queue queue,@Qualifier("mailExchange")FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue).to(fanoutExchange);
	}

	// 创建死信队列
	@Bean
	public Queue deadQueue(){
		Queue queue = new Queue(TT_DEAD_QUEUE_LETTER, true);
		return queue;
	}

	// 创建死信交换机
	@Bean
	public DirectExchange deadExchange() {
		return new DirectExchange(TT_DEAD_EXCHANGE_LETTER, true, false);
	}

	// 绑定死信队列
	@Bean
	public Binding bindingDeadMailExchange(@Qualifier("deadQueue")Queue deadQueue,@Qualifier("deadExchange")DirectExchange deadExchange) {
		return BindingBuilder.bind(deadQueue).to(deadExchange).with(ROUTING_KEY_DEAD_LETTER_KEY);
	}






	/* ---------------------------------- AE（alternate-exchange）形式｜备份交换机 ------------------------------ */

	//AE, 创建正常队列
	@Bean(name="sourceHotQueue")
	public Queue sourceHotQueue() {
		return new Queue(AE_ITUAC_SOURCE_HOT_QUEUE_ISSUE,true);
	}

	//创建源交换机，并绑定备份交换机
	@Bean(name="sourceExchange")
	public DirectExchange sourceExchange() {
		return ExchangeBuilder.directExchange(AE_ITUAC_SOURCE_EXCHANGE_ISSUE).durable(true)
				.autoDelete()
				.withArgument("alternate-exchange", AE_ITUAC_BACK_EXCHANGE_ISSUE).build();	// 源交换机没有匹配到会转发到备交换机
	}

	//创建备用队列
	@Bean(name="backHotQueue")
	public Queue backHotQueue() {
		return new Queue(AE_ITUAC_BACK_HOT_QUEUE_ISSUE,true);
	}

	//创建备用交换机
	@Bean(name="backExchange")
	public FanoutExchange backExchange() {
		// 此处的交换机要和 源交换机中 alternate-exchange 参数的值一致
		return new FanoutExchange(AE_ITUAC_BACK_EXCHANGE_ISSUE);
	}

	//被路由到的 交换器绑定队列
	@Bean
	Binding bindingExchangeGood(@Qualifier("sourceHotQueue")Queue sourceHotQueue,@Qualifier("sourceExchange")DirectExchange sourceExchange) {
		return BindingBuilder.bind(sourceHotQueue).to(sourceExchange).with(AE_ITUAC_SOURCE_HOT_ROUTING_KEY);
	}

	//未被路由到的 交换器绑定队列
	@Bean
	Binding bindingAeExchange(@Qualifier("backHotQueue")Queue backHotQueue,@Qualifier("backExchange")FanoutExchange backExchange) {
		return BindingBuilder.bind(backHotQueue).to(backExchange);
	}
	
}
