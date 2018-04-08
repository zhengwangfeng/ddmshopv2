package com.applet.userinfo.service.impl;


import com.applet.userinfo.UserInfoVo;
import com.applet.userinfo.service.AppletUserInfoServiceI;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import com.weixin.weixinuser.service.WeixinUserServiceI;

import org.apache.xerces.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Service("appletUserInfoService")
@Transactional
public class AppletUserInfoService implements AppletUserInfoServiceI {

    @Autowired
    private WeixinUserServiceI weixinUserService;

    @Override
    public boolean saveBaseUserInfo(UserInfoVo userInfo) throws Exception{
        TcsbWeixinUserEntity tcuser = new TcsbWeixinUserEntity();
        tcuser.setOpenid(userInfo.getOpenid());
        tcuser.setCity(userInfo.getCity());
        tcuser.setCountry(userInfo.getCountry());
        tcuser.setHeadimgurl(userInfo.getAvatarUrl());
        String niakname = userInfo.getNickName().replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""); 
        tcuser.setNickname(niakname);
        tcuser.setProvince(userInfo.getProvince());
        tcuser.setSex(userInfo.getGender());
        tcuser.setCreateTime(new Date());
        tcuser.setUnionid(userInfo.getUnionId());
        weixinUserService.save(tcuser);
        return true;
    }
    
    
    public String getUserInfo(String encryptedData,String sessionKey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        
        String result = "";
        
        try {
               // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = new String(resultByte, "UTF-8");
               //System.out.println(result);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    

	@Override
	public void updateUserConponByMobile(String mobile,String userId) {
		
		CriteriaQuery cq = new CriteriaQuery(TcsbCouponEntity.class);
		cq.add(Restrictions.eq("mobile", mobile));
		cq.add(Restrictions.isNull("userId"));
		List<TcsbCouponEntity> aa = weixinUserService.getListByCriteriaQuery(cq, false);
		if(aa.size()>0){
			for (TcsbCouponEntity tcsbCouponEntity : aa) {
				tcsbCouponEntity.setUserId(userId);
				weixinUserService.saveOrUpdate(tcsbCouponEntity);
			}
		}
	}


	@Override
	public void saveShopUser(String openid, String shopId) {
		WeixinUserEntity user = new WeixinUserEntity();
		user.setOpenid(openid);
		user.setShopId(shopId);
		user.setCreateTime(new Date());
		try {
			weixinUserService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void updateUserConponByUnionid(String unionid, String userId) {
		CriteriaQuery cq = new CriteriaQuery(TcsbCouponEntity.class);
		cq.add(Restrictions.eq("unionid", unionid));
		cq.add(Restrictions.isNull("userId"));
		List<TcsbCouponEntity> aa = weixinUserService.getListByCriteriaQuery(cq, false);
		if(aa.size()>0){
			for (TcsbCouponEntity tcsbCouponEntity : aa) {
				tcsbCouponEntity.setUserId(userId);
				weixinUserService.saveOrUpdate(tcsbCouponEntity);
			}
		}
	}
}
