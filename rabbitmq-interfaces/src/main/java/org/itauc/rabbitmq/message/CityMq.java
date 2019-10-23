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
	

}
