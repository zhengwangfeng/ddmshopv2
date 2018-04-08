package com.tcsb.socket.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.tcsb.socket.entity.ImUser;
import com.tcsb.socket.service.ImUserServiceI;

/**
 * 功能点controller
 *
 * @author HUYONG
 * @create 2016-12-08
 */
@Controller
@RequestMapping("/andysoso")
public class AndysosoController{

	@Autowired
	private ImUserServiceI imUserService;
    /**
     * 进入IM页面
     * @return
     */
    @RequestMapping(params = "actionImView")
    public ModelAndView actionImView(HttpServletRequest request){
        Map<String,Object> map = Maps.newHashMap();
        String viewUrl = "";
        try {
            ImUser adminDO = (ImUser)request.getSession().getAttribute("andysosoAdmin");
            if(null != adminDO){
                List<ImUser> adminList = imUserService.findByQueryString("from ImUser");
                map.put("andyAdmin",adminDO);
                map.put("adminList",adminList);
                viewUrl = "/user_im";
            }
        }catch (Exception e){
        }
        //viewUrl = "/user_im";
        return new ModelAndView(viewUrl,map);
    }
}
