package com.pc.memberuserrecharge.controller;

import com.pc.memberuserrecharge.service.TcsbPCMemberUserRechargeServiceI;
import com.pc.memberuserrecharge.vo.TcsbUserCostVO;
import com.pc.security.utils.SigNatureUtil;
import com.tcsb.usercost.entity.TcsbUserCostEntity;
import com.tcsb.userdelta.entity.TcsbUserDeltaEntity;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.web.cgform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/pc/PCMemberUserRechargeController")
public class TcsbPCMemberUserRechargeController {

    @Autowired
    private TcsbPCMemberUserRechargeServiceI tcsbPCMemberUserRechargeService;

    /**
     * PC端会员充值
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCMemberUserRecharge")
    @ResponseBody
    public AjaxJsonApi memberUserRecharge(HttpServletRequest request, HttpServletResponse response, String id, String amount, String presentAmount, String payUtil, String sigNature) {

        AjaxJsonApi ajaxJson = new AjaxJsonApi();

        try {
            Double amountMoney = Double.valueOf(amount);
            Double presentAmountMoney = Double.valueOf(presentAmount);
            try {
                StringBuilder builder = new StringBuilder();
                builder.append("id=");
                builder.append(id);
                builder.append(";");
                builder.append("amount=");
                builder.append(amount);
                builder.append(";");
                builder.append("presentAmount=");
                builder.append(presentAmount);
                builder.append(";");
                builder.append("payUtil=");
                builder.append(payUtil);
                String key = SigNatureUtil.encodeECB(builder.toString());
                if (sigNature.equals(key)) {
                    ajaxJson = tcsbPCMemberUserRechargeService.memberUserRecharge(id, amountMoney, presentAmountMoney, payUtil);
                } else {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("参数签名错误");
                }
            } catch (Exception e) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("系统异常");
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("充值金额格式错误");
        }
        return ajaxJson;
    }


    /**
     * 获取PC端会员充值记录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCMemberUserRechargeRecords")
    @ResponseBody
    public AjaxJsonApi memberUserRechargeRecords(HttpServletRequest request, HttpServletResponse response, String memberUserId) {

        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        List<TcsbUserDeltaEntity> list = tcsbPCMemberUserRechargeService.findHql("from TcsbUserDeltaEntity where memberUserId=? order by createDate desc", memberUserId);
        ajaxJson.setObj(list);
        return ajaxJson;
    }

    /**
     * 获取PC端会员消费记录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCMemberUserCostRecords")
    @ResponseBody
    public AjaxJsonApi memberUserCostRecords(HttpServletRequest request, HttpServletResponse response, String memberUserId) {

        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        List<TcsbUserCostEntity> list = tcsbPCMemberUserRechargeService.findHql("from TcsbUserCostEntity where memberUserId=? order by createDate desc", memberUserId);
        List<TcsbUserCostVO> outList = new ArrayList<>();
        try {
            for (TcsbUserCostEntity entity : list) {
                TcsbUserCostVO vo = new TcsbUserCostVO();
                MyBeanUtils.copyBeanNotNull2Bean(entity, vo);
                outList.add(vo);
            }
            ajaxJson.setObj(outList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ajaxJson;
    }


    /**
     * PC端会员消费
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCMemberUserCost")
    @ResponseBody
    public AjaxJsonApi memberUserCost(HttpServletRequest request, HttpServletResponse response, String memberUserId, String orderId, String offlinePrice, String offlineDiscount, String offlinePayMethod, String dicountType, String sigNature) {

        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
            Double offlinePriceMoney = Double.valueOf(BigDecimalUtil.numericRetentionDecimal(offlinePrice, 2));
            Double offlineDiscountMoney = Double.valueOf(BigDecimalUtil.numericRetentionDecimal(offlineDiscount, 2));
            try {
                StringBuilder builder = new StringBuilder();
                builder.append("memberUserId=");
                builder.append(memberUserId);
                builder.append(";");
                builder.append("orderId=");
                builder.append(orderId);
                builder.append(";");
                builder.append("offlinePrice=");
                builder.append(offlinePrice);
                builder.append(";");
                builder.append("offlineDiscount=");
                builder.append(offlineDiscount);
                builder.append(";");
                builder.append("offlinePayMethod=");
                builder.append(offlinePayMethod);
                builder.append(";");
                builder.append("dicountType=");
                builder.append(dicountType);
                String key = SigNatureUtil.encodeECB(builder.toString());
                if (sigNature.equals(key)) {
                    ajaxJson = tcsbPCMemberUserRechargeService.memberUserCost(memberUserId, orderId, offlinePriceMoney, offlineDiscountMoney,  offlinePayMethod, dicountType);
                } else {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("参数签名错误");
                }
            }
            catch (Exception e) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("系统异常");
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("充值金额格式错误");
        }
        return ajaxJson;
    }
}

