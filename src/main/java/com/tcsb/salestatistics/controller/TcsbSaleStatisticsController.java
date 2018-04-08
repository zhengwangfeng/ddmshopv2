package com.tcsb.salestatistics.controller;
import com.alibaba.fastjson.JSONObject;
import com.baidu.inf.iis.bcs.model.Empty;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.salestatistics.entity.TcsbSaleStatisticsEntity;
import com.tcsb.salestatistics.service.TcsbSaleStatisticsServiceI;
import com.tcsb.salestatistics.vo.FoodSaleCount;
import com.tcsb.salestatistics.vo.TcsbSaleStatisticsEntityVo;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopsmsservice.entity.TcsbShopSmsServiceEntity;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.weixin.util.DateUtils;
import com.weixin.weixinuser.entity.WeixinUserEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SaleSmsClient;
import org.jeecgframework.core.util.SellerSmsClient;
import org.jeecgframework.core.util.SmsClient;

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
 * @Description: 商品销量统计
 * @author onlineGenerator
 * @date 2017-05-05 15:04:20
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbSaleStatisticsController")
public class TcsbSaleStatisticsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbSaleStatisticsController.class);

	@Autowired
	private TcsbSaleStatisticsServiceI tcsbSaleStatisticsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	
	
	
	/**
	 * 商品销量统计列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "newlist")
	public ModelAndView newlist(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/salestatistics/tcsbSaleStatisticsListnew");
	}
	
	
	/**
	 * 
	 * @param request
	 * @param foodid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "newdetaillist")
	public ModelAndView newdetaillist(HttpServletRequest request,String foodid,ModelMap modelMap) {
		modelMap.addAttribute("foodid", foodid);
		return new ModelAndView("com/tcsb/salestatistics/tcsbSaleStatisticsListnewDetailList");
	}

	
	@RequestMapping(params = "datagridnew")
	public void datagrid2(TcsbSaleStatisticsEntity tcsbSaleStatistics,HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid,HttpSession session)  throws IllegalAccessException, InvocationTargetException {
		TSUser user = getCurrentUser();
		//获取商品信息
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodEntity.class);
		cq.setCurPage(dataGrid.getPage());
		cq.setPageSize(dataGrid.getRows());
		cq.add(Restrictions.eq("shopId",user.getShopId()));
		String foodName = request.getParameter("foodName");
		if(StringUtil.isNotEmpty(foodName)){
			cq.add(Restrictions.like("name", "%"+foodName+"%"));
		}
		List<TcsbFoodEntity> foodList = tcsbSaleStatisticsService.getListByCriteriaQuery(cq, true);
		
		//获取商品总数
		String foodCountHql = "from TcsbFoodEntity where shopId='"+user.getShopId()+"'";
		List<TcsbFoodEntity> foodListCount = tcsbSaleStatisticsService.findByQueryString(foodCountHql);
		
		//获取已支付的订单购买的食物总数,会员复购数
		List<FoodSaleCount> foodSaleCountList = new ArrayList<>();
		FoodSaleCount foodSaleCount;
		String sql = "select food_id,SUM(num),SUM(num-1),order_id from sale_food_count where shop_id = '"+user.getShopId()+"' and pay_status = '1' GROUP BY food_id";
		List<Object> salefoodcountList =  tcsbSaleStatisticsService.findListbySql(sql);
		for (Iterator iterator = salefoodcountList.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			foodSaleCount = new FoodSaleCount();
			foodSaleCount.setFoodid(object[0].toString());
			foodSaleCount.setAllSaleCount(object[1].toString());
			
			//根据fooid检测是否是时价商品
			TcsbFoodEntity tcsbFoodEntity = tcsbSaleStatisticsService.get(TcsbFoodEntity.class, object[0].toString());
			if(StringUtil.isNotEmpty(tcsbFoodEntity.getUnitId())){
				TcsbFoodUnitEntity tcsbFoodUnitEntity = tcsbSaleStatisticsService.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
				if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
					if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
						//时价菜品重新获取VipSaleCount
						String floatfoodsql = "SELECT order_id,COUNT(*) as vip from sale_food_count where food_id = '"+object[0].toString()+"' and pay_status = '1' GROUP BY order_id";
						List<Object> floatfoodlist =  tcsbSaleStatisticsService.findListbySql(floatfoodsql);
						int floatvipSaleCount = 0;
						for (Iterator iterator2 = floatfoodlist.iterator(); iterator2.hasNext();) {
							Object[] object2 = (Object[]) iterator2.next();
							floatvipSaleCount += (Integer.valueOf(object2[1].toString())-1);
						}
						foodSaleCount.setVipSaleCount(floatvipSaleCount+"");
					}else{
						foodSaleCount.setVipSaleCount(object[2].toString());
					}
				}else{
					foodSaleCount.setVipSaleCount(object[2].toString());
				}
			}else{
				foodSaleCount.setVipSaleCount(object[2].toString());
			}
			foodSaleCountList.add(foodSaleCount);
		}
		
		//
		List<TcsbSaleStatisticsEntityVo> TcsbSaleStatisticsEntityVoList = new ArrayList<>();
		TcsbSaleStatisticsEntityVo TcsbSaleStatisticsEntityVo;
		//遍历将商品销售详情
		for (TcsbFoodEntity tcsbFood : foodList) {
			TcsbSaleStatisticsEntityVo = new TcsbSaleStatisticsEntityVo();
			TcsbSaleStatisticsEntityVo.setId(tcsbFood.getId());
			TcsbSaleStatisticsEntityVo.setFoodId(tcsbFood.getId());
			TcsbSaleStatisticsEntityVo.setFoodName(tcsbFood.getName());
			TcsbSaleStatisticsEntityVo.setOnSaleTime(tcsbFood.getCreateDate());
			for (FoodSaleCount foodSale : foodSaleCountList) {
				if(tcsbFood.getId().equals(foodSale.getFoodid())){
					TcsbSaleStatisticsEntityVo.setAllSaleCount(foodSale.getAllSaleCount());
					TcsbSaleStatisticsEntityVo.setVipSaleCount(foodSale.getVipSaleCount());
				}
			}
			TcsbSaleStatisticsEntityVoList.add(TcsbSaleStatisticsEntityVo);
		}
		dataGrid.setTotal(foodListCount.size());
		dataGrid.setResults(TcsbSaleStatisticsEntityVoList);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 商品销量统计详情
	 * @param foodid
	 * @param tcsbSaleStatistics
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param session
	 */
	@RequestMapping(params = "detaildatagridnew")
	public void detaildatagridnew(String foodid,TcsbSaleStatisticsEntity tcsbSaleStatistics,HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid,HttpSession session) {
		//获取登入用户信息
		TSUser user = getCurrentUser();
		//查询商品销售详情
		StringBuilder sql = new StringBuilder();
		sql.append("select wu.ID as id,f.ID as foodId,f.name as foodName,u.nickname as nickName,u.mobile,sum(i.count-1) as vipSaleCount,max(o.create_date) as lastSaleTime ");
		sql.append("from tcsb_food f ");
		sql.append("LEFT JOIN tcsb_order_item i on f.ID = i.food_id ");
		sql.append("LEFT JOIN tcsb_order o on o.ID=i.order_id ");
		sql.append("LEFT JOIN tcsb_order_parent p on p.ID=o.order_parent_id ");
		sql.append("LEFT JOIN tcsb_user_order uo on uo.order_parent_id = p.ID ");
		sql.append("LEFT JOIN weixin_user wu on wu.openid = uo.user_id ");
		sql.append("LEFT JOIN tcsb_weixin_user u on u.openid = uo.user_id ");
		sql.append("where o.pay_status='1' and p.shop_id='"+user.getShopId()+"' AND wu.shop_id = '"+user.getShopId()+"' and f.ID='"+foodid+"' and o.ID is not null GROUP BY u.openid ");
		List<Map<String,Object>> list = tcsbSaleStatisticsService.findForJdbc(sql.toString());
		List<Map<String,Object>> tcsbSaleStatisticsList = list.subList((dataGrid.getPage()-1)*dataGrid.getRows(),(dataGrid.getPage())*dataGrid.getRows()>list.size()?list.size():(dataGrid.getPage())*dataGrid.getRows());
		dataGrid.setTotal(list.size());
		dataGrid.setResults(tcsbSaleStatisticsList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	/**
	 * 添加商品销量统计
	 *
	 * @return
	 */
	@RequestMapping(params = "send")
	@ResponseBody
	public AjaxJson send(String mobile,String content, HttpServletRequest request) {
		
		
		
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "信息发送成功";
		try{

			
			TSUser user = getCurrentUser();
			//获取用户店铺信息
			TcsbShopEntity tcsbShop= tcsbShopService.getTcsbShopEntityByUserid(user.getId());
			String tcsbShophql = "from TcsbShopSmsServiceEntity where shopId='"+tcsbShop.getId()+"'";
			TcsbShopSmsServiceEntity TcsbShopSmsService = tcsbSaleStatisticsService.singleResult(tcsbShophql);
			if(StringUtil.isNotEmpty(TcsbShopSmsService)){
				
					int count = 0;//初始化发送条数
					if(StringUtil.isEmpty(mobile)){
						message = "信息发送失败";
					}else{
						String[] mobileArray = mobile.split(",");
						
						if(TcsbShopSmsService.getCount()>= mobileArray.length){
							//短信条数足够
							for (String string : mobileArray) {
								//System.out.println(string);
								SaleSmsClient.sendMessageForContent(string,content);
								count ++;
								//systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
							}
							
							
						}else{
							message = "信息剩余条不足"+mobileArray.length+"条";;
						}	
					}
					//更新短信剩余条数
					TcsbShopSmsService.setCount(TcsbShopSmsService.getCount()-count);
					tcsbSaleStatisticsService.saveOrUpdate(TcsbShopSmsService);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "信息发送失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	


	/**
	 * 删除商品销量统计
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbSaleStatisticsEntity tcsbSaleStatistics, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbSaleStatistics = systemService.getEntity(TcsbSaleStatisticsEntity.class, tcsbSaleStatistics.getId());
		message = "商品销量统计删除成功";
		try{
			tcsbSaleStatisticsService.delete(tcsbSaleStatistics);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品销量统计删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除商品销量统计
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品销量统计删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbSaleStatisticsEntity tcsbSaleStatistics = systemService.getEntity(TcsbSaleStatisticsEntity.class, 
				id
				);
				tcsbSaleStatisticsService.delete(tcsbSaleStatistics);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商品销量统计删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加商品销量统计
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbSaleStatisticsEntity tcsbSaleStatistics, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品销量统计添加成功";
		try{
			tcsbSaleStatisticsService.save(tcsbSaleStatistics);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品销量统计添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新商品销量统计
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbSaleStatisticsEntity tcsbSaleStatistics, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品销量统计更新成功";
		TcsbSaleStatisticsEntity t = tcsbSaleStatisticsService.get(TcsbSaleStatisticsEntity.class, tcsbSaleStatistics.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbSaleStatistics, t);
			tcsbSaleStatisticsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品销量统计更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 商品销量统计新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(String mobile,ModelMap modelMap) {
		modelMap.addAttribute("mobile", mobile);
		System.out.println("add+++++++"+mobile);
		return new ModelAndView("com/tcsb/salestatistics/tcsbSaleStatistics-add");
	}
	/**
	 * 商品销量统计编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbSaleStatisticsEntity tcsbSaleStatistics, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbSaleStatistics.getId())) {
			tcsbSaleStatistics = tcsbSaleStatisticsService.getEntity(TcsbSaleStatisticsEntity.class, tcsbSaleStatistics.getId());
			req.setAttribute("tcsbSaleStatisticsPage", tcsbSaleStatistics);
		}
		return new ModelAndView("com/tcsb/salestatistics/tcsbSaleStatistics-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbSaleStatisticsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbSaleStatisticsEntity tcsbSaleStatistics,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbSaleStatisticsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbSaleStatistics, request.getParameterMap());
		List<TcsbSaleStatisticsEntity> tcsbSaleStatisticss = this.tcsbSaleStatisticsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商品销量统计");
		modelMap.put(NormalExcelConstants.CLASS,TcsbSaleStatisticsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品销量统计列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbSaleStatisticss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbSaleStatisticsEntity tcsbSaleStatistics,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"商品销量统计");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbSaleStatisticsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品销量统计列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbSaleStatisticsEntity> listTcsbSaleStatisticsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbSaleStatisticsEntity.class,params);
				for (TcsbSaleStatisticsEntity tcsbSaleStatistics : listTcsbSaleStatisticsEntitys) {
					tcsbSaleStatisticsService.save(tcsbSaleStatistics);
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
	public List<TcsbSaleStatisticsEntity> list() {
		List<TcsbSaleStatisticsEntity> listTcsbSaleStatisticss=tcsbSaleStatisticsService.getList(TcsbSaleStatisticsEntity.class);
		return listTcsbSaleStatisticss;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbSaleStatisticsEntity task = tcsbSaleStatisticsService.get(TcsbSaleStatisticsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbSaleStatisticsEntity tcsbSaleStatistics, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbSaleStatisticsEntity>> failures = validator.validate(tcsbSaleStatistics);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbSaleStatisticsService.save(tcsbSaleStatistics);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbSaleStatistics.getId();
		URI uri = uriBuilder.path("/rest/tcsbSaleStatisticsController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbSaleStatisticsEntity tcsbSaleStatistics) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbSaleStatisticsEntity>> failures = validator.validate(tcsbSaleStatistics);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbSaleStatisticsService.saveOrUpdate(tcsbSaleStatistics);
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
		tcsbSaleStatisticsService.deleteEntityById(TcsbSaleStatisticsEntity.class, id);
	}
}
