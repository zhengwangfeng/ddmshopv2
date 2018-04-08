package com.tcsb.socket.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.applet.appletsocket.AppletSocketController;
import com.tcsb.callservice.entity.TcsbCallServiceEntity;
import com.tcsb.shopmanage.entity.TcsbShopDeskManageEntity;
import com.tcsb.shopmanage.entity.TcsbShopManageEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * WebSocket处理类
 * 基本的SERVLET连接SOCKET
 *
 * @author HUYONG
 * @create 2016-12-06
 */
@ServerEndpoint("/websocket.ws/{userCode}/{cpuCode}")
@Component
public class WebsocketController {

    @Autowired
    private SystemService systemService;

    private static WebsocketController websocketController;

    @PostConstruct //@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行
    public void init() {
        websocketController = this;
        websocketController.systemService = this.systemService;
    }

    public static Map<String, Deque<String>> msgList = new HashMap<>();

    private static int onlineCount = 0;
    private static Logger LOGGER = LoggerFactory.getLogger(WebsocketController.class);

    public static Map<String, Map<String, Session>> webSocketSet = new ConcurrentHashMap<>();
    private Session session;


    /**
     * 打开连接事触发
     *
     * @param session
     * @param userCode shopId
     * @param cpuCode
     */
    @OnOpen
    public void onOpen(@PathParam("userCode") String userCode, @PathParam("cpuCode") String cpuCode, Session session) {
        LOGGER.info("打开websocket连接..." + userCode);
        this.session = session;

        // TODO: 2018/3/3 这块以后要改成redis缓存
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        Map<String, Session> shopMap = webSocketSet.get(userCode);
        if (shopMap == null) {
            Map<String, Session> map = new ConcurrentHashMap<>();
            webSocketSet.put(userCode, map);
            shopMap = webSocketSet.get(userCode);
        }
        shopMap.put(cpuCode, session);

        List<TcsbShopManageEntity> shopManageList = websocketController.systemService.findHql("from TcsbShopManageEntity where shopId=? and cpuCode=?", userCode, cpuCode);
        if (shopManageList.isEmpty()) {
            TcsbShopManageEntity shopManage = new TcsbShopManageEntity();
            shopManage.setCpuCode(cpuCode);
            shopManage.setShopId(userCode);
            shopManage.setProsceniumStatus("0");
            shopManage.setCreateTime(new Date());
            websocketController.systemService.saveOrUpdate(shopManage);
        }
        Deque<String> deque = msgList.get(userCode);
        if(deque!=null){

            for(String s:deque){
                websocketController.onMessage(s);
                deque.pop();
            }
        }
//        for(int i=0;i<100;i++){
//            try {
//                session.getBasicRemote().sendText("{\"shopId\":\"8a9ad8035c0c5381015c0c7461db0023\",\"businessCode\":\"order\",\"des\":\"下单通知\",\"orderId\":\"\"}");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void createAppletSocketClient() {
        if (AppletSocketController.client == null) {
            try {
                //client = new WebSocketClient(new URI("ws://ceshi.diandanme.xyz/shop/websocket.ws/123"), new Draft_17()) {
                //WebSocketClient client = new WebSocketClient(new URI("ws://192.168.0.164:9090/ddmShopV2/websocket.ws/123"),new Draft_17()) {
                AppletSocketController.client = new WebSocketClient(new URI("ws://"+ResourceUtil.getConfigByName("websocket.adress")+"/websocket.ws/123/123"), new Draft_17()) {
                    @Override
                    public void onOpen(ServerHandshake arg0) {
                        System.out.println("打开链接--创建");
                    }

                    @Override
                    public void onMessage(String arg0) {
                        System.out.println("收到消息" + arg0);
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
                            System.out.println(new String(bytes.array(), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }


                };
                AppletSocketController.client.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 收到客户端消息时触发，并发送给特定用户
     *
     * @param textJson
     * @return
     */
    @OnMessage
    public String onMessage(String textJson) {
        String userCode = "";
        //业务模块
        String businessCode = "";
        String shopId = "";
        String des = "";
        String orderId = "";
        //信息是复杂对象
        String message = "";
        /*String content ="";
        String from = "";
        String to = "";
        String fromName = "";
        String toName = "";*/
        try {
            if (StringUtils.isNotEmpty(textJson)) {

                /* '{"shopId":"'+sendName+'","businessCode":"order","des":"下单通知","orderId":""}'
                 '{"shopId":"'+sendName+'","businessCode":"preorder","des":"预约通知","orderId":""}'*/
                //解析字符串
                JSONObject object = JSONObject.fromObject(textJson);
                shopId = object.getString("shopId");
                des = object.getString("des");
                businessCode = object.getString("businessCode");
                orderId = object.getString("orderId");
               /* userCode = object.getString("sendName");
                businessCode = object.getString("businessCode");
                if (businessCode.equals(BusinessEnum.BUSINESSORDER.getCode())) {
					System.out.println(1);
				}*/
                //message = object.getString("data");
                //解析字符串
                /*JSONObject data = JSONObject.fromObject(message);
                content = data.getString("msg");
                from = data.getString("from");
                to = data.getString("to");
                fromName = data.getString("fromName");
                toName = data.getString("toName");*/
                //MessageDTO messageDTO = JSONHelper.fromJsonToObject(content, MessageDTO.class);
                //Session session = webSocketSet.get(userCode);

                Map<String, Session> shopMap = webSocketSet.get(shopId);
                Session session = null;

                List<Session> sessions = null;
                //订单消息
                if (businessCode.toString().equals("0")) {
                    String orderParentId = object.getString("businessId");
                    TcsbOrderParentEntity orderParentEntity = websocketController.systemService.get(TcsbOrderParentEntity.class, orderParentId);
                    sessions = getSessionsByDeskId(orderParentEntity.getDeskId(), shopId);

                } else if (businessCode.toString().equals("2")) {
                    //呼叫服务
                    String businessId = object.getString("businessId");
                    TcsbCallServiceEntity callServiceEntity = websocketController.systemService.get(TcsbCallServiceEntity.class, businessId);
                    sessions = getSessionsByDeskId(callServiceEntity.getDeskId(), shopId);
                } else {
                    //其他消息发给前台
                    sessions = getProsceniumSessions(shopId);
                }
                //如果始终找不到session，缓存起来，登录的时候发送，然后清除
                this.sendTextInSessions(sessions, textJson,shopId);
//                if (null != session) {
//                    session.getBasicRemote().sendText(textJson);
//                }
                Date d2 = new Date();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return "已经给" + userCode + "发送消息啦！message：" + message;
    }

    public void sendTextInSessions(List<Session> sessions, String textJson, String shopId) throws IOException {
        if (sessions.isEmpty()) {
            //暂时先用map缓存
            Deque<String> linkedList = msgList.get(shopId);
            if (linkedList == null) {
                linkedList = new ArrayDeque<>() ;
                msgList.put(shopId, linkedList);
            }
            linkedList.add(textJson);
        } else {
            for (Session s : sessions) {
                s.getBasicRemote().sendText(textJson);
            }
        }
    }

    public List<Session> getProsceniumSessions(String shopId) {
        List<Session> sessionList = new ArrayList<>();
        List<TcsbShopManageEntity> shopManage = websocketController.systemService.findByProperty(TcsbShopManageEntity.class, "shopId", shopId);
        if (!shopManage.isEmpty()) {
            for (TcsbShopManageEntity s : shopManage) {
                sessionList.add(webSocketSet.get(shopId).get(s.getCpuCode()));
            }
            return sessionList;
        } else {
            for (Map.Entry<String, Session> entry : webSocketSet.get(shopId).entrySet()) {
                sessionList.add(entry.getValue());
            }
            return sessionList;
        }
    }

    public List<Session> getSessionsByDeskId(String deskId, String shopId) {
        List<Session> sessionList = new ArrayList<>();
        List<TcsbShopDeskManageEntity> list = websocketController.systemService.findByProperty(TcsbShopDeskManageEntity.class, "deskId", deskId);
        //关联桌位的pc端为空，得到所有已经登录的店铺session
        if (list.isEmpty()) {
            for (Map.Entry<String, Session> entry : webSocketSet.get(shopId).entrySet()) {
                sessionList.add(entry.getValue());
            }
        } else {
            for (TcsbShopDeskManageEntity deskManage : list) {
                //pc端店铺已登录
                if (webSocketSet.get(shopId) != null) {
                    //找不到对应的桌位
                    if (webSocketSet.get(shopId).get(deskManage.getCpuCode()) == null) {
                        //找前台，没前台，找所有已登录的session
//                        for (Map.Entry<String, Session> entry : webSocketSet.get(shopId).entrySet()) {
//                            sessionList.add(entry.getValue());
//                        }
                    } else {
                        //且能找到对应的桌位
                        sessionList.add(webSocketSet.get(shopId).get(deskManage.getCpuCode()));
                    }

                }
            }
        }
        return sessionList;
    }

    /**
     * 异常时触发
     */
    @OnError
    public void onError(Throwable throwable) {
        LOGGER.info("Websocket连接出现异常:");
        LOGGER.info(throwable.getMessage(), throwable);
    }

    /**
     * 关闭连接时触发
     */
    @OnClose
    public void onClose(@PathParam("userCode") String userCode, @PathParam("cpuCode") String cpuCode) {
        LOGGER.info("Websocket 关闭连接...");

        Map<String, Session> shopMap = webSocketSet.get(userCode);
        if (shopMap != null) {
            shopMap.remove(cpuCode);
        }
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    public void sendMessage(String message)
            throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount += 1;
    }

    public static synchronized void subOnlineCount() {
        onlineCount -= 1;
    }
}
