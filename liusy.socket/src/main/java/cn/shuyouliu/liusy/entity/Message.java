package cn.shuyouliu.liusy.entity;

import java.io.Serializable;

public class Message  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7667692215459596418L;
	private int alonght;// 总长度
	private String msg;
	public int getAlonght() {
		return alonght;
	}
	public void setAlonght(int alonght) {
		this.alonght = alonght;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	} 
	
	@Override
	public String toString() {
		return msg;
	}
	

}
