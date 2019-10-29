package org.itauc.rabbitmq.message;

import java.io.Serializable;
import lombok.Data;

/**
 * 热数据集
 * @author ituac
 */

@Data
public class HotMq implements Serializable{

	private static final long serialVersionUID = 1614803394688388026L;
	
	private int id;
	
	private String name;
	
	private String descp;
	
	private int like;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}
}
