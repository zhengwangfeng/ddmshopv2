package com.tcsb.suborder.controller;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.desk.service.TcsbDeskServiceI;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.order.VO.TcsbOrderItemPrintVO;
import com.tcsb.order.VO.TcsbOrderPrintVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.page.TcsbOrderPage;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.printer.entity.TcsbPrinterEntity;
import com.tcsb.printerfoodfun.entity.TcsbPrinterFoodFunEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.suborder.service.TcsbSubOrderServiceI;
import com.tcsb.suborder.vo.TcsbSubOrderEntityVo;
import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;
import com.weixin.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

import org.jeecgframework.core.util.ExceptionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller  
 * @Description: 用户子订单
 * @author onlineGenerator
 * @date 2017-05-18 17:01:50
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbSubOrderController")
public class TcsbSubOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbSubOrderController.class);

	@Autowired
	private TcsbSubOrderServiceI tcsbSubOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbDeskServiceI tcsbDeskService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbFoodServiceI tcsbFoodService;


	/**
	 * 用户子订单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/suborder/tcsbSubOrderList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbSubOrderEntity tcsbSubOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbSubOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbSubOrder, request.getParameterMap());
		try{
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
			cq.eq("shopId", tcsbShopEntity.getId());
		}	
		cq.eq("orderIstake", "N");	
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbSubOrderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "datagridAccept")
	public void datagridAccept(TcsbSubOrderEntity tcsbSubOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		CriteriaQuery cq = new CriteriaQuery(TcsbSubOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbSubOrder);
		try{
		//自定义追加查询条件
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			cq.eq("shopId", user.getShopId());
		}	
		cq.addOrder("createTime", SortDirection.desc);
		
		
		//cq.eq("orderIstake", "N");
		String query_createTime_begin = request.getParameter("createTime_begin");
		String query_createTime_end = request.getParameter("createTime_end");
		if(StringUtil.isNotEmpty(query_createTime_begin)){
			cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_begin));
		}else{
			Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
			
			//calSrc.setTime(TcsbSettleAccountList.get(0).getSettleData()); 
			calSrc.add(calSrc.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动 
			cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calSrc.getTime())));
			//Date endtime = new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calSrc.getTime()));
			calSrc.add(calSrc.DATE,2);
			cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calSrc.getTime())));
		}
		if(StringUtil.isNotEmpty(query_createTime_end)){
			cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbSubOrderService.getDataGridReturn(cq, true);
		List<TcsbSubOrderEntity> tcsbOrderList = dataGrid.getResults();
		List<TcsbSubOrderEntityVo> tcsbOrdervoList = new ArrayList<>();
		TcsbSubOrderEntityVo tcsbSubOrderEntityVo;
		int i = 0;
		for (TcsbSubOrderEntity tcsbOrderEntity : tcsbOrderList) {
			tcsbSubOrderEntityVo = new TcsbSubOrderEntityVo();
			BeanUtils.copyProperties(tcsbSubOrderEntityVo, tcsbOrderEntity);
			if(StringUtil.isNotEmpty(tcsbOrderEntity.getDeskId())){
				TcsbDeskEntity TcsbDes = tcsbShopService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbOrderEntity.getDeskId());
				if(StringUtil.isNotEmpty(TcsbDes)){
					tcsbSubOrderEntityVo.setDeskId(TcsbDes.getDeskName());
				}
			}
		
			tcsbOrdervoList.add(tcsbSubOrderEntityVo);
		}
		dataGrid.setResults(tcsbOrdervoList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 查询还没有接单的记录
	 * @param tcsbOrder
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getNotReadOrder")
	@ResponseBody
	public AjaxJson getNotReadOrder(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "订单管理查询成功";
		List<TcsbSubOrderEntity> tcsbSubOrderEntities = new ArrayList<>();
		try{
			TSUser user = getCurrentUser();
			if (!checkAdmin()) {
				TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
				tcsbSubOrderEntities = tcsbSubOrderService.findHql("from TcsbSubOrderEntity where shopId =? and orderIstake = ?", tcsbShopEntity.getId(), "N");
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			message = "订单管理查询成功";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setSuccess(true);
		j.setObj(tcsbSubOrderEntities.size());
		return j;
	}
	
	/**
	 * 接单更改订单接收状态
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "receiveOrder")
	@ResponseBody
	public AjaxJson receiveOrder(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "恭喜你接单成功";
		try{
			if (StringUtil.isNotEmpty(tcsbSubOrder.getId())) {
				tcsbSubOrder = tcsbSubOrderService.getEntity(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
			}
			tcsbSubOrder.setOrderIstake("Y");
			tcsbSubOrderService.saveOrUpdate(tcsbSubOrder);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订单管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 订单打印预览跳转(版本1有包括预览使用jqprint)
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "print")
	public ModelAndView print(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbSubOrder.getId())) {
			tcsbSubOrder = tcsbSubOrderService.getEntity(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
			TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
			if (tcsbSubOrder!=null) {
				tcsbOrderPrintVO.setCreateTime(tcsbSubOrder.getCreateTime());
				TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbSubOrder.getDeskId());
				tcsbOrderPrintVO.setDeskName(tcsbDeskEntity.getDeskName());
				//tcsbOrderPrintVO.setFinalMoney(tcsbOrder.getDisPrice());
				tcsbOrderPrintVO.setOrderNo(tcsbSubOrder.getOrderNo());
				tcsbOrderPrintVO.setPlatformDiscountPrice(tcsbSubOrder.getPlatformDiscountPrice());
				TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbSubOrder.getShopId());
				tcsbOrderPrintVO.setShopName(tcsbShopEntity.getName());
				tcsbOrderPrintVO.setSpecialCouponPrice(tcsbSubOrder.getSpecialCouponPrice());
				tcsbOrderPrintVO.setTotalMoney(tcsbSubOrder.getTotalPrice());
				tcsbOrderPrintVO.setUniversalCouponPrice(tcsbSubOrder.getUniversalCouponPrice());
				List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
				List<Map<String, Object>> orderItemMaps = systemService.findForJdbc("select f.name,i.count,f.price,i.foodTasteFun from tcsb_user_order_item i LEFT JOIN tcsb_sub_order o on o.id = i.order_id LEFT JOIN tcsb_food f on f.id = i.food_id where i.order_id = ?", tcsbSubOrder.getId());
				for (Map<String, Object> map : orderItemMaps) {
					TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
					itemPrintVO.setCount(Double.valueOf(map.get("count").toString()));
					itemPrintVO.setFoodName((String)map.get("name"));
					itemPrintVO.setPrice((Double)map.get("price"));
					itemPrintVO.setFunFoodTaste((String)map.get("foodTasteFun"));
					tcsbOrderItemPrintVOs.add(itemPrintVO);
				}
				tcsbOrderPrintVO.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs);
			}
			req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
		}
		return new ModelAndView("com/tcsb/order/tcsbOrderPrint");
	}
	
	/**
	 * 订单打印预览跳转(版本2使用lodop)
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "print2")
	public ModelAndView print2(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbSubOrder.getId())) {
			tcsbSubOrder = tcsbSubOrderService.getEntity(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
			TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
			if (tcsbSubOrder!=null) {
				tcsbOrderPrintVO.setCreateTime(tcsbSubOrder.getCreateTime());
				TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbSubOrder.getDeskId());
				tcsbOrderPrintVO.setDeskName(tcsbDeskEntity.getDeskName());
				tcsbOrderPrintVO.setOrderNo(tcsbSubOrder.getOrderNo());
				TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbSubOrder.getShopId());
				tcsbOrderPrintVO.setShopName(tcsbShopEntity.getName());
				List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
				List<Map<String, Object>> orderItemMaps = systemService.findForJdbc("select f.name,i.count,f.price,i.foodTasteFun from tcsb_user_order_item i LEFT JOIN tcsb_sub_order o on o.id = i.order_id LEFT JOIN tcsb_food f on f.id = i.food_id where i.order_id = ?", tcsbSubOrder.getId());
				for (Map<String, Object> map : orderItemMaps) {
					TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
					itemPrintVO.setCount(Double.valueOf(map.get("count").toString()));
					itemPrintVO.setFoodName((String)map.get("name"));
					itemPrintVO.setPrice((Double)map.get("price"));
					itemPrintVO.setFunFoodTaste((String)map.get("foodTasteFun"));
					tcsbOrderItemPrintVOs.add(itemPrintVO);
				}
				tcsbOrderPrintVO.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs);
			}
			req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
		}
		return new ModelAndView("com/tcsb/order/tcsbOrderPrint2");
	}
	
	/**
	 * 订单打印预览跳转(版本3分权限根据食物使用lodop)
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "print3")
	public ModelAndView print3(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbSubOrder.getId())) {
			tcsbSubOrder = tcsbSubOrderService.getEntity(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
			TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
			if (tcsbSubOrder!=null) {
				//初始化子订单信息
				tcsbOrderPrintVO.setCreateTime(tcsbSubOrder.getCreateTime());
				TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbSubOrder.getDeskId());
				tcsbOrderPrintVO.setDeskName(tcsbDeskEntity.getDeskName());
				tcsbOrderPrintVO.setIsJustNowServing(tcsbSubOrder.getIsJustNowServing());
				tcsbOrderPrintVO.setOrderNo(tcsbSubOrder.getOrderNo());
				TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbSubOrder.getShopId());
				tcsbOrderPrintVO.setShopName(tcsbShopEntity.getName());
				tcsbOrderPrintVO.setNote(tcsbSubOrder.getNote());
				//获取子订单项信息
				List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
				List<Map<String, Object>> orderItemMaps = systemService.findForJdbc("select f.name,u.name as unitName,i.food_id as foodId,i.count,f.price,i.foodTasteFun from tcsb_user_order_item i LEFT JOIN tcsb_sub_order o on o.id = i.order_id LEFT JOIN tcsb_food f on f.id = i.food_id left join tcsb_food_unit u on u.id = f.unitId where i.order_id = ?", tcsbSubOrder.getId());
				for (Map<String, Object> map : orderItemMaps) {
					TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
					itemPrintVO.setCount(Double.valueOf(map.get("count").toString()));
					itemPrintVO.setFoodName((String)map.get("name"));
					itemPrintVO.setPrice((Double)map.get("price"));
					itemPrintVO.setFunFoodTaste((String)map.get("foodTasteFun"));
					itemPrintVO.setUnitName((String)map.get("unitName"));
					itemPrintVO.setFoodId((String)map.get("foodId"));
					tcsbOrderItemPrintVOs.add(itemPrintVO);
				}
				//获取打印机信息
				List<TcsbPrinterEntity> tcsbPrinterEntities = systemService.findHql("from TcsbPrinterEntity where shopId = ?", getCurrentUser().getShopId());
				
				if (!tcsbPrinterEntities.isEmpty()) {
					
					//存打印机信息
					for (TcsbPrinterEntity tcsbPrinterEntity : tcsbPrinterEntities) {
						List<TcsbPrinterFoodFunEntity>  tcsbPrinterFoodFunEntities = systemService.findHql("from TcsbPrinterFoodFunEntity where printerId = ?", tcsbPrinterEntity.getId());
						List<String>  tempId = new ArrayList<>();
						for (int i = 0; i < tcsbPrinterFoodFunEntities.size(); i++) {
							tempId.add(tcsbPrinterFoodFunEntities.get(i).getFoodId());
						}
						CriteriaQuery cq = new CriteriaQuery(TcsbFoodEntity.class);
						cq.add(Restrictions.in("id", tempId));
						List<TcsbFoodEntity> tcsbFoodEntities =  systemService.getListByCriteriaQuery(
			    				cq, false);
						//打印机有分配权限
						if (!tcsbFoodEntities.isEmpty()) {
							//创建所有食物id集合
							List<String> tempList = new ArrayList<>();
							for (int i = 0; i < tcsbFoodEntities.size(); i++) {
								tempList.add(tcsbFoodEntities.get(i).getId());
							}
							//权限中是否有匹配中的食物
							for (int j = 0; j < tcsbOrderItemPrintVOs.size(); j++) {
								if (tempList.contains(tcsbOrderItemPrintVOs.get(j).getFoodId())) {
									if (tcsbPrinterEntity.getTcsbOrderItemPrintVOs()!=null) {
										tcsbPrinterEntity.getTcsbOrderItemPrintVOs().add(tcsbOrderItemPrintVOs.get(j));
									}else {
										List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs2 = new ArrayList<>();
										tcsbOrderItemPrintVOs2.add(tcsbOrderItemPrintVOs.get(j));
										tcsbPrinterEntity.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs2);
									}
								}
							}
						}else {
							//打印机有分配权限
						}
					}
					//存取打印机中有食物的打印机
					List<TcsbPrinterEntity> tcsbPrinterEntitiesNow = new ArrayList<>();
					for (int i = 0; i < tcsbPrinterEntities.size(); i++) {
						if (tcsbPrinterEntities.get(i).getTcsbOrderItemPrintVOs()!=null) {
							tcsbPrinterEntitiesNow.add(tcsbPrinterEntities.get(i));
						}
					}
					tcsbOrderPrintVO.setTcsbPrinterEntities(tcsbPrinterEntitiesNow);
					req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
				}else {
					tcsbOrderPrintVO.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs);
					req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
				}
			}
		}
		return new ModelAndView("com/tcsb/order/tcsbOrderPrint3");
	}
	
	/**
	 * 下单获取打印信息
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getPrintSubOrder")
	@ResponseBody
	public AjaxJson getPrintSubOrder(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest req) {
		String message = null;
		AjaxJson ajaxJson = new AjaxJson();
		if (StringUtil.isNotEmpty(tcsbSubOrder.getId())) {
			tcsbSubOrder = tcsbSubOrderService.getEntity(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
			TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
			if (tcsbSubOrder!=null) {
				//初始化子订单信息
				tcsbOrderPrintVO.setCreateTime(tcsbSubOrder.getCreateTime());
				TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbSubOrder.getDeskId());
				tcsbOrderPrintVO.setDeskName(tcsbDeskEntity.getDeskName());
				tcsbOrderPrintVO.setIsJustNowServing(tcsbSubOrder.getIsJustNowServing());
				tcsbOrderPrintVO.setOrderNo(tcsbSubOrder.getOrderNo());
				tcsbOrderPrintVO.setNote(tcsbSubOrder.getNote());
				TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbSubOrder.getShopId());
				tcsbOrderPrintVO.setShopName(tcsbShopEntity.getName());
				//获取子订单项信息
				List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
				List<Map<String, Object>> orderItemMaps = systemService.findForJdbc("select f.name,u.name as unitName,i.food_id as foodId,i.count,f.price,i.foodTasteFun from tcsb_user_order_item i LEFT JOIN tcsb_sub_order o on o.id = i.order_id LEFT JOIN tcsb_food f on f.id = i.food_id left join tcsb_food_unit u on u.id = f.unitId where i.order_id = ?", tcsbSubOrder.getId());
				for (Map<String, Object> map : orderItemMaps) {
					TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
					itemPrintVO.setCount(Double.valueOf(map.get("count").toString()));
					itemPrintVO.setFoodName((String)map.get("name"));
					itemPrintVO.setPrice((Double)map.get("price"));
					itemPrintVO.setFunFoodTaste((String)map.get("foodTasteFun"));
					itemPrintVO.setUnitName((String)map.get("unitName"));
					itemPrintVO.setFoodId((String)map.get("foodId"));
					tcsbOrderItemPrintVOs.add(itemPrintVO);
				}
				//获取打印机信息
				List<TcsbPrinterEntity> tcsbPrinterEntities = systemService.findHql("from TcsbPrinterEntity where shopId = ? and autoPrint = ?", tcsbSubOrder.getShopId(),"1");
				
				if (!tcsbPrinterEntities.isEmpty()) {
					
					//存打印机信息
					for (TcsbPrinterEntity tcsbPrinterEntity : tcsbPrinterEntities) {
						List<TcsbPrinterFoodFunEntity>  tcsbPrinterFoodFunEntities = systemService.findHql("from TcsbPrinterFoodFunEntity where printerId = ?", tcsbPrinterEntity.getId());
						List<String>  tempId = new ArrayList<>();
						for (int i = 0; i < tcsbPrinterFoodFunEntities.size(); i++) {
							tempId.add(tcsbPrinterFoodFunEntities.get(i).getFoodId());
						}
						CriteriaQuery cq = new CriteriaQuery(TcsbFoodEntity.class);
						cq.add(Restrictions.in("id", tempId));
						List<TcsbFoodEntity> tcsbFoodEntities =  systemService.getListByCriteriaQuery(
			    				cq, false);
						//打印机有分配权限
						if (!tcsbFoodEntities.isEmpty()) {
							//创建所有食物id集合
							List<String> tempList = new ArrayList<>();
							for (int i = 0; i < tcsbFoodEntities.size(); i++) {
								tempList.add(tcsbFoodEntities.get(i).getId());
							}
							//权限中是否有匹配中的食物
							for (int j = 0; j < tcsbOrderItemPrintVOs.size(); j++) {
								if (tempList.contains(tcsbOrderItemPrintVOs.get(j).getFoodId())) {
									if (tcsbPrinterEntity.getTcsbOrderItemPrintVOs()!=null) {
										tcsbPrinterEntity.getTcsbOrderItemPrintVOs().add(tcsbOrderItemPrintVOs.get(j));
									}else {
										List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs2 = new ArrayList<>();
										tcsbOrderItemPrintVOs2.add(tcsbOrderItemPrintVOs.get(j));
										tcsbPrinterEntity.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs2);
									}
								}
							}
						}else {
							//打印机有分配权限
						}
					}
					//存取打印机中有食物的打印机
					List<TcsbPrinterEntity> tcsbPrinterEntitiesNow = new ArrayList<>();
					for (int i = 0; i < tcsbPrinterEntities.size(); i++) {
						if (tcsbPrinterEntities.get(i).getTcsbOrderItemPrintVOs()!=null) {
							tcsbPrinterEntitiesNow.add(tcsbPrinterEntities.get(i));
						}
					}
					tcsbOrderPrintVO.setTcsbPrinterEntities(tcsbPrinterEntitiesNow);
					ajaxJson.setMsg("打印信息获取成功");
					ajaxJson.setSuccess(true);
					ajaxJson.setObj(tcsbOrderPrintVO);
				}else {
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("没有设置打印机");
					//tcsbOrderPrintVO.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs);
					//req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
				}
			}
		}
		return ajaxJson;
	}
	
	/**
	 * 订单管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listAccept")
	public ModelAndView listAccept(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/suborder/tcsbSubOrderAcceptList");
	}
	
	/**
	 * 删除用户子订单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbSubOrder = systemService.getEntity(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
		message = "用户子订单删除成功";
		try{
			tcsbSubOrderService.delete(tcsbSubOrder);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户子订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除用户子订单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户子订单删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbSubOrderEntity tcsbSubOrder = systemService.getEntity(TcsbSubOrderEntity.class, 
				id
				);
				tcsbSubOrderService.delete(tcsbSubOrder);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "用户子订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加用户子订单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户子订单添加成功";
		try{
			tcsbSubOrderService.save(tcsbSubOrder);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户子订单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新用户子订单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户子订单更新成功";
		TcsbSubOrderEntity t = tcsbSubOrderService.get(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbSubOrder, t);
			tcsbSubOrderService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户子订单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 用户子订单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbSubOrder.getId())) {
			tcsbSubOrder = tcsbSubOrderService.getEntity(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
			req.setAttribute("tcsbSubOrderPage", tcsbSubOrder);
		}
		//根据 shopId获取所有食物
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
			List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbShopEntity.getId());
			req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
		}
		return new ModelAndView("com/tcsb/suborder/tcsbSubOrder-add");
	}
	/**
	 * 用户子订单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbSubOrder.getId())) {
			tcsbSubOrder = tcsbSubOrderService.getEntity(TcsbSubOrderEntity.class, tcsbSubOrder.getId());
			req.setAttribute("tcsbSubOrderPage", tcsbSubOrder);
		}
		//根据 shopId获取所有食物
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
			List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbShopEntity.getId());
			req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
		}
		return new ModelAndView("com/tcsb/suborder/tcsbSubOrder-update");
	}
	
	/**
	 * 加载明细列表[订单项管理]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tcsbSubOrderItemList")
	public ModelAndView tcsbSubOrderItemList(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tcsbSubOrder.getId();
		//===================================================================================
		//查询-订单项管理
	    String hql0 = "from TcsbUserOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    try{
	    	List<TcsbUserOrderItemEntity> tcsbUserOrderItemEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tcsbSubOrderItemList", tcsbUserOrderItemEntityList);
			//根据 shopId获取所有食物
			TSUser user = getCurrentUser();
			if (!checkAdmin()) {
				TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
				List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbShopEntity.getId());
				req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/tcsb/suborderitem/tcsbSubOrderItemList");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbSubOrderController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbSubOrderEntity tcsbSubOrder,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbSubOrderEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbSubOrder, request.getParameterMap());
		List<TcsbSubOrderEntity> tcsbSubOrders = this.tcsbSubOrderService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"用户子订单");
		modelMap.put(NormalExcelConstants.CLASS,TcsbSubOrderEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户子订单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbSubOrders);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbSubOrderEntity tcsbSubOrder,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"用户子订单");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbSubOrderEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户子订单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
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
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TcsbSubOrderEntity> listTcsbSubOrderEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbSubOrderEntity.class,params);
				for (TcsbSubOrderEntity tcsbSubOrder : listTcsbSubOrderEntitys) {
					tcsbSubOrderService.save(tcsbSubOrder);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TcsbSubOrderEntity> list() {
		List<TcsbSubOrderEntity> listTcsbSubOrders=tcsbSubOrderService.getList(TcsbSubOrderEntity.class);
		return listTcsbSubOrders;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbSubOrderEntity task = tcsbSubOrderService.get(TcsbSubOrderEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbSubOrderEntity tcsbSubOrder, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbSubOrderEntity>> failures = validator.validate(tcsbSubOrder);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbSubOrderService.save(tcsbSubOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbSubOrder.getId();
		URI uri = uriBuilder.path("/rest/tcsbSubOrderController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbSubOrderEntity tcsbSubOrder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbSubOrderEntity>> failures = validator.validate(tcsbSubOrder);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbSubOrderService.saveOrUpdate(tcsbSubOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tcsbSubOrderService.deleteEntityById(TcsbSubOrderEntity.class, id);
	}
	
	
	
	/**
	 * 检测是接单项是否否存在时价菜品
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "checkIsflatOrderFood")
	@ResponseBody
	public int checkIsflatOrderFood(String id) {
		//
		int result = 0;
		String hql = "from TcsbUserOrderItemEntity where orderId='"+id+"' and isUpdatePrice=1";
		List<TcsbUserOrderItemEntity> tcsbSubOrder = tcsbSubOrderService.findByQueryString(hql);
		if(tcsbSubOrder.size()>0){
			result = 1;
		}else{
			result = 0;
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "goIsflatOrderFood")
	public ModelAndView goIsflatOrderFood(TcsbSubOrderEntity tcsbSubOrder, HttpServletRequest req) {
		//modelMap.addAttribute("orderid", id);
		//===================================================================================
				//获取参数
				Object id0 = tcsbSubOrder.getId();
				//===================================================================================
				//查询-订单项管理
			    String hql0 = "from TcsbUserOrderItemEntity where 1 = 1 AND oRDER_ID = ? and isUpdatePrice=1";
			    try{
			    	List<TcsbUserOrderItemEntity> tcsbUserOrderItemEntityList = systemService.findHql(hql0,id0);
					req.setAttribute("tcsbOrderItemList", tcsbUserOrderItemEntityList);
					//根据 shopId获取所有食物
					TSUser user = getCurrentUser();
					if (!checkAdmin()) {
						TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
						List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbShopEntity.getId());
						req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
					}
				}catch(Exception e){
					logger.info(e.getMessage());
				}
		return new ModelAndView("com/tcsb/suborder/tcsbSubOrderIsfloatList");
		
	}
	
	@RequestMapping(params = "doisfloatAdd")
	@ResponseBody
	public AjaxJson doisfloatAdd(TcsbOrderPage tcsbOrderPage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户子订单编辑成功";
		
		if(StringUtil.isNotEmpty(tcsbOrderPage.getTcsbOrderItemList())){
			double accumulateMoney = 0.00; 
			
			for (TcsbOrderItemEntity iterable_element : tcsbOrderPage.getTcsbOrderItemList()) {
				TcsbUserOrderItemEntity tcsbSubOrder = tcsbFoodService.get(TcsbUserOrderItemEntity.class, iterable_element.getId());
				if(tcsbSubOrder.getCount() == iterable_element.getCount()){
					tcsbSubOrder.setIsUpdatePrice(0);
					//根据子项修改情况更新父项订单项
					//根据菜品id-规格获取订单项
					String hql = "from TcsbOrderItemEntity where foodId='"+tcsbSubOrder.getFoodId()+"' and foodTasteFun='"+tcsbSubOrder.getFoodTasteFun()+"' and isUpdatePrice=1";
					List<TcsbOrderItemEntity> tcsborderItem = tcsbFoodService.findByQueryString(hql);
					if(tcsborderItem.size()>0){
					//获取订单项第一项进行修改
					TcsbOrderItemEntity tcsbOrderItemEntity = tcsbFoodService.get(TcsbOrderItemEntity.class, tcsborderItem.get(0).getId());
					tcsbOrderItemEntity.setIsUpdatePrice(0);
					tcsbFoodService.saveOrUpdate(tcsbOrderItemEntity);
				}
					
				}else{
					
					//根据修改项累积计算订单总额
					//旧订单当前菜品的价格
					double olda = BigDecimalUtil.mul(tcsbSubOrder.getCount(),tcsbSubOrder.getPrice());
					//新订单当前菜品的价格
					double newb = BigDecimalUtil.mul(iterable_element.getCount(),tcsbSubOrder.getPrice());
					//新旧订单的差价
					double subc = BigDecimalUtil.sub(newb, olda);
					accumulateMoney = BigDecimalUtil.add(accumulateMoney, subc);
					
					//数量发生改变，修改执行的订单项	
					tcsbSubOrder.setCount(iterable_element.getCount());
					tcsbSubOrder.setIsUpdatePrice(0);
					//根据子项修改情况更新父项订单项
						//根据菜品id-规格获取订单项
					String hql = "from TcsbOrderItemEntity where orderItemRelation='"+tcsbSubOrder.getOrderItemRelation()+"' and isUpdatePrice=1";
					TcsbOrderItemEntity tcsborderItem = tcsbFoodService.singleResult(hql);
					tcsborderItem.setCount(iterable_element.getCount());
					tcsborderItem.setIsUpdatePrice(0);
					tcsbFoodService.saveOrUpdate(tcsborderItem);

					
				}
				tcsbFoodService.saveOrUpdate(tcsbSubOrder);
			}
			
			//更新子项订单总额
				//根据子项orderid获取子项订单
			TcsbSubOrderEntity tsuorder = tcsbFoodService.get(TcsbSubOrderEntity.class, tcsbOrderPage.getTcsbOrderItemList().get(0).getOrderId());
				//计算总额
			double total = BigDecimalUtil.add(tsuorder.getTotalPrice(), accumulateMoney);
			tsuorder.setTotalPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(total, 2)));
			tsuorder.setOrderIstake("Y");
			tcsbFoodService.saveOrUpdate(tsuorder);
			//更新父项订单总额
			//根据子项桌位号获取当前正在使用订单
			Map<String, Object>  map2 = tcsbFoodService.findOneForJdbc("select o.id,o.order_no from tcsb_order o  where o.desk_id=? and o.pay_status='0'", tsuorder.getDeskId());
			//如果有的情况
			if (map2!=null) {
				String orderId = (String) map2.get("id");
				TcsbOrderEntity tcsborder = tcsbFoodService.get(TcsbOrderEntity.class, orderId);
				double tcsbordertotal = BigDecimalUtil.add(tcsborder.getTotalPrice(), accumulateMoney);
				tcsborder.setTotalPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(tcsbordertotal, 2)));
				tcsbFoodService.saveOrUpdate(tcsborder);
			}
		}
		j.setMsg(message);
		return j;
	}
	
	
}
