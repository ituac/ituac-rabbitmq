package org.ituac.rabbitmq.config;

import org.itauc.rabbitmq.config.BaseMqConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Rabbit 进阶队列配置类
 * @author ituac
 */

@Order(2)			//配置加载顺序优先级，最低值具有高优先级
@Configuration
public class RabbitAdConfig extends BaseMqConfig{
	
	
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
