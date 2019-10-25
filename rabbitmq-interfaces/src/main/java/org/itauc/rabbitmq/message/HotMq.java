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

}
