package com.tcsb.shopcollect.controller;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopcollect.entity.TcsbShopCollectEntity;
import com.tcsb.shopcollect.service.TcsbShopCollectServiceI;
import com.tcsb.shopcollect.vo.TcsbShopCollectEntityVo;
import com.weixin.weixinuser.entity.WeixinUserEntity;

/**   
 * @Title: Controller  
 * @Description: 店铺收藏
 * @author onlineGenerator
 * @date 2017-05-02 20:59:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopCollectController")
public class TcsbShopCollectController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopCollectController.class);

	@Autowired
	private TcsbShopCollectServiceI tcsbShopCollectService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	


	/**
	 * 店铺收藏列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shopcollect/tcsbShopCollectList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbShopCollectEntity tcsbShopCollect,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopCollectEntity.class, dataGrid);
		TSUser user = getCurrentUser();
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopCollect, request.getParameterMap());
		try{
			
		//自定义追加查询条件
			if(!checkAdmin()) {
				// 查询商家店铺
				cq.eq("shopId", user.getShopId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopCollectService.getDataGridReturn(cq, true);
		List<TcsbShopCollectEntity> tcsbShopCollectEntityList = dataGrid.getResults();
		List<TcsbShopCollectEntityVo> tcsbShopCollectEntityVoList = new ArrayList<>();
		TcsbShopCollectEntityVo tcsbShopCollectEntityVo;
		
		for (TcsbShopCollectEntity tcsbShopCollectEntity : tcsbShopCollectEntityList) {
			tcsbShopCollectEntityVo = new TcsbShopCollectEntityVo();
			BeanUtils.copyProperties(tcsbShopCollectEntityVo, tcsbShopCollectEntity);
			tcsbShopCollectEntityVoList.add(tcsbShopCollectEntityVo);
		}
		int i=0;
		for (TcsbShopCollectEntityVo tcsbShopCollectVo : tcsbShopCollectEntityVoList) {
			TcsbShopEntity tcsbShopEntity= tcsbShopCollectService.get(TcsbShopEntity.class, tcsbShopCollectVo.getShopId());
			if(StringUtil.isNotEmpty(tcsbShopEntity)){
				tcsbShopCollectEntityVoList.get(i).setShopName(tcsbShopEntity.getName());
			}
			
			String userhql ="from WeixinUserEntity where openid='"+tcsbShopCollectVo.getUserId()+"' and shopId='"+tcsbShopCollectVo.getShopId()+"'";
			WeixinUserEntity weixinUserEntity = tcsbShopCollectService.singleResult(userhql);
			if(StringUtil.isNotEmpty(weixinUserEntity)){
				//tcsbShopCollectEntityVoList.get(i).setUserName(weixinUserEntity.getNickname());
			}
			i++;
		}
		
		dataGrid.setResults(tcsbShopCollectEntityVoList);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除店铺收藏
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopCollectEntity tcsbShopCollect, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShopCollect = systemService.getEntity(TcsbShopCollectEntity.class, tcsbShopCollect.getId());
		message = "店铺收藏删除成功";
		try{
			tcsbShopCollectService.delete(tcsbShopCollect);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺收藏删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除店铺收藏
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺收藏删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopCollectEntity tcsbShopCollect = systemService.getEntity(TcsbShopCollectEntity.class, 
				id
				);
				tcsbShopCollectService.delete(tcsbShopCollect);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺收藏删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加店铺收藏
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopCollectEntity tcsbShopCollect, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺收藏添加成功";
		try{
			tcsbShopCollectService.save(tcsbShopCollect);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "店铺收藏添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新店铺收藏
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopCollectEntity tcsbShopCollect, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺收藏更新成功";
		TcsbShopCollectEntity t = tcsbShopCollectService.get(TcsbShopCollectEntity.class, tcsbShopCollect.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopCollect, t);
			tcsbShopCollectService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺收藏更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺收藏新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopCollectEntity tcsbShopCollect, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopCollect.getId())) {
			tcsbShopCollect = tcsbShopCollectService.getEntity(TcsbShopCollectEntity.class, tcsbShopCollect.getId());
			req.setAttribute("tcsbShopCollectPage", tcsbShopCollect);
		}
		return new ModelAndView("com/tcsb/shopcollect/tcsbShopCollect-add");
	}
	/**
	 * 店铺收藏编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopCollectEntity tcsbShopCollect, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopCollect.getId())) {
			tcsbShopCollect = tcsbShopCollectService.getEntity(TcsbShopCollectEntity.class, tcsbShopCollect.getId());
			req.setAttribute("tcsbShopCollectPage", tcsbShopCollect);
		}
		return new ModelAndView("com/tcsb/shopcollect/tcsbShopCollect-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopCollectController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopCollectEntity tcsbShopCollect,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopCollectEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopCollect, request.getParameterMap());
		List<TcsbShopCollectEntity> tcsbShopCollects = this.tcsbShopCollectService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺收藏");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopCollectEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺收藏列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopCollects);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopCollectEntity tcsbShopCollect,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"店铺收藏");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopCollectEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺收藏列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopCollectEntity> listTcsbShopCollectEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopCollectEntity.class,params);
				for (TcsbShopCollectEntity tcsbShopCollect : listTcsbShopCollectEntitys) {
					tcsbShopCollectService.save(tcsbShopCollect);
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
	public List<TcsbShopCollectEntity> list() {
		List<TcsbShopCollectEntity> listTcsbShopCollects=tcsbShopCollectService.getList(TcsbShopCollectEntity.class);
		return listTcsbShopCollects;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopCollectEntity task = tcsbShopCollectService.get(TcsbShopCollectEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopCollectEntity tcsbShopCollect, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopCollectEntity>> failures = validator.validate(tcsbShopCollect);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopCollectService.save(tcsbShopCollect);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopCollect.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopCollectController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopCollectEntity tcsbShopCollect) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopCollectEntity>> failures = validator.validate(tcsbShopCollect);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopCollectService.saveOrUpdate(tcsbShopCollect);
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
		tcsbShopCollectService.deleteEntityById(TcsbShopCollectEntity.class, id);
	}
	
	/**
	 * 添加我的收藏
	 * @param userId
	 * @param shopId
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addShopCollection",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject addShopCollection(@RequestParam String userId,@RequestParam String shopId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		try {
			tcsbShopCollectService.addShopCollection(userId,shopId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.ADDSUCCESS.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	/**
	 * 获取我的收藏
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMyShopCollection",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getMyShopCollection(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		List<Map<String, Object>> maps= new ArrayList<>();
		try {
			maps = tcsbShopCollectService.findForJdbc("select s.id as shopId,s.name,s.headImg as shopImg,c.id from tcsb_shop_collect c left join tcsb_shop s on c.shop_id = s.id where c.user_id =? and c.is_del = 0", userId);
			for (Map<String, Object> map : maps) {
				map.put("shopImg", getCkPath()+map.get("shopImg"));
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
	}
	
	/**
	 * 取消我的收藏
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/cancelMyShopCollection",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject cancelMyShopCollection(@RequestParam String id,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		try {
			TcsbShopCollectEntity tcsbShopCollectEntity = tcsbShopCollectService.get(TcsbShopCollectEntity.class, id);
			tcsbShopCollectEntity.setIsDel("1");
			tcsbShopCollectService.saveOrUpdate(tcsbShopCollectEntity);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
}
