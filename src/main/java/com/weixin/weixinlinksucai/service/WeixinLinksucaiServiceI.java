package com.weixin.weixinlinksucai.service;
import com.weixin.weixinlinksucai.entity.WeixinLinksucaiEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinLinksucaiServiceI extends CommonService{
	
 	public void delete(WeixinLinksucaiEntity entity) throws Exception;
 	
 	public Serializable save(WeixinLinksucaiEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinLinksucaiEntity entity) throws Exception;
 	
	/**
 	 * 获得内部链接
 	 * @return
 	 */
 	//public String installOuterLinkWithSysParams(WeixinLinksucaiEntity t,String openid, String accountid);
 	
	/**
	 * 获取链接素材,对应的访问链接
	 * @param field 
	 * @return
	 */
	public String getInnerLink(String linkSucaiId);
	
	/**
 	 * 获取组装的最新外部链接，追加系统参数
 	 * @param url 外部URL地址
 	 * @wxcontent 微信用户发送消息
 	 * @return
 	 */
 	public String installOuterLinkWithSysParams(String url,String openid, String accountid,String wxcontent);
 	
}
