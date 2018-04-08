package com.tcsb.shopcar.controller;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.shopcar.VO.TcsbShopCarVO;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcar.service.TcsbShopCarServiceI;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.jeecgframework.core.util.JSONHelper;
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
import org.springframework.web.bind.annotation.RequestParam;
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
 * @Description: 购物车
 * @author onlineGenerator
 * @date 2017-04-26 21:44:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopCarController")
public class TcsbShopCarController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopCarController.class);

	@Autowired
	private TcsbShopCarServiceI tcsbShopCarService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 购物车列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopcar/tcsbShopCarList");
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
	public void datagrid(TcsbShopCarEntity tcsbShopCar,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopCarEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopCar, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopCarService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除购物车
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopCarEntity tcsbShopCar, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopCar = systemService.getEntity(TcsbShopCarEntity.class, tcsbShopCar.getId());
		message = "购物车删除成功";
		try{
			tcsbShopCarService.delete(tcsbShopCar);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "购物车删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除购物车
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物车删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopCarEntity tcsbShopCar = systemService.getEntity(TcsbShopCarEntity.class, 
				id
				);
				tcsbShopCarService.delete(tcsbShopCar);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "购物车删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加购物车
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopCarEntity tcsbShopCar, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物车添加成功";
		try{
			tcsbShopCarService.save(tcsbShopCar);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "购物车添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新购物车
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopCarEntity tcsbShopCar, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "购物车更新成功";
		TcsbShopCarEntity t = tcsbShopCarService.get(TcsbShopCarEntity.class, tcsbShopCar.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopCar, t);
			tcsbShopCarService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "购物车更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 购物车新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopCarEntity tcsbShopCar, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopCar.getId())) {
			tcsbShopCar = tcsbShopCarService.getEntity(TcsbShopCarEntity.class, tcsbShopCar.getId());
			req.setAttribute("tcsbShopCarPage", tcsbShopCar);
		}
		return new ModelAndView("com/tcsb/shopcar/tcsbShopCar-add");
	}
	/**
	 * 购物车编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopCarEntity tcsbShopCar, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopCar.getId())) {
			tcsbShopCar = tcsbShopCarService.getEntity(TcsbShopCarEntity.class, tcsbShopCar.getId());
			req.setAttribute("tcsbShopCarPage", tcsbShopCar);
		}
		return new ModelAndView("com/tcsb/shopcar/tcsbShopCar-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopCarController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopCarEntity tcsbShopCar,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopCarEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopCar, request.getParameterMap());
		List<TcsbShopCarEntity> tcsbShopCars = this.tcsbShopCarService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"购物车");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopCarEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("购物车列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopCars);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopCarEntity tcsbShopCar,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"购物车");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopCarEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("购物车列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopCarEntity> listTcsbShopCarEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopCarEntity.class,params);
				for (TcsbShopCarEntity tcsbShopCar : listTcsbShopCarEntitys) {
					tcsbShopCarService.save(tcsbShopCar);
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
	public List<TcsbShopCarEntity> list() {
		List<TcsbShopCarEntity> listTcsbShopCars=tcsbShopCarService.getList(TcsbShopCarEntity.class);
		return listTcsbShopCars;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopCarEntity task = tcsbShopCarService.get(TcsbShopCarEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopCarEntity tcsbShopCar, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopCarEntity>> failures = validator.validate(tcsbShopCar);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopCarService.save(tcsbShopCar);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopCar.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopCarController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopCarEntity tcsbShopCar) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopCarEntity>> failures = validator.validate(tcsbShopCar);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopCarService.saveOrUpdate(tcsbShopCar);
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
		tcsbShopCarService.deleteEntityById(TcsbShopCarEntity.class, id);
	}
	/**
	 * 添加更新无规格菜品购物车
	 * @param userId
	 * @param deskId
	 * @param foodId
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addOrUpdateShopCar",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject addOrUpdateShopCar(@RequestParam String userId,@RequestParam String deskId,@RequestParam String foodId,@RequestParam String status,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		try {
			tcsbShopCarService.addOrUpdateShopCar(userId,deskId,foodId,status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.UPDATESUCEESS.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	
	/**
	 * 添加更新无规格菜品购物车
	 * @param userId
	 * @param deskId
	 * @param foodId
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addOrUpdateTasteShopCar",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject addOrUpdateTasteShopCar(@RequestParam String foodTasteFun,@RequestParam String userId,@RequestParam String deskId,@RequestParam String foodId,@RequestParam String status,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		try {
			tcsbShopCarService.addOrUpdateTasteShopCar(userId,deskId,foodId,status,foodTasteFun);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.UPDATESUCEESS.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	/**
	 * 点击下单获取购物车信息
	 * @param userId
	 * @param deskId
	 * @param foodId
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getShopCar",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getShopCar(@RequestParam String userId,@RequestParam String deskId,@RequestParam String shopId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		Map<String , Object> map =new HashMap<String, Object>();
		try {
			map = tcsbShopCarService.getShopCar(userId,deskId,shopId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(map);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
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
	@RequestMapping(value = "/getShopCarFood",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getShopCarFood(@RequestParam String deskId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", deskId);
		if(StringUtil.isNotEmpty(tcsbShopCarEntity)){
			String hql = "from TcsbShopCarItemEntity where shopCar='"+tcsbShopCarEntity.getId()+"'";
			List<TcsbShopCarItemEntity> tcsbShopCarlist = tcsbShopCarService.findByQueryString(hql);
			List<TcsbShopCarItemEntity> tcsbShopCarlistArray = new ArrayList<>();
			TcsbShopCarItemEntity tcsbShopCarItemEntity;
			for (TcsbShopCarItemEntity tcsbShopCar : tcsbShopCarlist) {
				tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
				try {
					BeanUtils.copyProperties(tcsbShopCarItemEntity, tcsbShopCar);
					TcsbFoodEntity food = tcsbShopCarService.get(TcsbFoodEntity.class, tcsbShopCar.getFoodId());
					if(StringUtil.isNotEmpty(tcsbShopCarItemEntity.getFoodTasteFun())){
						tcsbShopCarItemEntity.setFoodTasteFun(tcsbShopCarItemEntity.getFoodTasteFun().substring(0, tcsbShopCarItemEntity.getFoodTasteFun().length()-1));	
					}
					tcsbShopCarItemEntity.setName(food.getName());
					tcsbShopCarItemEntity.setPrice(food.getPrice());
					//tcsbShopCarItemEntity.setNum(tcsbShopCarItemEntity.getCount());
					tcsbShopCarlistArray.add(tcsbShopCarItemEntity);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			ajaxJsonApi.setObj(tcsbShopCarlistArray);
		}else{
			ajaxJsonApi.setObj(null);
		}
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	/**
	 * 
	 * 查询该座位是否已有新的订单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "checkShopCarNew",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject checkShopCarNew(HttpServletRequest request) {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		String shopCar= request.getParameter("shopCar");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			    TcsbShopCarVO tcsbShopCarVO = JSONHelper.fromJsonToObject(shopCar, TcsbShopCarVO.class);
				Boolean flag = tcsbShopCarService.checkShopCarNew(tcsbShopCarVO);
				map.put("isNew", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(map);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	/**
	 * 获取我的购物车
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping(value = "/getMyShopCar",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getMyShopCar(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		List<TcsbShopCarVO> maps = new ArrayList<>();
		try {
			maps = tcsbShopCarService.getMyShopCar(userId);
			for (TcsbShopCarVO tcsbShopCarVO : maps) {
				tcsbShopCarVO.setShopImg(getCkPath()+tcsbShopCarVO.getShopImg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(maps);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}*/
	
}
