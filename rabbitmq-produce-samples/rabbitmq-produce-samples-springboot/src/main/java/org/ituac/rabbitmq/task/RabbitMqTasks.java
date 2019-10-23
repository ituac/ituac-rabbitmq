package org.ituac.rabbitmq.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.itauc.rabbitmq.message.CityMq;
import org.itauc.rabbitmq.message.MessageMq;
import org.itauc.rabbitmq.message.UserMq;
import org.ituac.rabbitmq.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时生产数据到消息队列
 * @author ituac
 */

@Component
public class RabbitMqTasks {
	
	private static final SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	private Sender sender;
	
	// 10秒钟执行一次
    //@Scheduled(fixedDelay = 10000)
    public void reportMessageCurrentTime() {
    	// 对象传输，对象必须实现Serializable序列化
        MessageMq message = new MessageMq();
        message.setId(1);
        message.setContent("这是消息内容");
        message.setLocked(true);
        message.setType(2);
        message.setCreate_time(new Date());
        message.setUpdate_time(new Date());
        
    	sender.sendMessage(message);
        System.out.println("now time：" + date.format(new Date()));
    }
    
    // 9秒钟执行一次
    //@Scheduled(fixedDelay = 9000)
    public void reportUserCurrentTime() {
    	// 对象传输，对象必须实现Serializable序列化
    	UserMq user = new UserMq();
    	user.setAge(30);
    	user.setId(2);
    	user.setMessage("I'm topic");
    	user.setName("波罗星");
    	user.setSex(2);
    	user.setTime(new Date());
        
    	sender.sendUser(user);
        System.out.println("now time：" + date.format(new Date()));
    }
    
    // 15秒钟执行一次
    @Scheduled(fixedDelay = 15000)
    public void reportCityCurrentTime() {
    	// 对象传输，对象必须实现Serializable序列化
    	CityMq city = new CityMq();
    	city.setId(2);
    	city.setCode("200");
    	city.setMsg("城市重要通知");
    	city.setTime(new Date());
        
    	sender.sendCity(city);
        System.out.println("now time：" + date.format(new Date()));
    }
}
