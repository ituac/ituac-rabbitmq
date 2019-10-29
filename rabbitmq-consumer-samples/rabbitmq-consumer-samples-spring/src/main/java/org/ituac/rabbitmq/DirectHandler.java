package org.ituac.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DirectHandler implements MessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void onMessage(Message msg) {
        //msg就是rabbitmq传来的消息
        // 使用jackson解析
            /*JsonNode jsonData = MAPPER.readTree(msg.getBody());
            System.out.println("id======" + jsonData.get("id").asText()
                    + ",name======" + jsonData.get("name").asText());*/
        System.out.println(new String(msg.getBody()));
    }

}