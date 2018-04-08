package com.weixin.weixinuser.controller;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.tcsb.userorder.service.TcsbUserOrderServiceI;
import com.weixin.core.entity.common.User;
import com.weixin.util.DateUtils;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import com.weixin.weixinuser.service.WeixinUserServiceI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.weixinuser.vo.TcsbOrderManageWeixinUserVO;
import com.weixin.weixinuser.vo.TcsbWeixinManageUserVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.p3.core.common.utils.DateUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
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
import org.jeecgframework.core.util.SellerSmsClient;
import org.jeecgframework.core.util.oConvertUtils;

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
 * @Description: 微信用户
 * @date 2017-04-11 15:59:51
 */
@Controller
@RequestMapping("/weixinUserController")
public class WeixinUserController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WeixinUserController.class);

    @Autowired
    private WeixinUserServiceI weixinUserService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;
    @Autowired
    private TcsbShopServiceI tcsbShopService;
    @Autowired
    private TcsbUserOrderServiceI tcsbUserOrderService;


    @RequestMapping(params = "retundpage")
    public ModelAndView retundpage(HttpServletRequest request) {
        return new ModelAndView("com/tcsb/refund/refund");
    }


    /**
     * 微信用户列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/weixin/weixinuser/weixinUserList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TcsbWeixinManageUserVO weixinUserEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = getCurrentUser();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT w.ID AS ID,w.shop_id AS shopId,IFNULL(wu.mobile, \"\") AS mobile, IFNULL(wu.nickname, \"\") AS nickname, IFNULL(wu.sex, \"\") AS sex, IFNULL(w.note, \"\") AS note, IFNULL(wu.city, \"\") AS city, IFNULL(wu.headimgurl, \"\") AS headimgurl, ");
        if (checkAdmin()) {
            sql.append("IFNULL(wu.create_time, \"\")");
        } else {
            sql.append("IFNULL(min(w.create_time), \"\")");
        }
        sql.append(" AS createTime FROM tcsb_weixin_user wu LEFT JOIN weixin_user w ON w.openid = wu.openid  ");
        sql.append("where 1=1 ");
        if (!checkAdmin()) {
            sql.append("and w.shop_id='" + user.getShopId() + "' ");
        }
        if (StringUtils.isNotEmpty(request.getParameter("mobile"))) {
            sql.append(" and wu.mobile= '" + request.getParameter("mobile") + "'");
        }
        
        if (StringUtils.isNotEmpty(request.getParameter("isMember"))) {
        	if(request.getParameter("isMember").equals("member")){
        		 sql.append(" and wu.mobile is not null");
        	}
        }
        
       
        sql.append(" GROUP BY wu.openid");
        StringBuilder sqlCount = new StringBuilder(sql);
        sqlCount.insert(0, "select count(*) as count from(");
        sqlCount.append(")cou");

        List<Map<String, Object>> countList = weixinUserService.findForJdbc(sqlCount.toString());
        int count = Integer.parseInt(String.valueOf(countList.get(0).get("count")));
        sql.append(" ORDER BY w.create_time DESC ");
        sql.append(" limit ");
        sql.append((dataGrid.getPage() - 1) * dataGrid.getRows());
        sql.append(",");
        sql.append(String.valueOf(dataGrid.getRows()));
        List<Map<String, Object>> weixinUserEntityListDto = weixinUserService.findForJdbc(sql.toString());
//		for(Map<String,Object> map : weixinUserEntityListDto){
//			if(map.get("shopId")!=null){
//				TcsbShopEntity shop = weixinUserService.get(TcsbShopEntity.class,(String)map.get("shopId"));
//				if(shop!=null){
//					map.put("shopName",shop.getName());
//				}
//			}
//		}
        dataGrid.setTotal(count);
        dataGrid.setResults(weixinUserEntityListDto);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除微信用户
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(WeixinUserEntity weixinUser, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        weixinUser = systemService.getEntity(WeixinUserEntity.class, weixinUser.getId());
        message = "微信用户删除成功";
        try {
            weixinUserService.delete(weixinUser);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "微信用户删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除微信用户
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "微信用户删除成功";
        try {
            for (String id : ids.split(",")) {
                WeixinUserEntity weixinUser = systemService.getEntity(WeixinUserEntity.class,
                        id
                );
                weixinUserService.delete(weixinUser);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "微信用户删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加微信用户
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(WeixinUserEntity weixinUser, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "微信用户添加成功";
        try {
            weixinUserService.save(weixinUser);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "微信用户添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新微信用户
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TcsbWeixinManageUserVO weixinUser, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "微信用户更新成功";
        WeixinUserEntity weixinUserEntity = weixinUserService.get(WeixinUserEntity.class, weixinUser.getId());
        try {
            weixinUserEntity.setNote(weixinUser.getNote());
            weixinUserService.saveOrUpdate(weixinUserEntity);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "微信用户更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 微信用户新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(WeixinUserEntity weixinUser, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(weixinUser.getId())) {
            weixinUser = weixinUserService.getEntity(WeixinUserEntity.class, weixinUser.getId());
            req.setAttribute("weixinUserPage", weixinUser);
        }
        return new ModelAndView("com/weixin/weixinuser/weixinUser-add");
    }

    /**
     * 微信用户编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TcsbWeixinManageUserVO tcsbOrderManageWeixinUserVO, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbOrderManageWeixinUserVO.getId())) {
            WeixinUserEntity weixinUserEntity = weixinUserService.getEntity(WeixinUserEntity.class, tcsbOrderManageWeixinUserVO.getId());
            TcsbWeixinUserEntity tcsbWeixinUserEntity = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", weixinUserEntity.getOpenid());
            TcsbWeixinManageUserVO weixinUser = new TcsbWeixinManageUserVO();
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tcsbWeixinUserEntity, weixinUser);
                weixinUser.setId(weixinUserEntity.getId());
                weixinUser.setNote(weixinUserEntity.getNote());
            } catch (Exception e) {
                e.printStackTrace();
            }
            req.setAttribute("weixinUserPage", weixinUser);
        }
        return new ModelAndView("com/weixin/weixinuser/weixinUser-update");
    }

    /**
     * 微信用户选择角色跳转页面
     *
     * @return
     */
    @RequestMapping(params = "weixinUser")
    public ModelAndView weixinUser(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("com/weixin/weixinuser/weixinUsers");
        String ids = oConvertUtils.getString(request.getParameter("ids"));
        mv.addObject("ids", ids);
        return mv;
    }

    /**
     * 订单用户
     *
     * @return
     */
    @RequestMapping(params = "weixinUserByOrder")
    public ModelAndView weixinUserByOrder(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("com/weixin/weixinuser/weixinUserListByOrder");
        String orderId = request.getParameter("orderId");
        mv.addObject("orderId", orderId);
        return mv;
    }

    /**
     * 微信用户显示列表
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridWeixinUser")
    public void datagridWeixinUser(WeixinUserEntity weixinUserEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WeixinUserEntity.class, dataGrid);
        //自定义追加查询条件
        if (!checkAdmin()) {
            TSUser tsUser = getCurrentUser();
            cq.eq("shopId", tsUser.getShopId());
        }
        cq.add();
        //过滤手机号无绑定的会员
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinUserEntity);
        this.systemService.getDataGridReturn(cq, true);

        List<WeixinUserEntity> weixinUserEntityList = dataGrid.getResults();

        List<TcsbWeixinUserEntity> weixinUserEntityVo = new ArrayList<>();

        //WeixinUserEntity weixinUser;

        for (WeixinUserEntity weixinUserEntity2 : weixinUserEntityList) {
        	 //weixinUser = new WeixinUserEntity();
        	TcsbWeixinUserEntity user = systemService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", weixinUserEntity2.getOpenid());
                //BeanUtils.copyProperties(weixinUser, weixinUserEntity2);
                //weixinUser.setMobile(weixinUserEntity2.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
                weixinUserEntityVo.add(user);
        }
        dataGrid.setResults(weixinUserEntityVo);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 订单用户显示列表
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridWeixinUserByOrder")
    public void datagridWeixinUserByOrder(WeixinUserEntity weixinUserEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WeixinUserEntity.class, dataGrid);
        String orderId = request.getParameter("orderId");
        List<TcsbWeixinUserEntity> outTcsbWeixinUserEntities = new ArrayList<>();
        //自定义追加查询条件
        TcsbOrderEntity tcsbOrderEntity = tcsbUserOrderService.get(TcsbOrderEntity.class, orderId);
        if (StringUtil.isNotEmpty(tcsbOrderEntity.getOrderParentId())) {
            List<TcsbUserOrderEntity> userOrderEntities = tcsbUserOrderService.findByProperty(TcsbUserOrderEntity.class, "orderParentId", tcsbOrderEntity.getOrderParentId());
            for (TcsbUserOrderEntity tcsbUserOrderEntity : userOrderEntities) {
                TcsbWeixinUserEntity tcsbWeixinUserEntity = tcsbUserOrderService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", tcsbUserOrderEntity.getUserId());
                TcsbOrderManageWeixinUserVO outEntity = new TcsbOrderManageWeixinUserVO();
                List<WeixinUserEntity> weixinUsers = tcsbUserOrderService.findHql("from WeixinUserEntity where openid=? and shopId=?", tcsbUserOrderEntity.getUserId(), getCurrentUser().getShopId());
                if (weixinUsers.size() > 0) {

                }
                try {
                    MyBeanUtils.copyBeanNotNull2Bean(tcsbWeixinUserEntity, outEntity);
                    if (StringUtil.isNotEmpty(weixinUsers.get(0).getId())) {
                        //为批量发送优惠卷调换位置
                        //userID为微信用户id
                        outEntity.setUserId(tcsbWeixinUserEntity.getId());
                        //ID为用户id
                        outEntity.setId(weixinUsers.get(0).getId());

                    }
                    outTcsbWeixinUserEntities.add(outEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        dataGrid.setTotal(outTcsbWeixinUserEntities.size());
        dataGrid.setResults(outTcsbWeixinUserEntities);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "weixinUserController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(WeixinUserEntity weixinUser, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(WeixinUserEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinUser, request.getParameterMap());
        List<WeixinUserEntity> weixinUsers = this.weixinUserService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "微信用户");
        modelMap.put(NormalExcelConstants.CLASS, WeixinUserEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("微信用户列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, weixinUsers);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(WeixinUserEntity weixinUser, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "微信用户");
        modelMap.put(NormalExcelConstants.CLASS, WeixinUserEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("微信用户列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
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
                List<WeixinUserEntity> listWeixinUserEntitys = ExcelImportUtil.importExcel(file.getInputStream(), WeixinUserEntity.class, params);
                for (WeixinUserEntity weixinUser : listWeixinUserEntitys) {
                    weixinUserService.save(weixinUser);
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
    public List<WeixinUserEntity> list() {
        List<WeixinUserEntity> listWeixinUsers = weixinUserService.getList(WeixinUserEntity.class);
        return listWeixinUsers;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        WeixinUserEntity task = weixinUserService.get(WeixinUserEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody WeixinUserEntity weixinUser, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WeixinUserEntity>> failures = validator.validate(weixinUser);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            weixinUserService.save(weixinUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = weixinUser.getId();
        URI uri = uriBuilder.path("/rest/weixinUserController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody WeixinUserEntity weixinUser) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WeixinUserEntity>> failures = validator.validate(weixinUser);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            weixinUserService.saveOrUpdate(weixinUser);
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
        weixinUserService.deleteEntityById(WeixinUserEntity.class, id);
    }

    //绑定手机验证信息
    @RequestMapping(value = "/checkIsBindMobile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONPObject checkIsBindMobile(@RequestParam String userId, @RequestParam String shopId, HttpServletRequest request, HttpServletResponse response) {
        String callbackFunName = request.getParameter("callbackparam");//得到js函数名称
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        try {
            //检查用户openid是否有绑定过手机号
            List<WeixinUserEntity> weixinUserList = weixinUserService.findByProperty(WeixinUserEntity.class, "openid", userId);
            boolean isband = false;
            String mobile = "";
            for (WeixinUserEntity weixinUserEntity : weixinUserList) {
                /*if(weixinUserEntity.getMobile() != null){
                    isband = true;
					mobile = weixinUserEntity.getMobile();
					break;
				}*/
            }
            if (isband) {
                /////用户绑定过手机号
                //检查用户在该商家是否绑定过手机号
                WeixinUserEntity weixinUser = weixinUserService.getWeixinUserByUserIdAndShopId(userId, shopId);
                if (weixinUser == null) {
                    //用户不存在
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    ajaxJsonApi.setMsg(ReturnMessageEnum.WEIXINUSERNOTEXIT.getMsg());
                    ajaxJsonApi.setSuccess(false);
                    return new JSONPObject(callbackFunName, ajaxJsonApi);
                } else {
                    /*if(weixinUser.getMobile() == null || weixinUser.getMobile().equals("")){
						////用户在该商家没有绑定手机好
						//系统自动帮助用户加绑定手机号
						weixinUser.setMobile(mobile);
						weixinUserService.saveOrUpdate(weixinUser);
					}*/
                    //用户无需在手动绑定手机号
                    ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
                    ajaxJsonApi.setSuccess(true);
                    ajaxJsonApi.setObj(isband);
                }
            } else {
                //用户没有绑定过手机号
                ///提示用户绑定手机号
                ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
                ajaxJsonApi.setSuccess(true);
                ajaxJsonApi.setObj(isband);
            }
			/*WeixinUserEntity weixinUser = weixinUserService.getWeixinUserByUserIdAndShopId(userId, shopId);
				if (weixinUser==null) {
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
					ajaxJsonApi.setMsg(ReturnMessageEnum.WEIXINUSERNOTEXIT.getMsg());
					ajaxJsonApi.setSuccess(false);
					return new JSONPObject(callbackFunName, ajaxJsonApi); 
				}
				boolean isBind= false;
				if(weixinUser.getMobile() == null || weixinUser.getMobile().equals("")){
					ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
					ajaxJsonApi.setSuccess(true);
					ajaxJsonApi.setObj(isBind);
				}else{
					isBind = true;
					ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
					ajaxJsonApi.setSuccess(true);
					ajaxJsonApi.setObj(isBind);
				}*/


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONPObject(callbackFunName, ajaxJsonApi);
    }


    //发送手机验证码
    @RequestMapping(value = "/sendMobileIdentifyingCode", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONPObject sendMobileIdentifyingCode(@RequestParam String userId, @RequestParam String shopId, @RequestParam String mobile, HttpServletRequest request, HttpServletResponse response) {
        String callbackFunName = request.getParameter("callbackparam");//得到js函数名称
        SellerSmsClient client = new SellerSmsClient();
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        String smsCode;
        boolean isSend = false;
        try {

            String hql = "from WeixinUserEntity where mobile='" + mobile + "'";
            List<WeixinUserEntity> isBandweixinUser = weixinUserService.findByQueryString(hql);
            if (isBandweixinUser.size() > 0) {
                ajaxJsonApi.setMsg("手机号已被绑定");
                ajaxJsonApi.setSuccess(true);
                ajaxJsonApi.setObj(isSend);
            } else {
                WeixinUserEntity weixinUser = weixinUserService.getWeixinUserByUserIdAndShopId(userId, shopId);
	        	/*if (StringUtil.isEmpty(weixinUser.getSmscode())) {
	        		smsCode = client.sendMessage(mobile);
	        		weixinUser.setSmscode(smsCode);
	        		weixinUser.setSendTime(new Date());
	        		weixinUserService.saveOrUpdate(weixinUser);
	        		ajaxJsonApi.setMsg(ReturnMessageEnum.SMSISSEND.getMsg());
					ajaxJsonApi.setSuccess(true);
					isSend = true;
					ajaxJsonApi.setObj(isSend);
				}else {
					Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(weixinUser.getSendTime(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
					Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
					if(DateUtils.dateDiff('s', calSrc, calDes)<120){
			        	ajaxJsonApi.setMsg(ReturnMessageEnum.SMSISSEND.getMsg());
						ajaxJsonApi.setSuccess(true);
						ajaxJsonApi.setObj(isSend);
					}else{
						//短信过期重新发送
						smsCode = client.sendMessage(mobile);
						ajaxJsonApi.setMsg(ReturnMessageEnum.SMSISOUTOFDATE.getMsg());
						ajaxJsonApi.setSuccess(true);
						weixinUser.setSmscode(smsCode);
		        		weixinUser.setSendTime(new Date());
		        		isSend = true;
						ajaxJsonApi.setObj(isSend);
		        		weixinUserService.saveOrUpdate(weixinUser);
					}
				}*/

            }
        } catch (IOException e) {
            ajaxJsonApi.setMsg(ReturnMessageEnum.SENDFAILE.getMsg());
            //ajaxJsonApi.setMsg(ReturnMessageEnum.SENDSUCEESS.getMsg());
            ajaxJsonApi.setSuccess(false);
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new JSONPObject(callbackFunName, ajaxJsonApi);
    }

    //绑定手机
    @RequestMapping(value = "/BindMobile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONPObject BindMobile(@RequestParam String userId, @RequestParam String shopId, @RequestParam String mobile,
                                  @RequestParam String smsCode, HttpServletRequest request, HttpServletResponse response) {
        String callbackFunName = request.getParameter("callbackparam");//得到js函数名称
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        boolean isBind = false;

        try {
            String hql = "from WeixinUserEntity where mobile='" + mobile + "'";
            List<WeixinUserEntity> isBandweixinUser = weixinUserService.findByQueryString(hql);
            if (isBandweixinUser.size() > 0) {
                ajaxJsonApi.setMsg("手机号已被绑定");
                ajaxJsonApi.setSuccess(true);
                ajaxJsonApi.setObj(isBind);
            } else {
				/*try {
					WeixinUserEntity weixinUser = weixinUserService.getWeixinUserByUserIdAndShopId(userId, shopId);
					if(weixinUser.getSmscode().equals(smsCode)){
						Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(weixinUser.getSendTime(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
						Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
						System.out.println(DateUtils.dateDiff('s', calSrc, calDes));
						System.out.println("---------------------------------------------------------");
						if(DateUtils.dateDiff('s', calSrc, calDes)<70){
							//验证码通过
							weixinUser.setMobile(mobile);
				        	weixinUserService.saveOrUpdate(weixinUser);
				        	TcsbWeixinUserEntity tcsbWeixinUserEntity = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", weixinUser.getOpenid());
				        	if(StringUtil.isNotEmpty(tcsbWeixinUserEntity)){
				        		ajaxJsonApi.setMsg("绑定成功");
								ajaxJsonApi.setSuccess(true);
								isBind = true;
								ajaxJsonApi.setObj(isBind);
				        	}else{
				        		//平台用户表中没有该会员
				        		TcsbWeixinUserEntity tcsbWeixinUser = new TcsbWeixinUserEntity();
				        		BeanUtils.copyProperties(tcsbWeixinUser, weixinUser);
				        		weixinUserService.save(tcsbWeixinUser);
				        		ajaxJsonApi.setMsg("绑定成功");
								ajaxJsonApi.setSuccess(true);
								isBind = true;
								ajaxJsonApi.setObj(isBind);
				        	}
						}else{
							ajaxJsonApi.setMsg("验证码过期");
							ajaxJsonApi.setSuccess(true);
							ajaxJsonApi.setObj(isBind);
						}
					}else{
						ajaxJsonApi.setMsg("验证码不正确");
						ajaxJsonApi.setSuccess(true);
						ajaxJsonApi.setObj(isBind);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return new JSONPObject(callbackFunName, ajaxJsonApi);
    }

}
