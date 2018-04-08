package com.weixin.weixinlinksucai.service.impl;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.service.SystemService;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.jeewx.api.wxuser.user.model.Wxuser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weixin.core.util.WeixinUtil;
import com.weixin.p3.oauth2.util.CmsFreemarkerHelperNew;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinlinksucai.entity.WeixinLinksucaiEntity;
import com.weixin.weixinlinksucai.service.WeixinLinksucaiServiceI;

@Service("weixinLinksucaiService")
@Transactional
public class WeixinLinksucaiServiceImpl extends CommonServiceImpl implements WeixinLinksucaiServiceI {

	 
 	public void delete(WeixinLinksucaiEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(WeixinLinksucaiEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(WeixinLinksucaiEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(WeixinLinksucaiEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(WeixinLinksucaiEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(WeixinLinksucaiEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(WeixinLinksucaiEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("name", t.getName());
		map.put("outer_link", t.getOuterLink());
		map.put("content", t.getContent());
		map.put("inner_link", t.getInnerLink());
		map.put("transfer_sign", t.getTransferSign());
		map.put("accountid", t.getAccountid());
		map.put("post_code", t.getPostCode());
		map.put("share_status", t.getShareStatus());
		map.put("is_encrypt", t.getIsEncrypt());
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
 	public String replaceVal(String sql,WeixinLinksucaiEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{outer_link}",String.valueOf(t.getOuterLink()));
 		sql  = sql.replace("#{content}",String.valueOf(t.getContent()));
 		sql  = sql.replace("#{inner_link}",String.valueOf(t.getInnerLink()));
 		sql  = sql.replace("#{transfer_sign}",String.valueOf(t.getTransferSign()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountid()));
 		sql  = sql.replace("#{post_code}",String.valueOf(t.getPostCode()));
 		sql  = sql.replace("#{share_status}",String.valueOf(t.getShareStatus()));
 		sql  = sql.replace("#{is_encrypt}",String.valueOf(t.getIsEncrypt()));
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
 	
 	/**
 	 * 获取组装的最新外部链接，追加系统参数
 	 * @param url 外部URL地址
 	 * @wxcontent 微信用户发送消息  
 	 * @return
 	 */
 	public String installOuterLinkWithSysParams(String url,String openid, String accountid,String wxcontent){
 		String outlink = null;
 		CmsFreemarkerHelperNew  cmsFreemarkerHelper = new CmsFreemarkerHelperNew();
 		//捷微微信管家系统，可以获取到的微信相关参数
 		Map<String,Object> mpLinkPrams = new HashMap<String,Object>();
 		mpLinkPrams.put("openid", oConvertUtils.getString(openid));
 		mpLinkPrams.put("accountid", accountid);
 		System.out.println("----------------------------setp1-------------------");
 		try {
	 		//传递客户发送消息，给外围接口，供业务使用
	 		mpLinkPrams.put("wxcontent", wxcontent);
	 		if(oConvertUtils.isNotEmpty(accountid)){
	 			WeixinAccountEntity weixinAccount  = this.get(WeixinAccountEntity.class, accountid);
	 			
	 	 		if(weixinAccount!= null){
	 	 			//对应微信公众账号原始ID
	 	 			mpLinkPrams.put("wxid", weixinAccount.getWeixinAccountid());
	 	 			mpLinkPrams.put("wxname", weixinAccount.getAccountname());
	 	 			mpLinkPrams.put("wxcode", weixinAccount.getAccountnumber());
	 	 			mpLinkPrams.put("appid", weixinAccount.getAccountappid());
	 	 			mpLinkPrams.put("appsecret", weixinAccount.getAccountappsecret());
	 	 			mpLinkPrams.put("accesstoken", weixinAccount.getAccounttoken());
	 	 			System.out.println("----------------------------setp2-------------------");
	 	 			
	 	 			//【微信用户昵称】【是否关注微信公众号】----------------------
					try {
						System.out.println("----------------------------setp3-------------------");
						String token = weixinAccount.getAccountaccesstoken();
						if (token != null && !"".equals(token)) {
							// 判断有效时间 是否超过2小时
							java.util.Date end = new java.util.Date();
							java.util.Date start = new java.util.Date(weixinAccount.getAddtoekntime()
									.getTime());
							if ((end.getTime() - start.getTime()) / 1000 / 3600 >= 2) {
								// 失效 重新获取
								String requestUrl = WeixinUtil.access_token_url.replace(
										"APPID", weixinAccount.getAccountappid()).replace(
										"APPSECRET", weixinAccount.getAccountappsecret());
								JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,
										"GET", null);
								if (null != jsonObject) {
									try {
										token = jsonObject.getString("access_token");
										// 重置token
										weixinAccount.setAccountaccesstoken(token);
										// 重置事件
										weixinAccount.setAddtoekntime(new Date());
										this.saveOrUpdate(weixinAccount);
									} catch (Exception e) {
										token = null;
										// 获取token失败
										String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
												+ jsonObject.getInt("errcode")
												+ jsonObject.getString("errmsg");
									}
								}
							} 
						} else {
							String requestUrl = WeixinUtil.access_token_url.replace("APPID",
									weixinAccount.getAccountappid()).replace("APPSECRET",
											weixinAccount.getAccountappsecret());
							JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET",
									null);
							if (null != jsonObject) {
								try {
									token = jsonObject.getString("access_token");
									// 重置token
									weixinAccount.setAccountaccesstoken(token);
									// 重置事件
									weixinAccount.setAddtoekntime(new Date());
									this.saveOrUpdate(weixinAccount);
								} catch (Exception e) {
									token = null;
									// 获取token失败
									String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
											+ jsonObject.getInt("errcode")
											+ jsonObject.getString("errmsg");
								}
							}
						}
						
						Wxuser wxuser =  JwUserAPI.getWxuser(weixinAccount.getAccountaccesstoken(), oConvertUtils.getString(openid));
						System.out.println("----------------------------setp4-------------------");
						if(wxuser!=null){
							mpLinkPrams.put("subscribe", wxuser.getSubscribe());
							//解码传递，中文会有问题
							mpLinkPrams.put("nickname", URLEncoder.encode(wxuser.getNickname(),"UTF-8"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						mpLinkPrams.put("subscribe", "0");
					}
					System.out.println("----------------------------setp3-------------------");
	 	 			//update-end--Author:yiming.zhang  Date:20150317 for：【微信用户昵称】【是否关注微信公众号】----------------------
	 	 		}
	 		}
	 		outlink = cmsFreemarkerHelper.parseTemplateContent(url, mpLinkPrams);
 		} catch (Exception e) {
 			e.printStackTrace();
		}
 		return outlink;
 	}
 	
 	
 	
	/**
	 * 获取链接素材对应的访问链接
	 * @param field 
	 * @return
	 */
	public String getInnerLink(String linkSucaiId) {
		String baseurl = ResourceUtil.getConfigByName("domain");
		List<WeixinAccountEntity> weixinAccountEntities  = getList(WeixinAccountEntity.class);
		WeixinAccountEntity weixinAccountEntity =weixinAccountEntities.get(0);
		String inner_link = baseurl+"/weixinLinksucaiController.do?link&id="+ linkSucaiId + "&accountid="+weixinAccountEntity.getId();
		return inner_link;
	}
}