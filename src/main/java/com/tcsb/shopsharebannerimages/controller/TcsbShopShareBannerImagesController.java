package com.tcsb.shopsharebannerimages.controller;
import com.tcsb.shopsharebannerimages.entity.TcsbShopShareBannerImagesEntity;
import com.tcsb.shopsharebannerimages.service.TcsbShopShareBannerImagesServiceI;
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
 * @Description: 店铺分享横幅图片管理
 * @author onlineGenerator
 * @date 2018-03-01 11:01:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopShareBannerImagesController")
public class TcsbShopShareBannerImagesController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopShareBannerImagesController.class);

	@Autowired
	private TcsbShopShareBannerImagesServiceI tcsbShopShareBannerImagesService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 店铺分享横幅图片管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopsharebannerimages/tcsbShopShareBannerImagesList");
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
	public void datagrid(TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = getCurrentUser();
		CriteriaQuery cq = new CriteriaQuery(TcsbShopShareBannerImagesEntity.class, dataGrid);
		
		
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopShareBannerImages, request.getParameterMap());
		try{
		//自定义追加查询条件
			cq.eq("shopId", user.getShopId());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopShareBannerImagesService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除店铺分享横幅图片管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopShareBannerImages = systemService.getEntity(TcsbShopShareBannerImagesEntity.class, tcsbShopShareBannerImages.getId());
		message = "店铺分享横幅图片管理删除成功";
		try{
			tcsbShopShareBannerImagesService.delete(tcsbShopShareBannerImages);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺分享横幅图片管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除店铺分享横幅图片管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺分享横幅图片管理删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages = systemService.getEntity(TcsbShopShareBannerImagesEntity.class, 
				id
				);
				tcsbShopShareBannerImagesService.delete(tcsbShopShareBannerImages);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺分享横幅图片管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加店铺分享横幅图片管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺分享横幅图片管理添加成功";
		try{
			TSUser user = getCurrentUser();
			tcsbShopShareBannerImages.setShopId(user.getShopId());
			tcsbShopShareBannerImagesService.save(tcsbShopShareBannerImages);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺分享横幅图片管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新店铺分享横幅图片管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺分享横幅图片管理更新成功";
		TcsbShopShareBannerImagesEntity t = tcsbShopShareBannerImagesService.get(TcsbShopShareBannerImagesEntity.class, tcsbShopShareBannerImages.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopShareBannerImages, t);
			tcsbShopShareBannerImagesService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺分享横幅图片管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺分享横幅图片管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopShareBannerImages.getId())) {
			tcsbShopShareBannerImages = tcsbShopShareBannerImagesService.getEntity(TcsbShopShareBannerImagesEntity.class, tcsbShopShareBannerImages.getId());
			req.setAttribute("tcsbShopShareBannerImagesPage", tcsbShopShareBannerImages);
		}
		return new ModelAndView("com/tcsb/shopsharebannerimages/tcsbShopShareBannerImages-add");
	}
	/**
	 * 店铺分享横幅图片管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopShareBannerImages.getId())) {
			tcsbShopShareBannerImages = tcsbShopShareBannerImagesService.getEntity(TcsbShopShareBannerImagesEntity.class, tcsbShopShareBannerImages.getId());
			req.setAttribute("tcsbShopShareBannerImagesPage", tcsbShopShareBannerImages);
		}
		return new ModelAndView("com/tcsb/shopsharebannerimages/tcsbShopShareBannerImages-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopShareBannerImagesController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopShareBannerImagesEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopShareBannerImages, request.getParameterMap());
		List<TcsbShopShareBannerImagesEntity> tcsbShopShareBannerImagess = this.tcsbShopShareBannerImagesService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺分享横幅图片管理");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopShareBannerImagesEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺分享横幅图片管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopShareBannerImagess);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"店铺分享横幅图片管理");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopShareBannerImagesEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺分享横幅图片管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopShareBannerImagesEntity> listTcsbShopShareBannerImagesEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopShareBannerImagesEntity.class,params);
				for (TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages : listTcsbShopShareBannerImagesEntitys) {
					tcsbShopShareBannerImagesService.save(tcsbShopShareBannerImages);
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
	public List<TcsbShopShareBannerImagesEntity> list() {
		List<TcsbShopShareBannerImagesEntity> listTcsbShopShareBannerImagess=tcsbShopShareBannerImagesService.getList(TcsbShopShareBannerImagesEntity.class);
		return listTcsbShopShareBannerImagess;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopShareBannerImagesEntity task = tcsbShopShareBannerImagesService.get(TcsbShopShareBannerImagesEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopShareBannerImagesEntity>> failures = validator.validate(tcsbShopShareBannerImages);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopShareBannerImagesService.save(tcsbShopShareBannerImages);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopShareBannerImages.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopShareBannerImagesController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopShareBannerImagesEntity tcsbShopShareBannerImages) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopShareBannerImagesEntity>> failures = validator.validate(tcsbShopShareBannerImages);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopShareBannerImagesService.saveOrUpdate(tcsbShopShareBannerImages);
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
		tcsbShopShareBannerImagesService.deleteEntityById(TcsbShopShareBannerImagesEntity.class, id);
	}
}
