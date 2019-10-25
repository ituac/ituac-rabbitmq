package org.itauc.rabbitmq.message;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 邮件业务
 * @author ituac
 */

@Data
public class MailMq implements Serializable{

	private static final long serialVersionUID = 8670318779955593332L;
	
	private int id;
	
	private String mail;
	
	private Date time;
	
}
