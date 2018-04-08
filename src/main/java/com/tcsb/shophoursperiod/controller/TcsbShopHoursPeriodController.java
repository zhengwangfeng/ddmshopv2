package com.tcsb.shophoursperiod.controller;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shophoursperiod.entity.TcsbShopHoursPeriodEntity;
import com.tcsb.shophoursperiod.service.TcsbShopHoursPeriodServiceI;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcsb.shophoursperiod.vo.TcsbShopHoursPeriodVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
 * @Description: 营业时间段
 * @author onlineGenerator
 * @date 2017-11-08 09:37:51
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopHoursPeriodController")
public class TcsbShopHoursPeriodController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopHoursPeriodController.class);

	@Autowired
	private TcsbShopHoursPeriodServiceI tcsbShopHoursPeriodService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 营业时间段列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shophoursperiod/tcsbShopHoursPeriodList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbShopHoursPeriodEntity tcsbShopHoursPeriod,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws InvocationTargetException, IllegalAccessException {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopHoursPeriodEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopHoursPeriod, request.getParameterMap());
		TSUser user = getCurrentUser();
		try{
		//自定义追加查询条件
			if (!checkAdmin()) {
				// 查询商家店铺
				cq.eq("shopId", user.getShopId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopHoursPeriodService.getDataGridReturn(cq, true);

		List<TcsbShopHoursPeriodEntity> shopHoursPeriodEntityList = dataGrid.getResults();
		if (StringUtil.isNotEmpty(shopHoursPeriodEntityList) && shopHoursPeriodEntityList.size() > 0) {
			List<TcsbShopHoursPeriodVO> shopHoursPeriodVOList = new ArrayList<TcsbShopHoursPeriodVO>();
			for (TcsbShopHoursPeriodEntity one : shopHoursPeriodEntityList) {
				TcsbShopHoursPeriodVO shopHoursPeriodVO = new TcsbShopHoursPeriodVO();
				BeanUtils.copyProperties(shopHoursPeriodVO, one);
				if(one.getShopId()!=null){
					TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, one.getShopId());
					shopHoursPeriodVO.setShopName(shopEntity.getName());
				}
				shopHoursPeriodVOList.add(shopHoursPeriodVO);
			}

			dataGrid.setResults(shopHoursPeriodVOList);
		}


		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除营业时间段
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopHoursPeriodEntity tcsbShopHoursPeriod, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopHoursPeriod = systemService.getEntity(TcsbShopHoursPeriodEntity.class, tcsbShopHoursPeriod.getId());
		message = "营业时间段删除成功";
		try{
			tcsbShopHoursPeriodService.delete(tcsbShopHoursPeriod);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "营业时间段删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除营业时间段
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "营业时间段删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopHoursPeriodEntity tcsbShopHoursPeriod = systemService.getEntity(TcsbShopHoursPeriodEntity.class, 
				id
				);
				tcsbShopHoursPeriodService.delete(tcsbShopHoursPeriod);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "营业时间段删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加营业时间段
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopHoursPeriodEntity tcsbShopHoursPeriod, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "营业时间段添加成功";
		TSUser user = getCurrentUser();
		try{
			if(StringUtils.isEmpty(String.valueOf(tcsbShopHoursPeriod.getName()))){
				throw new RuntimeException();
			}
			if (!checkAdmin()) {
				// 查询商家店铺
				TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, user.getShopId());
				tcsbShopHoursPeriod.setShopId(shopEntity.getId());
			}
			tcsbShopHoursPeriodService.save(tcsbShopHoursPeriod);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "营业时间段添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新营业时间段
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopHoursPeriodEntity tcsbShopHoursPeriod, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "营业时间段更新成功";
		TcsbShopHoursPeriodEntity t = tcsbShopHoursPeriodService.get(TcsbShopHoursPeriodEntity.class, tcsbShopHoursPeriod.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopHoursPeriod, t);
			tcsbShopHoursPeriodService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "营业时间段更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 营业时间段新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopHoursPeriodEntity tcsbShopHoursPeriod, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopHoursPeriod.getId())) {
			tcsbShopHoursPeriod = tcsbShopHoursPeriodService.getEntity(TcsbShopHoursPeriodEntity.class, tcsbShopHoursPeriod.getId());
			req.setAttribute("tcsbShopHoursPeriodPage", tcsbShopHoursPeriod);
		}
		return new ModelAndView("com/tcsb/shophoursperiod/tcsbShopHoursPeriod-add");
	}
	/**
	 * 营业时间段编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopHoursPeriodEntity tcsbShopHoursPeriod, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopHoursPeriod.getId())) {
			tcsbShopHoursPeriod = tcsbShopHoursPeriodService.getEntity(TcsbShopHoursPeriodEntity.class, tcsbShopHoursPeriod.getId());
			req.setAttribute("tcsbShopHoursPeriodPage", tcsbShopHoursPeriod);
		}
		return new ModelAndView("com/tcsb/shophoursperiod/tcsbShopHoursPeriod-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopHoursPeriodController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopHoursPeriodEntity tcsbShopHoursPeriod,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopHoursPeriodEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopHoursPeriod, request.getParameterMap());
		List<TcsbShopHoursPeriodEntity> tcsbShopHoursPeriods = this.tcsbShopHoursPeriodService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"营业时间段");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopHoursPeriodEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("营业时间段列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopHoursPeriods);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopHoursPeriodEntity tcsbShopHoursPeriod,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"营业时间段");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopHoursPeriodEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("营业时间段列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopHoursPeriodEntity> listTcsbShopHoursPeriodEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopHoursPeriodEntity.class,params);
				for (TcsbShopHoursPeriodEntity tcsbShopHoursPeriod : listTcsbShopHoursPeriodEntitys) {
					tcsbShopHoursPeriodService.save(tcsbShopHoursPeriod);
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
	public List<TcsbShopHoursPeriodEntity> list() {
		List<TcsbShopHoursPeriodEntity> listTcsbShopHoursPeriods=tcsbShopHoursPeriodService.getList(TcsbShopHoursPeriodEntity.class);
		return listTcsbShopHoursPeriods;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopHoursPeriodEntity task = tcsbShopHoursPeriodService.get(TcsbShopHoursPeriodEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopHoursPeriodEntity tcsbShopHoursPeriod, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopHoursPeriodEntity>> failures = validator.validate(tcsbShopHoursPeriod);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopHoursPeriodService.save(tcsbShopHoursPeriod);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopHoursPeriod.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopHoursPeriodController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopHoursPeriodEntity tcsbShopHoursPeriod) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopHoursPeriodEntity>> failures = validator.validate(tcsbShopHoursPeriod);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopHoursPeriodService.saveOrUpdate(tcsbShopHoursPeriod);
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
		tcsbShopHoursPeriodService.deleteEntityById(TcsbShopHoursPeriodEntity.class, id);
	}
}
