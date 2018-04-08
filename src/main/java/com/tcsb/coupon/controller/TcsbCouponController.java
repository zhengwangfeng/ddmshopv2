package com.tcsb.coupon.controller;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.coupon.service.TcsbCouponServiceI;
import com.tcsb.coupon.vo.TcsbCouponEntityVo;
import com.tcsb.coupon.vo.TcsbUserCouponVO;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.fullcuttemplate.service.TcsbFullcutTemplateServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopsmsservice.entity.TcsbShopSmsServiceEntity;
import com.tcsb.shopsmsservice.service.TcsbShopSmsServiceServiceI;
import com.tcsb.tcsbassociatorbigdata.entity.TcsbAssociatorBigdataEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import com.weixin.weixinuser.service.WeixinUserServiceI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.SellerSmsClient;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SaleSmsClient;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

import org.jeecgframework.core.util.ExceptionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 优惠卷
 * @date 2017-05-09 22:03:22
 */
@Controller
@RequestMapping("/tcsbCouponController")
public class TcsbCouponController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TcsbCouponController.class);

    @Autowired
    private TcsbCouponServiceI tcsbCouponService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;
    @Autowired
    private TcsbFullcutTemplateServiceI tcsbFullcutTemplateService;
    @Autowired
    private TcsbShopServiceI tcsbShopService;
    @Autowired
    private WeixinUserServiceI weixinUserService;
    @Autowired
    private TcsbShopSmsServiceServiceI tcsbShopSmsServiceService;


    /**
     * 优惠卷列表 页面跳转
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) throws ParseException {

        //获取有效期小于当前日期的优惠券
        CriteriaQuery cq = new CriteriaQuery(TcsbCouponEntity.class);
        Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd");
        cq.lt("expiryDate", DateUtils.parseDate(DateUtils.date_sdf.format(calSrc.getTime()), "yyyy-MM-dd"));
        cq.eq("useStatus", "0");
        cq.add();
        List<TcsbCouponEntity> tcsbCouponlist = tcsbCouponService.getListByCriteriaQuery(cq, false);
        for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponlist) {
            tcsbCouponEntity.setUseStatus("2");
            try {
                tcsbCouponService.saveOrUpdate(tcsbCouponEntity);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return new ModelAndView("com/tcsb/coupon/tcsbCouponList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TcsbCouponEntity tcsbCoupon, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
        CriteriaQuery cq = new CriteriaQuery(TcsbCouponEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbCoupon, request.getParameterMap());
        try {
            //自定义追加查询条件
            TSUser user = getCurrentUser();
            if (!checkAdmin()) {
                cq.eq("shopId", user.getShopId());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tcsbCouponService.getDataGridReturn(cq, true);


        List<TcsbCouponEntity> TcsbCouponEntityList = dataGrid.getResults();
        List<TcsbCouponEntityVo> TcsbCouponEntityVoList = new ArrayList<>();
        TcsbCouponEntityVo TcsbCouponEntityVo;
        for (TcsbCouponEntity TcsbCouponEntity : TcsbCouponEntityList) {
            TcsbCouponEntityVo = new TcsbCouponEntityVo();
            BeanUtils.copyProperties(TcsbCouponEntityVo, TcsbCouponEntity);
            TcsbCouponEntityVoList.add(TcsbCouponEntityVo);
        }

        int i = 0;
        for (TcsbCouponEntityVo tcsbDiscountActivityVo : TcsbCouponEntityVoList) {
            TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbCouponService.get(TcsbFullcutTemplateEntity.class, tcsbDiscountActivityVo.getFullcutTemplateId());
            if (StringUtil.isNotEmpty(tcsbFullcutTemplateEntity)) {
                TcsbCouponEntityVoList.get(i).setFullcutTemplateIdName("满" + tcsbFullcutTemplateEntity.getTotal() + "减" + tcsbFullcutTemplateEntity.getDiscount());
            }

            TcsbShopEntity tcsbShopEntity = tcsbCouponService.get(TcsbShopEntity.class, tcsbDiscountActivityVo.getShopId());
            if (StringUtil.isNotEmpty(tcsbShopEntity)) {
                TcsbCouponEntityVoList.get(i).setShopName(tcsbShopEntity.getName());
            }
            String userhql = "from TcsbWeixinUserEntity where openid='" + tcsbDiscountActivityVo.getUserId() + "'";
            TcsbWeixinUserEntity weixinUserEntity = tcsbCouponService.singleResult(userhql);

            if (StringUtil.isNotEmpty(weixinUserEntity)) {
                //tcsbDiscountActivityVo.setUserId(weixinUserEntity.getId());
                TcsbCouponEntityVoList.get(i).setUserName(weixinUserEntity.getNickname());  
            }
            i++;
        }
        dataGrid.setResults(TcsbCouponEntityVoList);

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除优惠卷
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TcsbCouponEntity tcsbCoupon, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tcsbCoupon = systemService.getEntity(TcsbCouponEntity.class, tcsbCoupon.getId());
        message = "优惠卷删除成功";
        try {
            tcsbCouponService.delete(tcsbCoupon);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "优惠卷删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除优惠卷
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "优惠卷删除成功";
        try {
            for (String id : ids.split(",")) {
                TcsbCouponEntity tcsbCoupon = systemService.getEntity(TcsbCouponEntity.class,
                        id
                );
                tcsbCouponService.delete(tcsbCoupon);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "优惠卷删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加优惠卷
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TcsbCouponEntity tcsbCoupon, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "优惠卷添加成功";
        try {
            TSUser user = getCurrentUser();
            //查询商家店铺
            TcsbShopEntity tcsbShopEntity = (TcsbShopEntity) tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
            //未使用
            tcsbCoupon.setUseStatus("0");
            tcsbCoupon.setShopId(tcsbShopEntity.getId());
            tcsbCoupon.setCouponType("0");
            message = tcsbCouponService.saveTcsbCouponAndSendSms(tcsbCoupon, tcsbShopEntity);
            //设置优惠卷的过期时间
            TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbCoupon.getFullcutTemplateId());
            Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
            String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();
            int days = 0;
            //todo
            if (dateUnit.equals("year")) {
                days = userPeriod * DateUtils.getCurrentYearDays();
            } else if (dateUnit.equals("month")) {
                days = userPeriod * DateUtils.getCurrentMonthDay();
            } else {
                days = userPeriod;
            }
            String expiryDate = DateUtils.getAfterDayDate(String.valueOf(days));
            tcsbCoupon.setExpiryDate(DateUtils.parseDate(expiryDate, "yyyy-MM-dd HH:mm:ss"));
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "优惠卷添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新优惠卷
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TcsbCouponEntity tcsbCoupon, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "优惠卷更新成功";
        TcsbCouponEntity t = tcsbCouponService.get(TcsbCouponEntity.class, tcsbCoupon.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tcsbCoupon, t);
            tcsbCouponService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "优惠卷更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 优惠卷新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TcsbCouponEntity tcsbCoupon, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbCoupon.getId())) {
            tcsbCoupon = tcsbCouponService.getEntity(TcsbCouponEntity.class, tcsbCoupon.getId());
            req.setAttribute("tcsbCouponPage", tcsbCoupon);
        }
        TSUser tsUser = getCurrentUser();
        //显示要显示的模版
        List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = tcsbFullcutTemplateService.findHql("from TcsbFullcutTemplateEntity where shopId = ? and isShow = ? and couponType='0'", tsUser.getShopId(), "1");
        req.setAttribute("tcsbFullcutTemplateEntities", tcsbFullcutTemplateEntities);
        return new ModelAndView("com/tcsb/coupon/tcsbCoupon-add");
    }


    /**
     * 优惠卷新增页面跳转(群发入口)
     *
     * @param ids 要发送的用户手ID，串格式如单个用户：(121321)-多用户：(123,321,...)
     * @param req
     * @return
     */
    @RequestMapping(params = "goSendCoupon")
    public ModelAndView goSendCoupon(String ids, HttpServletRequest req) {
        String[] useridsarray = ids.split(",");
        String openids = "";
        for (int i = 0; i < useridsarray.length; i++) {
            WeixinUserEntity weixinUserEntity = weixinUserService.get(WeixinUserEntity.class, useridsarray[i]);
            if (StringUtil.isNotEmpty(weixinUserEntity)) {
                openids = openids + weixinUserEntity.getOpenid() + ",";
            } else {
                TcsbAssociatorBigdataEntity tcsbAssociatorBigdataEntity = tcsbFullcutTemplateService.get(TcsbAssociatorBigdataEntity.class, useridsarray[i]);
                openids = openids + tcsbAssociatorBigdataEntity.getUserId() + ",";
            }
        }

        req.setAttribute("ids", openids.substring(0, openids.length() - 1));
        TSUser tsUser = getCurrentUser();
        String hql = "from TcsbFullcutTemplateEntity where shopId='" + tsUser.getShopId() + "' and isShow='1'";
        List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = tcsbFullcutTemplateService.findByQueryString(hql);
        req.setAttribute("tcsbFullcutTemplateEntities", tcsbFullcutTemplateEntities);
        return new ModelAndView("com/tcsb/coupon/goSendCoupon");
    }


    /**
     * 获取要发送的优惠卷  并转发给多个用户
     *
     * @param fullcutTemplateId 模板id
     * @param ids           多个用户id连接串
     * @param request
     * @return
     */
    @RequestMapping(params = "sendCoupon")
    @ResponseBody
    public AjaxJson sendCoupon(String fullcutTemplateId, String ids, String useRange, String foodTypeId, String foodId, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "优惠卷添加成功";
        try {
            TSUser user = getCurrentUser();
            //查询商家店铺
            TcsbShopEntity tcsbShopEntity = (TcsbShopEntity) tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
            String[] useridsarray = ids.split(",");
            if (useridsarray.length > 0) {
                //发送的用户不为空
                //检测商家要发送短信剩余条数是否足够
                TcsbShopSmsServiceEntity tcsbShopSmsServiceEntity = tcsbShopService.findUniqueByProperty(TcsbShopSmsServiceEntity.class, "shopId", tcsbShopEntity.getId());
                if (tcsbShopSmsServiceEntity != null) {
                    int count = tcsbShopSmsServiceEntity.getCount();
                    if (count < useridsarray.length) {
                        message = "短信剩余" + count + "条,不足" + useridsarray.length + "条";
                    } else {
                        //短信条数足够，群发优惠券
                        try {
                            for (int i = 0; i < useridsarray.length; i++) {
                                /////////保存发送的优惠券信息，并发送短信提醒
                                //保存用户优惠价信息
                                //根据用户手机号获取用户ID
                                List<WeixinUserEntity> weixinUserEntitys = systemService.findHql("from WeixinUserEntity where openid =? and shopId = ?", useridsarray[i], tcsbShopEntity.getId());
                                TcsbCouponEntity tcsbCoupon = new TcsbCouponEntity();
                                tcsbCoupon.setUserId(weixinUserEntitys.get(0).getOpenid());
                                tcsbCoupon.setShopId(tcsbShopEntity.getId());
                                tcsbCoupon.setUseStatus("0");
                                TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbShopService.get(TcsbFullcutTemplateEntity.class, fullcutTemplateId);
                                tcsbCoupon.setFullcutTemplateId(fullcutTemplateId);
                                Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
                                String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();
                                int days = 0;
                                //todo
                                if (dateUnit.equals("year")) {
                                    days = userPeriod * DateUtils.getCurrentYearDays();
                                } else if (dateUnit.equals("month")) {
                                    days = userPeriod * DateUtils.getCurrentMonthDay();
                                } else {
                                    days = userPeriod;
                                }
                                String expiryDate = DateUtils.getAfterDayDate(String.valueOf(days));
                                tcsbCoupon.setExpiryDate(DateUtils.parseDate(expiryDate, "yyyy-MM-dd HH:mm:ss"));
                                tcsbCoupon.setUseRange(useRange);
                                if (useRange.equals("1")) {
                                    tcsbCoupon.setFoodTypeId(foodTypeId);
                                    tcsbCoupon.setFoodId(foodId);
                                }
                                tcsbShopService.save(tcsbCoupon);//保持信息
                                //获取优惠券信息

                                //int userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
                                //String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();
                                if (dateUnit.equals("month")) {
                                    dateUnit = "个月";
                                }
                                if (dateUnit.equals("year")) {
                                    dateUnit = "年";
                                }
                                if (dateUnit.equals("day")) {
                                    dateUnit = "日";
                                }
                                //拼接发送的短信内容
                                String content = "恭喜您获得" + tcsbShopEntity.getName() + "的" + tcsbFullcutTemplateEntity.getDiscount() + "元优惠券一张，有效期" + userPeriod + "" + dateUnit + "，欢迎到店使用。";

                                TcsbWeixinUserEntity tcsbWeixinUserEntity = tcsbShopService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", weixinUserEntitys.get(0).getOpenid());
                                if (StringUtils.isNotEmpty(tcsbWeixinUserEntity.getMobile())) {
                                    SaleSmsClient.sendMessageForContent(tcsbWeixinUserEntity.getMobile(), content);//发送短信
                                }

                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        tcsbShopSmsServiceEntity.setCount(tcsbShopSmsServiceEntity.getCount() - useridsarray.length);
                        tcsbShopService.updateEntitie(tcsbShopSmsServiceEntity);
                    }
                }

            } else {
                message = "发送的用户为空";
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "优惠卷添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 优惠卷编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TcsbCouponEntity tcsbCoupon, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbCoupon.getId())) {
            tcsbCoupon = tcsbCouponService.getEntity(TcsbCouponEntity.class, tcsbCoupon.getId());
            req.setAttribute("tcsbCouponPage", tcsbCoupon);
        }
        //设置初始值
        String ids = tcsbCoupon.getUserId();
        String nickname = "";
//        if (ids.contains(",")) {
//            String[] arr = ids.split(",");
//            for (int i = 0; i < arr.length; i++) {
//                //WeixinUserEntity weixinUserEntity = systemService.get(WeixinUserEntity.class, arr[i]);
//                List<WeixinUserEntity> weixinUserEntities = systemService.findHql("from WeixinUserEntity where openid = ?", ids);
//                if (i < arr.length - 1) {
//                    //nickname+=weixinUserEntities.get(0).getNickname()+",";
//                } else {
//                    //nickname+=weixinUserEntities.get(0).getNickname();
//                }
//            }
//
//        } else {
        WeixinUserEntity weixinUserEntity = systemService.singleResult("from WeixinUserEntity where openid = '"+ ids+"' and shopId='"+tcsbCoupon.getShopId()+"'");
        //nickname = weixinUserEntities.get(0).getNickname();
        if(StringUtils.isNotEmpty(weixinUserEntity.getOpenid())){
            TcsbWeixinUserEntity tcsbWeixinUserEntity = systemService.findUniqueByProperty(TcsbWeixinUserEntity.class,"openid",weixinUserEntity.getOpenid());
            nickname = tcsbWeixinUserEntity.getNickname();
        }
//        }
        req.setAttribute("openid", ids);
        req.setAttribute("nickname", nickname);

        //
        TSUser tsUser = getCurrentUser();
        List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = tcsbFullcutTemplateService.findByProperty(TcsbFullcutTemplateEntity.class, "shopId", tsUser.getShopId());
        req.setAttribute("tcsbFullcutTemplateEntities", tcsbFullcutTemplateEntities);
        return new ModelAndView("com/tcsb/coupon/tcsbCoupon-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tcsbCouponController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TcsbCouponEntity tcsbCoupon, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TcsbCouponEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbCoupon, request.getParameterMap());
        List<TcsbCouponEntity> tcsbCoupons = this.tcsbCouponService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "优惠卷");
        modelMap.put(NormalExcelConstants.CLASS, TcsbCouponEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("优惠卷列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tcsbCoupons);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TcsbCouponEntity tcsbCoupon, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "优惠卷");
        modelMap.put(NormalExcelConstants.CLASS, TcsbCouponEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("优惠卷列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TcsbCouponEntity> listTcsbCouponEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TcsbCouponEntity.class, params);
                for (TcsbCouponEntity tcsbCoupon : listTcsbCouponEntitys) {
                    tcsbCouponService.save(tcsbCoupon);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<TcsbCouponEntity> list() {
        List<TcsbCouponEntity> listTcsbCoupons = tcsbCouponService.getList(TcsbCouponEntity.class);
        return listTcsbCoupons;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        TcsbCouponEntity task = tcsbCouponService.get(TcsbCouponEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody TcsbCouponEntity tcsbCoupon, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbCouponEntity>> failures = validator.validate(tcsbCoupon);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbCouponService.save(tcsbCoupon);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = tcsbCoupon.getId();
        URI uri = uriBuilder.path("/rest/tcsbCouponController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TcsbCouponEntity tcsbCoupon) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbCouponEntity>> failures = validator.validate(tcsbCoupon);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbCouponService.saveOrUpdate(tcsbCoupon);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        //按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        tcsbCouponService.deleteEntityById(TcsbCouponEntity.class, id);
    }

    /**
     * 获取我的优惠券
     *
     * @param userId
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/getMyCoupon", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONPObject getMyCoupon(@RequestParam String userId, @RequestParam String status, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String callbackFunName = request.getParameter("callbackparam");//得到js函数名称
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        List<TcsbCouponEntity> tcsbCouponEntities = tcsbCouponService.findHql("from TcsbCouponEntity where userId = ?", userId);
        List<TcsbUserCouponVO> tcsbUserCouponVOs = new ArrayList<>();
        for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
            TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbCouponEntity.getShopId());
            //获取活动生效的时间
            Date beginTime = tcsbCouponEntity.getCreateDate();
            TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
            Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
            String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();
            int days = 0;
            //todo
            if (dateUnit.equals("year")) {
                days = userPeriod * DateUtils.getCurrentYearDays();
            } else if (dateUnit.equals("month")) {
                days = userPeriod * DateUtils.getCurrentMonthDay();
            } else {
                days = userPeriod;
            }
            //未使用
            if ("0".equals(status)) {
                Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                if (DateUtils.dateDiff('s', calSrc, calDes) < 0 && tcsbCouponEntity.getUseStatus().equals("0")) {
                    TcsbUserCouponVO tcsbUserCouponVO = new TcsbUserCouponVO();
                    tcsbUserCouponVO.setShopId(tcsbShopEntity.getId());
                    tcsbUserCouponVO.setShopName(tcsbShopEntity.getName());
                    tcsbUserCouponVO.setFullDiscount(tcsbFullcutTemplateEntity.getTotal());
                    tcsbUserCouponVO.setDiscountDiscount(tcsbFullcutTemplateEntity.getDiscount());
                    tcsbUserCouponVO.setExpiryDate(DateUtils.format(tcsbCouponEntity.getExpiryDate(), "yyyy-MM-dd HH:mm:ss"));
                    tcsbUserCouponVOs.add(tcsbUserCouponVO);
                }

            }
            //已使用
            else if ("1".equals(status)) {
                if (tcsbCouponEntity.getUseStatus().equals("1")) {
                    TcsbUserCouponVO tcsbUserCouponVO = new TcsbUserCouponVO();
                    tcsbUserCouponVO.setShopId(tcsbShopEntity.getId());
                    tcsbUserCouponVO.setShopName(tcsbShopEntity.getName());
                    tcsbUserCouponVO.setFullDiscount(tcsbFullcutTemplateEntity.getTotal());
                    tcsbUserCouponVO.setDiscountDiscount(tcsbFullcutTemplateEntity.getDiscount());
                    tcsbUserCouponVO.setExpiryDate(DateUtils.format(tcsbCouponEntity.getExpiryDate(), "yyyy-MM-dd HH:mm:ss"));
                    tcsbUserCouponVOs.add(tcsbUserCouponVO);
                }
            }
            //已过期
            else {
                if (tcsbCouponEntity.getUseStatus().equals("2")) {
                    TcsbUserCouponVO tcsbUserCouponVO = new TcsbUserCouponVO();
                    tcsbUserCouponVO.setShopId(tcsbShopEntity.getId());
                    tcsbUserCouponVO.setShopName(tcsbShopEntity.getName());
                    tcsbUserCouponVO.setFullDiscount(tcsbFullcutTemplateEntity.getTotal());
                    tcsbUserCouponVO.setDiscountDiscount(tcsbFullcutTemplateEntity.getDiscount());
                    tcsbUserCouponVO.setExpiryDate(DateUtils.format(tcsbCouponEntity.getExpiryDate(), "yyyy-MM-dd HH:mm:ss"));
                    tcsbUserCouponVOs.add(tcsbUserCouponVO);
                } else {
                    Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                    Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                    if (DateUtils.dateDiff('s', calSrc, calDes) >= 0 && tcsbCouponEntity.getUseStatus().equals("0")) {
                        TcsbUserCouponVO tcsbUserCouponVO = new TcsbUserCouponVO();
                        tcsbUserCouponVO.setShopId(tcsbShopEntity.getId());
                        tcsbUserCouponVO.setShopName(tcsbShopEntity.getName());
                        tcsbUserCouponVO.setFullDiscount(tcsbFullcutTemplateEntity.getTotal());
                        tcsbUserCouponVO.setDiscountDiscount(tcsbFullcutTemplateEntity.getDiscount());
                        tcsbUserCouponVO.setExpiryDate(DateUtils.format(tcsbCouponEntity.getExpiryDate(), "yyyy-MM-dd HH:mm:ss"));
                        tcsbUserCouponVOs.add(tcsbUserCouponVO);
                        tcsbCouponEntity.setUseStatus("2");
                        systemService.saveOrUpdate(tcsbCouponEntity);
                    }
                }

            }
        }
        ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setObj(tcsbUserCouponVOs);
        return new JSONPObject(callbackFunName, ajaxJsonApi);
    }


    /**
     * 获取优惠卷授权登录url
     *
     * @param request
     * @param response
     * @param domain 域名 不带http://
     * @param shopId 店铺id
     * @return
     */
    @RequestMapping(params = "getCouponAuthUrl")
    @ResponseBody
    public AjaxJsonApi getCouponAuthUrl(HttpServletRequest request, HttpServletResponse response, String domain, String shopId) {
        AjaxJsonApi ajaxJson =  tcsbCouponService.getCouponAuthUrl(domain,shopId);
        return ajaxJson;
    }


    /**
     * 网页领取优惠卷
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "receiveCouponByWeb")
    @ResponseBody
    public JSONPObject receiveCouponByWeb(HttpServletRequest request, HttpServletResponse response,String mobile, String fullcutTemplateId) {
        String callbackFunName =request.getParameter("callbackparam");//得到js函数名称

//        String callbackFunName =request.getParameter("callback");//得到js函数名称
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        AjaxJsonApi ajaxJson = null;
        try {
            ajaxJson = tcsbCouponService.receiveCouponByWeb(mobile,fullcutTemplateId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONPObject(callbackFunName, ajaxJson);
    }


//    /**
//     * 根据授权微信code注册用户
//     *
//     * @param request
//     * @param response
//     * @param domain 域名 不带http://
//     * @param shopId 店铺id
//     * @return
//     */
//    @RequestMapping(params = "getCouponAuthUrl")
//    @ResponseBody
//    public AjaxJsonApi getCouponAuthUrl(HttpServletRequest request, HttpServletResponse response, String domain, String shopId) {
//        AjaxJsonApi ajaxJson =  tcsbCouponService.getCouponAuthUrl(domain,shopId);
//        return ajaxJson;
//    }

}
