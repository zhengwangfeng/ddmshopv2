package com.tcsb.membershiplevel.controller;

import com.tcsb.memberlevelconditions.entity.TcsbMemberLevelConditionsEntity;
import com.tcsb.memberlevelconditions.service.TcsbMemberLevelConditionsServiceI;
import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;
import com.tcsb.membershiplevel.entity.TcsbMembershipLevelEntity;
import com.tcsb.membershiplevel.service.TcsbMembershipLevelServiceI;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcsb.membershiplevel.vo.TcsbMembershipLevelVO;
import com.tcsb.membertitle.entity.TcsbMemberTitleEntity;
import com.tcsb.membertitle.service.TcsbMemberTitleServiceI;
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
 * @Description: 会员级别
 * @date 2017-11-03 16:11:55
 */
@Controller
@RequestMapping("/tcsbMembershipLevelController")
public class TcsbMembershipLevelController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TcsbMembershipLevelController.class);

    @Autowired
    private TcsbMembershipLevelServiceI tcsbMembershipLevelService;

    @Autowired
    private TcsbMemberLevelConditionsServiceI tcsbMemberLevelConditionsService;

    @Autowired
    private TcsbMemberTitleServiceI tcsbMemberTitleService;

    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;


    /**
     * 会员级别列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/tcsb/membershiplevel/tcsbMembershipLevelList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TcsbMembershipLevelEntity tcsbMembershipLevel, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
        CriteriaQuery cq = new CriteriaQuery(TcsbMembershipLevelEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMembershipLevel, request.getParameterMap());
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
        this.tcsbMembershipLevelService.getDataGridReturn(cq, true);


        List<TcsbMembershipLevelEntity> tcsbMembershipLevelEntitys = dataGrid.getResults();
        List<TcsbMembershipLevelVO> tcsbMembershipLevelVOs = new ArrayList<TcsbMembershipLevelVO>();
        if (StringUtil.isNotEmpty(tcsbMembershipLevelEntitys) && tcsbMembershipLevelEntitys.size() > 0) {
            for (TcsbMembershipLevelEntity tcsbMembershipLevelEntity : tcsbMembershipLevelEntitys) {
                TcsbMembershipLevelVO tcsbMembershipLevelVO = new TcsbMembershipLevelVO();
                BeanUtils.copyProperties(tcsbMembershipLevelVO, tcsbMembershipLevelEntity);
                TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, tcsbMembershipLevelEntity.getShopId());
                tcsbMembershipLevelVO.setShopName(shopEntity.getName());
                if (StringUtils.isNotEmpty(tcsbMembershipLevelEntity.getMemberLevelConditionsId())) {
                    TcsbMemberLevelConditionsEntity tcsbMemberLevelConditionsEntity = tcsbMemberLevelConditionsService.getEntity(TcsbMemberLevelConditionsEntity.class, tcsbMembershipLevelEntity.getMemberLevelConditionsId());
                    if (tcsbMemberLevelConditionsEntity != null) {
                        tcsbMembershipLevelVO.setMemberLevelConditionsName(tcsbMemberLevelConditionsEntity.getCharge() + "");
                    }
                }

                if (StringUtils.isNotEmpty(tcsbMembershipLevelEntity.getMemberTitleId())) {
                    TcsbMemberTitleEntity tcsbMemberTitleEntity = tcsbMemberTitleService.getEntity(TcsbMemberTitleEntity.class, tcsbMembershipLevelEntity.getMemberTitleId());
                    if (tcsbMemberTitleEntity != null) {
                        tcsbMembershipLevelVO.setMemberTitleName(tcsbMemberTitleEntity.getName());
                    }
                }

                if (StringUtils.isNotEmpty(tcsbMembershipLevelEntity.getMemberLevelEquityId())) {
                    TcsbMemberLevelEquityEntity tcsbMemberLevelEquityEntity = tcsbMemberTitleService.getEntity(TcsbMemberLevelEquityEntity.class, tcsbMembershipLevelEntity.getMemberLevelEquityId());
                    if (tcsbMemberLevelEquityEntity != null) {
                        tcsbMembershipLevelVO.setMemberLevelEquityName(tcsbMemberLevelEquityEntity.getName());
                    }
                }

                tcsbMembershipLevelVOs.add(tcsbMembershipLevelVO);
            }
            dataGrid.setResults(tcsbMembershipLevelVOs);
        }

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除会员级别
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TcsbMembershipLevelEntity tcsbMembershipLevel, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tcsbMembershipLevel = systemService.getEntity(TcsbMembershipLevelEntity.class, tcsbMembershipLevel.getId());

        message = "会员级别删除成功";
        try {
            tcsbMembershipLevelService.delete(tcsbMembershipLevel);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别删除失败";
            throw new BusinessException(e.getMessage());
        }

        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除会员级别
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别删除成功";
        try {
            for (String id : ids.split(",")) {
                TcsbMembershipLevelEntity tcsbMembershipLevel = systemService.getEntity(TcsbMembershipLevelEntity.class,
                        id
                );
                tcsbMembershipLevelService.delete(tcsbMembershipLevel);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加会员级别
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TcsbMembershipLevelEntity tcsbMembershipLevel, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别添加成功";
        TSUser user = getCurrentUser();
        try {
            if (StringUtil.isEmpty(tcsbMembershipLevel.getName()) || StringUtil.isEmpty(tcsbMembershipLevel.getMemberLevelConditionsId())) {
                throw new RuntimeException();
            }
            List<TcsbMembershipLevelEntity> list = systemService.findHql("from TcsbMembershipLevelEntity where name=? and shopId=?", tcsbMembershipLevel.getName(), user.getShopId());
            if (list.isEmpty()) {
                TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, user.getShopId());
                tcsbMembershipLevel.setShopId(shopEntity.getId());
                tcsbMembershipLevelService.save(tcsbMembershipLevel);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } else {
                message = "该会员级别名称已经存在";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新会员级别
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TcsbMembershipLevelEntity tcsbMembershipLevel, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员级别更新成功";
        TcsbMembershipLevelEntity t = tcsbMembershipLevelService.get(TcsbMembershipLevelEntity.class, tcsbMembershipLevel.getId());
        try {
            if (StringUtil.isEmpty(tcsbMembershipLevel.getName()) || StringUtil.isEmpty(tcsbMembershipLevel.getMemberLevelConditionsId())) {
                throw new RuntimeException();
            }
            MyBeanUtils.copyBeanNotNull2Bean(tcsbMembershipLevel, t);
            tcsbMembershipLevelService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员级别更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 会员级别新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TcsbMembershipLevelEntity tcsbMembershipLevel, HttpServletRequest req) {
        TSUser tsUser = getCurrentUser();
        if (StringUtil.isNotEmpty(tcsbMembershipLevel.getId())) {
            tcsbMembershipLevel = tcsbMembershipLevelService.getEntity(TcsbMembershipLevelEntity.class, tcsbMembershipLevel.getId());
            tcsbMembershipLevel.setShopId(tsUser.getShopId());
            req.setAttribute("tcsbMembershipLevelPage", tcsbMembershipLevel);
        }

        List<TcsbMemberLevelConditionsEntity> tcsbMemberLevelConditionsEntitys = null;
        if (StringUtil.isNotEmpty(tsUser.getShopId())) {
            tcsbMemberLevelConditionsEntitys = tcsbMemberLevelConditionsService.findByProperty(TcsbMemberLevelConditionsEntity.class, "shopId", tsUser.getShopId());
            req.setAttribute("tcsbMemberLevelConditionsEntitys", tcsbMemberLevelConditionsEntitys);
        }

        if (StringUtil.isNotEmpty(tsUser.getShopId())) {
            List<TcsbMemberLevelEquityEntity> tcsbMemberLevelEquityEntitys = tcsbMemberLevelConditionsService.findByProperty(TcsbMemberLevelEquityEntity.class, "shopId", tsUser.getShopId());
            req.setAttribute("tcsbMemberLevelEquityEntitys", tcsbMemberLevelEquityEntitys);
        }

        List<TcsbMemberTitleEntity> tcsbMemberTitleEntitys = null;
        if (StringUtil.isNotEmpty(tsUser.getShopId())) {
            tcsbMemberTitleEntitys = tcsbMemberTitleService.findByProperty(TcsbMemberTitleEntity.class, "shopId", tsUser.getShopId());
            req.setAttribute("tcsbMemberTitleEntitys", tcsbMemberTitleEntitys);
        }

        return new ModelAndView("com/tcsb/membershiplevel/tcsbMembershipLevel-add");
    }

    /**
     * 会员级别编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TcsbMembershipLevelEntity tcsbMembershipLevel, HttpServletRequest req) {
        TSUser tsUser = getCurrentUser();
        if (StringUtil.isNotEmpty(tcsbMembershipLevel.getId())) {
            tcsbMembershipLevel = tcsbMembershipLevelService.getEntity(TcsbMembershipLevelEntity.class, tcsbMembershipLevel.getId());
            req.setAttribute("tcsbMembershipLevelPage", tcsbMembershipLevel);
        }

        if (StringUtil.isNotEmpty(tcsbMembershipLevel.getMemberLevelConditionsId())) {
            TcsbMemberLevelConditionsEntity tcsbMemberLevelConditionsEntity = tcsbMemberLevelConditionsService.getEntity(TcsbMemberLevelConditionsEntity.class, tcsbMembershipLevel.getMemberLevelConditionsId());
            if (StringUtil.isNotEmpty(tcsbMembershipLevel.getMemberLevelConditionsId())) {
                req.setAttribute("memberLevelConditionsCharge", tcsbMemberLevelConditionsEntity.getId());
            }
        }
        List<TcsbMemberLevelConditionsEntity> tcsbMemberLevelConditionsEntitys = null;
        if (StringUtil.isNotEmpty(tsUser.getShopId())) {
            tcsbMemberLevelConditionsEntitys = tcsbMemberLevelConditionsService.findByProperty(TcsbMemberLevelConditionsEntity.class, "shopId", tsUser.getShopId());
            req.setAttribute("tcsbMemberLevelConditionsEntitys", tcsbMemberLevelConditionsEntitys);
        }
        if (StringUtil.isNotEmpty(tsUser.getShopId())) {
            List<TcsbMemberLevelEquityEntity> tcsbMemberLevelEquityEntitys = tcsbMemberLevelConditionsService.findByProperty(TcsbMemberLevelEquityEntity.class, "shopId", tsUser.getShopId());
            req.setAttribute("tcsbMemberLevelEquityEntitys", tcsbMemberLevelEquityEntitys);
        }

        List<TcsbMemberTitleEntity> tcsbMemberTitleEntitys = null;
        if (StringUtil.isNotEmpty(tsUser.getShopId())) {
            tcsbMemberTitleEntitys = tcsbMemberTitleService.findByProperty(TcsbMemberTitleEntity.class, "shopId", tsUser.getShopId());
            req.setAttribute("tcsbMemberTitleEntitys", tcsbMemberTitleEntitys);
        }
        return new ModelAndView("com/tcsb/membershiplevel/tcsbMembershipLevel-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tcsbMembershipLevelController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TcsbMembershipLevelEntity tcsbMembershipLevel, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TcsbMembershipLevelEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMembershipLevel, request.getParameterMap());
        List<TcsbMembershipLevelEntity> tcsbMembershipLevels = this.tcsbMembershipLevelService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "会员级别");
        modelMap.put(NormalExcelConstants.CLASS, TcsbMembershipLevelEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会员级别列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tcsbMembershipLevels);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TcsbMembershipLevelEntity tcsbMembershipLevel, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "会员级别");
        modelMap.put(NormalExcelConstants.CLASS, TcsbMembershipLevelEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会员级别列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
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
                List<TcsbMembershipLevelEntity> listTcsbMembershipLevelEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TcsbMembershipLevelEntity.class, params);
                for (TcsbMembershipLevelEntity tcsbMembershipLevel : listTcsbMembershipLevelEntitys) {
                    tcsbMembershipLevelService.save(tcsbMembershipLevel);
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
    public List<TcsbMembershipLevelEntity> list() {
        List<TcsbMembershipLevelEntity> listTcsbMembershipLevels = tcsbMembershipLevelService.getList(TcsbMembershipLevelEntity.class);
        return listTcsbMembershipLevels;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        TcsbMembershipLevelEntity task = tcsbMembershipLevelService.get(TcsbMembershipLevelEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody TcsbMembershipLevelEntity tcsbMembershipLevel, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbMembershipLevelEntity>> failures = validator.validate(tcsbMembershipLevel);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbMembershipLevelService.save(tcsbMembershipLevel);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = tcsbMembershipLevel.getId();
        URI uri = uriBuilder.path("/rest/tcsbMembershipLevelController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TcsbMembershipLevelEntity tcsbMembershipLevel) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbMembershipLevelEntity>> failures = validator.validate(tcsbMembershipLevel);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbMembershipLevelService.saveOrUpdate(tcsbMembershipLevel);
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
        tcsbMembershipLevelService.deleteEntityById(TcsbMembershipLevelEntity.class, id);
    }
}
