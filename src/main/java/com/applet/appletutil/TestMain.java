/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月25日 下午4:36:28
 */
package com.applet.appletutil;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月25日 下午4:36:28
 */
import java.util.HashMap;
import java.util.Map;
//对接口进行测试
public class TestMain {
	private String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx8610c8dbd9548114&secret=4a50c3eedae5e99f05375f335455b0f4&js_code=011J6lcw0eVu6h1uJWcw0Knlcw0J6lcU&grant_type=authorization_code";
	private String charset = "utf-8";
	private HttpClientUtil httpClientUtil = null;
	
	public TestMain(){
		httpClientUtil = new HttpClientUtil();
	}
	
	public void test(){
		String httpOrgCreateTest = url + "httpOrg/create";
		Map<String,String> createMap = new HashMap<String,String>();
		createMap.put("authuser","*****");
		createMap.put("authpass","*****");
		createMap.put("orgkey","****");
		createMap.put("orgname","****");
		String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);
		System.out.println("result:"+httpOrgCreateTestRtn);
	}
	
	public static void main(String[] args){
		TestMain main = new TestMain();
		main.test();
	}
}
