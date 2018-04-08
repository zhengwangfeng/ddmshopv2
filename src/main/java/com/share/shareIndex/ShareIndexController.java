package com.share.shareIndex;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SellerSmsClient;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.share.vo.ShareIndexVo;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopsharebannerimages.entity.TcsbShopShareBannerImagesEntity;
import com.tcsb.shopsharecontent.entity.TcsbShopShareContentEntity;
import com.tcsb.shopsharedestailsimages.entity.TcsbShopShareDestailsImagesEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.entity.common.JsapiTicket;
import com.weixin.core.util.PayCommonUtil;
import com.weixin.core.util.SignUtil;
import com.weixin.core.util.WeixinUtil;


@Controller
@RequestMapping("/shareIndexController")
public class ShareIndexController {
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	
	
	@RequestMapping(params = "getShareDetailInfo")
    @ResponseBody
    public JSONPObject getShareDetailInfo(HttpServletRequest request,String shareId,String shopId,String weixinOpenId,String unionid) {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
    	ShareIndexVo shareIndexVo = new ShareIndexVo();
    	List<TcsbShopShareBannerImagesEntity> shareBannerImages= tcsbShopService.findByProperty(TcsbShopShareBannerImagesEntity.class, "shopId", shopId);
    	List<TcsbShopShareDestailsImagesEntity> shareDestailsImages= tcsbShopService.findByProperty(TcsbShopShareDestailsImagesEntity.class, "shopId", shopId);
    	
    	TcsbShopShareContentEntity shareConten = tcsbShopService.findUniqueByProperty(TcsbShopShareContentEntity.class, "shopId", shopId);
    	
    	shareIndexVo.setShareBannerImages(shareBannerImages);
    	shareIndexVo.setShareDestailsImages(shareDestailsImages);
    	shareIndexVo.setShareContent(shareConten);
    	String hql = "from TcsbFullcutTemplateEntity where shopId ='"+shopId+"' and couponType=1 and isShow=1";
    	List<TcsbFullcutTemplateEntity> fullcutTemplateList = tcsbShopService.findByQueryString(hql);
    	shareIndexVo.setFullcutTemplate(fullcutTemplateList);
    	ajaxJsonApi.setMsg("成功");
    	ajaxJsonApi.setSuccess(true);
    	ajaxJsonApi.setObj(shareIndexVo);
    	return new JSONPObject(callbackFunName, ajaxJsonApi);
    }
	
	
	
	@RequestMapping(params = "getCoupon")
    @ResponseBody
    public JSONPObject getCoupon(HttpServletRequest request,String fullTemplateId,String shareUnionid,String unionid) throws ParseException {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

    	TcsbWeixinUserEntity user = tcsbShopService.findUniqueByProperty(TcsbWeixinUserEntity.class, "unionid", unionid);
    	if(user == null){
    		ajaxJsonApi.setMsg("用户数据异常");
        	ajaxJsonApi.setSuccess(false);
        	ajaxJsonApi.setObj(0);
    	}else{
    		if(StringUtil.isEmpty(user.getMobile())){
    			ajaxJsonApi.setMsg("未绑定手机号");
            	ajaxJsonApi.setSuccess(false);
            	ajaxJsonApi.setObj(1);
    		}else{
    			String hql = "from TcsbCouponEntity where unionid='"+unionid+"' and fullcutTemplateId='"+fullTemplateId+"'";
    			TcsbCouponEntity coupon = tcsbShopService.singleResult(hql);
    	    	if(coupon == null){
    	    		TcsbFullcutTemplateEntity fullcutTemplate = tcsbShopService.get(TcsbFullcutTemplateEntity.class, fullTemplateId);
    	    		TcsbCouponEntity saveCoupon = new TcsbCouponEntity();
    	    		saveCoupon.setCreateDate(new Date());
    	    		Integer userPeriod = fullcutTemplate.getUsePeriod();
    	            String dateUnit = fullcutTemplate.getDateUnit();
    	            int days = 0;
    	            //todo
    	            if (dateUnit.equals("year")) {
    	                days = userPeriod * DateUtils.getCurrentYearDays();
    	            } else if (dateUnit.equals("month")) {
    	                days = userPeriod * DateUtils.getCurrentMonthDay();
    	            } else {
    	                days = userPeriod;
    	            }
    	            String expiryDate = DateUtils.getAfterDayDate(String.valueOf(days));
    	            saveCoupon.setExpiryDate(DateUtils.parseDate(expiryDate, "yyyy-MM-dd HH:mm:ss"));
    	    		saveCoupon.setFullcutTemplateId(fullTemplateId);
    	    		saveCoupon.setShopId(fullcutTemplate.getShopId());
    	    		saveCoupon.setCouponType("1");
    	    		saveCoupon.setShareUnionid(shareUnionid);
    	    		saveCoupon.setUnionid(unionid);
    	    		saveCoupon.setUseStatus("0");
    	    		saveCoupon.setUseRange("0");
    	    		tcsbShopService.save(saveCoupon);
    	    		ajaxJsonApi.setMsg("领取成功");
    	        	ajaxJsonApi.setSuccess(true);
    	        	ajaxJsonApi.setObj(3);
    	    	}else{
    	    		ajaxJsonApi.setMsg("已领取");
    	        	ajaxJsonApi.setSuccess(false);
    	        	ajaxJsonApi.setObj(2);
    	    	}
    		}
    	}
    	
    	
    	
    	return new JSONPObject(callbackFunName, ajaxJsonApi);
    }
	
	
	
	@RequestMapping(params = "bindMobile")
    @ResponseBody
    public JSONPObject bindMobile(HttpServletRequest request,String mobile,String unionid,String smsCode) throws ParseException {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

    	TcsbWeixinUserEntity unioniduser = tcsbShopService.findUniqueByProperty(TcsbWeixinUserEntity.class, "unionid", unionid);
    	if(StringUtil.isNotEmpty(unioniduser)){
    		if(unioniduser.getSmscode().equals(smsCode)){
    			unioniduser.setMobile(mobile);
    			tcsbShopService.saveOrUpdate(unioniduser);
    			ajaxJsonApi.setMsg("绑定成功");
           	 	ajaxJsonApi.setSuccess(true);
           	 	ajaxJsonApi.setObj(true);
    		}else{
    			ajaxJsonApi.setMsg("验证码不正确");
           	 	ajaxJsonApi.setSuccess(false);
           	 	ajaxJsonApi.setObj(false);
    		}
    	}else{
    		ajaxJsonApi.setMsg("用户信息异常！！！");
       	 	ajaxJsonApi.setSuccess(false);
       	 	ajaxJsonApi.setObj(null);
    	}
    	return new JSONPObject(callbackFunName, ajaxJsonApi);
    }
	
	
	@RequestMapping(params = "checkMobile")
    @ResponseBody
    public JSONPObject checkMobile(HttpServletRequest request,String unionid) throws ParseException {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

    	TcsbWeixinUserEntity unioniduser = tcsbShopService.findUniqueByProperty(TcsbWeixinUserEntity.class, "unionid", unionid);
    	if(unioniduser == null){
    		ajaxJsonApi.setMsg("系统繁忙");
       	 	ajaxJsonApi.setSuccess(false);
       	 	ajaxJsonApi.setObj(null);
    	}else{
    		if(StringUtil.isNotEmpty(unioniduser.getMobile())){
    			ajaxJsonApi.setMsg("已绑定");
           	 	ajaxJsonApi.setSuccess(true);
           	 	ajaxJsonApi.setObj(true);
    		}else{
    			ajaxJsonApi.setMsg("未绑定");
           	 	ajaxJsonApi.setSuccess(false);
           	 	ajaxJsonApi.setObj(false);
    		}
    	}
    	return new JSONPObject(callbackFunName, ajaxJsonApi);
    }
	
	
	
	/**
     * 获取手机绑定验证码
     *
     * @param userId
     * @param mobile
     * @return
     */
    @RequestMapping(params = "getSmsCode")
    @ResponseBody
    public JSONPObject getSmsCode(HttpServletRequest request,String unionid, String mobile) {
    	String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        //AjaxJsonApi ajaxJson = new AjaxJsonApi();
        if (StringUtil.isNotEmpty(unionid) && StringUtil.isNotEmpty(mobile)) {
        	TcsbWeixinUserEntity mobileUser = tcsbShopService.findUniqueByProperty(TcsbWeixinUserEntity.class, "mobile", mobile);
        	if(mobileUser == null){
        		//检测验证码
                SellerSmsClient client = new SellerSmsClient();
                String smsCode;
                TcsbWeixinUserEntity weixinUser = tcsbShopService.findUniqueByProperty(TcsbWeixinUserEntity.class, "unionid", unionid);
                if (StringUtil.isNotEmpty(weixinUser)) {
                    if (StringUtil.isEmpty(weixinUser.getSmscode())) {
                        try {
                            smsCode = client.sendMessage(mobile);
                            weixinUser.setSmscode(smsCode);
                            weixinUser.setWaitMobile(mobile);
                            weixinUser.setSendtime(new Date());
                            tcsbShopService.saveOrUpdate(weixinUser);
                            ajaxJsonApi.setMsg("发送成功");
                            ajaxJsonApi.setSuccess(true);
                            ajaxJsonApi.setObj(smsCode);
                        } catch (IOException e) {
                        	ajaxJsonApi.setMsg("发送失败");
                        	ajaxJsonApi.setSuccess(false);
                        	ajaxJsonApi.setObj(null);
                        }
                    } else {
                        Calendar calDes = null;
                        try {
                            calDes = DateUtils.parseCalendar(DateUtils.date2Str(weixinUser.getSendtime(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar calSrc = null;
                        try {
                            calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (DateUtils.dateDiff('s', calSrc, calDes) < 120) {
                        	ajaxJsonApi.setMsg(ReturnMessageEnum.SMSISSEND.getMsg());
                        	ajaxJsonApi.setSuccess(false);
                        	ajaxJsonApi.setObj(null);
                        } else {
                            //短信过期重新发送
                            try {
                                smsCode = client.sendMessage(mobile);
                                ajaxJsonApi.setMsg(ReturnMessageEnum.SMSISOUTOFDATE.getMsg() + "重新发送");
                                ajaxJsonApi.setSuccess(true);
                                weixinUser.setSmscode(smsCode);
                                weixinUser.setWaitMobile(mobile);
                                weixinUser.setSendtime(new Date());
                                ajaxJsonApi.setObj(smsCode);
                                tcsbShopService.saveOrUpdate(weixinUser);
                            } catch (IOException e) {
                            	ajaxJsonApi.setMsg("发送失败");
                            	ajaxJsonApi.setSuccess(false);
                            	ajaxJsonApi.setObj(null);
                            }

                        }
                    }
                }
        	}else{
        		ajaxJsonApi.setMsg("手机号已被绑定");
            	ajaxJsonApi.setSuccess(false);
            	ajaxJsonApi.setObj(null);
        	}
                
            }else {
            	ajaxJsonApi.setMsg("用户ID，mobile不能为空");
            	ajaxJsonApi.setSuccess(false);
            	ajaxJsonApi.setObj(null);
        }


        return new JSONPObject(callbackFunName, ajaxJsonApi);
    }
	
	
	
	/**
	 * @Description: 批量上传图片文件
	 * @author: mar_x
	 * @return:String
	 */
	@RequestMapping(params = "uploadImgList")
	@ResponseBody
	public String uploadPictureList(
	        @RequestParam(value="file",required=false)MultipartFile[] file,
	        HttpServletRequest request,String MAX_FILE_SIZE){
		System.out.println(file+"==="+MAX_FILE_SIZE);
		
	    File targetFile=null;
	    String msg="";//返回存储路径
	    int code=1;
	    List imgList=new ArrayList();
	    if (file!=null && file.length>0) {
	        for (int i = 0; i < file.length; i++) {
	            String fileName=file[i].getOriginalFilename();//获取文件名加后缀
	            if(fileName!=null&&fileName!=""){   
	                String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
	                String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
	                String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
	                fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名

	                //先判断文件是否存在
	                String fileAdd = new Random().nextInt(1000)+"";
	                File file1 =new File(path); 
	                //如果文件夹不存在则创建    
	                if(!file1 .exists()  && !file1 .isDirectory()){       
	                    file1 .mkdir();  
	                }
	                targetFile = new File(file1, fileName);
	                try {
	                    file[i].transferTo(targetFile);
	                    msg=returnUrl+fileAdd+"/"+fileName;
	                    imgList.add(msg);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }               
	    return JSON.toJSONString(imgList);
	}
	
	
	
	@RequestMapping("/getShopAppletQrcode")
	@ResponseBody
	public JSONPObject getShopAppletQrcode(HttpServletRequest request,String shopId){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, shopId);
		ajaxJsonApi.setMsg("获取店铺小程序码成功 ");
		ajaxJsonApi.setSuccess(true);
		String appletShopQrcode = "";
		if (tcsbShopEntity!=null) {
			appletShopQrcode = ResourceUtil.getConfigByName("applet.qrcode") + tcsbShopEntity.getAppletQrcode();
		}
		ajaxJsonApi.setObj(appletShopQrcode);	
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	

}
