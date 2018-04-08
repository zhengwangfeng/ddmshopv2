/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月9日 下午2:57:40
 */
package com.applet.shopservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.NumberComparator4;
import org.jeecgframework.core.util.NumberComparator5;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.callservice.entity.TcsbCallServiceEntity;
import com.tcsb.service.entity.TcsbServiceEntity;
import com.tcsb.service.service.TcsbServiceServiceI;
import com.tcsb.shopsservice.entity.TcsbShopServiceEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月9日 下午2:57:40
 */
@RequestMapping("/appletShopService")
@Controller
public class AppletShopServiceController extends BaseController{
	
	@Autowired
	private TcsbServiceServiceI tcsbServiceService;
	
	
	/**
	 * 获取服务项
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/getService")
	@ResponseBody
	public AjaxJsonApi getService(String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		
		List<ShopServiceVo> shopServiceVoList = new ArrayList<>();
		ShopServiceVo shopServiceVo;
		List<TcsbShopServiceEntity> roleFunctionList = tcsbServiceService.findByProperty(TcsbShopServiceEntity.class, "tcsbShopEntity.id",shopId);
		List<TcsbServiceEntity> loginActionlist = new ArrayList<TcsbServiceEntity>();// 已有权限菜单
		if (roleFunctionList.size() > 0) {
			for (TcsbShopServiceEntity roleFunction : roleFunctionList) {
				TcsbServiceEntity function = (TcsbServiceEntity) roleFunction.getTcsbServiceEntity();
				if(StringUtil.isEmpty(function.getTcsbServiceEntity())){
					shopServiceVo = new ShopServiceVo();
					shopServiceVo.setParentName(function.getName());
					shopServiceVo.setParentid(function.getId());
					if(StringUtil.isEmpty(function.getStateorder())){
						shopServiceVo.setStateorder("0");
					}else{
						shopServiceVo.setStateorder(function.getStateorder());
					}
					shopServiceVoList.add(shopServiceVo);
				}
				loginActionlist.add(function);
			}
		}
		Collections.sort(shopServiceVoList, new NumberComparator5());
		Collections.sort(loginActionlist, new NumberComparator4());
        List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("tcsbServiceEntity_name");
		treeGridModel.setParentId("tcsbServiceEntity_id");
		treeGridModel.setIdField("id");
		treeGridModel.setSrc("logo");
		treeGridModel.setFunctionType("isNum");
		treeGridModel.setChildList("tcsbServiceEntityLists");
		// 添加排序字段
		treeGridModel.setOrder("stateorder");
		treeGrids = tcsbServiceService.treegrid(loginActionlist, treeGridModel);
		MutiLangUtil.setMutiTree(treeGrids);
		
		
		List<ShopServiceVo> shopServiceVoList2 = new ArrayList<>();
		for (ShopServiceVo shopServiceVo2 : shopServiceVoList) {
			List<TcsbServiceEntity> serviceList = new ArrayList<>();
			for (TreeGrid treeGrid : treeGrids) {
				if(treeGrid.getParentId().equals(shopServiceVo2.getParentid())){
					TcsbServiceEntity e = new TcsbServiceEntity();
					e.setId(treeGrid.getId());
					e.setName(treeGrid.getText());
					e.setLogo(treeGrid.getSrc());
					if(StringUtil.isNotEmpty(treeGrid.getFunctionType())){
						e.setIsNum(treeGrid.getFunctionType());
					}else{
						e.setIsNum("0");
					}
					serviceList.add(e);
				}
			}
			shopServiceVo2.setServiceList(serviceList);
			shopServiceVoList2.add(shopServiceVo2);
		}
		ajaxJson.setSuccess(true);
		ajaxJson.setMsg("请求成功");
		ajaxJson.setObj(shopServiceVoList2);
		return ajaxJson;
	}
	
	/**
	 * 
	 * @param shopId
	 * @param deskId
	 * @param count
	 * @param shopServiceId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/addService")
	@ResponseBody
	public AjaxJsonApi addService(String shopId,String deskId,String count,String shopServiceId,String userId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		if(StringUtil.isNotEmpty(shopId) && StringUtil.isNotEmpty(deskId)){
			TcsbCallServiceEntity  tc = new TcsbCallServiceEntity();
			tc.setDeskId(deskId);
			if(StringUtil.isNotEmpty(count)){
				tc.setCount(Integer.valueOf(count));
			}else{
				tc.setCount(null);
			}
			tc.setIsplay("0");
			tc.setIsread("0");
			tc.setShopId(shopId);
			tc.setShopServiceId(shopServiceId);
			tc.setUserId(userId);
			tc.setCreateTime(new Date());
			tcsbServiceService.save(tc);
			ajaxJson.setMsg("呼叫成功");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(tc.getId());
		}else{
			ajaxJson.setMsg("呼叫失败");
			ajaxJson.setSuccess(false);
		}
		
		return ajaxJson;
	}
	
	

}
