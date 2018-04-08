package com.tcsb.tcsbpaynotice.controller;
import com.tcsb.callservice.entity.TcsbCallServiceEntity;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.tcsbpaynotice.entity.TcsbPayNoticeEntity;
import com.tcsb.tcsbpaynotice.service.TcsbPayNoticeServiceI;
import java.util.ArrayList;
import java.util.Date;
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
 * @Description: 支付消息通知
 * @author onlineGenerator
 * @date 2017-06-21 14:21:27
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbPayNoticeController")
public class TcsbPayNoticeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbPayNoticeController.class);

	@Autowired
	private TcsbPayNoticeServiceI tcsbPayNoticeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 支付消息通知列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/tcsbpaynotice/tcsbPayNoticeList");
	}
	
	
	@RequestMapping(params = "NotReadList")
	public ModelAndView notReadList(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/tcsbpaynotice/tcsbPayNotReadList");
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
	public void datagrid(TcsbPayNoticeEntity tcsbPayNotice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbPayNoticeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPayNotice, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbPayNoticeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 获取商家未读的支付消息
	 * @param tcsbPayNotice
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "notreaddatagrid")
	public void notreaddatagrid(TcsbPayNoticeEntity tcsbPayNotice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		TSUser user = getCurrentUser();
		TcsbShopEntity tcsbShop = tcsbPayNoticeService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
		if(StringUtil.isNotEmpty(tcsbShop)){
			CriteriaQuery cq = new CriteriaQuery(TcsbPayNoticeEntity.class, dataGrid);
			
			//查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPayNotice, request.getParameterMap());
			try{
			//自定义追加查询条件
				cq.eq("shopId", tcsbShop.getId());
				cq.eq("isread","0");
			}catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			cq.add();
			this.tcsbPayNoticeService.getDataGridReturn(cq, true);
			 for (Object o : dataGrid.getResults()) {
		            if (o instanceof TcsbPayNoticeEntity) {
		            	TcsbPayNoticeEntity cfe = (TcsbPayNoticeEntity) o;
		            	TcsbShopEntity tcsbShopEntity = tcsbPayNoticeService.get(TcsbShopEntity.class, cfe.getShopId());
		            	cfe.setShopName(tcsbShopEntity.getName());
		            	TcsbDeskEntity tcsbDeskEntity = tcsbPayNoticeService.findUniqueByProperty(TcsbDeskEntity.class, "number", cfe.getDeskId());
		            	cfe.setDeskName(tcsbDeskEntity.getDeskName());
		            }
		        }
		}
		TagUtil.datagrid(response, dataGrid);
	}

	
	/**
	 * 设置支付消息为已读
	 * 
	 * @return
	 */
	@RequestMapping(params = "isread")
	@ResponseBody
	public int isread(String ID, HttpServletRequest request) {
		TcsbPayNoticeEntity tcsbPayNoticeEntity= tcsbPayNoticeService.get(TcsbPayNoticeEntity.class, ID);
		try {
			tcsbPayNoticeEntity.setIsread("1");
			tcsbPayNoticeService.saveOrUpdate(tcsbPayNoticeEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 删除支付消息通知
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbPayNoticeEntity tcsbPayNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbPayNotice = systemService.getEntity(TcsbPayNoticeEntity.class, tcsbPayNotice.getId());
		message = "支付消息通知删除成功";
		try{
			tcsbPayNoticeService.delete(tcsbPayNotice);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "支付消息通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除支付消息通知
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "支付消息通知删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbPayNoticeEntity tcsbPayNotice = systemService.getEntity(TcsbPayNoticeEntity.class, 
				id
				);
				tcsbPayNoticeService.delete(tcsbPayNotice);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "支付消息通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加支付消息通知
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbPayNoticeEntity tcsbPayNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "支付消息通知添加成功";
		try{
			tcsbPayNoticeService.save(tcsbPayNotice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "支付消息通知添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新支付消息通知
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbPayNoticeEntity tcsbPayNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "支付消息通知更新成功";
		TcsbPayNoticeEntity t = tcsbPayNoticeService.get(TcsbPayNoticeEntity.class, tcsbPayNotice.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbPayNotice, t);
			tcsbPayNoticeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "支付消息通知更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 支付消息通知新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbPayNoticeEntity tcsbPayNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbPayNotice.getId())) {
			tcsbPayNotice = tcsbPayNoticeService.getEntity(TcsbPayNoticeEntity.class, tcsbPayNotice.getId());
			req.setAttribute("tcsbPayNoticePage", tcsbPayNotice);
		}
		return new ModelAndView("com/tcsb/tcsbpaynotice/tcsbPayNotice-add");
	}
	/**
	 * 支付消息通知编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbPayNoticeEntity tcsbPayNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbPayNotice.getId())) {
			tcsbPayNotice = tcsbPayNoticeService.getEntity(TcsbPayNoticeEntity.class, tcsbPayNotice.getId());
			req.setAttribute("tcsbPayNoticePage", tcsbPayNotice);
		}
		return new ModelAndView("com/tcsb/tcsbpaynotice/tcsbPayNotice-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbPayNoticeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbPayNoticeEntity tcsbPayNotice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbPayNoticeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPayNotice, request.getParameterMap());
		List<TcsbPayNoticeEntity> tcsbPayNotices = this.tcsbPayNoticeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"支付消息通知");
		modelMap.put(NormalExcelConstants.CLASS,TcsbPayNoticeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("支付消息通知列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbPayNotices);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbPayNoticeEntity tcsbPayNotice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"支付消息通知");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbPayNoticeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("支付消息通知列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbPayNoticeEntity> listTcsbPayNoticeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbPayNoticeEntity.class,params);
				for (TcsbPayNoticeEntity tcsbPayNotice : listTcsbPayNoticeEntitys) {
					tcsbPayNoticeService.save(tcsbPayNotice);
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
	public List<TcsbPayNoticeEntity> list() {
		List<TcsbPayNoticeEntity> listTcsbPayNotices=tcsbPayNoticeService.getList(TcsbPayNoticeEntity.class);
		return listTcsbPayNotices;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbPayNoticeEntity task = tcsbPayNoticeService.get(TcsbPayNoticeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbPayNoticeEntity tcsbPayNotice, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbPayNoticeEntity>> failures = validator.validate(tcsbPayNotice);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbPayNoticeService.save(tcsbPayNotice);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbPayNotice.getId();
		URI uri = uriBuilder.path("/rest/tcsbPayNoticeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbPayNoticeEntity tcsbPayNotice) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbPayNoticeEntity>> failures = validator.validate(tcsbPayNotice);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbPayNoticeService.saveOrUpdate(tcsbPayNotice);
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
		tcsbPayNoticeService.deleteEntityById(TcsbPayNoticeEntity.class, id);
	}
	
	
	@RequestMapping(value = "/payNotice",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject callService(@RequestParam String orderNo,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		try {
			TcsbOrderEntity tcsbOrderEntity = tcsbPayNoticeService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", orderNo);
			if(StringUtil.isNotEmpty(tcsbOrderEntity)){
				TcsbPayNoticeEntity tcsbPayNoticeEntity = new TcsbPayNoticeEntity();
				tcsbPayNoticeEntity.setDeskId(tcsbOrderEntity.getDeskId());
				tcsbPayNoticeEntity.setIsread("0");
				tcsbPayNoticeEntity.setPayMoney(tcsbOrderEntity.getOnlinePrice()+"");
				tcsbPayNoticeEntity.setShopId(tcsbOrderEntity.getShopId());
				tcsbPayNoticeService.save(tcsbPayNoticeEntity);
			}
			ajaxJsonApi.setMsg("支付消息存取成功");
			ajaxJsonApi.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ajaxJsonApi.setMsg("支付消息存取失败");
			ajaxJsonApi.setSuccess(false);
		}
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	
}
