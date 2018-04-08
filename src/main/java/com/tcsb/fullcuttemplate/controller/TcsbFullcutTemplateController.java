package com.tcsb.fullcuttemplate.controller;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.coupon.service.TcsbCouponServiceI;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.fullcuttemplate.service.TcsbFullcutTemplateServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopsmsservice.entity.TcsbShopSmsServiceEntity;
import com.tcsb.shopsmsservice.service.TcsbShopSmsServiceServiceI;
import com.weixin.weixinuser.entity.WeixinUserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import java.io.Serializable;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SaleSmsClient;

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
 * @Description: 满减模版
 * @author onlineGenerator
 * @date 2017-04-13 17:49:50
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbFullcutTemplateController")
public class TcsbFullcutTemplateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbFullcutTemplateController.class);

	@Autowired
	private TcsbFullcutTemplateServiceI tcsbFullcutTemplateService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbShopSmsServiceServiceI tcsbShopSmsServiceService;
	@Autowired
	private TcsbCouponServiceI tcsbCouponService;
	


	/**
	 * 满减模版列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/fullcuttemplate/tcsbFullcutTemplateList");
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
	public void datagrid(TcsbFullcutTemplateEntity tcsbFullcutTemplate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFullcutTemplateEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFullcutTemplate, request.getParameterMap());
		try{
			TSUser user = getCurrentUser();
			if (!checkAdmin()) {
				cq.eq("shopId", user.getShopId());
			}
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.eq("isShow", "1");
		cq.add();
		this.tcsbFullcutTemplateService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除满减模版
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbFullcutTemplateEntity tcsbFullcutTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbFullcutTemplate = systemService.getEntity(TcsbFullcutTemplateEntity.class, tcsbFullcutTemplate.getId());
		message = "满减模版删除成功";
		try{
			tcsbFullcutTemplate.setIsShow("0");
			tcsbFullcutTemplateService.saveOrUpdate(tcsbFullcutTemplate);
			//tcsbFullcutTemplateService.delete(tcsbFullcutTemplate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "满减模版删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除满减模版
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "满减模版删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbFullcutTemplateEntity tcsbFullcutTemplate = systemService.getEntity(TcsbFullcutTemplateEntity.class, 
				id
				);
				tcsbFullcutTemplateService.delete(tcsbFullcutTemplate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "满减模版删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加满减模版
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbFullcutTemplateEntity tcsbFullcutTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "满减模版添加成功";
		try{
			TSUser tsUser = getCurrentUser();
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", tsUser.getId());
			tcsbFullcutTemplate.setShopId(tcsbShopEntity.getId());
			tcsbFullcutTemplate.setIsShow("1");
			tcsbFullcutTemplate.setCreateTime(new Date());
			tcsbFullcutTemplateService.save(tcsbFullcutTemplate);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "满减模版添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	@RequestMapping(params = "doSendAdd")
	@ResponseBody
	public String doSendAdd(TcsbFullcutTemplateEntity tcsbFullcutTemplate, HttpServletRequest request,String userids) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "满减模版添加成功";
		try{
			TSUser tsUser = getCurrentUser();
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", tsUser.getId());
			if("1".equals(tcsbFullcutTemplate.getIsShow())){
				//保存并发送
				String[] useridsarray = userids.split(",");
				if(useridsarray.length>0){
					//发送的用户不为空
					//检测商家要发送短信剩余条数是否足够
					TcsbShopSmsServiceEntity tcsbShopSmsServiceEntity = tcsbShopService.findUniqueByProperty(TcsbShopSmsServiceEntity.class, "shopId", tcsbShopEntity.getId());
					if (tcsbShopSmsServiceEntity!=null) {
						int count =tcsbShopSmsServiceEntity.getCount();
						if (count < useridsarray.length) {
							message = "短信剩余"+count+"条,不足"+useridsarray.length+"条";
						}
						else {
							//保存优惠券
							tcsbFullcutTemplate.setIsShow("1");
							tcsbFullcutTemplate.setShopId(tcsbShopEntity.getId());
							tcsbFullcutTemplate.setCreateTime(new Date());
							Serializable id = tcsbFullcutTemplateService.save(tcsbFullcutTemplate);
							systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
							//短信条数足够，群发优惠券
							try {
								for (int i = 0; i < useridsarray.length; i++) {
									/////////保存发送的优惠券信息，并发送短信提醒
									//保存用户优惠价信息
									//根据用户手机号获取用户ID
									List<WeixinUserEntity> weixinUserEntitys = systemService.findHql("from WeixinUserEntity where openid =? and shopId = ?", useridsarray[i],tcsbShopEntity.getId());
									TcsbCouponEntity tcsbCoupon = new TcsbCouponEntity();
									tcsbCoupon.setUserId(weixinUserEntitys.get(0).getOpenid());
									tcsbCoupon.setShopId(tcsbShopEntity.getId());
									tcsbCoupon.setUseStatus("0");
									//初始化使用范围
									initUseRange(tcsbFullcutTemplate,
											tcsbCoupon);
									String fullcutTemplateId = id.toString();
									tcsbCoupon.setFullcutTemplateId(fullcutTemplateId);
									//获取优惠券信息
									TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbShopService.get(TcsbFullcutTemplateEntity.class,fullcutTemplateId );
									tcsbCoupon.setFullcutTemplateId(fullcutTemplateId);
									Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
									String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); 
									int days = 0;
									//todo
									if (dateUnit.equals("year")) {
										days = userPeriod*DateUtils.getCurrentYearDays();
									}else if (dateUnit.equals("month")) {
										days = userPeriod*DateUtils.getCurrentMonthDay();
									}else {
										days = userPeriod;
									}
									String expiryDate = DateUtils.getAfterDayDate(String.valueOf(days));
									tcsbCoupon.setExpiryDate(DateUtils.parseDate(expiryDate, "yyyy-MM-dd HH:mm:ss"));
									
									tcsbCouponService.save(tcsbCoupon);//保持信息
									
									if (dateUnit.equals("month")) {
										dateUnit ="个月";
									}
									if (dateUnit.equals("year")) {
										dateUnit ="年";
									}
									if (dateUnit.equals("day")) {
										dateUnit ="日";
									}
									//拼接发送的短信内容
									String content = "恭喜您获得"+tcsbShopEntity.getName()+"的"+tcsbFullcutTemplateEntity.getDiscount()+"元优惠券一张，有效期"+userPeriod+""+dateUnit+"，欢迎到店使用。";
									//SaleSmsClient.sendMessageForContent(weixinUserEntitys.get(0).getMobile(),content);//发送短信
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							tcsbShopSmsServiceEntity.setCount(tcsbShopSmsServiceEntity.getCount()-useridsarray.length);
							tcsbShopSmsServiceService.updateEntitie(tcsbShopSmsServiceEntity);
						}
					}
					
				}else{
					message="发送的用户为空";
				}
				
				
			}else{
				if("2".equals(tcsbFullcutTemplate.getIsShow())){
					//不发送只保存到模板库
					tcsbFullcutTemplate.setIsShow("1");
					tcsbFullcutTemplate.setShopId(tcsbShopEntity.getId());
					tcsbFullcutTemplateService.save(tcsbFullcutTemplate);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}else{
					//只发送
					String[] useridsarray = userids.split(",");
					if(useridsarray.length>0){
						//发送的用户不为空
						//检测商家要发送短信剩余条数是否足够
						TcsbShopSmsServiceEntity tcsbShopSmsServiceEntity = tcsbShopService.findUniqueByProperty(TcsbShopSmsServiceEntity.class, "shopId", tcsbShopEntity.getId());
						if (tcsbShopSmsServiceEntity!=null) {
							int count =tcsbShopSmsServiceEntity.getCount();
							if (count < useridsarray.length) {
								message = "短信剩余"+count+"条,不足"+useridsarray.length+"条";
							}
							else {
								//保存优惠券
								tcsbFullcutTemplate.setIsShow("0");
								tcsbFullcutTemplate.setShopId(tcsbShopEntity.getId());
								Serializable id = tcsbFullcutTemplateService.save(tcsbFullcutTemplate);
								systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
								//短信条数足够，群发优惠券
								try {
									for (int i = 0; i < useridsarray.length; i++) {
										/////////保存发送的优惠券信息，并发送短信提醒
										//保存用户优惠价信息
										//根据用户手机号获取用户ID
										List<WeixinUserEntity> weixinUserEntitys = systemService.findHql("from WeixinUserEntity where openid =? and shopId = ?", useridsarray[i],tcsbShopEntity.getId());
										TcsbCouponEntity tcsbCoupon = new TcsbCouponEntity();
										tcsbCoupon.setUserId(weixinUserEntitys.get(0).getOpenid());
										tcsbCoupon.setShopId(tcsbShopEntity.getId());
										tcsbCoupon.setUseStatus("0");
										String fullcutTemplateId = id.toString();
										tcsbCoupon.setFullcutTemplateId(fullcutTemplateId);
										//初始化使用范围
										initUseRange(tcsbFullcutTemplate,
												tcsbCoupon);
										//获取优惠券信息
										TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbShopService.get(TcsbFullcutTemplateEntity.class,fullcutTemplateId );
										tcsbCoupon.setFullcutTemplateId(fullcutTemplateId);
										Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
										String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); 
										int days = 0;
										//todo
										if (dateUnit.equals("year")) {
											days = userPeriod*DateUtils.getCurrentYearDays();
										}else if (dateUnit.equals("month")) {
											days = userPeriod*DateUtils.getCurrentMonthDay();
										}else {
											days = userPeriod;
										}
										String expiryDate = DateUtils.getAfterDayDate(String.valueOf(days));
										tcsbCoupon.setExpiryDate(DateUtils.parseDate(expiryDate, "yyyy-MM-dd HH:mm:ss"));
										
										
										tcsbCouponService.save(tcsbCoupon);//保持信息
										if (dateUnit.equals("month")) {
											dateUnit ="个月";
										}
										if (dateUnit.equals("year")) {
											dateUnit ="年";
										}
										if (dateUnit.equals("day")) {
											dateUnit ="日";
										}
										//拼接发送的短信内容
										String content = "恭喜您获得"+tcsbShopEntity.getName()+"的"+tcsbFullcutTemplateEntity.getDiscount()+"元优惠券一张，有效期"+userPeriod+""+dateUnit+"，欢迎到店使用。";
										//SaleSmsClient.sendMessageForContent(weixinUserEntitys.get(0).getMobile(),content);//发送短信
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								tcsbShopSmsServiceEntity.setCount(tcsbShopSmsServiceEntity.getCount()-useridsarray.length);
								tcsbShopSmsServiceService.updateEntitie(tcsbShopSmsServiceEntity);
							}
						}
						
					}else{
						message="发送的用户为空";
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "满减模版添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return message;
	}

	private void initUseRange(TcsbFullcutTemplateEntity tcsbFullcutTemplate,
			TcsbCouponEntity tcsbCoupon) {
		if ("0".equals(tcsbFullcutTemplate.getUseRange())) {
			tcsbCoupon.setUseRange(tcsbFullcutTemplate.getUseRange());
		}else {
			tcsbCoupon.setUseRange(tcsbFullcutTemplate.getUseRange());
			tcsbCoupon.setFoodTypeId(tcsbFullcutTemplate.getFoodTypeId());
			tcsbCoupon.setFoodId(tcsbFullcutTemplate.getFoodId());
		}
	}
	
	/**
	 * 更新满减模版
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbFullcutTemplateEntity tcsbFullcutTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "满减模版更新成功";
		TcsbFullcutTemplateEntity t = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbFullcutTemplate.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFullcutTemplate, t);
			tcsbFullcutTemplateService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "满减模版更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 获取满减模版
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "getFullcutTemplate")
	@ResponseBody
	public AjaxJson getFullcutTemplate(TcsbFullcutTemplateEntity tcsbFullcutTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "获取满减模版成功";
		List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
		try {
			TSUser tsUser = getCurrentUser();
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", tsUser.getId());
			tcsbFullcutTemplateEntities = tcsbFullcutTemplateService.findHql("from TcsbFullcutTemplateEntity where shopId = ? order by createTime desc", tcsbShopEntity.getId());
		} catch (Exception e) {
			e.printStackTrace();
			message = "满减模版更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setSuccess(true);
		j.setObj(tcsbFullcutTemplateEntities);
		return j;
	}
	

	/**
	 * 满减模版新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbFullcutTemplateEntity tcsbFullcutTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFullcutTemplate.getId())) {
			tcsbFullcutTemplate = tcsbFullcutTemplateService.getEntity(TcsbFullcutTemplateEntity.class, tcsbFullcutTemplate.getId());
			req.setAttribute("tcsbFullcutTemplatePage", tcsbFullcutTemplate);
		}
		return new ModelAndView("com/tcsb/fullcuttemplate/tcsbFullcutTemplate-add");
	}
	
	@RequestMapping(params = "goSendAdd")
	public ModelAndView goSendAdd(TcsbFullcutTemplateEntity tcsbFullcutTemplate, HttpServletRequest req,String userids) {
		if (StringUtil.isNotEmpty(tcsbFullcutTemplate.getId())) {
			tcsbFullcutTemplate = tcsbFullcutTemplateService.getEntity(TcsbFullcutTemplateEntity.class, tcsbFullcutTemplate.getId());
			req.setAttribute("tcsbFullcutTemplatePage", tcsbFullcutTemplate);
		}
		req.setAttribute("userids", userids);
		return new ModelAndView("com/tcsb/fullcuttemplate/tcsbFullcutTemplate-goSendAdd");
	}
	/**
	 * 满减模版编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbFullcutTemplateEntity tcsbFullcutTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFullcutTemplate.getId())) {
			tcsbFullcutTemplate = tcsbFullcutTemplateService.getEntity(TcsbFullcutTemplateEntity.class, tcsbFullcutTemplate.getId());
			req.setAttribute("tcsbFullcutTemplatePage", tcsbFullcutTemplate);
		}
		return new ModelAndView("com/tcsb/fullcuttemplate/tcsbFullcutTemplate-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbFullcutTemplateController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbFullcutTemplateEntity tcsbFullcutTemplate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFullcutTemplateEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFullcutTemplate, request.getParameterMap());
		List<TcsbFullcutTemplateEntity> tcsbFullcutTemplates = this.tcsbFullcutTemplateService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"满减模版");
		modelMap.put(NormalExcelConstants.CLASS,TcsbFullcutTemplateEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("满减模版列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbFullcutTemplates);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbFullcutTemplateEntity tcsbFullcutTemplate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"满减模版");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbFullcutTemplateEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("满减模版列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbFullcutTemplateEntity> listTcsbFullcutTemplateEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbFullcutTemplateEntity.class,params);
				for (TcsbFullcutTemplateEntity tcsbFullcutTemplate : listTcsbFullcutTemplateEntitys) {
					tcsbFullcutTemplateService.save(tcsbFullcutTemplate);
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
	public List<TcsbFullcutTemplateEntity> list() {
		List<TcsbFullcutTemplateEntity> listTcsbFullcutTemplates=tcsbFullcutTemplateService.getList(TcsbFullcutTemplateEntity.class);
		return listTcsbFullcutTemplates;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbFullcutTemplateEntity task = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbFullcutTemplateEntity tcsbFullcutTemplate, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFullcutTemplateEntity>> failures = validator.validate(tcsbFullcutTemplate);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFullcutTemplateService.save(tcsbFullcutTemplate);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbFullcutTemplate.getId();
		URI uri = uriBuilder.path("/rest/tcsbFullcutTemplateController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbFullcutTemplateEntity tcsbFullcutTemplate) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFullcutTemplateEntity>> failures = validator.validate(tcsbFullcutTemplate);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFullcutTemplateService.saveOrUpdate(tcsbFullcutTemplate);
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
		tcsbFullcutTemplateService.deleteEntityById(TcsbFullcutTemplateEntity.class, id);
	}
}
