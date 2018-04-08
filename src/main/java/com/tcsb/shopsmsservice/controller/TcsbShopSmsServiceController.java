package com.tcsb.shopsmsservice.controller;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopsmsservice.entity.TcsbShopSmsServiceEntity;
import com.tcsb.shopsmsservice.service.TcsbShopSmsServiceServiceI;
import com.tcsb.shopsmsservice.vo.ShopSmsVo;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.Restrictions;
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
 * @Description: 商户短信服务
 * @author onlineGenerator
 * @date 2017-05-15 15:23:16
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopSmsServiceController")
public class TcsbShopSmsServiceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopSmsServiceController.class);

	@Autowired
	private TcsbShopSmsServiceServiceI tcsbShopSmsServiceService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 商户短信服务列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopsmsservice/tcsbShopSmsServiceList");
	}
	
	
	@RequestMapping(params = "SMSrechargeList")
	public ModelAndView SMSrecharge(String shopId,HttpServletRequest request,ModelMap modelMap) {
		modelMap.addAttribute("shopId", shopId);
		TcsbShopEntity tcsbShopEntity = tcsbShopSmsServiceService.singleResult("from TcsbShopEntity where id='"+shopId+"'");
		if(StringUtil.isNotEmpty(tcsbShopEntity)){
			modelMap.addAttribute("shopName", tcsbShopEntity.getName());
		}
		return new ModelAndView("com/tcsb/shopsmsservice/SMSrecharge");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbShopSmsServiceEntity tcsbShopSmsService,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopEntity.class);
		cq.setCurPage(dataGrid.getPage());
		cq.setPageSize(dataGrid.getRows());
		if(StringUtil.isNotEmpty(tcsbShopSmsService.getShopName())){
			cq.add(Restrictions.like("name", "%"+tcsbShopSmsService.getShopName()+"%"));
		}
		//cq.add(Restrictions.eq("userKey", "点单么商家"));
		List<TcsbShopEntity> tcsbShop = tcsbShopSmsServiceService.getListByCriteriaQuery(cq, true);

		List<ShopSmsVo> shopSmsVoList = new ArrayList<>();
		ShopSmsVo shopSmsvo;
		for (TcsbShopEntity tcsbShops : tcsbShop) {
			shopSmsvo = new ShopSmsVo();
			BeanUtils.copyProperties(shopSmsvo, tcsbShops);
			//TcsbShopEntity TcsbShopEntity = tcsbShopSmsServiceService.singleResult("from TcsbShopEntity where userId='"+tsBaseUser.getId()+"'");
			if(StringUtil.isNotEmpty(tcsbShops)){
				shopSmsvo.setShopName(tcsbShops.getName());
				shopSmsvo.setShopId(tcsbShops.getId());
				shopSmsvo.setShopAddress(tcsbShops.getAddress());
				TcsbShopSmsServiceEntity TcsbShopSmsService = tcsbShopSmsServiceService.singleResult("from TcsbShopSmsServiceEntity where shopId='"+tcsbShops.getId()+"'");
				if(StringUtil.isNotEmpty(TcsbShopSmsService)){
					shopSmsvo.setSmsCount(TcsbShopSmsService.getCount()+"");
				}else{
					shopSmsvo.setSmsCount("0");
				}
			}
			
			shopSmsVoList.add(shopSmsvo);
		}
		dataGrid.setTotal(tcsbShopSmsServiceService.getList(TcsbShopEntity.class).size());
		dataGrid.setResults(shopSmsVoList);
		TagUtil.datagrid(response, dataGrid);
		
		/*//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopSmsService, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopSmsServiceService.getDataGridReturn(cq, true);*/
		
	}

	/**
	 * 删除商户短信服务
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopSmsServiceEntity tcsbShopSmsService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopSmsService = systemService.getEntity(TcsbShopSmsServiceEntity.class, tcsbShopSmsService.getId());
		message = "商户短信服务删除成功";
		try{
			tcsbShopSmsServiceService.delete(tcsbShopSmsService);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商户短信服务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除商户短信服务
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商户短信服务删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopSmsServiceEntity tcsbShopSmsService = systemService.getEntity(TcsbShopSmsServiceEntity.class, 
				id
				);
				tcsbShopSmsServiceService.delete(tcsbShopSmsService);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商户短信服务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加商户短信服务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopSmsServiceEntity tcsbShopSmsService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商户短信服务添加成功";
		try{
			
			TcsbShopSmsServiceEntity tcsbShopSmsServiceEntity = tcsbShopSmsServiceService.singleResult("from TcsbShopSmsServiceEntity where shopId='"+tcsbShopSmsService.getShopId()+"'");
			if(StringUtil.isNotEmpty(tcsbShopSmsServiceEntity)){
				tcsbShopSmsServiceEntity.setCount(tcsbShopSmsServiceEntity.getCount()+tcsbShopSmsService.getCount());
				tcsbShopSmsServiceService.saveOrUpdate(tcsbShopSmsServiceEntity);
			}else{
				tcsbShopSmsServiceService.save(tcsbShopSmsService);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商户短信服务添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新商户短信服务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopSmsServiceEntity tcsbShopSmsService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商户短信服务更新成功";
		TcsbShopSmsServiceEntity t = tcsbShopSmsServiceService.get(TcsbShopSmsServiceEntity.class, tcsbShopSmsService.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopSmsService, t);
			tcsbShopSmsServiceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商户短信服务更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 商户短信服务新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopSmsServiceEntity tcsbShopSmsService, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopSmsService.getId())) {
			tcsbShopSmsService = tcsbShopSmsServiceService.getEntity(TcsbShopSmsServiceEntity.class, tcsbShopSmsService.getId());
			req.setAttribute("tcsbShopSmsServicePage", tcsbShopSmsService);
		}
		return new ModelAndView("com/tcsb/shopsmsservice/tcsbShopSmsService-add");
	}
	/**
	 * 商户短信服务编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopSmsServiceEntity tcsbShopSmsService, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopSmsService.getId())) {
			tcsbShopSmsService = tcsbShopSmsServiceService.getEntity(TcsbShopSmsServiceEntity.class, tcsbShopSmsService.getId());
			req.setAttribute("tcsbShopSmsServicePage", tcsbShopSmsService);
		}
		return new ModelAndView("com/tcsb/shopsmsservice/tcsbShopSmsService-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopSmsServiceController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopSmsServiceEntity tcsbShopSmsService,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopSmsServiceEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopSmsService, request.getParameterMap());
		List<TcsbShopSmsServiceEntity> tcsbShopSmsServices = this.tcsbShopSmsServiceService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商户短信服务");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopSmsServiceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商户短信服务列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopSmsServices);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopSmsServiceEntity tcsbShopSmsService,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"商户短信服务");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopSmsServiceEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商户短信服务列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopSmsServiceEntity> listTcsbShopSmsServiceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopSmsServiceEntity.class,params);
				for (TcsbShopSmsServiceEntity tcsbShopSmsService : listTcsbShopSmsServiceEntitys) {
					tcsbShopSmsServiceService.save(tcsbShopSmsService);
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
	public List<TcsbShopSmsServiceEntity> list() {
		List<TcsbShopSmsServiceEntity> listTcsbShopSmsServices=tcsbShopSmsServiceService.getList(TcsbShopSmsServiceEntity.class);
		return listTcsbShopSmsServices;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopSmsServiceEntity task = tcsbShopSmsServiceService.get(TcsbShopSmsServiceEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopSmsServiceEntity tcsbShopSmsService, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopSmsServiceEntity>> failures = validator.validate(tcsbShopSmsService);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopSmsServiceService.save(tcsbShopSmsService);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopSmsService.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopSmsServiceController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopSmsServiceEntity tcsbShopSmsService) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopSmsServiceEntity>> failures = validator.validate(tcsbShopSmsService);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopSmsServiceService.saveOrUpdate(tcsbShopSmsService);
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
		tcsbShopSmsServiceService.deleteEntityById(TcsbShopSmsServiceEntity.class, id);
	}
}
