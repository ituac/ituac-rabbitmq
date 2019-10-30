package org.ituac.rabbitmq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DirectHandler implements MessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void onMessage(Message msg) {
        //msg就是rabbitmq传来的消息
        // 使用jackson解析
        try {
            JsonNode jsonData =  MAPPER.readTree(msg.getBody());
            System.out.println("content======" + jsonData.get("content").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}