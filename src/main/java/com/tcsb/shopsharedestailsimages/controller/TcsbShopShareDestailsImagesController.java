package com.tcsb.shopsharedestailsimages.controller;
import com.tcsb.shopsharedestailsimages.entity.TcsbShopShareDestailsImagesEntity;
import com.tcsb.shopsharedestailsimages.service.TcsbShopShareDestailsImagesServiceI;
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
 * @Description: 店铺分享详情图片管理
 * @author onlineGenerator
 * @date 2018-03-01 11:00:41
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopShareDestailsImagesController")
public class TcsbShopShareDestailsImagesController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopShareDestailsImagesController.class);

	@Autowired
	private TcsbShopShareDestailsImagesServiceI tcsbShopShareDestailsImagesService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 店铺分享详情图片管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopsharedestailsimages/tcsbShopShareDestailsImagesList");
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
	public void datagrid(TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = getCurrentUser();
		CriteriaQuery cq = new CriteriaQuery(TcsbShopShareDestailsImagesEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopShareDestailsImages, request.getParameterMap());
		try{
		//自定义追加查询条件
			cq.eq("shopId", user.getShopId());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopShareDestailsImagesService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除店铺分享详情图片管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopShareDestailsImages = systemService.getEntity(TcsbShopShareDestailsImagesEntity.class, tcsbShopShareDestailsImages.getId());
		message = "店铺分享详情图片管理删除成功";
		try{
			tcsbShopShareDestailsImagesService.delete(tcsbShopShareDestailsImages);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺分享详情图片管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除店铺分享详情图片管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺分享详情图片管理删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages = systemService.getEntity(TcsbShopShareDestailsImagesEntity.class, 
				id
				);
				tcsbShopShareDestailsImagesService.delete(tcsbShopShareDestailsImages);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺分享详情图片管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加店铺分享详情图片管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺分享详情图片管理添加成功";
		try{
			TSUser user = getCurrentUser();
			tcsbShopShareDestailsImages.setShopId(user.getShopId());
			tcsbShopShareDestailsImagesService.save(tcsbShopShareDestailsImages);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺分享详情图片管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新店铺分享详情图片管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺分享详情图片管理更新成功";
		TcsbShopShareDestailsImagesEntity t = tcsbShopShareDestailsImagesService.get(TcsbShopShareDestailsImagesEntity.class, tcsbShopShareDestailsImages.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopShareDestailsImages, t);
			tcsbShopShareDestailsImagesService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺分享详情图片管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺分享详情图片管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopShareDestailsImages.getId())) {
			tcsbShopShareDestailsImages = tcsbShopShareDestailsImagesService.getEntity(TcsbShopShareDestailsImagesEntity.class, tcsbShopShareDestailsImages.getId());
			req.setAttribute("tcsbShopShareDestailsImagesPage", tcsbShopShareDestailsImages);
		}
		return new ModelAndView("com/tcsb/shopsharedestailsimages/tcsbShopShareDestailsImages-add");
	}
	/**
	 * 店铺分享详情图片管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopShareDestailsImages.getId())) {
			tcsbShopShareDestailsImages = tcsbShopShareDestailsImagesService.getEntity(TcsbShopShareDestailsImagesEntity.class, tcsbShopShareDestailsImages.getId());
			req.setAttribute("tcsbShopShareDestailsImagesPage", tcsbShopShareDestailsImages);
		}
		return new ModelAndView("com/tcsb/shopsharedestailsimages/tcsbShopShareDestailsImages-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopShareDestailsImagesController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopShareDestailsImagesEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopShareDestailsImages, request.getParameterMap());
		List<TcsbShopShareDestailsImagesEntity> tcsbShopShareDestailsImagess = this.tcsbShopShareDestailsImagesService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺分享详情图片管理");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopShareDestailsImagesEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺分享详情图片管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopShareDestailsImagess);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"店铺分享详情图片管理");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopShareDestailsImagesEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺分享详情图片管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopShareDestailsImagesEntity> listTcsbShopShareDestailsImagesEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopShareDestailsImagesEntity.class,params);
				for (TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages : listTcsbShopShareDestailsImagesEntitys) {
					tcsbShopShareDestailsImagesService.save(tcsbShopShareDestailsImages);
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
	public List<TcsbShopShareDestailsImagesEntity> list() {
		List<TcsbShopShareDestailsImagesEntity> listTcsbShopShareDestailsImagess=tcsbShopShareDestailsImagesService.getList(TcsbShopShareDestailsImagesEntity.class);
		return listTcsbShopShareDestailsImagess;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopShareDestailsImagesEntity task = tcsbShopShareDestailsImagesService.get(TcsbShopShareDestailsImagesEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopShareDestailsImagesEntity>> failures = validator.validate(tcsbShopShareDestailsImages);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopShareDestailsImagesService.save(tcsbShopShareDestailsImages);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopShareDestailsImages.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopShareDestailsImagesController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopShareDestailsImagesEntity tcsbShopShareDestailsImages) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopShareDestailsImagesEntity>> failures = validator.validate(tcsbShopShareDestailsImages);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopShareDestailsImagesService.saveOrUpdate(tcsbShopShareDestailsImages);
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
		tcsbShopShareDestailsImagesService.deleteEntityById(TcsbShopShareDestailsImagesEntity.class, id);
	}
}
