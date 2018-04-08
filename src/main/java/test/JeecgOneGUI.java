package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException; 
import java.net.URI; 
import java.net.URISyntaxException; 
import java.nio.ByteBuffer; 
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.dubbo.common.json.JSON;






/**
 * 【单表模型】代码生成器入口
 * @author 张代浩
 * @site www.jeecg.org
 *
 */
public class JeecgOneGUI {
	
	public static WebSocketClient client;
	
	public static void main(String[] args) throws URISyntaxException, NotYetConnectedException, IOException {
	    client = new WebSocketClient(new URI("ws://localhost:9090/ddmShopV2/websocket.ws/123"),new Draft_17()) {
	    	
	        @Override
	        public void onOpen(ServerHandshake arg0) {
	            System.out.println("打开链接");
	        }

	        @Override
	        public void onMessage(String arg0) {
	            System.out.println("收到消息"+arg0);
	        }

	        @Override
	        public void onError(Exception arg0) {
	            arg0.printStackTrace();
	            System.out.println("发生错误已关闭");
	        }

	        @Override
	        public void onClose(int arg0, String arg1, boolean arg2) {
	            System.out.println("链接已关闭");
	        }

	        @Override
	        public void onMessage(ByteBuffer bytes) {
	            try {
	                System.out.println(new String(bytes.array(),"utf-8"));
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	        }


	    };

	    client.connect();

	    while(!client.getReadyState().equals(READYSTATE.OPEN)){
	        System.out.println("还没有打开");
	    }
	    System.out.println("打开了");
	    //send();
	    MessageVo msg = new MessageVo();
		
		msg.setBusinessCode("0");
		msg.setShopId("8a9ad8035c0c5381015c0c7461db0023");
		msg.setBusinessId("20171130183641887268815631655230");
		
		String ms = JSON.json(msg);
	    client.send(ms);
	    //client.send("hello world");
	}
}
