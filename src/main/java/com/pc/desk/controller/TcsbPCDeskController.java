package com.pc.desk.controller;

import com.pc.desk.service.TcsbPCDeskServiceI;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author zwf
 */
@Controller
@RequestMapping("/pc/PCDeskController")
public class TcsbPCDeskController {

    @Autowired
    private TcsbPCDeskServiceI tcsbPCDeskService;
    /**
     * PC端预点转台
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCReservationShiftDesk")
    @ResponseBody
    public AjaxJsonApi reservationShiftDesk(HttpServletRequest request, HttpServletResponse response, String reservationId, String deskId) {

        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

        try {

            ajaxJsonApi = tcsbPCDeskService.reservationShiftDesk(reservationId,deskId);

        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }


        return ajaxJsonApi;
    }

    /**
     * PC端堂点转台
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCLocationOrderShiftDesk")
    @ResponseBody
    public AjaxJsonApi locationOrderShiftDesk(HttpServletRequest request, HttpServletResponse response, String orderParentId, String deskId) {

        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

        try {

            ajaxJsonApi = tcsbPCDeskService.locationOrderShiftDesk(orderParentId,deskId);

        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }


        return ajaxJsonApi;
    }

}
