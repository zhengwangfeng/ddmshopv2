package com.tcsb.desk.controller;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.QRCodeUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.p3.core.util.plugin.ContextHolderUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.demo.entity.test.JeecgDemo;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
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

import com.google.zxing.BarcodeFormat;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.desk.service.TcsbDeskServiceI;
import com.tcsb.desktype.entity.TcsbDeskTypeEntity;
import com.tcsb.desktype.service.TcsbDeskTypeServiceI;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.util.WeixinUtil;
import com.weixin.p3.oauth2.util.OAuth2Util;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinaccount.service.WeixinAccountServiceI;

/**   
 * @Title: Controller  
 * @Description: 桌位管理
 * @author onlineGenerator
 * @date 2017-03-23 10:29:48
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbDeskController")
public class TcsbDeskController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbDeskController.class);

	@Autowired
	private TcsbDeskServiceI tcsbDeskService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbDeskTypeServiceI tcsbDeskTypeService;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;

	/**
	 * 桌位管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		TSUser user = getCurrentUser();
		List<TcsbDeskTypeEntity> tcsbDeskTypeEntities =null;
		String deskTypeReplace = "";
		if (!checkAdmin()) {
			tcsbDeskTypeEntities = tcsbDeskTypeService.findByProperty(TcsbDeskTypeEntity.class, "shopId", user.getShopId());
		}else {
			tcsbDeskTypeEntities = tcsbDeskTypeService.getList(TcsbDeskTypeEntity.class);
		}
		for (TcsbDeskTypeEntity tcsbDeskTypeEntity : tcsbDeskTypeEntities) {
			if (deskTypeReplace.length() > 0) {
				deskTypeReplace += ",";
			}
			deskTypeReplace += tcsbDeskTypeEntity.getName() + "_" + tcsbDeskTypeEntity.getId();
		}
		
		request.setAttribute("deskTypeReplace", deskTypeReplace);
		return new ModelAndView("com/tcsb/desk/tcsbDeskList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbDeskEntity tcsbDesk,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDeskEntity.class, dataGrid);
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			cq.eq("shopId", user.getShopId());
		}
		
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDesk, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbDeskService.getDataGridReturn(cq, true);
		// 图片展示路径
		List<TcsbDeskEntity> resultList = dataGrid.getResults();
		for (TcsbDeskEntity one : resultList) {
			String img = one.getQrcode();
			String appletImg = one.getAppletQrcode();
			if (StringUtil.isNotEmpty(img)) {
				one.setQrcode(ResourceUtil.getConfigByName("qrcode")+img);
			}
			if (StringUtil.isNotEmpty(appletImg)) {
				one.setAppletQrcode(ResourceUtil.getConfigByName("applet.qrcode")+appletImg);
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除桌位管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbDeskEntity tcsbDesk, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbDesk = systemService.getEntity(TcsbDeskEntity.class, tcsbDesk.getId());
		message = "桌位管理删除成功";
		try{
			tcsbDeskService.delete(tcsbDesk);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除桌位管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位管理删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbDeskEntity tcsbDesk = systemService.getEntity(TcsbDeskEntity.class, 
				id
				);
				tcsbDeskService.delete(tcsbDesk);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加桌位管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbDeskEntity tcsbDesk, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位管理添加成功";
		try{
			TSUser user = getCurrentUser();
			tcsbDesk.setShopId(user.getShopId());
			//随机生成UUID
			String number = UUIDGenerator.generate();
			tcsbDesk.setNumber(number);
			tcsbDesk.setStatus("0");
			//二维码存放路径
			String toPath = "/qrcode/user"+user.getUserName()+"/number"+number+DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyyMMdd"));
			String filePostfix = "png";
			//D:\Tomcat 7.0\webapps\shop\
			String tempString = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
			File file = new File(ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/")+toPath+"."+filePostfix);
			if (!file.exists()) {
				file.mkdirs();
			}
			String deskBaseUrl = ResourceBundle.getBundle("sysConfig").getString("deskBaseUrl");
			//String tagetUrl = "http://www.xmxxit.net/shop/wechatAuthor2Controller.do?wechatAuthor2&no="+tcsbDesk.getNumber()+"&deskName="+tcsbDesk.getDeskName()+"&shopId="+tcsbDesk.getShopId();
			//String tagetUrl = "http://1m6309f047.iok.la/shop/wechatAuthor2Controller.do?wechatAuthor2&no="+tcsbDesk.getNumber()+"&deskName="+tcsbDesk.getDeskName()+"&shopId="+tcsbDesk.getShopId();//前端页面
			String tagetUrl = deskBaseUrl+"/shop/wechatAuthor2Controller.do?wechatAuthor2&no="+tcsbDesk.getNumber()+"&deskName="+tcsbDesk.getDeskName()+"&shopId="+tcsbDesk.getShopId();
			List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
			WeixinAccountEntity weixinAccountEntity = weixinAccountEntities.get(0);
			String redirectURL = OAuth2Util.obtainWeixinOAuth2Url(tagetUrl,  weixinAccountEntity.getAccountappid(),OAuth2Util.SNSAPI_USERINFO);
			QRCodeUtil.encode(redirectURL, file, filePostfix, BarcodeFormat.QR_CODE, 500, 500, null);
			tcsbDesk.setQrcode(toPath+"."+filePostfix);
			//上面为微信授权登入的二维码已停用
			tcsbDeskService.save(tcsbDesk);
			tcsbDesk.setAppletQrcode(getWeixinAppletQrcode(tcsbDesk,user));
			systemService.saveOrUpdate(tcsbDesk);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "桌位管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	private  String getWeixinAppletQrcode(TcsbDeskEntity tcsbDesk,TSUser user) throws Exception{
		if(tcsbDesk == null){
			throw new BusinessException("");
		}
		String filePostfix = "png";
		//下面为小程序二维码
		//小程序二维码存放路径
		String APPLETtoPath = "/appletqrcode/user_"+user.getUserName();
		//File appletFile = new File(ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/")+toPath+"."+filePostfix);
		//获取accessToken
		AccessToken accessToken = WeixinUtil.getAppletAccessToken();
		String appletPath = WeixinUtil.httpPostWithJSON(accessToken.getToken(), tcsbDesk.getId(), "pages/index/index", APPLETtoPath, filePostfix);

		return appletPath;
	}
	
	/**
	 * 更新桌位管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbDeskEntity tcsbDesk, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "桌位管理更新成功";
		TcsbDeskEntity t = tcsbDeskService.get(TcsbDeskEntity.class, tcsbDesk.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbDesk, t);
			//t.setAppletQrcode(getWeixinQrcode(tcsbDesk,getCurrentUser()));
			tcsbDeskService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "桌位管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 桌位管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbDeskEntity tcsbDesk, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDesk.getId())) {
			tcsbDesk = tcsbDeskService.getEntity(TcsbDeskEntity.class, tcsbDesk.getId());
			req.setAttribute("tcsbDeskPage", tcsbDesk);
		}
		//查找店铺桌位类型
		TSUser user = getCurrentUser();
		List<TcsbDeskTypeEntity> tcsbDeskTypeEntities = tcsbDeskTypeService.findByProperty(TcsbDeskTypeEntity.class, "shopId", user.getShopId());
		req.setAttribute("tcsbDeskTypeEntities", tcsbDeskTypeEntities);
		return new ModelAndView("com/tcsb/desk/tcsbDesk-add");
	}
	/**
	 * 桌位管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbDeskEntity tcsbDesk, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbDesk.getId())) {
			tcsbDesk = tcsbDeskService.getEntity(TcsbDeskEntity.class, tcsbDesk.getId());
			/*TcsbDeskEntity tcsbDeskEntity = new TcsbDeskEntity();
			if (!StringUtil.isEmpty(tcsbDesk.getQrcode())) {
				try {
					MyBeanUtils.copyBeanNotNull2Bean(tcsbDesk, tcsbDeskEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tcsbDeskEntity.setQrcode(ResourceUtil.getConfigByName("qrcode")+tcsbDesk.getQrcode());
			}*/
			req.setAttribute("tcsbDeskPage", tcsbDesk);
		}
		//查找店铺桌位类型
		TSUser user = getCurrentUser();
		List<TcsbDeskTypeEntity> tcsbDeskTypeEntities = new ArrayList<TcsbDeskTypeEntity>();
		if (checkAdmin()) {
			tcsbDeskTypeEntities = tcsbDeskTypeService.getList(TcsbDeskTypeEntity.class);
		}else {
			tcsbDeskTypeEntities = tcsbDeskTypeService.findByProperty(TcsbDeskTypeEntity.class, "shopId", user.getShopId());
		}
		req.setAttribute("tcsbDeskTypeEntities", tcsbDeskTypeEntities);
		return new ModelAndView("com/tcsb/desk/tcsbDesk-update");
	}
	
	/**
	 * 桌位打印预览跳转
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "print")
	public ModelAndView print(TcsbDeskEntity tcsbDesk, HttpServletRequest req) {
		// 获取部门信息
		/*List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);*/
		if (StringUtil.isNotEmpty(tcsbDesk.getId())) {
			tcsbDesk = tcsbDeskService.getEntity(TcsbDeskEntity.class, tcsbDesk.getId());
			req.setAttribute("tcsbDeskPage", tcsbDesk);
		}
		//查找店铺桌位类型
		TSUser user = getCurrentUser();
		List<TcsbDeskTypeEntity> tcsbDeskTypeEntities = new ArrayList<TcsbDeskTypeEntity>();
		if (checkAdmin()) {
			tcsbDeskTypeEntities = tcsbDeskTypeService.getList(TcsbDeskTypeEntity.class);
		}else {
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
			tcsbDeskTypeEntities = tcsbDeskTypeService.findByProperty(TcsbDeskTypeEntity.class, "shopId", tcsbShopEntity.getId());
		}
		req.setAttribute("tcsbDeskTypeEntities", tcsbDeskTypeEntities);
		return new ModelAndView("com/tcsb/desk/tcsbDeskPrint");
	}



	@RequestMapping(params = "apletQrcode")
	public void apletQrcode() throws Exception{
		List<TSUser> users = systemService.loadAll(TSUser.class);
		Map<String,TSUser> map = new HashMap<String,TSUser>();
		for(TSUser user : users){
			if(!map.containsKey(user.getShopId()) && user.getShopId()!=null){
				map.put(user.getShopId(),user);
			}
		}
		if(users.size()>0){
			List<TcsbDeskEntity>  list = systemService.loadAll(TcsbDeskEntity.class);
			for(TcsbDeskEntity entity : list){
				if(entity.getShopId()==null || !map.containsKey(entity.getShopId())){
					continue;
				}

				if(entity.getShopId()!=null && map.containsKey(entity.getShopId())){
					TSUser user= map.get(entity.getShopId());
					entity.setAppletQrcode(getWeixinAppletQrcode(entity,user));
					tcsbDeskService.doApletQrcode(entity);
				}
			}
		}
	}





	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbDeskController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbDeskEntity tcsbDesk,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbDeskEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbDesk, request.getParameterMap());
		List<TcsbDeskEntity> tcsbDesks = this.tcsbDeskService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"桌位管理");
		modelMap.put(NormalExcelConstants.CLASS,TcsbDeskEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("桌位管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbDesks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbDeskEntity tcsbDesk,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"桌位管理");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbDeskEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("桌位管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbDeskEntity> listTcsbDeskEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbDeskEntity.class,params);
				for (TcsbDeskEntity tcsbDesk : listTcsbDeskEntitys) {
					tcsbDeskService.save(tcsbDesk);
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
	public List<TcsbDeskEntity> list() {
		List<TcsbDeskEntity> listTcsbDesks=tcsbDeskService.getList(TcsbDeskEntity.class);
		return listTcsbDesks;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbDeskEntity task = tcsbDeskService.get(TcsbDeskEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbDeskEntity tcsbDesk, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDeskEntity>> failures = validator.validate(tcsbDesk);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDeskService.save(tcsbDesk);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbDesk.getId();
		URI uri = uriBuilder.path("/rest/tcsbDeskController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbDeskEntity tcsbDesk) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbDeskEntity>> failures = validator.validate(tcsbDesk);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbDeskService.saveOrUpdate(tcsbDesk);
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
		tcsbDeskService.deleteEntityById(TcsbDeskEntity.class, id);
	}
	
}
