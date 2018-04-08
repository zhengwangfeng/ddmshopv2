package com.tcsb.membertitle.controller;

import com.tcsb.membertitle.entity.TcsbMemberTitleEntity;
import com.tcsb.membertitle.service.TcsbMemberTitleServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcsb.membertitle.vo.TcsbMemberTitleVO;
import com.tcsb.shop.entity.TcsbShopEntity;
import org.apache.log4j.Logger;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.BeanUtils;
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
 * @Description: 会员头衔
 * @date 2017-11-03 17:35:30
 */
@Controller
@RequestMapping("/tcsbMemberTitleController")
public class TcsbMemberTitleController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TcsbMemberTitleController.class);

    @Autowired
    private TcsbMemberTitleServiceI tcsbMemberTitleService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;


    /**
     * 会员头衔列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/tcsb/membertitle/tcsbMemberTitleList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TcsbMemberTitleEntity tcsbMemberTitle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TcsbMemberTitleEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMemberTitle, request.getParameterMap());
        TSUser user = getCurrentUser();

        try {
            //自定义追加查询条件
            cq.eq("shopId", user.getShopId());

        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tcsbMemberTitleService.getDataGridReturn(cq, true);
        // 图片展示路径
        List<TcsbMemberTitleEntity> resultList = dataGrid.getResults();

        List<TcsbMemberTitleVO> tcsbMemberTitleVOs = null;

        if (StringUtil.isNotEmpty(resultList) && resultList.size() > 0) {
            tcsbMemberTitleVOs = new ArrayList<TcsbMemberTitleVO>();
            for (TcsbMemberTitleEntity one : resultList) {
                TcsbMemberTitleVO tcsbMemberTitleVO = new TcsbMemberTitleVO();
                BeanUtils.copyProperties(one,tcsbMemberTitleVO);
                String headimg = one.getImg();
                if (StringUtil.isNotEmpty(headimg)) {
                    String files = getCkPath();
                    tcsbMemberTitleVO.setImg(files + headimg);
                }
                if (StringUtil.isNotEmpty(one.getShopId())) {
                    TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, one.getShopId());
                    tcsbMemberTitleVO.setShopName(shopEntity.getName());
                }
                tcsbMemberTitleVOs.add(tcsbMemberTitleVO);
            }
            dataGrid.setResults(tcsbMemberTitleVOs);
        }

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除会员头衔
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TcsbMemberTitleEntity tcsbMemberTitle, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tcsbMemberTitle = systemService.getEntity(TcsbMemberTitleEntity.class, tcsbMemberTitle.getId());
        message = "会员头衔删除成功";
        try {
            tcsbMemberTitleService.delete(tcsbMemberTitle);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员头衔删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除会员头衔
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员头衔删除成功";
        try {
            for (String id : ids.split(",")) {
                TcsbMemberTitleEntity tcsbMemberTitle = systemService.getEntity(TcsbMemberTitleEntity.class,
                        id
                );
                tcsbMemberTitleService.delete(tcsbMemberTitle);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员头衔删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加会员头衔
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TcsbMemberTitleEntity tcsbMemberTitle, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员头衔添加成功";
        TSUser user = getCurrentUser();
        try {
            if(StringUtil.isEmpty(tcsbMemberTitle.getName())||StringUtil.isEmpty(tcsbMemberTitle.getImg())){
                throw new RuntimeException();
            }
            TcsbShopEntity shopEntity = systemService.getEntity(TcsbShopEntity.class, user.getShopId());
            tcsbMemberTitle.setShopId(shopEntity.getId());
            tcsbMemberTitle.setImg(filterCkPath(tcsbMemberTitle.getImg()));
            tcsbMemberTitleService.save(tcsbMemberTitle);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员头衔添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新会员头衔
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TcsbMemberTitleEntity tcsbMemberTitle, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "会员头衔更新成功";
        TcsbMemberTitleEntity t = tcsbMemberTitleService.get(TcsbMemberTitleEntity.class, tcsbMemberTitle.getId());
        try {
            if(StringUtil.isEmpty(tcsbMemberTitle.getName())||StringUtil.isEmpty(tcsbMemberTitle.getImg())){
                throw new RuntimeException();
            }
            MyBeanUtils.copyBeanNotNull2Bean(tcsbMemberTitle, t);
            tcsbMemberTitleService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "会员头衔更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 会员头衔新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TcsbMemberTitleEntity tcsbMemberTitle, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbMemberTitle.getId())) {
            tcsbMemberTitle = tcsbMemberTitleService.getEntity(TcsbMemberTitleEntity.class, tcsbMemberTitle.getId());
            req.setAttribute("tcsbMemberTitlePage", tcsbMemberTitle);
        }
        return new ModelAndView("com/tcsb/membertitle/tcsbMemberTitle-add");
    }

    /**
     * 会员头衔编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TcsbMemberTitleEntity tcsbMemberTitle, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tcsbMemberTitle.getId())) {
            tcsbMemberTitle = tcsbMemberTitleService.getEntity(TcsbMemberTitleEntity.class, tcsbMemberTitle.getId());
            if (StringUtil.isNotEmpty(tcsbMemberTitle.getImg())) {
                String files = getCkPath();
                tcsbMemberTitle.setImg(files + tcsbMemberTitle.getImg());
            }
            req.setAttribute("tcsbMemberTitlePage", tcsbMemberTitle);
        }
        return new ModelAndView("com/tcsb/membertitle/tcsbMemberTitle-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tcsbMemberTitleController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TcsbMemberTitleEntity tcsbMemberTitle, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TcsbMemberTitleEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbMemberTitle, request.getParameterMap());
        List<TcsbMemberTitleEntity> tcsbMemberTitles = this.tcsbMemberTitleService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "会员头衔");
        modelMap.put(NormalExcelConstants.CLASS, TcsbMemberTitleEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会员头衔列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tcsbMemberTitles);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TcsbMemberTitleEntity tcsbMemberTitle, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "会员头衔");
        modelMap.put(NormalExcelConstants.CLASS, TcsbMemberTitleEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会员头衔列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
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
                List<TcsbMemberTitleEntity> listTcsbMemberTitleEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TcsbMemberTitleEntity.class, params);
                for (TcsbMemberTitleEntity tcsbMemberTitle : listTcsbMemberTitleEntitys) {
                    tcsbMemberTitleService.save(tcsbMemberTitle);
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
    public List<TcsbMemberTitleEntity> list() {
        List<TcsbMemberTitleEntity> listTcsbMemberTitles = tcsbMemberTitleService.getList(TcsbMemberTitleEntity.class);
        return listTcsbMemberTitles;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        TcsbMemberTitleEntity task = tcsbMemberTitleService.get(TcsbMemberTitleEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody TcsbMemberTitleEntity tcsbMemberTitle, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbMemberTitleEntity>> failures = validator.validate(tcsbMemberTitle);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbMemberTitleService.save(tcsbMemberTitle);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = tcsbMemberTitle.getId();
        URI uri = uriBuilder.path("/rest/tcsbMemberTitleController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TcsbMemberTitleEntity tcsbMemberTitle) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<TcsbMemberTitleEntity>> failures = validator.validate(tcsbMemberTitle);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            tcsbMemberTitleService.saveOrUpdate(tcsbMemberTitle);
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
        tcsbMemberTitleService.deleteEntityById(TcsbMemberTitleEntity.class, id);
    }
}
