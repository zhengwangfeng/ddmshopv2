package com.weixin.weixinautoresponse.controller;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinaccount.service.WeixinAccountServiceI;
import com.weixin.weixinautoresponse.entity.WeixinAutoresponseEntity;
import com.weixin.weixinautoresponse.service.WeixinAutoresponseServiceI;
import com.weixin.weixinnewstemplate.entity.WeixinNewstemplateEntity;
import com.weixin.weixinnewstemplate.service.WeixinNewstemplateServiceI;
import com.weixin.weixinsubscribe.entity.WeixinSubscribeEntity;
import com.weixin.weixintexttemplate.entity.WeixinTexttemplateEntity;
import com.weixin.weixintexttemplate.service.WeixinTexttemplateServiceI;

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
 * @Description: 微信关键字回复
 * @author onlineGenerator
 * @date 2017-03-28 15:51:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/weixinAutoresponseController")
public class WeixinAutoresponseController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WeixinAutoresponseController.class);

	@Autowired
	private WeixinAutoresponseServiceI weixinAutoresponseService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private WeixinTexttemplateServiceI weixinTexttemplateService;
	@Autowired
	private WeixinNewstemplateServiceI weixinNewstemplateService;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;

	/**
	 * 微信关键字回复列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weixin/weixinautoresponse/weixinAutoresponseList");
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
	public void datagrid(WeixinAutoresponseEntity weixinAutoresponse,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WeixinAutoresponseEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinAutoresponse, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.weixinAutoresponseService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除微信关键字回复
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WeixinAutoresponseEntity weixinAutoresponse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		weixinAutoresponse = systemService.getEntity(WeixinAutoresponseEntity.class, weixinAutoresponse.getId());
		message = "微信关键字回复删除成功";
		try{
			weixinAutoresponseService.delete(weixinAutoresponse);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信关键字回复删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除微信关键字回复
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信关键字回复删除成功";
		try{
			for(String id:ids.split(",")){
				WeixinAutoresponseEntity weixinAutoresponse = systemService.getEntity(WeixinAutoresponseEntity.class, 
				id
				);
				weixinAutoresponseService.delete(weixinAutoresponse);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "微信关键字回复删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加微信关键字回复
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WeixinAutoresponseEntity weixinAutoresponse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信关键字回复添加成功";
		try{
			//获取公众号信息
			List<WeixinAccountEntity>  weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
			WeixinAccountEntity weixinAccountEntity = weixinAccountEntities.get(0);
			String templateName = "";
			if ("text".equals(weixinAutoresponse.getMsgtype())) {
				WeixinTexttemplateEntity textTemplate = this.weixinTexttemplateService.getEntity(WeixinTexttemplateEntity.class, weixinAutoresponse.getTemplateid());
				if (textTemplate != null) {
					templateName = textTemplate.getTemplatename();
				}
			} else if ("news".equals(weixinAutoresponse.getMsgtype())) {
				WeixinNewstemplateEntity newsTemplate = this.weixinNewstemplateService.getEntity(WeixinNewstemplateEntity.class, weixinAutoresponse.getTemplateid());
				if (newsTemplate != null) {
					templateName = newsTemplate.getTemplatename();
				}
			}
			weixinAutoresponse.setAccountid(weixinAccountEntity.getId());
			weixinAutoresponse.setTemplatename(templateName);
			weixinAutoresponseService.save(weixinAutoresponse);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信关键字回复添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新微信关键字回复
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WeixinAutoresponseEntity weixinAutoresponse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信关键字回复更新成功";
		WeixinAutoresponseEntity t = weixinAutoresponseService.get(WeixinAutoresponseEntity.class, weixinAutoresponse.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(weixinAutoresponse, t);
			weixinAutoresponseService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "微信关键字回复更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 微信关键字回复新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WeixinAutoresponseEntity weixinAutoresponse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinAutoresponse.getId())) {
			weixinAutoresponse = weixinAutoresponseService.getEntity(WeixinAutoresponseEntity.class, weixinAutoresponse.getId());
			req.setAttribute("weixinAutoresponsePage", weixinAutoresponse);
		}
		//设置文本消息和图文消息
		List<WeixinTexttemplateEntity> weixinTexttemplateEntities = weixinTexttemplateService.getList(WeixinTexttemplateEntity.class);
		req.setAttribute("textList", weixinTexttemplateEntities);
		List<WeixinNewstemplateEntity> weixinNewstemplateEntities = weixinNewstemplateService.getList(WeixinNewstemplateEntity.class);
		req.setAttribute("newsList", weixinNewstemplateEntities);
		return new ModelAndView("com/weixin/weixinautoresponse/weixinAutoresponse-add");
	}
	/**
	 * 微信关键字回复编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WeixinAutoresponseEntity weixinAutoresponse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinAutoresponse.getId())) {
			weixinAutoresponse = weixinAutoresponseService.getEntity(WeixinAutoresponseEntity.class, weixinAutoresponse.getId());
			req.setAttribute("weixinAutoresponsePage", weixinAutoresponse);
		}
		//设置文本消息和图文消息
		List<WeixinTexttemplateEntity> weixinTexttemplateEntities = weixinTexttemplateService.getList(WeixinTexttemplateEntity.class);
		req.setAttribute("textList", weixinTexttemplateEntities);
		List<WeixinNewstemplateEntity> weixinNewstemplateEntities = weixinNewstemplateService.getList(WeixinNewstemplateEntity.class);
		req.setAttribute("newsList", weixinNewstemplateEntities);
		req.setAttribute("lx", weixinAutoresponse.getMsgtype());
		return new ModelAndView("com/weixin/weixinautoresponse/weixinAutoresponse-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","weixinAutoresponseController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WeixinAutoresponseEntity weixinAutoresponse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WeixinAutoresponseEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinAutoresponse, request.getParameterMap());
		List<WeixinAutoresponseEntity> weixinAutoresponses = this.weixinAutoresponseService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"微信关键字回复");
		modelMap.put(NormalExcelConstants.CLASS,WeixinAutoresponseEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信关键字回复列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,weixinAutoresponses);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WeixinAutoresponseEntity weixinAutoresponse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"微信关键字回复");
    	modelMap.put(NormalExcelConstants.CLASS,WeixinAutoresponseEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信关键字回复列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WeixinAutoresponseEntity> listWeixinAutoresponseEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WeixinAutoresponseEntity.class,params);
				for (WeixinAutoresponseEntity weixinAutoresponse : listWeixinAutoresponseEntitys) {
					weixinAutoresponseService.save(weixinAutoresponse);
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
	public List<WeixinAutoresponseEntity> list() {
		List<WeixinAutoresponseEntity> listWeixinAutoresponses=weixinAutoresponseService.getList(WeixinAutoresponseEntity.class);
		return listWeixinAutoresponses;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WeixinAutoresponseEntity task = weixinAutoresponseService.get(WeixinAutoresponseEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WeixinAutoresponseEntity weixinAutoresponse, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinAutoresponseEntity>> failures = validator.validate(weixinAutoresponse);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinAutoresponseService.save(weixinAutoresponse);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = weixinAutoresponse.getId();
		URI uri = uriBuilder.path("/rest/weixinAutoresponseController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WeixinAutoresponseEntity weixinAutoresponse) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinAutoresponseEntity>> failures = validator.validate(weixinAutoresponse);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinAutoresponseService.saveOrUpdate(weixinAutoresponse);
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
		weixinAutoresponseService.deleteEntityById(WeixinAutoresponseEntity.class, id);
	}
}
