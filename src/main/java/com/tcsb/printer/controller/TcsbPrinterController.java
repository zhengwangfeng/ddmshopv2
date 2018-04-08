package com.tcsb.printer.controller;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.foodtype.service.TcsbFoodTypeServiceI;
import com.tcsb.printer.entity.TcsbPrinterEntity;
import com.tcsb.printer.service.TcsbPrinterServiceI;
import com.tcsb.printerfoodfun.entity.TcsbPrinterFoodFunEntity;
import com.tcsb.printerfoodtype.entity.TcsbPrinterFoodTypeEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

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
 * @Description: 打印机设置
 * @author onlineGenerator
 * @date 2017-07-12 11:54:31
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbPrinterController")
public class TcsbPrinterController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbPrinterController.class);

	@Autowired
	private TcsbPrinterServiceI tcsbPrinterService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbFoodTypeServiceI tcsbFoodTypeService;
	


	/**
	 * 打印机设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/printer/tcsbPrinterList");
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
	public void datagrid(TcsbPrinterEntity tcsbPrinter,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbPrinterEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPrinter, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user=getCurrentUser();
			if (!checkAdmin()) {
				cq.add(Restrictions.eq("shopId", user.getShopId()));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbPrinterService.getDataGridReturn(cq, true);
		List<TcsbPrinterEntity> tcsbPrinterEntities = new ArrayList<>();
		 for (Object o : dataGrid.getResults()) {
	            if (o instanceof TcsbPrinterEntity) {
	            	TcsbPrinterEntity cfe = (TcsbPrinterEntity) o;
	            	TcsbPrinterEntity tcsbPrinterEntity = new TcsbPrinterEntity();
	            	try {
						MyBeanUtils.copyBeanNotNull2Bean(cfe, tcsbPrinterEntity);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	if ("0".equals(cfe.getPrintChild())) {
	            		tcsbPrinterEntity.setPrintChild("否");
					}else {
						tcsbPrinterEntity.setPrintChild("是");
					}
	            	if ("0".equals(cfe.getAutoPrint())) {
	            		tcsbPrinterEntity.setAutoPrint("否");
					}else {
						tcsbPrinterEntity.setAutoPrint("是");
					}
	            	tcsbPrinterEntities.add(tcsbPrinterEntity);
	            }
	        }
		 dataGrid.setResults(tcsbPrinterEntities);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 接单更改订单接收状态
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "princtChild")
	@ResponseBody
	public AjaxJson princtChild(TcsbPrinterEntity tcsbPrinter, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "设置套打成功";
		try{
			if (StringUtil.isNotEmpty(tcsbPrinter.getId())) {
				tcsbPrinter = tcsbPrinterService.getEntity(TcsbPrinterEntity.class, tcsbPrinter.getId());
			}
			if ("0".equals(tcsbPrinter.getPrintChild())) {
				tcsbPrinter.setPrintChild("1");
			}else {
				tcsbPrinter.setPrintChild("0");
			}
			tcsbPrinterService.saveOrUpdate(tcsbPrinter);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订单管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 接单更改订单接收状态
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "autoPrint")
	@ResponseBody
	public AjaxJson autoPrint(TcsbPrinterEntity tcsbPrinter, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "设置套打成功";
		try{
			if (StringUtil.isNotEmpty(tcsbPrinter.getId())) {
				tcsbPrinter = tcsbPrinterService.getEntity(TcsbPrinterEntity.class, tcsbPrinter.getId());
			}
			if ("0".equals(tcsbPrinter.getAutoPrint())) {
				tcsbPrinter.setAutoPrint("1");
			}else {
				tcsbPrinter.setAutoPrint("0");
			}
			tcsbPrinterService.saveOrUpdate(tcsbPrinter);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订单管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 获取食物所属的所有打印机
	 * 
	 * @return
	 */
	@RequestMapping(params = "getPrinterByFoodId")
	@ResponseBody
	public AjaxJson getPrinterByFoodId(String foodId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "查询成功";
		TSUser tsUser = getCurrentUser();
		try{
			List<Map<String, Object>> list = systemService.findForJdbc("select p.print_index from tcsb_printer_food_fun f left join tcsb_printer p on f.printer_id = p.id where food_id = ? and shop_id = ? ", foodId,tsUser.getShopId());
			j.setMsg(message);
			j.setObj(list);
			j.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			message = "查询失败";
			j.setMsg(message);
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		return j;
	}
	/**
	 * 删除打印机设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbPrinterEntity tcsbPrinter, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbPrinter = systemService.getEntity(TcsbPrinterEntity.class, tcsbPrinter.getId());
		message = "打印机设置删除成功";
		try{
			tcsbPrinterService.delete(tcsbPrinter);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "打印机设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除打印机设置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "打印机设置删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbPrinterEntity tcsbPrinter = systemService.getEntity(TcsbPrinterEntity.class, 
				id
				);
				tcsbPrinterService.delete(tcsbPrinter);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "打印机设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加打印机设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbPrinterEntity tcsbPrinter, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "打印机设置添加成功";
		try{
			List<TcsbPrinterEntity> list = tcsbPrinter.getPrintList();
			for (TcsbPrinterEntity tcsbPrinterEntity : list) {
				TSUser user=getCurrentUser();
				tcsbPrinterEntity.setShopId(user.getShopId());	
				tcsbPrinterEntity.setPrintChild("0");
				//设置默认打印为一份 
				tcsbPrinterEntity.setPrintNum(1);
				tcsbPrinterEntity.setAutoPrint("0");
				tcsbPrinterService.save(tcsbPrinterEntity);
			}
			//tcsbPrinterService.save(tcsbPrinter);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "打印机设置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新打印机设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbPrinterEntity tcsbPrinter, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "打印机设置更新成功";
		TcsbPrinterEntity t = tcsbPrinterService.get(TcsbPrinterEntity.class, tcsbPrinter.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbPrinter, t);
			tcsbPrinterService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "打印机设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 打印机设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbPrinterEntity tcsbPrinter, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbPrinter.getId())) {
			tcsbPrinter = tcsbPrinterService.getEntity(TcsbPrinterEntity.class, tcsbPrinter.getId());
			req.setAttribute("tcsbPrinterPage", tcsbPrinter);
		}
		return new ModelAndView("com/tcsb/printer/tcsbPrinter-add");
	}
	/**
	 * 打印机设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbPrinterEntity tcsbPrinter, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbPrinter.getId())) {
			tcsbPrinter = tcsbPrinterService.getEntity(TcsbPrinterEntity.class, tcsbPrinter.getId());
			req.setAttribute("tcsbPrinterPage", tcsbPrinter);
		}
		return new ModelAndView("com/tcsb/printer/tcsbPrinter-update");
	}
	
	/**
	 * 打印机菜品列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "foodtype")
	public ModelAndView foodtype(HttpServletRequest request,String printerId) {
		request.setAttribute("printerId", printerId);
		return new ModelAndView("com/tcsb/printer/printSet");
	}
	
	/**
	 * 打印机食物列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "foodFun")
	public ModelAndView foodFun(HttpServletRequest request,String printerId) {
		request.setAttribute("printerId", printerId);
		return new ModelAndView("com/tcsb/printer/printSet");
	}
	/**
	 * 设置分类打印权限
	 * 
	 * @param role
	 * @param request
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "setAuthority")  
	@ResponseBody
	public List<ComboTree> setAuthority(TcsbPrinterEntity tcsbPrinterEntity,
			HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodTypeEntity.class);
		TSUser tsUser = getCurrentUser();
		/*if (comboTree.getId() != null) {
			cq.eq("TSFunction.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSFunction");
		}*/
		cq.add(Restrictions.eq("shopId", tsUser.getShopId()));
		cq.add();
		List<TcsbFoodTypeEntity> foodTypeList = systemService.getListByCriteriaQuery(
				cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		String printerId = request.getParameter("printerId");
		List<TcsbFoodTypeEntity> loginActionlist = new ArrayList<TcsbFoodTypeEntity>();// 已有权限菜单
		tcsbPrinterEntity = this.systemService.get(TcsbPrinterEntity.class, printerId);
		if (tcsbPrinterEntity != null) {  
			List<TcsbPrinterFoodTypeEntity> printerFoodTypeList = systemService.findByProperty(TcsbPrinterFoodTypeEntity.class, "printerId",printerId);
			if (printerFoodTypeList.size() > 0) {
				for (TcsbPrinterFoodTypeEntity tcsbPrinterFoodTypeEntity : printerFoodTypeList) {
					TcsbFoodTypeEntity  tcsbFoodTypeEntity = tcsbFoodTypeService.get(TcsbFoodTypeEntity.class, tcsbPrinterFoodTypeEntity.getFoodTypeId());
					loginActionlist.add(tcsbFoodTypeEntity);
				}
			}
			printerFoodTypeList.clear();
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","name", "null");
		comboTrees = systemService.ComboTree(foodTypeList, comboTreeModel,loginActionlist, false);
		MutiLangUtil.setMutiComboTree(comboTrees);
		foodTypeList.clear();
		loginActionlist.clear();
		return comboTrees;
	}
	
	/**
	 * 设置食物打印权限
	 * 
	 * @param role
	 * @param request
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "setAuthority2")  
	@ResponseBody
	public List<ComboTree> setAuthority2(TcsbPrinterEntity tcsbPrinterEntity,
			HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TcsbFoodTypeEntity.class);
		TSUser tsUser = getCurrentUser();
		cq.add(Restrictions.eq("shopId", tsUser.getShopId()));
		cq.add();
		//获取该店铺的所有食物信息
		List<TcsbFoodEntity> foodList = systemService.getListByCriteriaQuery(
				cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		String printerId = request.getParameter("printerId");
		//设置 已有权限菜单
		List<TcsbFoodEntity> loginActionlist = new ArrayList<TcsbFoodEntity>();
		tcsbPrinterEntity = this.systemService.get(TcsbPrinterEntity.class, printerId);
		if (tcsbPrinterEntity != null) {  
			//根据打印机获取打印机关联表信息
			List<TcsbPrinterFoodFunEntity> printerFoodFunList = systemService.findByProperty(TcsbPrinterFoodFunEntity.class, "printerId",printerId);
			if (printerFoodFunList.size() > 0) {
				for (TcsbPrinterFoodFunEntity tcsbPrinterFoodFunEntity : printerFoodFunList) {
					TcsbFoodEntity  tcsbFoodEntity = systemService.get(TcsbFoodEntity.class, tcsbPrinterFoodFunEntity.getFoodId());
					loginActionlist.add(tcsbFoodEntity);
				}
			}
			printerFoodFunList.clear();
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","name", "TcsbFoodEntitys");
		comboTrees = systemService.ComboTree(foodList, comboTreeModel,loginActionlist, true);
		MutiLangUtil.setMutiComboTree(comboTrees);
		foodList.clear();
		loginActionlist.clear();
		return comboTrees;
	}
	
	/**
	 * 更新菜品分类权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateAuthority")
	@ResponseBody
	public AjaxJson updateAuthority(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String printerId = request.getParameter("printerId");
			String printfunction = request.getParameter("printfunctions");
			TcsbPrinterEntity tcsbPrinterEntity = this.systemService.get(TcsbPrinterEntity.class, printerId);
			List<TcsbPrinterFoodTypeEntity> PrinterFoodTypeList = systemService
					.findByProperty(TcsbPrinterFoodTypeEntity.class, "printerId",
							printerId);
			//map旧的权限列表
			Map<String, TcsbPrinterFoodTypeEntity> map = new HashMap<String, TcsbPrinterFoodTypeEntity>();
			for (TcsbPrinterFoodTypeEntity printerFoodType : PrinterFoodTypeList) {
				//保存食物分类id和打印分类信息
				map.put(printerFoodType.getFoodTypeId(), printerFoodType);
			}
			String[] printFunctions = printfunction.split(",");
			//set最新的权限列表
			Set<String> set = new HashSet<String>();
			for (String s : printFunctions) {
				set.add(s);
			}
			updateCompare(set, tcsbPrinterEntity, map);
			j.setMsg("权限更新成功");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("权限更新失败");
		}
		return j;
	}
	
	/**
	 * 更新食物权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateAuthority2")
	@ResponseBody
	public AjaxJson updateAuthority2(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String printerId = request.getParameter("printerId");
			String printfunction = request.getParameter("printfunctions");
			TcsbPrinterEntity tcsbPrinterEntity = this.systemService.get(TcsbPrinterEntity.class, printerId);
			List<TcsbPrinterFoodFunEntity> printerFoodFunList = systemService
					.findByProperty(TcsbPrinterFoodFunEntity.class, "printerId",
							printerId);
			//map旧的权限列表
			Map<String, TcsbPrinterFoodFunEntity> map = new HashMap<String, TcsbPrinterFoodFunEntity>();
			for (TcsbPrinterFoodFunEntity printerFoodFun : printerFoodFunList) {
				//保存食物id和打印食物权限信息
				map.put(printerFoodFun.getFoodId(), printerFoodFun);
			}
			String[] printFunctions = printfunction.split(",");
			//set最新的权限列表
			Set<String> set = new HashSet<String>();
			for (String s : printFunctions) {
				set.add(s);
			}
			updateCompare2(set, tcsbPrinterEntity, map);
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
	 * @param tcsbPrinterEntity
	 *            当前打印机
	 * @param map
	 *            旧的权限列表
	 */
	private void updateCompare(Set<String> set, TcsbPrinterEntity tcsbPrinterEntity,
			Map<String, TcsbPrinterFoodTypeEntity> map) {
		List<TcsbPrinterFoodTypeEntity> entitys = new ArrayList<TcsbPrinterFoodTypeEntity>();
		List<TcsbPrinterFoodTypeEntity> deleteEntitys = new ArrayList<TcsbPrinterFoodTypeEntity>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				TcsbPrinterFoodTypeEntity rf = new TcsbPrinterFoodTypeEntity();
				TcsbFoodTypeEntity f = this.systemService.get(TcsbFoodTypeEntity.class, s);
				rf.setFoodTypeId(f.getId());
				rf.setPrinterId(tcsbPrinterEntity.getId());
				entitys.add(rf);
			}
		}
		Collection<TcsbPrinterFoodTypeEntity> collection = map.values();
		Iterator<TcsbPrinterFoodTypeEntity> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
		}
		systemService.batchSave(entitys);
		systemService.deleteAllEntitie(deleteEntitys);

	}
	
	/**
	 * 权限比较
	 * 
	 * @param set
	 *            最新的权限列表
	 * @param tcsbPrinterEntity
	 *            当前打印机
	 * @param map
	 *            旧的权限列表
	 */
	private void updateCompare2(Set<String> set, TcsbPrinterEntity tcsbPrinterEntity,
			Map<String, TcsbPrinterFoodFunEntity> map) {
		List<TcsbPrinterFoodFunEntity> entitys = new ArrayList<TcsbPrinterFoodFunEntity>();
		List<TcsbPrinterFoodFunEntity> deleteEntitys = new ArrayList<TcsbPrinterFoodFunEntity>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				TcsbPrinterFoodFunEntity rf = new TcsbPrinterFoodFunEntity();
				TcsbFoodEntity f = this.systemService.get(TcsbFoodEntity.class, s);
				if(StringUtil.isNotEmpty(f)){
					rf.setFoodId(f.getId());
					rf.setPrinterId(tcsbPrinterEntity.getId());
					entitys.add(rf);
				}
				
			}
		}
		Collection<TcsbPrinterFoodFunEntity> collection = map.values();
		Iterator<TcsbPrinterFoodFunEntity> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
		}
		systemService.batchSave(entitys);
		systemService.deleteAllEntitie(deleteEntitys);

	}
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbPrinterController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbPrinterEntity tcsbPrinter,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbPrinterEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbPrinter, request.getParameterMap());
		List<TcsbPrinterEntity> tcsbPrinters = this.tcsbPrinterService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"打印机设置");
		modelMap.put(NormalExcelConstants.CLASS,TcsbPrinterEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("打印机设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbPrinters);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbPrinterEntity tcsbPrinter,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"打印机设置");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbPrinterEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("打印机设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbPrinterEntity> listTcsbPrinterEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbPrinterEntity.class,params);
				for (TcsbPrinterEntity tcsbPrinter : listTcsbPrinterEntitys) {
					tcsbPrinterService.save(tcsbPrinter);
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
	public List<TcsbPrinterEntity> list() {
		List<TcsbPrinterEntity> listTcsbPrinters=tcsbPrinterService.getList(TcsbPrinterEntity.class);
		return listTcsbPrinters;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbPrinterEntity task = tcsbPrinterService.get(TcsbPrinterEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbPrinterEntity tcsbPrinter, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbPrinterEntity>> failures = validator.validate(tcsbPrinter);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbPrinterService.save(tcsbPrinter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbPrinter.getId();
		URI uri = uriBuilder.path("/rest/tcsbPrinterController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbPrinterEntity tcsbPrinter) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbPrinterEntity>> failures = validator.validate(tcsbPrinter);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbPrinterService.saveOrUpdate(tcsbPrinter);
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
		tcsbPrinterService.deleteEntityById(TcsbPrinterEntity.class, id);
	}
}
