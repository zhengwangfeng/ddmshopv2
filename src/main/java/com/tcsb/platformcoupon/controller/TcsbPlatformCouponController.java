package com.tcsb.platformcoupon.controller;
import com.tcsb.platformcoupon.entity.TcsbPlatformCouponEntity;
import com.tcsb.platformcoupon.service.TcsbPlatformCouponServiceI;
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
 * @Description: 用户平台优惠券
 * @author onlineGenerator
 * @date 2018-01-27 10:29:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbPlatformCouponController")
public class TcsbPlatformCouponController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbPlatformCouponController.class);

	@Autowired
	private TcsbPlatformCouponServiceI tcsbPlatformCouponService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 用户平台优惠券列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/platformcoupon/tcsbPlatformCouponList");
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
	public void datagrid(TcsbPlatformCouponEntity tcsbPlatformCoupon,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbPlatformCouponEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPlatformCoupon, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbPlatformCouponService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除用户平台优惠券
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbPlatformCouponEntity tcsbPlatformCoupon, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbPlatformCoupon = systemService.getEntity(TcsbPlatformCouponEntity.class, tcsbPlatformCoupon.getId());
		message = "用户平台优惠券删除成功";
		try{
			tcsbPlatformCouponService.delete(tcsbPlatformCoupon);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户平台优惠券删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除用户平台优惠券
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户平台优惠券删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbPlatformCouponEntity tcsbPlatformCoupon = systemService.getEntity(TcsbPlatformCouponEntity.class, 
				id
				);
				tcsbPlatformCouponService.delete(tcsbPlatformCoupon);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "用户平台优惠券删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加用户平台优惠券
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbPlatformCouponEntity tcsbPlatformCoupon, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户平台优惠券添加成功";
		try{
			tcsbPlatformCouponService.save(tcsbPlatformCoupon);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户平台优惠券添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新用户平台优惠券
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbPlatformCouponEntity tcsbPlatformCoupon, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户平台优惠券更新成功";
		TcsbPlatformCouponEntity t = tcsbPlatformCouponService.get(TcsbPlatformCouponEntity.class, tcsbPlatformCoupon.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbPlatformCoupon, t);
			tcsbPlatformCouponService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户平台优惠券更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 用户平台优惠券新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbPlatformCouponEntity tcsbPlatformCoupon, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbPlatformCoupon.getId())) {
			tcsbPlatformCoupon = tcsbPlatformCouponService.getEntity(TcsbPlatformCouponEntity.class, tcsbPlatformCoupon.getId());
			req.setAttribute("tcsbPlatformCouponPage", tcsbPlatformCoupon);
		}
		return new ModelAndView("com/tcsb/platformcoupon/tcsbPlatformCoupon-add");
	}
	/**
	 * 用户平台优惠券编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbPlatformCouponEntity tcsbPlatformCoupon, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbPlatformCoupon.getId())) {
			tcsbPlatformCoupon = tcsbPlatformCouponService.getEntity(TcsbPlatformCouponEntity.class, tcsbPlatformCoupon.getId());
			req.setAttribute("tcsbPlatformCouponPage", tcsbPlatformCoupon);
		}
		return new ModelAndView("com/tcsb/platformcoupon/tcsbPlatformCoupon-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbPlatformCouponController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbPlatformCouponEntity tcsbPlatformCoupon,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbPlatformCouponEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPlatformCoupon, request.getParameterMap());
		List<TcsbPlatformCouponEntity> tcsbPlatformCoupons = this.tcsbPlatformCouponService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"用户平台优惠券");
		modelMap.put(NormalExcelConstants.CLASS,TcsbPlatformCouponEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户平台优惠券列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbPlatformCoupons);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbPlatformCouponEntity tcsbPlatformCoupon,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"用户平台优惠券");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbPlatformCouponEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户平台优惠券列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbPlatformCouponEntity> listTcsbPlatformCouponEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbPlatformCouponEntity.class,params);
				for (TcsbPlatformCouponEntity tcsbPlatformCoupon : listTcsbPlatformCouponEntitys) {
					tcsbPlatformCouponService.save(tcsbPlatformCoupon);
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
	public List<TcsbPlatformCouponEntity> list() {
		List<TcsbPlatformCouponEntity> listTcsbPlatformCoupons=tcsbPlatformCouponService.getList(TcsbPlatformCouponEntity.class);
		return listTcsbPlatformCoupons;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbPlatformCouponEntity task = tcsbPlatformCouponService.get(TcsbPlatformCouponEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbPlatformCouponEntity tcsbPlatformCoupon, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbPlatformCouponEntity>> failures = validator.validate(tcsbPlatformCoupon);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbPlatformCouponService.save(tcsbPlatformCoupon);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbPlatformCoupon.getId();
		URI uri = uriBuilder.path("/rest/tcsbPlatformCouponController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbPlatformCouponEntity tcsbPlatformCoupon) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbPlatformCouponEntity>> failures = validator.validate(tcsbPlatformCoupon);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbPlatformCouponService.saveOrUpdate(tcsbPlatformCoupon);
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
		tcsbPlatformCouponService.deleteEntityById(TcsbPlatformCouponEntity.class, id);
	}
}
