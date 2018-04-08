package com.weixin.weixinsubscribe.controller;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinaccount.service.WeixinAccountServiceI;
import com.weixin.weixinnewstemplate.entity.WeixinNewstemplateEntity;
import com.weixin.weixinnewstemplate.service.WeixinNewstemplateServiceI;
import com.weixin.weixinsubscribe.entity.WeixinSubscribeEntity;
import com.weixin.weixinsubscribe.service.WeixinSubscribeServiceI;
import com.weixin.weixintexttemplate.entity.WeixinTexttemplateEntity;
import com.weixin.weixintexttemplate.service.WeixinTexttemplateServiceI;

/**   
 * @Title: Controller  
 * @Description: 微信关注欢迎语
 * @author onlineGenerator
 * @date 2017-03-28 10:01:19
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/weixinSubscribeController")
public class WeixinSubscribeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WeixinSubscribeController.class);

	@Autowired
	private WeixinSubscribeServiceI weixinSubscribeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private WeixinTexttemplateServiceI weixinTexttemplateService;
	@Autowired
	private WeixinNewstemplateServiceI weixinNewstemplateService;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;


	/**
	 * 微信关注欢迎语列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weixin/weixinsubscribe/weixinSubscribeList");
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
	public void datagrid(WeixinSubscribeEntity weixinSubscribe,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WeixinSubscribeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinSubscribe, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.weixinSubscribeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除微信关注欢迎语
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WeixinSubscribeEntity weixinSubscribe, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		weixinSubscribe = systemService.getEntity(WeixinSubscribeEntity.class, weixinSubscribe.getId());
		message = "微信关注欢迎语删除成功";
		try{
			weixinSubscribeService.delete(weixinSubscribe);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信关注欢迎语删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除微信关注欢迎语
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信关注欢迎语删除成功";
		try{
			for(String id:ids.split(",")){
				WeixinSubscribeEntity weixinSubscribe = systemService.getEntity(WeixinSubscribeEntity.class, 
				id
				);
				weixinSubscribeService.delete(weixinSubscribe);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "微信关注欢迎语删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加微信关注欢迎语
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WeixinSubscribeEntity weixinSubscribe, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信关注欢迎语添加成功";
		try{
			//获取公众号信息
			List<WeixinAccountEntity>  weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
			WeixinAccountEntity weixinAccountEntity = weixinAccountEntities.get(0);
			//判断当前公众帐号是否已经配置欢迎语
			int size = weixinSubscribeService.findByProperty(WeixinSubscribeEntity.class, "accountid", weixinAccountEntity.getId()).size();				
			//如果集合不为0
			if(size!=0){
				j.setSuccess(false);
				j.setMsg("每个公众帐号只能配置一个欢迎语。");
				return j;
			}
			String templateName = "";
			if ("text".equals(weixinSubscribe.getMsgtype())) {
				WeixinTexttemplateEntity textTemplate = this.weixinTexttemplateService.getEntity(WeixinTexttemplateEntity.class, weixinSubscribe.getTemplateid());
				if (textTemplate != null) {
					templateName = textTemplate.getTemplatename();
				}
			} else if ("news".equals(weixinSubscribe.getMsgtype())) {
				WeixinNewstemplateEntity newsTemplate = this.weixinNewstemplateService.getEntity(WeixinNewstemplateEntity.class, weixinSubscribe.getTemplateid());
				if (newsTemplate != null) {
					templateName = newsTemplate.getTemplatename();
				}
			}
			weixinSubscribe.setAccountid(weixinAccountEntity.getId());
			weixinSubscribe.setTemplatename(templateName);
			weixinSubscribeService.save(weixinSubscribe);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信关注欢迎语添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新微信关注欢迎语
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WeixinSubscribeEntity weixinSubscribe, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信关注欢迎语更新成功";
		WeixinSubscribeEntity t = weixinSubscribeService.get(WeixinSubscribeEntity.class, weixinSubscribe.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(weixinSubscribe, t);
			weixinSubscribeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "微信关注欢迎语更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 微信关注欢迎语新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WeixinSubscribeEntity weixinSubscribe, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinSubscribe.getId())) {
			weixinSubscribe = weixinSubscribeService.getEntity(WeixinSubscribeEntity.class, weixinSubscribe.getId());
			req.setAttribute("weixinSubscribePage", weixinSubscribe);
		}
		//设置文本消息和图文消息
		List<WeixinTexttemplateEntity> weixinTexttemplateEntities = weixinTexttemplateService.getList(WeixinTexttemplateEntity.class);
		req.setAttribute("textList", weixinTexttemplateEntities);
		List<WeixinNewstemplateEntity> weixinNewstemplateEntities = weixinNewstemplateService.getList(WeixinNewstemplateEntity.class);
		req.setAttribute("newsList", weixinNewstemplateEntities);
		return new ModelAndView("com/weixin/weixinsubscribe/weixinSubscribe-add");
	}
	/**
	 * 微信关注欢迎语编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WeixinSubscribeEntity weixinSubscribe, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinSubscribe.getId())) {
			weixinSubscribe = weixinSubscribeService.getEntity(WeixinSubscribeEntity.class, weixinSubscribe.getId());
			req.setAttribute("weixinSubscribePage", weixinSubscribe);
		}
		//设置文本消息和图文消息
		List<WeixinTexttemplateEntity> weixinTexttemplateEntities = weixinTexttemplateService.getList(WeixinTexttemplateEntity.class);
		req.setAttribute("textList", weixinTexttemplateEntities);
		List<WeixinNewstemplateEntity> weixinNewstemplateEntities = weixinNewstemplateService.getList(WeixinNewstemplateEntity.class);
		req.setAttribute("newsList", weixinNewstemplateEntities);
		req.setAttribute("lx", weixinSubscribe.getMsgtype());
		return new ModelAndView("com/weixin/weixinsubscribe/weixinSubscribe-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","weixinSubscribeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WeixinSubscribeEntity weixinSubscribe,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WeixinSubscribeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinSubscribe, request.getParameterMap());
		List<WeixinSubscribeEntity> weixinSubscribes = this.weixinSubscribeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"微信关注欢迎语");
		modelMap.put(NormalExcelConstants.CLASS,WeixinSubscribeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信关注欢迎语列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,weixinSubscribes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WeixinSubscribeEntity weixinSubscribe,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"微信关注欢迎语");
    	modelMap.put(NormalExcelConstants.CLASS,WeixinSubscribeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信关注欢迎语列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WeixinSubscribeEntity> listWeixinSubscribeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WeixinSubscribeEntity.class,params);
				for (WeixinSubscribeEntity weixinSubscribe : listWeixinSubscribeEntitys) {
					weixinSubscribeService.save(weixinSubscribe);
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
	public List<WeixinSubscribeEntity> list() {
		List<WeixinSubscribeEntity> listWeixinSubscribes=weixinSubscribeService.getList(WeixinSubscribeEntity.class);
		return listWeixinSubscribes;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WeixinSubscribeEntity task = weixinSubscribeService.get(WeixinSubscribeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WeixinSubscribeEntity weixinSubscribe, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinSubscribeEntity>> failures = validator.validate(weixinSubscribe);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinSubscribeService.save(weixinSubscribe);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = weixinSubscribe.getId();
		URI uri = uriBuilder.path("/rest/weixinSubscribeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WeixinSubscribeEntity weixinSubscribe) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinSubscribeEntity>> failures = validator.validate(weixinSubscribe);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinSubscribeService.saveOrUpdate(weixinSubscribe);
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
		weixinSubscribeService.deleteEntityById(WeixinSubscribeEntity.class, id);
	}
}
