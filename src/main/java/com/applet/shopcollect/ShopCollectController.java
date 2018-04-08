/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月3日 下午3:22:20
 */
package com.applet.shopcollect;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.applet.appletutil.LatLngUtil;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopcollect.entity.TcsbShopCollectEntity;
import com.tcsb.shopcollect.service.TcsbShopCollectServiceI;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月3日 下午3:22:20
 */
@Controller
@RequestMapping("/appletShopCollect")
public class ShopCollectController extends BaseController{
	@Autowired
	private TcsbShopCollectServiceI tcsbShopCollectService;
	
	/**
	 * 我的收藏
	 * @param userId
	 * @return
	 */
	@RequestMapping("/myShopCollect")
	@ResponseBody
	public AjaxJsonApi myShopCollect(@RequestParam String userId,String lat,String lng){
			AjaxJsonApi ajaxJson = new AjaxJsonApi();
			List<TcsbShopCollectEntity> tcsbShopCollectList = tcsbShopCollectService.findByProperty(TcsbShopCollectEntity.class, "userId", userId);
			List<ShopCollectVo> collectShopList = new ArrayList<>();
			ShopCollectVo shopCollectVo;
			if(tcsbShopCollectList.size()>0){
				for (TcsbShopCollectEntity tcsbShopCollect : tcsbShopCollectList) {
					shopCollectVo = new ShopCollectVo();

						TcsbShopEntity tcsbShop = tcsbShopCollectService.get(TcsbShopEntity.class, tcsbShopCollect.getShopId());
						if(tcsbShop!=null){
							TcsbShopEntity tcsbShopEntity = new TcsbShopEntity();
							try {
								BeanUtils.copyProperties(tcsbShopEntity, tcsbShop);
								//tcsbShopEntity.setHeadimg(getAppletCkPath()+tcsbShopEntity.getHeadimg().replace("images", "_thumbs/Images"));
								tcsbShopEntity.setHeadimg(getAppletCkPath()+tcsbShopEntity.getHeadimg());
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							shopCollectVo.setTcsbShop(tcsbShopEntity);
							//获取店铺优惠
							String hql = "from TcsbShopFullcutTemplateEntity where shopId=?";
							List<TcsbShopFullcutTemplateEntity> TcsbShopFullcutTemplateEntity = tcsbShopCollectService.findHql(hql, tcsbShopCollect.getShopId());
							if(StringUtil.isNotEmpty(TcsbShopFullcutTemplateEntity)){
								String promotion = "";
								for (TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity2 : TcsbShopFullcutTemplateEntity) {
									promotion = promotion + "满"+tcsbShopFullcutTemplateEntity2.getTotal()+"减"+tcsbShopFullcutTemplateEntity2.getDiscount()+",";
								}
								if(StringUtil.isNotEmpty(promotion)){
									promotion = promotion.substring(0, promotion.length()-1);
									shopCollectVo.setPromotion(promotion);
								}else{
									shopCollectVo.setPromotion("暂无优惠信息");
								}
							}else{
								shopCollectVo.setPromotion("暂无优惠信息");
							}


							//获取商家订单信息

							String orderhql = "from TcsbOrderEntity where shopId='"+tcsbShopCollect.getShopId()+"' and payStatus=1";
							List<TcsbOrderEntity> tcsbOrderEntity = tcsbShopCollectService.findByQueryString(orderhql);
							if(tcsbOrderEntity.size()>0){
								double allsale = 0.0;
								for (TcsbOrderEntity tcsbOrderEntity2 : tcsbOrderEntity) {
									allsale  = BigDecimalUtil.add(allsale, tcsbOrderEntity2.getTotalPrice());
								}
								shopCollectVo.setBuyPeopleNum(tcsbOrderEntity.size()+"");
								String avgsale = BigDecimalUtil.divide(allsale+"", tcsbOrderEntity.size()+"", 0);
								shopCollectVo.setAvgSale(avgsale);
							}else{
								shopCollectVo.setBuyPeopleNum("0");
								shopCollectVo.setAvgSale("0");
							}


							if(StringUtil.isNotEmpty(lng) && StringUtil.isNotEmpty(lat)){
								double distance = LatLngUtil.getDistance(Double.valueOf(lng), Double.valueOf(lat), Double.valueOf(tcsbShopEntity.getLongitude()), Double.valueOf(tcsbShopEntity.getLatitude()));

								if(distance > 1000){
									String dist = BigDecimalUtil.divide(distance+"", "1000", 2);
									shopCollectVo.setDistance(dist+"千米");
								}else{
									if(distance < 150){
										shopCollectVo.setDistance("200米");
									}else{
										shopCollectVo.setDistance(distance+"米");
									}
								}
							}else{
								shopCollectVo.setDistance("");
							}


							collectShopList.add(shopCollectVo);
							ajaxJson.setMsg("获取成功");
							ajaxJson.setSuccess(true);
							ajaxJson.setObj(collectShopList);
						}


				}
			}else{
				ajaxJson.setMsg("暂无店铺收藏");
				ajaxJson.setSuccess(false);
				ajaxJson.setObj(null);	
			}
		return ajaxJson;
	}
	
	/**
	 * 收藏店铺
	 * @param userId
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/addShopCollect")
	@ResponseBody
	public AjaxJsonApi addShopCollect(@RequestParam String userId,@RequestParam String shopId){
			AjaxJsonApi ajaxJson = new AjaxJsonApi();
			String hql = "from TcsbShopCollectEntity where shopId='"+shopId+"' and userId='"+userId+"'";
			TcsbShopCollectEntity tcsbShopCollect = tcsbShopCollectService.singleResult(hql);
			if(StringUtil.isNotEmpty(tcsbShopCollect)){
				if(tcsbShopCollect.getIsDel().equals("0")){
					ajaxJson.setMsg("已收藏店铺");
					ajaxJson.setSuccess(false);
					ajaxJson.setObj(null);	
				}else{
					tcsbShopCollect.setIsDel("0");
					try {
						tcsbShopCollectService.saveOrUpdate(tcsbShopCollect);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ajaxJson.setMsg("收藏成功");
					ajaxJson.setSuccess(true);
					ajaxJson.setObj(null);	
				}
			}else{
				TcsbShopCollectEntity tcsbShopCollectEntity =new TcsbShopCollectEntity();
				tcsbShopCollectEntity.setIsDel("0");
				tcsbShopCollectEntity.setShopId(shopId);
				tcsbShopCollectEntity.setUserId(userId);
				try {
					tcsbShopCollectService.save(tcsbShopCollectEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ajaxJson.setMsg("收藏成功");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(null);	
			}
		return ajaxJson;
	}
	
	
	/**
	 * 取消收藏店铺
	 * @param userId
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/cancleShopCollect")
	@ResponseBody
	public AjaxJsonApi cancleShopCollect(@RequestParam String userId,@RequestParam String shopId){
			AjaxJsonApi ajaxJson = new AjaxJsonApi();
			String hql = "from TcsbShopCollectEntity where shopId='"+shopId+"' and userId='"+userId+"' and isDel='0'";
			TcsbShopCollectEntity tcsbShopCollect = tcsbShopCollectService.singleResult(hql);
			if(StringUtil.isNotEmpty(tcsbShopCollect)){
				ajaxJson.setMsg("取消收藏店铺");
				tcsbShopCollect.setIsDel("1");
				try {
					tcsbShopCollectService.saveOrUpdate(tcsbShopCollect);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(null);	
			}else{
				ajaxJson.setMsg("未收藏该店铺");
				ajaxJson.setSuccess(false);
				ajaxJson.setObj(null);	
			}
		return ajaxJson;
	}
	
	/**
	 * 检测该店铺是否被收藏
	 * @param userId
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/checkShopCollect")
	@ResponseBody
	public AjaxJsonApi checkShopCollect(@RequestParam String userId,@RequestParam String shopId){
			AjaxJsonApi ajaxJson = new AjaxJsonApi();
			String hql = "from TcsbShopCollectEntity where shopId='"+shopId+"' and userId='"+userId+"' and isDel='0'";
			TcsbShopCollectEntity tcsbShopCollect = tcsbShopCollectService.singleResult(hql);
			if(StringUtil.isNotEmpty(tcsbShopCollect)){
				ajaxJson.setMsg("收藏店铺");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(null);
			}else{
				ajaxJson.setMsg("未收藏该店铺");
				ajaxJson.setSuccess(false);
				ajaxJson.setObj(null);	
			}
		return ajaxJson;
	}
}
