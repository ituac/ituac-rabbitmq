package org.itauc.rabbitmq.message;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 消息业务消息体
 * @author ituac
 */

@Data
public class MessageMq implements Serializable{

	private static final long serialVersionUID = 5817205742019742560L;
	
	/**
	 * 内容id
	 */
	private int id;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 类型
	 */
	private int type;
	
	/**
	 * 是否删除
	 */
	private boolean locked;
	
	/**
	 * 创建时间
	 */
	private Date create_time;

	/**
	 * 更新时间
	 */
	private Date update_time;

}
