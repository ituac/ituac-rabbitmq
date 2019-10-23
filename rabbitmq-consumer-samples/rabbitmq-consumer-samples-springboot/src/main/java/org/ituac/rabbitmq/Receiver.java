package org.ituac.rabbitmq;

import java.io.IOException;
import org.itauc.rabbitmq.config.BaseMqConfig;
import org.itauc.rabbitmq.message.CityMq;
import org.itauc.rabbitmq.message.MessageMq;
import org.itauc.rabbitmq.message.UserMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component; 
import com.rabbitmq.client.Channel;

/**
 * Rabbit 消费者
 * @author ituac
 */

@Component
public class Receiver extends BaseMqConfig {
	
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);


	/* ---------------------------------------------------- Direct 形式 -------------------------------------------- */
	/**
	 * Direct 模式消息队列
	 * @param message
	 * @param channel
	 * @throws IOException the io exception  这里异常需要处理
	 */
    @RabbitListener(queues = DIRECT_QUEUE_MESSAGE_ISSUE)
    public void processDirectMessage(@Payload MessageMq res, Message message, Channel channel) throws IOException {
    	try {
            log.info(">>>>>>> success： " + res.toString());
        } catch (Exception e){ 
        	e.printStackTrace();
        	log.info(">>>>>>> error： " + new String(message.getBody()));
        }finally {
        	// 后续调整其他部分
        	// 对处理好的消息进行应答
        	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        	
        }
    }
    
    
    
    
    
	/* ---------------------------------------------------- Topic 形式 -------------------------------------------- */
    @RabbitListener(queues=TOPIC_QUEUE_USER_ISSUE)    //监听器监听指定的Queue
    public void processDirectMessageUser1(@Payload UserMq res, Message message, Channel channel) throws IOException {
    	try {
            log.info(">>>>>>> user1_success： " + res.toString());
        } catch (Exception e){ 
        	e.printStackTrace();
        	log.info(">>>>>>> user1_error： " + new String(message.getBody()));
        }finally {
        	// 后续调整其他部分
        	// 对处理好的消息进行应答
        	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        	
        }
    }

    @RabbitListener(queues=TOPIC_QUEUE_ALL_USER_ISSUE)    //监听器监听指定的Queue
    public void processDirectMessageUser2(@Payload UserMq res, Message message, Channel channel) throws IOException {
    	try {
            log.info(">>>>>>> user2_success： " + res.toString());
        } catch (Exception e){ 
        	e.printStackTrace();
        	log.info(">>>>>>> user2_error： " + new String(message.getBody()));
        }finally {
        	// 后续调整其他部分
        	// 对处理好的消息进行应答
        	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        	
        }
    }
    
    
    
    /* ---------------------------------------------------- Fanout 形式 -------------------------------------------- */
    
    //北京城市信息
    @RabbitListener(queues=FANOUT_QUEUE_BJ_ISSUE)    //监听器监听指定的Queue
    public void processFanoutMessageUser1(@Payload CityMq res, Message message, Channel channel) throws IOException {
    	try {
            log.info(">>>>>>> bj_info： " + res.toString());
            if(res.getCode().equals("200")) {
            	log.info(">>>>>>> bj_success: 北京收到");
            }
        } catch (Exception e){ 
        	e.printStackTrace();
        	log.info(">>>>>>> user1_error： " + new String(message.getBody()));
        }finally {
        	// 后续调整其他部分
        	// 对处理好的消息进行应答
        	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        	
        }
    }
    
    //监听上海
    @RabbitListener(queues=FANOUT_QUEUE_SH_ISSUE)    //监听器监听指定的Queue
    public void processFanoutSh(@Payload CityMq res, Message message, Channel channel) throws IOException {
    	try {
    		log.info(">>>>>>> bj_info： " + res.toString());
            if(res.getCode().equals("200")) {
            	log.info(">>>>>>> bj_success: 上海收到");
            }
        } catch (Exception e){ 
        	e.printStackTrace();
        	log.info(">>>>>>> user1_error： " + new String(message.getBody()));
        }finally {
        	// 后续调整其他部分
        	// 对处理好的消息进行应答
        	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        	
        }
    }
    
    //监听广州
    @RabbitListener(queues=FANOUT_QUEUE_GZ_ISSUE)    //监听器监听指定的Queue
    public void processFanoutGz(@Payload CityMq res, Message message, Channel channel) throws IOException {
    	try {
    		log.info(">>>>>>> bj_info： " + res.toString());
            if(res.getCode().equals("200")) {
            	log.info(">>>>>>> bj_success: 广州收到");
            }
        } catch (Exception e){ 
        	e.printStackTrace();
        	log.info(">>>>>>> user1_error： " + new String(message.getBody()));
        }finally {
        	// 后续调整其他部分
        	// 对处理好的消息进行应答
        	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        	
        }
    }
    


}
