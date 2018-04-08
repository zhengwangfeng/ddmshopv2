package com.weixin.core.util;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.weixin.p3.oauth2.pojo.oauth2.WeixinOauth2Token;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.service.SystemService;

import com.weixin.appletaccesstoken.entity.WeixinAppletAccesstokenEntity;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.entity.common.JsapiTicket;
import com.weixin.weixinaccesstoken.entity.WeixinAccesstokenEntity;
import com.weixin.weixinjsapiticket.entity.WeixinJsapiTicketEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 公众平台通用接口工具类
 *
 * @author jimmy
 * @date 2017-03-20
 */
@Component
public class WeixinUtil {
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //客服接口地址
    public static String send_message_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    //通过access_token获取ticket（2小时更新）
    public final static String sign_ticket_create_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    //
    /**
     * 小程序通过API获取openid
     * param：APPID,APPSECRET,JSCODE
     */
    public final static String applet_jscode2session_url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=APPSECRET&js_code=JSCODE&grant_type=authorization_code";


    //创建小程序二维码(采用b接口生成菊花似小程序码)
    //String url ="https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN";//小程序二维码
    //String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN";
    //String url ="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
    public final static String applet_qrcode_create_url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";

    public static final String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");//服务号的appid
    public static final String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("APP_SECRECT");//服务号的appSecrect

    public static final String APPLET_APPID = ResourceBundle.getBundle("sysConfig").getString("applet.APPID");//小程序的appid
    public static final String APPLET_APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("applet.APPSECRET");//小程序的appSecrect

    @Autowired
    private SystemService systemService;

    private static WeixinUtil weixinUtil;


    @PostConstruct //@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行
    public void init() {
        weixinUtil = this;
        weixinUtil.systemService = this.systemService;
    }

    //    private static final ResourceBundle bundle  = ResourceBundle.getBundle("weixin");

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
            //jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            org.jeecgframework.core.util.LogUtil.info("Weixin server connection timed out.");
        } catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.info("https request error:{}" + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 获取access_token
     *
     * @return
     */
    public static AccessToken getAccessToken() {
        // 第三方用户唯一凭证
//        String appid = bundle.getString("appId");
//        // 第三方用户唯一凭证密钥
//        String appsecret = bundle.getString("appSecret");
        List<WeixinAccesstokenEntity> accessTockenList = null;
        try {
            accessTockenList = getRealAccessToken();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (!accessTockenList.isEmpty()) {
            WeixinAccesstokenEntity accessTocken = accessTockenList.get(0);
            if (new Date().getTime() - accessTocken.getCreateTime().getTime() > accessTocken.getExpiresIn() * 1000) {
                AccessToken accessToken = null;
                String requestUrl = access_token_url.replace("APPID", WeixinUtil.APPID).replace("APPSECRET", WeixinUtil.APP_SECRECT);
                JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
                // 如果请求成功
                if (null != jsonObject) {
                    try {
                        accessToken = new AccessToken();
                        accessToken.setToken(jsonObject.getString("access_token"));
                        accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                        //凭证过期更新凭证
                        accessTocken.setExpiresIn(jsonObject.getInt("expires_in"));
                        accessTocken.setAccessToken(jsonObject.getString("access_token"));
                        updateAccessToken(accessTocken);
                    } catch (Exception e) {
                        // 获取token失败
                        String wrongMessage = "获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
                        org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                    }
                }
                return accessToken;
            } else {
                AccessToken accessToken = new AccessToken();
                accessToken.setToken(accessTocken.getAccessToken());
                accessToken.setExpiresIn(accessTocken.getExpiresIn());
                return accessToken;
            }
        } else {
            AccessToken accessToken = null;
            String requestUrl = access_token_url.replace("APPID", WeixinUtil.APPID).replace("APPSECRET", WeixinUtil.APP_SECRECT);
            JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInt("expires_in"));

                    WeixinAccesstokenEntity atyw = new WeixinAccesstokenEntity();
                    atyw.setExpiresIn(jsonObject.getInt("expires_in"));
                    atyw.setAccessToken(jsonObject.getString("access_token"));
                    atyw.setCreateTime(new Date());
                    saveAccessToken(atyw);
                } catch (Exception e) {
                    // 获取token失败
                    String wrongMessage = "获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
                    org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                }
            }
            return accessToken;
        }
    }


    /**
     * 获取小程序access_token
     *
     * @return
     */
    public static AccessToken getAppletAccessToken() {
        // 第三方用户唯一凭证
//        String appid = bundle.getString("appId");
//        // 第三方用户唯一凭证密钥
//        String appsecret = bundle.getString("appSecret");
        List<WeixinAppletAccesstokenEntity> accessTockenList = null;
        try {
            accessTockenList = getRealAppletAccessToken();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (!accessTockenList.isEmpty()) {
            WeixinAppletAccesstokenEntity accessTocken = accessTockenList.get(0);
            Calendar calSrc = null;
            try {
                calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd hh:mm:ss");
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Calendar calDes = null;
            try {
                calDes = DateUtils.parseCalendar(accessTocken.getCreateTime().toString(), "yyyy-MM-dd hh:mm:ss");
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            int aa = DateUtils.dateDiff('s', calSrc, calDes);
            if (aa > 7200) {
                AccessToken accessToken = null;
                String requestUrl = access_token_url.replace("APPID", WeixinUtil.APPLET_APPID).replace("APPSECRET", WeixinUtil.APPLET_APP_SECRECT);
                JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
                // 如果请求成功
                if (null != jsonObject) {
                    try {
                        accessToken = new AccessToken();
                        accessToken.setToken(jsonObject.getString("access_token"));
                        accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                        //凭证过期更新凭证
                        accessTocken.setExpiresIn(jsonObject.getInt("expires_in"));
                        accessTocken.setAccessToken(jsonObject.getString("access_token"));
                        updateAppletAccessToken(accessTocken);
                    } catch (Exception e) {
                        // 获取token失败
                        String wrongMessage = "获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
                        org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                    }
                }
                return accessToken;
            } else {
                AccessToken accessToken = new AccessToken();
                accessToken.setToken(accessTocken.getAccessToken());
                accessToken.setExpiresIn(accessTocken.getExpiresIn());
                return accessToken;
            }
        } else {
            AccessToken accessToken = null;
            String requestUrl = access_token_url.replace("APPID", WeixinUtil.APPLET_APPID).replace("APPSECRET", WeixinUtil.APPLET_APP_SECRECT);
            JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInt("expires_in"));

                    WeixinAppletAccesstokenEntity atyw = new WeixinAppletAccesstokenEntity();
                    atyw.setExpiresIn(jsonObject.getInt("expires_in"));
                    atyw.setAccessToken(jsonObject.getString("access_token"));
                    atyw.setCreateTime(new Date());
                    saveAppletAccessToken(atyw);
                } catch (Exception e) {
                    // 获取token失败
                    String wrongMessage = "获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
                    org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                }
            }
            return accessToken;
        }
    }

    /**
     * 获取ticket
     *
     * @param accessTocken
     * @return JsapiTicket
     * @throws ParseException
     * @throws IOException
     */
    public static JsapiTicket getTicket(String accessTocken) throws ParseException, IOException {
        List<WeixinJsapiTicketEntity> ticketEntityList = getRealJsapiTticket();
        if (!ticketEntityList.isEmpty()) {
            WeixinJsapiTicketEntity ticketEntity = ticketEntityList.get(0);
            if (new Date().getTime() - ticketEntity.getCreateTime().getTime() > ticketEntity.getExpiresIn() * 1000) {
                JsapiTicket jsapiTicket = null;
                String requestUrl = sign_ticket_create_url.replace("ACCESS_TOKEN", accessTocken);
                JSONObject jsonObject = httpRequest(requestUrl, "POST", null);
                // 如果请求成功
                if (null != jsonObject) {
                    try {
                        jsapiTicket = new JsapiTicket();
                        jsapiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
                        jsapiTicket.setTicket(jsonObject.getString("ticket"));
                        //凭证过期更新凭证
                        ticketEntity.setExpiresIn(jsonObject.getInt("expires_in"));
                        ticketEntity.setJsapiTicket(jsonObject.getString("ticket"));
                        updateJsapiTicket(ticketEntity);
                    } catch (Exception e) {
                        // 获取token失败
                        String wrongMessage = "获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
                        org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                    }
                }
                return jsapiTicket;
            } else {
                JsapiTicket jsapiTicket = new JsapiTicket();
                jsapiTicket.setTicket(ticketEntity.getJsapiTicket());
                jsapiTicket.setExpiresIn(ticketEntity.getExpiresIn());
                return jsapiTicket;
            }
        } else {
            JsapiTicket jsapiTicket = null;
            String requestUrl = sign_ticket_create_url.replace("ACCESS_TOKEN", accessTocken);
            JSONObject jsonObject = httpRequest(requestUrl, "POST", null);
            WeixinJsapiTicketEntity newticket = null;
            // 如果请求成功
            if (null != jsonObject) {
                newticket = new WeixinJsapiTicketEntity();
                try {
                    jsapiTicket = new JsapiTicket();
                    jsapiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
                    jsapiTicket.setTicket(jsonObject.getString("ticket"));
                    //持久化
                    newticket.setExpiresIn(jsonObject.getInt("expires_in"));
                    newticket.setJsapiTicket(jsonObject.getString("ticket"));
                    newticket.setCreateTime(new Date());
                    saveJsapiTicket(newticket);
                } catch (Exception e) {
                    // 获取token失败
                    String wrongMessage = "获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
                    org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                }
            }
            return jsapiTicket;
        }

       /* JSONObject jsonObject = new JSONObject();
        JSONObject postjson=new JSONObject();
        String ticket =null;
        String url = sign_ticket_create_url.replace("ACCESS_TOKEN",accessToken);
        System.out.print("url="+url);
        String ticketurl ="";
        try {
            jsonObject = WeixinUtil.httpRequest(url, "POST",postjson.toString());
            ticket= jsonObject.getString("ticket");
            System.out.println("ticket:"+ticket);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;*/
    }

    ;

    /**
     * 从数据库中读取凭证
     *
     * @return
     */
    public static List<WeixinAccesstokenEntity> getRealAccessToken() {
        List<WeixinAccesstokenEntity> accessTockenList = weixinUtil.systemService.findByQueryString("from WeixinAccesstokenEntity");
        return accessTockenList;
    }

    /**
     * 从数据库中读取凭证
     *
     * @return
     */
    public static List<WeixinAppletAccesstokenEntity> getRealAppletAccessToken() {
        List<WeixinAppletAccesstokenEntity> accessTockenList = weixinUtil.systemService.findByQueryString("from WeixinAppletAccesstokenEntity");
        return accessTockenList;
    }


    /**
     * 保存凭证
     *
     * @return
     */
    public static void saveAccessToken(WeixinAccesstokenEntity accessTocken) {
        weixinUtil.systemService.save(accessTocken);
    }

    /**
     * 保存凭证
     *
     * @return
     */
    public static void saveAppletAccessToken(WeixinAppletAccesstokenEntity accessTocken) {
        weixinUtil.systemService.save(accessTocken);
    }

    /**
     * 更新凭证
     *
     * @return
     */
    public static void updateAccessToken(WeixinAccesstokenEntity accessTocken) {
        accessTocken.setCreateTime(new Date());
        //String sql = "update weixin_applet_accesstoken set access_token='"+accessTocken.getAccessToken()+"',expires_in="+accessTocken.getExpiresIn()+",create_time=now() where id='"+accessTocken.getId()+"'";
        //systemService.updateBySqlString(sql);
        weixinUtil.systemService.saveOrUpdate(accessTocken);
    }

    /**
     * 更新凭证
     *
     * @return
     */
    public static void updateAppletAccessToken(WeixinAppletAccesstokenEntity accessTocken) {
        accessTocken.setCreateTime(new Date());
        //String sql = "update weixin_applet_accesstoken set access_token='"+accessTocken.getAccessToken()+"',expires_in="+accessTocken.getExpiresIn()+",create_time=now() where id='"+accessTocken.getId()+"'";
        //systemService.updateBySqlString(sql);
        weixinUtil.systemService.saveOrUpdate(accessTocken);
    }

    /**
     * 从数据库中读取临时票据
     *
     * @return
     */
    public static List<WeixinJsapiTicketEntity> getRealJsapiTticket() {
        List<WeixinJsapiTicketEntity> ssapiTicketList = weixinUtil.systemService.findByQueryString("from WeixinJsapiTicketEntity");
        return ssapiTicketList;
    }

    /**
     * 保存票据
     *
     * @return
     */
    public static void saveJsapiTicket(WeixinJsapiTicketEntity ticket) {
        weixinUtil.systemService.save(ticket);
    }

    /**
     * 更新票据
     *
     * @return
     */
    public static void updateJsapiTicket(WeixinJsapiTicketEntity ticket) {
        String sql = "update weixin_jsapi_ticket set jsapi_ticket='" + ticket.getJsapiTicket() + "',expires_in=" + ticket.getExpiresIn() + ",create_time=now() where id='" + ticket.getId() + "'";
        weixinUtil.systemService.updateBySqlString(sql);
    }

    /**
     * 编码
     *
     * @param bstr
     * @return String
     */
    public static String encode(byte[] bstr) {
        return new sun.misc.BASE64Encoder().encode(bstr);
    }

    /**
     * 解码
     *
     * @param str
     * @return string
     */
    public static byte[] decode(String str) {

        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;

    }

    public static String httpPostWithJSON(String accessToken, String deskId, String page, String APPLETtoPath, String filePostfix)
            throws Exception {
        String requestUrl = applet_qrcode_create_url.replace("ACCESS_TOKEN", accessToken);
        Map<String, Object> map = new HashMap<String, Object>();
        //map.put("path", "pages/index/index");
        map.put("page", page);//你二维码中跳向的地址
        map.put("scene", deskId);//参数
        JSONObject json = JSONObject.fromObject(map);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity se = new StringEntity(json.toString());
        se.setContentType("application/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "UTF-8"));
        httpPost.setEntity(se);
        // httpClient.execute(httpPost);
        HttpResponse response = httpClient.execute(httpPost);
        String filename = DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyyMMdd")) + new Date().hashCode();
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStream instreams = resEntity.getContent();
                String dir = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/") + APPLETtoPath;
                File saveFile = new File(dir + "/" + filename + "." + filePostfix);
                // 判断这个文件（saveFile）是否存在
                if (!saveFile.getParentFile().exists()) {
                    // 如果不存在就创建这个文件夹
                    saveFile.getParentFile().mkdirs();
                }
                saveToImgByInputStream(instreams, dir + "/", filename + "." + filePostfix);
            }
        }
        httpPost.abort();
        System.out.println(response);
        return APPLETtoPath + "/" + filename + "." + filePostfix;
    }

    /**
     * 带有文字或LOGO的小程序码
     * @param accessToken
     * @param deskId
     * @param page
     * @param APPLETtoPath
     * @param filePostfix
     * @return
     * @throws Exception
     */
    public static String httpPostWithJSON2(String accessToken, String deskId, String page, String APPLETtoPath, String filePostfix)
            throws Exception {
        String requestUrl = applet_qrcode_create_url.replace("ACCESS_TOKEN", accessToken);
        Map<String, Object> map = new HashMap<String, Object>();
        //map.put("path", "pages/index/index");
        map.put("page", page);//你二维码中跳向的地址
        map.put("scene", deskId);//参数
        JSONObject json = JSONObject.fromObject(map);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity se = new StringEntity(json.toString());
        se.setContentType("application/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "UTF-8"));
        httpPost.setEntity(se);
        // httpClient.execute(httpPost);
        HttpResponse response = httpClient.execute(httpPost);
        String filename = DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyyMMdd")) + new Date().hashCode();
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStream instreams = resEntity.getContent();
                String dir = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/") + APPLETtoPath;
                File saveFile = new File(dir + "/" + filename + "." + filePostfix);
                // 判断这个文件（saveFile）是否存在
                if (!saveFile.getParentFile().exists()) {
                    // 如果不存在就创建这个文件夹
                    saveFile.getParentFile().mkdirs();
                }
                //带文字的小程序码
                //saveToImgByInputStream(instreams, dir + "/", filename + "." + filePostfix);
                saveToImgAndFontByInputStream(instreams, dir + "/", filename + "." + filePostfix, "请长按识别", "");
            }
        }
        httpPost.abort();
        System.out.println(response);
        return APPLETtoPath + "/" + filename + "." + filePostfix;
    }
    
    /**
     * 带有文字背景图的小程序码
     * @param fontPath 文字背景图的默认路径
     * @param accessToken
     * @param deskId
     * @param page
     * @param APPLETtoPath
     * @param filePostfix
     * @return
     * @throws Exception
     */
    public static String httpPostWithJSON3(String accessToken, String deskId, String page, String APPLETtoPath, String fontPath, String filePostfix)
            throws Exception {
        String requestUrl = applet_qrcode_create_url.replace("ACCESS_TOKEN", accessToken);
        Map<String, Object> map = new HashMap<String, Object>();
        //map.put("path", "pages/index/index");
        map.put("page", page);//你二维码中跳向的地址
        map.put("scene", deskId);//参数
        JSONObject json = JSONObject.fromObject(map);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity se = new StringEntity(json.toString());
        se.setContentType("application/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "UTF-8"));
        httpPost.setEntity(se);
        // httpClient.execute(httpPost);
        HttpResponse response = httpClient.execute(httpPost);
        String filename = DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyyMMdd")) + new Date().hashCode();
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStream instreams = resEntity.getContent();
                String dir = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/") + APPLETtoPath;
                File saveFile = new File(dir + "/" + filename + "." + filePostfix);
                // 判断这个文件（saveFile）是否存在
                if (!saveFile.getParentFile().exists()) {
                    // 如果不存在就创建这个文件夹
                    saveFile.getParentFile().mkdirs();
                }
                //带文字的小程序码
                //saveToImgByInputStream(instreams, dir + "/", filename + "." + filePostfix);
                //saveToImgAndFontByInputStream(instreams, dir + "/", filename + "." + filePostfix, "请长按识别", "");
                saveToImgAndFont2ByInputStream(instreams, dir + "/", fontPath, filename + "." + filePostfix);
            }
        }
        httpPost.abort();
        System.out.println(response);
        return APPLETtoPath + "/" + filename + "." + filePostfix;
    }
    

    /* @param instreams 二进制流
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return
     *      1：保存正常
     *      0：保存失败
     */
    public static int saveToImgByInputStream(InputStream instreams, String imgPath, String imgName) {

        int stateInt = 1;
        if (instreams != null) {
            try {
                File file = new File(imgPath + imgName);//可以是任何图片格式.jpg,.png等
                FileOutputStream fos = new FileOutputStream(file);

                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
                try {
                    instreams.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return stateInt;
    }
      
    /**
     * 在二维码图片上放文字或LOGO
     * @param instreams 二进制流
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @param font 二维码上的文字
     * @param headImg 二维码上的LOGO
     * @return
     *      1：保存正常
     *      0：保存失败
     */
    public static int saveToImgAndFontByInputStream(InputStream instreams, String imgPath, String imgName, String font,String headImg){  
    	int stateInt = 1;
    	try {  
            //1.jpg是你的 主图片的路径  
           // InputStream is = new FileInputStream("d:/1.png");  
              
              
            //通过JPEG图象流创建JPEG数据流解码器  
            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(instreams);  
            //解码当前JPEG数据流，返回BufferedImage对象  
            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();  
            //得到画笔对象  
            Graphics g = buffImg.getGraphics();  
              
            //创建你要附加的图象。  
            //小图片的路径  
            ImageIcon imgIcon = new ImageIcon(headImg);   
              
            //得到Image对象。  
            Image img = imgIcon.getImage();  
              
            //将小图片绘到大图片上。  
            //5,300 .表示你的小图片在大图片上的位置。  
            g.drawImage(img,400,15,null);  
              
            //设置颜色。  
            g.setColor(Color.BLACK);  
              
              
            //最后一个参数用来设置字体的大小  
            Font f = new Font("宋体",Font.PLAIN,35);  
            Color mycolor = Color.red;//new Color(0, 0, 255);  
            g.setColor(mycolor);  
            g.setFont(f);  
              
            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。  
            g.drawString(font,140,40);  
              
            g.dispose();  
              
              
            //OutputStream os;  
          
            //os = new FileOutputStream("d:/union.jpg");  
            //String shareFileName = "D:/" + System.currentTimeMillis() + ".jpg";  
            //os = new FileOutputStream(shareFileName);
            File file = new File(imgPath + imgName);//可以是任何图片格式.jpg,.png等
            FileOutputStream os = new FileOutputStream(file);
             //创键编码器，用于编码内存中的图象数据。            
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);  
            en.encode(buffImg);           
              
            instreams.close();  
            os.close();  
        } catch (Exception e) {  
        	 stateInt = 0;
             e.printStackTrace(); 
        }  finally {
            try {
                instreams.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
         return stateInt; 
    } 
    /**
     * 在二维码图片上放文字
     * 加上带文字的背景图
     * @param instreams 二进制流
     * @param imgPath 图片的保存路径
     * @param fontPath 文字背景图的默认路径
     * @param imgName 图片的名称
     * @return
     *      1：保存正常
     *      0：保存失败
     */
    public static int saveToImgAndFont2ByInputStream(InputStream instreams, String imgPath, String fontPath, String imgName){  
    	int stateInt = 1;
    	 try {  
             //BufferedImage image= ImageIO.read(new File("d:/1.png"));//二维码
    		 //读到二维码图片信息
    		 BufferedImage image = ImageIO.read(instreams);//读二维码图片流
    		 String dir = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
             BufferedImage bg= ImageIO.read(new File(dir+fontPath));//背景图
             Graphics2D g=bg.createGraphics();
             //int width=image.getWidth(null) > bg.getWidth() * 5/10? (bg.getWidth() * 5/10) : image.getWidth(null);
             //int height=image.getHeight(null) > bg.getHeight() *5 /10? (bg.getHeight() * 5/10) : image.getWidth(null);
             //g.drawImage(image,(bg.getWidth()- width)/2,(bg.getHeight()-height)/2,width,height,null);
             //g.drawImage(image,(bg.getWidth()- image.getWidth()),(bg.getHeight()-image.getHeight()),(bg.getWidth()),(bg.getHeight()),null);
             g.drawImage(image,40,40,image.getWidth()-40,image.getHeight()-40,null);
             g.dispose();
             bg.flush();
             image.flush();
             File file = new File(imgPath + imgName);//可以是任何图片格式.jpg,.png等
             FileOutputStream os = new FileOutputStream(file); 
             ImageIO.write(bg,"png", os);
             instreams.close();  
             os.close();  
             
         } catch (Exception e) {  
             // TODO Auto-generated catch block  
        	 stateInt = 0;
             e.printStackTrace();  
         } 
         return stateInt; 
    } 
  //未使用此方法为上一个方法另一种写法
    public static void exportImg1(){  
        int width = 100;     
        int height = 100;     
        String s = "你好";     
             
        File file = new File("d:/image.jpg");     
             
        Font font = new Font("Serif", Font.BOLD, 10);     
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);     
        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
        g2.setBackground(Color.WHITE);     
        g2.clearRect(0, 0, width, height);     
        g2.setPaint(Color.RED);     
             
        FontRenderContext context = g2.getFontRenderContext();     
        Rectangle2D bounds = font.getStringBounds(s, context);     
        double x = (width - bounds.getWidth()) / 2;     
        double y = (height - bounds.getHeight()) / 2;     
        double ascent = -bounds.getY();     
        double baseY = y + ascent;     
             
        g2.drawString(s, (int)x, (int)baseY);     
             
        try {  
            ImageIO.write(bi, "jpg", file);  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
    }  
    
    /**
     * 通过code换取网页授权access_token
     *
     * @param code
     * @return
     */
    public static WeixinOauth2Token getWebAccessTokenByCode(String code) {
        WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(WeixinUtil.APPID,WeixinUtil.APP_SECRECT,code);
        return weixinOauth2Token;
    }

    /**
     * 通过refresh_token进行刷新网页授权access_token
     *
     * @param refreshToken
     * @return
     */
    public static WeixinOauth2Token refreshWebAccessToken(String refreshToken) {
        WeixinOauth2Token weixinOauth2Token = AdvancedUtil.refreshOauth2AccessToken(WeixinUtil.APPID,refreshToken);
        return weixinOauth2Token;
    }
    
    
    
    public static JsapiTicket getTicket2(String accessTocken) throws ParseException, IOException {

		WeixinJsapiTicketEntity ticketEntity = weixinUtil.systemService.get(WeixinJsapiTicketEntity.class, "8a9ad8035bf1a41b015bf1a7de5d0001");
		if (new Date().getTime() - ticketEntity.getCreateTime().getTime() > ticketEntity.getExpiresIn() * 1000) {
			JsapiTicket jsapiTicket = null;
            String requestUrl = sign_ticket_create_url.replace("ACCESS_TOKEN", accessTocken);
            JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "POST", null);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    jsapiTicket = new JsapiTicket();
                    jsapiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
                    jsapiTicket.setTicket(jsonObject.getString("ticket"));
                    //凭证过期更新凭证
                } catch (Exception e) {
                    // 获取token失败
                    String wrongMessage = "获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
                    org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                }
            }
            ticketEntity.setExpiresIn(jsonObject.getInt("expires_in"));
            ticketEntity.setJsapiTicket(jsonObject.getString("ticket"));
            ticketEntity.setCreateTime(new Date());
            weixinUtil.systemService.saveOrUpdate(ticketEntity);
            return jsapiTicket;
		}else{
			JsapiTicket jsapiTicket = new JsapiTicket();
            jsapiTicket.setTicket(ticketEntity.getJsapiTicket());
            jsapiTicket.setExpiresIn(ticketEntity.getExpiresIn());
            return jsapiTicket;
			
		}   
            }
    
    
    
}