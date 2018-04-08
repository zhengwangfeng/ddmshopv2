package com.tcsb.desktype.controller;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcsb.desktype.entity.TcsbDeskTypeEntity;
import com.tcsb.desktype.service.TcsbDeskTypeServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;

/**   
 * @Title: Controller  
 * @Description: 桌位类型
 * @author onlineGenerator
 * @date 2017-03-23 10:11:51
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbDeskTypeController")
public class TcsbDeskTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbDeskTypeController.class);

	@Autowired
	private TcsbDeskTypeServiceI tcsbDeskTypeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;


	/**
	 * 桌位类型列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String shopsReplace = "";
		List<TcsbShopEntity> tcsbShopEntities = systemService.getList(TcsbShopEntity.class);
		for (TcsbShopEntity tcsbShopEntity : tcsbShopEntities) {
			if (shopsReplace.length() > 0) {
				shopsReplace += ",";
			}
			shopsReplace += tcsbShopEntity.getName() + "_" + tcsbShopEntity.getId();
		}
		//如果是admin用户展示店铺选择
		TSUser tsUser = getCurrentUser();
		if (tsUser.getUserName().equals("admin")) {
			request.setAttribute("isAdmin", true);
		}
		request.setAttribute("shopsReplace", shopsReplace);
		return new ModelAndView("com/tcsb/desktype/tcsbDeskTypeList");
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
	public void datagrid(TcsbDeskTypeEntity tcsbDeskType,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDeskTypeEntity.class, dataGrid);
		TSUser user = getCurrentUser();
		if (!user.getUserName().equals("admin")) {
			cq.eq("shopId", user.getShopId());
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDeskType, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbDeskTypeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除桌位类型
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbDeskTypeEntity tcsbDeskType, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbDeskType = systemService.getEntity(TcsbDeskTypeEntity.class, tcsbDeskType.getId());
		message = "桌位类型删除成功";
		try{
			tcsbDeskTypeService.delete(tcsbDeskType);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位类型删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除桌位类型
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位类型删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbDeskTypeEntity tcsbDeskType = systemService.getEntity(TcsbDeskTypeEntity.class, 
				id
				);
				tcsbDeskTypeService.delete(tcsbDeskType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位类型删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加桌位类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbDeskTypeEntity tcsbDeskType, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位类型添加成功";
		try{
			//获取当前用户
			TSUser user = getCurrentUser();
			//查询商家店铺
			TcsbShopEntity tcsbShopEntity = (TcsbShopEntity) tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
			tcsbDeskType.setShopId(tcsbShopEntity.getId());
			tcsbDeskTypeService.save(tcsbDeskType);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位类型添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新桌位类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbDeskTypeEntity tcsbDeskType, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位类型更新成功";
		TcsbDeskTypeEntity t = tcsbDeskTypeService.get(TcsbDeskTypeEntity.class, tcsbDeskType.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbDeskType, t);
			tcsbDeskTypeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "桌位类型更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 桌位类型新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbDeskTypeEntity tcsbDeskType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDeskType.getId())) {
			tcsbDeskType = tcsbDeskTypeService.getEntity(TcsbDeskTypeEntity.class, tcsbDeskType.getId());
			req.setAttribute("tcsbDeskTypePage", tcsbDeskType);
		}
		return new ModelAndView("com/tcsb/desktype/tcsbDeskType-add");
	}
	/**
	 * 桌位类型编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbDeskTypeEntity tcsbDeskType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDeskType.getId())) {
			tcsbDeskType = tcsbDeskTypeService.getEntity(TcsbDeskTypeEntity.class, tcsbDeskType.getId());
			req.setAttribute("tcsbDeskTypePage", tcsbDeskType);
		}
		return new ModelAndView("com/tcsb/desktype/tcsbDeskType-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbDeskTypeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbDeskTypeEntity tcsbDeskType,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDeskTypeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDeskType, request.getParameterMap());
		List<TcsbDeskTypeEntity> tcsbDeskTypes = this.tcsbDeskTypeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"桌位类型");
		modelMap.put(NormalExcelConstants.CLASS,TcsbDeskTypeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("桌位类型列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbDeskTypes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbDeskTypeEntity tcsbDeskType,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"桌位类型");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbDeskTypeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("桌位类型列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbDeskTypeEntity> listTcsbDeskTypeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbDeskTypeEntity.class,params);
				for (TcsbDeskTypeEntity tcsbDeskType : listTcsbDeskTypeEntitys) {
					tcsbDeskTypeService.save(tcsbDeskType);
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
	public List<TcsbDeskTypeEntity> list() {
		List<TcsbDeskTypeEntity> listTcsbDeskTypes=tcsbDeskTypeService.getList(TcsbDeskTypeEntity.class);
		return listTcsbDeskTypes;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbDeskTypeEntity task = tcsbDeskTypeService.get(TcsbDeskTypeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbDeskTypeEntity tcsbDeskType, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDeskTypeEntity>> failures = validator.validate(tcsbDeskType);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDeskTypeService.save(tcsbDeskType);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbDeskType.getId();
		URI uri = uriBuilder.path("/rest/tcsbDeskTypeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbDeskTypeEntity tcsbDeskType) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDeskTypeEntity>> failures = validator.validate(tcsbDeskType);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDeskTypeService.saveOrUpdate(tcsbDeskType);
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
		tcsbDeskTypeService.deleteEntityById(TcsbDeskTypeEntity.class, id);
	}
}
