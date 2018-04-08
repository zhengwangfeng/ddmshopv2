package com.tcsb.socket.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.Result;
import com.google.common.collect.Maps;
import com.tcsb.socket.entity.ImUser;
import com.tcsb.socket.service.ImUserServiceI;

/**
 * 用户controller
 *
 * @author HUYONG
 * @create 2016-12-08
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

	@Autowired
	private ImUserServiceI imUserService;
    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
	 @RequestMapping(params = "adminLogin")
	    @ResponseBody
	    public void adminLogin(HttpServletRequest request, String userName, String password){
	        Map<String,Object> map = Maps.newHashMap();
	        ImUser adminDO = null;
	        int status = 0;
	        try {
	            if(!StringUtils.isEmpty(userName) ){
	                adminDO = (ImUser) imUserService.findUniqueByProperty(ImUser.class, "userName",userName);
	            }
	            if(null != adminDO){
	                request.getSession().setAttribute("andysosoAdmin",adminDO);
	                status = 1;
	            }
	        }catch (Exception e){
	        }
	        map.put("status",status);
	        //return successResponse(map);
	    }
}
