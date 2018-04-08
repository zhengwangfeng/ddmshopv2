package com.tcsb.foodstandard.controller;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.foodstandard.service.TcsbFoodStandardServiceI;
import com.tcsb.foodstandardrelationship.entity.TcsbFoodStandardRelationshipEntity;
import com.tcsb.foodstandardrelationship.service.TcsbFoodStandardRelationshipServiceI;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;

import java.util.ArrayList;
import java.util.HashSet;
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
import org.hibernate.criterion.Restrictions;
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
 * @Description: 食物规格
 * @author onlineGenerator
 * @date 2017-11-27 13:50:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbFoodStandardController")
public class TcsbFoodStandardController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbFoodStandardController.class);

	@Autowired
	private TcsbFoodStandardServiceI tcsbFoodStandardService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbFoodStandardRelationshipServiceI tcsbFoodStandardRelationshipService;
	

	/**
	 * 食物规格列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/foodstandard/tcsbFoodStandardList");
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
	public void datagrid(TcsbFoodStandardEntity tcsbFoodStandard,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodStandardEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodStandard, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbFoodStandardService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "foodStandardDatagrid")
	public void foodStandardDatagrid(TcsbFoodStandardEntity tcsbFoodStandard,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodStandardEntity.class, dataGrid);
		
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodStandard, request.getParameterMap());
		try{
		//自定义追加查询条件
			String foodId = request.getParameter("foodId");
			Set<String> set = new HashSet<>();
			if (!StringUtil.isEmpty(foodId)) {
				List<TcsbFoodStandardRelationshipEntity> tcsbFoodStandardRelationshipEntities = tcsbFoodStandardRelationshipService.findHql("from TcsbFoodStandardRelationshipEntity where foodId = ?",foodId);
				if (!tcsbFoodStandardRelationshipEntities.isEmpty()) {
					for (TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationshipEntity : tcsbFoodStandardRelationshipEntities) {
						set.add(tcsbFoodStandardRelationshipEntity.getFoodStandardId());
					}
					cq.add(Restrictions.in("id", set));
				}else {
					set.add("-1");
					cq.add(Restrictions.in("id", set));
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbFoodStandardService.getDataGridReturn(cq, true);
		if (!dataGrid.getResults().isEmpty()) {
			List<TcsbFoodStandardEntity> tcsbFoodStandardEntities = dataGrid.getResults();
			for (TcsbFoodStandardEntity tcsbFoodStandardEntity : tcsbFoodStandardEntities) {
				if (tcsbFoodStandardEntity.getStatus()==1) {
					tcsbFoodStandardEntity.setStatusName("启用中");
				}else {
					tcsbFoodStandardEntity.setStatusName("已禁用");
				}
			}
			
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 启用或禁用规格
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "updateStatus")
	@ResponseBody
	public AjaxJson updateStatus(TcsbFoodStandardEntity tcsbFoodStandardEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "恭喜你改变状态成功";
		try{
			if (StringUtil.isNotEmpty(tcsbFoodStandardEntity.getId())) {
				tcsbFoodStandardEntity = tcsbFoodStandardService.getEntity(TcsbFoodStandardEntity.class, tcsbFoodStandardEntity.getId());
			}
			tcsbFoodStandardEntity.setStatus(Integer.parseInt(request.getParameter("status")));
			tcsbFoodStandardService.saveOrUpdate(tcsbFoodStandardEntity);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 删除食物规格
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbFoodStandardEntity tcsbFoodStandard, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbFoodStandard = systemService.getEntity(TcsbFoodStandardEntity.class, tcsbFoodStandard.getId());
		message = "食物规格删除成功";
		try{
			tcsbFoodStandardService.delete(tcsbFoodStandard);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "食物规格删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除食物规格
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物规格删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbFoodStandardEntity tcsbFoodStandard = systemService.getEntity(TcsbFoodStandardEntity.class, 
				id
				);
				tcsbFoodStandardService.delete(tcsbFoodStandard);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "食物规格删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加食物规格
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbFoodStandardEntity tcsbFoodStandard, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物规格添加成功";
		try{
			String foodId = request.getParameter("foodId");
			tcsbFoodStandard.setStatus(1);
			tcsbFoodStandardService.save(tcsbFoodStandard);
			TcsbFoodStandardRelationshipEntity tcsbFoodStandardRelationshipEntity = new TcsbFoodStandardRelationshipEntity();
			tcsbFoodStandardRelationshipEntity.setFoodId(foodId);
			tcsbFoodStandardRelationshipEntity.setFoodStandardId(tcsbFoodStandard.getId());
			tcsbFoodStandardRelationshipService.save(tcsbFoodStandardRelationshipEntity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "食物规格添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新食物规格
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbFoodStandardEntity tcsbFoodStandard, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物规格更新成功";
		TcsbFoodStandardEntity t = tcsbFoodStandardService.get(TcsbFoodStandardEntity.class, tcsbFoodStandard.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFoodStandard, t);
			tcsbFoodStandardService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "食物规格更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 食物规格新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbFoodStandardEntity tcsbFoodStandard, HttpServletRequest req) {
		String foodId = req.getParameter("foodId");
		req.setAttribute("foodId", foodId);
		if (StringUtil.isNotEmpty(tcsbFoodStandard.getId())) {
			tcsbFoodStandard = tcsbFoodStandardService.getEntity(TcsbFoodStandardEntity.class, tcsbFoodStandard.getId());
			req.setAttribute("tcsbFoodStandardPage", tcsbFoodStandard);
		}
		return new ModelAndView("com/tcsb/foodstandard/tcsbFoodStandard-add");
	}
	/**
	 * 食物规格编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbFoodStandardEntity tcsbFoodStandard, HttpServletRequest req) {
		String foodId = req.getParameter("foodId");
		req.setAttribute("foodId", foodId);
		if (StringUtil.isNotEmpty(tcsbFoodStandard.getId())) {
			tcsbFoodStandard = tcsbFoodStandardService.getEntity(TcsbFoodStandardEntity.class, tcsbFoodStandard.getId());
			req.setAttribute("tcsbFoodStandardPage", tcsbFoodStandard);
		}
		return new ModelAndView("com/tcsb/foodstandard/tcsbFoodStandard-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbFoodStandardController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbFoodStandardEntity tcsbFoodStandard,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodStandardEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodStandard, request.getParameterMap());
		List<TcsbFoodStandardEntity> tcsbFoodStandards = this.tcsbFoodStandardService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"食物规格");
		modelMap.put(NormalExcelConstants.CLASS,TcsbFoodStandardEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("食物规格列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbFoodStandards);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbFoodStandardEntity tcsbFoodStandard,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"食物规格");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbFoodStandardEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("食物规格列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbFoodStandardEntity> listTcsbFoodStandardEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbFoodStandardEntity.class,params);
				for (TcsbFoodStandardEntity tcsbFoodStandard : listTcsbFoodStandardEntitys) {
					tcsbFoodStandardService.save(tcsbFoodStandard);
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
	public List<TcsbFoodStandardEntity> list() {
		List<TcsbFoodStandardEntity> listTcsbFoodStandards=tcsbFoodStandardService.getList(TcsbFoodStandardEntity.class);
		return listTcsbFoodStandards;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbFoodStandardEntity task = tcsbFoodStandardService.get(TcsbFoodStandardEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbFoodStandardEntity tcsbFoodStandard, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodStandardEntity>> failures = validator.validate(tcsbFoodStandard);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodStandardService.save(tcsbFoodStandard);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbFoodStandard.getId();
		URI uri = uriBuilder.path("/rest/tcsbFoodStandardController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbFoodStandardEntity tcsbFoodStandard) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodStandardEntity>> failures = validator.validate(tcsbFoodStandard);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodStandardService.saveOrUpdate(tcsbFoodStandard);
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
		tcsbFoodStandardService.deleteEntityById(TcsbFoodStandardEntity.class, id);
	}
}
