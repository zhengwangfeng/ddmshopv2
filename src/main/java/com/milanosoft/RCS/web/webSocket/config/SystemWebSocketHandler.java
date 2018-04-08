/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年5月24日 下午5:51:28
 */
package com.milanosoft.RCS.web.webSocket.config;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年5月24日 下午5:51:28
 */
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author lzk
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {

	public static final Map<Long, WebSocketSession> userSocketSessionMap;

	static {
		userSocketSessionMap = new HashMap<Long, WebSocketSession>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("connect to the websocket success......");
		session.sendMessage(new TextMessage("Server:connected OK!"));
		Long uid = (Long) session.getAttributes().get("user");
		if (userSocketSessionMap.get(uid) == null) {
			userSocketSessionMap.put(uid, session);
		}
	}

	@Override
	public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {
		System.out.println(wsm.getPayload());
		System.out.println(wss.getId());
		TextMessage returnMessage = new TextMessage(wsm.getPayload() + " received at server");

		System.out.println(wss.getHandshakeHeaders().getFirst("Cookie"));
		// wss.sendMessage(returnMessage);
		broadcast(returnMessage);
		/*
		 * if(wsm.getPayloadLength()==0)return; Message msg=new
		 * Gson().fromJson(wsm.getPayload().toString(),Message.class);
		 * msg.setDate(new Date()); sendMessageToUser(msg.getTo(), new
		 * TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss"
		 * ).create().toJson(msg)));
		 */

	}

	@Override
	public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
		if (wss.isOpen()) {
			wss.close();
		}
		System.out.println("websocket connection closed......");
		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<Long, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(wss.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) throws Exception {
		System.out.println("websocket connection closed......");

		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<Long, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(wss.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void broadcast(final TextMessage message) throws IOException {
		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 多线程群发
		while (it.hasNext()) {
			final Entry<Long, WebSocketSession> entry = it.next();
			if (entry.getValue().isOpen()) {
				// entry.getValue().sendMessage(message);
				new Thread(new Runnable() {
					public void run() {
						try {
							if (entry.getValue().isOpen()) {
								entry.getValue().sendMessage(message);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 * 
	 * @param userName
	 * @param message
	 * @throws IOException
	 */
	public void sendMessageToUser(Long uid, TextMessage message) throws IOException {
		WebSocketSession session = userSocketSessionMap.get(uid);
		if (session != null && session.isOpen()) {
			session.sendMessage(message);
		}
	}

}