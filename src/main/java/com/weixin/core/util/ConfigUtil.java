package com.weixin.core.util;

import java.util.ResourceBundle;

/**
 * 微信支付配置文件 
 * @author jimmy
 *
 */
public class ConfigUtil { 
/** 
* 服务号相关信息 
*/ 
/*public final static String APPID = "wxfd33b1c6f460c400";//服务号的appid 
public final static String APP_SECRECT = "5131e89833c5e2d0e8b33915a22f9345";//服务号的appSecrect 
public final static String TOKEN = "tcsbShop";//服务号的配置token 
public final static String MCH_ID = "1433028902";//开通微信支付分配的商户号 
public final static String API_KEY = "diandanmexiamenguotongbaoyouxian";//商户API密钥 自行去商户平台设置 
public final static String SIGN_TYPE = "MD5";//签名加密方式 
public final static String NOTIFY_URL = "http://1m6309f047.iok.la/shop/rest/wechatPayController/payNotifyUrl"; //用于告知微信服务器 调用成功
*/
	public final static String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");//服务号的appid 
	public final static String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("APP_SECRECT");//服务号的appSecrect 
	public final static String TOKEN = ResourceBundle.getBundle("sysConfig").getString("TOKEN");//服务号的配置token 
	public final static String MCH_ID = ResourceBundle.getBundle("sysConfig").getString("MCH_ID");//开通微信支付分配的商户号 
	public final static String API_KEY = ResourceBundle.getBundle("sysConfig").getString("API_KEY");//商户API密钥 自行去商户平台设置 
	public final static String SIGN_TYPE = "MD5";//签名加密方式 
	public final static String NOTIFY_URL = ResourceBundle.getBundle("sysConfig").getString("deskBaseUrl")+"/shop/rest/wechatPayController/payNotifyUrl"; //用于告知微信服务器 调用成功

	
	public final static String APPLET_APPID = ResourceBundle.getBundle("sysConfig").getString("applet.APPID");//服务号的appid 
	public final static String APPLET_APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("applet.APPSECRET");//服务号的appSecrect 
	public final static String APPLET_NOTIFY_URL = ResourceBundle.getBundle("sysConfig").getString("applet.BaseUrl")+"/shop/rest/appletPay/payNotify"; //用于告知微信服务器 调用成功
	public final static String reservationPayNotify_URL = ResourceBundle.getBundle("sysConfig").getString("applet.BaseUrl")+"/shop/rest/appletPay/reservationPayNotify"; //用于告知微信服务器 调用成功
	public final static String reservationDeltaNotify_URL = ResourceBundle.getBundle("sysConfig").getString("applet.BaseUrl")+"/shop/rest/appletPay/reservationDeltaNotify"; //用于告知微信服务器 调用成功
	public final static String deltaPayNotify_URL = ResourceBundle.getBundle("sysConfig").getString("applet.BaseUrl")+"/shop/rest/appletPay/deltaPayNotify";//用于告知微信服务器 调用成功
	public final static String APPLET_SHOPCAR_NOTIFY_URL= ResourceBundle.getBundle("sysConfig").getString("applet.BaseUrl")+"/shop/rest/appletPay/shopCarPayNotify";//用于告知微信服务器 调用成功
	public final static String APPLET_RESERVATIONTOTALMONEY_SHOPCAR_NOTIFY_URL= ResourceBundle.getBundle("sysConfig").getString("applet.BaseUrl")+"/shop/rest/appletPay/reservationTotalMoneyshopCarPayNotify";//用于告知微信服务器 调用成功
	public final static String APPLET_RESERVATIONDEPOSITMONEY_SHOPCAR_NOTIFY_URL= ResourceBundle.getBundle("sysConfig").getString("applet.BaseUrl")+"/shop/rest/appletPay/reservationDepositMoneyshopCarPayNotify";//用于告知微信服务器 调用成功
	//public final static String deltaPayNotify_URL = ResourceBundle.getBundle("sysConfig").getString("applet.baseUrlTest")+"/jeecg/rest/appletPay/deltaPayNotify";//用于告知微信服务器 调用成功
	/**退款证书路径**/
	public final static String certificatePath = ResourceBundle.getBundle("sysConfig").getString("applet.certificatePath");;
	
/**
 * 微信基础接口地址
 */
 //获取token接口(GET)
 public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
 //oauth2授权接口(GET)
 public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
 //刷新access_token接口（GET）
 public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
// 菜单创建接口（POST）
 public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
// 菜单查询（GET）
 public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
// 菜单删除（GET）
public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
/**
 * 微信支付接口地址
 */
//微信支付统一接口(POST)
public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//微信退款接口(POST)
public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
//订单查询接口(POST)
public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
//关闭订单接口(POST)
public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
//退款查询接口(POST)
public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
//对账单接口(POST)
public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
//短链接转换接口(POST)
public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
//接口调用上报接口(POST)
public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
//微信消息模板推送接口(POST)
public final static String MESSAGE_TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
} 