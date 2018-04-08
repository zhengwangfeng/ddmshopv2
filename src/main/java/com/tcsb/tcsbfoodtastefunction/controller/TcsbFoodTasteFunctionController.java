package com.tcsb.tcsbfoodtastefunction.controller;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.tcsbfoodfunction.entity.TcsbFoodFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.service.TcsbFoodTasteFunctionServiceI;
import com.weixin.weixinaccount.entity.WeixinAccountEntity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.core.util.NumberComparator3;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;

import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @Description: 口味偏好
 * @author onlineGenerator
 * @date 2017-06-20 14:33:53
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbFoodTasteFunctionController")
public class TcsbFoodTasteFunctionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbFoodTasteFunctionController.class);

	@Autowired
	private TcsbFoodTasteFunctionServiceI tcsbFoodTasteFunctionService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 口味偏好列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/tcsbfoodtastefunction/tcsbFoodTasteFunctionList");
	}

	/**
	 * 权限列表跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "fun")
	public ModelAndView fun(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		request.setAttribute("roleId", roleId);
		return new ModelAndView("com/tcsb/tcsbfoodtastefunction/roleSet");
	}
	
	
	/**
	 * 设置权限
	 * 
	 * @param role
	 * @param request
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "setAuthority")
	@ResponseBody
	public List<ComboTree> setAuthority(TcsbFoodEntity role,
			HttpServletRequest request, ComboTree comboTree) {
		TSUser user = getCurrentUser();
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodTasteFunctionEntity.class);
		cq.isNull("tcsbFoodTasteFunctionEntity");
		try {
			if (!checkAdmin()) {
				//获取用户店铺信息
				cq.eq("shopid", user.getShopId());
			}
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.addOrder("stateorder", SortDirection.asc);
		cq.add();
		List<TcsbFoodTasteFunctionEntity> functionList = systemService.getListByCriteriaQuery(
				cq, false);
		Collections.sort(functionList, new NumberComparator3());
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		String roleId = request.getParameter("roleId");
		List<TcsbFoodTasteFunctionEntity> loginActionlist = new ArrayList<TcsbFoodTasteFunctionEntity>();// 已有权限菜单
		role = this.systemService.get(TcsbFoodEntity.class, roleId);
		if (role != null) {
			List<TcsbFoodFunctionEntity> roleFunctionList = systemService.findByProperty(TcsbFoodFunctionEntity.class, "tcsbFoodEntity.id",role.getId());
			if (roleFunctionList.size() > 0) {
				for (TcsbFoodFunctionEntity roleFunction : roleFunctionList) {
					TcsbFoodTasteFunctionEntity function = (TcsbFoodTasteFunctionEntity) roleFunction.getTcsbFoodTasteFunctionEntity();
					loginActionlist.add(function);
				}
			}
			roleFunctionList.clear();
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","statename", "TcsbFoodTasteFunctionEntitys");

		comboTrees = systemService.ComboTree(functionList, comboTreeModel,loginActionlist, true);
		MutiLangUtil.setMutiComboTree(comboTrees);

		
		functionList.clear();
		loginActionlist.clear();
		return comboTrees;
	}
	
	
	
	/**
	 * 更新权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateAuthority")
	@ResponseBody
	public AjaxJson updateAuthority(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String roleId = request.getParameter("roleId");
			String rolefunction = request.getParameter("rolefunctions");
			TcsbFoodEntity role = this.systemService.get(TcsbFoodEntity.class, roleId);
			List<TcsbFoodFunctionEntity> roleFunctionList = systemService
					.findByProperty(TcsbFoodFunctionEntity.class, "tcsbFoodEntity.id",
							role.getId());
			Map<String, TcsbFoodFunctionEntity> map = new HashMap<String, TcsbFoodFunctionEntity>();
			for (TcsbFoodFunctionEntity functionOfRole : roleFunctionList) {
				map.put(functionOfRole.getTcsbFoodTasteFunctionEntity().getId(), functionOfRole);
			}
			String[] roleFunctions = rolefunction.split(",");
			Set<String> set = new HashSet<String>();
			for (String s : roleFunctions) {
				set.add(s);
			}
			updateCompare(set, role, map);
			j.setMsg("权限更新成功");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("权限更新失败");
		}
		return j;
	}

	/**
	 * 权限比较
	 * 
	 * @param set
	 *            最新的权限列表
	 * @param role
	 *            当前角色
	 * @param map
	 *            旧的权限列表
	 */
	private void updateCompare(Set<String> set, TcsbFoodEntity role,
			Map<String, TcsbFoodFunctionEntity> map) {
		List<TcsbFoodFunctionEntity> entitys = new ArrayList<TcsbFoodFunctionEntity>();
		List<TcsbFoodFunctionEntity> deleteEntitys = new ArrayList<TcsbFoodFunctionEntity>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				TcsbFoodFunctionEntity rf = new TcsbFoodFunctionEntity();
				TcsbFoodTasteFunctionEntity f = this.systemService.get(TcsbFoodTasteFunctionEntity.class, s);
				rf.setTcsbFoodTasteFunctionEntity(f);
				rf.setTcsbFoodEntity(role);
				entitys.add(rf);
			}
		}
		Collection<TcsbFoodFunctionEntity> collection = map.values();
		Iterator<TcsbFoodFunctionEntity> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
		}
		systemService.batchSave(entitys);
		systemService.deleteAllEntitie(deleteEntitys);

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
	public void datagrid(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodTasteFunctionEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodTasteFunction, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbFoodTasteFunctionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	/**
	 * 食品口味菜单TreeGrid列表
	 */
	@RequestMapping(params = "foodtastegrid")
	@ResponseBody
	public List<TreeGrid> tcsbFoodTasteFunctiongrid(HttpServletRequest request,TreeGrid treegrid) {
		
		
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodTasteFunctionEntity.class);
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("tcsbFoodTasteFunctionEntity.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("tcsbFoodTasteFunctionEntity");
		}
		try {
			if (!checkAdmin()) {
				TSUser user = getCurrentUser();
				cq.eq("shopid", user.getShopId());
			}
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.addOrder("stateorder", SortDirection.asc);
		cq.add();

		//获取装载数据权限的条件HQL
		cq = HqlGenerateUtil.getDataAuthorConditionHql(cq, new TcsbFoodTasteFunctionEntity());
		cq.add();

		
		List<TcsbFoodTasteFunctionEntity> tcsbFoodTasteFunctionEntityList = systemService.getListByCriteriaQuery(cq, false);

        Collections.sort(tcsbFoodTasteFunctionEntityList, new NumberComparator3());
        List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("statename");
		treeGridModel.setParentText("tcsbFoodTasteFunctionEntity_statename");
		treeGridModel.setParentId("tcsbFoodTasteFunctionEntity_id");
		treeGridModel.setIdField("id");
		treeGridModel.setSrc("url");
		treeGridModel.setFunctionType("type");
		treeGridModel.setChildList("TcsbFoodTasteFunctionEntitys");
		// 添加排序字段
		treeGridModel.setOrder("stateorder");
		treeGrids = systemService.treegrid(tcsbFoodTasteFunctionEntityList, treeGridModel);

		MutiLangUtil.setMutiTree(treeGrids);
		return treeGrids;
	}
	
	
	
	/**
	 * 食品口味菜单新增或编辑页面跳转
	 * @param TcsbFoodTasteFunctionEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity, HttpServletRequest req) {
		String menuid = req.getParameter("id");
		List<TcsbFoodTasteFunctionEntity> tcsbFoodTasteFunctionEntitylist = systemService.getList(TcsbFoodTasteFunctionEntity.class);
		req.setAttribute("flist", tcsbFoodTasteFunctionEntitylist);
		if (menuid != null) {
			tcsbFoodTasteFunctionEntity = systemService.getEntity(TcsbFoodTasteFunctionEntity.class, menuid);
			req.setAttribute("tcsbFoodTasteMenuPage", tcsbFoodTasteFunctionEntity);
		}
		if (tcsbFoodTasteFunctionEntity.getTcsbFoodTasteFunctionEntity() != null
				&& tcsbFoodTasteFunctionEntity.getTcsbFoodTasteFunctionEntity().getId() != null) {
			tcsbFoodTasteFunctionEntity.setFunctionlevel((short) 1);
			tcsbFoodTasteFunctionEntity.setTcsbFoodTasteFunctionEntity(
					(TcsbFoodTasteFunctionEntity) systemService.getEntity(TcsbFoodTasteFunctionEntity.class, tcsbFoodTasteFunctionEntity.getTcsbFoodTasteFunctionEntity().getId())
																		);
			req.setAttribute("tcsbFoodTasteMenuPage", tcsbFoodTasteFunctionEntity);
		}
		return new ModelAndView("com/tcsb/tcsbfoodtastefunction/tcsbFoodTasteFunction");
	}
	

	/**
	 * 微信菜单录入
	 * 
	 * @param TcsbFoodTasteFunctionEntity
	 * @return
	 */
	@RequestMapping(params = "saveFoodTasteFunction")
	@ResponseBody
	public AjaxJson saveWeixinMenu(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity,String parentfunctionid, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//TODO
		
		if(StringUtil.isNotEmpty(tcsbFoodTasteFunctionEntity.getId())){
			//执行更新
			TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction = systemService.get(TcsbFoodTasteFunctionEntity.class, tcsbFoodTasteFunctionEntity.getId());
			tcsbFoodTasteFunction.setFunctionlevel(tcsbFoodTasteFunctionEntity.getFunctionlevel());
			tcsbFoodTasteFunction.setStatename(tcsbFoodTasteFunctionEntity.getStatename());
			tcsbFoodTasteFunction.setStateorder(tcsbFoodTasteFunctionEntity.getStateorder());
			/*if(StringUtil.isNotEmpty(tcsbShopEntity)){
				tcsbFoodTasteFunction.setShopid(tcsbShopEntity.getId());
			}*/
			systemService.saveOrUpdate(tcsbFoodTasteFunction);
			message="更新成功";
		}else{
			//添加保存
			TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction = new TcsbFoodTasteFunctionEntity();
			if(StringUtil.isNotEmpty(parentfunctionid)){
				tcsbFoodTasteFunction.setFunctionlevel((short) 1);
				tcsbFoodTasteFunction.setStatename(tcsbFoodTasteFunctionEntity.getStatename());
				tcsbFoodTasteFunction.setStateorder(tcsbFoodTasteFunctionEntity.getStateorder());
				TcsbFoodTasteFunctionEntity tcsbParentFoodTasteFunction = systemService.get(TcsbFoodTasteFunctionEntity.class, parentfunctionid);
				tcsbFoodTasteFunction.setTcsbFoodTasteFunctionEntity(tcsbParentFoodTasteFunction);
				
			}else{
				tcsbFoodTasteFunction.setFunctionlevel((short) 0);
				tcsbFoodTasteFunction.setStatename(tcsbFoodTasteFunctionEntity.getStatename());
				tcsbFoodTasteFunction.setStateorder(tcsbFoodTasteFunctionEntity.getStateorder());
				tcsbFoodTasteFunction.setTcsbFoodTasteFunctionEntity(null);
			}
			TSUser user = getCurrentUser();
			//获取用户店铺信息
			TcsbShopEntity tcsbShopEntity= systemService.findUniqueByProperty(TcsbShopEntity.class,"userId",user.getId());
			if(StringUtil.isNotEmpty(tcsbShopEntity)){
				tcsbFoodTasteFunction.setShopid(tcsbShopEntity.getId());
			}
			systemService.save(tcsbFoodTasteFunction);
			message="保存成功";
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	
	/**
	 * 删除口味偏好
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbFoodTasteFunction = systemService.getEntity(TcsbFoodTasteFunctionEntity.class, tcsbFoodTasteFunction.getId());
		message = "口味偏好删除成功";
		try{
			tcsbFoodTasteFunctionService.delete(tcsbFoodTasteFunction);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "口味偏好删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除口味偏好
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "口味偏好删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction = systemService.getEntity(TcsbFoodTasteFunctionEntity.class, 
				id
				);
				tcsbFoodTasteFunctionService.delete(tcsbFoodTasteFunction);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "口味偏好删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加口味偏好
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "口味偏好添加成功";
		try{
			if(!StringUtil.isNotEmpty(tcsbFoodTasteFunction)){
				//tcsbFoodTasteFunction.setParentfunctionid(null);
			}
			tcsbFoodTasteFunctionService.save(tcsbFoodTasteFunction);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "口味偏好添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新口味偏好
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "口味偏好更新成功";
		TcsbFoodTasteFunctionEntity t = tcsbFoodTasteFunctionService.get(TcsbFoodTasteFunctionEntity.class, tcsbFoodTasteFunction.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbFoodTasteFunction, t);
			tcsbFoodTasteFunctionService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "口味偏好更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 口味偏好新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodTasteFunction.getId())) {
			tcsbFoodTasteFunction = tcsbFoodTasteFunctionService.getEntity(TcsbFoodTasteFunctionEntity.class, tcsbFoodTasteFunction.getId());
			req.setAttribute("tcsbFoodTasteFunctionPage", tcsbFoodTasteFunction);
		}
		return new ModelAndView("com/tcsb/tcsbfoodtastefunction/tcsbFoodTasteFunction-add");
	}
	/**
	 * 口味偏好编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbFoodTasteFunction.getId())) {
			tcsbFoodTasteFunction = tcsbFoodTasteFunctionService.getEntity(TcsbFoodTasteFunctionEntity.class, tcsbFoodTasteFunction.getId());
			req.setAttribute("tcsbFoodTasteFunctionPage", tcsbFoodTasteFunction);
		}
		return new ModelAndView("com/tcsb/tcsbfoodtastefunction/tcsbFoodTasteFunction-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbFoodTasteFunctionController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodTasteFunctionEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbFoodTasteFunction, request.getParameterMap());
		List<TcsbFoodTasteFunctionEntity> tcsbFoodTasteFunctions = this.tcsbFoodTasteFunctionService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"口味偏好");
		modelMap.put(NormalExcelConstants.CLASS,TcsbFoodTasteFunctionEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("口味偏好列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbFoodTasteFunctions);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"口味偏好");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbFoodTasteFunctionEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("口味偏好列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbFoodTasteFunctionEntity> listTcsbFoodTasteFunctionEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbFoodTasteFunctionEntity.class,params);
				for (TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction : listTcsbFoodTasteFunctionEntitys) {
					tcsbFoodTasteFunctionService.save(tcsbFoodTasteFunction);
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
	public List<TcsbFoodTasteFunctionEntity> list() {
		List<TcsbFoodTasteFunctionEntity> listTcsbFoodTasteFunctions=tcsbFoodTasteFunctionService.getList(TcsbFoodTasteFunctionEntity.class);
		return listTcsbFoodTasteFunctions;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbFoodTasteFunctionEntity task = tcsbFoodTasteFunctionService.get(TcsbFoodTasteFunctionEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodTasteFunctionEntity>> failures = validator.validate(tcsbFoodTasteFunction);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodTasteFunctionService.save(tcsbFoodTasteFunction);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbFoodTasteFunction.getId();
		URI uri = uriBuilder.path("/rest/tcsbFoodTasteFunctionController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbFoodTasteFunctionEntity tcsbFoodTasteFunction) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbFoodTasteFunctionEntity>> failures = validator.validate(tcsbFoodTasteFunction);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbFoodTasteFunctionService.saveOrUpdate(tcsbFoodTasteFunction);
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
		tcsbFoodTasteFunctionService.deleteEntityById(TcsbFoodTasteFunctionEntity.class, id);
	}
}
