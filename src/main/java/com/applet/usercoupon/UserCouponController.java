/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月3日 下午2:39:53
 */
package com.applet.usercoupon;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jacob.com.DateUtilities;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.coupon.service.TcsbCouponServiceI;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.platformcoupon.entity.TcsbPlatformCouponEntity;
import com.tcsb.platformfullcuttemplate.entity.TcsbPlatformFullcutTemplateEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.weixin.util.DateUtils;
import com.weixin.weixinuser.entity.WeixinUserEntity;

import freemarker.template.utility.DateUtil;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月3日 下午2:39:53
 */
@Controller
@RequestMapping("/appletUserCoupon")
public class UserCouponController extends BaseController{
	
	
	@Autowired
	private TcsbCouponServiceI tcsbCouponService;
	/**
	 * 我的优惠券
	 * @param userId
	 * @param couponState 0未使用1已使用2已过期
	 * @return
	 */
	@RequestMapping("/myUserCoupon")
	@ResponseBody
	public AjaxJsonApi myUserCoupon(@RequestParam String userId,@RequestParam String couponState){
			AjaxJsonApi ajaxJson = new AjaxJsonApi();
			List<CouponVo> cpList = new ArrayList<>();
			CouponVo cp;
			if(couponState.isEmpty()){
				String hql = "from TcsbCouponEntity where userId='"+userId+"'";
				List<TcsbCouponEntity> tcsbCouponList = tcsbCouponService.findByQueryString(hql);
				for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponList) {
					cp = new CouponVo();
					cp.setCouponId(tcsbCouponEntity.getId());
					
					cp.setExpiryDate(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.date_sdf_wz));
					TcsbShopEntity tcsbShopEntity = tcsbCouponService.get(TcsbShopEntity.class, tcsbCouponEntity.getShopId());
					cp.setShopId(tcsbShopEntity.getId());
					cp.setShopName(tcsbShopEntity.getName());
					TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbCouponService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
					cp.setDiscount(tcsbFullcutTemplateEntity.getDiscount());
					cp.setTotal(tcsbFullcutTemplateEntity.getTotal());
					cp.setUseRangeCode(tcsbCouponEntity.getUseRange());
					cp.setUseRangeName("本商店");
					cpList.add(cp);
				}
			}else{
					String hql = "from TcsbCouponEntity where userId='"+userId+"' and useStatus='"+couponState+"' order by createDate desc";
					List<TcsbCouponEntity> tcsbCouponList = tcsbCouponService.findByQueryString(hql);

					for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponList) {
						cp = new CouponVo();
						cp.setCouponId(tcsbCouponEntity.getId());
						
						cp.setExpiryDate(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.date_sdf_wz));
						TcsbShopEntity tcsbShopEntity = tcsbCouponService.get(TcsbShopEntity.class, tcsbCouponEntity.getShopId());
						cp.setShopId(tcsbShopEntity.getId());
						cp.setShopName(tcsbShopEntity.getName());
						TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbCouponService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
						cp.setDiscount(tcsbFullcutTemplateEntity.getDiscount());
						cp.setTotal(tcsbFullcutTemplateEntity.getTotal());
						cp.setUseRangeCode(tcsbCouponEntity.getUseRange());
						cp.setUseRangeName("本商店");
						cpList.add(cp);
					}
					
					String platformhql = "from TcsbPlatformCouponEntity where userId='"+userId+"' and useStatus='"+couponState+"' order by createDate desc";
					List<TcsbPlatformCouponEntity> tcsbPlatformCouponList = tcsbCouponService.findByQueryString(platformhql);
					for (TcsbPlatformCouponEntity tcsbPlatformCouponEntity : tcsbPlatformCouponList) {
						cp = new CouponVo();
						cp.setCouponId(tcsbPlatformCouponEntity.getId());
						
						cp.setExpiryDate(DateUtils.date2Str(tcsbPlatformCouponEntity.getExpiryDate(),DateUtils.date_sdf_wz));
						//TcsbShopEntity tcsbShopEntity = tcsbCouponService.get(TcsbShopEntity.class, tcsbPlatformCouponEntity.getShopId());
						cp.setShopId("");
						cp.setShopName("点单么平台");
						TcsbPlatformFullcutTemplateEntity tcsbPlatformFullcutTemplateEntity = tcsbCouponService.get(TcsbPlatformFullcutTemplateEntity.class, tcsbPlatformCouponEntity.getFullcutTemplateId());
						cp.setDiscount(tcsbPlatformFullcutTemplateEntity.getDiscount());
						cp.setTotal(tcsbPlatformFullcutTemplateEntity.getTotal());
						cp.setUseRangeCode("");
						cp.setUseRangeName("点单么平台");
						cpList.add(cp);
					}

			}
			ajaxJson.setMsg("查询成功");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(cpList);		

		return ajaxJson;
	}

}
