package com.tcsb.shopcaritem.controller;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.shopcaritem.service.TcsbShopCarItemServiceI;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;

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
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
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
import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
 * @Description: 购物明细
 * @author onlineGenerator
 * @date 2017-04-26 21:43:49
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopCarItemController")
public class TcsbShopCarItemController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopCarItemController.class);

	@Autowired
	private TcsbShopCarItemServiceI tcsbShopCarItemService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 购物明细列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopcaritem/tcsbShopCarItemList");
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
	public void datagrid(TcsbShopCarItemEntity tcsbShopCarItem,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopCarItemEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopCarItem, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopCarItemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除购物明细
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopCarItemEntity tcsbShopCarItem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopCarItem = systemService.getEntity(TcsbShopCarItemEntity.class, tcsbShopCarItem.getId());
		message = "购物明细删除成功";
		try{
			tcsbShopCarItemService.delete(tcsbShopCarItem);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "购物明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除购物明细
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物明细删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopCarItemEntity tcsbShopCarItem = systemService.getEntity(TcsbShopCarItemEntity.class, 
				id
				);
				tcsbShopCarItemService.delete(tcsbShopCarItem);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "购物明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加购物明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopCarItemEntity tcsbShopCarItem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物明细添加成功";
		try{
			tcsbShopCarItemService.save(tcsbShopCarItem);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "购物明细添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新购物明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopCarItemEntity tcsbShopCarItem, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物明细更新成功";
		TcsbShopCarItemEntity t = tcsbShopCarItemService.get(TcsbShopCarItemEntity.class, tcsbShopCarItem.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopCarItem, t);
			tcsbShopCarItemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "购物明细更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 购物明细新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopCarItemEntity tcsbShopCarItem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopCarItem.getId())) {
			tcsbShopCarItem = tcsbShopCarItemService.getEntity(TcsbShopCarItemEntity.class, tcsbShopCarItem.getId());
			req.setAttribute("tcsbShopCarItemPage", tcsbShopCarItem);
		}
		return new ModelAndView("com/tcsb/shopcaritem/tcsbShopCarItem-add");
	}
	/**
	 * 购物明细编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopCarItemEntity tcsbShopCarItem, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopCarItem.getId())) {
			tcsbShopCarItem = tcsbShopCarItemService.getEntity(TcsbShopCarItemEntity.class, tcsbShopCarItem.getId());
			req.setAttribute("tcsbShopCarItemPage", tcsbShopCarItem);
		}
		return new ModelAndView("com/tcsb/shopcaritem/tcsbShopCarItem-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopCarItemController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopCarItemEntity tcsbShopCarItem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopCarItemEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopCarItem, request.getParameterMap());
		List<TcsbShopCarItemEntity> tcsbShopCarItems = this.tcsbShopCarItemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"购物明细");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopCarItemEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("购物明细列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopCarItems);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopCarItemEntity tcsbShopCarItem,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"购物明细");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopCarItemEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("购物明细列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopCarItemEntity> listTcsbShopCarItemEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopCarItemEntity.class,params);
				for (TcsbShopCarItemEntity tcsbShopCarItem : listTcsbShopCarItemEntitys) {
					tcsbShopCarItemService.save(tcsbShopCarItem);
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
	public List<TcsbShopCarItemEntity> list() {
		List<TcsbShopCarItemEntity> listTcsbShopCarItems=tcsbShopCarItemService.getList(TcsbShopCarItemEntity.class);
		return listTcsbShopCarItems;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopCarItemEntity task = tcsbShopCarItemService.get(TcsbShopCarItemEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopCarItemEntity tcsbShopCarItem, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopCarItemEntity>> failures = validator.validate(tcsbShopCarItem);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopCarItemService.save(tcsbShopCarItem);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopCarItem.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopCarItemController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopCarItemEntity tcsbShopCarItem) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopCarItemEntity>> failures = validator.validate(tcsbShopCarItem);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopCarItemService.saveOrUpdate(tcsbShopCarItem);
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
		tcsbShopCarItemService.deleteEntityById(TcsbShopCarItemEntity.class, id);
	}
	
	/**
	 * 点击下单获取购物车食品信息
	 * @param userId
	 * @param deskId
	 * @param foodId
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delcargoods",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject delcargoods(@RequestParam String id,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		TcsbShopCarItemEntity tcsbShopCarItemEntity = tcsbShopCarItemService.get(TcsbShopCarItemEntity.class, id);
		if(StringUtil.isNotEmpty(tcsbShopCarItemEntity)){
			if(tcsbShopCarItemEntity.getCount()>1){
				tcsbShopCarItemEntity.setCount(tcsbShopCarItemEntity.getCount()-1);
				try {
					tcsbShopCarItemService.saveOrUpdate(tcsbShopCarItemEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					tcsbShopCarItemService.delete(tcsbShopCarItemEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		ajaxJsonApi.setObj(null);
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}

	@RequestMapping(value = "/addcargoods",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject addcargoods(@RequestParam String id,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		TcsbShopCarItemEntity tcsbShopCarItemEntity = tcsbShopCarItemService.get(TcsbShopCarItemEntity.class, id);
		if(StringUtil.isNotEmpty(tcsbShopCarItemEntity)){
			//检测是否时价
			TcsbFoodEntity tcsbFoodEntity = tcsbShopCarItemService.get(TcsbFoodEntity.class, tcsbShopCarItemEntity.getFoodId());
			if(StringUtil.isNotEmpty(tcsbFoodEntity)){
				TcsbFoodUnitEntity tcsbFoodUnitEntity =tcsbShopCarItemService.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
				if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
					if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
						TcsbShopCarItemEntity newTcsbShopCarItem = new TcsbShopCarItemEntity();
						newTcsbShopCarItem.setCount(1);
						newTcsbShopCarItem.setFoodId(tcsbShopCarItemEntity.getFoodId());
						newTcsbShopCarItem.setFoodTasteFun(tcsbShopCarItemEntity.getFoodTasteFun());
						newTcsbShopCarItem.setName(tcsbShopCarItemEntity.getName());
						//newTcsbShopCarItem.setNum(tcsbShopCarItemEntity.getNum());
						newTcsbShopCarItem.setPrice(tcsbShopCarItemEntity.getPrice());
						newTcsbShopCarItem.setShopCar(tcsbShopCarItemEntity.getShopCar());
						try {
							tcsbShopCarItemService.save(newTcsbShopCarItem);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						tcsbShopCarItemEntity.setCount(tcsbShopCarItemEntity.getCount()+1);
						try {
							tcsbShopCarItemService.saveOrUpdate(tcsbShopCarItemEntity);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					tcsbShopCarItemEntity.setCount(tcsbShopCarItemEntity.getCount()+1);
					try {
						tcsbShopCarItemService.saveOrUpdate(tcsbShopCarItemEntity);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		ajaxJsonApi.setObj(null);
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
}
