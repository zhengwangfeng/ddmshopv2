package com.pc.memberuser.controller;

import com.pc.memberuser.service.TcsbPCMemberUserServiceI;
import com.pc.memberuser.vo.TcsbPCMemberUserBasicMsgVO;
import com.pc.memberuser.vo.TcsbPCMemberUserVO;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pc/PCMemberUserController")
public class TcsbPCMemberUserController {
    @Autowired
    private TcsbPCMemberUserServiceI tcsbPCMemberUserService;


    /**
     * 加载所有的会员信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCGetShopAllMemberUser")
    @ResponseBody
    public AjaxJsonApi getShopAllMemberUser(TcsbMemberUserEntity tcsbMemberUser, HttpServletRequest request, HttpServletResponse response,
                                            DataGrid dataGrid, String queryString) {

        dataGrid.setField(this.dataGridSetField(TcsbPCMemberUserVO.class));
        CriteriaQuery cq = new CriteriaQuery(TcsbMemberUserEntity.class, dataGrid);

        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMemberUser, request.getParameterMap());
        cq.add();

        cq.eq("shopId", tcsbMemberUser.getShopId());
        cq.addOrder("cardNo", SortDirection.desc);
        this.tcsbPCMemberUserService.getDataGridReturn(cq, true);
        List<TcsbMemberUserEntity> list = dataGrid.getResults();

        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        List<TcsbPCMemberUserVO> outList = new ArrayList<>();
        try {
            for (TcsbMemberUserEntity entity : list) {
                outList.add(tcsbPCMemberUserService.convertMemberUserEntityTOVO(entity));
            }
            dataGrid.setResults(outList);
            ajaxJsonApi.setObj(dataGrid);
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统错误");
            e.printStackTrace();
        }
        return ajaxJsonApi;
    }


    /**
     * 根据查询条件查找会员信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCGetShopOneMemberUser")
    @ResponseBody
    public AjaxJsonApi getShopOneMemberUser(HttpServletRequest request, HttpServletResponse response,
                                            String queryString, String shopId) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        if (!queryString.trim().equals("")) {
            try {
                ajaxJsonApi = tcsbPCMemberUserService.getShopOneMemberUser(queryString, shopId);
            } catch (Exception e) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("系统错误");
                e.printStackTrace();
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("查询参数为空");
        }
        return ajaxJsonApi;
    }

    public String dataGridSetField(Class t) {
        Field[] fields = t.getDeclaredFields();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i < fields.length) {
                builder.append(fields[i].getName() + ",");
            } else {
                builder.append(fields[i].getName());
            }
        }
        return builder.toString();
    }

    public AjaxJsonApi validMemberUser(TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();


        if (ajaxJsonApi.isSuccess() && memberUserBasicMsgVO.getName().trim().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员姓名为空");
        }
        if (ajaxJsonApi.isSuccess() && memberUserBasicMsgVO.getMobile().trim().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("电话号码为空");
        }
        if (ajaxJsonApi.isSuccess() && memberUserBasicMsgVO.getCardNo().trim().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员卡号为空");
        }
        if (ajaxJsonApi.isSuccess() && memberUserBasicMsgVO.getMembershipLevelId().trim().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员类型为空");
        }
        if (ajaxJsonApi.isSuccess() && memberUserBasicMsgVO.getSex().trim().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员性别为空");
        }
        if (ajaxJsonApi.isSuccess() && memberUserBasicMsgVO.getBirthOfDate().toString().equals("")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员日期为空");
        }
        if (ajaxJsonApi.isSuccess() && !memberUserBasicMsgVO.getSex().matches("^[10]{1}$")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员性别格式错误");
        }
        if (ajaxJsonApi.isSuccess() && !memberUserBasicMsgVO.getMobile().matches("^1[34578][0-9]{9}$")) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("请输入正确的11位手机号格式");
        }
        return ajaxJsonApi;
    }

    /**
     * 新增会员
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCAddMemberUser")
    @ResponseBody
    public AjaxJsonApi addMemberLevel(HttpServletRequest request, HttpServletResponse response, TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
            ajaxJson = validMemberUser(memberUserBasicMsgVO);
            if (ajaxJson.isSuccess()) {
                ajaxJson = tcsbPCMemberUserService.addMemberUser(memberUserBasicMsgVO);
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }


    /**
     * 修改会员
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCUpdateMemberUser")
    @ResponseBody
    public AjaxJsonApi updateMemberUser(HttpServletRequest request, HttpServletResponse response, TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
            ajaxJson = validMemberUser(memberUserBasicMsgVO);
            if (ajaxJson.isSuccess()) {
                ajaxJson = tcsbPCMemberUserService.updateMemberUser(memberUserBasicMsgVO);
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 修改会员状态
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCUpdateMemberUserStatus")
    @ResponseBody
    public AjaxJsonApi updateMemberUserStatus(HttpServletRequest request, HttpServletResponse response, String id) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
            ajaxJson = tcsbPCMemberUserService.updateMemberUserStatus(id);

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }
}
