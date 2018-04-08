package com.applet.usermember;

import com.pc.memberuserrecharge.service.TcsbPCMemberUserRechargeServiceI;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.membershiplevel.entity.TcsbMembershipLevelEntity;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import com.tcsb.memberuser.service.TcsbMemberUserServiceI;

@Controller
@RequestMapping("/appletMember")
public class UserMemberController extends BaseController{
	
	@Autowired
	private TcsbMemberUserServiceI memberUserService;

	@Autowired
	private TcsbPCMemberUserRechargeServiceI tcsbPCMemberUserRechargeService;
	
	/**
	 * 会员中心
	 * @param userId
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/getUserMember")
	@ResponseBody
	public AjaxJsonApi getUserMember(String userId,String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		UserMemberVo userMemberVo = new UserMemberVo();
		String hql = "from TcsbMemberUserEntity where openid='"+userId+"' and shopId='"+shopId+"'";
		TcsbMemberUserEntity tm = memberUserService.singleResult(hql);
		if(StringUtil.isNotEmpty(tm)){
			userMemberVo.setBlance(tm.getBalance()+"");
			TcsbMembershipLevelEntity tcsbMembershipLevelEntity = memberUserService.get(TcsbMembershipLevelEntity.class, tm.getMembershipLevelId());
			userMemberVo.setMemberName(tcsbMembershipLevelEntity.getName());
		}else{
			userMemberVo.setBlance("0");
			userMemberVo.setMemberName("普通用户");
		}
		ajaxJson.setMsg("请求成功");
		ajaxJson.setObj(userMemberVo);
		ajaxJson.setSuccess(true);
		return ajaxJson;
	}

	/**
	 * 小程序会员余额支付
	 * @return
	 */
	@RequestMapping("/memberUserCost")
	@ResponseBody
	public AjaxJsonApi userMemberCost( String memberUserId, String orderId, String realPayMoney,String openId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		Double payMoney = Double.valueOf(realPayMoney);
		try {
			//tcsbPCMemberUserRechargeService.memberUserCost(memberUserId, orderId, payMoney,0.0,"0",null,"1",openId);
		} catch (Exception e) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("系统异常");
			e.printStackTrace();
		}
		return ajaxJson;
	}

}
