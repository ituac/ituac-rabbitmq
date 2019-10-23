package org.itauc.rabbitmq.config;

/**
 * Rabbit base config
 * @author ituac
 */

public class BaseMqConfig {
	
		// 消息编码
		public static final String MESSAGE_ENCODING = "UTF-8";

		// direct交换机
		public final static String DIRECT_EXCHANGE_MESSAGE_ISSUE = "direct.exchange.message.issue";
		// direct队列
		public final static String DIRECT_QUEUE_MESSAGE_ISSUE = "direct.queue.message.issue";
		
		
		
		
		// topic 订阅交换机
		public final static String TOPIC_EXCHANGE_USER_ISSUE = "topic.exchange.user.issue";
		// topic 队列
		public final static String TOPIC_QUEUE_USER_ISSUE = "topic.queue.message.issue";
		// topic 队列
		public final static String TOPIC_QUEUE_ALL_USER_ISSUE = "topic.queue_all.user.issue";
		
		
		
		// fanout 交换机
		public static final String FANOUT_EXCHANGE_CITY_ISSUE = "fanout.exchange.city.issue";
		// fanout 队列
		public static final String FANOUT_QUEUE_BJ_ISSUE = "fanout.queue.bj.issue";
		public static final String FANOUT_QUEUE_SH_ISSUE = "fanout.queue.sh.issue";
		public static final String FANOUT_QUEUE_GZ_ISSUE = "fanout.queue.gz.issue";
		
		/**
		 * routingkey
		 */
		public static final String ROUTING_KEY_MESSAGE_ISSUE = "routing.key_message_issue";		//消息
		public static final String ROUTING_KEY_USER_ISSUE = "routing.key.user.issue";			//用户
		public static final String ROUTING_KEY_XXXX_ISSUE = "routing.key.*.issue";			//用户
		public static final String ROUTING_KEY_ALL_CITY_ISSUE = "routing.key.all.city.issue";	//城市

}
