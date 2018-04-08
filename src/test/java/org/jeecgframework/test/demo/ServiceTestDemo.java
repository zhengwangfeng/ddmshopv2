package org.jeecgframework.test.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.jeecgframework.core.junit.AbstractUnitTest;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.HttpClientUtil;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.util.WeixinUtil;
/**
 * Service 单元测试Demo
 * @author  许国杰
 */
public class ServiceTestDemo extends AbstractUnitTest{
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private SystemService systemService;
	
	@Test
	public void testCheckUserExits() {
		TSUser user = new TSUser();
		user.setUserName("admin");
		user.setPassword("c44b01947c9e6e3f");
		TSUser u = userService.checkUserExits(user);
		assert(u.getUserName().equals(user.getUserName()));
	}

	@Test
	public void testGetUserRole() {
		TSUser user = new TSUser();
		user.setId("8a8ab0b246dc81120146dc8181950052");
		String roles = userService.getUserRole(user);
		assert(roles.equals("admin,"));
	}
	
	private String url ="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
	private String charset = "utf-8";


	@Test
	public void token() {
		//String APPID = ResourceBundle.getBundle("sysConfig").getString("applet.APPID");//服务号的appid 
		//String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("applet.APPSECRET");//服务号的appSecrect 
		//AccessToken accessToken = WeixinUtil.getAccessToken(systemService, APPID, APP_SECRECT);
		String token = "Lv0oj8gpdFa3B6-wx14oK5KOaPgec3RhptJS34kRecZlkAZYCczQdPGAFnGJwcjKfIxSwl7IuFt_VDMNOhZ5MLw_HHMrOxTB2mXpc1D52SoZ-x6YM8ViDkA-1R1c9vtUWTZaAAAJGM";
		//System.out.println(accessToken.getToken());
		url = url.replace("ACCESS_TOKEN", token);
		HttpClientUtil httpClientUtil  = new HttpClientUtil();
		String httpOrgCreateTest = url + "httpOrg/create";
		Map<String,String> createMap = new HashMap<String,String>();
		createMap.put("scene","8a9ccf875c44396b015c445a24e2008b");
		createMap.put("page","pages/index/index?deskId=");
		//createMap.put("orgkey","****");
		//createMap.put("orgname","****");
		String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);
		System.out.println("result:"+httpOrgCreateTestRtn);
		
		
		
	}

}
