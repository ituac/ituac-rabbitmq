package org.ituac.rabbitmq;


import org.itauc.rabbitmq.config.BaseMqConfig;
import org.itauc.rabbitmq.message.CityMq;
import org.itauc.rabbitmq.message.MessageMq;
import org.itauc.rabbitmq.message.UserMq;
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


}
