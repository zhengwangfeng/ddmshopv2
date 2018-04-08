package com.tcsb.mealfee.controller;
import com.tcsb.mealfee.entity.TcsbMealFeeEntity;
import com.tcsb.mealfee.service.TcsbMealFeeServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;

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
import org.jeecgframework.tag.vo.datatable.SortDirection;
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
 * @Description: 餐位费管理
 * @author onlineGenerator
 * @date 2017-11-30 16:30:49
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbMealFeeController")
public class TcsbMealFeeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbMealFeeController.class);

	@Autowired
	private TcsbMealFeeServiceI tcsbMealFeeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	


	/**
	 * 餐位费管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/mealfee/tcsbMealFeeList");
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
	public void datagrid(TcsbMealFeeEntity tcsbMealFee,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbMealFeeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMealFee, request.getParameterMap());
		try{
			if (!checkAdmin()) {
				TSUser user = getCurrentUser();
				// 查询商家店铺
				cq.eq("shopId", user.getShopId());
			}
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbMealFeeService.getDataGridReturn(cq, true);
		if (!dataGrid.getResults().isEmpty()) {
			List<TcsbMealFeeEntity> tcsbMealFeeEntities = dataGrid.getResults();
			for (TcsbMealFeeEntity tcsbMealFeeEntity : tcsbMealFeeEntities) {
				TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbMealFeeEntity.getShopId());
				tcsbMealFeeEntity.setShopName(tcsbShopEntity.getName());
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除餐位费管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbMealFeeEntity tcsbMealFee, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbMealFee = systemService.getEntity(TcsbMealFeeEntity.class, tcsbMealFee.getId());
		message = "餐位费管理删除成功";
		try{
			tcsbMealFeeService.delete(tcsbMealFee);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "餐位费管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除餐位费管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "餐位费管理删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbMealFeeEntity tcsbMealFee = systemService.getEntity(TcsbMealFeeEntity.class, 
				id
				);
				tcsbMealFeeService.delete(tcsbMealFee);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "餐位费管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加餐位费管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbMealFeeEntity tcsbMealFee, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "餐位费设置成功";
		try{
			
			// 获取当前用户
			TSUser user = getCurrentUser();
			// 查询商家店铺
			TcsbShopEntity tcsbShopEntity = (TcsbShopEntity) tcsbShopService
					.findUniqueByProperty(TcsbShopEntity.class, "userId",
							user.getId());
			//判断是否有餐位费管理
			List<TcsbMealFeeEntity> tcsbMealFeeEntities = tcsbMealFeeService.findHql("from TcsbMealFeeEntity where shopId =? ", tcsbShopEntity.getId());				
			//如果集合不为0
			if(!tcsbMealFeeEntities.isEmpty()){
				j.setSuccess(false);
				j.setMsg("你已经设置餐位费");
				return j;
			}
			tcsbMealFee.setShopId(tcsbShopEntity.getId());
			tcsbMealFeeService.save(tcsbMealFee);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "设置餐位费失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新餐位费管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbMealFeeEntity tcsbMealFee, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "餐位费管理更新成功";
		TcsbMealFeeEntity t = tcsbMealFeeService.get(TcsbMealFeeEntity.class, tcsbMealFee.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbMealFee, t);
			tcsbMealFeeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "餐位费管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 餐位费管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbMealFeeEntity tcsbMealFee, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbMealFee.getId())) {
			tcsbMealFee = tcsbMealFeeService.getEntity(TcsbMealFeeEntity.class, tcsbMealFee.getId());
			req.setAttribute("tcsbMealFeePage", tcsbMealFee);
		}
		return new ModelAndView("com/tcsb/mealfee/tcsbMealFee-add");
	}
	/**
	 * 餐位费管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbMealFeeEntity tcsbMealFee, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbMealFee.getId())) {
			tcsbMealFee = tcsbMealFeeService.getEntity(TcsbMealFeeEntity.class, tcsbMealFee.getId());
			req.setAttribute("tcsbMealFeePage", tcsbMealFee);
		}
		return new ModelAndView("com/tcsb/mealfee/tcsbMealFee-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbMealFeeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbMealFeeEntity tcsbMealFee,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbMealFeeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMealFee, request.getParameterMap());
		List<TcsbMealFeeEntity> tcsbMealFees = this.tcsbMealFeeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"餐位费管理");
		modelMap.put(NormalExcelConstants.CLASS,TcsbMealFeeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("餐位费管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbMealFees);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbMealFeeEntity tcsbMealFee,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"餐位费管理");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbMealFeeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("餐位费管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbMealFeeEntity> listTcsbMealFeeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbMealFeeEntity.class,params);
				for (TcsbMealFeeEntity tcsbMealFee : listTcsbMealFeeEntitys) {
					tcsbMealFeeService.save(tcsbMealFee);
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
	public List<TcsbMealFeeEntity> list() {
		List<TcsbMealFeeEntity> listTcsbMealFees=tcsbMealFeeService.getList(TcsbMealFeeEntity.class);
		return listTcsbMealFees;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbMealFeeEntity task = tcsbMealFeeService.get(TcsbMealFeeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbMealFeeEntity tcsbMealFee, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbMealFeeEntity>> failures = validator.validate(tcsbMealFee);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbMealFeeService.save(tcsbMealFee);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbMealFee.getId();
		URI uri = uriBuilder.path("/rest/tcsbMealFeeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbMealFeeEntity tcsbMealFee) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbMealFeeEntity>> failures = validator.validate(tcsbMealFee);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbMealFeeService.saveOrUpdate(tcsbMealFee);
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
		tcsbMealFeeService.deleteEntityById(TcsbMealFeeEntity.class, id);
	}
}
