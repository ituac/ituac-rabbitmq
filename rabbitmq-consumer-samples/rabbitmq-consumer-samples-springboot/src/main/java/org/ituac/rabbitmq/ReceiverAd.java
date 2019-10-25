package org.ituac.rabbitmq;

import java.io.IOException;
import org.itauc.rabbitmq.config.BaseMqConfig;
import org.itauc.rabbitmq.message.HotMq;
import org.itauc.rabbitmq.message.MailMq;
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
public class ReceiverAd extends BaseMqConfig {
	
    private static final Logger log = LoggerFactory.getLogger(ReceiverAd.class);

    /* ---------------------------------------------------- 死信形式 -------------------------------------------- */
	/**
	 * 死信队列
	 * @param message
	 * @param channel
	 * @throws Exception 
	 */
    @RabbitListener(queues = TT_DEAD_QUEUE_LETTER)
    public void processDeadMessage(@Payload MailMq res, Message message, Channel channel) throws Exception {
    		int i = 1 / 1;
	        log.info(">>>>>>> dead message  10s 后 消费消息： " + res.toString()+":"+i);
	        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    

    /*
     * 监听替补队列 来验证死信.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     */
    @RabbitListener(queues = TT_ITUAC_MAIL_QUEUE_LETTER)
    public void redirect(@Payload MailMq res,Message message, Channel channel) throws Exception {
    	try {
			log.info(">>>>>>> recess： " + res.toString()+">>id:"+message.getMessageProperties().getCorrelationId());
			int i = 1/0;
			log.info(">>>>>>> success： " + res.toString() +">>i:"+ i);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			log.info("cousumers ribbitmq message error");
			//拒绝消息，并将消息放入队列中	
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		}
    }
    
    
    
    /* ---------------------------------------------------- AE形式 -------------------------------------------- */
    // 源队列
    @RabbitListener(queues = AE_ITUAC_SOURCE_HOT_QUEUE_ISSUE)
    public void sourceQueue(@Payload HotMq res,Message message, Channel channel) throws IOException {
        System.out.println("成功路由到，这是：" + res.toString());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    //备份队列
    @RabbitListener(queues = AE_ITUAC_BACK_HOT_QUEUE_ISSUE)
    public void backQueue(@Payload HotMq res,Message message, Channel channel) throws IOException {
        System.out.println("错误路由到，这是：" + res.toString());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    
    


}
