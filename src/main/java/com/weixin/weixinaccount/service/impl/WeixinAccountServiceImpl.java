package com.weixin.weixinaccount.service.impl;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.LogAnnotation;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeewx.api.core.exception.WexinReqException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weixin.core.util.WeixinUtil;
import com.weixin.p3.oauth2.def.WeiXinOpenConstants;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinaccount.service.WeixinAccountServiceI;

@Service("weixinAccountService")
@Transactional
public class WeixinAccountServiceImpl extends CommonServiceImpl implements WeixinAccountServiceI {

	
 	public void delete(WeixinAccountEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(WeixinAccountEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(WeixinAccountEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(WeixinAccountEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(WeixinAccountEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(WeixinAccountEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(WeixinAccountEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("accountname", t.getAccountname());
		map.put("accounttoken", t.getAccounttoken());
		map.put("accountnumber", t.getAccountnumber());
		map.put("accounttype", t.getAccounttype());
		map.put("accountemail", t.getAccountemail());
		map.put("accountdesc", t.getAccountdesc());
		map.put("accountaccesstoken", t.getAccountaccesstoken());
		map.put("accountappid", t.getAccountappid());
		map.put("accountappsecret", t.getAccountappsecret());
		map.put("username", t.getUsername());
		map.put("weixin_accountid", t.getWeixinAccountid());
		map.put("apiticket", t.getApiticket());
		map.put("apiticketttime", t.getApiticketttime());
		map.put("jsapiticket", t.getJsapiticket());
		map.put("jsapitickettime", t.getJsapitickettime());
		map.put("update_name", t.getUpdateName());
		map.put("update_date", t.getUpdateDate());
		map.put("update_by", t.getUpdateBy());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,WeixinAccountEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{accountname}",String.valueOf(t.getAccountname()));
 		sql  = sql.replace("#{accounttoken}",String.valueOf(t.getAccounttoken()));
 		sql  = sql.replace("#{accountnumber}",String.valueOf(t.getAccountnumber()));
 		sql  = sql.replace("#{accounttype}",String.valueOf(t.getAccounttype()));
 		sql  = sql.replace("#{accountemail}",String.valueOf(t.getAccountemail()));
 		sql  = sql.replace("#{accountdesc}",String.valueOf(t.getAccountdesc()));
 		sql  = sql.replace("#{accountaccesstoken}",String.valueOf(t.getAccountaccesstoken()));
 		sql  = sql.replace("#{accountappid}",String.valueOf(t.getAccountappid()));
 		sql  = sql.replace("#{accountappsecret}",String.valueOf(t.getAccountappsecret()));
 		sql  = sql.replace("#{username}",String.valueOf(t.getUsername()));
 		sql  = sql.replace("#{weixin_accountid}",String.valueOf(t.getWeixinAccountid()));
 		sql  = sql.replace("#{apiticket}",String.valueOf(t.getApiticket()));
 		sql  = sql.replace("#{apiticketttime}",String.valueOf(t.getApiticketttime()));
 		sql  = sql.replace("#{jsapiticket}",String.valueOf(t.getJsapiticket()));
 		sql  = sql.replace("#{jsapitickettime}",String.valueOf(t.getJsapitickettime()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
 	
 	public String getAccessToken() {
		String token = "";
		//WeixinAccountEntity account = findLoginWeixinAccount();
		//获取当前系统唯的公众号
		WeixinAccountEntity account = (WeixinAccountEntity) getList(WeixinAccountEntity.class).get(0);
		token = account.getAccountaccesstoken();
		if (token != null && !"".equals(token)) {
			// 判断有效时间 是否超过2小时
			java.util.Date end = new java.util.Date();
			java.util.Date start = new java.util.Date(account.getAddtoekntime()
					.getTime());
			if ((end.getTime() - start.getTime()) / 1000 / 3600 >= 2) {
				// 失效 重新获取
				String requestUrl = WeixinUtil.access_token_url.replace(
						"APPID", account.getAccountappid()).replace(
						"APPSECRET", account.getAccountappsecret());
				JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,
						"GET", null);
				if (null != jsonObject) {
					try {
						token = jsonObject.getString("access_token");
						// 重置token
						account.setAccountaccesstoken(token);
						// 重置事件
						account.setAddtoekntime(new Date());
						this.saveOrUpdate(account);
					} catch (Exception e) {
						token = null;
						// 获取token失败
						String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
								+ jsonObject.getInt("errcode")
								+ jsonObject.getString("errmsg");
					}
				}
			} else {
				return account.getAccountaccesstoken();
			}
		} else {
			String requestUrl = WeixinUtil.access_token_url.replace("APPID",
					account.getAccountappid()).replace("APPSECRET",
					account.getAccountappsecret());
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET",
					null);
			if (null != jsonObject) {
				try {
					token = jsonObject.getString("access_token");
					// 重置token
					account.setAccountaccesstoken(token);
					// 重置事件
					account.setAddtoekntime(new Date());
					this.saveOrUpdate(account);
				} catch (Exception e) {
					token = null;
					// 获取token失败
					String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
							+ jsonObject.getInt("errcode")
							+ jsonObject.getString("errmsg");
				}
			}
		}
		return token;
	}
	
	
	public String getAccessToken(String accountId) {
		
		WeixinAccountEntity weixinAccountEntity = this.findUniqueByProperty(WeixinAccountEntity.class, "weixin_accountid", accountId);
		String token = weixinAccountEntity.getAccountaccesstoken();
		if (token != null && !"".equals(token)) {
			// 判断有效时间 是否超过2小时
			java.util.Date end = new java.util.Date();
			java.util.Date start = new java.util.Date(weixinAccountEntity.getAddtoekntime()
					.getTime());
			if ((end.getTime() - start.getTime()) / 1000 / 3600 >= 2) {
				// 失效 重新获取
				String requestUrl = WeixinUtil.access_token_url.replace(
						"APPID", weixinAccountEntity.getAccountappid()).replace(
						"APPSECRET", weixinAccountEntity.getAccountappsecret());
				JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,
						"GET", null);
				if (null != jsonObject) {
					try {
						token = jsonObject.getString("access_token");
						// 重置token
						weixinAccountEntity.setAccountaccesstoken(token);
						// 重置事件
						weixinAccountEntity.setAddtoekntime(new Date());
						this.saveOrUpdate(weixinAccountEntity);
					} catch (Exception e) {
						token = null;
						// 获取token失败
						String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
								+ jsonObject.getInt("errcode")
								+ jsonObject.getString("errmsg");
					}
				}
			} else {
				return weixinAccountEntity.getAccountaccesstoken();
			}
		} else {
			String requestUrl = WeixinUtil.access_token_url.replace("APPID",
					weixinAccountEntity.getAccountappid()).replace("APPSECRET",
							weixinAccountEntity.getAccountappsecret());
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET",
					null);
			if (null != jsonObject) {
				try {
					token = jsonObject.getString("access_token");
					// 重置token
					weixinAccountEntity.setAccountaccesstoken(token);
					// 重置事件
					weixinAccountEntity.setAddtoekntime(new Date());
					this.saveOrUpdate(weixinAccountEntity);
				} catch (Exception e) {
					token = null;
					// 获取token失败
					String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
							+ jsonObject.getInt("errcode")
							+ jsonObject.getString("errmsg");
				}
			}
		}
		return token;
	}

	public WeixinAccountEntity findLoginWeixinAccount() {
		TSUser user = ResourceUtil.getSessionUserName();
		List<WeixinAccountEntity> acclst = this.findByProperty(
				WeixinAccountEntity.class, "userName", user.getUserName());
		WeixinAccountEntity weixinAccountEntity = acclst.size() != 0 ? acclst
				.get(0) : null;
		if (weixinAccountEntity != null) {
			return weixinAccountEntity;
		} else {
			weixinAccountEntity = new WeixinAccountEntity();
			// 返回个临时对象，防止空指针
			weixinAccountEntity.setWeixinAccountid("-1");
			weixinAccountEntity.setId("-1");
			return weixinAccountEntity;
		}
	}

	@Override
	public List<WeixinAccountEntity> findByUsername(String username) {
		List<WeixinAccountEntity> acclst = this.findByProperty(
				WeixinAccountEntity.class, "userName", username);
		return acclst;
	}

	@Override
	public WeixinAccountEntity findByToUsername(String toUserName) {
		return this.findUniqueByProperty(WeixinAccountEntity.class,
				"weixin_accountid", toUserName);

	}
	/**
	 * 通过微信原始ID，获取系统微信公众账号配置信息
	 * @param weixinId
	 * @return
	 */
	public WeixinAccountEntity getWeixinAccountByWeixinOldId(String weixinId){
		if(oConvertUtils.isEmpty(weixinId)){
			return null;
		}
		List<WeixinAccountEntity> weixinAccounts = this.findByProperty(WeixinAccountEntity.class, "weixin_accountid", weixinId);
		if(weixinAccounts!=null){
			return weixinAccounts.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 重置 AccessToken
	 * @return
	 * @throws WexinReqException 
	 */
	@LogAnnotation(operateDescribe="重置Token",operateFuncNm="resetAccessToken",operateModelNm="AjaxJson")
	public AjaxJson resetAccessToken(String accountid) throws WexinReqException {
		AjaxJson json = new AjaxJson();
		String token = "";
		Date getAccessTokenDate = new Date();
		WeixinAccountEntity account  = this.get(WeixinAccountEntity.class, accountid);
		token = account.getAccountaccesstoken();
		String requestUrl = WeixinUtil.access_token_url.replace("APPID",account.getAccountappid()).replace("APPSECRET",account.getAccountappsecret());
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET",null);
		
		if (null != jsonObject) {
			if (jsonObject.has("errcode") && jsonObject.getInt("errcode") != 0) {
				//update-begin----author:scott---------date:20150719-------for:提示信息优化----------------------
				String errormsg = "很抱歉，系统异常，请联系管理员!";
				if(jsonObject.containsKey("errcode")){
					errormsg = errormsg + "　错误码:"+jsonObject.get("errcode");
				}
				json.setMsg(errormsg);
				//update-end----author:scott---------date:20150719-------for:提示信息优化----------------------
				json.setSuccess(false);
				return json;
			}
			try {
				token = jsonObject.getString("access_token");
				// 重置token
				account.setAccountaccesstoken(token);
				// 重置事件
				account.setCreateDate(getAccessTokenDate);
				
				//--update-begin---author：scott-------date:20151026--------for:重置Token扩展支持Apiticket、jsapi_ticket-------------------------
				try {
					//[2].获取api凭证
//					GetticketRtn getticketRtn = JwQrcodeAPI.doGetticket(token);
//					if (null != getticketRtn) {
//						try {
//							// 重置token
//							account.setApiticket(getticketRtn.getTicket());
//							// 重置事件
//							account.setApiticketttime(getAccessTokenDate);
//							LogUtil.info("---------定时任务重置超过2小时失效token------------------"+"获取Apiticket成功");
//						} catch (Exception e) {
//							// 获取api凭证失败
//							String wrongMessage = "获取api凭证失败 errcode:{"+ getticketRtn.getErrcode()+"} errmsg:{"+getticketRtn.getErrmsg()+"}";
//							LogUtil.info(wrongMessage);
//						}
//					}
				} catch (Exception e) {
					LogUtil.info("---------------------定时任务异常--【获取api凭证】--------------"+e.toString());
				}
				//[3].获取jsapi凭证
				try {
					String jsapiticket = null;
					String jsapi_ticket_url = WeiXinOpenConstants.JSAPI_TICKET_URL.replace("ACCESS_TOKEN", token);
					JSONObject jsapi_ticket_json = WeixinUtil.httpRequest(jsapi_ticket_url, "GET", null);
					if (null != jsapi_ticket_json) {
						try {
							jsapiticket = jsapi_ticket_json.getString("ticket");
							// 重置token
							account.setJsapiticket(jsapiticket);
							// 重置事件
							account.setJsapitickettime(getAccessTokenDate);
							LogUtil.info("---------定时任务重置超过2小时失效token------------------"+"获取Jsapiticket成功");
						} catch (Exception e) {
							//获取jsapi凭证失败
							String wrongMessage = "获取jsapi凭证失败 errcode:{"+ (jsonObject.containsKey("errcode")?jsonObject.get("errcode"):"") +"} errmsg:{"+ (jsonObject.containsKey("errmsg")?jsonObject.getString("errmsg"):"") +"}";
							LogUtil.info(wrongMessage);
						}
					}
				} catch (Exception e) {
					LogUtil.info("---------------------定时任务异常--【获取jsapi凭证】--------------"+e.toString());
				}
				//--update-end---author：scott-------date:20151026--------for:重置Token扩展支持Apiticket、jsapi_ticket-------------------------
				this.saveOrUpdate(account);
			} catch (Exception e) {
				token = null;
				// 获取token失败
				String wrongMessage = "获取token失败 errcode:{ "+ jsonObject.getInt("errcode")+" } errmsg:{ "+ jsonObject.getString("errmsg") +" }";
				json.setMsg(wrongMessage);
				json.setSuccess(false);
				return json;
			}
		}
		json.setSuccess(true);
		return json;
	}
}