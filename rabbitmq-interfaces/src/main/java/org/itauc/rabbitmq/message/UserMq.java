package org.itauc.rabbitmq.message;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserMq implements Serializable{

	private static final long serialVersionUID = -7843753875101842209L;
	
	private int id;
	
	private String name;
	
	private int age;
	
	private int sex;
	
	private String message;
	
	private Date time;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
