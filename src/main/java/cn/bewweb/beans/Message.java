package cn.bewweb.beans;

import java.io.File;

import javax.websocket.Session;

import cn.bewweb.entities.User;

public class Message {
	private User user;
	private Session sender;
	private Session receiver;
	private String text;
	
	
	public Session getSender() {
		return sender;
	}
	public void setSender(Session sender) {
		this.sender = sender;
	}
	public Session getReceiver() {
		return receiver;
	}
	public void setReceiver(Session receiver) {
		this.receiver = receiver;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
