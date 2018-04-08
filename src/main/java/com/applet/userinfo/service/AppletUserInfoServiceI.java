package com.applet.userinfo.service;

import com.applet.userinfo.UserInfoVo;

import java.io.Serializable;

public interface AppletUserInfoServiceI {
    public boolean saveBaseUserInfo(UserInfoVo userInfoVo) throws Exception;
    
    public void updateUserConponByMobile(String mobile,String userId);
    
    public void updateUserConponByUnionid(String unionid,String userId);
    
    public String getUserInfo(String encryptedData,String sessionKey,String iv);
    
    public void saveShopUser(String openid,String shopId);
    
}
