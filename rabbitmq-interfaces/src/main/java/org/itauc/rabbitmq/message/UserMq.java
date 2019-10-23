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

}
