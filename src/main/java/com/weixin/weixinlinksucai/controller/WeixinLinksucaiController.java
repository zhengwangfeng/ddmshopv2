package com.weixin.weixinlinksucai.controller;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
import org.jeecgframework.core.util.oConvertUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.weixin.p3.oauth2.rule.RemoteWeixinMethod;
import com.weixin.p3.oauth2.util.SignatureUtil;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinaccount.service.WeixinAccountServiceI;
import com.weixin.weixinlinksucai.entity.WeixinLinksucaiEntity;
import com.weixin.weixinlinksucai.service.WeixinLinksucaiServiceI;

/**   
 * @Title: Controller  
 * @Description: 微信链接素材
 * @author onlineGenerator
 * @date 2017-04-05 15:41:20
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/weixinLinksucaiController")
public class WeixinLinksucaiController extends BaseController {
	/**
	 * 签名密钥key
	 */
	private static final String SIGN_KEY = "4B6CAED6F7B19126F72780372E839CC47B1912B6CAED753F";
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WeixinLinksucaiController.class);

	@Autowired
	private WeixinLinksucaiServiceI weixinLinksucaiService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	@Autowired
	private RemoteWeixinMethod remoteWeixinMethod;


	/**
	 * 微信链接素材列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weixin/weixinlinksucai/weixinLinksucaiList");
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
	public void datagrid(WeixinLinksucaiEntity weixinLinksucai,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WeixinLinksucaiEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinLinksucai, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.weixinLinksucaiService.getDataGridReturn(cq, true);
		String baseurl = ResourceUtil.getConfigByName("domain");
		for(int i=0;i<dataGrid.getResults().size();i++)
		{
			WeixinLinksucaiEntity  t=(WeixinLinksucaiEntity) dataGrid.getResults().get(i);
			
			String inner_link = baseurl+"/weixinLinksucaiController.do?link&id="+t.getId();
			t.setInnerLink(inner_link);
		}
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 链接跳转
	 */
	@RequestMapping(params = "link")
	public void link(WeixinLinksucaiEntity weixinLinksucai,HttpServletRequest request, HttpServletResponse response) {
		//获取请求路径http://1m6309f047.iok.la/shop/weixinLinksucaiController.do?link&id=402881e75b3d12e8015b40e4dbb90003
		String backUrl = this.getRequestUrlWithParams(request);
		//URL配置ID
		String id = request.getParameter("id");
		//URL配置信息
		weixinLinksucai = systemService.getEntity(WeixinLinksucaiEntity.class, id);
		//微信公众账号ID
		String accountid = weixinLinksucai.getAccountid();
		
		//如果连接带参数jwid，则通过jwid原始ID获取公众号信息------------
		//如果带有参数jwid，标示指定公众号，逻辑如下
		String jwid = request.getParameter("jwid");
		if(oConvertUtils.isNotEmpty(jwid)){
			WeixinAccountEntity weixinAccountEntity = weixinAccountService.getWeixinAccountByWeixinOldId(jwid);
			if(weixinAccountEntity!=null){
				accountid = weixinAccountEntity.getId();
			}
		}
		//如果连接带参数jwid，则通过jwid原始ID获取公众号信息--------------
		
		
		//微信用户从SESSION取 Openid
		String openid = ResourceUtil.getUserOpenId();
		//跳转出链接
		String outer_link_deal = null;
		
		//【判断访问地址是否有附加参数，有的话原样带回去】----------------------
	    String requestQueryString = (request.getRequestURL() + "?" + request.getQueryString()).replace(weixinLinksucai.getInnerLink(), "");
	    String outUrl = weixinLinksucai.getOuterLink();
	 /*   if(oConvertUtils.isNotEmpty(requestQueryString)){
	    	outUrl = outUrl + requestQueryString;
	    }*/
	    //【判断访问地址是否有附加参数，有的话原样带回去】----------------------
	    
		//-------------------------------------------------------------------------------------------------------------
		//如果为空则调用author2.0接口
		if(oConvertUtils.isEmpty(openid)){
			 outer_link_deal = remoteWeixinMethod.callWeixinAuthor2ReturnUrl(request, accountid, backUrl);
		}
		//授权后
		if(oConvertUtils.isEmpty(outer_link_deal)){
		    openid = ResourceUtil.getUserOpenId();
		    System.out.println("------------------begin----------begin1-------------------");
			outer_link_deal = weixinLinksucaiService.installOuterLinkWithSysParams(outUrl, openid, accountid,null);
			System.out.println("------------------begin----------begin2-------------------");
		}
		//-------------------------------------------------------------------------------------------------------------
		
		try {
			//-------------参数加签名----------------------------------
			if(outer_link_deal.indexOf("https://open.weixin.qq.com")!=-1){
				//针对调整到auth2.0链接不加签名
				response.sendRedirect(outer_link_deal);
			}else{
				//针对参数加签，防止用户篡改
				String sign = SignatureUtil.sign(SignatureUtil.getSignMap(outer_link_deal), SIGN_KEY);
				response.sendRedirect(outer_link_deal+"&sign="+sign);
			}
			//---------------参数加签名----------------------------------
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除微信链接素材
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WeixinLinksucaiEntity weixinLinksucai, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		weixinLinksucai = systemService.getEntity(WeixinLinksucaiEntity.class, weixinLinksucai.getId());
		message = "微信链接素材删除成功";
		try{
			weixinLinksucaiService.delete(weixinLinksucai);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信链接素材删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除微信链接素材
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信链接素材删除成功";
		try{
			for(String id:ids.split(",")){
				WeixinLinksucaiEntity weixinLinksucai = systemService.getEntity(WeixinLinksucaiEntity.class, 
				id
				);
				weixinLinksucaiService.delete(weixinLinksucai);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "微信链接素材删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加微信链接素材
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WeixinLinksucaiEntity weixinLinksucai, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信链接素材添加成功";
		try{
			List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
			WeixinAccountEntity weixinAccountEntity = weixinAccountEntities.get(0);
			weixinLinksucai.setAccountid(weixinAccountEntity.getId());
			weixinLinksucaiService.save(weixinLinksucai);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信链接素材添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新微信链接素材
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WeixinLinksucaiEntity weixinLinksucai, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信链接素材更新成功";
		WeixinLinksucaiEntity t = weixinLinksucaiService.get(WeixinLinksucaiEntity.class, weixinLinksucai.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(weixinLinksucai, t);
			weixinLinksucaiService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "微信链接素材更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 微信链接素材新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WeixinLinksucaiEntity weixinLinksucai, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinLinksucai.getId())) {
			weixinLinksucai = weixinLinksucaiService.getEntity(WeixinLinksucaiEntity.class, weixinLinksucai.getId());
			req.setAttribute("weixinLinksucaiPage", weixinLinksucai);
		}
		return new ModelAndView("com/weixin/weixinlinksucai/weixinLinksucai-add");
	}
	/**
	 * 微信链接素材编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WeixinLinksucaiEntity weixinLinksucai, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinLinksucai.getId())) {
			weixinLinksucai = weixinLinksucaiService.getEntity(WeixinLinksucaiEntity.class, weixinLinksucai.getId());
			req.setAttribute("weixinLinksucaiPage", weixinLinksucai);
		}
		return new ModelAndView("com/weixin/weixinlinksucai/weixinLinksucai-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","weixinLinksucaiController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WeixinLinksucaiEntity weixinLinksucai,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WeixinLinksucaiEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinLinksucai, request.getParameterMap());
		List<WeixinLinksucaiEntity> weixinLinksucais = this.weixinLinksucaiService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"微信链接素材");
		modelMap.put(NormalExcelConstants.CLASS,WeixinLinksucaiEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信链接素材列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,weixinLinksucais);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WeixinLinksucaiEntity weixinLinksucai,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"微信链接素材");
    	modelMap.put(NormalExcelConstants.CLASS,WeixinLinksucaiEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信链接素材列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WeixinLinksucaiEntity> listWeixinLinksucaiEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WeixinLinksucaiEntity.class,params);
				for (WeixinLinksucaiEntity weixinLinksucai : listWeixinLinksucaiEntitys) {
					weixinLinksucaiService.save(weixinLinksucai);
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
	public List<WeixinLinksucaiEntity> list() {
		List<WeixinLinksucaiEntity> listWeixinLinksucais=weixinLinksucaiService.getList(WeixinLinksucaiEntity.class);
		return listWeixinLinksucais;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WeixinLinksucaiEntity task = weixinLinksucaiService.get(WeixinLinksucaiEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WeixinLinksucaiEntity weixinLinksucai, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinLinksucaiEntity>> failures = validator.validate(weixinLinksucai);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinLinksucaiService.save(weixinLinksucai);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = weixinLinksucai.getId();
		URI uri = uriBuilder.path("/rest/weixinLinksucaiController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WeixinLinksucaiEntity weixinLinksucai) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinLinksucaiEntity>> failures = validator.validate(weixinLinksucai);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinLinksucaiService.saveOrUpdate(weixinLinksucai);
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
		weixinLinksucaiService.deleteEntityById(WeixinLinksucaiEntity.class, id);
	}
	
	@RequestMapping(params = "poplink")
	public ModelAndView poplink(ModelMap modelMap,
                                    @RequestParam String id) 
	{
		//WeixinLinksucaiEntity weixinLinksucai = weixinLinksucaiService.getEntity(WeixinLinksucaiEntity.class, id);
		
		ResourceBundle bundler = ResourceBundle.getBundle("sysConfig");
		String absolutePathUrl =  bundler.getString("domain")  + "/weixinLinksucaiController.do?link&id=" + id;
        modelMap.put("url",absolutePathUrl);
		return new ModelAndView("com/weixin/weixinlinksucai/poplinksucai");
	}

	/**
     * 获取Request请求的路径信息 带参数
     * @param request
     * @return
     */
    private static String getRequestUrlWithParams(HttpServletRequest request){
  	  String backurl = request.getScheme()+"://"+request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
  	  return backurl;
    }
}
