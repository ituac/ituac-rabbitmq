package org.ituac.rabbitmq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Rabbit 消费者
 * @author ituac
 */

@Component
public class AndunTTDeadReceiver implements MessageListener {
	
    private static final Logger log = LoggerFactory.getLogger(AndunTTDeadReceiver.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message) {
        try {
            JsonNode jsonData =  MAPPER.readTree(message.getBody());
            System.out.println("dead message  10s 后 消费消息>>>>>>> success： " + jsonData.get("content").asText());
            log.info("method  onMessage>>>>>>> success： " + jsonData.get("content").asText());
        } catch (Exception e){
            e.printStackTrace();
            log.info("method  onMessage>>>>>>> error： " + new String(message.getBody()));
        }
    }
}
