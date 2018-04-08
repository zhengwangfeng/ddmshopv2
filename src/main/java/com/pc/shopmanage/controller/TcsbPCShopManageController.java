package com.pc.shopmanage.controller;

import com.pc.shopmanage.service.TcsbPCShopManageServiceI;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * pc端管理桌位，设置前台
 *
 * @author zwf
 */
@Controller
@RequestMapping("/pc/PCShopManageController")
public class TcsbPCShopManageController {

    @Autowired
    private TcsbPCShopManageServiceI tcsbPCShopManageService;

    /**
     * PC端设置前台
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCSetShopProscenium")
    @ResponseBody
    public AjaxJsonApi setShopProscenium(HttpServletRequest request, HttpServletResponse response, String shopId, String cpuCode) {
        AjaxJsonApi ajaxJsonApi = null;
        try {
            ajaxJsonApi = validParam(shopId, cpuCode);
            if (ajaxJsonApi.isSuccess()) {
                tcsbPCShopManageService.setShopProscenium(shopId, cpuCode);
            }
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJsonApi;
    }

    private AjaxJsonApi validParam(String shopId, String cpuCode) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        if (ajaxJsonApi.isSuccess() && StringUtils.isEmpty(shopId)) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("店铺id为空");
        }
        if (ajaxJsonApi.isSuccess() && StringUtils.isEmpty(cpuCode)) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("cpu序列号为空");
        }
        return ajaxJsonApi;
    }

    /**
     * PC端设置管理桌位
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCManageShopDesk")
    @ResponseBody
    public AjaxJsonApi manageShopDesk(HttpServletRequest request, HttpServletResponse response, String cpuCode, String shopId, String deskIds) {

        AjaxJsonApi ajaxJsonApi = null;

        try {
            ajaxJsonApi = validParam(shopId, cpuCode);
            if (ajaxJsonApi.isSuccess()) {
                ajaxJsonApi = tcsbPCShopManageService.manageShopDesk(shopId, cpuCode, deskIds);
            }
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJsonApi;
    }

    /**
     * PC端取消管理桌位
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCShopDisManageDesk")
    @ResponseBody
    public AjaxJsonApi shopDisManageDesk(HttpServletRequest request, HttpServletResponse response) {

        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

        try {


        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJsonApi;
    }
}
