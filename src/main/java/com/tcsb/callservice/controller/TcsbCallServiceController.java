package com.tcsb.callservice.controller;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.callservice.entity.TcsbCallServiceEntity;
import com.tcsb.callservice.service.TcsbCallServiceServiceI;
import com.tcsb.callservice.vo.TcsbCallServiceEntityVo;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.service.entity.TcsbServiceEntity;
import com.tcsb.service.service.TcsbServiceServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.jeecgframework.web.system.pojo.base.TSUser;
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
import org.hibernate.criterion.Restrictions;
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
 * @Description: 呼叫服务
 * @author onlineGenerator
 * @date 2017-05-10 15:23:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbCallServiceController")
public class TcsbCallServiceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbCallServiceController.class);

	@Autowired
	private TcsbCallServiceServiceI tcsbCallServiceService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbServiceServiceI tcsbServiceService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	


	/**
	 * 呼叫服务列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/callservice/tcsbCallServiceList");
	}

	/**
	 * 呼叫服务列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "NotReadList")
	public ModelAndView NotReadList(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/callservice/tcsbCallServiceListNotRead");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	
	//todo
	@RequestMapping(params = "datagrid")
	public void datagrid(HttpSession session,TcsbCallServiceEntity tcsbCallService,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//获取登入用户信息
		TSUser user = null;
		if(session.getAttribute(ResourceUtil.LOCAL_CLINET_USER) != null){
			user = (TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		}
		//获取用户店铺信息
		TcsbShopEntity tcsbShopEntity= tcsbShopService.getTcsbShopEntityByUserid(user.getId());
		
		CriteriaQuery cq = new CriteriaQuery(TcsbCallServiceEntity.class, dataGrid);
		cq.add(Restrictions.eq("isread", "0"));
		cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
		List<TcsbCallServiceEntity> TcsbCallServiceList = tcsbCallServiceService.getListByCriteriaQuery(cq, true);
		List<TcsbCallServiceEntityVo> tcsbCallServiceEntityVoList = new ArrayList<>();
		TcsbCallServiceEntityVo tcsbCallServiceEntityVo;
		for (TcsbCallServiceEntity tcsbCallServiceEntity2 : TcsbCallServiceList) {
			tcsbCallServiceEntityVo = new TcsbCallServiceEntityVo();
			try {
				BeanUtils.copyProperties(tcsbCallServiceEntityVo, tcsbCallServiceEntity2);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TcsbServiceEntity TcsbServiceEntity = tcsbServiceService.get(TcsbServiceEntity.class, tcsbCallServiceEntity2.getShopServiceId());
			if(TcsbServiceEntity != null){
				tcsbCallServiceEntityVo.setServiceName(TcsbServiceEntity.getName());
			}else{
				tcsbCallServiceEntityVo.setServiceName("呼叫服务员");
			}
			TcsbDeskEntity TcsbDeskEntity = tcsbServiceService.get(TcsbDeskEntity.class, tcsbCallServiceEntity2.getDeskId());
			tcsbCallServiceEntityVo.setDeskId(TcsbDeskEntity.getDeskName());
			tcsbCallServiceEntityVoList.add(tcsbCallServiceEntityVo);
			
			//更新消息为已播放音频
			TcsbCallServiceEntity updateTcsbCallServiceEntity = tcsbCallServiceService.get(TcsbCallServiceEntity.class, tcsbCallServiceEntity2.getId());
			try {
				updateTcsbCallServiceEntity.setIsplay("0");
				tcsbCallServiceService.saveOrUpdate(updateTcsbCallServiceEntity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String hql = "from TcsbCallServiceEntity where isread='0'";
		List<TcsbCallServiceEntity> tcsbCallServiceEntity = tcsbCallServiceService.findByQueryString(hql);
		//查询条件组装器
		/*org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbCallService, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbCallServiceService.getDataGridReturn(cq, true);*/
		dataGrid.setTotal(tcsbCallServiceEntity.size());
		dataGrid.setResults(tcsbCallServiceEntityVoList);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridNotRead")
	public void datagridNotRead(TcsbCallServiceEntity tcsbCallService,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = getCurrentUser();
		CriteriaQuery cq = new CriteriaQuery(TcsbCallServiceEntity.class, dataGrid);
		cq.add(Restrictions.eq("isread", "0"));
		cq.add(Restrictions.eq("shopId", user.getShopId()));
		List<TcsbCallServiceEntity> TcsbCallServiceList = tcsbCallServiceService.getListByCriteriaQuery(cq, true);
		List<TcsbCallServiceEntityVo> tcsbCallServiceEntityVoList = new ArrayList<>();
		TcsbCallServiceEntityVo tcsbCallServiceEntityVo;
		for (TcsbCallServiceEntity tcsbCallServiceEntity2 : TcsbCallServiceList) {
			tcsbCallServiceEntityVo = new TcsbCallServiceEntityVo();
			try {
				BeanUtils.copyProperties(tcsbCallServiceEntityVo, tcsbCallServiceEntity2);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TcsbServiceEntity TcsbServiceEntity = tcsbServiceService.get(TcsbServiceEntity.class, tcsbCallServiceEntity2.getShopServiceId());
			if(TcsbServiceEntity != null){
				tcsbCallServiceEntityVo.setServiceName(TcsbServiceEntity.getName());
			}else{
				tcsbCallServiceEntityVo.setServiceName("呼叫服务员");
			}
			TcsbDeskEntity TcsbDeskEntity = tcsbServiceService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbCallServiceEntity2.getDeskId());
			tcsbCallServiceEntityVo.setDeskId(TcsbDeskEntity.getDeskName());
			tcsbCallServiceEntityVoList.add(tcsbCallServiceEntityVo);
			
			//更新消息为已播放音频
			TcsbCallServiceEntity updateTcsbCallServiceEntity = tcsbCallServiceService.get(TcsbCallServiceEntity.class, tcsbCallServiceEntity2.getId());
			try {
				updateTcsbCallServiceEntity.setIsplay("0");
				tcsbCallServiceService.saveOrUpdate(updateTcsbCallServiceEntity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String hql = "from TcsbCallServiceEntity where isread='0' and shopId='"+user.getShopId()+"'";
		List<TcsbCallServiceEntity> tcsbCallServiceEntity = tcsbCallServiceService.findByQueryString(hql);
		//查询条件组装器
		/*org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbCallService, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbCallServiceService.getDataGridReturn(cq, true);*/
		dataGrid.setTotal(tcsbCallServiceEntity.size());
		dataGrid.setResults(tcsbCallServiceEntityVoList);
		TagUtil.datagrid(response, dataGrid);
		
		/*CriteriaQuery cq = new CriteriaQuery(TcsbCallServiceEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbCallService, request.getParameterMap());
		TSUser user = getCurrentUser();
		try{
			if (!checkAdmin()) {
				TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
				cq.eq("shopId",tcsbShopEntity.getId());
			}
			
			cq.eq("isread", "0");
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbCallServiceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);*/
	}
	
	
	
	/**
	 * 用户呼叫服务信息
	 * @param userId 
	 * @param shopId 商店编号
	 * @param deskId 桌号
	 * @param serviceId 服务id
	 * @param count 数量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/callService",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject callService(@RequestParam String userId,@RequestParam String shopId,@RequestParam String deskId,@RequestParam String serviceId,Integer count,HttpServletRequest request,HttpServletResponse response){
		System.out.println("呼叫服务");
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		try {
			System.out.println("保存服务");
			TcsbCallServiceEntity call = new TcsbCallServiceEntity();
			call.setCount(count);
			call.setCreateTime(new Date());
			call.setDeskId(deskId);
			call.setIsplay("1");
			call.setIsread("0");
			call.setShopId(shopId);
			call.setShopServiceId(serviceId);
			call.setUserId(userId);
			tcsbCallServiceService.save(call);
			ajaxJsonApi.setMsg("呼叫成功");
			ajaxJsonApi.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ajaxJsonApi.setMsg("呼叫失败");
			ajaxJsonApi.setSuccess(false);
		}
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	
	
	/**
	 * 呼叫服务信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "getcallMessage")
	@ResponseBody
	public List<TcsbCallServiceEntityVo> getcallMessage(HttpServletRequest request) {
		String hql = "from TcsbCallServiceEntity where isread='0'";
		List<TcsbCallServiceEntity> tcsbCallServiceEntity = tcsbCallServiceService.findByQueryString(hql);
		List<TcsbCallServiceEntityVo> tcsbCallServiceEntityVoList = new ArrayList<>();
		TcsbCallServiceEntityVo tcsbCallServiceEntityVo;
		for (TcsbCallServiceEntity tcsbCallServiceEntity2 : tcsbCallServiceEntity) {
			tcsbCallServiceEntityVo = new TcsbCallServiceEntityVo();
			try {
				BeanUtils.copyProperties(tcsbCallServiceEntityVo, tcsbCallServiceEntity2);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TcsbServiceEntity TcsbServiceEntity = tcsbServiceService.get(TcsbServiceEntity.class, tcsbCallServiceEntity2.getShopServiceId());
			if(TcsbServiceEntity != null){
				tcsbCallServiceEntityVo.setServiceName(TcsbServiceEntity.getName());
			}else{
				tcsbCallServiceEntityVo.setServiceName("呼叫服务员");
			}
			tcsbCallServiceEntityVoList.add(tcsbCallServiceEntityVo);
			
			//更新消息为已播放音频
			TcsbCallServiceEntity updateTcsbCallServiceEntity = tcsbCallServiceService.get(TcsbCallServiceEntity.class, tcsbCallServiceEntity2.getId());
			try {
				updateTcsbCallServiceEntity.setIsplay("0");
				tcsbCallServiceService.saveOrUpdate(updateTcsbCallServiceEntity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tcsbCallServiceEntityVoList;
	}
	
	/**
	 * 设置呼叫服务为已读
	 * 
	 * @return
	 */
	@RequestMapping(params = "isread")
	@ResponseBody
	public int isread(String ID, HttpServletRequest request) {
		TcsbCallServiceEntity tcsbCallServiceEntity= tcsbCallServiceService.get(TcsbCallServiceEntity.class, ID);
		try {
			tcsbCallServiceEntity.setIsread("1");
			tcsbCallServiceService.saveOrUpdate(tcsbCallServiceEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	
	
	@RequestMapping(params = "getCallShopId")
	@ResponseBody
	public String getCallShopId(HttpSession session) {
		//TODO
		// 获取登入用户信息
		TSUser user = null;
		if (session.getAttribute(ResourceUtil.LOCAL_CLINET_USER) != null) {
			user = (TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		}
		// 获取用户店铺信息
		TcsbShopEntity tcsbShopEntity = tcsbShopService.getTcsbShopEntityByUserid(user.getId());

		return tcsbShopEntity.getId();
	}
	
	/**
	 * 删除呼叫服务
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbCallServiceEntity tcsbCallService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbCallService = systemService.getEntity(TcsbCallServiceEntity.class, tcsbCallService.getId());
		message = "呼叫服务删除成功";
		try{
			tcsbCallServiceService.delete(tcsbCallService);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "呼叫服务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除呼叫服务
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "呼叫服务删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbCallServiceEntity tcsbCallService = systemService.getEntity(TcsbCallServiceEntity.class, 
				id
				);
				tcsbCallServiceService.delete(tcsbCallService);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "呼叫服务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加呼叫服务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbCallServiceEntity tcsbCallService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "呼叫服务添加成功";
		try{
			tcsbCallServiceService.save(tcsbCallService);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "呼叫服务添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新呼叫服务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbCallServiceEntity tcsbCallService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "呼叫服务更新成功";
		TcsbCallServiceEntity t = tcsbCallServiceService.get(TcsbCallServiceEntity.class, tcsbCallService.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbCallService, t);
			tcsbCallServiceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "呼叫服务更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 呼叫服务新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbCallServiceEntity tcsbCallService, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbCallService.getId())) {
			tcsbCallService = tcsbCallServiceService.getEntity(TcsbCallServiceEntity.class, tcsbCallService.getId());
			req.setAttribute("tcsbCallServicePage", tcsbCallService);
		}
		return new ModelAndView("com/tcsb/callService/tcsbCallService-add");
	}
	/**
	 * 呼叫服务编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbCallServiceEntity tcsbCallService, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbCallService.getId())) {
			tcsbCallService = tcsbCallServiceService.getEntity(TcsbCallServiceEntity.class, tcsbCallService.getId());
			req.setAttribute("tcsbCallServicePage", tcsbCallService);
		}
		return new ModelAndView("com/tcsb/callService/tcsbCallService-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbCallServiceController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbCallServiceEntity tcsbCallService,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbCallServiceEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbCallService, request.getParameterMap());
		List<TcsbCallServiceEntity> tcsbCallServices = this.tcsbCallServiceService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"呼叫服务");
		modelMap.put(NormalExcelConstants.CLASS,TcsbCallServiceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("呼叫服务列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbCallServices);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbCallServiceEntity tcsbCallService,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"呼叫服务");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbCallServiceEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("呼叫服务列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbCallServiceEntity> listTcsbCallServiceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbCallServiceEntity.class,params);
				for (TcsbCallServiceEntity tcsbCallService : listTcsbCallServiceEntitys) {
					tcsbCallServiceService.save(tcsbCallService);
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
	public List<TcsbCallServiceEntity> list() {
		List<TcsbCallServiceEntity> listTcsbCallServices=tcsbCallServiceService.getList(TcsbCallServiceEntity.class);
		return listTcsbCallServices;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbCallServiceEntity task = tcsbCallServiceService.get(TcsbCallServiceEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbCallServiceEntity tcsbCallService, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbCallServiceEntity>> failures = validator.validate(tcsbCallService);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbCallServiceService.save(tcsbCallService);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbCallService.getId();
		URI uri = uriBuilder.path("/rest/tcsbCallServiceController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbCallServiceEntity tcsbCallService) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbCallServiceEntity>> failures = validator.validate(tcsbCallService);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbCallServiceService.saveOrUpdate(tcsbCallService);
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
		tcsbCallServiceService.deleteEntityById(TcsbCallServiceEntity.class, id);
	}
}
