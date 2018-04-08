package com.tcsb.memberlevelequity.controller;

import com.tcsb.memberlevelconditions.entity.TcsbMemberLevelConditionsEntity;
import com.tcsb.memberlevelconditions.service.TcsbMemberLevelConditionsServiceI;
import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;
import com.tcsb.memberlevelequity.service.TcsbMemberLevelEquityServiceI;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcsb.memberlevelequity.vo.TcsbMemberLevelEquityVO;
import com.tcsb.membershiplevel.entity.TcsbMembershipLevelEntity;
import com.tcsb.membershiplevel.service.TcsbMembershipLevelServiceI;
import com.tcsb.shop.entity.TcsbShopEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

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
 * @Description: 会员级别权益
 * @date 2017-11-06 15:11:17
 */
@Controller
@RequestMapping("/tcsbMemberLevelEquityController")
public class TcsbMemberLevelEquityController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TcsbMemberLevelEquityController.class);

    @Autowired
    private TcsbMemberLevelEquityServiceI tcsbMemberLevelEquityService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    @Autowired
    private TcsbMembershipLevelServiceI tcsbMembershipLevelService;


    /**
     * 会员级别权益列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/tcsb/memberlevelequity/tcsbMemberLevelEquityList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws InvocationTargetException, IllegalAccessException {
        CriteriaQuery cq = new CriteriaQuery(TcsbMemberLevelEquityEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMemberLevelEquity, request.getParameterMap());
        TSUser user = getCurrentUser();
        try {
            //自定义追加查询条件
            if (!checkAdmin()) {
                cq.eq("shopId", user.getShopId());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tcsbMemberLevelEquityService.getDataGridReturn(cq, true);

        List<TcsbMemberLevelEquityEntity> result = dataGrid.getResults();
        List<TcsbMemberLevelEquityVO> vos = new ArrayList<TcsbMemberLevelEquityVO>();
        for (TcsbMemberLevelEquityEntity one : result) {
            TcsbMemberLevelEquityVO vo = new TcsbMemberLevelEquityVO();
            BeanUtils.copyProperties(vo, one);
            if (StringUtil.isNotEmpty(one.getShopId())) {
                TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, one.getShopId());
                vo.setShopName(shopEntity.getName());
            }
//			if (StringUtil.isNotEmpty(one.getMembershipLevelId())) {
//				TcsbMembershipLevelEntity csbMembershipLevelEntity = tcsbMembershipLevelService.getEntity(TcsbMembershipLevelEntity.class,one.getMembershipLevelId());
//				vo.setMembershipLevelName(csbMembershipLevelEntity.getName());
//
//			}
            vos.add(vo);
        }
        dataGrid.setResults(vos);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除会员级别权益
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tcsbMemberLevelEquity = systemService.getEntity(TcsbMemberLevelEquityEntity.class, tcsbMemberLevelEquity.getId());


        message = "会员级别权益删除成功";
        try {
            tcsbMemberLevelEquityService.delete(tcsbMemberLevelEquity);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别权益删除失败";
            throw new BusinessException(e.getMessage());
        }


        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除会员级别权益
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别权益删除成功";
        try {
            for (String id : ids.split(",")) {
                TcsbMemberLevelEquityEntity tcsbMemberLevelEquity = systemService.getEntity(TcsbMemberLevelEquityEntity.class,
                        id
                );
                tcsbMemberLevelEquityService.delete(tcsbMemberLevelEquity);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别权益删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加会员级别权益
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别权益添加成功";
        TSUser user = getCurrentUser();
        try {
            List<TcsbMemberLevelEquityEntity> list = systemService.findHql("from TcsbMemberLevelEquityEntity where name=? and shopId=?", tcsbMemberLevelEquity.getName(), user.getShopId());
            if (list.isEmpty()) {
                TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, user.getShopId());
                tcsbMemberLevelEquity.setShopId(shopEntity.getId());
                tcsbMemberLevelEquityService.save(tcsbMemberLevelEquity);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }else {
                message = "该会员级别权益已存在";
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别权益添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新会员级别权益
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别权益更新成功";
        TcsbMemberLevelEquityEntity t = tcsbMemberLevelEquityService.get(TcsbMemberLevelEquityEntity.class, tcsbMemberLevelEquity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tcsbMemberLevelEquity, t);
            tcsbMemberLevelEquityService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别权益更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 会员级别权益新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, HttpServletRequest req) {
        TSUser user = getCurrentUser();
        if (StringUtil.isNotEmpty(tcsbMemberLevelEquity.getId())) {
            tcsbMemberLevelEquity = tcsbMemberLevelEquityService.getEntity(TcsbMemberLevelEquityEntity.class, tcsbMemberLevelEquity.getId());
            req.setAttribute("tcsbMemberLevelEquityPage", tcsbMemberLevelEquity);
        }
        List<TcsbMembershipLevelEntity> tcsbMembershipLevelEntitys = null;
        if (StringUtil.isNotEmpty(user.getShopId())) {
            tcsbMembershipLevelEntitys = tcsbMembershipLevelService.findByProperty(TcsbMembershipLevelEntity.class, "shopId", user.getShopId());
            req.setAttribute("tcsbMembershipLevelEntitys", tcsbMembershipLevelEntitys);
        }
        return new ModelAndView("com/tcsb/memberlevelequity/tcsbMemberLevelEquity-add");
    }

    /**
     * 会员级别权益编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, HttpServletRequest req) {
        TSUser user = getCurrentUser();
        if (StringUtil.isNotEmpty(tcsbMemberLevelEquity.getId())) {
            tcsbMemberLevelEquity = tcsbMemberLevelEquityService.getEntity(TcsbMemberLevelEquityEntity.class, tcsbMemberLevelEquity.getId());
            req.setAttribute("tcsbMemberLevelEquityPage", tcsbMemberLevelEquity);
        }
        List<TcsbMembershipLevelEntity> tcsbMembershipLevelEntitys = null;
        if (StringUtil.isNotEmpty(user.getShopId())) {
            tcsbMembershipLevelEntitys = tcsbMembershipLevelService.findByProperty(TcsbMembershipLevelEntity.class, "shopId", user.getShopId());
            req.setAttribute("tcsbMembershipLevelEntitys", tcsbMembershipLevelEntitys);
        }
        return new ModelAndView("com/tcsb/memberlevelequity/tcsbMemberLevelEquity-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tcsbMemberLevelEquityController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TcsbMemberLevelEquityEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMemberLevelEquity, request.getParameterMap());
        List<TcsbMemberLevelEquityEntity> tcsbMemberLevelEquitys = this.tcsbMemberLevelEquityService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "会员级别权益");
        modelMap.put(NormalExcelConstants.CLASS, TcsbMemberLevelEquityEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会员级别权益列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tcsbMemberLevelEquitys);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "会员级别权益");
        modelMap.put(NormalExcelConstants.CLASS, TcsbMemberLevelEquityEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会员级别权益列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
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
                List<TcsbMemberLevelEquityEntity> listTcsbMemberLevelEquityEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TcsbMemberLevelEquityEntity.class, params);
                for (TcsbMemberLevelEquityEntity tcsbMemberLevelEquity : listTcsbMemberLevelEquityEntitys) {
                    tcsbMemberLevelEquityService.save(tcsbMemberLevelEquity);
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
    public List<TcsbMemberLevelEquityEntity> list() {
        List<TcsbMemberLevelEquityEntity> listTcsbMemberLevelEquitys = tcsbMemberLevelEquityService.getList(TcsbMemberLevelEquityEntity.class);
        return listTcsbMemberLevelEquitys;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        TcsbMemberLevelEquityEntity task = tcsbMemberLevelEquityService.get(TcsbMemberLevelEquityEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody TcsbMemberLevelEquityEntity tcsbMemberLevelEquity, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbMemberLevelEquityEntity>> failures = validator.validate(tcsbMemberLevelEquity);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbMemberLevelEquityService.save(tcsbMemberLevelEquity);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = tcsbMemberLevelEquity.getId();
        URI uri = uriBuilder.path("/rest/tcsbMemberLevelEquityController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TcsbMemberLevelEquityEntity tcsbMemberLevelEquity) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbMemberLevelEquityEntity>> failures = validator.validate(tcsbMemberLevelEquity);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbMemberLevelEquityService.saveOrUpdate(tcsbMemberLevelEquity);
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
        tcsbMemberLevelEquityService.deleteEntityById(TcsbMemberLevelEquityEntity.class, id);
    }
}
