package org.itauc.rabbitmq.message;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CityMq implements Serializable{

	private static final long serialVersionUID = -4224579455856863405L;
	
	private int id;
	
	private String code;
	
	private String msg;
	
	private Date time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
