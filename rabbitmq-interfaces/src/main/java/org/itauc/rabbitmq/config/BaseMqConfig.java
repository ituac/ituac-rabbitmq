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
		public static final String ROUTING_KEY_XXXX_ISSUE = "routing.key.*.issue";				//用户
		public static final String ROUTING_KEY_ALL_CITY_ISSUE = "routing.key.all.city.issue";	//城市
	    

		
		
		
		
		/*----------------------------------------------进阶---------------------------------------------*/
		
		
		// 死信相关
		public static final String TT_ITUAC_MAIL_QUEUE_LETTER = "tt.ituac.mail.queue.letter";			// 正常队列
		public static final String TT_ITUAC_MAIL_EXCHANGE_LETTER = "tt.ituac.mail.exchange.letter";		//	业务交换机
		public static final String TT_ITUAC_MAIL_ROUTING_KEY = "tt.ituac.mail.routing.key";				//	业务routing_key
		public static final String TT_DEAD_QUEUE_LETTER = "tt.dead.letter.queue";						//	死信队列
		
		public static final String TT_DEAD_EXCHANGE_LETTER = "tt.dead.letter.exchange";					//	死信交换机
		public static final String ROUTING_KEY_DEAD_LETTER_KEY = "tt.dead.letter.routing.key";			//死信routekey
		
		
		
		
		
		// AE相关
		public static final String AE_ITUAC_SOURCE_EXCHANGE_ISSUE = "ae.ituac.source.exchange_issue";			//源交换机
		public static final String AE_ITUAC_SOURCE_HOT_QUEUE_ISSUE = "ae.ituac.source.hot.queue.issue";			//源队列
		public static final String AE_ITUAC_SOURCE_HOT_ROUTING_KEY = "ae.ituac.source.hot.routing.key";			//源队列
		
		public static final String AE_ITUAC_BACK_EXCHANGE_ISSUE = "ae.ituac.back.exchange_issue";				//备份交换机
		public static final String AE_ITUAC_BACK_HOT_QUEUE_ISSUE = "ae.ituac.back.hot.queue.issue";				//备份队列
		
		
		
		//消息分发相关
		public final static String DIRECT_EXCHANGE_ROUND_ROBIN_ISSUE = "direct.exchange.round.robin.issue";
		public final static String DIRECT_QUEUE_ROUND_ROBIN_ISSUE = "direct.queue.round.robin.issue";
		public final static String DIRECT_ROUND_ROBIN_ROTING_KEY = "direct.round.robin.roting.key";

		


		
		

		
		
		

}
