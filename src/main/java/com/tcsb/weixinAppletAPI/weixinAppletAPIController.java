package com.tcsb.weixinAppletAPI;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.antlr.grammar.v3.ANTLRv3Parser.element_return;
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
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ListSorter;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.core.util.NumberComparator3;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFunction;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.sun.accessibility.internal.resources.accessibility;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.food.vo.FoodTypeVO;
import com.tcsb.food.vo.FoodVO;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.foodtype.service.TcsbFoodTypeServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcar.service.TcsbShopCarServiceI;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.shopcaritem.service.TcsbShopCarItemServiceI;
import com.tcsb.tcsbfoodfunction.entity.TcsbFoodFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.vo.FoodTastePageVo;
import com.tcsb.tcsbfoodtastefunction.vo.FoodTasteVo;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;

/**
 * @Title: Controller
 * @Description: 美食
 * @author onlineGenerator
 * @date 2017-02-28 16:32:34
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/weixinAppletAPIController")
public class weixinAppletAPIController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(weixinAppletAPIController.class);

	@Autowired
	private TcsbFoodServiceI tcsbFoodService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbFoodTypeServiceI tcsbFoodTypeService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbShopCarServiceI tcsbShopCarService;
	@Autowired
	private TcsbShopCarItemServiceI tcsbShopCarItemService;


	
	
	
	
	
	
	
}
