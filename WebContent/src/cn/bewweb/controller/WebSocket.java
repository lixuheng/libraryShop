package cn.bewweb.controller;

import java.io.*;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.websocket.*;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map.Entry;

@ServerEndpoint("/websocket")
public class WebSocket {

	private static Logger log = LoggerFactory.getLogger(WebSocket.class);
	/** 使用Hashtable来保存所有连接信息 */
	private final static Hashtable<Session, WebSocket> clients = new Hashtable<Session, WebSocket>();

	@OnOpen
	public void onOpen(Session session) {
		log.info("openid--" + session.getId());
	}

	/**
	 * 连接关闭调用的方法(服务器自动调用)
	 */
	@OnClose
	public void onClose(Session session) {
		log.info("onClose");
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.info("onError");
		log.info("客户端意外地主动关闭了一个连接，或者是接收方不存在，或其他错误");
		try {
			session.close();
		} catch (IOException e) {
			log.info("clsoe error");
			e.printStackTrace();
		}
		error.printStackTrace();
	}

	/**
	 * 接收文本消息(服务器自动调用)
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            服务器响应的session
	 */

	@OnMessage
	public void onMessage(String message, Session session) {

	}

	/**
	 * 接收二进制消息(服务器自动调用) 在此实现保存到服务器磁盘，并生成一个url返回供用户下载
	 * 
	 * @param b
	 *            数据缓存区
	 * @param last
	 *            是否为最后一块
	 * @param session
	 *            发送方的session
	 * @throws IOException
	 */
	@OnMessage
	public void onBlobMessage(byte[] b, boolean last, Session session) {

	}

	public boolean isOnline(String talkTo) {
		boolean b = false;
		return b;
	}

	/**
	 * 发送文本消息
	 * 
	 * @param message
	 *            要发送的消息内容
	 * @param session
	 *            接收方
	 */
	public void sendMessage(String message, Session receiver) {
		Basic basic = receiver.getBasicRemote();
		try {
			basic.sendText(message);
		} catch (IOException e) {
			log.error("发送消息失败");
			e.printStackTrace();
		}
	}

	/**
	 * 带命令的发送文本消息
	 * 
	 * @param command
	 *            命令
	 * @param message
	 *            要发送的消息内容
	 * @param session
	 *            接收方
	 */
	public void sendMessage(String command, String message, Session receiver) {
		Basic basic = receiver.getBasicRemote();
		try {
			basic.sendText(command + message);
		} catch (IOException e) {
			log.error("发送消息失败");
			e.printStackTrace();
		}
	}

	/**
	 * 发送二进制文件的方法
	 * 
	 * @param ByteBuffer
	 *            文件的ByteBuffer
	 * @throws IOException
	 */
	public void sendData(ByteBuffer data, Session receiver) {
		Basic basic = receiver.getBasicRemote();
		try {
			basic.sendBinary(data);
		} catch (IOException e) {
			log.error("发送消息失败");
			e.printStackTrace();
		}
	}

	/**
	 * 响应式一对一发送消息
	 * 
	 * @param message
	 *            消息
	 * @param fromSession
	 *            收到消息后响应的session
	 */
	public void oneToOneChat(String message, Session sender) {

	}

}
