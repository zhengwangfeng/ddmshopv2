package com.weixin.weixinaccount.service;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;
import org.jeewx.api.core.exception.WexinReqException;

import java.io.Serializable;
import java.util.List;

public interface WeixinAccountServiceI extends CommonService{
	
 	public void delete(WeixinAccountEntity entity) throws Exception;
 	
 	public Serializable save(WeixinAccountEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinAccountEntity entity) throws Exception;
 	
 	public String getAccessToken();
 	
 	public String getAccessToken(String accountId);
 	
 	@Deprecated
 	public WeixinAccountEntity findLoginWeixinAccount();
 	public List<WeixinAccountEntity> findByUsername(String username);
 	/**
 	 * 按微信的toUsername获取对象
 	 * @param toUserName
 	 * @return
 	 */
 	public WeixinAccountEntity findByToUsername(String toUserName);
 	/**
	 * 通过微信原始ID，获取系统微信公众账号配置信息
	 * @param weixinId
	 * @return
	 */
	public WeixinAccountEntity getWeixinAccountByWeixinOldId(String weixinId);
	
	public AjaxJson resetAccessToken(String accountid) throws WexinReqException;
 	
}
