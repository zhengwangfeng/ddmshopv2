package com.tcsb.tcsbshopbankcard.controller;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.tcsbshopbankcard.entity.TcsbShopBankcardEntity;
import com.tcsb.tcsbshopbankcard.service.TcsbShopBankcardServiceI;
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
import org.jeecgframework.core.util.GetBank;
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
 * @Description: 店铺关联银行卡
 * @author onlineGenerator
 * @date 2017-07-15 15:38:58
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopBankcardController")
public class TcsbShopBankcardController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopBankcardController.class);

	@Autowired
	private TcsbShopBankcardServiceI tcsbShopBankcardService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 店铺关联银行卡列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		
		TSUser user = getCurrentUser();
		TcsbShopBankcardEntity tcsbShopBankcard = tcsbShopBankcardService.findUniqueByProperty(TcsbShopBankcardEntity.class, "shopId", user.getShopId());
		if(StringUtil.isNotEmpty(tcsbShopBankcard)){
			return new ModelAndView("com/tcsb/tcsbshopbankcard/tcsbShopBankcardList");
		}else{
			TcsbShopBankcardEntity saveShopBankcard = new TcsbShopBankcardEntity();
			saveShopBankcard.setShopId(user.getShopId());
			saveShopBankcard.setBeneficiaryName(user.getRealName());
			try {
				tcsbShopBankcardService.save(saveShopBankcard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("com/tcsb/tcsbshopbankcard/tcsbShopBankcardList");
		}
		
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
	public void datagrid(TcsbShopBankcardEntity tcsbShopBankcard,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopBankcardEntity.class, dataGrid);
		TSUser user = getCurrentUser();
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopBankcard, request.getParameterMap());
		try{
		//自定义追加查询条件
			cq.eq("shopId", user.getShopId());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopBankcardService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除店铺关联银行卡
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopBankcardEntity tcsbShopBankcard, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopBankcard = systemService.getEntity(TcsbShopBankcardEntity.class, tcsbShopBankcard.getId());
		message = "店铺关联银行卡删除成功";
		try{
			tcsbShopBankcardService.delete(tcsbShopBankcard);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺关联银行卡删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除店铺关联银行卡
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺关联银行卡删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopBankcardEntity tcsbShopBankcard = systemService.getEntity(TcsbShopBankcardEntity.class, 
				id
				);
				tcsbShopBankcardService.delete(tcsbShopBankcard);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺关联银行卡删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加店铺关联银行卡
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopBankcardEntity tcsbShopBankcard, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺关联银行卡添加成功";
		try{
			tcsbShopBankcardService.save(tcsbShopBankcard);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺关联银行卡添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新店铺关联银行卡
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopBankcardEntity tcsbShopBankcard, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺关联银行卡更新成功";
		TcsbShopBankcardEntity t = tcsbShopBankcardService.get(TcsbShopBankcardEntity.class, tcsbShopBankcard.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopBankcard, t);
			String cardnumber = t.getBeneficiaryAccount().substring(0,6);
			String bankname = GetBank.getname(cardnumber);
			if(StringUtil.isNotEmpty(bankname)){
				if(bankname.indexOf("建设")>=0){
					t.setBeneficiaryBankcode("01");
				}else{
					t.setBeneficiaryBankcode("02");
				}
			}
			tcsbShopBankcardService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺关联银行卡更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺关联银行卡新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopBankcardEntity tcsbShopBankcard, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopBankcard.getId())) {
			tcsbShopBankcard = tcsbShopBankcardService.getEntity(TcsbShopBankcardEntity.class, tcsbShopBankcard.getId());
			req.setAttribute("tcsbShopBankcardPage", tcsbShopBankcard);
		}
		return new ModelAndView("com/tcsb/tcsbshopbankcard/tcsbShopBankcard-add");
	}
	/**
	 * 店铺关联银行卡编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopBankcardEntity tcsbShopBankcard, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopBankcard.getId())) {
			tcsbShopBankcard = tcsbShopBankcardService.getEntity(TcsbShopBankcardEntity.class, tcsbShopBankcard.getId());
			req.setAttribute("tcsbShopBankcardPage", tcsbShopBankcard);
		}
		return new ModelAndView("com/tcsb/tcsbshopbankcard/tcsbShopBankcard-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopBankcardController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopBankcardEntity tcsbShopBankcard,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopBankcardEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopBankcard, request.getParameterMap());
		List<TcsbShopBankcardEntity> tcsbShopBankcards = this.tcsbShopBankcardService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺关联银行卡");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopBankcardEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺关联银行卡列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopBankcards);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopBankcardEntity tcsbShopBankcard,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"店铺关联银行卡");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopBankcardEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺关联银行卡列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopBankcardEntity> listTcsbShopBankcardEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopBankcardEntity.class,params);
				for (TcsbShopBankcardEntity tcsbShopBankcard : listTcsbShopBankcardEntitys) {
					tcsbShopBankcardService.save(tcsbShopBankcard);
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
	public List<TcsbShopBankcardEntity> list() {
		List<TcsbShopBankcardEntity> listTcsbShopBankcards=tcsbShopBankcardService.getList(TcsbShopBankcardEntity.class);
		return listTcsbShopBankcards;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopBankcardEntity task = tcsbShopBankcardService.get(TcsbShopBankcardEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopBankcardEntity tcsbShopBankcard, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopBankcardEntity>> failures = validator.validate(tcsbShopBankcard);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopBankcardService.save(tcsbShopBankcard);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopBankcard.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopBankcardController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopBankcardEntity tcsbShopBankcard) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopBankcardEntity>> failures = validator.validate(tcsbShopBankcard);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopBankcardService.saveOrUpdate(tcsbShopBankcard);
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
		tcsbShopBankcardService.deleteEntityById(TcsbShopBankcardEntity.class, id);
	}
}
