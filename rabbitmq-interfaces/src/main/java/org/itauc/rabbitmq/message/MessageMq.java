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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
