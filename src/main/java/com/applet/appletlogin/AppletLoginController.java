/**
 * ddmShopV2
 *
 * @author Mar_x
 * create on 2017 2017年10月25日 下午4:39:56
 */
package com.applet.appletlogin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import com.pc.security.TokenMgr;
import com.pc.security.config.Constant;
import com.pc.security.model.SubjectModel;
import org.apache.http.HttpResponse;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSON;
import com.applet.appletutil.HttpClientUtil;
import com.applet.appletutil.TestMain;
import com.applet.userinfo.UserInfoVo;
import com.applet.userinfo.service.AppletUserInfoServiceI;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.weixin.core.util.WeixinUtil;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import com.weixin.weixinuser.service.WeixinUserServiceI;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/appletLogin")
public class AppletLoginController {
	
	@Autowired
    private WeixinUserServiceI weixinUserService;

	@Autowired
	private AppletUserInfoServiceI appletUserInfoService;
    /**
     * 小程序登陆获取openid
     *
     * @param js_code
     * @return
     */
    @RequestMapping("/appletLogin")
    @ResponseBody
    public AjaxJsonApi appletLogin(String js_code,String userInfo) {
    	UserInfoVo userInfo2 = JSON.parseObject(userInfo, UserInfoVo.class);
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        String APPID = ResourceBundle.getBundle("sysConfig").getString("applet.APPID");//服务号的appid
        String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("applet.APPSECRET");//服务号的appSecrect
        String requestUrl = WeixinUtil.applet_jscode2session_url.replace("APPID", APPID).replace("APPSECRET", APP_SECRECT).replace("JSCODE", js_code);
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
        if (jsonObject.get("errcode") != null) {
            ajaxJson.setSuccess(false);
            SubjectModel sub = new SubjectModel(0002, "appletLogin");
            ajaxJson.setToken(TokenMgr.createJWT((String) jsonObject.get("openid"), TokenMgr.generalSubject(sub), Constant.JWT_APPLET_TTL));
        } else {
        	ajaxJson.setMsg(null);
            ajaxJson.setSuccess(true);
            ajaxJson.setObj(jsonObject);
        }
        return ajaxJson;
    }

    
    @RequestMapping("/register")
    @ResponseBody
    public AjaxJsonApi register(String sessionkey,String encryptedData,String iv,String shopId) throws Exception {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        String result = appletUserInfoService.getUserInfo(encryptedData,sessionkey,iv);
        UserInfoVo userInfo = JSON.parseObject(result, UserInfoVo.class);
        
        if(StringUtil.isNotEmpty(userInfo.getUnionId())){
        	
        	TcsbWeixinUserEntity weixinuser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "unionid", userInfo.getUnionId());
        	if(weixinuser == null){
        		appletUserInfoService.saveBaseUserInfo(userInfo);
        	}else{
        		if(StringUtil.isEmpty(weixinuser.getOpenid())){
        			weixinuser.setOpenid(userInfo.getOpenid());
        			weixinUserService.saveOrUpdate(weixinuser);
        		}
        	}
        	
        	 //绑定店铺用户信息
            if(StringUtil.isNotEmpty(shopId)){
            	String hql = "from WeixinUserEntity where openid='"+userInfo.getOpenid()+"' and shopId='"+shopId+"'";
            	WeixinUserEntity user = weixinUserService.singleResult(hql);
            	if(user == null){
            		appletUserInfoService.saveShopUser(userInfo.getOpenid(), shopId);
            	}
            }
            
            //更新优惠券信息
            if(StringUtil.isNotEmpty(userInfo.getUnionId())){
            	appletUserInfoService.updateUserConponByUnionid(userInfo.getUnionId(),userInfo.getOpenid());
            }
        	
        	ajaxJson.setMsg("注册成功");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(result);
        }else{
        	ajaxJson.setMsg("用户授权失败");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(result);
        }
        
        return ajaxJson;
    }
    
    
    
}
