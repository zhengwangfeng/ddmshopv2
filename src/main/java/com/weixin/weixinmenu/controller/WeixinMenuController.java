package com.weixin.weixinmenu.controller;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.NumberComparator2;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
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

import com.weixin.core.entity.common.Button;
import com.weixin.core.entity.common.CommonButton;
import com.weixin.core.entity.common.ComplexButton;
import com.weixin.core.entity.common.Menu;
import com.weixin.core.entity.common.ViewButton;
import com.weixin.core.util.WeixinUtil;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;
import com.weixin.weixinaccount.service.WeixinAccountServiceI;
import com.weixin.weixinmenu.entity.WeixinMenuEntity;
import com.weixin.weixinmenu.service.WeixinMenuServiceI;
import com.weixin.weixinnewstemplate.entity.WeixinNewstemplateEntity;
import com.weixin.weixinnewstemplate.service.WeixinNewstemplateServiceI;
import com.weixin.weixintexttemplate.entity.WeixinTexttemplateEntity;
import com.weixin.weixintexttemplate.service.WeixinTexttemplateServiceI;

/**   
 * @Title: Controller  
 * @Description: 微信菜单
 * @author onlineGenerator
 * @date 2017-03-28 17:47:49
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/weixinMenuController")
public class WeixinMenuController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WeixinMenuController.class);

	@Autowired
	private WeixinMenuServiceI weixinMenuService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	@Autowired
	private WeixinTexttemplateServiceI weixinTexttemplateService;
	@Autowired
	private WeixinNewstemplateServiceI weixinNewstemplateService;
	


	/**
	 * 微信菜单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/weixin/weixinmenu/weixinMenuList");
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
	public void datagrid(WeixinMenuEntity weixinMenu,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WeixinMenuEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinMenu, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.weixinMenuService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 微信菜单TreeGrid列表
	 */
	@RequestMapping(params = "weixinMenugrid")
	@ResponseBody
	public List<TreeGrid> weixinMenugrid(HttpServletRequest request,TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(WeixinMenuEntity.class);
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("weixinMenuEntity.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("weixinMenuEntity");
		}
		/*if(type != null){
			cq.eq("functionType", type.shortValue());
		}*/
		cq.addOrder("orders", SortDirection.asc);
		cq.add();

		//获取装载数据权限的条件HQL
		cq = HqlGenerateUtil.getDataAuthorConditionHql(cq, new WeixinMenuEntity());
		cq.add();

		
		List<WeixinMenuEntity> weixinMenuEntityList = systemService.getListByCriteriaQuery(cq, false);

        Collections.sort(weixinMenuEntityList, new NumberComparator2());
        List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		//treeGridModel.setIcon("TSIcon_iconPath");
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("weixinMenuEntity_name");
		treeGridModel.setParentId("weixinMenuEntity_id");
		treeGridModel.setSrc("url");
		treeGridModel.setIdField("id");
		treeGridModel.setFunctionType("type");
		treeGridModel.setChildList("WeixinMenuEntitys");
		// 添加排序字段
		treeGridModel.setOrder("orders");
		//treeGridModel.setIconStyle("functionIconStyle");

		//treeGridModel.setFunctionType("functionType");

		treeGrids = systemService.treegrid(weixinMenuEntityList, treeGridModel);

		MutiLangUtil.setMutiTree(treeGrids);
		return treeGrids;
	}
	@RequestMapping(params = "gettemplate")
	@ResponseBody
	public AjaxJson gettemplate(HttpServletRequest request,
			HttpServletResponse response) {
		List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
		String accountid = weixinAccountEntities.get(0).getId();
		String msgtype = request.getParameter("msgtype");
		AjaxJson ajaxJson =new AjaxJson();
		if ("text".equals(msgtype)) {
			List<WeixinTexttemplateEntity> textList = weixinTexttemplateService.findByProperty(WeixinTexttemplateEntity.class, "accountid", accountid);
			ajaxJson.setObj(textList);
			
		} else if ("news".equals(msgtype)) {
			List<WeixinNewstemplateEntity> newsList = weixinNewstemplateService.findByProperty(WeixinNewstemplateEntity.class, "accountid", accountid);
			ajaxJson.setObj(newsList);
		}
		ajaxJson.setSuccess(true);
		ajaxJson.setMsg("获取模版成功");
		return ajaxJson;
	}
	/**
	 * 删除微信菜单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WeixinMenuEntity weixinMenu, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		weixinMenu = systemService.getEntity(WeixinMenuEntity.class, weixinMenu.getId());
		message = "微信菜单删除成功";
		try{
			weixinMenuService.delete(weixinMenu);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信菜单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除微信菜单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信菜单删除成功";
		try{
			for(String id:ids.split(",")){
				WeixinMenuEntity weixinMenu = systemService.getEntity(WeixinMenuEntity.class, 
				id
				);
				weixinMenuService.delete(weixinMenu);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "微信菜单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加微信菜单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WeixinMenuEntity weixinMenu, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信菜单添加成功";
		try{
			weixinMenuService.save(weixinMenu);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信菜单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新微信菜单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WeixinMenuEntity weixinMenu, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信菜单更新成功";
		WeixinMenuEntity t = weixinMenuService.get(WeixinMenuEntity.class, weixinMenu.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(weixinMenu, t);
			weixinMenuService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "微信菜单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 菜单同步到微信
	 * @param menuEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "sameMenu")
	@ResponseBody
	public AjaxJson sameMenu(WeixinMenuEntity menuEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message=null;
		List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
		String accountid = weixinAccountEntities.get(0).getId();
		String hql = "from WeixinMenuEntity where weixinMenuEntity.id is null and accountid = '"
				+ accountid + "'  order by  orders asc";
		List<WeixinMenuEntity> menuList = this.systemService.findByQueryString(hql);
		org.jeecgframework.core.util.LogUtil.info(".....一级菜单的个数是....." + menuList.size());
		Menu menu = new Menu();
		Button firstArr[] = new Button[menuList.size()];
		for (int a = 0; a < menuList.size(); a++) {
			WeixinMenuEntity entity = menuList.get(a);
			String hqls = "from WeixinMenuEntity where weixinMenuEntity.id = '" + entity.getId()
					+ "' and accountid = '" + accountid
					+ "'  order by  orders asc";
			List<WeixinMenuEntity> childList = this.systemService
					.findByQueryString(hqls);
			// org.jeecgframework.core.util.LogUtil.info("....二级菜单的大小....." + childList.size());
			if (childList.size() == 0) {
				if("view".equals(entity.getType())){
					ViewButton viewButton = new ViewButton();
					viewButton.setName(entity.getName());
					viewButton.setType(entity.getType());
					viewButton.setUrl(entity.getUrl());
					firstArr[a] = viewButton;
				}else if("click".equals(entity.getType())){
					CommonButton cb = new CommonButton();
					cb.setKey(entity.getMenukey());
					cb.setName(entity.getName());
					cb.setType(entity.getType());
					firstArr[a] = cb;
				}
			
			} else {
				ComplexButton complexButton = new ComplexButton();
				complexButton.setName(entity.getName());

				Button[] secondARR = new Button[childList.size()];
				for (int i = 0; i < childList.size(); i++) {
					WeixinMenuEntity children = childList.get(i);
					String type = children.getType();
					if ("view".equals(type)) {
						ViewButton viewButton = new ViewButton();
						viewButton.setName(children.getName());
						viewButton.setType(children.getType());
						viewButton.setUrl(children.getUrl());
						secondARR[i] = viewButton;

					} else if ("click".equals(type)) {

						CommonButton cb1 = new CommonButton();
						cb1.setName(children.getName());
						cb1.setType(children.getType());
						cb1.setKey(children.getMenukey());
						secondARR[i] = cb1;

					}

				}
				complexButton.setSub_button(secondARR);
				firstArr[a] = complexButton;
			}
		}
		menu.setButton(firstArr);
		JSONObject jsonMenu = JSONObject.fromObject(menu);
		String accessToken = weixinAccountService.getAccessToken();
		String url = WeixinUtil.menu_create_url.replace("ACCESS_TOKEN",
				accessToken);
		JSONObject jsonObject= new JSONObject();
		
		try {
			jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu.toString());
			LogUtil.info(jsonObject);
			if(jsonObject!=null){
				if (0 == jsonObject.getInt("errcode")) {
						message = "同步菜单信息数据成功！";
				}
				else {
					message = "同步菜单信息数据失败！错误码为："+jsonObject.getInt("errcode")+"错误信息为："+jsonObject.getString("errmsg");
				}
			}else{
				message = "同步菜单信息数据失败！同步自定义菜单URL地址不正确。";
			}
		} catch (Exception e) {
			message = "同步菜单信息数据失败！";
		}finally{
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
			j.setMsg(message);
		}
		return j;
	}

	/**
	 * 微信菜单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WeixinMenuEntity weixinMenu, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinMenu.getId())) {
			weixinMenu = weixinMenuService.getEntity(WeixinMenuEntity.class, weixinMenu.getId());
			req.setAttribute("weixinMenuPage", weixinMenu);
		}
		return new ModelAndView("com/weixin/weixinmenu/weixinMenu-add");
	}
	

	/**
	 * 微信菜单新增或编辑页面跳转
	 * @param weixinMenuEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(WeixinMenuEntity weixinMenuEntity, HttpServletRequest req) {
		String menuid = req.getParameter("id");
		List<WeixinMenuEntity> weixinMenulist = systemService.getList(WeixinMenuEntity.class);
		req.setAttribute("flist", weixinMenulist);
		if (menuid != null) {
			weixinMenuEntity = systemService.getEntity(WeixinMenuEntity.class, menuid);
			req.setAttribute("weixinMenuPage", weixinMenuEntity);
		}
		if (weixinMenuEntity.getWeixinMenuEntity() != null
				&& weixinMenuEntity.getWeixinMenuEntity().getId() != null) {
			weixinMenuEntity.setMenuLevel((short) 1);
			weixinMenuEntity.setWeixinMenuEntity((WeixinMenuEntity) systemService.getEntity(
					WeixinMenuEntity.class, weixinMenuEntity.getWeixinMenuEntity().getId()));
			req.setAttribute("weixinMenuPage", weixinMenuEntity);
		}
		return new ModelAndView("com/weixin/weixinmenu/weixinMenu");
	}
	
	/**
	 * 微信菜单录入
	 * 
	 * @param weixinMenuEntity
	 * @return
	 */
	@RequestMapping(params = "saveWeixinMenu")
	@ResponseBody
	public AjaxJson saveWeixinMenu(WeixinMenuEntity weixinMenuEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		weixinMenuEntity.setUrl(weixinMenuEntity.getUrl().trim());
		String order = weixinMenuEntity.getOrders();
		if (StringUtils.isEmpty(order)) {
			weixinMenuEntity.setOrders("0");
		}
		if (weixinMenuEntity.getWeixinMenuEntity().getId().equals("")) {
			weixinMenuEntity.setWeixinMenuEntity(null);
		} else {
			WeixinMenuEntity parent = systemService.getEntity(WeixinMenuEntity.class,
					weixinMenuEntity.getWeixinMenuEntity().getId());
			weixinMenuEntity.setMenuLevel(Short.valueOf(parent.getMenuLevel()+ 1 + ""));
		}
		if (StringUtil.isNotEmpty(weixinMenuEntity.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.menu");
			//获取唯一公众号
			List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
			if (weixinAccountEntities.isEmpty()) {
				j.setMsg("请先添加微信公众号");
				j.setSuccess(false);
				return j;
			}
			WeixinAccountEntity weixinAccountEntity = weixinAccountEntities.get(0);
			weixinMenuEntity.setAccountid(weixinAccountEntity.getId());
			systemService.saveOrUpdate(weixinMenuEntity);
			systemService.addLog(message, Globals.Log_Type_UPDATE,Globals.Log_Leavel_INFO);
			List<WeixinMenuEntity> subWeixinMenu = systemService.findByProperty(WeixinMenuEntity.class, "weixinMenuEntity.id", weixinMenuEntity.getId());
			updateSubWeixinMenu(subWeixinMenu,weixinMenuEntity);

			//systemService.flushRoleFunciton(weixinMenuEntity.getId(), weixinMenuEntity);
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			TSUser user = ResourceUtil.getSessionUserName();
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				session.removeAttribute(role.getId());
			}

		} else {
			if (weixinMenuEntity.getMenuLevel().equals(Globals.Function_Leave_ONE)) {
				List<WeixinMenuEntity> functionList = systemService.findByProperty(
						WeixinMenuEntity.class, "menuLevel",
						Globals.Function_Leave_ONE);
				// int ordre=functionList.size()+1;
				// function.setFunctionOrder(Globals.Function_Order_ONE+ordre);
				weixinMenuEntity.setOrders(weixinMenuEntity.getOrders());
			} else {
				List<WeixinMenuEntity> functionList = systemService.findByProperty(
						WeixinMenuEntity.class, "menuLevel",
						Globals.Function_Leave_TWO);
				// int ordre=functionList.size()+1;
				// function.setFunctionOrder(Globals.Function_Order_TWO+ordre);
				weixinMenuEntity.setOrders(weixinMenuEntity.getOrders());
			}
			message = MutiLangUtil.paramAddSuccess("common.menu");
			//获取唯一公众号
			List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
			if (weixinAccountEntities.isEmpty()) {
				j.setMsg("请先添加微信公众号");
				j.setSuccess(false);
				return j;
			}
			WeixinAccountEntity weixinAccountEntity = weixinAccountEntities.get(0);
			weixinMenuEntity.setAccountid(weixinAccountEntity.getId());
			systemService.save(weixinMenuEntity);
			systemService.addLog(message, Globals.Log_Type_INSERT,Globals.Log_Leavel_INFO);
		}

		j.setMsg(message);
		return j;
	}
	/**
	 * 递归更新子菜单的menuLevel
	 * @param subFunction
	 * @param parent
	 */
	private void updateSubWeixinMenu(List<WeixinMenuEntity> subWeixinMenu,WeixinMenuEntity  parent) {
		if(subWeixinMenu.size() ==0){//没有子菜单是结束
              return;
       }else{
    	   for (WeixinMenuEntity tsWeixinMenu : subWeixinMenu) {
    		   tsWeixinMenu.setMenuLevel(Short.valueOf(parent.getMenuLevel()
						+ 1 + ""));
				systemService.saveOrUpdate(tsWeixinMenu);
				List<WeixinMenuEntity> subFunction1 = systemService.findByProperty(WeixinMenuEntity.class, "weixinMenuEntity.id", tsWeixinMenu.getId());
				updateSubWeixinMenu(subFunction1,tsWeixinMenu);
		   }
       }
	}
	/**
	 * 父级权限下拉菜单
	 */
	@RequestMapping(params = "setPWeixinMenu")
	@ResponseBody
	public List<ComboTree> setPWeixinMenu(Integer type,HttpServletRequest request,
			ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(WeixinMenuEntity.class);
		if (null != request.getParameter("selfId")) {
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("weixinMenuEntity.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("weixinMenuEntity");
		}
		//菜单类型指什么？指菜单管理有啥事数据权限
		/*if(type != null){
			cq.eq("functionType", type.shortValue());
		}*/
		cq.add();
		List<WeixinMenuEntity> weixinMenuList = systemService.getListByCriteriaQuery(
				cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","name", "WeixinMenuEntitys");
		WeixinMenuEntity defaultFunction = new WeixinMenuEntity();
			defaultFunction.setName("请选择上级菜单管理");
			weixinMenuList.add(0, defaultFunction);
		comboTrees = systemService.ComboTree(weixinMenuList, comboTreeModel,
				null, false);
		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
	}

	/**
	 * 微信菜单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WeixinMenuEntity weixinMenu, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(weixinMenu.getId())) {
			weixinMenu = weixinMenuService.getEntity(WeixinMenuEntity.class, weixinMenu.getId());
			req.setAttribute("weixinMenuPage", weixinMenu);
		}
		return new ModelAndView("com/weixin/weixinmenu/weixinMenu-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","weixinMenuController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WeixinMenuEntity weixinMenu,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WeixinMenuEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinMenu, request.getParameterMap());
		List<WeixinMenuEntity> weixinMenus = this.weixinMenuService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"微信菜单");
		modelMap.put(NormalExcelConstants.CLASS,WeixinMenuEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信菜单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,weixinMenus);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WeixinMenuEntity weixinMenu,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"微信菜单");
    	modelMap.put(NormalExcelConstants.CLASS,WeixinMenuEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信菜单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WeixinMenuEntity> listWeixinMenuEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WeixinMenuEntity.class,params);
				for (WeixinMenuEntity weixinMenu : listWeixinMenuEntitys) {
					weixinMenuService.save(weixinMenu);
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
	public List<WeixinMenuEntity> list() {
		List<WeixinMenuEntity> listWeixinMenus=weixinMenuService.getList(WeixinMenuEntity.class);
		return listWeixinMenus;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WeixinMenuEntity task = weixinMenuService.get(WeixinMenuEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WeixinMenuEntity weixinMenu, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinMenuEntity>> failures = validator.validate(weixinMenu);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinMenuService.save(weixinMenu);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = weixinMenu.getId();
		URI uri = uriBuilder.path("/rest/weixinMenuController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WeixinMenuEntity weixinMenu) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WeixinMenuEntity>> failures = validator.validate(weixinMenu);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			weixinMenuService.saveOrUpdate(weixinMenu);
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
		weixinMenuService.deleteEntityById(WeixinMenuEntity.class, id);
	}
}
