package com.tcsb.tcsborderbigdatarecord.controller;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.tcsbassociatorbigdata.entity.TcsbAssociatorBigdataEntity;
import com.tcsb.tcsborderbigdatarecord.entity.TcsbOrderBigdataRecordEntity;
import com.tcsb.tcsborderbigdatarecord.service.TcsbOrderBigdataRecordServiceI;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.weixin.weixinuser.entity.WeixinUserEntity;

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

import org.jeecgframework.core.util.BigDecimalUtil;
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
 * @Description: 大数据生成项
 * @author onlineGenerator
 * @date 2017-07-18 10:55:05
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbOrderBigdataRecordController")
public class TcsbOrderBigdataRecordController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbOrderBigdataRecordController.class);

	@Autowired
	private TcsbOrderBigdataRecordServiceI tcsbOrderBigdataRecordService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 大数据生成项列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/tcsborderbigdatarecord/tcsbOrderBigdataRecordList");
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
	public void datagrid(TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbOrderBigdataRecordEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrderBigdataRecord, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbOrderBigdataRecordService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除大数据生成项
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbOrderBigdataRecord = systemService.getEntity(TcsbOrderBigdataRecordEntity.class, tcsbOrderBigdataRecord.getId());
		message = "大数据生成项删除成功";
		try{
			tcsbOrderBigdataRecordService.delete(tcsbOrderBigdataRecord);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "大数据生成项删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除大数据生成项
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "大数据生成项删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord = systemService.getEntity(TcsbOrderBigdataRecordEntity.class, 
				id
				);
				tcsbOrderBigdataRecordService.delete(tcsbOrderBigdataRecord);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "大数据生成项删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加大数据生成项
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "大数据生成项添加成功";
		try{
			tcsbOrderBigdataRecordService.save(tcsbOrderBigdataRecord);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "大数据生成项添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新大数据生成项
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "大数据生成项更新成功";
		TcsbOrderBigdataRecordEntity t = tcsbOrderBigdataRecordService.get(TcsbOrderBigdataRecordEntity.class, tcsbOrderBigdataRecord.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbOrderBigdataRecord, t);
			tcsbOrderBigdataRecordService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "大数据生成项更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 大数据生成项新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrderBigdataRecord.getId())) {
			tcsbOrderBigdataRecord = tcsbOrderBigdataRecordService.getEntity(TcsbOrderBigdataRecordEntity.class, tcsbOrderBigdataRecord.getId());
			req.setAttribute("tcsbOrderBigdataRecordPage", tcsbOrderBigdataRecord);
		}
		return new ModelAndView("com/tcsb/tcsborderbigdatarecord/tcsbOrderBigdataRecord-add");
	}
	/**
	 * 大数据生成项编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrderBigdataRecord.getId())) {
			tcsbOrderBigdataRecord = tcsbOrderBigdataRecordService.getEntity(TcsbOrderBigdataRecordEntity.class, tcsbOrderBigdataRecord.getId());
			req.setAttribute("tcsbOrderBigdataRecordPage", tcsbOrderBigdataRecord);
		}
		return new ModelAndView("com/tcsb/tcsborderbigdatarecord/tcsbOrderBigdataRecord-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbOrderBigdataRecordController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbOrderBigdataRecordEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrderBigdataRecord, request.getParameterMap());
		List<TcsbOrderBigdataRecordEntity> tcsbOrderBigdataRecords = this.tcsbOrderBigdataRecordService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"大数据生成项");
		modelMap.put(NormalExcelConstants.CLASS,TcsbOrderBigdataRecordEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("大数据生成项列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbOrderBigdataRecords);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"大数据生成项");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbOrderBigdataRecordEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("大数据生成项列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbOrderBigdataRecordEntity> listTcsbOrderBigdataRecordEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbOrderBigdataRecordEntity.class,params);
				for (TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord : listTcsbOrderBigdataRecordEntitys) {
					tcsbOrderBigdataRecordService.save(tcsbOrderBigdataRecord);
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
	public List<TcsbOrderBigdataRecordEntity> list() {
		List<TcsbOrderBigdataRecordEntity> listTcsbOrderBigdataRecords=tcsbOrderBigdataRecordService.getList(TcsbOrderBigdataRecordEntity.class);
		return listTcsbOrderBigdataRecords;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbOrderBigdataRecordEntity task = tcsbOrderBigdataRecordService.get(TcsbOrderBigdataRecordEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbOrderBigdataRecordEntity>> failures = validator.validate(tcsbOrderBigdataRecord);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbOrderBigdataRecordService.save(tcsbOrderBigdataRecord);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbOrderBigdataRecord.getId();
		URI uri = uriBuilder.path("/rest/tcsbOrderBigdataRecordController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecord) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbOrderBigdataRecordEntity>> failures = validator.validate(tcsbOrderBigdataRecord);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbOrderBigdataRecordService.saveOrUpdate(tcsbOrderBigdataRecord);
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
		tcsbOrderBigdataRecordService.deleteEntityById(TcsbOrderBigdataRecordEntity.class, id);
	}
	
	/*
	 * 根据订单号执行大数据更新操作
	 */
	@RequestMapping(value = "/executeBigDataUpdate",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getMyShopFoot(@RequestParam String orderNo,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		//检测该订单号是否在执行项中
		TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecordEntity = systemService.findUniqueByProperty(TcsbOrderBigdataRecordEntity.class,"orderno",orderNo);
		if(StringUtil.isNotEmpty(tcsbOrderBigdataRecordEntity)){
			//检测该执行项是否需要执行
			if(tcsbOrderBigdataRecordEntity.getIsExecute().equals(1)){
				//允许执行
					//根据orderno获取订单id
				TcsbOrderEntity tcsbOrderEntity = systemService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", orderNo);
				if(StringUtil.isNotEmpty(tcsbOrderEntity)){
					//跟据orderid获取需要更新的会员用户
					List<TcsbUserOrderEntity> tcsbUserOrderEntities = systemService.findByProperty(TcsbUserOrderEntity.class, "orderId", tcsbOrderEntity.getId());
					//遍历更新会员的消费数据
					for (TcsbUserOrderEntity tcsbUserOrderEntity : tcsbUserOrderEntities) {
						//检查该会员是否生成过该店铺的数据
						String tcsbAssociatorBigdatahql = "from TcsbAssociatorBigdataEntity where userId='"+tcsbUserOrderEntity.getUserId()+"' and shopId='"+tcsbOrderEntity.getShopId()+"'";
						TcsbAssociatorBigdataEntity  tcsbAssociatorBigdataEntity = systemService.singleResult(tcsbAssociatorBigdatahql);
						if(StringUtil.isNotEmpty(tcsbAssociatorBigdataEntity)){
							//已有数据  执行更新
							int saleCount = Integer.valueOf(tcsbAssociatorBigdataEntity.getSaleCount()).intValue()+1;
							tcsbAssociatorBigdataEntity.setSaleCount(saleCount+"");
							double saleTotal = BigDecimalUtil.add(Double.valueOf(tcsbAssociatorBigdataEntity.getSaleTotal()), tcsbOrderEntity.getTotalPrice());
							String saleAvgTotal = BigDecimalUtil.divide(saleTotal+"", tcsbAssociatorBigdataEntity.getSaleCount()+"",2);
							tcsbAssociatorBigdataEntity.setSaleTotal(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(saleTotal, 2)));
							tcsbAssociatorBigdataEntity.setLastSaleTime(tcsbOrderEntity.getCreateTime());
							tcsbAssociatorBigdataEntity.setSaleAvgTotal(Double.valueOf(saleAvgTotal));
							systemService.saveOrUpdate(tcsbAssociatorBigdataEntity);
						}else{
							//生成新的数据
							TcsbAssociatorBigdataEntity tcsbAssociatorBigdatasave = new TcsbAssociatorBigdataEntity();
							tcsbAssociatorBigdatasave.setLastSaleTime(tcsbOrderEntity.getCreateTime());
							tcsbAssociatorBigdatasave.setSaleAvgTotal(tcsbOrderEntity.getTotalPrice());
							tcsbAssociatorBigdatasave.setSaleCount("1");
							tcsbAssociatorBigdatasave.setSaleTotal(tcsbOrderEntity.getTotalPrice());
							tcsbAssociatorBigdatasave.setShopId(tcsbOrderEntity.getShopId());
							String getUserHql = "from WeixinUserEntity where openid='"+tcsbUserOrderEntity.getUserId()+"' and shopId='"+tcsbOrderEntity.getShopId()+"'";
							WeixinUserEntity tcsbWeixinUserEntity = systemService.singleResult(getUserHql);
							//tcsbAssociatorBigdatasave.setUserCity(tcsbWeixinUserEntity.getCity());
							tcsbAssociatorBigdatasave.setUserId(tcsbUserOrderEntity.getUserId());
							//tcsbAssociatorBigdatasave.setUserMobile(tcsbWeixinUserEntity.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
							//tcsbAssociatorBigdatasave.setUserNickname(tcsbWeixinUserEntity.getNickname());
							systemService.save(tcsbAssociatorBigdatasave);
						}
					}
					tcsbOrderBigdataRecordEntity.setIsExecute(0);
					systemService.saveOrUpdate(tcsbOrderBigdataRecordEntity);
				}
			}
			//else  已执行过更新操作
		}
		//else  执行项不存在
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	
}
