package com.tcsb.tcsbshoppaymode.controller;
import com.tcsb.tcsbshoppaymode.entity.TcsbShopPaymodeEntity;
import com.tcsb.tcsbshoppaymode.service.TcsbShopPaymodeServiceI;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.ComboTree;
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
 * @Description: 店铺支付方式
 * @author onlineGenerator
 * @date 2017-10-30 12:00:14
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopPaymodeController")
public class TcsbShopPaymodeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopPaymodeController.class);

	@Autowired
	private TcsbShopPaymodeServiceI tcsbShopPaymodeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 店铺支付方式列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/tcsbshoppaymode/tcsbShopPaymodeList");
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
	public void datagrid(TcsbShopPaymodeEntity tcsbShopPaymode,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopPaymodeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopPaymode, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopPaymodeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除店铺支付方式
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopPaymodeEntity tcsbShopPaymode, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopPaymode = systemService.getEntity(TcsbShopPaymodeEntity.class, tcsbShopPaymode.getId());
		message = "店铺支付方式删除成功";
		try{
			tcsbShopPaymodeService.delete(tcsbShopPaymode);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺支付方式删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除店铺支付方式
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺支付方式删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopPaymodeEntity tcsbShopPaymode = systemService.getEntity(TcsbShopPaymodeEntity.class, 
				id
				);
				tcsbShopPaymodeService.delete(tcsbShopPaymode);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺支付方式删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加店铺支付方式
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopPaymodeEntity tcsbShopPaymode, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺支付方式添加成功";
		try{
			tcsbShopPaymodeService.save(tcsbShopPaymode);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺支付方式添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新店铺支付方式
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopPaymodeEntity tcsbShopPaymode, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺支付方式更新成功";
		TcsbShopPaymodeEntity t = tcsbShopPaymodeService.get(TcsbShopPaymodeEntity.class, tcsbShopPaymode.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopPaymode, t);
			tcsbShopPaymodeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺支付方式更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺支付方式新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopPaymodeEntity tcsbShopPaymode, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopPaymode.getId())) {
			tcsbShopPaymode = tcsbShopPaymodeService.getEntity(TcsbShopPaymodeEntity.class, tcsbShopPaymode.getId());
			req.setAttribute("tcsbShopPaymodePage", tcsbShopPaymode);
		}
		return new ModelAndView("com/tcsb/tcsbshoppaymode/tcsbShopPaymode-add");
	}

	/**
	 * 设置店铺支付方式新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goSetShopPaymode")
	public ModelAndView setShopPaymode(HttpServletRequest request) {
		if(StringUtils.isNotEmpty(request.getParameter("shopId"))){
			TcsbShopPaymodeEntity tcsbShopPaymodeEntity = tcsbShopPaymodeService.getEntity(TcsbShopPaymodeEntity.class, request.getParameter("shopId"));
			request.setAttribute("payMode", tcsbShopPaymodeEntity.getPayMode()==null?0:tcsbShopPaymodeEntity.getPayMode());
		}
		return new ModelAndView("com/tcsb/tcsbshoppaymode/tcsbShopPaymode-set");
	}

	/**
	 * 设置权限
	 *
	 * @param role
	 * @param request
	 * @param comboTree
	 * @return
	 */
//	@RequestMapping(params = "setAuthority")
//	@ResponseBody
//	public List<ComboTree> setAuthority(TcsbFoodEntity role,
//										HttpServletRequest request, ComboTree comboTree) {
//
//
//		functionList.clear();
//		loginActionlist.clear();
//		return comboTrees;
//	}

	/**
	 * 店铺支付方式编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopPaymodeEntity tcsbShopPaymode, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopPaymode.getId())) {
			tcsbShopPaymode = tcsbShopPaymodeService.getEntity(TcsbShopPaymodeEntity.class, tcsbShopPaymode.getId());
			req.setAttribute("tcsbShopPaymodePage", tcsbShopPaymode);
		}
		return new ModelAndView("com/tcsb/tcsbshoppaymode/tcsbShopPaymode-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopPaymodeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopPaymodeEntity tcsbShopPaymode,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopPaymodeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopPaymode, request.getParameterMap());
		List<TcsbShopPaymodeEntity> tcsbShopPaymodes = this.tcsbShopPaymodeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺支付方式");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopPaymodeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺支付方式列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopPaymodes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopPaymodeEntity tcsbShopPaymode,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"店铺支付方式");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopPaymodeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺支付方式列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopPaymodeEntity> listTcsbShopPaymodeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopPaymodeEntity.class,params);
				for (TcsbShopPaymodeEntity tcsbShopPaymode : listTcsbShopPaymodeEntitys) {
					tcsbShopPaymodeService.save(tcsbShopPaymode);
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
	public List<TcsbShopPaymodeEntity> list() {
		List<TcsbShopPaymodeEntity> listTcsbShopPaymodes=tcsbShopPaymodeService.getList(TcsbShopPaymodeEntity.class);
		return listTcsbShopPaymodes;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopPaymodeEntity task = tcsbShopPaymodeService.get(TcsbShopPaymodeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopPaymodeEntity tcsbShopPaymode, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopPaymodeEntity>> failures = validator.validate(tcsbShopPaymode);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopPaymodeService.save(tcsbShopPaymode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopPaymode.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopPaymodeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopPaymodeEntity tcsbShopPaymode) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopPaymodeEntity>> failures = validator.validate(tcsbShopPaymode);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopPaymodeService.saveOrUpdate(tcsbShopPaymode);
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
		tcsbShopPaymodeService.deleteEntityById(TcsbShopPaymodeEntity.class, id);
	}
}
