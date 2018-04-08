package com.tcsb.desksnapshot.controller;
import com.tcsb.desksnapshot.entity.TcsbDeskSnapshotEntity;
import com.tcsb.desksnapshot.service.TcsbDeskSnapshotServiceI;
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
 * @Description: 桌位快照
 * @author onlineGenerator
 * @date 2017-11-14 16:11:49
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbDeskSnapshotController")
public class TcsbDeskSnapshotController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbDeskSnapshotController.class);

	@Autowired
	private TcsbDeskSnapshotServiceI tcsbDeskSnapshotService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 桌位快照列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/desksnapshot/tcsbDeskSnapshotList");
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
	public void datagrid(TcsbDeskSnapshotEntity tcsbDeskSnapshot,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDeskSnapshotEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDeskSnapshot, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbDeskSnapshotService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除桌位快照
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbDeskSnapshotEntity tcsbDeskSnapshot, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbDeskSnapshot = systemService.getEntity(TcsbDeskSnapshotEntity.class, tcsbDeskSnapshot.getId());
		message = "桌位快照删除成功";
		try{
			tcsbDeskSnapshotService.delete(tcsbDeskSnapshot);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位快照删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除桌位快照
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位快照删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbDeskSnapshotEntity tcsbDeskSnapshot = systemService.getEntity(TcsbDeskSnapshotEntity.class, 
				id
				);
				tcsbDeskSnapshotService.delete(tcsbDeskSnapshot);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位快照删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加桌位快照
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbDeskSnapshotEntity tcsbDeskSnapshot, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位快照添加成功";
		try{
			tcsbDeskSnapshotService.save(tcsbDeskSnapshot);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位快照添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新桌位快照
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbDeskSnapshotEntity tcsbDeskSnapshot, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位快照更新成功";
		TcsbDeskSnapshotEntity t = tcsbDeskSnapshotService.get(TcsbDeskSnapshotEntity.class, tcsbDeskSnapshot.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbDeskSnapshot, t);
			tcsbDeskSnapshotService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "桌位快照更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 桌位快照新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbDeskSnapshotEntity tcsbDeskSnapshot, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDeskSnapshot.getId())) {
			tcsbDeskSnapshot = tcsbDeskSnapshotService.getEntity(TcsbDeskSnapshotEntity.class, tcsbDeskSnapshot.getId());
			req.setAttribute("tcsbDeskSnapshotPage", tcsbDeskSnapshot);
		}
		return new ModelAndView("com/tcsb/desksnapshot/tcsbDeskSnapshot-add");
	}
	/**
	 * 桌位快照编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbDeskSnapshotEntity tcsbDeskSnapshot, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDeskSnapshot.getId())) {
			tcsbDeskSnapshot = tcsbDeskSnapshotService.getEntity(TcsbDeskSnapshotEntity.class, tcsbDeskSnapshot.getId());
			req.setAttribute("tcsbDeskSnapshotPage", tcsbDeskSnapshot);
		}
		return new ModelAndView("com/tcsb/desksnapshot/tcsbDeskSnapshot-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbDeskSnapshotController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbDeskSnapshotEntity tcsbDeskSnapshot,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDeskSnapshotEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDeskSnapshot, request.getParameterMap());
		List<TcsbDeskSnapshotEntity> tcsbDeskSnapshots = this.tcsbDeskSnapshotService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"桌位快照");
		modelMap.put(NormalExcelConstants.CLASS,TcsbDeskSnapshotEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("桌位快照列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbDeskSnapshots);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbDeskSnapshotEntity tcsbDeskSnapshot,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"桌位快照");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbDeskSnapshotEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("桌位快照列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbDeskSnapshotEntity> listTcsbDeskSnapshotEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbDeskSnapshotEntity.class,params);
				for (TcsbDeskSnapshotEntity tcsbDeskSnapshot : listTcsbDeskSnapshotEntitys) {
					tcsbDeskSnapshotService.save(tcsbDeskSnapshot);
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
	public List<TcsbDeskSnapshotEntity> list() {
		List<TcsbDeskSnapshotEntity> listTcsbDeskSnapshots=tcsbDeskSnapshotService.getList(TcsbDeskSnapshotEntity.class);
		return listTcsbDeskSnapshots;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbDeskSnapshotEntity task = tcsbDeskSnapshotService.get(TcsbDeskSnapshotEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbDeskSnapshotEntity tcsbDeskSnapshot, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDeskSnapshotEntity>> failures = validator.validate(tcsbDeskSnapshot);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDeskSnapshotService.save(tcsbDeskSnapshot);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbDeskSnapshot.getId();
		URI uri = uriBuilder.path("/rest/tcsbDeskSnapshotController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbDeskSnapshotEntity tcsbDeskSnapshot) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDeskSnapshotEntity>> failures = validator.validate(tcsbDeskSnapshot);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDeskSnapshotService.saveOrUpdate(tcsbDeskSnapshot);
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
		tcsbDeskSnapshotService.deleteEntityById(TcsbDeskSnapshotEntity.class, id);
	}
}
