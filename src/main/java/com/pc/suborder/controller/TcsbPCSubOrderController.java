package com.pc.suborder.controller;

import com.pc.suborder.service.TcsbPCSubOrderServiceI;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * pc端加单及加单项生成
 */
@Controller
@RequestMapping("/pc/PCSubOrderController")
public class TcsbPCSubOrderController {

    @Autowired
    private TcsbPCSubOrderServiceI tcsbPCSubOrderService;

    /**
     * PC端生成加单及加单项
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCCreateSubOrderAndSubOrderItems")
    @ResponseBody
    public AjaxJsonApi createSubOrderAndSubOrderItems(HttpServletRequest request, HttpServletResponse response, String content) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        tcsbPCSubOrderService.createSubOrderAndSubOrderItems(content);
        return ajaxJsonApi;
    }

}