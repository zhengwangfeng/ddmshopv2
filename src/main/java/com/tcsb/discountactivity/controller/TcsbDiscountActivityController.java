package com.tcsb.discountactivity.controller;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.discountactivity.service.TcsbDiscountActivityServiceI;
import com.tcsb.discountactivity.vo.TcsbDiscountActivityEntityVo;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.fullcuttemplate.service.TcsbFullcutTemplateServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopfullcuttemplate.service.TcsbShopFullcutTemplateServiceI;

import java.util.ArrayList;
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
 * @Description: 优惠活动
 * @author onlineGenerator
 * @date 2017-04-13 17:56:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbDiscountActivityController")
public class TcsbDiscountActivityController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbDiscountActivityController.class);

	@Autowired
	private TcsbDiscountActivityServiceI tcsbDiscountActivityService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbFullcutTemplateServiceI tcsbFullcutTemplateService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbShopFullcutTemplateServiceI tcsbShopFullcutTemplateService;


	/**
	 * 优惠活动列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/discountactivity/tcsbDiscountActivityList");
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

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbDiscountActivityEntity tcsbDiscountActivity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		CriteriaQuery cq = new CriteriaQuery(TcsbDiscountActivityEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDiscountActivity, request.getParameterMap());
		try{
			TSUser user = getCurrentUser();
			if (!checkAdmin()) {
				cq.eq("shopId", user.getShopId());
			}
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbDiscountActivityService.getDataGridReturn(cq, true);
		 for (Object o : dataGrid.getResults()) {
	            if (o instanceof TcsbDiscountActivityEntity) {
	            	TcsbDiscountActivityEntity cfe = (TcsbDiscountActivityEntity) o;
	            	//查询模版和店铺
	            	TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, cfe.getFullcutTemplateId());
	            	TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbShopFullcutTemplateEntity.getShopId());
	            	cfe.setFullcutTemplateIdName("满"+tcsbShopFullcutTemplateEntity.getTotal()+"减"+tcsbShopFullcutTemplateEntity.getDiscount());
	            	cfe.setShopName(tcsbShopEntity.getName());
	            }
	        }
		
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除优惠活动
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbDiscountActivityEntity tcsbDiscountActivity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbDiscountActivity = systemService.getEntity(TcsbDiscountActivityEntity.class, tcsbDiscountActivity.getId());
		message = "优惠活动删除成功";
		try{
			tcsbDiscountActivityService.delete(tcsbDiscountActivity);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠活动删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除优惠活动
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "优惠活动删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbDiscountActivityEntity tcsbDiscountActivity = systemService.getEntity(TcsbDiscountActivityEntity.class, 
				id
				);
				tcsbDiscountActivityService.delete(tcsbDiscountActivity);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠活动删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加优惠活动
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbDiscountActivityEntity tcsbDiscountActivity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "优惠活动添加成功";
		try{
			TSUser tsUser = getCurrentUser();
			TcsbShopEntity tcsbShopEntity = systemService.findUniqueByProperty(TcsbShopEntity.class, "userId", tsUser.getId());
			tcsbDiscountActivity.setShopId(tcsbShopEntity.getId());
			tcsbDiscountActivityService.save(tcsbDiscountActivity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠活动添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新优惠活动
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbDiscountActivityEntity tcsbDiscountActivity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "优惠活动更新成功";
		TcsbDiscountActivityEntity t = tcsbDiscountActivityService.get(TcsbDiscountActivityEntity.class, tcsbDiscountActivity.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbDiscountActivity, t);
			tcsbDiscountActivityService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "优惠活动更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 优惠活动新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbDiscountActivityEntity tcsbDiscountActivity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDiscountActivity.getId())) {
			tcsbDiscountActivity = tcsbDiscountActivityService.getEntity(TcsbDiscountActivityEntity.class, tcsbDiscountActivity.getId());
			req.setAttribute("tcsbDiscountActivityPage", tcsbDiscountActivity);
		}
		TSUser tsUser = getCurrentUser();
		/*List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = tcsbFullcutTemplateService.findByProperty(TcsbFullcutTemplateEntity.class, "shopId", tcsbShopEntity.getId());*/
		//update by jimmy begin店铺满减模版
		List<TcsbShopFullcutTemplateEntity> tcsbFullcutTemplateEntities = tcsbShopFullcutTemplateService.findByProperty(TcsbShopFullcutTemplateEntity.class, "shopId", tsUser.getShopId());
		//update by jimmy end
		req.setAttribute("tcsbFullcutTemplateEntities", tcsbFullcutTemplateEntities);
		return new ModelAndView("com/tcsb/discountactivity/tcsbDiscountActivity-add");
	}
	/**
	 * 优惠活动编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbDiscountActivityEntity tcsbDiscountActivity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDiscountActivity.getId())) {
			tcsbDiscountActivity = tcsbDiscountActivityService.getEntity(TcsbDiscountActivityEntity.class, tcsbDiscountActivity.getId());
			req.setAttribute("tcsbDiscountActivityPage", tcsbDiscountActivity);
		}
		TSUser tsUser = getCurrentUser();
		//update by jimmy begin店铺满减模版
		List<TcsbShopFullcutTemplateEntity> tcsbFullcutTemplateEntities = tcsbShopFullcutTemplateService.findByProperty(TcsbShopFullcutTemplateEntity.class, "shopId", tsUser.getShopId());
		//update by jimmy end
		req.setAttribute("tcsbFullcutTemplateEntities", tcsbFullcutTemplateEntities);
		return new ModelAndView("com/tcsb/discountactivity/tcsbDiscountActivity-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbDiscountActivityController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbDiscountActivityEntity tcsbDiscountActivity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDiscountActivityEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDiscountActivity, request.getParameterMap());
		List<TcsbDiscountActivityEntity> tcsbDiscountActivitys = this.tcsbDiscountActivityService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"优惠活动");
		modelMap.put(NormalExcelConstants.CLASS,TcsbDiscountActivityEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("优惠活动列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbDiscountActivitys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbDiscountActivityEntity tcsbDiscountActivity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"优惠活动");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbDiscountActivityEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("优惠活动列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbDiscountActivityEntity> listTcsbDiscountActivityEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbDiscountActivityEntity.class,params);
				for (TcsbDiscountActivityEntity tcsbDiscountActivity : listTcsbDiscountActivityEntitys) {
					tcsbDiscountActivityService.save(tcsbDiscountActivity);
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
	public List<TcsbDiscountActivityEntity> list() {
		List<TcsbDiscountActivityEntity> listTcsbDiscountActivitys=tcsbDiscountActivityService.getList(TcsbDiscountActivityEntity.class);
		return listTcsbDiscountActivitys;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbDiscountActivityEntity task = tcsbDiscountActivityService.get(TcsbDiscountActivityEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbDiscountActivityEntity tcsbDiscountActivity, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDiscountActivityEntity>> failures = validator.validate(tcsbDiscountActivity);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDiscountActivityService.save(tcsbDiscountActivity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbDiscountActivity.getId();
		URI uri = uriBuilder.path("/rest/tcsbDiscountActivityController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbDiscountActivityEntity tcsbDiscountActivity) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDiscountActivityEntity>> failures = validator.validate(tcsbDiscountActivity);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDiscountActivityService.saveOrUpdate(tcsbDiscountActivity);
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
		tcsbDiscountActivityService.deleteEntityById(TcsbDiscountActivityEntity.class, id);
	}
}
