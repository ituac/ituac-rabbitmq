package org.ituac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Rabbitmq 消费者
 * @author ituac
 */

@SpringBootApplication
public class RabbitMqApplication {

	public static void main(String [] args){
        SpringApplication.run(RabbitMqApplication.class,args);
    }
	
}
