package com.weixin.core.controller;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.entity.common.JsapiTicket;
import com.weixin.core.util.ConfigUtil;
import com.weixin.core.util.PayCommonUtil;
import com.weixin.core.util.SignUtil;
import com.weixin.core.util.WeixinUtil;

@Controller
@RequestMapping("/wechatJsApi")
public class WechatJsApiController {
	@Autowired
	private SystemService systemService;
    
  /*  public AjaxJsonApi getConfigInfo(String url) {
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
    	try {
    		AccessToken accessToken = WeixinUtil.getAccessToken(systemService, ConfigUtil.APPID, ConfigUtil.APP_SECRECT);
        	JsapiTicket ticket = WeixinUtil.getTicket(systemService, accessToken.getToken());
        	System.out.println("ticket::::::;" + JSON.toJSONString(ticket));
        	if(ticket != null){
        		long timestamp = new Date().getTime();
        		String noncestr = PayCommonUtil.CreateNoncestr();
        		StringBuilder sb = new StringBuilder("jsapi_ticket=");
        		sb.append(ticket.getTicket()).append("&noncestr=").append(noncestr)
        		.append("&timestamp=").append(timestamp).append("&url=").append(url);
        		MessageDigest md = MessageDigest.getInstance("SHA-1");
    			// 对接后的字符串进行sha1加密
    			byte[] digest = md.digest(sb.toString().getBytes());
    			String signature = SignUtil.byteToStr(digest).toLowerCase();
    	    	Map<String, String> map = new HashMap<String, String>();
    	    	map.put("jsapi_ticket", ticket.getTicket());
    	    	map.put("appId",  ConfigUtil.APPID);
    	    	map.put("timestamp", String.valueOf(timestamp));
    	    	map.put("nonceStr", noncestr);
    	    	map.put("signature", signature);
    	    	map.put("originalStr", sb.toString());
    	    	ajaxJsonApi.setMsg("成功");
    	    	ajaxJsonApi.setSuccess(true);
    	    	ajaxJsonApi.setObj(map);
    	    	return ajaxJsonApi; 
        	}
        	ajaxJsonApi.setMsg("失败");
        	ajaxJsonApi.setSuccess(false);
        	
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return ajaxJsonApi;
    }*/
    
	@RequestMapping(value="/getConfigInfo.json")
    @ResponseBody
    public JSONPObject getConfigInfo(String url, HttpServletRequest request) {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
    	try {
    		//AccessToken accessToken = WeixinUtil.getAccessToken(systemService, ConfigUtil.APPID, ConfigUtil.APP_SECRECT);
    		String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");//服务号的appid 
    		String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("APP_SECRECT");//服务号的appSecrect 
    		AccessToken accessToken = WeixinUtil.getAccessToken();
        	JsapiTicket ticket = WeixinUtil.getTicket( accessToken.getToken());
        	System.out.println("ticket::::::;" + JSON.toJSONString(ticket));
        	if(ticket != null){
        		long timestamp = new Date().getTime();
        		String noncestr = PayCommonUtil.CreateNoncestr();
        		StringBuilder sb = new StringBuilder("jsapi_ticket=");
        		sb.append(ticket.getTicket()).append("&noncestr=").append(noncestr)
        		.append("&timestamp=").append(timestamp).append("&url=").append(url);
        		MessageDigest md = MessageDigest.getInstance("SHA-1");
    			// 对接后的字符串进行sha1加密
    			byte[] digest = md.digest(sb.toString().getBytes());
    			String signature = SignUtil.byteToStr(digest).toLowerCase();
    	    	Map<String, String> map = new HashMap<String, String>();
    	    	map.put("jsapi_ticket", ticket.getTicket());
    	    	//map.put("appId",  ConfigUtil.APPID);
    	    	map.put("appId",  APPID);
    	    	map.put("timestamp", String.valueOf(timestamp));
    	    	map.put("nonceStr", noncestr);
    	    	map.put("signature", signature);
    	    	map.put("originalStr", sb.toString());
    	    	ajaxJsonApi.setMsg("成功");
    	    	ajaxJsonApi.setSuccess(true);
    	    	ajaxJsonApi.setObj(map);
    	    	return new JSONPObject(callbackFunName, ajaxJsonApi); 
        	}
        	ajaxJsonApi.setMsg("失败");
        	ajaxJsonApi.setSuccess(false);
        	
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return new JSONPObject(callbackFunName, ajaxJsonApi);
    }
    
    /*@NoLogin
    @RequestMapping(value="/getPhoto.json", method=RequestMethod.POST)
    @ResponseBody
    public Object getPhoto(String media_id) throws NoSuchAlgorithmException{
    	//http请求方式: GET,https调用
//        var url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
    	AccessToken token = AccessTokenJsapiTicketThread.accessToken;
    	String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + token.getAccess_token() + "&media_id=" + media_id;
    	HttpsURLConnection httpsUrl = null;
    	InputStream inputStream = null;
    	Date now = new Date();
    	String saveFileName = null;
    	try {
    		httpsUrl = HttpUtil.initHttpsConnection(url, "GET");
    		int responseCode = httpsUrl.getResponseCode();
    	    if (responseCode == 200) {
    	        // 从服务器返回一个输入流
    	        inputStream = httpsUrl.getInputStream();
    	        
    	        byte[] data = new byte[1024];
    	        int len = 0;
    	        FileOutputStream fileOutputStream = null;
    	        
    	        saveFileName = DateUtil.convertYMDH(now) + RandomStringUtils.random(6, true, true) + ".jpg";;

    	        // 绝对路径
    	        String path = imageRootPath + DateUtil.convertYMD(now) + "/" + saveFileName;
    	        
    	        File dir = new File(imageRootPath + DateUtil.convertYMD(now) + "/");
                if (!dir.exists()) {
                    FileUtils.forceMkdir(dir);
                }
                
    	        try {
    	        	fileOutputStream = new FileOutputStream(path);
    	        	while ((len = inputStream.read(data)) != -1) {
    	        		fileOutputStream.write(data, 0, len);
    	        	}
    	        	fileOutputStream.flush();
    	        } catch (IOException e) {
    	          e.printStackTrace();
    	        } finally {
    	        	 if (inputStream != null) {
	        	        try {
	        	          inputStream.close();
	        	        } catch (IOException e) {
	        	        }
	        	     }
	        	      if (fileOutputStream != null) {
	        	        try {
	        	          fileOutputStream.close();
	        	        } catch (IOException e) {
	        	        }
	        	    }
    	        }
    	    }
		} catch (IOException e) {
			e.printStackTrace();
		}
    	// 返回图片路径
    	return JsonConvertor.convertSuccessResult(DateUtil.convertYMD(now) + "/" + saveFileName);
    }*/
}
