package org.ituac.rabbitmq;


import org.itauc.rabbitmq.config.BaseMqConfig;
import org.itauc.rabbitmq.message.*;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * 消息生产者
 *
 * @author ituac
 */

@Component
public class Sender extends BaseMqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /* ---------------------------------------------------- Direct 形式 -------------------------------------------- */

    /**
     * 生产业务消息
     *
     * @param o 消息对象
     */
    public void sendMessage(Object o) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessageMq message = (MessageMq) o;
        this.rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_MESSAGE_ISSUE, ROUTING_KEY_MESSAGE_ISSUE, message, correlationData);
    }
    /* ---------------------------------------------------- Topic 形式 -------------------------------------------- */

    /**
     * 订阅用户相关业务
     *
     * @param o 消息对象
     */
    public void sendUser(Object o) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        UserMq user = (UserMq) o;
        this.rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_USER_ISSUE, ROUTING_KEY_USER_ISSUE, user, correlationData);
    }

    /* ---------------------------------------------------- Fanout 形式 -------------------------------------------- */

    /**
     * 订阅用户相关业务
     *
     * @param o 消息对象
     */
    public void sendCity(Object o) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        CityMq city = (CityMq) o;
        this.rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_CITY_ISSUE, ROUTING_KEY_ALL_CITY_ISSUE, city, correlationData);
    }


    /* ---------------------------------------------------- 死信队列 形式 -------------------------------------------- */
    /**
     * 死信保证数据相关业务
     * @param o 消息对象
     */
    public void sendDead(Object o) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //      声明消息处理器  这个对消息进行处理  可以设置一些参数   对消息进行一些定制化处理   我们这里  来设置消息的编码  以及消息的过期时间  因为在.net 以及其他版本过期时间不一致   这里的时间毫秒值 为字符串
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            //          设置编码
            messageProperties.setContentEncoding("utf-8");
            //		  设置消息Id
            messageProperties.setCorrelationId(UUID.randomUUID().toString());
            return message;
        };
        MailMq mail = (MailMq) o;

        //      向DL_QUEUE 发送消息  10*1000毫秒后过期 形成死信s
        rabbitTemplate.convertAndSend(TT_ITUAC_MAIL_EXCHANGE_LETTER, TT_ITUAC_MAIL_ROUTING_KEY, mail, messagePostProcessor, correlationData);
    }


    /* ---------------------------------------------------- AE 形式 -------------------------------------------- */

    public void sendHotSource(Object o) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        HotMq hot = (HotMq) o;
        rabbitTemplate.convertAndSend(AE_ITUAC_SOURCE_EXCHANGE_ISSUE, AE_ITUAC_SOURCE_HOT_ROUTING_KEY, hot,correlationData);
    }

    public void sendHotBack(Object o) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        HotMq hot = (HotMq) o;
        // 进行测试当routing key错误的情况
        rabbitTemplate.convertAndSend(AE_ITUAC_SOURCE_EXCHANGE_ISSUE, "xxx", hot,correlationData);
    }



}
