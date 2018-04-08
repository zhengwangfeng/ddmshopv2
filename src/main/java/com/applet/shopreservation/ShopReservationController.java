/**
 * ddmShopV2
 *
 * @author Mar_x
 * create on 2017 2017年11月8日 下午5:44:14
 */
package com.applet.shopreservation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.tcsbfoodunit.service.TcsbFoodUnitServiceI;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.applet.shopcar.TcsbShopCarItemVo;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import com.tcsb.depositdelta.service.TcsbDepositDeltaServiceI;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.desk.service.TcsbDeskServiceI;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsbdeskreservation.service.TcsbDeskReservationServiceI;
import com.tcsb.tcsbdeskreservationitem.entity.TcsbDeskReservationItemEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月8日 下午5:44:14
 */
@Controller
@RequestMapping("/shopReservation")
public class ShopReservationController extends BaseController {
    @Autowired
    private TcsbDeskReservationServiceI tcsbDeskReservationService;
    @Autowired
    private TcsbDeskServiceI tcsbDeskService;
    @Autowired
    private TcsbFoodUnitServiceI tcsbFoodUnitService;

    @Autowired
    private TcsbFoodServiceI tcsbFoodService;
    @Autowired
    private TcsbShopServiceI tcsbShopService;
    @Autowired
    private TcsbDepositDeltaServiceI tcsbDepositDeltaService;
    @Autowired
    private SystemService systemService;


    /**
     * 预约下单，并返回下单号
     *
     * @param shopId
     * @param sex
     * @param phone
     * @param nickname
     * @param shopcarItem
     * @param userId
     * @param peopleNum
     * @param eatTime
     * @param note
     * @return
     */
    @RequestMapping("/addReservation")
    @ResponseBody
    public AjaxJsonApi addReservation(String shopId, String sex, String phone, String nickname, String shopcarItem, String userId, String peopleNum, String eatTime, String note) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
/*
        String bb = "shopId:" + shopId + "sex:" + sex + "phone:" + phone + "nickname:" + nickname + "shopcarItem:" + shopcarItem + "userId:" +
                userId + "peopleNum:" + peopleNum + "eatTime:" + eatTime + "note:" + note;
        //搜索系统空闲的随机座位(桌位人数要当前的用餐我数)
        String sql = "select d.id from tcsb_desk d left join tcsb_shop s "
                + " on  d.shop_id = s.id "
                + " where s.id = ? and d.is_order = '0'";
        List<Map<String, Object>> tcsbEntities = tcsbDeskService.findForJdbc(sql, shopId);
        String deskId = "";
        if (tcsbEntities.isEmpty()) {
            ajaxJson.setMsg("没有空闲的预约桌位");
            ajaxJson.setSuccess(false);
        } else {*/
            /*for (Map<String, Object> map : tcsbEntities) {
                deskId = (String) map.get("id");
                break;
            }*/
            TcsbDeskReservationEntity deskReservation = new TcsbDeskReservationEntity();
            if (StringUtil.isNotEmpty(shopId) && StringUtil.isNotEmpty(phone) && StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(shopcarItem)) {
                //shopcarItem = "[{'foodId':'8a9ccf875d39d3cd015d3ebf2f3a030b','count':1,'currentPrice':true,'foodTaste':true}]";
                List<TcsbShopCarItemVo> tcsbShopCarItemList = JSON.parseArray(shopcarItem, TcsbShopCarItemVo.class);

                deskReservation.setNickname(nickname);
                deskReservation.setPhone(phone);
                deskReservation.setSex(sex);
                deskReservation.setNote(note);
                deskReservation.setTotalprice("0");
                deskReservation.setOnlinePrice("0");
                deskReservation.setPayStatus("0");
                deskReservation.setPlatformDiscountPrice(0.0);
                deskReservation.setUniversalCouponPrice(0.0);
                deskReservation.setSpecialCouponPrice(0.0);
                deskReservation.setShopId(shopId);
                deskReservation.setUserId(userId);
                deskReservation.setNum(Integer.parseInt(peopleNum));
                deskReservation.setStatus("1");
                //默认为无押金
                deskReservation.setIsDeposit("0");
                //deskReservation.setDeskId(deskId);
                
                    try {
						deskReservation.setOrderTime(DateUtils.parseDate(eatTime, "yyyy-MM-dd HH:mm"));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    try {
						tcsbDeskReservationService.save(deskReservation);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    //更新桌位为预约
                   /* TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.get(TcsbDeskEntity.class, deskId);
                    tcsbDeskEntity.setIsOrder("1");
                    tcsbDeskReservationService.saveOrUpdate(tcsbDeskEntity);*/

                try {
                    deskReservation.setOrderTime(DateUtils.parseDate(eatTime, "yyyy-MM-dd HH:mm"));
                } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    tcsbDeskReservationService.save(deskReservation);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //更新桌位为预约
              /*  TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.get(TcsbDeskEntity.class, deskId);
                tcsbDeskEntity.setIsOrder("1");
                tcsbDeskReservationService.saveOrUpdate(tcsbDeskEntity);*/

                for (TcsbShopCarItemVo tcsbShopCarItemVo : tcsbShopCarItemList) {
                    TcsbFoodEntity tcsbFood = tcsbDeskReservationService.get(TcsbFoodEntity.class, tcsbShopCarItemVo.getFoodId());
                    TcsbFoodUnitEntity unit = tcsbFoodUnitService.get(TcsbFoodUnitEntity.class, tcsbFood.getUnitId());
                    TcsbDeskReservationItemEntity reservationItem = new TcsbDeskReservationItemEntity();
                    //reservationItem.setFoodtastefun(tcsbShopCarItemVo.getFoodTasteFun());
                    reservationItem.setReservationId(deskReservation.getId());
                    reservationItem.setFoodId(tcsbShopCarItemVo.getFoodId());
                    reservationItem.setIsSetMeal(tcsbFood.getIsSetMeal());
                    if (StringUtil.isNotEmpty(tcsbShopCarItemVo.getStandardId())) {
                        TcsbFoodStandardEntity tfs = tcsbFoodUnitService.get(TcsbFoodStandardEntity.class, tcsbShopCarItemVo.getStandardId());
                        if (StringUtil.isNotEmpty(tfs)) {
                            reservationItem.setPrice(tfs.getPrice());
                            reservationItem.setFoodtastefun(tcsbShopCarItemVo.getFoodTasteFun() + "/" + tfs.getName());
                        } else {

                            reservationItem.setFoodtastefun(tcsbShopCarItemVo.getFoodTasteFun());
                            reservationItem.setPrice(tcsbFood.getPrice());
                        }

                    } else if (StringUtil.isNotEmpty(tcsbFood)) {
                        reservationItem.setFoodtastefun(tcsbShopCarItemVo.getFoodTasteFun());
                        reservationItem.setPrice(tcsbFood.getPrice());
                    }
                    //reservationItem.setPrice(tcsbFood.getPrice());
                    reservationItem.setFoodName(tcsbFood.getName());
                    reservationItem.setUnitName(unit.getName());
                    if (tcsbShopCarItemVo.isCurrentPrice()) {
                        //时价菜品
                        if (tcsbShopCarItemVo.getCount() == 1) {
                            reservationItem.setCount(1);
                        } else {
                            for (int i = 0; i < tcsbShopCarItemVo.getCount(); i++) {
                                reservationItem.setCount(1);
                            }
                        }
                        tcsbDeskReservationService.save(reservationItem);
                    } else {
                        //非时价菜品
                        if (tcsbShopCarItemVo.isFoodTaste()) {
                            //菜品有规格
                            reservationItem.setCount(1);
                            tcsbDeskReservationService.save(reservationItem);
                        } else {
                            //菜品无规格
                            //此处做非时价商品
                            //直接添加
                            String hql = "from TcsbDeskReservationItemEntity where foodId='" + tcsbShopCarItemVo.getFoodId() + "' and reservationId='" + deskReservation.getId() + "'";
                            TcsbDeskReservationItemEntity addtcsbShopCarItem = tcsbDeskReservationService.singleResult(hql);
                            if (StringUtil.isNotEmpty(addtcsbShopCarItem)) {
                                //addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount()+tcsbShopCarItemVo.getNum());
                                addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount() + tcsbShopCarItemVo.getCount());
                                tcsbDeskReservationService.saveOrUpdate(addtcsbShopCarItem);
                            } else {
                                reservationItem.setCount(tcsbShopCarItemVo.getCount());
                                tcsbDeskReservationService.save(reservationItem);
                            }
                        }
                    }
                }
                ajaxJson.setMsg("预定成功");
                ajaxJson.setObj(deskReservation.getId());
                ajaxJson.setSuccess(true);

            } else {
                //String aa = "shopId:" + shopId + "sex:" + sex + "phone:" + phone + "nickname:" + nickname + "shopcarItem:" + shopcarItem + "userId:" +
                // userId + "peopleNum:" + peopleNum + "eatTime:" + eatTime + "note:" + note;
                ajaxJson.setMsg("预定失败");
                ajaxJson.setObj(null);
                ajaxJson.setSuccess(false);
            }

       // }
        return ajaxJson;

    }

//    /**
//     * 更改预订单退款状态
//     *
//     * @param reservationId
//     * @return
//     */
//    @RequestMapping("/reservationRefund")
//    @ResponseBody
//    public AjaxJsonApi reservationRefund(String reservationId) {
//        AjaxJsonApi ajaxJosn = new AjaxJsonApi();
//        ReservationInfoVo reservationInfoVo = new ReservationInfoVo();
//        TcsbDeskReservationEntity tcsbDeskReservation = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
//        try {
//            TcsbDeskReservationEntity tcsbDeskReservationReturn = tcsbDeskReservationService.updateRefund(tcsbDeskReservation);
//            MyBeanUtils.copyBeanNotNull2Bean(tcsbDeskReservationReturn,reservationInfoVo);
//        } catch (Exception e) {
//
//        }
//        ajaxJosn.setMsg("请求成功");
//        ajaxJosn.setObj(reservationInfoVo);
//        ajaxJosn.setSuccess(true);
//        return ajaxJosn;
//    }

    /**
     * 根据预定订单ID获取订单信息
     * @param reservationId
     * @return
     */
    @RequestMapping("/getReservation")
    @ResponseBody
    public AjaxJsonApi getReservation(String reservationId) {
        AjaxJsonApi ajaxJosn = new AjaxJsonApi();
        ReservationInfoVo reservationInfoVo = new ReservationInfoVo();    
        TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
        List<TcsbDeskReservationItemEntity> tcsbDeskReservationItemList = tcsbDeskReservationService.findByProperty(TcsbDeskReservationItemEntity.class, "reservationId", reservationId);
		reservationInfoVo.setTcsbOrderItemArray(tcsbDeskReservationItemList);
		Double totalMoney = 0.0;
		Double nodisMoney = 0.0;
		Double finallyMoney = 0.0;
		Double alreadyPaid =0.0;
		Double needNeedMoney = 0.0;
		//未付款算优惠信息
		if ("0".equals(tcsbDeskReservationEntity.getPayStatus())) {
			//算出具体的价钱
			for (TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity : tcsbDeskReservationItemList) {
				TcsbFoodEntity tcsbFoodEntity = tcsbFoodService.get(TcsbFoodEntity.class, tcsbDeskReservationItemEntity.getFoodId());
				Double money =0.0;
				money = BigDecimalUtil.mul(tcsbDeskReservationItemEntity.getPrice(), tcsbDeskReservationItemEntity.getCount());
				totalMoney = BigDecimalUtil.add(totalMoney, money);
			}
			finallyMoney = totalMoney;
			//打折情况
			//获取优惠活动
			List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = systemService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", tcsbDeskReservationEntity.getShopId());
			if (!tcsbDiscountActivityEntities.isEmpty()) {
				List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
				for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
					TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
					tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
				}
				if (!tcsbDiscountActivityEntities.isEmpty()) {
					ListSorter.sort(tcsbShopFullcutTemplateEntities, "total","discount");
					for (int i = tcsbShopFullcutTemplateEntities.size()-1; i >= 0; i--) {
						if (finallyMoney>=tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
							finallyMoney = BigDecimalUtil.sub(finallyMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
							tcsbDeskReservationEntity.setUniversalCouponPrice(Double.valueOf(tcsbShopFullcutTemplateEntities.get(i).getDiscount()));
							reservationInfoVo.setShopUserDiscountMsg(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + "");
							if(finallyMoney<0){
								finallyMoney=0.01;
							}
							break;
						}
					}
				}
			}
			//获取优惠券信息
			List<TcsbCouponEntity> tcsbCouponEntities = systemService.findHql("from TcsbCouponEntity where useStatus='0' and  shopId = ? and userId = ?", tcsbDeskReservationEntity.getShopId(),tcsbDeskReservationEntity.getUserId());
			List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
			if (!tcsbCouponEntities.isEmpty()) {
				for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
					TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
					Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();//使用期限
					String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); //日期单位
					//专用券中有效的满减活动
					Calendar calDes = null;
					try {
						calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Calendar calSrc = null;
					try {
						calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (DateUtils.dateDiff('s', calSrc, calDes)<0) {
						tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity.getId());
						tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
					};
				}
			}
			//立减
			if (!tcsbFullcutTemplateEntities.isEmpty()) {
				ListSorter.sort(tcsbFullcutTemplateEntities, "total","discount");
				outer:for (int i = tcsbFullcutTemplateEntities.size()-1; i >= 0; i--) {
					TcsbCouponEntity tcsbCouponEntity = systemService.get(TcsbCouponEntity.class, tcsbFullcutTemplateEntities.get(i).getCouponId());
					if(tcsbCouponEntity.getUseRange().equals("0")){
						if (finallyMoney>=tcsbFullcutTemplateEntities.get(i).getTotal()) {
							finallyMoney = BigDecimalUtil.sub(finallyMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
							tcsbDeskReservationEntity.setSpecialCouponPrice(Double.valueOf(tcsbFullcutTemplateEntities.get(i).getDiscount()));
							reservationInfoVo.setUniversalCouponPrice(tcsbFullcutTemplateEntities.get(i).getDiscount() + "");
							if(finallyMoney<0){
								finallyMoney=0.01;
							}
							break outer;
						}
					}else{
						//查找订单项中是否有该道菜
						/*TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbCouponEntity.getFoodId());
						for (int j = 0; j < tcsbShopCarItemEntities.size(); j++) {
							if (tcsbFoodEntity.getId().equals(tcsbShopCarItemEntities.get(j).getFoodId())) {
								payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
								if(payMoney<0){
									payMoney=0.01;
								}
								break outer;
							}
						}*/
					}
				}
				
			}
			try {
				tcsbDeskReservationEntity.setTotalprice(BigDecimalUtil.numericRetentionDecimal(totalMoney, 2));
				tcsbDeskReservationEntity.setOnlinePrice(BigDecimalUtil.numericRetentionDecimal(finallyMoney, 2));
				tcsbDeskReservationService.saveOrUpdate(tcsbDeskReservationEntity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reservationInfoVo.setReservation(tcsbDeskReservationEntity);
			//有付押金的情况
			if ("1".equals(tcsbDeskReservationEntity.getIsDeposit())) {
					TcsbDepositDeltaEntity  tcsbDepositDeltaEntity = tcsbDepositDeltaService.get(TcsbDepositDeltaEntity.class, tcsbDeskReservationEntity.getDepositDeltaId());
					//已付押金
					reservationInfoVo.setDepositeMoney(BigDecimalUtil.numericRetentionDecimal(tcsbDepositDeltaEntity.getReserveDeposit(), 2));
					Double need =BigDecimalUtil.sub(Double.valueOf(tcsbDeskReservationEntity.getOnlinePrice()),tcsbDepositDeltaEntity.getReserveDeposit());
					//需付款
					reservationInfoVo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(need, 2));
					reservationInfoVo.setAlreadyPaid(BigDecimalUtil.numericRetentionDecimal(tcsbDepositDeltaEntity.getReserveDeposit(), 2));
					//总金额
					reservationInfoVo.setFinalMoney(tcsbDeskReservationEntity.getTotalprice());
					if (StringUtil.isNotEmpty(tcsbDeskReservationEntity.getSpecialCouponPrice())) {
						reservationInfoVo.setShopUserDiscountMsg(BigDecimalUtil.numericRetentionDecimal(tcsbDeskReservationEntity.getSpecialCouponPrice(), 2));
					}else {
						reservationInfoVo.setShopUserDiscountMsg("0");
					}
					if (StringUtil.isNotEmpty(tcsbDeskReservationEntity.getUniversalCouponPrice())) {
						reservationInfoVo.setUniversalCouponPrice(BigDecimalUtil.numericRetentionDecimal(tcsbDeskReservationEntity.getUniversalCouponPrice(), 2));
					}else {
						reservationInfoVo.setUniversalCouponPrice("0");
					}
				}
		}else {
			//直接拿
			//已付押金
			reservationInfoVo.setDepositeMoney("0");
			//需付款
			reservationInfoVo.setNeedPayMoney("0");
			reservationInfoVo.setAlreadyPaid(tcsbDeskReservationEntity.getOnlinePrice());
			//总金额
			reservationInfoVo.setFinalMoney(tcsbDeskReservationEntity.getTotalprice());
			reservationInfoVo.setShopUserDiscountMsg(BigDecimalUtil.numericRetentionDecimal(tcsbDeskReservationEntity.getSpecialCouponPrice(), 2));
			reservationInfoVo.setUniversalCouponPrice(BigDecimalUtil.numericRetentionDecimal(tcsbDeskReservationEntity.getUniversalCouponPrice(), 2));
		}
		reservationInfoVo.setVipDisCount("0.0");
    	reservationInfoVo.setReservation(tcsbDeskReservationEntity);
    	
    	if(StringUtil.isNotEmpty(tcsbDeskReservationEntity)){
    		if(StringUtil.isNotEmpty(tcsbDeskReservationEntity.getDeskId())){
    			TcsbDeskEntity desk = tcsbDeskReservationService.get(TcsbDeskEntity.class, tcsbDeskReservationEntity.getDeskId());
    			reservationInfoVo.setReservationDeskName(desk.getDeskName());
    		}
    	}
    	
    	
        ajaxJosn.setMsg("请求成功");
        ajaxJosn.setObj(reservationInfoVo);
        ajaxJosn.setSuccess(true);

        return ajaxJosn;
    }

    /***
     * 获取我的预定订单
     * @param userId
     * @return
     */
    @RequestMapping("/getMyReservation")
    @ResponseBody
    public AjaxJsonApi getMyReservation(String userId){
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        List<MyReservationVo> reservationInfoList = new ArrayList<>();
        MyReservationVo reservationInfoVo;
        String hql = "from TcsbDeskReservationEntity where userId='"+userId+"' and status = '1'";
        List<TcsbDeskReservationEntity> tcsbDeskReservationList = tcsbDeskReservationService.findByQueryString(hql);
        for (TcsbDeskReservationEntity tcsbDeskReservationEntity : tcsbDeskReservationList) {
            reservationInfoVo = new MyReservationVo();
            reservationInfoVo.setReservationid(tcsbDeskReservationEntity.getId());
            if(tcsbDeskReservationEntity.getIsDeposit().equals("1")){
                reservationInfoVo.setDeposit(true);
            }else{
                reservationInfoVo.setDeposit(false);
            }
            reservationInfoVo.setStates(Integer.valueOf(tcsbDeskReservationEntity.getStatus()));
            TcsbShopEntity tp = tcsbDeskReservationService.get(TcsbShopEntity.class, tcsbDeskReservationEntity.getShopId());

            if(StringUtil.isNotEmpty(tp)){
                reservationInfoVo.setShopName(tp.getName());
                reservationInfoVo.setShopHeadImg(getAppletCkPath()+tp.getHeadimg().replace("images", "_thumbs/Images"));
            }
            List<TcsbDeskReservationItemEntity> tcsbOrderItemArray = tcsbDeskReservationService.findByProperty(TcsbDeskReservationItemEntity.class, "reservationId", tcsbDeskReservationEntity.getId());
            double allMoney = 0.0;
            List<ReServationItemVo> itemVoList = new ArrayList<>();
            ReServationItemVo itemVo;
            if(tcsbOrderItemArray.size()>3){
                for (TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity : tcsbOrderItemArray) {
                    double thisMoney = BigDecimalUtil.mul(tcsbDeskReservationItemEntity.getPrice(), tcsbDeskReservationItemEntity.getCount());
                    allMoney = BigDecimalUtil.add(allMoney, thisMoney);
                }
                for(int i=0;i<3;i++){
                    TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity = tcsbOrderItemArray.get(i);
                    itemVo = new ReServationItemVo();
                    itemVo.setCount(tcsbDeskReservationItemEntity.getCount()+"");
                    itemVo.setFoodName(tcsbDeskReservationItemEntity.getFoodName());
                    itemVoList.add(itemVo);
                }
            }else{
                for (TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity : tcsbOrderItemArray) {
                    double thisMoney = BigDecimalUtil.mul(tcsbDeskReservationItemEntity.getPrice(), tcsbDeskReservationItemEntity.getCount());
                    allMoney = BigDecimalUtil.add(allMoney, thisMoney);
                    itemVo = new ReServationItemVo();
                    itemVo.setCount(tcsbDeskReservationItemEntity.getCount()+"");
                    itemVo.setFoodName(tcsbDeskReservationItemEntity.getFoodName());
                    itemVoList.add(itemVo);
                }
            }
            reservationInfoVo.setAllMoney(BigDecimalUtil.numericRetentionDecimal(allMoney, 2));
            reservationInfoVo.setAllcount(tcsbOrderItemArray.size()+"");
            reservationInfoVo.setItemVo(itemVoList);
            reservationInfoList.add(reservationInfoVo);
        }
        ajaxJson.setMsg("请求成功");
        ajaxJson.setObj(reservationInfoList);
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }


    /**
     * 检测是否有存在未支付的预定单
     * @param userId
     * @param shopId
     * @return
     */
    @RequestMapping("/checkResevation")
    @ResponseBody
    public AjaxJsonApi checkResevation(String userId,String shopId){
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        String reshql = "from TcsbDeskReservationEntity where shopId='"+shopId+"' and userId='"+userId+"' and isDeposit='0'";
        TcsbDeskReservationEntity tres =  tcsbDeskReservationService.singleResult(reshql);
        if(StringUtil.isNotEmpty(tres)){
            ajaxJson.setMsg("存在未支付的预定单");
            ajaxJson.setObj(tres.getId());
            ajaxJson.setSuccess(true);
        }else{
            ajaxJson.setMsg("不存在未支付的预定单");
            ajaxJson.setObj(null);
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


}
