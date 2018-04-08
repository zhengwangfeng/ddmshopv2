package com.pc.memberlevel.controller;

import com.pc.memberlevel.service.TcsbPCMemberLevelServiceI;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pc/PCMemberLevelController")
public class TcsbPCMemberLevelController {

    @Autowired
    private TcsbPCMemberLevelServiceI tcsbPCMemberLevelService;


    /**
     * 加载所有的会员类型
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCGetShopAllMemberLevel")
    @ResponseBody
    public AjaxJsonApi getShopAllMemberLevel(HttpServletRequest request, HttpServletResponse response, String shopId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
            ajaxJson = tcsbPCMemberLevelService.getShopAllMemberLevel(shopId);

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }


    /**
     * 新增会员类型
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCAddMemberLevel")
    @ResponseBody
    public AjaxJsonApi addMemberLevel(HttpServletRequest request, HttpServletResponse response, String levelName, String discount, String shopId) {
        AjaxJsonApi ajaxJson = validMemberLevel(levelName, discount);
        try {
            if (ajaxJson.isSuccess()) {
                ajaxJson = tcsbPCMemberLevelService.addMemberLevel(levelName, discount, shopId);
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    public AjaxJsonApi validMemberLevel(String levelName, String discount) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        if (ajaxJsonApi.isSuccess() && StringUtils.isEmpty(levelName)) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员类型名称为空");
        }
        if (ajaxJsonApi.isSuccess() && discount.matches("/^(0\\.([1-9]|\\d[1-9])|[1-9](\\.\\d{1,2})?)$/")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("请输入正确的折扣0到10之间");
        }
        return ajaxJsonApi;
    }


    /**
     * 修改会员类型
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCUpdateMemberLevel")
    @ResponseBody
    public AjaxJsonApi updateMemberLevel(HttpServletRequest request, HttpServletResponse response, String memberLevelId, String levelName, String discount) {
        AjaxJsonApi ajaxJson = validMemberLevel(levelName, discount);
        try {
            if (ajaxJson.isSuccess()) {
                ajaxJson = tcsbPCMemberLevelService.updateMemberLevel(memberLevelId, levelName, discount);
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 删除会员类型
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCDeleteMemberLevel")
    @ResponseBody
    public AjaxJsonApi deleteMemberLevel(HttpServletRequest request, HttpServletResponse response, String memberLevelId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
            ajaxJson = tcsbPCMemberLevelService.deleteMemberLevel(memberLevelId);

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }
}
