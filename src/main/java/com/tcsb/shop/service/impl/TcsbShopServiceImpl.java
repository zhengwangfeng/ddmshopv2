package com.tcsb.shop.service.impl;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.imagesmanager.entity.TcsbImagesManagerEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.tcsbshoppaymode.entity.TcsbShopPaymodeEntity;
import com.weixin.appletaccesstoken.entity.WeixinAppletAccesstokenEntity;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.util.WeixinUtil;

@Service("tcsbShopService")
@Transactional
public class TcsbShopServiceImpl extends CommonServiceImpl implements TcsbShopServiceI {

	
	
 	public void delete(TcsbShopEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TcsbShopEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TcsbShopEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TcsbShopEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TcsbShopEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TcsbShopEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(TcsbShopEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("name", t.getName());
		map.put("headimg", t.getHeadimg());
		map.put("introdution", t.getIntrodution());
		map.put("address", t.getAddress());
		map.put("phone", t.getPhone());
		map.put("id_del", t.getIdDel());
		map.put("level", t.getLevel());
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
 	public String replaceVal(String sql,TcsbShopEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{headimg}",String.valueOf(t.getHeadimg()));
 		sql  = sql.replace("#{introdution}",String.valueOf(t.getIntrodution()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{id_del}",String.valueOf(t.getIdDel()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
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

	@Override
	public TcsbShopEntity getTcsbShopEntityByUserid(String userid) {
		String hql = "from TcsbShopEntity where userId ='"+userid+"'";
		return commonDao.singleResult(hql);
	}

	@Override
	public void addMain(TcsbShopEntity tcsbShop,
			List<TcsbImagesManagerEntity> imagesManagerList) {
		//保存店铺信息主表
		try {
			this.save(tcsbShop);
			//保存店铺图片附表信息
			for (TcsbImagesManagerEntity tcsbImagesManagerEntity : imagesManagerList) {
				tcsbImagesManagerEntity.setShopId(tcsbShop.getId());
				this.save(tcsbImagesManagerEntity);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
	}

	@Override
	public void updateMain(TcsbShopEntity t,
			List<TcsbImagesManagerEntity> imagesManagerList) {
		//保存店铺主表信息
		try {
			this.saveOrUpdate(t);
			//更新店铺支付方式
			//店铺支付方式写表
			TcsbShopPaymodeEntity tcsbShopPaymodeEntity = this.findUniqueByProperty(TcsbShopPaymodeEntity.class, "shopId", t.getId());
			tcsbShopPaymodeEntity.setPayMode(t.getPayMode());
			this.saveOrUpdate(tcsbShopPaymodeEntity);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//===================================================================================
		//获取参数
		String shopId = t.getId();
		//===================================================================================
		//1.查询出数据库图片信息
	    String hql0 = "from TcsbImagesManagerEntity where 1 = 1 AND shopId = ? ";
	    List<TcsbImagesManagerEntity> tcsbImagesManagerEntities = this.findHql(hql0,shopId);
		//2.筛选更新数据库图片信息
		for(TcsbImagesManagerEntity oldE:tcsbImagesManagerEntities){
			boolean isUpdate = false;
				for(TcsbImagesManagerEntity sendE:imagesManagerList){
					//需要更新的图片信息
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的图片信息，前台没有传递过来则是删除图片
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-店铺图片
			for(TcsbImagesManagerEntity tcsbImagesManagerEntity:imagesManagerList){
				//新增的图片图片ID为空
				if(oConvertUtils.isEmpty(tcsbImagesManagerEntity.getId())){
					//外键设置
					tcsbImagesManagerEntity.setShopId(shopId);;
					this.save(tcsbImagesManagerEntity);
				}
			}
	}

	@Override
	public void addMain(TcsbShopEntity tcsbShop, TSUser user,
			List<TcsbImagesManagerEntity> imagesManagerList) {
		//保存店铺信息主表
				try {
					//店铺状态(0不停用，1停用)
					tcsbShop.setStatus("0");
					this.save(tcsbShop);
					//更新小程序二维码
					tcsbShop.setAppletQrcode(getWeixinAppletQrcode2(tcsbShop,user));
					this.saveOrUpdate(tcsbShop);
					//更新用户权限信息
					user.setShopId(tcsbShop.getId());
					this.saveOrUpdate(user);
					//保存店铺图片附表信息
					for (TcsbImagesManagerEntity tcsbImagesManagerEntity : imagesManagerList) {
						tcsbImagesManagerEntity.setShopId(tcsbShop.getId());
						this.save(tcsbImagesManagerEntity);
					}
					//店铺支付方式写表
					TcsbShopPaymodeEntity tcsbShopPaymodeEntity = new TcsbShopPaymodeEntity();
					tcsbShopPaymodeEntity.setPayMode(tcsbShop.getPayMode());
					tcsbShopPaymodeEntity.setShopId(tcsbShop.getId());
					this.save(tcsbShopPaymodeEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
	private  String getWeixinAppletQrcode(TcsbShopEntity tcsbShop,TSUser user){
		if(tcsbShop == null){
			throw new BusinessException("");
		}
		String filePostfix = "png";
		//下面为小程序二维码
		//小程序二维码存放路径
		String APPLETtoPath = "/appletShopQrcode/user_"+user.getUserName();
		//File appletFile = new File(ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/")+toPath+"."+filePostfix);
		//获取accessToken
		String APPID = ResourceBundle.getBundle("sysConfig").getString("applet.APPID");//服务号的appid
		String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("applet.APPSECRET");//服务号的appSecrect
		AccessToken accessToken = getAppletAccessToken(APPID, APP_SECRECT);

		String appletPath = "";
		try {
			appletPath = WeixinUtil.httpPostWithJSON(accessToken.getToken(), tcsbShop.getId(), "pages/restaurant/restaurant" ,APPLETtoPath,filePostfix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return appletPath;
	}
	
	/**
	 * 带背景文字的小程序码
	 * @param tcsbShop
	 * @param user
	 * @return
	 */
	private  String getWeixinAppletQrcode2(TcsbShopEntity tcsbShop,TSUser user){
		if(tcsbShop == null){
			throw new BusinessException("");
		}
		String filePostfix = "png";
		//下面为小程序二维码
		//小程序二维码存放路径
		String APPLETtoPath = "/appletShopQrcode/user_"+user.getUserName();
		//File appletFile = new File(ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/")+toPath+"."+filePostfix);
		//获取accessToken
		String APPID = ResourceBundle.getBundle("sysConfig").getString("applet.APPID");//服务号的appid
		String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("applet.APPSECRET");//服务号的appSecrect
		AccessToken accessToken = getAppletAccessToken(APPID, APP_SECRECT);
		String fontPath = "/appletShopQrcode/font.png";
		String appletPath = "";
		try {
			//appletPath = WeixinUtil.httpPostWithJSON(accessToken.getToken(), tcsbShop.getId(), "pages/restaurant/restaurant" ,APPLETtoPath,filePostfix);
			appletPath = WeixinUtil.httpPostWithJSON3(accessToken.getToken(), tcsbShop.getId(), "pages/restaurant/restaurant" ,APPLETtoPath,fontPath,filePostfix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return appletPath;
	}
	
	  /**
     * 获取小程序access_token
     * @param appid 凭证
    * @param appsecret 密钥
    * @return
     */
    private AccessToken getAppletAccessToken(String appid,String appsecret) {
    	// 第三方用户唯一凭证
//        String appid = bundle.getString("appId");
//        // 第三方用户唯一凭证密钥
//        String appsecret = bundle.getString("appSecret");
    	List<WeixinAppletAccesstokenEntity> accessTockenList =null;
    	try {
    		accessTockenList = getRealAppletAccessToken();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	if(!accessTockenList.isEmpty()){
    		WeixinAppletAccesstokenEntity accessTocken = accessTockenList.get(0);
        	if(new Date().getTime()-accessTocken.getCreateTime().getTime()>accessTocken.getExpiresIn()*1000){
        		 AccessToken accessToken = null;
                 String requestUrl = WeixinUtil.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
                 JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
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
                         String wrongMessage = "获取token失败 errcode:{} errmsg:{}"+jsonObject.getInt("errcode")+jsonObject.getString("errmsg");
                         org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                     }
                 }
                 return accessToken;
        	}else{
        		 AccessToken  accessToken = new AccessToken();
                 accessToken.setToken(accessTocken.getAccessToken());
                 accessToken.setExpiresIn(accessTocken.getExpiresIn());
        		return accessToken;
        	}
    	}else{
    		 AccessToken accessToken = null;
             String requestUrl = WeixinUtil.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
             JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
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
                     String wrongMessage = "获取token失败 errcode:{} errmsg:{}"+jsonObject.getInt("errcode")+jsonObject.getString("errmsg");
                     org.jeecgframework.core.util.LogUtil.info(wrongMessage);
                 }
             }
             return accessToken;
    	}
    }
    
    /**
     * 从数据库中读取凭证
     * @return
     */
    public List<WeixinAppletAccesstokenEntity> getRealAppletAccessToken(){
        List<WeixinAppletAccesstokenEntity> accessTockenList = this.findByQueryString("from WeixinAppletAccesstokenEntity");
 		return accessTockenList;
    }
    
    /**
     * 更新凭证
     * @return
     */
    public void updateAppletAccessToken( WeixinAppletAccesstokenEntity accessTocken){
    	String sql = "update weixin_applet_accesstoken set access_token='"+accessTocken.getAccessToken()+"',expires_in="+accessTocken.getExpiresIn()+",create_time=now() where id='"+accessTocken.getId()+"'";
    	this.updateBySqlString(sql);
    }
    
    
    /**
     * 保存凭证
     * @return
     */
    public void saveAppletAccessToken( WeixinAppletAccesstokenEntity accessTocken){
    	this.save(accessTocken);
    }

	@Override
	public String getWeixinAppletShopQrcode(TcsbShopEntity entity, TSUser user) {
			return getWeixinAppletQrcode2(entity, user);
	}
}