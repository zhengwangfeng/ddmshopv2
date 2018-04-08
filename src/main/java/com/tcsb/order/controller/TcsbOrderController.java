package com.tcsb.order.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ListSorter;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.OrderNumberGenerateUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.RoletoJson;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.desk.service.TcsbDeskServiceI;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.discountactivity.service.TcsbDiscountActivityServiceI;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.fullcuttemplate.service.TcsbFullcutTemplateServiceI;
import com.tcsb.order.VO.OrderStatisticVo;
import com.tcsb.order.VO.TcsbOrderItemPrintVO;
import com.tcsb.order.VO.TcsbOrderItemVO;
import com.tcsb.order.VO.TcsbOrderPrintVO;
import com.tcsb.order.VO.TcsbOrderVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.page.TcsbOrderPage;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.platformdiscount.service.TcsbPlatformDiscountServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopevaluate.entity.TcsbShopEvaluateEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopfullcuttemplate.service.TcsbShopFullcutTemplateServiceI;
import com.tcsb.tcsbassociatorbigdata.entity.TcsbAssociatorBigdataEntity;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.tcsborderbigdatarecord.entity.TcsbOrderBigdataRecordEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.tcsb.userorder.service.TcsbUserOrderServiceI;
import com.weixin.weixinuser.entity.WeixinUserEntity;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 订单管理
 * @date 2017-03-08 10:41:50
 */
@Controller
@RequestMapping("/tcsbOrderController")
public class TcsbOrderController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TcsbOrderController.class);

    @Autowired
    private TcsbOrderServiceI tcsbOrderService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;
    @Autowired
    private TcsbShopServiceI tcsbShopService;
    @Autowired
    private TcsbPlatformDiscountServiceI tcsbPlatformDiscountService;
    @Autowired
    private TcsbDiscountActivityServiceI tcsbDiscountActivityService;
    @Autowired
    private TcsbFullcutTemplateServiceI tcsbFullcutTemplateService;
    @Autowired
    private TcsbFoodServiceI tcsbFoodService;
    @Autowired
    private TcsbDeskServiceI tcsbDeskService;
    @Autowired
    private TcsbUserOrderServiceI tcsbUserOrderService;
    @Autowired
    private TcsbShopFullcutTemplateServiceI tcsbShopFullcutTemplateService;

    /**
     * 订单管理列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        List<TcsbShopEntity> tcsbShopEntities = tcsbShopService.getList(TcsbShopEntity.class);
        //request.setAttribute("tcsbShopEntities", RoletoJson.listToReplaceStr(tcsbShopEntities, "name", "id"));
        String shopReplace = "";
        for (TcsbShopEntity tcsbShopEntity : tcsbShopEntities) {
            if (shopReplace.length() > 0) {
                shopReplace += ",";
            }
            shopReplace += tcsbShopEntity.getName() + "_"
                    + tcsbShopEntity.getId();
        }
        request.setAttribute("shopReplace", shopReplace);
        //如果是admin用户展示店铺选择
        if (checkAdmin()) {
            request.setAttribute("isAdmin", true);
        }

		
		
		/*List<TcsbOrderEntity> ooo = tcsbShopService.getList(TcsbOrderEntity.class);*/

		/*for (TcsbOrderEntity tcsbOrderEntity : ooo) {
			if(tcsbOrderEntity.getDeskId().length()<32){
				System.out.println(tcsbOrderEntity.getDeskId());
			}
		}*/


        return new ModelAndView("com/tcsb/order/tcsbOrderList");
    }

    /**
     * 订单管理列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "orderList")
    public ModelAndView orderList(HttpServletRequest request) {
        List<TcsbShopEntity> tcsbShopEntities = tcsbShopService.getList(TcsbShopEntity.class);
        //request.setAttribute("tcsbShopEntities", RoletoJson.listToReplaceStr(tcsbShopEntities, "name", "id"));
        String shopReplace = "";
        for (TcsbShopEntity tcsbShopEntity : tcsbShopEntities) {
            if (shopReplace.length() > 0) {
                shopReplace += ",";
            }
            shopReplace += tcsbShopEntity.getName() + "_"
                    + tcsbShopEntity.getId();
        }
        request.setAttribute("shopReplace", shopReplace);
        //如果是admin用户展示店铺选择
        if (checkAdmin()) {
            request.setAttribute("isAdmin", true);
        }
        String orderParentId = request.getParameter("orderParentId");
        request.setAttribute("orderParentId", orderParentId);
		
		/*List<TcsbOrderEntity> ooo = tcsbShopService.getList(TcsbOrderEntity.class);*/
		
		/*for (TcsbOrderEntity tcsbOrderEntity : ooo) {
			if(tcsbOrderEntity.getDeskId().length()<32){
				System.out.println(tcsbOrderEntity.getDeskId());
			}
		}*/


        return new ModelAndView("com/tcsb/order/tcsbOrderByParentList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TcsbOrderEntity tcsbOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
        tcsbOrder.setDeskId(null);

        CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
        try {
            TSUser user = getCurrentUser();
            if (!checkAdmin()) {
                cq.add(Restrictions.eq("shopId", user.getShopId()));
            }
            //有父订单ID的情况

            //自定义追加查询条件
            cq.addOrder("updateDate", SortDirection.desc);
            String query_createTime_begin = request.getParameter("createTime_begin");
            String query_createTime_end = request.getParameter("createTime_end");
            if (StringUtil.isNotEmpty(query_createTime_begin)) {
                cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_begin));
            }
            if (StringUtil.isNotEmpty(query_createTime_end)) {
                cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_end));
            }


            String deskId = request.getParameter("deskId");
            if (StringUtil.isNotEmpty(deskId)) {
                CriteriaQuery deskcq = new CriteriaQuery(TcsbDeskEntity.class);
                deskcq.add(Restrictions.like("deskName", "%" + deskId + "%"));
                List<TcsbDeskEntity> deskList = tcsbShopService.getListByCriteriaQuery(deskcq, false);
                if (deskList.size() > 0) {

                    Set set = new HashSet<>();
                    for (TcsbDeskEntity object : deskList) {
                        set.add(object.getNumber());
                    }
                    cq.add(Restrictions.in("deskId", set));
                }
            }


        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tcsbOrderService.getDataGridReturn(cq, true);
        //TODO
        /**
         * 添加订单显示桌位号
         * Mar_x
         *
         */
        List<TcsbOrderEntity> TcsbOrderList = dataGrid.getResults();
        List<TcsbOrderEntity> TcsbOrderListVo = new ArrayList<>();
        TcsbOrderEntity outTcsbOrder;
        for (TcsbOrderEntity tcsbOrderEntity : TcsbOrderList) {
            outTcsbOrder = new TcsbOrderEntity();
            BeanUtils.copyProperties(outTcsbOrder, tcsbOrderEntity);
            //已支付
            if (tcsbOrderEntity.getPayStatus().equals("1")) {
                if (tcsbOrderEntity.getPayMethod().equals("1")) {
                    outTcsbOrder.setOfflinePrice(outTcsbOrder.getTotalPrice());
                }
                if (tcsbOrderEntity.getPayMethod().equals("0")) {
                    outTcsbOrder.setOnlinePrice(outTcsbOrder.getTotalPrice());
                }
            }

            if (StringUtil.isNotEmpty(tcsbOrderEntity.getDeskId())) {
                TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class, tcsbOrderEntity.getOrderParentId());
                outTcsbOrder.setPeople(tcsbOrderParentEntity.getPeople());
                if (StringUtils.isNotEmpty(tcsbOrderParentEntity.getDeskId())) {
                    TcsbDeskEntity tcsbDeskEntity = tcsbOrderService.get(TcsbDeskEntity.class, tcsbOrderParentEntity.getDeskId());
                    if (StringUtil.isNotEmpty(tcsbDeskEntity.getDeskName())) {
                        outTcsbOrder.setDeskName(tcsbDeskEntity.getDeskName());
                    }
                }
            }
            TcsbOrderListVo.add(outTcsbOrder);

        }

        dataGrid.setResults(TcsbOrderListVo);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(params = "datagridByParent")
    public void datagridByParent(TcsbOrderEntity tcsbOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
        System.out.println(tcsbOrder.getOrderParentId());
        tcsbOrder.setDeskId(null);

        CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
        try {
            TSUser user = getCurrentUser();
            if (!checkAdmin()) {
                cq.add(Restrictions.eq("shopId", user.getShopId()));
            }
            //有父订单ID的情况

            //自定义追加查询条件
            cq.addOrder("updateDate", SortDirection.desc);
            String query_createTime_begin = request.getParameter("createTime_begin");
            String query_createTime_end = request.getParameter("createTime_end");
            if (StringUtil.isNotEmpty(query_createTime_begin)) {
                cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_begin));
            }
            if (StringUtil.isNotEmpty(query_createTime_end)) {
                cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_end));
            }


            String deskId = request.getParameter("deskId");
            if (StringUtil.isNotEmpty(deskId)) {
                CriteriaQuery deskcq = new CriteriaQuery(TcsbDeskEntity.class);
                deskcq.add(Restrictions.like("deskName", "%" + deskId + "%"));
                List<TcsbDeskEntity> deskList = tcsbShopService.getListByCriteriaQuery(deskcq, false);
                if (deskList.size() > 0) {

                    Set set = new HashSet<>();
                    for (TcsbDeskEntity object : deskList) {
                        set.add(object.getNumber());
                    }
                    cq.add(Restrictions.in("deskId", set));
                }
            }


        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tcsbOrderService.getDataGridReturn(cq, true);
        //TODO
        /**
         * 添加订单显示桌位号
         * Mar_x
         *
         */
        List<TcsbOrderEntity> TcsbOrderList = dataGrid.getResults();
        List<TcsbOrderEntity> TcsbOrderListVo = new ArrayList<>();
        TcsbOrderEntity TcsbOrder;
        for (TcsbOrderEntity tcsbOrderEntity : TcsbOrderList) {
            TcsbOrder = new TcsbOrderEntity();
            BeanUtils.copyProperties(TcsbOrder, tcsbOrderEntity);

            if (StringUtil.isNotEmpty(tcsbOrderEntity.getDeskId())) {
                TcsbDeskEntity TcsbDes = tcsbOrderService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbOrderEntity.getDeskId());
                if (StringUtil.isNotEmpty(TcsbDes)) {
                    TcsbOrder.setDeskId(TcsbDes.getDeskName());
                }
            }
            TcsbOrderListVo.add(TcsbOrder);

        }

        dataGrid.setResults(TcsbOrderListVo);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 营业收入列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "income")
    public ModelAndView incomt(HttpServletRequest request) {
        return new ModelAndView("com/tcsb/incomestatistics/tcsbIncomeStatisticsList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */

/*	@RequestMapping(params = "incomedatagrid")
	public void incomedatagrid(OrderStatisticVo tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		String startTime = "2017-05-08";
		String endTime = "2017-05-18";
		//总支付
		String sql = "SELECT DATE_FORMAT( create_time, '%Y-%m-%d' ) as create_time , sum( total_price ) as total_price,"
				+"sum( dis_price ) as dis_price,sum(universal_coupon_price) as universal,sum(special_coupon_price) as special,"
				+"sum(platform_discount_price) as platform"
				+ " FROM tcsb_order where ((pay_method = 0 and pay_status = 1) or pay_method = 1 )and create_time BETWEEN '"+startTime
				+"' AND '"+endTime
				+"' GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' ) ";
		List<Object> ovoList1 = tcsbOrderService.findListbySql(sql);
		//线上支付
		String sql2 = "SELECT DATE_FORMAT( create_time, '%Y-%m-%d' ) as create_time , sum( total_price ) as total_price,"
				+"sum( dis_price ) as dis_price"
				+ " FROM tcsb_order where pay_method = 0 and pay_status = 1 and create_time BETWEEN '"+startTime
				+"' AND '"+endTime
				+"' GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' ) ";
		List<Object> ovoList2 = tcsbOrderService.findListbySql(sql2);
		//线下支付
		String sql3 = "SELECT DATE_FORMAT( create_time, '%Y-%m-%d' ) as create_time , sum( total_price ) as total_price,"
				+"sum( dis_price ) as dis_price"
				+ " FROM tcsb_order where pay_method = 1 and create_time BETWEEN '"+startTime
				+"' AND '"+endTime
				+"' GROUP BY DATE_FORMAT( create_date, '%Y-%m-%d' ) ";
		List<Object> ovoList3 = tcsbOrderService.findListbySql(sql3);
		OrderStatisticVo ovo;
		List<OrderStatisticVo> ovoDto1 = new ArrayList<>();
		System.out.println(ovoList1.size());
	     for(Iterator iterator = ovoList1.iterator();iterator.hasNext();){ 
	    	 ovo = new OrderStatisticVo();
	            Object[] objects = (Object[]) iterator.next(); 
	            ovo.setCreatedate(objects[0].toString());//订单时间
	            ovo.setTotalprice(objects[1].toString());//营业额
	            ovo.setDisprice(objects[2].toString());//实际收款
	            ovo.setUniversalcouponprice(objects[3].toString());
	            ovo.setSpecialcouponprice(objects[4].toString());
	            ovo.setPlatformdiscountprice(objects[5].toString());//平台优惠
	            ovoDto1.add(ovo);
	        } 
	     
	     List<OrderStatisticVo> ovoDto2 = new ArrayList<>();
	     for(Iterator iterator = ovoList2.iterator();iterator.hasNext();){ 
	    	 ovo = new OrderStatisticVo();
	            //每个集合元素都是一个数组，数组元素师person_id,person_name,person_age三列值 
	            Object[] objects = (Object[]) iterator.next(); 
	            ovo.setCreatedate(objects[0].toString());//订单时间
	            ovo.setTotalprice(objects[1].toString());//线上支付
	            ovo.setDisprice(objects[2].toString());//线上实际收款
	            ovoDto2.add(ovo);
	        } 
	     
	     List<OrderStatisticVo> ovoDto3 = new ArrayList<>();
	     for(Iterator iterator = ovoList3.iterator();iterator.hasNext();){ 
	    	 ovo = new OrderStatisticVo();
	            //每个集合元素都是一个数组，数组元素师person_id,person_name,person_age三列值 
	            Object[] objects = (Object[]) iterator.next(); 
	            ovo.setCreatedate(objects[0].toString());//订单时间
	            ovo.setTotalprice(objects[1].toString());//线下支付
	            ovo.setDisprice(objects[2].toString());//线下实际收款
	            ovoDto3.add(ovo);
	        } 
	     System.out.println(ovoDto2);
	     for (OrderStatisticVo orderStatisticVo : ovoDto2) {
	    	 for (int i = 0; i < ovoDto1.size(); i++) {
				if(ovoDto1.get(i).getCreatedate().equals(orderStatisticVo.getCreatedate())){
					//这个时间段有线上实际支付款
					ovoDto1.get(i).setOnlinePayment(orderStatisticVo.getDisprice());
					ovoDto1.get(i).setPlatformSettlement(orderStatisticVo.getDisprice());
				}
			}
	     }
	     for (OrderStatisticVo orderStatisticVo : ovoDto3) {
	    	 for (int i = 0; i < ovoDto1.size(); i++) {
				if(ovoDto1.get(i).getCreatedate().equals(orderStatisticVo.getCreatedate())){
					//这个时间段有线下支付款
					ovoDto1.get(i).setOfflinePayment(orderStatisticVo.getDisprice());
				}
			}
	     }
	     
	     double totalprice = 0.00;
	     double onlinePayment = 0.00;
	     double offlinePayment = 0.00;
	     double disPrice1 = 0.00;
	     double disPrice2 = 0.00;
	     double disPrice3 = 0.00;
	     double disprice = 0.00;
	     double platformSettlement = 0.00;
	     
	     for (OrderStatisticVo orderStatisticVo : ovoDto1) {
	    	 	totalprice += Double.valueOf(orderStatisticVo.getTotalprice()); 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOnlinePayment())){
	    		 onlinePayment += 0;  
	    	 }else{
	    		 onlinePayment += Double.valueOf(orderStatisticVo.getOnlinePayment());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinePayment())){
	    		 offlinePayment += 0;  
	    	 }else{
	    		 offlinePayment += Double.valueOf(orderStatisticVo.getOfflinePayment());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getDisprice())){
	    		 disprice += 0;  
	    	 }else{
	    		 disprice += Double.valueOf(orderStatisticVo.getDisprice());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getPlatformSettlement())){
	    		 platformSettlement += 0;  
	    	 }else{
	    		 platformSettlement += Double.valueOf(orderStatisticVo.getPlatformSettlement());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getUniversalcouponprice())){
	    		 disPrice1 += 0;  
	    	 }else{
	    		 disPrice1 += Double.valueOf(orderStatisticVo.getUniversalcouponprice());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getSpecialcouponprice())){
	    		 disPrice2 += 0;  
	    	 }else{
	    		 disPrice2 += Double.valueOf(orderStatisticVo.getSpecialcouponprice());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getPlatformdiscountprice())){
	    		 disPrice3 += 0;  
	    	 }else{
	    		 disPrice3 += Double.valueOf(orderStatisticVo.getPlatformdiscountprice());  
	    	 }
	    	 
		}
	   
	     
	     dataGrid.setFooter("id:<font color ='black'>统计</font>,"+
	    		 "totalprice:<font color ='red'>"+totalprice+"</font>,"+
	    		 "onlinePayment:<font color ='red'>"+onlinePayment+"</font>,"+
	    		 "offlinePayment:<font color ='red'>"+offlinePayment+"</font>,"+
	    		 "universalcouponprice:<font color ='red'>"+disPrice1+"</font>,"+
	    		 "specialcouponprice:<font color ='red'>"+disPrice2+"</font>,"+
	    		 "platformdiscountprice:<font color ='red'>"+disPrice3+"</font>,"+
	    		 "disprice:<font color ='red'>"+disprice+"</font>,"+
	    		 "platformSettlement:<font color ='red'>"+platformSettlement+"</font>");
			dataGrid.setTotal(ovoDto1.size());
			dataGrid.setResults(ovoDto1);
			TagUtil.datagrid(response, dataGrid); 
	}
	*/


    /**
     * 删除订单管理
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TcsbOrderEntity tcsbOrder, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        //TSUser user = getCurrentUser();
        tcsbOrder = systemService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
        String message = "订单管理删除成功";
        try {
            TcsbShopEntity tcsbShopEntity = tcsbOrderService.get(TcsbShopEntity.class, tcsbOrder.getShopId());


            List<TcsbUserOrderEntity> tcsbUserOrderList = tcsbOrderService.findByQueryString("from TcsbUserOrderEntity where orderId='" + tcsbOrder.getId() + "'");

            for (TcsbUserOrderEntity tcsbUserOrderEntity : tcsbUserOrderList) {
                if (tcsbOrder.getPayStatus().equals("1")) {

                    //订单已支付回滚bigData
                    String hql = "from TcsbAssociatorBigdataEntity where userId='" + tcsbUserOrderEntity.getUserId() + "' and shopId='" + tcsbOrder.getShopId() + "'";
                    TcsbAssociatorBigdataEntity tcsbAssociatorBigdataEntity = tcsbOrderService.singleResult(hql);

                    int saleCount = Integer.valueOf(tcsbAssociatorBigdataEntity.getSaleCount()) - 1;
                    double saleTotal = tcsbAssociatorBigdataEntity.getSaleTotal();
                    saleTotal = BigDecimalUtil.sub(saleTotal, tcsbOrder.getTotalPrice());
                    String saleAvgTotal = BigDecimalUtil.divide(saleTotal + "", saleCount + "", 2);
                    tcsbAssociatorBigdataEntity.setSaleCount(saleCount + "");
                    tcsbAssociatorBigdataEntity.setSaleTotal(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(saleTotal, 2)));
                    tcsbAssociatorBigdataEntity.setSaleAvgTotal(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(saleAvgTotal, 2)));
                    tcsbOrderService.saveOrUpdate(tcsbAssociatorBigdataEntity);
                }
                String orderid = tcsbUserOrderEntity.getOrderId();
                tcsbUserOrderEntity.setOrderId(orderid + tcsbShopEntity.getName());
                tcsbOrderService.saveOrUpdate(tcsbUserOrderEntity);
            }
            tcsbOrder.setShopId(tcsbShopEntity.getName());
            //tcsbOrderService.delMain(tcsbOrder);
            tcsbOrderService.saveOrUpdate(tcsbOrder);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除订单管理
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "订单管理删除成功";
        try {
            for (String id : ids.split(",")) {
                TcsbOrderEntity tcsbOrder = systemService.getEntity(TcsbOrderEntity.class, id);

                tcsbOrderService.delMain(tcsbOrder);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 退菜
     *
     * @return
     */
    @RequestMapping(params = "retreatOrder")
    @ResponseBody
    public AjaxJson retreatOrder(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "订单管理退菜成功";
        String orderId = request.getParameter("orderId");
        String totalPrice = request.getParameter("totalPrice");
        String offlinePrice = request.getParameter("offlinePrice");

        String orderItemId = request.getParameter("orderItemId");
        String orderItemnum = request.getParameter("orderItemnum");
        try {
            tcsbOrderService.updateBySqlString("update tcsb_order set total_price = " + totalPrice + ",offline_price = " + offlinePrice + " where id = '" + orderId + "'");
            if ("0.0".equals(orderItemnum) || "0".equals(orderItemnum)) {
                systemService.executeSql("delete from tcsb_order_item where id = ?", orderItemId);
            } else {
                systemService.updateBySqlString("update tcsb_order_item set count = " + orderItemnum + " where id = '" + orderItemId + "'");
            }
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单管理退菜失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加订单管理
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TcsbOrderEntity tcsbOrder, TcsbOrderPage tcsbOrderPage, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (tcsbOrder.getOrderNo() == null) {
            //检测桌位是否正在被使用
            Map<String, Object> map2 = tcsbShopService.findOneForJdbc("select o.id,o.order_no from tcsb_order o  where o.desk_id=? and o.pay_status='0'", tcsbOrder.getDeskId());
            //如果有的情况
            if (map2 == null) {
                //生成订单号
                String orderNoGenerate = OrderNumberGenerateUtil.getOrderNumber();
                tcsbOrder.setOrderNo(orderNoGenerate);
                TSUser user = getCurrentUser();
                tcsbOrder.setShopId(user.getShopId());
                tcsbOrder.setTaste("0");
                tcsbOrder.setPlatformDiscountPrice(0.0);
                tcsbOrder.setSpecialCouponPrice(0.0);
                tcsbOrder.setUniversalCouponPrice(0.0);
                List<TcsbOrderItemEntity> tcsbOrderItemList = tcsbOrderPage.getTcsbOrderItemList();
                String message = "添加成功";
                try {
                    tcsbOrderService.addMain(tcsbOrder, tcsbOrderItemList);
                    systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "订单管理添加失败";
                    throw new BusinessException(e.getMessage());
                }
                j.setMsg(message);
            } else {
                String message = "桌位正在被使用";
                j.setMsg(message);
            }
        } else {
            List<TcsbOrderItemEntity> tcsbOrderItemList = tcsbOrderPage.getTcsbOrderItemList();
            String message = "添加成功";
            try {
                tcsbOrderService.addMain(tcsbOrder, tcsbOrderItemList);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = "订单管理添加失败";
                throw new BusinessException(e.getMessage());
            }
            j.setMsg(message);
        }
        return j;
    }

    /**
     * 更新订单管理
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TcsbOrderEntity tcsbOrder, TcsbOrderPage tcsbOrderPage, HttpServletRequest request) {
        List<TcsbOrderItemEntity> tcsbOrderItemList = tcsbOrderPage.getTcsbOrderItemList();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        try {
            tcsbOrderService.updateMain(tcsbOrder, tcsbOrderItemList);
            if (tcsbOrder.getPayStatus().equals("1")) {
                TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecordEntity = systemService.findUniqueByProperty(TcsbOrderBigdataRecordEntity.class, "orderno", tcsbOrder.getOrderNo());
                if (StringUtil.isNotEmpty(tcsbOrderBigdataRecordEntity)) {
                    if (tcsbOrderBigdataRecordEntity.getIsExecute().equals(1)) {
                        //允许执行
                        //根据orderno获取订单id
                        TcsbOrderEntity tcsbOrderEntity = systemService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", tcsbOrder.getOrderNo());
                        if (StringUtil.isNotEmpty(tcsbOrderEntity)) {
                            //跟据orderid获取需要更新的会员用户
                            List<TcsbUserOrderEntity> tcsbUserOrderEntities = systemService.findByProperty(TcsbUserOrderEntity.class, "orderId", tcsbOrderEntity.getId());
                            //遍历更新会员的消费数据
                            for (TcsbUserOrderEntity tcsbUserOrderEntity : tcsbUserOrderEntities) {
                                //检查该会员是否生成过该店铺的数据
                                String tcsbAssociatorBigdatahql = "from TcsbAssociatorBigdataEntity where userId='" + tcsbUserOrderEntity.getUserId() + "' and shopId='" + tcsbOrderEntity.getShopId() + "'";
                                TcsbAssociatorBigdataEntity tcsbAssociatorBigdataEntity = systemService.singleResult(tcsbAssociatorBigdatahql);
                                if (StringUtil.isNotEmpty(tcsbAssociatorBigdataEntity)) {
                                    //已有数据  执行更新
                                    int saleCount = Integer.valueOf(tcsbAssociatorBigdataEntity.getSaleCount()).intValue() + 1;
                                    tcsbAssociatorBigdataEntity.setSaleCount(saleCount + "");
                                    double saleTotal = BigDecimalUtil.add(Double.valueOf(tcsbAssociatorBigdataEntity.getSaleTotal()), tcsbOrderEntity.getTotalPrice());
                                    String saleAvgTotal = BigDecimalUtil.divide(saleTotal + "", tcsbAssociatorBigdataEntity.getSaleCount() + "", 2);
                                    tcsbAssociatorBigdataEntity.setSaleTotal(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(saleTotal, 2)));
                                    tcsbAssociatorBigdataEntity.setLastSaleTime(tcsbOrderEntity.getCreateTime());
                                    tcsbAssociatorBigdataEntity.setSaleAvgTotal(Double.valueOf(saleAvgTotal));
                                    systemService.saveOrUpdate(tcsbAssociatorBigdataEntity);
                                } else {
                                    //生成新的数据
                                    TcsbAssociatorBigdataEntity tcsbAssociatorBigdatasave = new TcsbAssociatorBigdataEntity();
                                    tcsbAssociatorBigdatasave.setLastSaleTime(tcsbOrderEntity.getCreateTime());
                                    tcsbAssociatorBigdatasave.setSaleAvgTotal(tcsbOrderEntity.getTotalPrice());
                                    tcsbAssociatorBigdatasave.setSaleCount("1");
                                    tcsbAssociatorBigdatasave.setSaleTotal(tcsbOrderEntity.getTotalPrice());
                                    tcsbAssociatorBigdatasave.setShopId(tcsbOrderEntity.getShopId());

                                    String getUserHql = "from WeixinUserEntity where openid='" + tcsbUserOrderEntity.getUserId() + "' and shopId='" + tcsbOrderEntity.getShopId() + "'";
                                    WeixinUserEntity tcsbWeixinUserEntity = systemService.singleResult(getUserHql);
                                    //tcsbAssociatorBigdatasave.setUserCity(tcsbWeixinUserEntity.getCity());
                                    tcsbAssociatorBigdatasave.setUserId(tcsbUserOrderEntity.getUserId());
                                    //tcsbAssociatorBigdatasave.setUserMobile(tcsbWeixinUserEntity.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
                                    //tcsbAssociatorBigdatasave.setUserNickname(tcsbWeixinUserEntity.getNickname());
                                    systemService.save(tcsbAssociatorBigdatasave);
                                }
                            }
                        }
                    }
                } else {
                    TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecordsave = new TcsbOrderBigdataRecordEntity();
                    tcsbOrderBigdataRecordsave.setIsExecute(0);
                    tcsbOrderBigdataRecordsave.setOrderno(tcsbOrder.getOrderNo());
                    systemService.save(tcsbOrderBigdataRecordsave);
                    //根据orderno获取订单id
                    TcsbOrderEntity tcsbOrderEntity = systemService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", tcsbOrder.getOrderNo());
                    if (StringUtil.isNotEmpty(tcsbOrderEntity)) {
                        //跟据orderid获取需要更新的会员用户
                        List<TcsbUserOrderEntity> tcsbUserOrderEntities = systemService.findByProperty(TcsbUserOrderEntity.class, "orderId", tcsbOrderEntity.getId());
                        //遍历更新会员的消费数据
                        for (TcsbUserOrderEntity tcsbUserOrderEntity : tcsbUserOrderEntities) {
                            //检查该会员是否生成过该店铺的数据
                            String tcsbAssociatorBigdatahql = "from TcsbAssociatorBigdataEntity where userId='" + tcsbUserOrderEntity.getUserId() + "' and shopId='" + tcsbOrderEntity.getShopId() + "'";
                            TcsbAssociatorBigdataEntity tcsbAssociatorBigdataEntity = systemService.singleResult(tcsbAssociatorBigdatahql);
                            if (StringUtil.isNotEmpty(tcsbAssociatorBigdataEntity)) {
                                //已有数据  执行更新
                                int saleCount = Integer.valueOf(tcsbAssociatorBigdataEntity.getSaleCount()).intValue() + 1;
                                tcsbAssociatorBigdataEntity.setSaleCount(saleCount + "");
                                double saleTotal = BigDecimalUtil.add(Double.valueOf(tcsbAssociatorBigdataEntity.getSaleTotal()), tcsbOrderEntity.getTotalPrice());
                                String saleAvgTotal = BigDecimalUtil.divide(saleTotal + "", tcsbAssociatorBigdataEntity.getSaleCount() + "", 2);
                                tcsbAssociatorBigdataEntity.setSaleTotal(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(saleTotal, 2)));
                                tcsbAssociatorBigdataEntity.setLastSaleTime(tcsbOrderEntity.getCreateTime());
                                tcsbAssociatorBigdataEntity.setSaleAvgTotal(Double.valueOf(saleAvgTotal));
                                systemService.saveOrUpdate(tcsbAssociatorBigdataEntity);
                            } else {
                                //生成新的数据
                                TcsbAssociatorBigdataEntity tcsbAssociatorBigdatasave = new TcsbAssociatorBigdataEntity();
                                tcsbAssociatorBigdatasave.setLastSaleTime(tcsbOrderEntity.getCreateTime());
                                tcsbAssociatorBigdatasave.setSaleAvgTotal(tcsbOrderEntity.getTotalPrice());
                                tcsbAssociatorBigdatasave.setSaleCount("1");
                                tcsbAssociatorBigdatasave.setSaleTotal(tcsbOrderEntity.getTotalPrice());
                                tcsbAssociatorBigdatasave.setShopId(tcsbOrderEntity.getShopId());
                                TcsbWeixinUserEntity tcsbWeixinUserEntity = systemService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", tcsbUserOrderEntity.getUserId());
                                tcsbAssociatorBigdatasave.setUserCity(tcsbWeixinUserEntity.getCity());
                                tcsbAssociatorBigdatasave.setUserId(tcsbUserOrderEntity.getUserId());
                                tcsbAssociatorBigdatasave.setUserMobile(tcsbWeixinUserEntity.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
                                tcsbAssociatorBigdatasave.setUserNickname(tcsbWeixinUserEntity.getNickname());
                                systemService.save(tcsbAssociatorBigdatasave);
                            }
                        }
                    }
                }


            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新订单管理失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 接单更改订单接收状态
     *
     * @return
     */
    @RequestMapping(params = "receiveOrder")
    @ResponseBody
    public AjaxJson receiveOrder(TcsbOrderEntity tcsbOrder, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "恭喜你接单成功";
        try {
            if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
                tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
            }
            tcsbOrder.setOrderIstake("Y");
            tcsbOrderService.saveOrUpdate(tcsbOrder);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新订单管理失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 订单管理新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
            tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
            req.setAttribute("tcsbOrderPage", tcsbOrder);
        }
        //根据 shopId获取所有食物
        TSUser user = getCurrentUser();
        if (!checkAdmin()) {

            List<TcsbFoodTypeEntity> tcsbFoodTypeEntity = tcsbFoodService.findHql("from TcsbFoodTypeEntity where shopId = ?", user.getShopId());
            req.setAttribute("tcsbFoodTypeEntity", tcsbFoodTypeEntity);

            List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", user.getShopId());
            req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
            for (int i = 0; i < tcsbFoodEntities.size(); i++) {
                if (StringUtil.isNotEmpty(tcsbFoodEntities.get(i).getUnitId())) {
                    TcsbFoodUnitEntity foodUnitEntity = tcsbShopService.get(TcsbFoodUnitEntity.class, tcsbFoodEntities.get(i).getUnitId());
                    if (StringUtil.isNotEmpty(foodUnitEntity)) {
                        //tcsbFoodEntities.get(i).setIsFloat(foodUnitEntity.getIsfloat());
                        tcsbFoodEntities.get(i).setUnitName(foodUnitEntity.getName());
                    } else {
                        //tcsbFoodEntities.get(i).setIsFloat("0");
                        tcsbFoodEntities.get(i).setUnitName("份 _");
                    }
                } else {
                    //tcsbFoodEntities.get(i).setIsFloat("0");
                    tcsbFoodEntities.get(i).setUnitName("份 _");
                }

            }


            List<TcsbDeskEntity> deskList = tcsbShopService.findByProperty(TcsbDeskEntity.class, "shopId", user.getShopId());
            List<TcsbDeskEntity> noneDeskList = new ArrayList<>();
            for (TcsbDeskEntity tcsbDeskEntity : deskList) {
                //过滤正在被使用的桌位
                TcsbDeskEntity map2 = tcsbShopService.singleResult("from TcsbDeskEntity where ID='" + tcsbDeskEntity.getId() + "' and status='0'");
                //如果有的情况
                if (map2 == null) {
                    noneDeskList.add(tcsbDeskEntity);
                }
            }
            req.setAttribute("deskList", noneDeskList);
        }
        return new ModelAndView("com/tcsb/order/tcsbOrder-add");
    }

    /**
     * 订单管理编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
            tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
            req.setAttribute("tcsbOrderPage", tcsbOrder);
            TcsbDeskEntity tcsbDeskEntity = systemService.get(TcsbDeskEntity.class, tcsbOrder.getDeskId());
            req.setAttribute("deskName", tcsbDeskEntity.getDeskName());
        }
		
		
		/*//根据 shopId获取所有食物*/
        //根据 shopId获取所有食物分类
        TSUser user = getCurrentUser();
        if (!checkAdmin()) {

            List<TcsbFoodTypeEntity> tcsbFoodTypeEntity = tcsbFoodService.findHql("from TcsbFoodTypeEntity where shopId = ?", user.getShopId());
            req.setAttribute("tcsbFoodTypeEntity", tcsbFoodTypeEntity);
            List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", user.getShopId());

            for (int i = 0; i < tcsbFoodEntities.size(); i++) {
                if (StringUtil.isNotEmpty(tcsbFoodEntities.get(i).getUnitId())) {
                    TcsbFoodUnitEntity foodUnitEntity = tcsbShopService.get(TcsbFoodUnitEntity.class, tcsbFoodEntities.get(i).getUnitId());
                    if (StringUtil.isNotEmpty(foodUnitEntity)) {
                        tcsbFoodEntities.get(i).setUnitName(foodUnitEntity.getName());
                    } else {
                        tcsbFoodEntities.get(i).setUnitName("份 _");
                    }
                } else {
                    tcsbFoodEntities.get(i).setUnitName("份 _");
                }

            }


            req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
        }
        return new ModelAndView("com/tcsb/order/tcsbOrder-update");
    }

    /**
     * 订单打印预览跳转
     * @param
     * @param req
     * @return
     *//*
	@RequestMapping(params = "print")
	public ModelAndView print(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
			tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
			TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
			if (tcsbOrder!=null) {
				tcsbOrderPrintVO.setCreateTime(tcsbOrder.getCreateTime());
				TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbOrder.getDeskId());
				tcsbOrderPrintVO.setDeskName(tcsbDeskEntity.getDeskName());
				//tcsbOrderPrintVO.setFinalMoney(tcsbOrder.getDisPrice());
				tcsbOrderPrintVO.setOrderNo(tcsbOrder.getOrderNo());
				tcsbOrderPrintVO.setPlatformDiscountPrice(tcsbOrder.getPlatformDiscountPrice());
				TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbOrder.getShopId());
				tcsbOrderPrintVO.setShopName(tcsbShopEntity.getName());
				tcsbOrderPrintVO.setSpecialCouponPrice(tcsbOrder.getSpecialCouponPrice());
				tcsbOrderPrintVO.setTotalMoney(tcsbOrder.getTotalPrice());
				tcsbOrderPrintVO.setUniversalCouponPrice(tcsbOrder.getUniversalCouponPrice());
				List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
				List<Map<String, Object>> orderItemMaps = systemService.findForJdbc("select f.name,i.count,f.price from tcsb_order_item i LEFT JOIN tcsb_order o on o.id = i.order_id LEFT JOIN tcsb_food f on f.id = i.food_id where i.order_id = ?", tcsbOrder.getId());
				for (Map<String, Object> map : orderItemMaps) {
					TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
					itemPrintVO.setCount((Integer)map.get("count"));
					itemPrintVO.setFoodName((String)map.get("name"));
					itemPrintVO.setPrice((Double)map.get("price"));
					tcsbOrderItemPrintVOs.add(itemPrintVO);
				}
				tcsbOrderPrintVO.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs);
			}
			req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
		}
		return new ModelAndView("com/tcsb/order/tcsbOrderPrint");
	}*/

    /**
     * 加载明细列表[订单项管理]
     *
     * @return
     */
    @RequestMapping(params = "tcsbOrderItemList")
    public ModelAndView tcsbOrderItemList(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tcsbOrder.getId();
        //===================================================================================
        //查询-订单项管理
        String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
        try {
            List<TcsbOrderItemEntity> tcsbOrderItemEntityList = systemService.findHql(hql0, id0);

            for (int i = 0; i < tcsbOrderItemEntityList.size(); i++) {
                //根据foodid获取食品信息
                TcsbFoodEntity foodEntity = tcsbShopService.get(TcsbFoodEntity.class, tcsbOrderItemEntityList.get(i).getFoodId());
                TcsbFoodTypeEntity tcsbFoodTypeEntity = tcsbShopService.get(TcsbFoodTypeEntity.class, foodEntity.getFoodTypeId());
                tcsbOrderItemEntityList.get(i).setFoodTypeId(tcsbFoodTypeEntity.getId());
                if (StringUtil.isNotEmpty(foodEntity.getUnitId())) {
                    TcsbFoodUnitEntity foodUnitEntity = tcsbShopService.get(TcsbFoodUnitEntity.class, foodEntity.getUnitId());
                    if (StringUtil.isNotEmpty(foodUnitEntity)) {
                        tcsbOrderItemEntityList.get(i).setIsFloat(foodUnitEntity.getIsfloat());
                        tcsbOrderItemEntityList.get(i).setUnitName(foodUnitEntity.getName());
                    } else {
                        tcsbOrderItemEntityList.get(i).setIsFloat("0");
                        tcsbOrderItemEntityList.get(i).setUnitName("份 _");
                    }
                } else {
                    tcsbOrderItemEntityList.get(i).setIsFloat("0");
                    tcsbOrderItemEntityList.get(i).setUnitName("份 _");
                }

            }

            req.setAttribute("tcsbOrderItemList", tcsbOrderItemEntityList);
            //根据 shopId获取所有食物
            TcsbOrderEntity tcsbOrderEntity = tcsbShopService.get(TcsbOrderEntity.class,tcsbOrder.getId());
            List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbOrderEntity.getShopId());
            req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);



            List<TcsbFoodTypeEntity> tcsbFoodTypeEntity = tcsbFoodService.findHql("from TcsbFoodTypeEntity where shopId = ?", tcsbOrderEntity.getShopId());
            req.setAttribute("tcsbFoodTypeEntity", tcsbFoodTypeEntity);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return new ModelAndView("com/tcsb/orderitem/tcsbOrderItemList");
    }

    /**
     * 订单打印预览跳转
     *
     * @param
     * @param req
     * @return
     */
    @RequestMapping(params = "print")
    public ModelAndView print(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
            tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
            TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
            if (tcsbOrder != null) {
                tcsbOrderPrintVO.setCreateTime(tcsbOrder.getCreateTime());
                TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbOrder.getDeskId());
                tcsbOrderPrintVO.setDeskName(tcsbDeskEntity.getDeskName());
                //tcsbOrderPrintVO.setFinalMoney(tcsbOrder.getDisPrice());
                tcsbOrderPrintVO.setOrderNo(tcsbOrder.getOrderNo());
                tcsbOrderPrintVO.setPlatformDiscountPrice(tcsbOrder.getPlatformDiscountPrice());
                TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbOrder.getShopId());
                tcsbOrderPrintVO.setShopName(tcsbShopEntity.getName());
                tcsbOrderPrintVO.setPhone(tcsbShopEntity.getPhone());
                tcsbOrderPrintVO.setSpecialCouponPrice(tcsbOrder.getSpecialCouponPrice());
                tcsbOrderPrintVO.setTotalMoney(tcsbOrder.getTotalPrice());
                if (StringUtil.isNotEmpty(tcsbOrder.getOfflineDiscount())) {
                    tcsbOrderPrintVO.setOfflineDiscount(tcsbOrder.getTotalPrice() - tcsbOrder.getTotalPrice() / Double.parseDouble(tcsbOrder.getOfflineDiscount()) + "");
                    tcsbOrderPrintVO.setTotalMoney(tcsbOrder.getTotalPrice() / Double.parseDouble(tcsbOrder.getOfflineDiscount()));
                }
                if (tcsbOrder.getOfflinePrice() == 0.0 && tcsbOrder.getOnlinePrice() != 0.0) {
                    tcsbOrderPrintVO.setFinalMoney(tcsbOrder.getOnlinePrice());
                }
                if (tcsbOrder.getOnlinePrice() == 0.0 && tcsbOrder.getOfflinePrice() != 0.0) {
                    tcsbOrderPrintVO.setFinalMoney(tcsbOrder.getOfflinePrice());
                }
                if (tcsbOrder.getOnlinePrice() == 0.0 && tcsbOrder.getOfflinePrice() == 0.0) {
                    tcsbOrderPrintVO.setFinalMoney(tcsbOrder.getOfflinePrice());
                }
                tcsbOrderPrintVO.setUniversalCouponPrice(tcsbOrder.getUniversalCouponPrice());
                List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
                List<Map<String, Object>> orderItemMaps = systemService.findForJdbc("select f.name,u.name as unitName,i.count,f.price,i.foodTasteFun from tcsb_order_item i LEFT JOIN tcsb_order o on o.id = i.order_id LEFT JOIN tcsb_food f on f.id = i.food_id left join tcsb_food_unit u on u.id = f.unitId where i.order_id = ?", tcsbOrder.getId());
                for (Map<String, Object> map : orderItemMaps) {
                    TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
                    itemPrintVO.setCount(Double.valueOf(map.get("count").toString()));
                    itemPrintVO.setFoodName((String) map.get("name"));
                    itemPrintVO.setPrice((Double) map.get("price"));
                    itemPrintVO.setUnitName((String) map.get("unitName"));
                    itemPrintVO.setFunFoodTaste((String) map.get("foodTasteFun"));
                    tcsbOrderItemPrintVOs.add(itemPrintVO);

                }
                tcsbOrderPrintVO.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs);
            }
            req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
        }
        return new ModelAndView("com/tcsb/order/tcsbOrderPrint");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TcsbOrderEntity tcsbOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap map) {
        CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        List<TcsbOrderEntity> list = this.tcsbOrderService.getListByCriteriaQuery(cq, false);
        List<TcsbOrderPage> pageList = new ArrayList<TcsbOrderPage>();
        if (list != null && list.size() > 0) {
            for (TcsbOrderEntity entity : list) {
                try {
                    TcsbOrderPage page = new TcsbOrderPage();
                    MyBeanUtils.copyBeanNotNull2Bean(entity, page);
                    Object id0 = entity.getId();
                    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
                    List<TcsbOrderItemEntity> tcsbOrderItemEntityList = systemService.findHql(hql0, id0);
                    page.setTcsbOrderItemList(tcsbOrderItemEntityList);
                    pageList.add(page);
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
        }
        map.put(NormalExcelConstants.FILE_NAME, "订单管理");
        map.put(NormalExcelConstants.CLASS, TcsbOrderPage.class);
        map.put(NormalExcelConstants.PARAMS, new ExportParams("订单管理列表", "导出人:Jeecg",
                "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST, pageList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(2);
            params.setNeedSave(true);
            try {
                List<TcsbOrderPage> list = ExcelImportUtil.importExcel(file.getInputStream(), TcsbOrderPage.class, params);
                TcsbOrderEntity entity1 = null;
                for (TcsbOrderPage page : list) {
                    entity1 = new TcsbOrderEntity();
                    MyBeanUtils.copyBeanNotNull2Bean(page, entity1);
                    tcsbOrderService.addMain(entity1, page.getTcsbOrderItemList());
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    /**
     * 导出excel 使模板
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(ModelMap map) {
        map.put(NormalExcelConstants.FILE_NAME, "订单管理");
        map.put(NormalExcelConstants.CLASS, TcsbOrderPage.class);
        map.put(NormalExcelConstants.PARAMS, new ExportParams("订单管理列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST, new ArrayList());
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tcsbOrderController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<TcsbOrderEntity> list() {
        List<TcsbOrderEntity> listTcsbOrders = tcsbOrderService.getList(TcsbOrderEntity.class);
        return listTcsbOrders;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        TcsbOrderEntity task = tcsbOrderService.get(TcsbOrderEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody TcsbOrderPage tcsbOrderPage, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbOrderPage>> failures = validator.validate(tcsbOrderPage);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        List<TcsbOrderItemEntity> tcsbOrderItemList = tcsbOrderPage.getTcsbOrderItemList();

        TcsbOrderEntity tcsbOrder = new TcsbOrderEntity();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tcsbOrder, tcsbOrderPage);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        tcsbOrderService.addMain(tcsbOrder, tcsbOrderItemList);

        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = tcsbOrderPage.getId();
        URI uri = uriBuilder.path("/rest/tcsbOrderController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TcsbOrderPage tcsbOrderPage) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbOrderPage>> failures = validator.validate(tcsbOrderPage);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        List<TcsbOrderItemEntity> tcsbOrderItemList = tcsbOrderPage.getTcsbOrderItemList();

        TcsbOrderEntity tcsbOrder = new TcsbOrderEntity();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tcsbOrder, tcsbOrderPage);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        tcsbOrderService.updateMain(tcsbOrder, tcsbOrderItemList);

        //按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        TcsbOrderEntity tcsbOrder = tcsbOrderService.get(TcsbOrderEntity.class, id);
        tcsbOrderService.delMain(tcsbOrder);
    }

    /**
     * 生成订单
     * 只返回订单号
     *
     * @return
     */
    @RequestMapping(value = "saveOrderReturnOrderNo", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONPObject saveOrderReturnOrderNo(HttpServletRequest request) {
        String callbackFunName = request.getParameter("callbackparam");//得到js函数名称
        String order = request.getParameter("order");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            TcsbOrderVO tcsbOrderVO = JSONHelper.fromJsonToObject(order, TcsbOrderVO.class);
            map = tcsbOrderService.saveOrderReturnOrderNo(tcsbOrderVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setObj(map);
        return new JSONPObject(callbackFunName, ajaxJsonApi);
    }

    @RequestMapping(value = "getOrderByOrderNo", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONPObject getOrderByOrderNo(@RequestParam String orderNo, @RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
        String callbackFunName = request.getParameter("callbackparam");//得到js函数名称
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", orderNo);
        //根据userId和shopId判断是否已收藏该店铺
        List<Map<String, Object>> shopCollectMaps = systemService.findHql("from TcsbShopCollectEntity where userId=? and shopId=? and isDel=0", userId, tcsbOrderEntity.getShopId());
        boolean isCollect = false;
        if (!shopCollectMaps.isEmpty()) {
            isCollect = true;
        }

        //根据userId和shopId判断是否已有评论该店铺
        boolean isEvaluate = false;
        String shopEvaluatehql = "from TcsbShopEvaluateEntity where userId='" + userId + "' and shopId='" + tcsbOrderEntity.getShopId() + "' and orderId='" + tcsbOrderEntity.getId() + "'";
        List<TcsbShopEvaluateEntity> TcsbShopEvaluateList = systemService.findByQueryString(shopEvaluatehql);
        if (TcsbShopEvaluateList.size() > 0) {
            isEvaluate = true;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("isCollect", isCollect);
        map.put("isEvaluate", isEvaluate);
        TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbOrderEntity.getShopId());
        map.put("shopName", tcsbShopEntity.getName());
        map.put("shopImg", getCkPath() + tcsbShopEntity.getHeadimg());
        map.put("createTime", tcsbOrderEntity.getCreateTime());
        map.put("isSupportPay", tcsbShopEntity.getIsSupportPay());
        TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbOrderEntity.getDeskId());
        if (tcsbDeskEntity == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            ajaxJsonApi.setMsg("请传正确的deskId");
            ajaxJsonApi.setSuccess(false);
            return new JSONPObject(callbackFunName, ajaxJsonApi);
        }
        map.put("platformDiscountPrice", tcsbOrderEntity.getPlatformDiscountPrice());
        map.put("deskName", tcsbDeskEntity.getDeskName());
        TcsbOrderVO tcsbOrderVO = new TcsbOrderVO();
        List<TcsbOrderItemVO> tcsbOrderItemVOs = new ArrayList<>();
        try {
            tcsbOrderVO.setDeskId(tcsbOrderEntity.getDeskId());
            tcsbOrderVO.setShopId(tcsbOrderEntity.getShopId());
            List<TcsbOrderItemEntity> tcsbOrderItemEntities = systemService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
            for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
                TcsbOrderItemVO tcsbOrderItemVO = new TcsbOrderItemVO();
                MyBeanUtils.copyBeanNotNull2Bean(tcsbOrderItemEntity, tcsbOrderItemVO);
                TcsbFoodEntity tcsbFoodEntity = tcsbFoodService.get(TcsbFoodEntity.class, tcsbOrderItemEntity.getFoodId());
                tcsbOrderItemVO.setName(tcsbFoodEntity.getName());
                if (StringUtil.isNotEmpty(tcsbOrderItemEntity.getFoodTasteFun())) {
                    tcsbOrderItemVO.setIstaste(true);
                } else {
                    tcsbOrderItemVO.setIstaste(false);
                }
                tcsbOrderItemVOs.add(tcsbOrderItemVO);
            }
            tcsbOrderVO.setTcsbOrderItemVOs(tcsbOrderItemVOs);
            map.put("order", tcsbOrderVO);
            map.put("totalMoney", tcsbOrderEntity.getTotalPrice());
            Double finalMoney = tcsbOrderEntity.getTotalPrice();
            //获取优惠活动
            List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = tcsbDiscountActivityService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", tcsbOrderEntity.getShopId());
            if (!tcsbDiscountActivityEntities.isEmpty()) {
                List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
                for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                    TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
                    tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
                }
                if (!tcsbDiscountActivityEntities.isEmpty()) {
                    ListSorter.sort(tcsbShopFullcutTemplateEntities, "total", "discount");
                    boolean hasCut = false;
                    for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                        if (finalMoney >= tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
                            finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                            tcsbOrderEntity.setUniversalCouponPrice(Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                            map.put("shopDiscountMsg", "满" + tcsbShopFullcutTemplateEntities.get(i).getTotal() + "减" + tcsbShopFullcutTemplateEntities.get(i).getDiscount());
                            hasCut = true;
                            break;
                        }
                    }
                    if (!hasCut) {
                        tcsbOrderEntity.setUniversalCouponPrice(0.0);
                        map.put("shopDiscountMsg", "无");
                    }
                } else {
                    tcsbOrderEntity.setUniversalCouponPrice(0.0);
                    map.put("shopDiscountMsg", "无");
                }
            } else {
                tcsbOrderEntity.setUniversalCouponPrice(0.0);
                map.put("finalMoney", finalMoney);
                map.put("shopDiscountMsg", "无");
            }

            //获取专用券（userId）
            List<TcsbCouponEntity> tcsbCouponEntities = systemService.findHql("from TcsbCouponEntity where useStatus='0' and  shopId = ? and userId = ?", tcsbOrderEntity.getShopId(), userId);
            List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
            if (!tcsbCouponEntities.isEmpty()) {
                for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
                    TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
                    Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();//使用期限
                    String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); //日期单位
                    int days = 0;
                    //todo
                    if (dateUnit.equals("year")) {
                        days = userPeriod * DateUtils.getCurrentYearDays();
                    } else if (dateUnit.equals("month")) {
                        days = userPeriod * DateUtils.getCurrentMonthDay();
                    } else {
                        days = userPeriod;
                    }
                    //专用券中有效的满减活动
                    Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                    Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                    if (DateUtils.dateDiff('s', calSrc, calDes) < 0) {
                        tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity.getId());
                        tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
                    }
                    ;
                }
            }

            //立减
            if (!tcsbFullcutTemplateEntities.isEmpty()) {
                ListSorter.sort(tcsbFullcutTemplateEntities, "total", "discount");
                boolean hasCut = false;
                outer:
                for (int i = tcsbFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                    if (finalMoney >= tcsbFullcutTemplateEntities.get(i).getTotal()) {
                        tcsbOrderEntity.setSpecialCouponPrice(Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount() + ""));
                        //获取所属专用券
                        TcsbCouponEntity tcsbCouponEntity = systemService.get(TcsbCouponEntity.class, tcsbFullcutTemplateEntities.get(i).getCouponId());
                        if ("0".equals(tcsbCouponEntity.getUseRange())) {
                            finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount() + ""));
                            map.put("shopUserDiscountMsg", "满" + tcsbFullcutTemplateEntities.get(i).getTotal() + "减" + tcsbFullcutTemplateEntities.get(i).getDiscount());
                            hasCut = true;
                            break outer;
                        }
                        if ("1".equals(tcsbCouponEntity.getUseRange())) {
                            TcsbFoodEntity tcsbFoodEntity = tcsbFoodService.get(TcsbFoodEntity.class, tcsbCouponEntity.getFoodId());
                            //查找订单项中是否有该道菜
                            for (int j = 0; j < tcsbOrderItemVOs.size(); j++) {
                                if (tcsbFoodEntity.getId().equals(tcsbOrderItemVOs.get(j).getFoodId())) {
                                    finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount() + ""));
                                    map.put("shopUserDiscountMsg", "满" + tcsbFullcutTemplateEntities.get(i).getTotal() + "减" + tcsbFullcutTemplateEntities.get(i).getDiscount() + "(" + tcsbFoodEntity.getName() + ")");
                                    hasCut = true;
                                    break outer;
                                }
                            }
                        }
                    }
                }
                if (!hasCut) {
                    tcsbOrderEntity.setSpecialCouponPrice(0.0);
                    map.put("shopUserDiscountMsg", "无");
                }
                map.put("finalMoney", finalMoney);
            } else {
                tcsbOrderEntity.setSpecialCouponPrice(0.0);
                map.put("shopUserDiscountMsg", "无");
                map.put("finalMoney", finalMoney);
                ;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setObj(map);
        return new JSONPObject(callbackFunName, ajaxJsonApi);
        //return ajaxJsonApi;
    }

    /**
     * 检测是否有存在时价物品为进行更新
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "checkOrderIsSupportPay", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONPObject checkOrderIsSupportPay(HttpServletRequest request) {
        String callbackFunName = request.getParameter("callbackparam");//得到js函数名称
        String orderNo = request.getParameter("orderNo");
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        Boolean isSupportPay = true;
        //根据订单编号获取订单信息
        TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", orderNo);
        if (StringUtil.isNotEmpty(tcsbOrderEntity)) {
            List<TcsbOrderItemEntity> tcsbOrderItemEntities = systemService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
            if (StringUtil.isNotEmpty(tcsbOrderItemEntities)) {
                //遍历检查是否存在时价食品为进行更新
                for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
                    if (tcsbOrderItemEntity.getIsUpdatePrice() == 1) {
                        isSupportPay = false;
                        ajaxJsonApi.setMsg("您有存在未更新的时价物品，无法支付，请联系店铺服务员！");
                        break;
                    }
                }
            } else {
                isSupportPay = false;
                ajaxJsonApi.setMsg("子订单异常，子订单商品不存在");
            }
        } else {
            isSupportPay = false;
            ajaxJsonApi.setMsg("订单异常，订单不存在");
        }
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setObj(isSupportPay);
        return new JSONPObject(callbackFunName, ajaxJsonApi);
    }

    @RequestMapping(params = "getfoodByfoodType")
    @ResponseBody
    public List<TcsbFoodEntity> getfoodByfoodType(String typeid) {
        List<TcsbFoodEntity> tcsbFoodEntity = tcsbFoodService.findHql("from TcsbFoodEntity where foodTypeId = ?", typeid);
        return tcsbFoodEntity;
    }


    @RequestMapping(params = "updatediscountprice")
    @ResponseBody
    public int updatediscountprice(String orderId, String offLineDiscount) {
        TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.get(TcsbOrderEntity.class, orderId);
        if (StringUtil.isNotEmpty(tcsbOrderEntity)) {
            if (StringUtil.isNotEmpty(tcsbOrderEntity.getOfflineDiscount())) {
                if (tcsbOrderEntity.getOfflineDiscount().equals("1")) {
                    //未打过折
                    double total = BigDecimalUtil.mul(tcsbOrderEntity.getTotalPrice(), Double.valueOf(offLineDiscount));
                    tcsbOrderEntity.setTotalPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(total, 2)));
                    tcsbOrderEntity.setOfflinePrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(total, 2)));
                    tcsbOrderEntity.setOfflineDiscount(offLineDiscount);
                    tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                } else {
                    String total = BigDecimalUtil.divide(tcsbOrderEntity.getTotalPrice() + "", tcsbOrderEntity.getOfflineDiscount(), 2);
                    double totalprice = BigDecimalUtil.mul(Double.valueOf(total), Double.valueOf(offLineDiscount));
                    tcsbOrderEntity.setTotalPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(totalprice, 2)));
                    tcsbOrderEntity.setOfflinePrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(totalprice, 2)));
                    tcsbOrderEntity.setOfflineDiscount(offLineDiscount);
                    tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                }
            } else {
                //未打过折
                double total = BigDecimalUtil.mul(tcsbOrderEntity.getTotalPrice(), Double.valueOf(offLineDiscount));
                tcsbOrderEntity.setTotalPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(total, 2)));
                tcsbOrderEntity.setOfflinePrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(total, 2)));
                tcsbOrderEntity.setOfflineDiscount(offLineDiscount);
                tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
            }
            return 1;
        } else {
            return 0;
        }

    }

}
