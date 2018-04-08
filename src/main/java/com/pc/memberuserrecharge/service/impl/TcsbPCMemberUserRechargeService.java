package com.pc.memberuserrecharge.service.impl;

import com.pc.memberuserrecharge.service.TcsbPCMemberUserRechargeServiceI;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.usercost.entity.TcsbUserCostEntity;
import com.tcsb.userdelta.entity.TcsbUserDeltaEntity;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("tcsbPCMemberUserRechargeService")
@Transactional
public class TcsbPCMemberUserRechargeService extends CommonServiceImpl implements TcsbPCMemberUserRechargeServiceI {
    @Override
    public AjaxJsonApi memberUserRecharge(String id, Double amount, Double presentAmount, String payUtil) throws Exception {
        TcsbMemberUserEntity tcsbMemberUserEntity = this.get(TcsbMemberUserEntity.class, id);
        AjaxJsonApi ajaxJsonApi = validMember(tcsbMemberUserEntity);
        if (ajaxJsonApi.isSuccess()) {
            if (amount > 0) {
                Double realAmount = BigDecimalUtil.add(amount, presentAmount);
                Double balance = BigDecimalUtil.add(realAmount, tcsbMemberUserEntity.getBalance());
                tcsbMemberUserEntity.setBalance(balance);
                this.saveOrUpdate(tcsbMemberUserEntity);
                TcsbUserDeltaEntity deltaEntity = new TcsbUserDeltaEntity();
                deltaEntity.setBalance(balance);
                deltaEntity.setAmount(amount);
                deltaEntity.setRealAmount(realAmount);
                deltaEntity.setPayUtil(payUtil);
                deltaEntity.setStatus(1);
                deltaEntity.setCreateDate(new Date());
                deltaEntity.setPresentAmount(presentAmount);
                deltaEntity.setMemberUserId(tcsbMemberUserEntity.getId());
                deltaEntity.setShopId(tcsbMemberUserEntity.getShopId());
                deltaEntity.setPayMethod("1");
                this.save(deltaEntity);
            } else {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("充值金额小于0");
            }
        }
        return ajaxJsonApi;
    }

    public AjaxJsonApi validMember(TcsbMemberUserEntity tcsbMemberUserEntity) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        if (tcsbMemberUserEntity != null) {
            if (tcsbMemberUserEntity.getStatus().equals("0")) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("会员已停用，请先启用");
            } else if (tcsbMemberUserEntity.getStatus().equals("1")) {
                ajaxJsonApi.setSuccess(true);
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员用户不存在");
        }
        return ajaxJsonApi;
    }

    @Override
    public AjaxJsonApi memberUserCost(String memberUserId, String orderId, Double realPayMoney, Double offlineDiscount, String offlinePayMethod, String dicountType) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        //实付金额
        Double offlinePrice = realPayMoney;
        if (offlinePrice < 0) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("消费金额小于0");
            return ajaxJsonApi;
        }

        TcsbOrderEntity tcsbOrderEntity = this.get(TcsbOrderEntity.class, orderId);
        if (tcsbOrderEntity != null) {
            if (!tcsbOrderEntity.getPayStatus().equals("0")) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("订单状态异常");
                return ajaxJsonApi;
            }
            TcsbOrderParentEntity tcsbOrderParentEntity = this.get(TcsbOrderParentEntity.class, tcsbOrderEntity.getOrderParentId());
            if (tcsbOrderParentEntity == null) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("总订单异常");
                return ajaxJsonApi;
            }
//            if (offlinePrice > BigDecimalUtil.mul(tcsbOrderEntity.getTotalPrice(), tcsbOrderParentEntity.getDeskNum() == null ? 1 : tcsbOrderParentEntity.getDeskNum())) {
//                ajaxJsonApi.setSuccess(false);
//                ajaxJsonApi.setMsg("支付金额大于需支付金额");
//                return ajaxJsonApi;
//            }
            boolean useFront = false;
            double frontMoney = 0.0;
            //押金参与付款
            if (tcsbOrderParentEntity.getUseFrontMoney() != null) {
                if (tcsbOrderParentEntity.getUseFrontMoney().equals("0")) {
                    if (tcsbOrderParentEntity.getFrontMoney() != null) {
                    	frontMoney =tcsbOrderParentEntity.getFrontMoney();
                        //offlinePrice = BigDecimalUtil.add(offlinePrice, tcsbOrderParentEntity.getFrontMoney());
                        useFront = true;
                    }
                }
            }
            //Double discount = BigDecimalUtil.sub(BigDecimalUtil.sub(tcsbOrderEntity.getTotalPrice() * (tcsbOrderParentEntity.getDeskNum() == null ? 1 : tcsbOrderParentEntity.getDeskNum()), offlinePrice), offlineDiscount);

            //会员余额支付
            if (offlinePayMethod.equals("3")) {
                if (StringUtils.isNotEmpty(memberUserId)) {
                    TcsbMemberUserEntity tcsbMemberUserEntity = this.get(TcsbMemberUserEntity.class, memberUserId);
                    //会员账号是否可用
                    ajaxJsonApi = validMember(tcsbMemberUserEntity);
                    if (!ajaxJsonApi.isSuccess()) {
                        return ajaxJsonApi;
                    }
                    //校验余额
                    double balance = BigDecimalUtil.sub(tcsbMemberUserEntity.getBalance(), offlinePrice);
                    if (balance < 0) {
                        ajaxJsonApi.setSuccess(false);
                        ajaxJsonApi.setMsg("会员余额不足，差:" + balance * (-1));
                        return ajaxJsonApi;
                    }
                    //会员结算
                    tcsbMemberUserEntity.setBalance(balance);
                    this.saveOrUpdate(tcsbMemberUserEntity);
                    TcsbUserCostEntity costEntity = new TcsbUserCostEntity();
                    costEntity.setBalance(balance);
                    costEntity.setCost(offlinePrice);
                    costEntity.setDeskId(tcsbOrderParentEntity.getDeskId());
                    costEntity.setShopId(tcsbOrderEntity.getShopId());
                    costEntity.setOrderId(orderId);
                    costEntity.setMemberUserId(memberUserId);
                    costEntity.setCreateDate(new Date());
                    this.save(costEntity);
                } else {
                    ajaxJsonApi.setSuccess(false);
                    ajaxJsonApi.setMsg("userId参数为空");
                    return ajaxJsonApi;
                }
            }
            //统一更新订单和桌位状态
            if (useFront) {
                tcsbOrderParentEntity.setUseFrontMoney("1");
                tcsbOrderEntity.setOnlinePrice(frontMoney);
                this.saveOrUpdate(tcsbOrderParentEntity);
            }

            tcsbOrderEntity.setOfflineDiscount(offlineDiscount.toString());
            tcsbOrderEntity.setOfflinePrice(offlinePrice);
            tcsbOrderEntity.setPayMethod("1");
            tcsbOrderEntity.setOfflinePayMethod(offlinePayMethod);
            tcsbOrderEntity.setPayStatus("1");
            if (dicountType.equals("1")) {
                tcsbOrderEntity.setVipDiscountPrice(offlineDiscount);
                tcsbOrderEntity.setHandDiscountPrice(0.0);
            } else if (dicountType.equals("0")) {
                tcsbOrderEntity.setVipDiscountPrice(0.0);
                tcsbOrderEntity.setHandDiscountPrice(offlineDiscount);
            }
            tcsbOrderEntity.setPayTime(new Date());
            this.saveOrUpdate(tcsbOrderEntity);

            TcsbDeskEntity deskEntity = this.get(TcsbDeskEntity.class, tcsbOrderParentEntity.getDeskId());
            deskEntity.setStatus("2");
            this.saveOrUpdate(deskEntity);

        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("订单不存在");
            return ajaxJsonApi;
        }
        return ajaxJsonApi;
    }

}
