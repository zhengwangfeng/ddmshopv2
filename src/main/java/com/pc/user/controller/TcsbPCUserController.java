package com.pc.user.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;

import com.pc.security.TokenMgr;
import com.pc.security.config.Constant;
import com.pc.security.model.SubjectModel;
import com.pc.security.utils.SigNatureUtil;
import com.tcsb.shop.entity.TcsbShopEntity;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

/**
 * @author jimmy
 */
@Controller
@RequestMapping("/PCUserController")
public class TcsbPCUserController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(TcsbPCUserController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;
    @Autowired
    private UserService userService;
    @Autowired
    private MutiLangServiceI mutiLangService;

    /**
     * 检查用户名称
     *
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "checkuser")
    @ResponseBody
    public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
        HttpSession session = req.getSession();
        AjaxJson j = new AjaxJson();
        TSUser userKey = new TSUser();
//        int data[] = new int[bytes.length];
//        for (int i = 0; i < user.getSignature().length; i++) {
//            data[i] = bytes[i] & 0xff;
//        }
        try {
            byte[] key = new BASE64Decoder().decodeBuffer(SigNatureUtil.PC_PARAM_KEY);
            userKey.setUserName(user.getUserName());
            //System.out.println(user.getPassword());
            userKey.setPassword(SigNatureUtil.decodeECB(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
//         用户登录验证逻辑
        TSUser u = userService.checkUserExits(userKey);
//        TSUser u = userService.checkUserExits(user);
        if (u == null) {
            j.setMsg("用户或密码错误");
            j.setSuccess(false);
            return j;
        }
        if (u != null && u.getStatus() != 0) {
            // 处理用户有多个组织机构的情况，以弹出框的形式让用户选择
            j.setObj(u.getShopId());
            SubjectModel sub = new SubjectModel(0001, "pcuser");
            String token = TokenMgr.createJWT(u.getShopId(), TokenMgr.generalSubject(sub), Constant.JWT_TTL);

            j.setToken(token);
            j.setSuccess(true);
            j.setMsg("登入成功");
        } else {
            j.setMsg("用户已锁定");
            j.setSuccess(false);
        }
        return j;
    }

//    /**
//     * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中；
//     *
//     * @param req   request
//     * @param user  当前登录用户
//     * @param orgId 组织主键
//     */
//    private void saveLoginSuccessInfo(HttpServletRequest req, TSUser user, String orgId) {
//        String message = null;
//        TSDepart currentDepart = systemService.get(TSDepart.class, orgId);
//        user.setCurrentDepart(currentDepart);
//
//        HttpSession session = ContextHolderUtils.getSession();
//        session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, user);
//        message = mutiLangService.getLang("common.user") + ": " + user.getUserName() + "[" + currentDepart.getDepartname() + "]" + mutiLangService.getLang("common.login.success");
//
//        //当前session为空 或者 当前session的用户信息与刚输入的用户信息一致时，则更新Client信息
//        Client clientOld = ClientManager.getInstance().getClient(session.getId());
//        if (clientOld == null || clientOld.getUser() == null || user.getUserName().equals(clientOld.getUser().getUserName())) {
//            Client client = new Client();
//            client.setIp(IpUtil.getIpAddr(req));
//            client.setLogindatetime(new Date());
//            client.setUser(user);
//            ClientManager.getInstance().addClinet(session.getId(), client);
//        } else {//如果不一致，则注销session并通过session=req.getSession(true)初始化session
//            ClientManager.getInstance().removeClinet(session.getId());
//            session.invalidate();
//            session = req.getSession(true);//session初始化
//            session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, user);
//            session.setAttribute("randCode", req.getParameter("randCode"));//保存验证码
//            checkuser(user, req);
//        }
//
//
//        // 添加登陆日志
//        systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
//    }


    /**
     * PC端用户注册接口
     *
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "saveUser")
    @ResponseBody
    public AjaxJson saveUser(TSUser user, HttpServletRequest req) {
        String message = null;
        AjaxJson j = new AjaxJson();
        // 得到用户的角色
        String password = oConvertUtils.getString(req.getParameter("password"));
        if (StringUtil.isNotEmpty(user.getId())) {
            TSUser users = systemService.getEntity(TSUser.class, user.getId());
            users.setEmail(user.getEmail());
            users.setOfficePhone(user.getOfficePhone());
            users.setMobilePhone(user.getMobilePhone());

            systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId());
            users.setRealName(user.getRealName());
            users.setStatus(Globals.User_Normal);
            systemService.updateEntitie(users);

            message = "用户: " + users.getUserName() + "更新成功";

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            TSUser users = systemService.findUniqueByProperty(TSUser.class, "userName", user.getUserName());
            if (users != null) {
                message = "用户: " + users.getUserName() + "已经存在";
            } else {
                user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));
//				if (user.getTSDepart().equals("")) {
//					user.setTSDepart(null);
//				}
                //系统更新update by jimmy 如果是商家只读出商家权限
                /*更新字断shopId--------begin-------过滤用户权限*/
//				if (!checkAdmin()) {
//					TSUser tsUser = getCurrentUser();
//					TcsbShopEntity tcsbShopEntity = systemService.findUniqueByProperty(TcsbShopEntity.class, "userId", tsUser.getId());
//					user.setShopId(tcsbShopEntity.getId());
//				}
                /*更新字断shopId--------end-------过滤用户权限*/
                user.setStatus(Globals.User_Normal);
                user.setDeleteFlag(Globals.Delete_Normal);
                systemService.save(user);
                saveUserOrg(user);
                saveRoleUser(user);
                message = "用户: " + user.getUserName() + "添加成功";

                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }

        }
        j.setMsg(message);
        return j;
    }

    /**
     * 保存 用户-组织机构 关系信息
     *
     * @param user user
     */
    private void saveUserOrg(TSUser user) {
        TSDepart depart = new TSDepart();
        //默认
        depart.setId("402881ed5a8e4fa0015a91a9bf3b000d");
        TSUserOrg userOrg = new TSUserOrg();
        userOrg.setTsUser(user);
        userOrg.setTsDepart(depart);
        systemService.save(userOrg);
    }

    /**
     * 保存 用户-组织机构 关系信息
     *
     * @param user user
     */
    protected void saveRoleUser(TSUser user) {
        TSRoleUser rUser = new TSRoleUser();
        TSRole role = systemService.getEntity(TSRole.class, "8a8ab0b246dc81120146dc81818b0051");
        rUser.setTSRole(role);
        rUser.setTSUser(user);
        systemService.save(rUser);
    }

}
