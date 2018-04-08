package com.applet.platform.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SaleSmsClient;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.platformcoupon.entity.TcsbPlatformCouponEntity;
import com.tcsb.platformcoupon.service.TcsbPlatformCouponServiceI;
import com.tcsb.platformfullcuttemplate.entity.TcsbPlatformFullcutTemplateEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;

@Controller
@RequestMapping("/appletPlatFormCoupon")
public class AppletPlatFormCouponController {

	@Autowired
	private TcsbPlatformCouponServiceI tcsbPlatformCouponService;
	
	
	@RequestMapping("/getPlatFormCoupon")
	@ResponseBody
	public AjaxJsonApi getPlatFormCoupon(String userId,String mobile) throws Exception{
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		
		TcsbWeixinUserEntity user = tcsbPlatformCouponService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
		
		
		if(StringUtil.isNotEmpty(user)){
			
			if(StringUtil.isNotEmpty(user.getMobile())){
				String hql = "from TcsbPlatformCouponEntity where userId = '"+userId+"' and isNewuserCouon=1";
				TcsbPlatformCouponEntity tpcoupon = tcsbPlatformCouponService.singleResult(hql);
				if(StringUtil.isEmpty(tpcoupon)){
					//领取优惠券
					String hqlrand = "from TcsbPlatformFullcutTemplateEntity where isNewuserRandom=1 and isUse=1"; 
					List<TcsbPlatformFullcutTemplateEntity> tpFullcutTemplate = tcsbPlatformCouponService.findByQueryString(hqlrand);
					TcsbPlatformCouponEntity platformCoupon = new TcsbPlatformCouponEntity();
					int selected= getPrizeIndex(tpFullcutTemplate);
					
					Integer userPeriod = tpFullcutTemplate.get(selected).getUsePeriod();
		            String dateUnit = tpFullcutTemplate.get(selected).getDateUnit();
		            int days = 0;
		            if (dateUnit.equals("year")) {
		                days = userPeriod * DateUtils.getCurrentYearDays();
		            } else if (dateUnit.equals("month")) {
		                days = userPeriod * DateUtils.getCurrentMonthDay();
		            } else {
		                days = userPeriod;
		            }
		            
		           
		            
		            String expiryDate = DateUtils.getAfterDayDate(String.valueOf(days));
		            platformCoupon.setExpiryDate(DateUtils.parseDate(expiryDate, "yyyy-MM-dd HH:mm:ss"));
		            platformCoupon.setUserId(userId);
		            platformCoupon.setFullcutTemplateId(tpFullcutTemplate.get(selected).getId());
		            platformCoupon.setIsNewuserCouon("1");
		            platformCoupon.setUseStatus("0");
		            platformCoupon.setCreateDate(new Date());
		            tcsbPlatformCouponService.save(platformCoupon);
		            
		            String dateUnitName = "";
		            //发送短信
		            if (dateUnit.equals("month")) {
		            	dateUnitName = "个月";
		            }
		            if (dateUnit.equals("year")) {
		            	dateUnitName = "年";
		            }
		            if (dateUnit.equals("day")) {
		            	dateUnitName = "日";
		            }
		            //拼接发送的短信内容
		            String content = "恭喜您获得点单么平台满"+ tpFullcutTemplate.get(selected).getTotal() +"元减" + tpFullcutTemplate.get(selected).getDiscount() + "元优惠券一张，有效期" + userPeriod + "" + dateUnitName + "，欢迎到店使用。";
		            SaleSmsClient.sendMessageForContent(mobile, content);//发送短信
		            
					ajaxJson.setMsg("领取成功");
					ajaxJson.setSuccess(true);
					ajaxJson.setObj(tpFullcutTemplate.get(selected));
				}else{
					//用户领取过新手礼包
					ajaxJson.setMsg("已领取过优惠券");
					ajaxJson.setSuccess(false);
				}
			}else{
				ajaxJson.setMsg("未绑定手机");
				ajaxJson.setSuccess(false);
			}
		}else{
			
			ajaxJson.setMsg("用户不存在");
			ajaxJson.setSuccess(false);
			
		}
		
		
		
		return ajaxJson;
	}
	

	
	/**
     * 根据Math.random()产生一个double型的随机数，判断每个奖品出现的概率
     * @param prizes
     * @return random：奖品列表prizes中的序列（prizes中的第random个就是抽中的奖品）
     */
    public int getPrizeIndex(List<TcsbPlatformFullcutTemplateEntity> prizes) {
        DecimalFormat df = new DecimalFormat("######0.00");  
        int random = -1;
        try{
            //计算总权重
            double sumWeight = 0;
            for(TcsbPlatformFullcutTemplateEntity p : prizes){
                sumWeight += Double.valueOf(p.getWeight());
            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;          
            for(int i=0;i<prizes.size();i++){
                d2 += Double.parseDouble(prizes.get(i).getWeight())/sumWeight;
                if(i==0){
                    d1 = 0;
                }else{
                    d1 +=Double.parseDouble(prizes.get(i-1).getWeight())/sumWeight;
                }
                if(randomNumber >= d1 && randomNumber <= d2){
                    random = i;
                    break;
                }
            }
        }catch(Exception e){
            System.out.println("生成抽奖随机数出错，出错原因：" +e.getMessage());
        }
        return random;
    } 
	//测试
	/*int[] result=new int[4];
	String[] resultname =new String[4];
	for(int i=0; i<10000; i++){
		int selected= getPrizeIndex(tpFullcutTemplate);
		result[selected]++;
		resultname[selected] = tpFullcutTemplate.get(selected).getTotal() + "减" +tpFullcutTemplate.get(selected).getDiscount();
	}
	System.out.println("抽奖结束");
    System.out.println("每种奖品抽到的数量为：");
    System.out.println(resultname[0]+":"+result[0]);
    System.out.println(resultname[1]+":"+result[1]);
    System.out.println(resultname[2]+":"+result[2]);
    System.out.println(resultname[3]+":"+result[3]); */
	//测试
	
}
