package com.applet.appletsocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import com.tcsb.socket.controller.WebsocketController;
import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;


@Controller
@RequestMapping("/socket")
public class AppletSocketController {

    public static WebSocketClient client;

//    public static void createClient(){
//
//    }

    @RequestMapping("/send")
    @ResponseBody
    public void sendSock(MessageVo msg) throws IOException, URISyntaxException {
        if (AppletSocketController.client == null) {
            WebsocketController.createAppletSocketClient();

        }
        while (!AppletSocketController.client.getReadyState().equals(READYSTATE.OPEN)) {
            //System.out.print("1");
            try {
                Thread.sleep(100);
                System.out.print("shoudongshuimian");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("2");

        String ms = JSON.json(msg);
//        System.out.println(ms);
        AppletSocketController.client.send(ms);

    }


}
