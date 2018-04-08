package com.tcsb.shopfullcuttemplate.controller;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopfullcuttemplate.service.TcsbShopFullcutTemplateServiceI;

import java.util.ArrayList;
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
 * @Description: 店铺活动满减模版
 * @author onlineGenerator
 * @date 2017-06-14 14:13:17
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopFullcutTemplateController")
public class TcsbShopFullcutTemplateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopFullcutTemplateController.class);

	@Autowired
	private TcsbShopFullcutTemplateServiceI tcsbShopFullcutTemplateService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	


	/**
	 * 店铺活动满减模版列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopfullcuttemplate/tcsbShopFullcutTemplateList");
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
	public void datagrid(TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopFullcutTemplateEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopFullcutTemplate, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = getCurrentUser();
			if (!checkAdmin()) {
				cq.eq("shopId", user.getShopId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopFullcutTemplateService.getDataGridReturn(cq, true);
		
		 for (Object o : dataGrid.getResults()) {
	            if (o instanceof TcsbShopFullcutTemplateEntity) {
	            	TcsbShopFullcutTemplateEntity cfe = (TcsbShopFullcutTemplateEntity) o;
	            	TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, cfe.getShopId());
	            	cfe.setShopName(tcsbShopEntity.getName());
	            }
	        }
		
		
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除店铺活动满减模版
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopFullcutTemplate = systemService.getEntity(TcsbShopFullcutTemplateEntity.class, tcsbShopFullcutTemplate.getId());
		message = "店铺活动满减模版删除成功";
		try{
			tcsbShopFullcutTemplateService.delete(tcsbShopFullcutTemplate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺活动满减模版删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除店铺活动满减模版
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺活动满减模版删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate = systemService.getEntity(TcsbShopFullcutTemplateEntity.class, 
				id
				);
				tcsbShopFullcutTemplateService.delete(tcsbShopFullcutTemplate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺活动满减模版删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加店铺活动满减模版
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺活动满减模版添加成功";
		try{
			TSUser tsUser = getCurrentUser();
			if (!checkAdmin()) {
				TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", tsUser.getId());
				tcsbShopFullcutTemplate.setShopId(tcsbShopEntity.getId());
			}
			tcsbShopFullcutTemplateService.save(tcsbShopFullcutTemplate);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺活动满减模版添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新店铺活动满减模版
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺活动满减模版更新成功";
		TcsbShopFullcutTemplateEntity t = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, tcsbShopFullcutTemplate.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopFullcutTemplate, t);
			tcsbShopFullcutTemplateService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺活动满减模版更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺活动满减模版新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopFullcutTemplate.getId())) {
			tcsbShopFullcutTemplate = tcsbShopFullcutTemplateService.getEntity(TcsbShopFullcutTemplateEntity.class, tcsbShopFullcutTemplate.getId());
			req.setAttribute("tcsbShopFullcutTemplatePage", tcsbShopFullcutTemplate);
		}
		return new ModelAndView("com/tcsb/shopfullcuttemplate/tcsbShopFullcutTemplate-add");
	}
	/**
	 * 店铺活动满减模版编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopFullcutTemplate.getId())) {
			tcsbShopFullcutTemplate = tcsbShopFullcutTemplateService.getEntity(TcsbShopFullcutTemplateEntity.class, tcsbShopFullcutTemplate.getId());
			req.setAttribute("tcsbShopFullcutTemplatePage", tcsbShopFullcutTemplate);
		}
		return new ModelAndView("com/tcsb/shopfullcuttemplate/tcsbShopFullcutTemplate-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopFullcutTemplateController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopFullcutTemplateEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopFullcutTemplate, request.getParameterMap());
		List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplates = this.tcsbShopFullcutTemplateService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺活动满减模版");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopFullcutTemplateEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺活动满减模版列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopFullcutTemplates);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"店铺活动满减模版");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopFullcutTemplateEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺活动满减模版列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopFullcutTemplateEntity> listTcsbShopFullcutTemplateEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopFullcutTemplateEntity.class,params);
				for (TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate : listTcsbShopFullcutTemplateEntitys) {
					tcsbShopFullcutTemplateService.save(tcsbShopFullcutTemplate);
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
	public List<TcsbShopFullcutTemplateEntity> list() {
		List<TcsbShopFullcutTemplateEntity> listTcsbShopFullcutTemplates=tcsbShopFullcutTemplateService.getList(TcsbShopFullcutTemplateEntity.class);
		return listTcsbShopFullcutTemplates;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopFullcutTemplateEntity task = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopFullcutTemplateEntity>> failures = validator.validate(tcsbShopFullcutTemplate);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopFullcutTemplateService.save(tcsbShopFullcutTemplate);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopFullcutTemplate.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopFullcutTemplateController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopFullcutTemplateEntity>> failures = validator.validate(tcsbShopFullcutTemplate);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopFullcutTemplateService.saveOrUpdate(tcsbShopFullcutTemplate);
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
		tcsbShopFullcutTemplateService.deleteEntityById(TcsbShopFullcutTemplateEntity.class, id);
	}
}
