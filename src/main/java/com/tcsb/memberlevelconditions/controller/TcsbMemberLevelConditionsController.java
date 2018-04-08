package com.tcsb.memberlevelconditions.controller;

import com.tcsb.memberlevelconditions.entity.TcsbMemberLevelConditionsEntity;
import com.tcsb.memberlevelconditions.service.TcsbMemberLevelConditionsServiceI;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcsb.memberlevelconditions.vo.TcsbMemberLevelConditionsVO;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopcollect.entity.TcsbShopCollectEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.DataGridReturn;
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
 * @Description: 会员级别条件
 * @date 2017-11-03 16:25:59
 */
@Controller
@RequestMapping("/tcsbMemberLevelConditionsController")
public class TcsbMemberLevelConditionsController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TcsbMemberLevelConditionsController.class);

    @Autowired
    private TcsbMemberLevelConditionsServiceI tcsbMemberLevelConditionsService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;


    /**
     * 会员级别条件列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/tcsb/memberlevelconditions/tcsbMemberLevelConditionsList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
        CriteriaQuery cq = new CriteriaQuery(TcsbMemberLevelConditionsEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMemberLevelConditions, request.getParameterMap());

        TSUser user = getCurrentUser();

        try {
            //自定义追加查询条件

            if (!checkAdmin()) {
                // 查询商家店铺
                cq.eq("shopId", user.getShopId());

            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tcsbMemberLevelConditionsService.getDataGridReturn(cq, true);

        List<TcsbMemberLevelConditionsEntity> memberLevelConditionsEntityList = dataGrid.getResults();
        if (StringUtil.isNotEmpty(memberLevelConditionsEntityList) && memberLevelConditionsEntityList.size() > 0) {
            List<TcsbMemberLevelConditionsVO> menberLevelConditionsVOList = new ArrayList<TcsbMemberLevelConditionsVO>();
            for (TcsbMemberLevelConditionsEntity memberLevelConditionsEntity : memberLevelConditionsEntityList) {
                TcsbMemberLevelConditionsVO memberLevelConditionsVO = new TcsbMemberLevelConditionsVO();
                BeanUtils.copyProperties(memberLevelConditionsVO, memberLevelConditionsEntity);
                if (memberLevelConditionsEntity.getShopId() != null) {
                    TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, memberLevelConditionsEntity.getShopId());
                    memberLevelConditionsVO.setShopName(shopEntity.getName());
                }
                menberLevelConditionsVOList.add(memberLevelConditionsVO);
            }

            dataGrid.setResults(menberLevelConditionsVOList);
        }

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除会员级别条件
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tcsbMemberLevelConditions = systemService.getEntity(TcsbMemberLevelConditionsEntity.class, tcsbMemberLevelConditions.getId());
        message = "会员级别条件删除成功";
        try {
            tcsbMemberLevelConditionsService.delete(tcsbMemberLevelConditions);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别条件删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除会员级别条件
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别条件删除成功";
        try {
            for (String id : ids.split(",")) {
                TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions = systemService.getEntity(TcsbMemberLevelConditionsEntity.class,
                        id
                );
                tcsbMemberLevelConditionsService.delete(tcsbMemberLevelConditions);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别条件删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加会员级别条件
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别条件添加成功";
        TSUser user = getCurrentUser();
        try {
            if (StringUtils.isEmpty(String.valueOf(tcsbMemberLevelConditions.getCharge()))) {
                throw new RuntimeException();
            }

            // 查询商家店铺
            TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, user.getShopId());
            tcsbMemberLevelConditions.setShopId(shopEntity.getId());

            tcsbMemberLevelConditionsService.save(tcsbMemberLevelConditions);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别条件添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新会员级别条件
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别条件更新成功";
        TcsbMemberLevelConditionsEntity t = tcsbMemberLevelConditionsService.get(TcsbMemberLevelConditionsEntity.class, tcsbMemberLevelConditions.getId());
        try {
            if (StringUtils.isEmpty(String.valueOf(tcsbMemberLevelConditions.getCharge()))) {
                throw new RuntimeException();
            }
            MyBeanUtils.copyBeanNotNull2Bean(tcsbMemberLevelConditions, t);
            tcsbMemberLevelConditionsService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别条件更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 会员级别条件新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbMemberLevelConditions.getId())) {
            tcsbMemberLevelConditions = tcsbMemberLevelConditionsService.getEntity(TcsbMemberLevelConditionsEntity.class, tcsbMemberLevelConditions.getId());
            req.setAttribute("tcsbMemberLevelConditionsPage", tcsbMemberLevelConditions);
        }
        return new ModelAndView("com/tcsb/memberlevelconditions/tcsbMemberLevelConditions-add");
    }

    /**
     * 会员级别条件编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbMemberLevelConditions.getId())) {
            tcsbMemberLevelConditions = tcsbMemberLevelConditionsService.getEntity(TcsbMemberLevelConditionsEntity.class, tcsbMemberLevelConditions.getId());
            req.setAttribute("tcsbMemberLevelConditionsPage", tcsbMemberLevelConditions);
        }
        return new ModelAndView("com/tcsb/memberlevelconditions/tcsbMemberLevelConditions-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tcsbMemberLevelConditionsController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TcsbMemberLevelConditionsEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMemberLevelConditions, request.getParameterMap());
        List<TcsbMemberLevelConditionsEntity> tcsbMemberLevelConditionss = this.tcsbMemberLevelConditionsService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "会员级别条件");
        modelMap.put(NormalExcelConstants.CLASS, TcsbMemberLevelConditionsEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会员级别条件列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tcsbMemberLevelConditionss);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "会员级别条件");
        modelMap.put(NormalExcelConstants.CLASS, TcsbMemberLevelConditionsEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会员级别条件列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
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
                List<TcsbMemberLevelConditionsEntity> listTcsbMemberLevelConditionsEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TcsbMemberLevelConditionsEntity.class, params);
                for (TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions : listTcsbMemberLevelConditionsEntitys) {
                    tcsbMemberLevelConditionsService.save(tcsbMemberLevelConditions);
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
    public List<TcsbMemberLevelConditionsEntity> list() {
        List<TcsbMemberLevelConditionsEntity> listTcsbMemberLevelConditionss = tcsbMemberLevelConditionsService.getList(TcsbMemberLevelConditionsEntity.class);
        return listTcsbMemberLevelConditionss;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        TcsbMemberLevelConditionsEntity task = tcsbMemberLevelConditionsService.get(TcsbMemberLevelConditionsEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbMemberLevelConditionsEntity>> failures = validator.validate(tcsbMemberLevelConditions);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbMemberLevelConditionsService.save(tcsbMemberLevelConditions);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = tcsbMemberLevelConditions.getId();
        URI uri = uriBuilder.path("/rest/tcsbMemberLevelConditionsController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TcsbMemberLevelConditionsEntity tcsbMemberLevelConditions) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbMemberLevelConditionsEntity>> failures = validator.validate(tcsbMemberLevelConditions);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbMemberLevelConditionsService.saveOrUpdate(tcsbMemberLevelConditions);
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
        tcsbMemberLevelConditionsService.deleteEntityById(TcsbMemberLevelConditionsEntity.class, id);
    }
}
