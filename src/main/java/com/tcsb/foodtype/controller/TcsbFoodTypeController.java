package com.tcsb.foodtype.controller;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.hibernate.criterion.Restrictions;
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
import org.jeecgframework.core.util.PageBean;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.foodtype.service.TcsbFoodTypeServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;

/**   
 * @Title: Controller  
 * @Description: 食物分类
 * @author onlineGenerator
 * @date 2017-02-28 16:24:27
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbFoodTypeController")
public class TcsbFoodTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbFoodTypeController.class);

	@Autowired
	private TcsbFoodTypeServiceI tcsbFoodTypeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbFoodServiceI tcsbFoodService;

	/**
	 * 食物分类列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String shopsReplace = "";
		List<TcsbShopEntity> tcsbShopEntities = systemService.getList(TcsbShopEntity.class);
		for (TcsbShopEntity tcsbShopEntity : tcsbShopEntities) {
			if (shopsReplace.length() > 0) {
				shopsReplace += ",";
			}
			shopsReplace += tcsbShopEntity.getName() + "_" + tcsbShopEntity.getId();
		}
		//如果是admin用户展示店铺选择
		TSUser tsUser = getCurrentUser();
		if (tsUser.getUserName().equals("admin")) {
			request.setAttribute("isAdmin", true);
		}
		request.setAttribute("shopsReplace", shopsReplace);
		return new ModelAndView("com/tcsb/foodtype/tcsbFoodTypeList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbFoodTypeEntity tcsbFoodType,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodTypeEntity.class, dataGrid);
		
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodType, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user=getCurrentUser();
			if (!checkAdmin()) {
				cq.add(Restrictions.eq("shopId", user.getShopId()));
				cq.add(Restrictions.eq("isDel", 0));
			}
			cq.addOrder("orders",SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbFoodTypeService.getDataGridReturn(cq, true);
		//图片展示路径
		List<TcsbFoodTypeEntity> resultList = dataGrid.getResults();
		if(StringUtil.isNotEmpty(resultList) && resultList.size()>0){
			for(TcsbFoodTypeEntity one : resultList){
				String typeimg = one.getTypeImg();
				System.out.println(typeimg);
				if(StringUtil.isNotEmpty(typeimg)){
					if(typeimg.equals("null")){
						String files = getCkPath();
						one.setTypeImg(files + "/images/none.jpg");
					}else{
						String files = getCkPath();
						one.setTypeImg(files + typeimg);
					}
				}else{
					String files = getCkPath();
					one.setTypeImg(files + "/images/none.jpg");
				}
			}
			
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除食物分类
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbFoodTypeEntity tcsbFoodType, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbFoodType = systemService.getEntity(TcsbFoodTypeEntity.class, tcsbFoodType.getId());
		message = "食物分类删除成功";
		try{
			List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where isdelete = ? and foodTypeId = ?", 0,tcsbFoodType.getId());
			if (tcsbFoodEntities.isEmpty()) {
				tcsbFoodType.setIsDel(1);
				tcsbFoodTypeService.saveOrUpdate(tcsbFoodType);
			}else {
				message = "该分类下有菜品无法进行删除";
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "食物分类删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除食物分类
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物分类删除成功";
		try{
			boolean canDel = true;
			for(String id:ids.split(",")){
				List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where foodTypeId = ?", id);
				if (!tcsbFoodEntities.isEmpty()) {
					canDel = false;
					break;
				}
			}
			if (canDel) {
				for(String id:ids.split(",")){
					TcsbFoodTypeEntity tcsbFoodType = systemService.getEntity(TcsbFoodTypeEntity.class, id);
					tcsbFoodTypeService.delete(tcsbFoodType);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}else {
				message = "某分类下有菜品无法进行删除";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "食物分类删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加食物分类
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbFoodTypeEntity tcsbFoodType, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物分类添加成功";
		try{
			TSUser user=getCurrentUser();
			//查询商家店铺
			tcsbFoodType.setShopId(user.getShopId());
			tcsbFoodType.setIsDel(0);
			//图片过滤路径
			tcsbFoodType.setTypeImg(filterCkPath(tcsbFoodType.getTypeImg()));
			//同时保存快照给PC端同步用
			tcsbFoodTypeService.save(tcsbFoodType);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "食物分类添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新食物分类
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbFoodTypeEntity tcsbFoodType, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "食物分类更新成功";
		TcsbFoodTypeEntity t = tcsbFoodTypeService.get(TcsbFoodTypeEntity.class, tcsbFoodType.getId());
		//设置图片过滤路径
		if (!StringUtil.isEmpty(tcsbFoodType.getTypeImg())) {
			tcsbFoodType.setTypeImg(filterCkPath(tcsbFoodType.getTypeImg()));
		}
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFoodType, t);
			//同时保存快照给PC端同步用
			tcsbFoodTypeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "食物分类更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 食物分类新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbFoodTypeEntity tcsbFoodType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodType.getId())) {
			tcsbFoodType = tcsbFoodTypeService.getEntity(TcsbFoodTypeEntity.class, tcsbFoodType.getId());
			req.setAttribute("tcsbFoodTypePage", tcsbFoodType);
		}
		return new ModelAndView("com/tcsb/foodtype/tcsbFoodType-add");
	}
	/**
	 * 食物分类编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbFoodTypeEntity tcsbFoodType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodType.getId())) {
			tcsbFoodType = tcsbFoodTypeService.getEntity(TcsbFoodTypeEntity.class, tcsbFoodType.getId());
			// 替换原先的图片路径s
			if (!StringUtil.isEmpty(tcsbFoodType.getTypeImg())) {
				//奇怪的问题清除SESSION一级缓存
				TcsbFoodTypeEntity tcsbFoodTypePage = new TcsbFoodTypeEntity();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(tcsbFoodType, tcsbFoodTypePage);
					tcsbFoodTypePage.setTypeImg(getCkPath()+tcsbFoodType.getTypeImg());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				req.setAttribute("tcsbFoodTypePage", tcsbFoodTypePage);
			}else {
				req.setAttribute("tcsbFoodTypePage", tcsbFoodType);
			}
		}
		return new ModelAndView("com/tcsb/foodtype/tcsbFoodType-update");
	}
	
	/**
	 * 获得食品分类
	 *
	 * @return
	 */
	@RequestMapping(params = "getFoodType")
	@ResponseBody
	public AjaxJson getFoodType(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "获取食品分类成功";
		List<TcsbFoodTypeEntity> tcsbFoodTypeEntities = new ArrayList<>();
		try{
			TSUser user=getCurrentUser();
			//查询商家店铺
			TcsbShopEntity tcsbShopEntity = (TcsbShopEntity) tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
			tcsbFoodTypeEntities = tcsbFoodTypeService.findHql("from TcsbFoodTypeEntity where shopId = ?", tcsbShopEntity.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "获取食品分类失败";
			throw new BusinessException(e.getMessage());
		}
		j.setObj(tcsbFoodTypeEntities);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbFoodTypeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbFoodTypeEntity tcsbFoodType,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodTypeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodType, request.getParameterMap());
		List<TcsbFoodTypeEntity> tcsbFoodTypes = this.tcsbFoodTypeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"食物分类");
		modelMap.put(NormalExcelConstants.CLASS,TcsbFoodTypeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("食物分类列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbFoodTypes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbFoodTypeEntity tcsbFoodType,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"食物分类");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbFoodTypeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("食物分类列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbFoodTypeEntity> listTcsbFoodTypeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbFoodTypeEntity.class,params);
				for (TcsbFoodTypeEntity tcsbFoodType : listTcsbFoodTypeEntitys) {
					tcsbFoodTypeService.save(tcsbFoodType);
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
	public List<TcsbFoodTypeEntity> list() {
		List<TcsbFoodTypeEntity> listTcsbFoodTypes=tcsbFoodTypeService.getList(TcsbFoodTypeEntity.class);
		return listTcsbFoodTypes;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbFoodTypeEntity task = tcsbFoodTypeService.get(TcsbFoodTypeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbFoodTypeEntity tcsbFoodType, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodTypeEntity>> failures = validator.validate(tcsbFoodType);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodTypeService.save(tcsbFoodType);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbFoodType.getId();
		URI uri = uriBuilder.path("/rest/tcsbFoodTypeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbFoodTypeEntity tcsbFoodType) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodTypeEntity>> failures = validator.validate(tcsbFoodType);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodTypeService.saveOrUpdate(tcsbFoodType);
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
		tcsbFoodTypeService.deleteEntityById(TcsbFoodTypeEntity.class, id);
	}
	
	@RequestMapping(value = "/listByShopId1",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AjaxJsonApi listByShopId1(@RequestParam String shopId,@RequestParam Integer page,Integer pageSize,HttpServletRequest request,HttpServletResponse response){
		//List<Map<String,Object>> listTcsbFoodTypes=tcsbFoodTypeService.findForJdbc("select id,name,type_img as typeImg from tcsb_food_type where shop_id = ?", shopId);
		if (pageSize==null) {
			pageSize=5;
		}
		String sql = "select id,name,type_img as typeImg from tcsb_food_type where shop_id = ?";
		String countSql = "select count(1) from tcsb_food_type where shop_id = ?";
		List<Map<String, Object>> listTcsbFoodTypes = tcsbFoodTypeService.findForJdbcParam(sql, page, pageSize, shopId); 
		Long totalCount = tcsbFoodTypeService.getCountForJdbcParam(countSql, new Object[]{shopId});
		//创建PAGEBEAN
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		pageBean.setTotalCount(Integer.parseInt(String.valueOf(totalCount)));
		pageBean.setList(listTcsbFoodTypes);
		pageBean.setPage(page);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalPage(pageSize, Integer.parseInt(String.valueOf(totalCount)));
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		if (listTcsbFoodTypes.isEmpty()) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			ajaxJson.setMsg("没有该商家");
			ajaxJson.setSuccess(false);
			return ajaxJson;
		}
		ajaxJson.setMsg("查询成功");
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(pageBean);
		return ajaxJson;
	}
	@RequestMapping(value = "/listByShopId",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject listByShopId(@RequestParam String shopId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		List<Map<String,Object>> listTcsbFoodTypes=tcsbFoodTypeService.findForJdbc("select id,name,type_img as typeImg from tcsb_food_type where shop_id = ?", shopId);
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		if (listTcsbFoodTypes.isEmpty()) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			ajaxJsonApi.setMsg(ReturnMessageEnum.SHOPNOTEXIT.getMsg());
			ajaxJsonApi.setSuccess(false);
			return new JSONPObject(callbackFunName, ajaxJsonApi); 
			
		}
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		//替换图片路径
		for (int i = 0; i < listTcsbFoodTypes.size(); i++) {
			Map<String,Object> map = listTcsbFoodTypes.get(i);
			listTcsbFoodTypes.get(i).put("typeImg", getCkPath()+map.get("typeImg"));
		}
		ajaxJsonApi.setObj(listTcsbFoodTypes);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	@RequestMapping(value = "/listByShopId2",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AjaxJsonApi listByShopId2(@RequestParam String shopId,HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> listTcsbFoodTypes=tcsbFoodTypeService.findForJdbc("select id,name,type_img as typeImg from tcsb_food_type where shop_id = ?", shopId);
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		//替换图片路径
		for (int i = 0; i < listTcsbFoodTypes.size(); i++) {
			Map<String,Object> map = listTcsbFoodTypes.get(i);
			listTcsbFoodTypes.get(i).put("typeImg", getCkPath()+map.get("typeImg"));
		}
		ajaxJsonApi.setObj(listTcsbFoodTypes);
		return ajaxJsonApi;
	}
	
	@RequestMapping(value="/getpic", method = RequestMethod.GET)  
	public void getpic(Model model,HttpServletRequest request, HttpServletResponse response) throws IOException {  
		 String result = null;
         // 将JSON进行UTF-8编码,以便传输中文
//         String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
//         DefaultHttpClient httpClient = new DefaultHttpClient();
//         HttpPost httpPost = new HttpPost(url);
//         httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
//         StringEntity se = new StringEntity(json);
//         se.setContentType(CONTENT_TYPE_TEXT_JSON);
//         se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
//                         APPLICATION_JSON));
//         httpPost.setEntity(se);
//         // httpClient.execute(httpPost);
//         HttpResponse response = httpClient.execute(httpPost);
//         if (response != null) {   
//                 HttpEntity resEntity = response.getEntity();
//                 if (resEntity != null) {
//                         InputStream instreams = resEntity.getContent(); 
//                         saveToImgByInputStream(instreams, "D:\\", "apr.jpg");
//                 }
//         }
	    //response.setContentType("image/jpeg"); // 设置返回内容格式  
//	    File file = new File("d:/1.jpg");       //括号里参数为文件图片路径  
//	    if(file.exists()){   //如果文件存在  
//	        FileInputStream in = new FileInputStream("d:/1.jpg");   //用该文件创建一个输入流  
//	        OutputStream os = response.getOutputStream();  //创建输出流  
//	        byte[] b = new byte[1024];    
//	        while( in.read(b)!= -1){   
//	        os.write(b);       
//	        }  
//	        in.close();   
//	        os.flush();  
//	        os.close();  
	    }  
		
}
