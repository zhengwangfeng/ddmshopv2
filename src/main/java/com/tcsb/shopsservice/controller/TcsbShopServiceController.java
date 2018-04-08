package com.tcsb.shopsservice.controller;
import com.alibaba.fastjson.JSONObject;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.service.entity.TcsbServiceEntity;
import com.tcsb.service.service.TcsbServiceServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopsservice.entity.TcsbShopServiceEntity;
import com.tcsb.shopsservice.service.TcsbShopServiceServiceI;
import com.tcsb.shopsservice.vo.ShopServiceVo;
import com.tcsb.tcsbfoodfunction.entity.TcsbFoodFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;
import com.weixin.weixinuser.entity.WeixinUserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.codehaus.jackson.map.util.JSONPObject;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.NumberComparator3;
import org.jeecgframework.core.util.NumberComparator4;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Validator;

import org.springframework.http.MediaType;


/**   
 * @Title: Controller  
 * @Description: 商家服务
 * @author onlineGenerator
 * @date 2017-05-09 22:10:50
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopServiceController")
public class TcsbShopServiceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopServiceController.class);

	@Autowired
	private TcsbShopServiceServiceI tcsbShopServiceService;
	@Autowired
	private TcsbServiceServiceI tcsbServiceService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;


	/**
	 * 商家服务列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		
		return new ModelAndView("com/tcsb/shopsservice/tcsbShopServiceList");
	}
	
	
	/**
	 * 权限列表跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "fun")
	public ModelAndView fun(HttpServletRequest request) {
		//String roleId = request.getParameter("roleId");
		TSUser user = getCurrentUser();
		request.setAttribute("shopId", user.getShopId());
		return new ModelAndView("com/tcsb/shopsservice/roleSet");
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
	public List<ComboTree> setAuthority(TcsbShopEntity role,
			HttpServletRequest request, ComboTree comboTree) {
		TSUser user = getCurrentUser();
		CriteriaQuery cq = new CriteriaQuery(TcsbServiceEntity.class);
		cq.isNull("tcsbServiceEntity");
		/*try {
			if (!checkAdmin()) {
				//获取用户店铺信息
				cq.eq("shopid", user.getShopId());
			}
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}*/
		cq.addOrder("stateorder", SortDirection.asc);
		cq.add();
		List<TcsbServiceEntity> functionList = systemService.getListByCriteriaQuery(
				cq, false);
		Collections.sort(functionList, new NumberComparator4());
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		String roleId = request.getParameter("roleId");
		List<TcsbServiceEntity> loginActionlist = new ArrayList<TcsbServiceEntity>();// 已有权限菜单
		System.out.println("====================");
		System.out.println(roleId);
		role = this.systemService.get(TcsbShopEntity.class, roleId);
		if (role != null) {
			List<TcsbShopServiceEntity> roleFunctionList = systemService.findByProperty(TcsbShopServiceEntity.class, "tcsbShopEntity.id",role.getId());
			if (roleFunctionList.size() > 0) {
				for (TcsbShopServiceEntity roleFunction : roleFunctionList) {
					TcsbServiceEntity function = (TcsbServiceEntity) roleFunction.getTcsbServiceEntity();
					loginActionlist.add(function);
				}
			}
			roleFunctionList.clear();
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","name", "TcsbServiceEntityLists");

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
			TcsbShopEntity role = this.systemService.get(TcsbShopEntity.class, roleId);
			List<TcsbShopServiceEntity> roleFunctionList = systemService
					.findByProperty(TcsbShopServiceEntity.class, "tcsbShopEntity.id",
							role.getId());
			Map<String, TcsbShopServiceEntity> map = new HashMap<String, TcsbShopServiceEntity>();
			for (TcsbShopServiceEntity functionOfRole : roleFunctionList) {
				map.put(functionOfRole.getTcsbServiceEntity().getId(), functionOfRole);
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
	private void updateCompare(Set<String> set, TcsbShopEntity role,
			Map<String, TcsbShopServiceEntity> map) {
		List<TcsbShopServiceEntity> entitys = new ArrayList<TcsbShopServiceEntity>();
		List<TcsbShopServiceEntity> deleteEntitys = new ArrayList<TcsbShopServiceEntity>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				TcsbShopServiceEntity rf = new TcsbShopServiceEntity();
				TcsbServiceEntity f = this.systemService.get(TcsbServiceEntity.class, s);
				rf.setTcsbServiceEntity(f);
				rf.setTcsbShopEntity(role);
				entitys.add(rf);
			}
		}
		Collection<TcsbShopServiceEntity> collection = map.values();
		Iterator<TcsbShopServiceEntity> it = collection.iterator();
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
	@ResponseBody
	public List<TreeGrid> tcsbFoodTasteFunctiongrid(HttpServletRequest request,TreeGrid treegrid) {
	//public void datagrid(TcsbShopServiceEntity tcsbShopServiceEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,HttpSession session) {
		
		CriteriaQuery cq = new CriteriaQuery(TcsbServiceEntity.class);
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("tcsbServiceEntity.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("tcsbServiceEntity");
		}
		/*try {
			if (!checkAdmin()) {
				TSUser user = getCurrentUser();
				cq.eq("shopid", user.getShopId());
			}
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}*/
		cq.addOrder("stateorder", SortDirection.asc);
		cq.add();

		//获取装载数据权限的条件HQL
		cq = HqlGenerateUtil.getDataAuthorConditionHql(cq, new TcsbServiceEntity());
		cq.add();

		
		List<TcsbServiceEntity> tcsbServiceEntityList = systemService.getListByCriteriaQuery(cq, false);
		System.out.println("=======================");
		System.out.println(tcsbServiceEntityList.size());
		
		
        Collections.sort(tcsbServiceEntityList, new NumberComparator4());
        List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("tcsbServiceEntity_name");
		treeGridModel.setParentId("tcsbServiceEntity_id");
		treeGridModel.setIdField("id");
		treeGridModel.setSrc("logo");
		treeGridModel.setFunctionType("type");
		treeGridModel.setChildList("tcsbServiceEntityLists");
		// 添加排序字段
		treeGridModel.setOrder("stateorder");
		treeGrids = systemService.treegrid(tcsbServiceEntityList, treeGridModel);

		MutiLangUtil.setMutiTree(treeGrids);
		return treeGrids;
		
		/*TSUser user = getCurrentUser();
		//获取系统服务信息
		List<TcsbServiceEntity> tcsbServiceLIist = tcsbServiceService.findByQueryString("from TcsbServiceEntity");
		List<ShopServiceVo> voList = new ArrayList<>();
		ShopServiceVo shopServiceVo;
		for (TcsbServiceEntity TcsbServiceEntity : tcsbServiceLIist) {
			shopServiceVo = new ShopServiceVo();
			try {
				BeanUtils.copyProperties(shopServiceVo, TcsbServiceEntity);
				//检查该服务商家是否开启
				TcsbShopServiceEntity checkShopService = tcsbShopServiceService.getTcsbShopServiceEntityByServiceIdAndShopId(TcsbServiceEntity.getId(), user.getShopId());
				if(checkShopService == null){
					//商家未添加该服务
					shopServiceVo.setState(0);
				}else{
					//商家已添加该服务
					shopServiceVo.setState(1);
				}
				voList.add(shopServiceVo);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		dataGrid.setResults(voList);
		TagUtil.datagrid(response, dataGrid);*/
		/*JSONObject object = new JSONObject();
		PrintWriter pw = null;
		try {
			pw=response.getWriter();
			pw.write(object.toJSONString(voList));
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				pw.close();
				object.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}

	
	
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid2")
	@ResponseBody
	public void datagrid(TcsbShopServiceEntity tcsbShopServiceEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,HttpSession session) {
		
		/*CriteriaQuery cq = new CriteriaQuery(TcsbServiceEntity.class);
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("tcsbServiceEntity.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("tcsbServiceEntity");
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
		cq = HqlGenerateUtil.getDataAuthorConditionHql(cq, new TcsbServiceEntity());
		cq.add();

		
		List<TcsbServiceEntity> tcsbServiceEntityList = systemService.getListByCriteriaQuery(cq, false);
		System.out.println("=======================");
		System.out.println(tcsbServiceEntityList.size());
		
		
        Collections.sort(tcsbServiceEntityList, new NumberComparator4());
        List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("tcsbServiceEntity_name");
		treeGridModel.setParentId("tcsbServiceEntity_id");
		treeGridModel.setIdField("id");
		treeGridModel.setSrc("logo");
		treeGridModel.setFunctionType("type");
		treeGridModel.setChildList("tcsbServiceEntityLists");
		// 添加排序字段
		treeGridModel.setOrder("stateorder");
		treeGrids = systemService.treegrid(tcsbServiceEntityList, treeGridModel);

		MutiLangUtil.setMutiTree(treeGrids);
		return treeGrids;*/
		
		/*TSUser user = getCurrentUser();
		//获取系统服务信息
		List<TcsbServiceEntity> tcsbServiceLIist = tcsbServiceService.findByQueryString("from TcsbServiceEntity");
		List<ShopServiceVo> voList = new ArrayList<>();
		ShopServiceVo shopServiceVo;
		for (TcsbServiceEntity TcsbServiceEntity : tcsbServiceLIist) {
			shopServiceVo = new ShopServiceVo();
			try {
				BeanUtils.copyProperties(shopServiceVo, TcsbServiceEntity);
				//检查该服务商家是否开启
				TcsbShopServiceEntity checkShopService = tcsbShopServiceService.getTcsbShopServiceEntityByServiceIdAndShopId(TcsbServiceEntity.getId(), user.getShopId());
				if(checkShopService == null){
					//商家未添加该服务
					shopServiceVo.setState(0);
				}else{
					//商家已添加该服务
					shopServiceVo.setState(1);
				}
				voList.add(shopServiceVo);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		dataGrid.setResults(voList);
		TagUtil.datagrid(response, dataGrid);*/
		/*JSONObject object = new JSONObject();
		PrintWriter pw = null;
		try {
			pw=response.getWriter();
			pw.write(object.toJSONString(voList));
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				pw.close();
				object.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}

	
	
	
	/**
	 * 删除商家服务
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public int doDel(TcsbShopServiceEntity tcsbShopServiceEntity, HttpServletRequest request,String serviceId,HttpSession session) {
		TSUser user = getCurrentUser();
		TcsbShopServiceEntity checkShopService = tcsbShopServiceService.getTcsbShopServiceEntityByServiceIdAndShopId(serviceId, user.getShopId());
		try {
			tcsbShopServiceService.delete(checkShopService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	


	/**
	 * 添加商家服务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public int doAdd(TcsbShopServiceEntity tcsbShopServiceEntity, HttpServletRequest request,String serviceId,HttpSession session) {
		TSUser user = getCurrentUser();
		//获取用户店铺信息
		TcsbShopServiceEntity checkShopService = tcsbShopServiceService.getTcsbShopServiceEntityByServiceIdAndShopId(serviceId, user.getShopId());
		if(checkShopService == null){
			TcsbShopServiceEntity ts = new TcsbShopServiceEntity();
			//ts.setServiceId(serviceId);
			//ts.setShopId(user.getShopId());
			try {
				tcsbShopServiceService.save(ts);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 1;
		}else{
			return 0;
		}
		
	}
	
	
	
	
	//用户获取呼叫服务信息
	@RequestMapping(value = "/getService",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getService(@RequestParam String shopId,HttpServletRequest request,HttpServletResponse response){
		System.out.println("获取服务列表");
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		try {
			List<TcsbShopServiceEntity> TcsbShopServiceEntityList= tcsbShopServiceService.getTcsbShopServiceEntityListByShopId(shopId);
			List<TcsbServiceEntity> TcsbServiceEntityList = new ArrayList<>();
			for (TcsbShopServiceEntity tcsbShopServiceEntity : TcsbShopServiceEntityList) {
				//TcsbServiceEntity tcsbServiceEntity = tcsbServiceService.get(TcsbServiceEntity.class, tcsbShopServiceEntity.getServiceId());
				//TcsbServiceEntityList.add(tcsbServiceEntity);
			}
			ajaxJsonApi.setObj(TcsbServiceEntityList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	
	
	

	
	
	
	
	/*
	*//**
	 * 批量删除商家服务
	 * 
	 * @return
	 *//*
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商家服务删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbShopServiceEntity tcsbShopService = systemService.getEntity(TcsbShopServiceEntity.class, 
				id
				);
				tcsbShopServiceService.delete(tcsbShopService);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商家服务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	
	
	*//**
	 * 更新商家服务
	 * 
	 * @param ids
	 * @return
	 *//*
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbShopServiceEntity tcsbShopService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商家服务更新成功";
		TcsbShopServiceEntity t = tcsbShopServiceService.get(TcsbShopServiceEntity.class, tcsbShopService.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbShopService, t);
			tcsbShopServiceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商家服务更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	*//**
	 * 商家服务新增页面跳转
	 * 
	 * @return
	 *//*
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbShopServiceEntity tcsbShopService, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopService.getId())) {
			tcsbShopService = tcsbShopServiceService.getEntity(TcsbShopServiceEntity.class, tcsbShopService.getId());
			req.setAttribute("tcsbShopServicePage", tcsbShopService);
		}
		return new ModelAndView("com/tcsb/shopsservice/tcsbShopService-add");
	}
	*//**
	 * 商家服务编辑页面跳转
	 * 
	 * @return
	 *//*
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbShopServiceEntity tcsbShopService, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbShopService.getId())) {
			tcsbShopService = tcsbShopServiceService.getEntity(TcsbShopServiceEntity.class, tcsbShopService.getId());
			req.setAttribute("tcsbShopServicePage", tcsbShopService);
		}
		return new ModelAndView("com/tcsb/shopsservice/tcsbShopService-update");
	}
	
	*//**
	 * 导入功能跳转
	 * 
	 * @return
	 *//*
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbShopServiceController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	*//**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbShopServiceEntity tcsbShopService,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbShopServiceEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbShopService, request.getParameterMap());
		List<TcsbShopServiceEntity> tcsbShopServices = this.tcsbShopServiceService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商家服务");
		modelMap.put(NormalExcelConstants.CLASS,TcsbShopServiceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商家服务列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbShopServices);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	*//**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbShopServiceEntity tcsbShopService,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"商家服务");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbShopServiceEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商家服务列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbShopServiceEntity> listTcsbShopServiceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbShopServiceEntity.class,params);
				for (TcsbShopServiceEntity tcsbShopService : listTcsbShopServiceEntitys) {
					tcsbShopServiceService.save(tcsbShopService);
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
	public List<TcsbShopServiceEntity> list() {
		List<TcsbShopServiceEntity> listTcsbShopServices=tcsbShopServiceService.getList(TcsbShopServiceEntity.class);
		return listTcsbShopServices;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbShopServiceEntity task = tcsbShopServiceService.get(TcsbShopServiceEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbShopServiceEntity tcsbShopService, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopServiceEntity>> failures = validator.validate(tcsbShopService);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopServiceService.save(tcsbShopService);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbShopService.getId();
		URI uri = uriBuilder.path("/rest/tcsbShopServiceController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbShopServiceEntity tcsbShopService) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbShopServiceEntity>> failures = validator.validate(tcsbShopService);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbShopServiceService.saveOrUpdate(tcsbShopService);
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
		tcsbShopServiceService.deleteEntityById(TcsbShopServiceEntity.class, id);
	}*/
}
