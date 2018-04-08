package com.tcsb.associatorstatistics.controller;
import com.tcsb.associatorstatistics.entity.TcsbAssociatorStatisticsEntity;
import com.tcsb.associatorstatistics.service.TcsbAssociatorStatisticsServiceI;
import com.tcsb.associatorstatistics.vo.UserFoodStatisticsVo;
import com.tcsb.associatorstatistics.vo.WeixinUserStatiticsVo;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.vo.FoodVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopsmsservice.entity.TcsbShopSmsServiceEntity;
import com.tcsb.shopsmsservice.service.TcsbShopSmsServiceServiceI;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.weixin.weixinuser.entity.WeixinUserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.text.DecimalFormat;
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
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SaleSmsClient;
import org.jeecgframework.core.util.SellerSmsClient;
import org.jeecgframework.core.util.SmsClient;

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
 * @Description: 会员统计已消费
 * @author onlineGenerator
 * @date 2017-05-04 16:07:56
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbAssociatorStatisticsController")
public class TcsbAssociatorStatisticsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbAssociatorStatisticsController.class);

	@Autowired
	private TcsbAssociatorStatisticsServiceI tcsbAssociatorStatisticsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbShopSmsServiceServiceI tcsbShopSmsServiceService;
	


	/**
	 * 会员统计首页 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request,HttpSession session) {
		
		TSUser user = getCurrentUser();
		//获取店铺未消费的用户
		CriteriaQuery cq = new CriteriaQuery(WeixinUserEntity.class);
		cq.add(Restrictions.eq("isSale", "0"));
		cq.add(Restrictions.eq("shopId",user.getShopId()));
		cq.add(Restrictions.isNotNull("mobile"));
		List<WeixinUserEntity> weixinUserEntityList = tcsbAssociatorStatisticsService.getListByCriteriaQuery(cq, false);
		//检测用户是否有消费
		for (WeixinUserEntity weixinUserEntity : weixinUserEntityList) {
			int point = 0;
			//根据用户openid获取用户订单
			String userorderhql = "from TcsbUserOrderEntity where userId='"+weixinUserEntity.getOpenid()+"'";
			List<TcsbUserOrderEntity> TcsbUserOrderList = tcsbAssociatorStatisticsService.findByQueryString(userorderhql);
			//检查该orderid是否是付款
			for (TcsbUserOrderEntity tcsbUserOrderEntity : TcsbUserOrderList) {
				String orderHql = "from TcsbOrderEntity where id='"+tcsbUserOrderEntity.getOrderId()+"' and shopId='"+user.getShopId()+"'";
				TcsbOrderEntity TcsbOrder =  tcsbAssociatorStatisticsService.singleResult(orderHql);
				if(StringUtil.isNotEmpty(TcsbOrder)){
					if(TcsbOrder.getPayStatus().equals("1")){
						point = 1;
						break;
					}
				}
			}
			if(point == 1){
				//用户有消费过
				weixinUserEntity.setIsSale("1");
				tcsbAssociatorStatisticsService.saveOrUpdate(weixinUserEntity);
			}
				
		}
		return new ModelAndView("com/tcsb/associatorstatistics/tcsbAssociatorStatisticsList");
	}
	
	
	
	/**
	 * easyui AJAX请求数据
	 * 获取会员消费首页 消费信息
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,HttpSession session) throws IllegalAccessException, InvocationTargetException {
		
		TSUser user = getCurrentUser();
		CriteriaQuery cq = new CriteriaQuery(WeixinUserEntity.class);
		cq.setCurPage(dataGrid.getPage());
		cq.setPageSize(dataGrid.getRows());
		//cq.eq("isSale", "1");//有消费的会员用户
		//cq.eq("shopId",tcsbShopEntity.getId());
		cq.add(Restrictions.eq("isSale", "1"));
		cq.add(Restrictions.eq("shopId",user.getShopId()));
		cq.add(Restrictions.isNotNull("mobile"));
		String querykey = request.getParameter("querykey");
		if(StringUtil.isNotEmpty(querykey)){
			cq.or(Restrictions.like("nickname", "%"+querykey+"%"), Restrictions.like("mobile", "%"+querykey+"%"));
		}
		List<WeixinUserEntity> weixinUserEntityList = tcsbAssociatorStatisticsService.getListByCriteriaQuery(cq, true);
		//获取商店的所有会员用户
		List<WeixinUserEntity> weixinUserEntityTotalList = tcsbAssociatorStatisticsService.findByQueryString("from WeixinUserEntity where shopId='"+user.getShopId()+"' and mobile!='' and issale=1");
		//根据会员情况获取会员的消费信息
		List<WeixinUserStatiticsVo> weixinUserStatiticsVoList = new ArrayList<>();
		WeixinUserStatiticsVo weixinUserStatiticsVo;
		for (WeixinUserEntity weixinUserEntity : weixinUserEntityList) {
			double AllsaleMoney = 0.00;//初始化消费金额
			int saleCount = 0;//初始化消费次数
			weixinUserStatiticsVo = new WeixinUserStatiticsVo();
			BeanUtils.copyProperties(weixinUserStatiticsVo, weixinUserEntity);
			//获取会员的消费订单
			String userorderhql = "from TcsbUserOrderEntity where userId='"+weixinUserEntity.getOpenid()+"'";
			List<TcsbUserOrderEntity> TcsbUserOrderEntityList = tcsbAssociatorStatisticsService.findByQueryString(userorderhql);
			//遍历用户的每个订单编号
			for (TcsbUserOrderEntity tcsbUserOrderEntity : TcsbUserOrderEntityList) {
				//根据orderid获取订单详情
				String orderHql = "from TcsbOrderEntity where id='"+tcsbUserOrderEntity.getOrderId()+"' and shopId='"+user.getShopId()+"'";
				TcsbOrderEntity tcsbOrderEntity = tcsbAssociatorStatisticsService.singleResult(orderHql);
				if(tcsbOrderEntity == null){
					AllsaleMoney += 0;
					saleCount += 0;
				}else{
					if(tcsbOrderEntity.getPayStatus().equals("1")){
						AllsaleMoney += (tcsbOrderEntity.getOnlinePrice()+tcsbOrderEntity.getOfflinePrice());
						saleCount += 1;
					}else{
						AllsaleMoney += 0;
						saleCount += 0;
					}
					
				}
			}
			//计算当前用户的平均消费额
			float size = (float)AllsaleMoney/saleCount;
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
			String filesize = df.format(size);//返回的是String类型的
			weixinUserStatiticsVo.setSaleCount(saleCount);//消费次数
			weixinUserStatiticsVo.setAvgSaleMoney(filesize);//均消费金额
			//隐藏当前用户的手机号中间4位
			//weixinUserStatiticsVo.setMobile(weixinUserStatiticsVo.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
			//获取用户最近一次消费时间
			String getLastTimeSql = "SELECT * from tcsb_user_order where user_id ='"+weixinUserEntity.getOpenid()+"' order by create_date desc limit 1";
			List<Object> saleLastTime = tcsbAssociatorStatisticsService.findListbySql(getLastTimeSql);
			if(saleLastTime.size() == 0){
				weixinUserStatiticsVo.setLastTime(null);
			}else{
				String saleLastTimes = "";
				for (Iterator iterator = saleLastTime.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					saleLastTimes = object[8].toString();
				}
				weixinUserStatiticsVo.setLastTime(saleLastTimes);
			}
			weixinUserStatiticsVoList.add(weixinUserStatiticsVo);
		
		}
		dataGrid.setTotal(weixinUserEntityTotalList.size());
		dataGrid.setResults(weixinUserStatiticsVoList);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 跳转会员消费详情页面
	 * @param openid
	 * @param request
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "detaillist")
	public ModelAndView detaillist(String openid,HttpServletRequest request,ModelMap modelMap,HttpSession session) {
		modelMap.addAttribute("openid", openid);
		TSUser user = getCurrentUser();
		//获取用户信息
		String hql = "from WeixinUserEntity where openid='"+openid+"' and shopId='"+user.getShopId()+"'";
		WeixinUserEntity weixinUserEntity = tcsbAssociatorStatisticsService.singleResult(hql);
		modelMap.addAttribute("weixinUserEntity", weixinUserEntity);
		return new ModelAndView("com/tcsb/associatorstatistics/tcsbAssociatorStatisticsDetailList");
	}
	
	

	
	/**
	 * 会员消费详情
	 * @param openid
	 * @param tcsbAssociatorStatistics
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param session
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(params = "detaildatagrid")
	public void detaildatagrid(String openid,TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,HttpSession session) throws IllegalAccessException, InvocationTargetException {
		//获取登入用户信息
		TSUser user = getCurrentUser();
		StringBuilder sql = new StringBuilder();
		sql.append("select wu.ID AS id,format(sum(i.count),0) as count,p.ID AS orderParentId,d.desk_name as deskName,format(sum(i.price * i.count),1) allSaleMoney,max(o.create_date) AS saleTime ");
		sql.append("from tcsb_order_parent p ");
		sql.append("LEFT JOIN tcsb_desk d ON d.ID = p.desk_id ");
		sql.append("LEFT JOIN tcsb_order o on p.ID = o.order_parent_id ");
		sql.append("LEFT JOIN tcsb_order_item i on o.ID = i.order_id ");
		sql.append("LEFT JOIN tcsb_user_order uo on p.ID = uo.order_parent_id ");
		sql.append("LEFT JOIN weixin_user wu on wu.openid = uo.user_id ");
		sql.append("LEFT JOIN tcsb_weixin_user u on uo.user_id=u.openid ");
		sql.append("where wu.shop_id='"+user.getShopId()+"' AND p.shop_id = '"+user.getShopId()+"' ");
		sql.append("and wu.openid='"+openid+"' ");
		sql.append("and o.pay_status='1' GROUP BY p.ID");

		List<Map<String,Object>> list = tcsbAssociatorStatisticsService.findForJdbc(sql.toString());
		List<Map<String,Object>> tcsbAssociatorBigdataList = list.subList((dataGrid.getPage()-1)*dataGrid.getRows(),(dataGrid.getPage())*dataGrid.getRows()>list.size()?list.size():(dataGrid.getPage())*dataGrid.getRows());
		dataGrid.setTotal(list.size());
		dataGrid.setResults(tcsbAssociatorBigdataList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	
	
	
	@RequestMapping(params = "foodlist")
	public ModelAndView foodlist(String orderParentId,String count,String allSaleMoney,HttpServletRequest request,ModelMap modelMap) {
		modelMap.addAttribute("count", count);
		modelMap.addAttribute("allSaleMoney", allSaleMoney);
		modelMap.addAttribute("orderParentId", orderParentId);
		return new ModelAndView("com/tcsb/associatorstatistics/tcsbAssociatorStatisticsFoodDetailList");
	}

	@RequestMapping(params = "userfooddetaillist")
	public ModelAndView userfooddetaillist(String openid,HttpServletRequest request,ModelMap modelMap,HttpSession session) {
		modelMap.addAttribute("openid", openid);
//		TSUser user = getCurrentUser();
//		//获取用户信息
//		String hql = "from WeixinUserEntity where openid='"+openid+"' and shopId='"+user.getShopId()+"'";
//		WeixinUserEntity weixinUserEntity = tcsbAssociatorStatisticsService.singleResult(hql);
//		modelMap.addAttribute("weixinUserEntity", weixinUserEntity);
		return new ModelAndView("com/tcsb/associatorstatistics/tcsbAssociatorStatisticsUserFoodDetailList");
	}
	

	
	
	/**
	 * 用户菜品消费分析
	 * @param openid
	 * @param tcsbAssociatorStatistics
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param session
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(params = "userfooddetaildatagrid")
	public void userfooddetaildatagrid(String openid,TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,HttpSession session) throws IllegalAccessException, InvocationTargetException {
		TSUser user = getCurrentUser();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT f.ID AS foodId,f. NAME AS foodname,FORMAT(count(p.ID),0) as saleCount,FORMAT(sum(i.count),0) as buyCount, ");
		sql.append("FORMAT(sum(i.count)/count(p.ID),1) as avgSaleCount ");
		sql.append("FROM tcsb_food f ");
		sql.append("LEFT JOIN tcsb_order_item i ON f.ID = i.food_id ");
		sql.append("LEFT JOIN tcsb_order o ON o.ID = i.order_id ");
		sql.append("LEFT JOIN tcsb_order_parent p ON p.ID = o.order_parent_id ");
		sql.append("LEFT JOIN tcsb_user_order uo ON uo.order_parent_id = p.ID ");
		sql.append("WHERE ");
		sql.append("p.shop_id = '"+user.getShopId()+"' ");
		sql.append("and o.pay_status = '1' and uo.user_id ='"+openid+"' ");
		sql.append("GROUP BY f.ID ");

		List<Map<String,Object>> list = tcsbAssociatorStatisticsService.findForJdbc(sql.toString());
		List<Map<String,Object>> tcsbAssociatorStatisticsList = list.subList((dataGrid.getPage()-1)*dataGrid.getRows(),(dataGrid.getPage())*dataGrid.getRows()>list.size()?list.size():(dataGrid.getPage())*dataGrid.getRows());
		dataGrid.setTotal(list.size());
		dataGrid.setResults(tcsbAssociatorStatisticsList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	@RequestMapping(params = "fooddetaildatagrid")
	public void fooddetaildatagrid(String orderParentId,TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,HttpSession session) throws IllegalAccessException, InvocationTargetException {
		TSUser user = getCurrentUser();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT f.ID as id,format(sum(i.count),0) as num,f.name as name,f.price as price FROM tcsb_order_parent p ");
		sql.append("LEFT JOIN tcsb_order o ON p.ID = o.order_parent_id ");
		sql.append("LEFT JOIN tcsb_order_item i ON o.ID = i.order_id ");
		sql.append("LEFT JOIN tcsb_food f on f.ID = i.food_id ");
		sql.append("WHERE ");
		sql.append("p.shop_id='"+user.getShopId()+"' ");
		sql.append("and p.ID = '"+orderParentId+"' ");
		sql.append("AND o.pay_status = '1' ");
		sql.append("GROUP BY f.ID");

		List<Map<String,Object>> list = tcsbAssociatorStatisticsService.findForJdbc(sql.toString());
		List<Map<String,Object>> tcsbAssociatorStatisticsList = list.subList((dataGrid.getPage()-1)*dataGrid.getRows(),(dataGrid.getPage())*dataGrid.getRows()>list.size()?list.size():(dataGrid.getPage())*dataGrid.getRows());
		dataGrid.setTotal(list.size());
		dataGrid.setResults(tcsbAssociatorStatisticsList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 短息营销，短信内容编辑窗口
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "smsPage")
	public ModelAndView smsPage(HttpServletRequest request,String ids,ModelMap modelMap) {
		modelMap.addAttribute("ids", ids);
		return new ModelAndView("com/tcsb/associatorstatistics/tcsbSaleStatistics-add");
	}
	
	
	/**
	 * 发送营销短信
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "send")
	@ResponseBody
	public AjaxJson send(String ids,String content, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "信息发送成功";
		try{	
			if(StringUtil.isEmpty(ids)){
				message = "信息发送失败";
			}else{
				
				TSUser user = getCurrentUser();
				//获取用户店铺信息
				TcsbShopEntity tcsbShop= tcsbShopService.getTcsbShopEntityByUserid(user.getId());
				String tcsbShophql = "from TcsbShopSmsServiceEntity where shopId='"+tcsbShop.getId()+"'";
				TcsbShopSmsServiceEntity TcsbShopSmsService = tcsbAssociatorStatisticsService.singleResult(tcsbShophql);
				if(StringUtil.isNotEmpty(TcsbShopSmsService)){
					String[] idarray = ids.split(",");
					if(TcsbShopSmsService.getCount()>= idarray.length){
						//短信条数足够
						int count = 0;//初始化发送条数
						for (String string : idarray) {
							WeixinUserEntity weixinUserEntity = tcsbAssociatorStatisticsService.get(WeixinUserEntity.class, string);
							/*if(StringUtil.isEmpty(weixinUserEntity.getMobile())){
								message = "信息发送失败";
							}else{
								message = "信息发送成功";
								SaleSmsClient.sendMessageForContent(weixinUserEntity.getMobile(),content);
								count++;
							}*/
						}
						//更新短信剩余条数
						TcsbShopSmsService.setCount(TcsbShopSmsService.getCount()-count);
						tcsbAssociatorStatisticsService.saveOrUpdate(TcsbShopSmsService);
					}else{
						message = "信息剩余条不足"+idarray.length+"条";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "信息发送失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	@RequestMapping(params = "getSmsCount")
	@ResponseBody
	public int getSmsCount(HttpServletRequest request) {
		TSUser user = getCurrentUser();
		//获取用户店铺信息
		TcsbShopEntity tcsbShop= tcsbShopService.getTcsbShopEntityByUserid(user.getId());
		String tcsbShophql = "";
		if(StringUtil.isNotEmpty(tcsbShop)){
			tcsbShophql = "from TcsbShopSmsServiceEntity where shopId='"+tcsbShop.getId()+"'";
			TcsbShopSmsServiceEntity TcsbShopSmsService = tcsbAssociatorStatisticsService.singleResult(tcsbShophql);
			if(StringUtil.isNotEmpty(TcsbShopSmsService)){
				return TcsbShopSmsService.getCount();
			}else{
				TcsbShopSmsServiceEntity tcsbShopSmsServiceEntity = new TcsbShopSmsServiceEntity();
				tcsbShopSmsServiceEntity.setCount(0);
				tcsbShopSmsServiceEntity.setShopId(tcsbShop.getId());
				try {
					tcsbShopSmsServiceService.save(tcsbShopSmsServiceEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	
	
	/**
	 * 删除会员统计已消费
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tcsbAssociatorStatistics = systemService.getEntity(TcsbAssociatorStatisticsEntity.class, tcsbAssociatorStatistics.getId());
		message = "会员统计已消费删除成功";
		try{
			tcsbAssociatorStatisticsService.delete(tcsbAssociatorStatistics);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "会员统计已消费删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除会员统计已消费
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "会员统计已消费删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics = systemService.getEntity(TcsbAssociatorStatisticsEntity.class, 
				id
				);
				tcsbAssociatorStatisticsService.delete(tcsbAssociatorStatistics);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "会员统计已消费删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加会员统计已消费
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "会员统计已消费添加成功";
		try{
			tcsbAssociatorStatisticsService.save(tcsbAssociatorStatistics);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "会员统计已消费添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新会员统计已消费
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "会员统计已消费更新成功";
		TcsbAssociatorStatisticsEntity t = tcsbAssociatorStatisticsService.get(TcsbAssociatorStatisticsEntity.class, tcsbAssociatorStatistics.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tcsbAssociatorStatistics, t);
			tcsbAssociatorStatisticsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "会员统计已消费更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 会员统计已消费新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbAssociatorStatistics.getId())) {
			tcsbAssociatorStatistics = tcsbAssociatorStatisticsService.getEntity(TcsbAssociatorStatisticsEntity.class, tcsbAssociatorStatistics.getId());
			req.setAttribute("tcsbAssociatorStatisticsPage", tcsbAssociatorStatistics);
		}
		return new ModelAndView("com/tcsb/associatorstatistics/tcsbAssociatorStatistics-add");
	}
	/**
	 * 会员统计已消费编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbAssociatorStatistics.getId())) {
			tcsbAssociatorStatistics = tcsbAssociatorStatisticsService.getEntity(TcsbAssociatorStatisticsEntity.class, tcsbAssociatorStatistics.getId());
			req.setAttribute("tcsbAssociatorStatisticsPage", tcsbAssociatorStatistics);
		}
		return new ModelAndView("com/tcsb/associatorstatistics/tcsbAssociatorStatistics-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tcsbAssociatorStatisticsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TcsbAssociatorStatisticsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbAssociatorStatistics, request.getParameterMap());
		List<TcsbAssociatorStatisticsEntity> tcsbAssociatorStatisticss = this.tcsbAssociatorStatisticsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"会员统计已消费");
		modelMap.put(NormalExcelConstants.CLASS,TcsbAssociatorStatisticsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("会员统计已消费列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tcsbAssociatorStatisticss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"会员统计已消费");
    	modelMap.put(NormalExcelConstants.CLASS,TcsbAssociatorStatisticsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("会员统计已消费列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TcsbAssociatorStatisticsEntity> listTcsbAssociatorStatisticsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TcsbAssociatorStatisticsEntity.class,params);
				for (TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics : listTcsbAssociatorStatisticsEntitys) {
					tcsbAssociatorStatisticsService.save(tcsbAssociatorStatistics);
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
	public List<TcsbAssociatorStatisticsEntity> list() {
		List<TcsbAssociatorStatisticsEntity> listTcsbAssociatorStatisticss=tcsbAssociatorStatisticsService.getList(TcsbAssociatorStatisticsEntity.class);
		return listTcsbAssociatorStatisticss;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbAssociatorStatisticsEntity task = tcsbAssociatorStatisticsService.get(TcsbAssociatorStatisticsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbAssociatorStatisticsEntity>> failures = validator.validate(tcsbAssociatorStatistics);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbAssociatorStatisticsService.save(tcsbAssociatorStatistics);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbAssociatorStatistics.getId();
		URI uri = uriBuilder.path("/rest/tcsbAssociatorStatisticsController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbAssociatorStatisticsEntity tcsbAssociatorStatistics) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbAssociatorStatisticsEntity>> failures = validator.validate(tcsbAssociatorStatistics);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tcsbAssociatorStatisticsService.saveOrUpdate(tcsbAssociatorStatistics);
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
		tcsbAssociatorStatisticsService.deleteEntityById(TcsbAssociatorStatisticsEntity.class, id);
	}
}
