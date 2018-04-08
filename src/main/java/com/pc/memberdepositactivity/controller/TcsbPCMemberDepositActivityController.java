package com.pc.memberdepositactivity.controller;

import com.tcsb.memberdepositactivity.entity.TcsbMemberDepositActivityEntity;
import com.tcsb.memberdepositactivity.service.TcsbMemberDepositActivityServiceI;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/pc/PCMemberDepositActivityController")
public class TcsbPCMemberDepositActivityController {

    @Autowired
    private TcsbMemberDepositActivityServiceI tcsbMemberDepositActivityService;

    /**
     * 获得列表
     *
     * @return
     */
    @RequestMapping(params = "list")
    @ResponseBody
    public AjaxJsonApi list(HttpServletRequest request, TcsbMemberDepositActivityEntity tcsbMemberDepositActivityEntity) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        try {
            List<TcsbMemberDepositActivityEntity> list = tcsbMemberDepositActivityService.findHql("from TcsbMemberDepositActivityEntity where shopId=? order by amount desc", tcsbMemberDepositActivityEntity.getShopId());
            ajaxJsonApi.setObj(list);
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统错误");
        }
        return ajaxJsonApi;
    }

    public AjaxJsonApi validMemberDepositActivityEntity(TcsbMemberDepositActivityEntity tcsbMemberDepositActivityEntity) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        if (ajaxJsonApi.isSuccess() && tcsbMemberDepositActivityEntity.getName().trim().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("充值优惠活动名称为空");
        }
        if (ajaxJsonApi.isSuccess() && tcsbMemberDepositActivityEntity.getAmount().toString().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("充值金额为空");
        }
        if (ajaxJsonApi.isSuccess() && tcsbMemberDepositActivityEntity.getShopId().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("店铺id为空");
        }
        if (ajaxJsonApi.isSuccess() && tcsbMemberDepositActivityEntity.getPresentAmount().toString().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("赠送金额为空");
        }
        if (ajaxJsonApi.isSuccess()) {
            //修改
            if (tcsbMemberDepositActivityEntity.getId() != null) {
                TcsbMemberDepositActivityEntity entity = tcsbMemberDepositActivityService.get(TcsbMemberDepositActivityEntity.class, tcsbMemberDepositActivityEntity.getId());
                if (ajaxJsonApi.isSuccess() && !entity.getName().equals(tcsbMemberDepositActivityEntity.getName())) {
                    ajaxJsonApi = validName(ajaxJsonApi, tcsbMemberDepositActivityEntity);
                }
                if (ajaxJsonApi.isSuccess() && entity.getAmount() != tcsbMemberDepositActivityEntity.getAmount()) {
                    ajaxJsonApi = validAmount(ajaxJsonApi, tcsbMemberDepositActivityEntity);
                }
            } else {
                if (ajaxJsonApi.isSuccess()) {
                    ajaxJsonApi = validName(ajaxJsonApi, tcsbMemberDepositActivityEntity);
                }
                if (ajaxJsonApi.isSuccess()) {
                    ajaxJsonApi = validAmount(ajaxJsonApi, tcsbMemberDepositActivityEntity);
                }
            }
        }
        return ajaxJsonApi;
    }

    public AjaxJsonApi validAmount(AjaxJsonApi ajaxJsonApi, TcsbMemberDepositActivityEntity tcsbMemberDepositActivityEntity) {
        if (ajaxJsonApi.isSuccess()) {
            List<TcsbMemberDepositActivityEntity> list = tcsbMemberDepositActivityService.findHql("from TcsbMemberDepositActivityEntity where shopId=? and name=?", tcsbMemberDepositActivityEntity.getShopId(), tcsbMemberDepositActivityEntity.getName());
            if (!list.isEmpty()) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("该充值优惠活动名称已存在");
            }
        }
        return ajaxJsonApi;
    }

    public AjaxJsonApi validName(AjaxJsonApi ajaxJsonApi, TcsbMemberDepositActivityEntity tcsbMemberDepositActivityEntity) {
        if (ajaxJsonApi.isSuccess()) {
            List<TcsbMemberDepositActivityEntity> list = tcsbMemberDepositActivityService.findHql("from TcsbMemberDepositActivityEntity where shopId=? and amount=?", tcsbMemberDepositActivityEntity.getShopId(), tcsbMemberDepositActivityEntity.getAmount());
            if (!list.isEmpty()) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("该充值金额已存在");
            }
        }
        return ajaxJsonApi;
    }

    /**
     * 添加
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJsonApi doAdd(TcsbMemberDepositActivityEntity tcsbMemberDepositActivity, HttpServletRequest request) {
        AjaxJsonApi ajaxJsonApi = validMemberDepositActivityEntity(tcsbMemberDepositActivity);
        try {
            if(ajaxJsonApi.isSuccess()){
                tcsbMemberDepositActivityService.save(tcsbMemberDepositActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统错误");
        }
        return ajaxJsonApi;
    }

    /**
     * 更新
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJsonApi doUpdate(TcsbMemberDepositActivityEntity tcsbMemberDepositActivity, HttpServletRequest request) {
        AjaxJsonApi ajaxJsonApi = validMemberDepositActivityEntity(tcsbMemberDepositActivity);
        try {
            if(ajaxJsonApi.isSuccess()){
                TcsbMemberDepositActivityEntity t = tcsbMemberDepositActivityService.get(TcsbMemberDepositActivityEntity.class, tcsbMemberDepositActivity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tcsbMemberDepositActivity, t);
                tcsbMemberDepositActivityService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统错误");
        }
        return ajaxJsonApi;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJsonApi doDel(TcsbMemberDepositActivityEntity tcsbMemberDepositActivity, HttpServletRequest request) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        tcsbMemberDepositActivity = tcsbMemberDepositActivityService.getEntity(TcsbMemberDepositActivityEntity.class, tcsbMemberDepositActivity.getId());
        try {
            tcsbMemberDepositActivityService.delete(tcsbMemberDepositActivity);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统错误");
        }
        return ajaxJsonApi;
    }
}
