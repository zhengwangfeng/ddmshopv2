package com.applet.shop;

import java.util.HashMap;
import java.util.Map;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
@Controller
@RequestMapping("/appletShop")
public class AppletShopController {
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@RequestMapping("/getShopAppletQrcode")
	@ResponseBody
	public AjaxJsonApi getShopAppletQrcode(String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, shopId);
		ajaxJson.setMsg("获取店铺小程序码成功 ");
		ajaxJson.setSuccess(true);
		String appletShopQrcode = "";
		if (tcsbShopEntity!=null) {
			appletShopQrcode = ResourceUtil.getConfigByName("applet.qrcode") + tcsbShopEntity.getAppletQrcode();
		}
		ajaxJson.setObj(appletShopQrcode);	
		return ajaxJson;
	}
	/*获取店铺的预约方式 */
	@RequestMapping("/getReservationMethod")
	@ResponseBody
	public AjaxJsonApi getReservationMethod(String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, shopId);
		Map<String , Object> map = new  HashMap<>();
		ajaxJson.setMsg("获取店铺预约方式成功 ");
		/*0全额1免押金2押金*/
		if (tcsbShopEntity!=null) {
			map.put("reservationMethod", tcsbShopEntity.getReservationMethod());
		}
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(map);	
		return ajaxJson;
	}
}
