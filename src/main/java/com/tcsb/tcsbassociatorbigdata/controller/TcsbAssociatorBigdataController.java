package com.tcsb.tcsbassociatorbigdata.controller;
import com.tcsb.tcsbassociatorbigdata.entity.TcsbAssociatorBigdataEntity;
import com.tcsb.tcsbassociatorbigdata.service.TcsbAssociatorBigdataServiceI;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
 * @Description: 会员信息统计
 * @author onlineGenerator
 * @date 2017-07-18 11:37:59
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbAssociatorBigdataController")
public class TcsbAssociatorBigdataController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbAssociatorBigdataController.class);

	@Autowired
	private TcsbAssociatorBigdataServiceI tcsbAssociatorBigdataService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 会员信息统计列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/tcsbassociatorbigdata/tcsbAssociatorBigdataList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbAssociatorBigdataEntity tcsbAssociatorBigdata,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws ParseException {
		TSUser user = getCurrentUser();
		StringBuilder sql = new StringBuilder();

		sql.append("select wu.ID as id,wu.openid as userId,u.nickname as userNickname,IFNULL(u.mobile, \"\") as userMobile,IFNULL(u.city, \"\") as userCity,format(count(DISTINCT(p.ID)),0) saleCount,format(sum(i.price*i.count),1) saleTotal, format(sum(i.price*i.count)/count(DISTINCT(p.ID)),1) as saleAvgTotal,max(o.create_date) as lastSaleTime ");
		sql.append("from tcsb_order_parent p ");
		sql.append("LEFT JOIN tcsb_order o on p.ID = o.order_parent_id ");
		sql.append("LEFT JOIN tcsb_order_item i on o.ID = i.order_id ");
		sql.append("LEFT JOIN tcsb_user_order uo on p.ID = uo.order_parent_id ");
		sql.append("LEFT JOIN weixin_user wu on wu.openid = uo.user_id ");
		sql.append("LEFT JOIN tcsb_weixin_user u on uo.user_id=u.openid ");
		sql.append("where 1=1 ");
		if(!checkAdmin()){
			sql.append("and wu.shop_id='"+user.getShopId()+"' ");
		}
		sql.append("and o.pay_status='1' GROUP BY wu.ID HAVING 1=1");
		if(StringUtils.isNotEmpty(request.getParameter("userNickname"))){
			sql.append(" and userNickname like '%"+request.getParameter("userNickname")+"%'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("saleCount"))){
			sql.append(" and saleCount='"+request.getParameter("saleCount")+"'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("saleTotal_begin"))){
			sql.append(" and saleTotal>'"+request.getParameter("saleTotal_begin")+"'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("saleTotal_end"))){
			sql.append(" and saleTotal<'"+request.getParameter("saleTotal_end")+"'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("saleAvgTotal_begin"))){
			sql.append(" and saleAvgTotal>'"+request.getParameter("saleAvgTotal_begin")+"'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("saleAvgTotal_end"))){
			sql.append(" and saleAvgTotal<'"+request.getParameter("saleAvgTotal_end")+"'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("lastSaleTime_begin"))){
			sql.append(" and lastSaleTime>'"+request.getParameter("lastSaleTime_begin")+"'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("lastSaleTime_end"))){
			sql.append(" and lastSaleTime<'"+request.getParameter("lastSaleTime_end")+"'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("userCity"))){
			sql.append(" and userCity like '%"+request.getParameter("userCity")+"%'");
		}
		StringBuilder sqlCount = new StringBuilder(sql);
		sqlCount.insert(0,"select count(*) as count from(");
		sqlCount.append(")cou");

		List<Map<String,Object>> countList = tcsbAssociatorBigdataService.findForJdbc(sqlCount.toString());
		int count = Integer.parseInt(String.valueOf(countList.get(0).get("count")));
		sql.append(" limit ");
		sql.append((dataGrid.getPage()-1)*dataGrid.getRows());
		sql.append(",");
		sql.append((dataGrid.getPage())*dataGrid.getRows()>count?count:(dataGrid.getPage())*dataGrid.getRows());
		List<Map<String,Object>> tcsbAssociatorBigdataList = tcsbAssociatorBigdataService.findForJdbc(sql.toString());
		dataGrid.setTotal(count);
		dataGrid.setResults(tcsbAssociatorBigdataList);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除会员信息统计
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbAssociatorBigdataEntity tcsbAssociatorBigdata, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbAssociatorBigdata = systemService.getEntity(TcsbAssociatorBigdataEntity.class, tcsbAssociatorBigdata.getId());
		message = "会员信息统计删除成功";
		try{
			tcsbAssociatorBigdataService.delete(tcsbAssociatorBigdata);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "会员信息统计删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除会员信息统计
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "会员信息统计删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbAssociatorBigdataEntity tcsbAssociatorBigdata = systemService.getEntity(TcsbAssociatorBigdataEntity.class, 
				id
				);
				tcsbAssociatorBigdataService.delete(tcsbAssociatorBigdata);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "会员信息统计删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加会员信息统计
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbAssociatorBigdataEntity tcsbAssociatorBigdata, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "会员信息统计添加成功";
		try{
			tcsbAssociatorBigdataService.save(tcsbAssociatorBigdata);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "会员信息统计添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新会员信息统计
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbAssociatorBigdataEntity tcsbAssociatorBigdata, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "会员信息统计更新成功";
		TcsbAssociatorBigdataEntity t = tcsbAssociatorBigdataService.get(TcsbAssociatorBigdataEntity.class, tcsbAssociatorBigdata.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbAssociatorBigdata, t);
			tcsbAssociatorBigdataService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "会员信息统计更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 会员信息统计新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbAssociatorBigdataEntity tcsbAssociatorBigdata, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbAssociatorBigdata.getId())) {
			tcsbAssociatorBigdata = tcsbAssociatorBigdataService.getEntity(TcsbAssociatorBigdataEntity.class, tcsbAssociatorBigdata.getId());
			req.setAttribute("tcsbAssociatorBigdataPage", tcsbAssociatorBigdata);
		}
		return new ModelAndView("com/tcsb/tcsbassociatorbigdata/tcsbAssociatorBigdata-add");
	}
	/**
	 * 会员信息统计编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbAssociatorBigdataEntity tcsbAssociatorBigdata, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbAssociatorBigdata.getId())) {
			tcsbAssociatorBigdata = tcsbAssociatorBigdataService.getEntity(TcsbAssociatorBigdataEntity.class, tcsbAssociatorBigdata.getId());
			req.setAttribute("tcsbAssociatorBigdataPage", tcsbAssociatorBigdata);
		}
		return new ModelAndView("com/tcsb/tcsbassociatorbigdata/tcsbAssociatorBigdata-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbAssociatorBigdataController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbAssociatorBigdataEntity tcsbAssociatorBigdata,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbAssociatorBigdataEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbAssociatorBigdata, request.getParameterMap());
		List<TcsbAssociatorBigdataEntity> tcsbAssociatorBigdatas = this.tcsbAssociatorBigdataService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"会员信息统计");
		modelMap.put(NormalExcelConstants.CLASS,TcsbAssociatorBigdataEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("会员信息统计列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbAssociatorBigdatas);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbAssociatorBigdataEntity tcsbAssociatorBigdata,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"会员信息统计");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbAssociatorBigdataEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("会员信息统计列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbAssociatorBigdataEntity> listTcsbAssociatorBigdataEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbAssociatorBigdataEntity.class,params);
				for (TcsbAssociatorBigdataEntity tcsbAssociatorBigdata : listTcsbAssociatorBigdataEntitys) {
					tcsbAssociatorBigdataService.save(tcsbAssociatorBigdata);
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
	public List<TcsbAssociatorBigdataEntity> list() {
		List<TcsbAssociatorBigdataEntity> listTcsbAssociatorBigdatas=tcsbAssociatorBigdataService.getList(TcsbAssociatorBigdataEntity.class);
		return listTcsbAssociatorBigdatas;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbAssociatorBigdataEntity task = tcsbAssociatorBigdataService.get(TcsbAssociatorBigdataEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbAssociatorBigdataEntity tcsbAssociatorBigdata, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbAssociatorBigdataEntity>> failures = validator.validate(tcsbAssociatorBigdata);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbAssociatorBigdataService.save(tcsbAssociatorBigdata);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbAssociatorBigdata.getId();
		URI uri = uriBuilder.path("/rest/tcsbAssociatorBigdataController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbAssociatorBigdataEntity tcsbAssociatorBigdata) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbAssociatorBigdataEntity>> failures = validator.validate(tcsbAssociatorBigdata);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbAssociatorBigdataService.saveOrUpdate(tcsbAssociatorBigdata);
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
		tcsbAssociatorBigdataService.deleteEntityById(TcsbAssociatorBigdataEntity.class, id);
	}
}
