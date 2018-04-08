package com.weixin.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.online.util.FreemarkerHelper;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.weixin.core.entity.message.resp.Article;
import com.weixin.core.entity.message.resp.LinkMessageResp;
import com.weixin.core.entity.message.resp.NewsMessageResp;
import com.weixin.core.entity.message.resp.TextMessageResp;
import com.weixin.core.util.MessageUtil;
import com.weixin.message.dao.WeixinTextTemplateDao;
import com.weixin.util.DateUtils;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinaccount.service.WeixinAccountServiceI;
import com.weixin.weixinautoresponse.entity.WeixinAutoresponseEntity;
import com.weixin.weixinautoresponse.service.WeixinAutoresponseServiceI;
import com.weixin.weixinmenu.entity.WeixinMenuEntity;
import com.weixin.weixinnewstemplate.service.WeixinNewstemplateServiceI;
import com.weixin.weixinsubscribe.entity.WeixinSubscribeEntity;
import com.weixin.weixinsubscribe.service.WeixinSubscribeServiceI;
import com.weixin.weixintexttemplate.entity.WeixinTexttemplateEntity;
import com.weixin.weixintexttemplate.service.WeixinTexttemplateServiceI;

@Service("wechatService")
public class WechatService {
	@Autowired
	private WeixinTextTemplateDao weixinTextTemplateDao;
	@Autowired
	private WeixinAutoresponseServiceI weixinAutoresponseService;
	@Autowired
	private WeixinTexttemplateServiceI weixinTexttemplateService;
	@Autowired
	private WeixinNewstemplateServiceI weixinNewstemplateService;
	/*@Autowired
	private ReceiveTextServiceI receiveTextService;*/
	/*@Autowired
	private NewsItemServiceI newsItemService;*/
	@Autowired
	private SystemService systemService;
	@Autowired
	private WeixinSubscribeServiceI weixinSubscribeService;
	/*@Autowired
	private WeixinExpandconfigServiceI weixinExpandconfigService;*/
	@Autowired
	private WeixinAccountServiceI weixinAccountService;

	public String coreService(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			String msgId = requestMap.get("MsgId");
			//消息内容
			String content = requestMap.get("Content");
			LogUtil.info("------------微信客户端发送请求---------------------   |   fromUserName:"+fromUserName+"   |   ToUserName:"+toUserName+"   |   msgType:"+msgType+"   |   msgId:"+msgId+"   |   content:"+content);
			//根据微信ID,获取配置的全局的数据权限ID
			LogUtil.info("-toUserName--------"+toUserName);
			/*String sys_accountId = weixinAccountService.findByToUsername(toUserName).getId();*/
			List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
			String sys_accountId = weixinAccountEntities.get(0).getId();
			LogUtil.info("-sys_accountId--------"+sys_accountId);
			ResourceBundle bundler = ResourceBundle.getBundle("sysConfig");
			// 默认回复此文本消息
			TextMessageResp textMessage = new TextMessageResp();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setContent(getMainMenu());
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);
			//【微信触发类型】文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				LogUtil.info("------------微信客户端发送请求------------------【微信触发类型】文本消息---");
				/*respMessage = doTextResponse(content,toUserName,textMessage,bundler,
						sys_accountId,respMessage,fromUserName,request,msgId,msgType);*/
			}
			//【微信触发类型】图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			//【微信触发类型】地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			//【微信触发类型】链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			//【微信触发类型】音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			//【微信触发类型】事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				LogUtil.info("------------微信客户端发送请求------------------【微信触发类型】事件推送---");
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respMessage = doDingYueEventResponse(requestMap, textMessage, bundler, respMessage, toUserName, fromUserName, respContent, sys_accountId);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 事件类型：CLICK(自定义菜单点击事件)
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					respMessage = doMyMenuClickEvent(requestMap, textMessage, bundler, respMessage, toUserName, fromUserName, respContent, sys_accountId, request);
				}
				//事件类型：VIEW(自定义菜单点击事件)
				else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					LinkMessageResp linkMessage = new LinkMessageResp();
					linkMessage.setToUserName(fromUserName);
					linkMessage.setFromUserName(toUserName);
					linkMessage.setCreateTime(new Date().getTime());
					linkMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_LINK);
					linkMessage.setDescription("test");
					linkMessage.setTitle("test");
					linkMessage.setUrl(requestMap.get("EventKey"));
					respMessage = MessageUtil.linkMessageToXml(linkMessage);
					return respMessage;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}


	/**
	 * Q译通使用指南
	 * 
	 * @return
	 */
	public static String getTranslateUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("微译使用指南").append("\n\n");
		buffer.append("微译为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
		buffer.append("    中 -> 英").append("\n");
		buffer.append("    英 -> 中").append("\n");
		buffer.append("    日 -> 中").append("\n\n");
		buffer.append("使用示例：").append("\n");
		buffer.append("    翻译我是中国人").append("\n");
		buffer.append("    翻译dream").append("\n");
		buffer.append("    翻译さようなら").append("\n\n");
		buffer.append("回复“?”显示主菜单");
		return buffer.toString();
	}

	/**
	 * 遍历关键字管理中是否存在用户输入的关键字信息
	 * 
	 * @param content
	 * @return
	 */
	private WeixinAutoresponseEntity findKey(String content, String toUsername) {
		LogUtil.info("---------sys_accountId--------"+toUsername+"|");
		//获取全局的数据权限ID
		//String sys_accountId = weixinAccountService.findByToUsername(toUsername).getId();
		List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
		String sys_accountId = weixinAccountEntities.get(0).getId();
		LogUtil.info("---------sys_accountId--------"+sys_accountId);
		// 获取关键字管理的列表，匹配后返回信息
		List<WeixinAutoresponseEntity> autoResponses = weixinAutoresponseService.findByProperty(WeixinAutoresponseEntity.class, "accountid", sys_accountId);
		LogUtil.info("---------sys_accountId----关键字查询结果条数：----"+autoResponses!=null?autoResponses.size():0);
		for (WeixinAutoresponseEntity r : autoResponses) {
			// 如果包含关键字
			String kw = r.getKeyword();
			String[] allkw = kw.split(",");
			for (String k : allkw) {
				if (k.equals(content)) {
					LogUtil.info("---------sys_accountId----查询结果----"+r);
					return r;
				}
			}
		}
		return null;
	}

	/**
	 * 针对文本消息
	 * @param content
	 * @param toUserName
	 * @param textMessage
	 * @param bundler
	 * @param sys_accountId
	 * @param respMessage
	 * @param fromUserName
	 * @param request
	 * @throws Exception 
	 */
	/*String doTextResponse(String content,String toUserName,TextMessageResp textMessage,ResourceBundle bundler,
			String sys_accountId,String respMessage,String fromUserName,HttpServletRequest request,String msgId,String msgType) throws Exception{
		//=================================================================================================================
		// 保存接收到的信息
		ReceiveText receiveText = new ReceiveText();
		receiveText.setContent(content);
		Timestamp temp = Timestamp.valueOf(DateUtils
				.getDate("yyyy-MM-dd HH:mm:ss"));
		receiveText.setCreateTime(temp);
		receiveText.setFromUserName(fromUserName);
		receiveText.setToUserName(toUserName);
		receiveText.setMsgId(msgId);
		receiveText.setMsgType(msgType);
		receiveText.setResponse("0");
		receiveText.setAccountId(toUserName);
		this.receiveTextService.save(receiveText);
		//=================================================================================================================
		//Step.1 判断关键字信息中是否管理该文本内容。有的话优先采用数据库中的回复
		LogUtil.info("------------微信客户端发送请求--------------Step.1 判断关键字信息中是否管理该文本内容。有的话优先采用数据库中的回复---");
		WeixinAutoresponseEntity autoResponse = findKey(content, toUserName);
		// 根据系统配置的关键字信息，返回对应的消息
		if (autoResponse != null) {
			String resMsgType = autoResponse.getMsgtype();
			if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(resMsgType)) {
				//根据返回消息key，获取对应的文本消息返回给微信客户端
				WeixinTexttemplateEntity textTemplate = weixinTextTemplateDao.getTextTemplate(sys_accountId, autoResponse.getTemplatename());
				textMessage.setContent(textTemplate.getContent());
				respMessage = MessageUtil.textMessageToXml(textMessage);
			} else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(resMsgType)) {
				List<NewsItem> newsList = this.newsItemService.findByProperty(NewsItem.class,"newsTemplate.id", autoResponse.getResContent());
				NewsTemplate newsTemplate = newsTemplateService.getEntity(NewsTemplate.class, autoResponse.getResContent());
				List<Article> articleList = new ArrayList<Article>();
				for (NewsItem news : newsList) {
					Article article = new Article();
					article.setTitle(news.getTitle());
					article.setPicUrl(bundler.getString("domain") + "/"+ news.getImagePath());
					String url = "";
					if (oConvertUtils.isEmpty(news.getUrl())) {
						url = bundler.getString("domain")+ "/newsItemController.do?goContent&id="+ news.getId();
					} else {
						url = news.getUrl();
					}
					article.setUrl(url);
					article.setDescription(news.getDescription());
					articleList.add(article);
				}
				NewsMessageResp newsResp = new NewsMessageResp();
				newsResp.setCreateTime(new Date().getTime());
				newsResp.setFromUserName(toUserName);
				newsResp.setToUserName(fromUserName);
				newsResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsResp.setArticleCount(newsList.size());
				newsResp.setArticles(articleList);
				respMessage = MessageUtil.newsMessageToXml(newsResp);
			}
		}else {
			// Step.2  通过微信扩展接口（支持二次开发，例如：翻译，天气）
			LogUtil.info("------------微信客户端发送请求--------------Step.2  通过微信扩展接口（支持二次开发，例如：翻译，天气）---");
			List<WeixinExpandconfigEntity> weixinExpandconfigEntityLst = weixinExpandconfigService.findByQueryString("FROM WeixinExpandconfigEntity");
			if (weixinExpandconfigEntityLst.size() != 0) {
				for (WeixinExpandconfigEntity wec : weixinExpandconfigEntityLst) {
					boolean findflag = false;// 是否找到关键字信息
					// 如果已经找到关键字并处理业务，结束循环。
					if (findflag) {
						break;// 如果找到结束循环
					}
					String[] keys = wec.getKeyword().split(",");
					for (String k : keys) {
						if (content.indexOf(k) != -1) {
							String className = wec.getClassname();
							KeyServiceI keyService = (KeyServiceI) Class.forName(className).newInstance();
							respMessage = keyService.excute(content,textMessage, request);
							findflag = true;// 改变标识，已经找到关键字并处理业务，结束循环。
							break;// 当前关键字信息处理完毕，结束当前循环
						}
					}
				}
			}

		}
		return respMessage;
	}*/
	
	/**
	 * 针对事件消息
	 * @param requestMap
	 * @param textMessage
	 * @param bundler
	 * @param respMessage
	 * @param toUserName
	 * @param fromUserName
	 */
	String doDingYueEventResponse(Map<String, String> requestMap,TextMessageResp textMessage ,ResourceBundle bundler,String respMessage
			,String toUserName,String fromUserName,String respContent,String sys_accountId){
		respContent = "谢谢您的关注！回复\"?\"进入主菜单。";
		List<WeixinSubscribeEntity> lst = weixinSubscribeService.findByProperty(WeixinSubscribeEntity.class, "accountid", sys_accountId);
		if (lst.size() != 0) {
			WeixinSubscribeEntity subscribe = lst.get(0);
			String type = subscribe.getMsgtype();
			if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(type)) {
				WeixinTexttemplateEntity textTemplate = this.weixinTexttemplateService
						.getEntity(WeixinTexttemplateEntity.class, subscribe
								.getTemplateid());
				String content = textTemplate.getContent();
				textMessage.setContent(content);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			} /*else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(type)) {
				List<NewsItem> newsList = this.newsItemService.findByProperty(NewsItem.class,"newsTemplate.id", subscribe.getTemplateId());
				List<Article> articleList = new ArrayList<Article>();
				NewsTemplate newsTemplate = newsTemplateService.getEntity(NewsTemplate.class, subscribe.getTemplateId());
				for (NewsItem news : newsList) {
					Article article = new Article();
					article.setTitle(news.getTitle());
					article.setPicUrl(bundler.getString("domain")+ "/" + news.getImagePath());
					String url = "";
					if (oConvertUtils.isEmpty(news.getUrl())) {
						url = bundler.getString("domain")+ "/newsItemController.do?goContent&id="+ news.getId();
					} else {
						url = news.getUrl();
					}
					article.setUrl(url);
					article.setDescription(news.getDescription());
					articleList.add(article);
				}
				NewsMessageResp newsResp = new NewsMessageResp();
				newsResp.setCreateTime(new Date().getTime());
				newsResp.setFromUserName(toUserName);
				newsResp.setToUserName(fromUserName);
				newsResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsResp.setArticleCount(newsList.size());
				newsResp.setArticles(articleList);
				respMessage = MessageUtil.newsMessageToXml(newsResp);
			}*/
		}
		return respMessage;
	}
	
	/**
	 * 
	 * @param requestMap
	 * @param textMessage
	 * @param bundler
	 * @param respMessage
	 * @param toUserName
	 * @param fromUserName
	 * @param respContent
	 * @param sys_accountId
	 * @param request
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	String doMyMenuClickEvent(Map<String, String> requestMap,TextMessageResp textMessage ,ResourceBundle bundler,String respMessage
			,String toUserName,String fromUserName,String respContent,String sys_accountId,HttpServletRequest request) throws Exception{
		String key = requestMap.get("EventKey");
		//自定义菜单CLICK类型
		WeixinMenuEntity menuEntity = this.systemService.findUniqueByProperty(WeixinMenuEntity.class, "menukey",key);
		if (menuEntity != null&& oConvertUtils.isNotEmpty(menuEntity.getTemplateid())) {
			String type = menuEntity.getMsgtype();
			if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(type)) {
				WeixinTexttemplateEntity textTemplate = this.weixinTexttemplateService.getEntity(WeixinTexttemplateEntity.class, menuEntity.getTemplateid());
				String content = textTemplate.getContent();
				textMessage.setContent(content);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			} /*else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(type)) {
				List<NewsItem> newsList = this.newsItemService.findByProperty(NewsItem.class,"newsTemplate.id", menuEntity.getTemplateId());
				List<Article> articleList = new ArrayList<Article>();
				NewsTemplate newsTemplate = newsTemplateService.getEntity(NewsTemplate.class, menuEntity.getTemplateId());
				for (NewsItem news : newsList) {
					Article article = new Article();
					article.setTitle(news.getTitle());
					article.setPicUrl(bundler.getString("domain")+ "/" + news.getImagePath());
					String url = "";
					if (oConvertUtils.isEmpty(news.getUrl())) {
						url = bundler.getString("domain")+ "/newsItemController.do?goContent&id="+ news.getId();
					} else {
						url = news.getContent();
					}
					article.setUrl(url);
					article.setDescription(news.getContent());
					articleList.add(article);
				}
				NewsMessageResp newsResp = new NewsMessageResp();
				newsResp.setCreateTime(new Date().getTime());
				newsResp.setFromUserName(toUserName);
				newsResp.setToUserName(fromUserName);
				newsResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsResp.setArticleCount(newsList.size());
				newsResp.setArticles(articleList);
				respMessage = MessageUtil
						.newsMessageToXml(newsResp);
			}*/ /*else if ("expand".equals(type)) {
				WeixinExpandconfigEntity expandconfigEntity = weixinExpandconfigService.getEntity(WeixinExpandconfigEntity.class,menuEntity.getTemplateId());
				String className = expandconfigEntity.getClassname();
				KeyServiceI keyService = (KeyServiceI) Class.forName(className).newInstance();
				respMessage = keyService.excute("", textMessage,request);

			}*/
		}
		return respMessage;
	}
	
	/**
	 * 
	 * @param requestMap
	 * @param textMessage
	 * @param bundler
	 * @param respMessage
	 * @param toUserName
	 * @param fromUserName
	 * @param respContent
	 * @param sys_accountId
	 * @param request
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	String doMyMenuViewEvent(Map<String, String> requestMap,TextMessageResp textMessage ,ResourceBundle bundler,String respMessage
			,String toUserName,String fromUserName,String respContent,String sys_accountId,HttpServletRequest request) throws Exception{
		String url = requestMap.get("EventKey");
		return null;
	}
	/**
	 * 欢迎语
	 * @return
	 */
	public static String getMainMenu() {
		// 复杂字符串文本读取，采用文件方式存储
		String html = new FreemarkerHelper().parseTemplate("/weixin/welcome.ftl", null);
		return html;
	}
}
