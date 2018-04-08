package com.tcsb.shopevaluate.controller;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;

import com.tcsb.shopevaluate.entity.TcsbShopEvaluateEntity;
import com.tcsb.shopevaluate.service.TcsbShopEvaluateServiceI;
import com.tcsb.shopevaluate.vo.TcsbShopEvaluateEntityVo;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.weixin.weixinuser.entity.WeixinUserEntity;

import java.util.ArrayList;
import java.util.Date;
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
import org.jeecgframework.core.util.PageBean;
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
 * @Description: 店铺评价
 * @author onlineGenerator
 * @date 2017-04-24 13:50:10
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopEvaluateController")
public class TcsbShopEvaluateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopEvaluateController.class);

	@Autowired
	private TcsbShopEvaluateServiceI tcsbShopEvaluateService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	


	/**
	 * 店铺评价列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopevaluate/tcsbShopEvaluateList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbShopEvaluateEntity tcsbShopEvaluate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopEvaluateEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopEvaluate, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user=getCurrentUser();
			if (!user.getUserName().equals("admin")) {
				cq.eq("shopId", user.getShopId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopEvaluateService.getDataGridReturn(cq, true);
		
		List<TcsbShopEvaluateEntity> TcsbShopEvaluateEntityList = dataGrid.getResults();
		List<TcsbShopEvaluateEntityVo> TcsbShopEvaluateEntityVoList = new ArrayList<>();
		TcsbShopEvaluateEntityVo TcsbShopEvaluateEntityVo;
		
		for (TcsbShopEvaluateEntity TcsbShopEvaluateEntity : TcsbShopEvaluateEntityList) {
			TcsbShopEvaluateEntityVo = new TcsbShopEvaluateEntityVo();
			BeanUtils.copyProperties(TcsbShopEvaluateEntityVo, TcsbShopEvaluateEntity);
			TcsbShopEvaluateEntityVoList.add(TcsbShopEvaluateEntityVo);
		}
		int i=0;
		for (TcsbShopEvaluateEntityVo tcsbShopCollectVo : TcsbShopEvaluateEntityVoList) {
			TcsbShopEntity tcsbShopEntity= tcsbShopService.get(TcsbShopEntity.class, tcsbShopCollectVo.getShopId());
			if(StringUtil.isNotEmpty(tcsbShopEntity)){
				TcsbShopEvaluateEntityVoList.get(i).setShopName(tcsbShopEntity.getName());
			}
			
			String userhql ="from WeixinUserEntity where openid='"+tcsbShopCollectVo.getUserId()+"' and shopId='"+tcsbShopCollectVo.getShopId()+"'";
			WeixinUserEntity weixinUserEntity = tcsbShopService.singleResult(userhql);
			if(StringUtil.isNotEmpty(weixinUserEntity)){
				//TcsbShopEvaluateEntityVoList.get(i).setUserName(weixinUserEntity.getNickname());
				TcsbWeixinUserEntity tcsbWeixinUserEntity = systemService.findUniqueByProperty(TcsbWeixinUserEntity.class,"openid",weixinUserEntity.getOpenid());
				tcsbShopCollectVo.setUserName(tcsbWeixinUserEntity.getNickname());
			}
			i++;
		}
		
		dataGrid.setResults(TcsbShopEvaluateEntityVoList);
		
		
		
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除店铺评价
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopEvaluateEntity tcsbShopEvaluate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopEvaluate = systemService.getEntity(TcsbShopEvaluateEntity.class, tcsbShopEvaluate.getId());
		message = "店铺评价删除成功";
		try{
			tcsbShopEvaluateService.delete(tcsbShopEvaluate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺评价删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除店铺评价
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺评价删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopEvaluateEntity tcsbShopEvaluate = systemService.getEntity(TcsbShopEvaluateEntity.class, 
				id
				);
				tcsbShopEvaluateService.delete(tcsbShopEvaluate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺评价删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加店铺评价
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopEvaluateEntity tcsbShopEvaluate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺评价添加成功";
		try{
			tcsbShopEvaluateService.save(tcsbShopEvaluate);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺评价添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新店铺评价
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopEvaluateEntity tcsbShopEvaluate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺评价更新成功";
		TcsbShopEvaluateEntity t = tcsbShopEvaluateService.get(TcsbShopEvaluateEntity.class, tcsbShopEvaluate.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopEvaluate, t);
			tcsbShopEvaluateService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺评价更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺评价新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopEvaluateEntity tcsbShopEvaluate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopEvaluate.getId())) {
			tcsbShopEvaluate = tcsbShopEvaluateService.getEntity(TcsbShopEvaluateEntity.class, tcsbShopEvaluate.getId());
			req.setAttribute("tcsbShopEvaluatePage", tcsbShopEvaluate);
		}
		return new ModelAndView("com/tcsb/shopevaluate/tcsbShopEvaluate-add");
	}
	/**
	 * 店铺评价编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopEvaluateEntity tcsbShopEvaluate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopEvaluate.getId())) {
			tcsbShopEvaluate = tcsbShopEvaluateService.getEntity(TcsbShopEvaluateEntity.class, tcsbShopEvaluate.getId());
			req.setAttribute("tcsbShopEvaluatePage", tcsbShopEvaluate);
		}
		return new ModelAndView("com/tcsb/shopevaluate/tcsbShopEvaluate-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopEvaluateController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopEvaluateEntity tcsbShopEvaluate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopEvaluateEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopEvaluate, request.getParameterMap());
		List<TcsbShopEvaluateEntity> tcsbShopEvaluates = this.tcsbShopEvaluateService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺评价");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopEvaluateEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺评价列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopEvaluates);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopEvaluateEntity tcsbShopEvaluate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"店铺评价");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopEvaluateEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺评价列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopEvaluateEntity> listTcsbShopEvaluateEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopEvaluateEntity.class,params);
				for (TcsbShopEvaluateEntity tcsbShopEvaluate : listTcsbShopEvaluateEntitys) {
					tcsbShopEvaluateService.save(tcsbShopEvaluate);
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
	public List<TcsbShopEvaluateEntity> list() {
		List<TcsbShopEvaluateEntity> listTcsbShopEvaluates=tcsbShopEvaluateService.getList(TcsbShopEvaluateEntity.class);
		return listTcsbShopEvaluates;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopEvaluateEntity task = tcsbShopEvaluateService.get(TcsbShopEvaluateEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopEvaluateEntity tcsbShopEvaluate, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopEvaluateEntity>> failures = validator.validate(tcsbShopEvaluate);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopEvaluateService.save(tcsbShopEvaluate);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopEvaluate.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopEvaluateController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopEvaluateEntity tcsbShopEvaluate) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopEvaluateEntity>> failures = validator.validate(tcsbShopEvaluate);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopEvaluateService.saveOrUpdate(tcsbShopEvaluate);
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
		tcsbShopEvaluateService.deleteEntityById(TcsbShopEvaluateEntity.class, id);
	}
	
	@RequestMapping(value = "/addShopEvaluate",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject addShopEvaluate(TcsbShopEvaluateEntity tcsbShopEvaluate,HttpServletRequest request,HttpServletResponse response){
		//TODO
		System.out.println(tcsbShopEvaluate.getOrderId()+"=============");
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbShopEvaluate.getShopId());
		TcsbOrderEntity tcsbOrderEntity = tcsbShopService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", tcsbShopEvaluate.getOrderId());
		if(StringUtil.isNotEmpty(tcsbOrderEntity)){
			tcsbShopEvaluate.setOrderId(tcsbOrderEntity.getId());
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		if (tcsbShopEntity==null) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			ajaxJsonApi.setMsg(ReturnMessageEnum.SHOPNOTEXIT.getMsg());
			ajaxJsonApi.setSuccess(false);
			return new JSONPObject(callbackFunName, ajaxJsonApi); 
		}
		try {
			tcsbShopEvaluate.setCreateTime(new Date());
		
			tcsbShopEvaluateService.save(tcsbShopEvaluate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ajaxJsonApi.setMsg(ReturnMessageEnum.ADDSUCCESS.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	@RequestMapping(value = "/listByShopId",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject listByShopId(@RequestParam String shopId,@RequestParam String page,String pageSize,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		if (pageSize==null) {
			pageSize="5";
		}
		String sql = "select e.dishes_evaluation as dishesEvaluation,e.content,e.create_time as createTime,w.headimgurl,w.nickname from tcsb_shop_evaluate e left join weixin_user w on e.user_id = w.openid where e.shop_id = w.shop_id and e.shop_id=?";
		String countSql = "select count(1) from tcsb_shop_evaluate where shop_id = ?";
		List<Map<String, Object>> listShopEvaluates = tcsbShopEvaluateService.findForJdbcParam(sql, Integer.parseInt(page), Integer.parseInt(pageSize), shopId); 
		Long totalCount = tcsbShopEvaluateService.getCountForJdbcParam(countSql, new Object[]{shopId});
		//创建PAGEBEAN
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		pageBean.setTotalCount(Integer.parseInt(String.valueOf(totalCount)));
		pageBean.setList(listShopEvaluates);
		pageBean.setPage(Integer.parseInt(page));
		pageBean.setPageSize(Integer.parseInt(pageSize));
		pageBean.setTotalPage(Integer.parseInt(pageSize), Integer.parseInt(String.valueOf(totalCount)));
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		ajaxJson.setMsg("查询成功");
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(pageBean);
		return new JSONPObject(callbackFunName, ajaxJson);
	}
}
