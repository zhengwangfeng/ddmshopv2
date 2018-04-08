package com.tcsb.shop.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
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

import ch.qos.logback.core.joran.conditional.ElseAction;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.discountactivity.service.TcsbDiscountActivityServiceI;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.fullcuttemplate.service.TcsbFullcutTemplateServiceI;
import com.tcsb.imagesmanager.entity.TcsbImagesManagerEntity;
import com.tcsb.imagesmanager.service.TcsbImagesManagerServiceI;
import com.tcsb.platformdiscount.service.TcsbPlatformDiscountServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.page.TcsbShopPage;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopfullcuttemplate.service.TcsbShopFullcutTemplateServiceI;
import com.tcsb.tcsbshoppaymode.entity.TcsbShopPaymodeEntity;
import com.tcsb.tcsbshoppaymode.service.TcsbShopPaymodeServiceI;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.util.WeixinUtil;

/**
 * @Title: Controller
 * @Description: 店铺管理
 * @author onlineGenerator
 * @date 2017-02-28 11:06:59
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/tcsbShopController")
public class TcsbShopController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(TcsbShopController.class);

	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbDiscountActivityServiceI tcsbDiscountActivityService;
	@Autowired
	private TcsbPlatformDiscountServiceI tcsbPlatformDiscountService;
	@Autowired
	private TcsbFullcutTemplateServiceI tcsbFullcutTemplateService;
	@Autowired
	private TcsbImagesManagerServiceI tcsbImagesManagerService;
	@Autowired
	private TcsbShopFullcutTemplateServiceI tcsbShopFullcutTemplateService;
	@Autowired
	private TcsbShopPaymodeServiceI tcsbShopPaymodeService;
	/**
	 * 店铺管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/shop/tcsbShopList");
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
	public void datagrid(TcsbShopEntity tcsbShop, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopEntity.class, dataGrid);
		TSUser user = getCurrentUser();
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				tcsbShop, request.getParameterMap());
		try {
			if (!checkAdmin()) {
				//仅限更改为
				cq.add(Restrictions.eq("id", user.getShopId()));
				//cq.eq("userId", user.getId());
			}
			
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbShopService.getDataGridReturn(cq, true);
		// 图片展示路径
		List<TcsbShopEntity> resultList = dataGrid.getResults();
		List<TcsbShopEntity> resultListResult = new ArrayList<>(); 
		if (StringUtil.isNotEmpty(resultList) && resultList.size() > 0) {
			for (TcsbShopEntity one : resultList) {
				TcsbShopEntity tcsbShopEntity = new TcsbShopEntity();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(one, tcsbShopEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String headimg = one.getHeadimg();
				String appletQrcode = one .getAppletQrcode();
				if (StringUtil.isNotEmpty(headimg)) {
					String files = getCkPath();
					tcsbShopEntity.setHeadimg(files + headimg);
					//替换为
//					tcsbShopEntity.setHeadimg(one.getHeadimg());
				}
				if (StringUtil.isNotEmpty(appletQrcode)) {
					tcsbShopEntity.setAppletQrcode(ResourceUtil.getConfigByName("applet.qrcode")+appletQrcode);
				}
				//店铺的状态
				if ("0".equals(one.getStatus())) {
					tcsbShopEntity.setStatusName("启用中");
				}
				else {
					tcsbShopEntity.setStatusName("禁用中");
				}
				//店铺的预约方式/*0全额1免押金2押金*/
			/*	if ("0".equals(tcsbShopEntity.getReservationMethod())) {
					tcsbShopEntity.setReservationMethodName("全额");
				}else if ("1".equals(tcsbShopEntity.getReservationMethod())) {
					tcsbShopEntity.setReservationMethodName("免押金");
				}else {
					tcsbShopEntity.setReservationMethodName("押金");
				}*/
				//店铺的支付方式
				TcsbShopPaymodeEntity tcsbShopPaymodeEntity = tcsbShopPaymodeService.findUniqueByProperty(TcsbShopPaymodeEntity.class, "shopId", one.getId());
				if ("0".equals(tcsbShopPaymodeEntity.getPayMode())) {
					tcsbShopEntity.setPayMode("无限制");
				}else if ("1".equals(tcsbShopPaymodeEntity.getPayMode())) {
					tcsbShopEntity.setPayMode("线上付款");
				}else {
					tcsbShopEntity.setPayMode("线下付款");
				}
			
				resultListResult.add(tcsbShopEntity);
			}

		}
		dataGrid.setResults(resultListResult);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "initShopPayMode")
	public void initShopPayMode(
			HttpServletResponse response, DataGrid dataGrid) {
		List<TcsbShopEntity> tcsbShopEntities = tcsbShopService.loadAll(TcsbShopEntity.class);
		for (TcsbShopEntity tcsbShopEntity : tcsbShopEntities) {
			TcsbShopPaymodeEntity tcsbShopPaymodeEntity = new TcsbShopPaymodeEntity();
			tcsbShopPaymodeEntity.setPayMode("0");
			tcsbShopPaymodeEntity.setShopId(tcsbShopEntity.getId());
			try {
				tcsbShopPaymodeService.save(tcsbShopPaymodeEntity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除店铺管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbShopEntity tcsbShop, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbShop = systemService.getEntity(TcsbShopEntity.class,
				tcsbShop.getId());
		message = "店铺管理删除成功";
		try {
			tcsbShopService.delete(tcsbShop);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除店铺管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺管理删除成功";
		try {
			for (String id : ids.split(",")) {
				TcsbShopEntity tcsbShop = systemService.getEntity(
						TcsbShopEntity.class, id);
				tcsbShopService.delete(tcsbShop);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加店铺管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbShopEntity tcsbShop, TSUser user, TcsbShopPage tcsbShopPage,
			HttpServletRequest req) {
		//获取图片管理器
		List<TcsbImagesManagerEntity> imagesManagerList=tcsbShopPage.getImagesManagerList();
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺管理添加成功";
		try {
			// 用户添加
			// 得到用户的角色
			String roleid = oConvertUtils.getString(req.getParameter("roleid"));
			String password = oConvertUtils.getString(req
					.getParameter("password"));
			TSUser users = systemService.findUniqueByProperty(TSUser.class,
					"userName", user.getUserName());
			if (users != null) {
				message = "用户: " + users.getUserName() + "已经存在";
			} else {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(),
						password, PasswordUtil.getStaticSalt()));
				// if (user.getTSDepart().equals("")) {
				// user.setTSDepart(null);
				// }
				TSRole tSRole =  systemService.get(TSRole.class, roleid);
				user.setUserKey(tSRole.getRoleName());
				user.setStatus(Globals.User_Normal);
				user.setDeleteFlag(Globals.Delete_Normal);
				systemService.save(user);
				// todo zhanggm 保存多个组织机构
				saveUserOrgList(req, user);
				message = "用户: " + user.getUserName() + "添加成功";
				if (StringUtil.isNotEmpty(roleid)) {
					saveRoleUser(user, roleid);
				}
				//添加店铺
				tcsbShop.setHeadimg(filterCkPath(tcsbShop.getHeadimg()));
				 //系统更新update by jimmy 如果是商家只读出商家权限id移到系统表里
				tcsbShop.setUserId(user.getId());
				//系统更新update by jimmy 如果是商家只读出商家权限id移到系统表里
				tcsbShopService.addMain(tcsbShop,user,imagesManagerList);
				//无权限
				//tcsbShopService.addMain(tcsbShop,imagesManagerList);
				systemService.addLog(message, Globals.Log_Type_INSERT,
						Globals.Log_Leavel_INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新店铺管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopEntity tcsbShop, TcsbShopPage tcsbShopPage, HttpServletRequest req,TSUser user) {
		//获取图片管理器
		List<TcsbImagesManagerEntity> imagesManagerList=tcsbShopPage.getImagesManagerList();
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "店铺管理更新成功";
		TcsbShopEntity t = tcsbShopService.get(TcsbShopEntity.class,
				tcsbShop.getId());
		// 设置图片过滤路径
		if (!StringUtil.isEmpty(tcsbShop.getHeadimg())) {
			tcsbShop.setHeadimg(filterCkPath(tcsbShop.getHeadimg()));
		}
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShop, t);
			tcsbShopService.updateMain(t,imagesManagerList);
			//tcsbShopService.saveOrUpdate(t);
			
			// 得到用户的角色
			String roleid = oConvertUtils.getString(req.getParameter("roleid"));
			String password = oConvertUtils.getString(req.getParameter("password"));
			TSUser users = systemService.getEntity(TSUser.class, tcsbShop.getUserId());
			users.setEmail(user.getEmail());
			users.setOfficePhone(user.getOfficePhone());
			users.setMobilePhone(user.getMobilePhone());

            systemService.executeSql("delete from t_s_user_org where user_id=?", tcsbShop.getUserId());
            saveUserOrgList(req, users);
//	            users.setTSDepart(user.getTSDepart());
			users.setRealName(user.getRealName());
			//users.setStatus(Globals.User_Normal);
			users.setActivitiSync(user.getActivitiSync());
			systemService.updateEntitie(users);
			List<TSRoleUser> ru = systemService.findByProperty(TSRoleUser.class, "TSUser.id", tcsbShop.getUserId());
			systemService.deleteAllEntitie(ru);
			message = "用户: " + users.getUserName() + "更新成功";
			if (StringUtil.isNotEmpty(roleid)) {
				saveRoleUser(users, roleid);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "店铺管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 店铺管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopEntity tcsbShop, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShop.getId())) {
			tcsbShop = tcsbShopService.getEntity(TcsbShopEntity.class,
					tcsbShop.getId());
			req.setAttribute("tcsbShopPage", tcsbShop);
		}
		return new ModelAndView("com/tcsb/shop/tcsbShop-add");
	}

	/**
	 * 启用或禁用店铺
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "updateStatus")
	@ResponseBody
	public AjaxJson updateStatus(TcsbShopEntity tcsbShopEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "恭喜你改变状态成功";
		try{
			if (StringUtil.isNotEmpty(tcsbShopEntity.getId())) {
				tcsbShopEntity = tcsbShopService.getEntity(TcsbShopEntity.class, tcsbShopEntity.getId());
			}
			tcsbShopEntity.setStatus(request.getParameter("status"));
			tcsbShopService.saveOrUpdate(tcsbShopEntity);
			//更改用用户激活或锁定状态
			List<TSUser> users = systemService.findHql("from TSUser where shopId = ?", tcsbShopEntity.getId());
			if ("0".equals(request.getParameter("status"))) {
				//激活该店铺的所有用户
				Short short1 = 1;
				for (TSUser tsUser : users) {
					tsUser.setStatus(short1);
				}
			}else {
				//禁用该店铺下的所有用户
				Short short2 = 0;
				for (TSUser tsUser : users) {
					tsUser.setStatus(short2);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setSuccess(true);
		return j;
	}
	/**
	 * 店铺管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopEntity tcsbShop, HttpServletRequest req,HttpSession session) {
		if (StringUtil.isNotEmpty(tcsbShop.getId())) {
			tcsbShop = tcsbShopService.getEntity(TcsbShopEntity.class,
					tcsbShop.getId());
			//根据SHOPID获取支付方式
			TcsbShopPaymodeEntity tcsbShopPaymodeEntity = tcsbShopPaymodeService.findUniqueByProperty(TcsbShopPaymodeEntity.class, "shopId", tcsbShop.getId());
			tcsbShop.setPayMode(tcsbShopPaymodeEntity.getPayMode());
			//获取轮播图片
	        List<TcsbImagesManagerEntity> imagesManagerList=tcsbImagesManagerService.findByProperty(TcsbImagesManagerEntity.class, "shopId", tcsbShop.getId());
	        req.setAttribute("imagesManagerList", imagesManagerList);
		}
		  List<String> orgIdList = new ArrayList<String>();
	        TSDepart tsDepart = new TSDepart();
			if (StringUtil.isNotEmpty(tcsbShop.getUserId())) {
				TSUser user = systemService.getEntity(TSUser.class, tcsbShop.getUserId());
				/**
				 * 检测是否隐藏
				 */
				TSUser users =(TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
				if (users.getUserName().equals("admin")) {
					req.setAttribute("isadmin",1);
				}else{
					
						req.setAttribute("isadmin",0);
				}
				req.setAttribute("user", user);
				idandname(req, user);
				getOrgInfos(req, user);
			}
			// 替换原先的图片路径
			if (!StringUtil.isEmpty(tcsbShop.getHeadimg())) {
				//奇怪的问题清除SESSION一级缓存
				TcsbShopEntity tcsbShopPage = new TcsbShopEntity();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(tcsbShop, tcsbShopPage);
					tcsbShopPage.setHeadimg(getCkPath() + tcsbShop.getHeadimg());	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				req.setAttribute("tcsbShopPage", tcsbShopPage);
			}else {
				req.setAttribute("tcsbShopPage", tcsbShop);
			}
			req.setAttribute("tsDepart", tsDepart);
		return new ModelAndView("com/tcsb/shop/tcsbShop-update");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tcsbShopController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopEntity tcsbShop,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				tcsbShop, request.getParameterMap());
		List<TcsbShopEntity> tcsbShops = this.tcsbShopService
				.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "店铺管理");
		modelMap.put(NormalExcelConstants.CLASS, TcsbShopEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("店铺管理列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tcsbShops);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopEntity tcsbShop,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "店铺管理");
		modelMap.put(NormalExcelConstants.CLASS, TcsbShopEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("店铺管理列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request,
			HttpServletResponse response) {
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
				List<TcsbShopEntity> listTcsbShopEntitys = ExcelImportUtil
						.importExcel(file.getInputStream(),
								TcsbShopEntity.class, params);
				for (TcsbShopEntity tcsbShop : listTcsbShopEntitys) {
					tcsbShopService.save(tcsbShop);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}

	@RequestMapping(params = "apletShopQrcode")
	public void apletShopQrcode() throws Exception{
		/*List<TcsbShopEntity> tcsbShopEntities = tcsbShopService.loadAll(TcsbShopEntity.class);
		for (TcsbShopEntity tcsbShopEntity : tcsbShopEntities) {
			String appleShopQrcode = tcsbShopService.getWeixinAppletShopQrcode(tcsbShopEntity, user);
			tcsbShopEntity.setAppletQrcode(appleShopQrcode);
		}*/
		
		List<TSUser> users = systemService.loadAll(TSUser.class);
		Map<String,TSUser> map = new HashMap<String,TSUser>();
		for(TSUser user : users){
			if(!map.containsKey(user.getShopId()) && user.getShopId()!=null){
				map.put(user.getShopId(),user);
			}
		}
		if(!users.isEmpty()){
			List<TcsbShopEntity>  list = systemService.loadAll(TcsbShopEntity.class);
			for(TcsbShopEntity entity : list){
				if(map.containsKey(entity.getId())){
					TSUser user= map.get(entity.getId());
					String appleShopQrcode = tcsbShopService.getWeixinAppletShopQrcode(entity, user);
					entity.setAppletQrcode(appleShopQrcode);
					tcsbShopService.saveOrUpdate(entity);
				}
			}
		}
	}
	
	/**
	 * 带文字背景的小程序码
	 * @throws Exception
	 */
	@RequestMapping(params = "apletShopQrcode2")
	public void apletShopQrcode2() throws Exception{
		/*List<TcsbShopEntity> tcsbShopEntities = tcsbShopService.loadAll(TcsbShopEntity.class);
		for (TcsbShopEntity tcsbShopEntity : tcsbShopEntities) {
			String appleShopQrcode = tcsbShopService.getWeixinAppletShopQrcode(tcsbShopEntity, user);
			tcsbShopEntity.setAppletQrcode(appleShopQrcode);
		}*/
		
		List<TSUser> users = systemService.loadAll(TSUser.class);
		Map<String,TSUser> map = new HashMap<String,TSUser>();
		for(TSUser user : users){
			if(!map.containsKey(user.getShopId()) && user.getShopId()!=null){
				map.put(user.getShopId(),user);
			}
		}
		if(!users.isEmpty()){
			List<TcsbShopEntity>  list = systemService.loadAll(TcsbShopEntity.class);
			for(TcsbShopEntity entity : list){
				if(map.containsKey(entity.getId())){
					TSUser user= map.get(entity.getId());
					String appleShopQrcode = tcsbShopService.getWeixinAppletShopQrcode(entity, user);
					entity.setAppletQrcode(appleShopQrcode);
					tcsbShopService.saveOrUpdate(entity);
				}
			}
		}
	}


	

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TcsbShopEntity> list() {
		List<TcsbShopEntity> listTcsbShops = tcsbShopService
				.getList(TcsbShopEntity.class);
		return listTcsbShops;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopEntity task = tcsbShopService.get(TcsbShopEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopEntity tcsbShop,
			UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopEntity>> failures = validator
				.validate(tcsbShop);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		try {
			tcsbShopService.save(tcsbShop);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShop.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopController/" + id).build()
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopEntity tcsbShop) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopEntity>> failures = validator
				.validate(tcsbShop);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		
		// 保存
		try {
			tcsbShopService.saveOrUpdate(tcsbShop);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		// 按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tcsbShopService.deleteEntityById(TcsbShopEntity.class, id);
	}

	/**
	 * 保存 用户-组织机构 关系信息
	 * 
	 * @param request
	 *            request
	 * @param user
	 *            user
	 */
	private void saveUserOrgList(HttpServletRequest request, TSUser user) {
		String orgIds = oConvertUtils.getString(request.getParameter("orgIds"));

		List<TSUserOrg> userOrgList = new ArrayList<TSUserOrg>();
		List<String> orgIdList = extractIdListByComma(orgIds);
		for (String orgId : orgIdList) {
			TSDepart depart = new TSDepart();
			depart.setId(orgId);

			TSUserOrg userOrg = new TSUserOrg();
			userOrg.setTsUser(user);
			userOrg.setTsDepart(depart);

			userOrgList.add(userOrg);
		}
		if (!userOrgList.isEmpty()) {
			systemService.batchSave(userOrgList);
		}
	}

	protected void saveRoleUser(TSUser user, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			TSRoleUser rUser = new TSRoleUser();
			TSRole role = systemService.getEntity(TSRole.class, roleids[i]);
			rUser.setTSRole(role);
			rUser.setTSUser(user);
			systemService.save(rUser);

		}
	}
	
	public void idandname(HttpServletRequest req, TSUser user) {
		List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		String roleId = "";
		String roleName = "";
		if (roleUsers.size() > 0) {
			for (TSRoleUser tRoleUser : roleUsers) {
				roleId += tRoleUser.getTSRole().getId() + ",";
				roleName += tRoleUser.getTSRole().getRoleName() + ",";
			}
		}
		req.setAttribute("id", roleId);
		req.setAttribute("roleName", roleName);

	}
	
	public void getOrgInfos(HttpServletRequest req, TSUser user) {
		List<TSUserOrg> tSUserOrgs = systemService.findByProperty(TSUserOrg.class, "tsUser.id", user.getId());
		String orgIds = "";
		String departname = "";
		if (tSUserOrgs.size() > 0) {
			for (TSUserOrg tSUserOrg : tSUserOrgs) {
				orgIds += tSUserOrg.getTsDepart().getId() + ",";
				departname += tSUserOrg.getTsDepart().getDepartname() + ",";
			}
		}
		req.setAttribute("orgIds", orgIds);
		req.setAttribute("departname", departname);

	}
	
	
	/**
	 * 根据shopId获取商家店铺详情
	 * @param shopId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getShopDetail",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getShopDetail(@RequestParam String shopId,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		String sql = "select s.name,s.headImg,s.level,s.address,s.longitude,s.latitude,s.phone from tcsb_shop s where s.id = ?";
		
		Map<String, Object> map = tcsbShopService.findOneForJdbc(sql, shopId);
		map.put("headImg", getCkPath()+map.get("headImg"));
		//查找店铺轮播图片
		List<TcsbImagesManagerEntity> tcsbImagesManagerEntities = tcsbImagesManagerService.findHql("from TcsbImagesManagerEntity where shopId = ?", shopId);
		String shopImgs = "";
		if (!tcsbImagesManagerEntities.isEmpty()) {
			for (int i = 0; i < tcsbImagesManagerEntities.size(); i++) {
				if (i<tcsbImagesManagerEntities.size()-1) {
					shopImgs = shopImgs + getCkPath()+tcsbImagesManagerEntities.get(i).getfPath()+",";
				}else {
					shopImgs = shopImgs + getCkPath()+tcsbImagesManagerEntities.get(i).getfPath();
				}
			}
		}
		map.put("shopImgs", shopImgs);
		//获取优惠活动
		List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = tcsbDiscountActivityService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", shopId);
		if (!tcsbDiscountActivityEntities.isEmpty()) {
			//获取优惠活动中满减服务中有效的活动
			//List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntitysIsActives = new ArrayList<>();
			String shopDiscountMsg = "";
			for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
				/*//获取活动生效的时间
				Date beginTime = tcsbDiscountActivityEntity.getCreateDate();
				
				TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
				String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); 
				int days = 0;
				//todo
				if (dateUnit.equals("year")) {
					days = userPeriod*DateUtils.getCurrentYearDays();
				}else if (dateUnit.equals("month")) {
					days = userPeriod*DateUtils.getCurrentMonthDay();
				}else {
					days = userPeriod;
				}*/
				//优惠活动中有效的满减活动
					//update by jimmy begin
					TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbShopFullcutTemplateService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
					shopDiscountMsg =shopDiscountMsg+ "满"+ tcsbFullcutTemplateEntity.getTotal() + "减" + tcsbFullcutTemplateEntity.getDiscount() + ",";
					//update by jimmy end
			}
		   /*map.put("shopDiscountTotal", tcsbFullcutTemplateEntity.getTotal());
			map.put("shopDiscountDiscount", tcsbFullcutTemplateEntity.getDiscount());*/
			if (!StringUtil.isEmpty(shopDiscountMsg)) {
				shopDiscountMsg = shopDiscountMsg.substring(0, shopDiscountMsg.length()-1);
			}
			map.put("shopDiscountMsg", shopDiscountMsg);
			map.put("shopDiscount", "true");
		}else {
			map.put("shopDiscount", "false");
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(map);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	/**
	 * 获取我的足迹
	 * @param tcsbShopVO
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMyShopFoot",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getMyShopFoot(HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		String shopFootList = request.getParameter("shopFootList");
		List<Map<String, Object>> tcsbShopEntities = new ArrayList<>();
		if (shopFootList.contains(",")) {
			String args[] = shopFootList.split(",");
			for (int i = 0; i < args.length; i++) {
				Map<String, Object>  tcsbshopMap = tcsbShopService.findOneForJdbc("select id,name,headImg as shopImg from tcsb_shop where id =?", args[i]);
				if (tcsbshopMap!=null) {
					tcsbshopMap.put("shopImg", getCkPath()+tcsbshopMap.get("shopImg"));
					tcsbShopEntities.add(tcsbshopMap);
				}
			}
		}else {
			Map<String, Object>  tcsbshopMap = tcsbShopService.findOneForJdbc("select id,name,headImg as shopImg from tcsb_shop where id =?", shopFootList);
			if (tcsbshopMap!=null) {
				tcsbshopMap.put("shopImg", getCkPath()+tcsbshopMap.get("shopImg"));
				tcsbShopEntities.add(tcsbshopMap);
			}
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(tcsbShopEntities);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
}
