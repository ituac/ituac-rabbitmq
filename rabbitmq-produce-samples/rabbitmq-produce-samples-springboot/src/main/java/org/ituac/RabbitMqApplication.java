package org.ituac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Rabbit 生产者
 * @author ituac
 */

@EnableScheduling // 定时任务注解
@SpringBootApplication
public class RabbitMqApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RabbitMqApplication.class, args);
	}
}
