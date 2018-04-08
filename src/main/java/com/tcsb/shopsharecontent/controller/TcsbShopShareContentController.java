package com.tcsb.shopsharecontent.controller;
import com.google.zxing.BarcodeFormat;
import com.tcsb.shopsharecontent.entity.TcsbShopShareContentEntity;
import com.tcsb.shopsharecontent.service.TcsbShopShareContentServiceI;
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
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.p3.core.util.plugin.ContextHolderUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.QRCodeUtil;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.File;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.ResourceBundle;

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
 * @Description: 分享内容管理
 * @author onlineGenerator
 * @date 2018-03-16 11:39:18
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopShareContentController")
public class TcsbShopShareContentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopShareContentController.class);

	@Autowired
	private TcsbShopShareContentServiceI tcsbShopShareContentService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 分享内容管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		TSUser user = getCurrentUser();
		TcsbShopShareContentEntity tcsbShopShareContentEntity = tcsbShopShareContentService.findUniqueByProperty(TcsbShopShareContentEntity.class, "shopId", user.getShopId());
		if(StringUtil.isEmpty(tcsbShopShareContentEntity)){
			TcsbShopShareContentEntity tcsbShopShareContentEntity2 = new TcsbShopShareContentEntity();
			String number = UUIDGenerator.generate();
			String toPath = "/shareqrcode/user"+user.getUserName()+"/number"+number+DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyyMMdd"));
			String filePostfix = "png";
			
			File file = new File(ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/")+toPath+"."+filePostfix);
			if (!file.exists()) {
				file.mkdirs();
			}
			
			String configUrl = ResourceBundle.getBundle("sysConfig").getString("configUrl");
			String redirectURL = configUrl+"share.html?shopId="+user.getShopId()+"&shareId=admin";
			//share.html?shopId=8a9ad8035c0c5381015c0c7461db0023&shareId=admin
			QRCodeUtil.encode(redirectURL, file, filePostfix, BarcodeFormat.QR_CODE, 500, 500, null);
			tcsbShopShareContentEntity2.setShopId(user.getShopId());
			tcsbShopShareContentEntity2.setQrCode(ResourceUtil.getConfigByName("qrcode")+toPath+"."+filePostfix);
			
			try {
				tcsbShopShareContentService.save(tcsbShopShareContentEntity2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new ModelAndView("com/tcsb/shopsharecontent/tcsbShopShareContentList");
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
	public void datagrid(TcsbShopShareContentEntity tcsbShopShareContent,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = getCurrentUser();
		CriteriaQuery cq = new CriteriaQuery(TcsbShopShareContentEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopShareContent, request.getParameterMap());
		try{
		//自定义追加查询条件
			cq.eq("shopId", user.getShopId());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopShareContentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除分享内容管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopShareContentEntity tcsbShopShareContent, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopShareContent = systemService.getEntity(TcsbShopShareContentEntity.class, tcsbShopShareContent.getId());
		message = "分享内容管理删除成功";
		try{
			tcsbShopShareContentService.delete(tcsbShopShareContent);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "分享内容管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除分享内容管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "分享内容管理删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopShareContentEntity tcsbShopShareContent = systemService.getEntity(TcsbShopShareContentEntity.class, 
				id
				);
				tcsbShopShareContentService.delete(tcsbShopShareContent);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "分享内容管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加分享内容管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopShareContentEntity tcsbShopShareContent, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "分享内容管理添加成功";
		try{
			tcsbShopShareContentService.save(tcsbShopShareContent);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "分享内容管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新分享内容管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopShareContentEntity tcsbShopShareContent, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "分享内容管理更新成功";
		TcsbShopShareContentEntity t = tcsbShopShareContentService.get(TcsbShopShareContentEntity.class, tcsbShopShareContent.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopShareContent, t);
			tcsbShopShareContentService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "分享内容管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 分享内容管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopShareContentEntity tcsbShopShareContent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopShareContent.getId())) {
			tcsbShopShareContent = tcsbShopShareContentService.getEntity(TcsbShopShareContentEntity.class, tcsbShopShareContent.getId());
			req.setAttribute("tcsbShopShareContentPage", tcsbShopShareContent);
		}
		return new ModelAndView("com/tcsb/shopsharecontent/tcsbShopShareContent-add");
	}
	/**
	 * 分享内容管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopShareContentEntity tcsbShopShareContent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopShareContent.getId())) {
			tcsbShopShareContent = tcsbShopShareContentService.getEntity(TcsbShopShareContentEntity.class, tcsbShopShareContent.getId());
			req.setAttribute("tcsbShopShareContentPage", tcsbShopShareContent);
		}
		return new ModelAndView("com/tcsb/shopsharecontent/tcsbShopShareContent-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopShareContentController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopShareContentEntity tcsbShopShareContent,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopShareContentEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopShareContent, request.getParameterMap());
		List<TcsbShopShareContentEntity> tcsbShopShareContents = this.tcsbShopShareContentService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"分享内容管理");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopShareContentEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("分享内容管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopShareContents);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopShareContentEntity tcsbShopShareContent,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"分享内容管理");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopShareContentEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("分享内容管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopShareContentEntity> listTcsbShopShareContentEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopShareContentEntity.class,params);
				for (TcsbShopShareContentEntity tcsbShopShareContent : listTcsbShopShareContentEntitys) {
					tcsbShopShareContentService.save(tcsbShopShareContent);
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
	public List<TcsbShopShareContentEntity> list() {
		List<TcsbShopShareContentEntity> listTcsbShopShareContents=tcsbShopShareContentService.getList(TcsbShopShareContentEntity.class);
		return listTcsbShopShareContents;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopShareContentEntity task = tcsbShopShareContentService.get(TcsbShopShareContentEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopShareContentEntity tcsbShopShareContent, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopShareContentEntity>> failures = validator.validate(tcsbShopShareContent);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopShareContentService.save(tcsbShopShareContent);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopShareContent.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopShareContentController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopShareContentEntity tcsbShopShareContent) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopShareContentEntity>> failures = validator.validate(tcsbShopShareContent);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopShareContentService.saveOrUpdate(tcsbShopShareContent);
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
		tcsbShopShareContentService.deleteEntityById(TcsbShopShareContentEntity.class, id);
	}
}
