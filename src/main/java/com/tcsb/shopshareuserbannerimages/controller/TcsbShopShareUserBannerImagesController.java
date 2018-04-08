package com.tcsb.shopshareuserbannerimages.controller;
import com.tcsb.shopshareuserbannerimages.entity.TcsbShopShareUserBannerImagesEntity;
import com.tcsb.shopshareuserbannerimages.service.TcsbShopShareUserBannerImagesServiceI;
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
 * @Description: 用户分享横幅图片管理
 * @author onlineGenerator
 * @date 2018-03-01 11:01:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopShareUserBannerImagesController")
public class TcsbShopShareUserBannerImagesController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopShareUserBannerImagesController.class);

	@Autowired
	private TcsbShopShareUserBannerImagesServiceI tcsbShopShareUserBannerImagesService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 用户分享横幅图片管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopshareuserbannerimages/tcsbShopShareUserBannerImagesList");
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
	public void datagrid(TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopShareUserBannerImagesEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopShareUserBannerImages, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopShareUserBannerImagesService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除用户分享横幅图片管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopShareUserBannerImages = systemService.getEntity(TcsbShopShareUserBannerImagesEntity.class, tcsbShopShareUserBannerImages.getId());
		message = "用户分享横幅图片管理删除成功";
		try{
			tcsbShopShareUserBannerImagesService.delete(tcsbShopShareUserBannerImages);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户分享横幅图片管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除用户分享横幅图片管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户分享横幅图片管理删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages = systemService.getEntity(TcsbShopShareUserBannerImagesEntity.class, 
				id
				);
				tcsbShopShareUserBannerImagesService.delete(tcsbShopShareUserBannerImages);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "用户分享横幅图片管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加用户分享横幅图片管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户分享横幅图片管理添加成功";
		try{
			tcsbShopShareUserBannerImagesService.save(tcsbShopShareUserBannerImages);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户分享横幅图片管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新用户分享横幅图片管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户分享横幅图片管理更新成功";
		TcsbShopShareUserBannerImagesEntity t = tcsbShopShareUserBannerImagesService.get(TcsbShopShareUserBannerImagesEntity.class, tcsbShopShareUserBannerImages.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopShareUserBannerImages, t);
			tcsbShopShareUserBannerImagesService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户分享横幅图片管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 用户分享横幅图片管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopShareUserBannerImages.getId())) {
			tcsbShopShareUserBannerImages = tcsbShopShareUserBannerImagesService.getEntity(TcsbShopShareUserBannerImagesEntity.class, tcsbShopShareUserBannerImages.getId());
			req.setAttribute("tcsbShopShareUserBannerImagesPage", tcsbShopShareUserBannerImages);
		}
		return new ModelAndView("com/tcsb/shopshareuserbannerimages/tcsbShopShareUserBannerImages-add");
	}
	/**
	 * 用户分享横幅图片管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopShareUserBannerImages.getId())) {
			tcsbShopShareUserBannerImages = tcsbShopShareUserBannerImagesService.getEntity(TcsbShopShareUserBannerImagesEntity.class, tcsbShopShareUserBannerImages.getId());
			req.setAttribute("tcsbShopShareUserBannerImagesPage", tcsbShopShareUserBannerImages);
		}
		return new ModelAndView("com/tcsb/shopshareuserbannerimages/tcsbShopShareUserBannerImages-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopShareUserBannerImagesController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopShareUserBannerImagesEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopShareUserBannerImages, request.getParameterMap());
		List<TcsbShopShareUserBannerImagesEntity> tcsbShopShareUserBannerImagess = this.tcsbShopShareUserBannerImagesService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"用户分享横幅图片管理");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopShareUserBannerImagesEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户分享横幅图片管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopShareUserBannerImagess);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"用户分享横幅图片管理");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopShareUserBannerImagesEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户分享横幅图片管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopShareUserBannerImagesEntity> listTcsbShopShareUserBannerImagesEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopShareUserBannerImagesEntity.class,params);
				for (TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages : listTcsbShopShareUserBannerImagesEntitys) {
					tcsbShopShareUserBannerImagesService.save(tcsbShopShareUserBannerImages);
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
	public List<TcsbShopShareUserBannerImagesEntity> list() {
		List<TcsbShopShareUserBannerImagesEntity> listTcsbShopShareUserBannerImagess=tcsbShopShareUserBannerImagesService.getList(TcsbShopShareUserBannerImagesEntity.class);
		return listTcsbShopShareUserBannerImagess;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopShareUserBannerImagesEntity task = tcsbShopShareUserBannerImagesService.get(TcsbShopShareUserBannerImagesEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopShareUserBannerImagesEntity>> failures = validator.validate(tcsbShopShareUserBannerImages);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopShareUserBannerImagesService.save(tcsbShopShareUserBannerImages);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopShareUserBannerImages.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopShareUserBannerImagesController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopShareUserBannerImagesEntity tcsbShopShareUserBannerImages) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopShareUserBannerImagesEntity>> failures = validator.validate(tcsbShopShareUserBannerImages);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopShareUserBannerImagesService.saveOrUpdate(tcsbShopShareUserBannerImages);
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
		tcsbShopShareUserBannerImagesService.deleteEntityById(TcsbShopShareUserBannerImagesEntity.class, id);
	}
}
