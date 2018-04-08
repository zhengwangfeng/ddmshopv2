package com.applet.deltaquota;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.deltaquota.service.TcsbDeltaQuotaServiceI;
@Controller
@RequestMapping("/appletDeltaQuota")
public class AppletDeltaQuotaController {
	@Autowired
	private TcsbDeltaQuotaServiceI tcsbDeltaQuotaService;
	
	
	@RequestMapping("/getDeltaQuotaByShop")
	@ResponseBody
	public AjaxJsonApi getDeltaQuotaByShop(String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		List<Map<String, Object>>  maps= tcsbDeltaQuotaService.findForJdbc("select id as quotaId,quota,is_towards as isTowards,towards_limit as towardsLimit from tcsb_delta_quota where shop_id = ?", shopId);
		if (maps.isEmpty()) {
			ajaxJson.setMsg("商家未设置冲值额度 ");
			ajaxJson.setSuccess(false);
		}
		ajaxJson.setMsg("获取冲值额度成功 ");
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(maps);	
		return ajaxJson;
	}
}
