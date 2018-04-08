package com.tcsb.common;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tcsb.shop.entity.TcsbShopEntity;
import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * 上传多张图片
 *
 * @author jimmy(王志明)
 * 2016年5月11日
 */
@Scope("prototype")
@Controller
@RequestMapping("/uploadController")
public class UploadController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(UploadController.class);

    /**
     * 图片服务
     */
    /*@Autowired
    private CommonImagesServiceI commonImagesService;*/
    private String message;

    @Autowired
    private SystemService systemService;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 上传 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "selectimages")
    public ModelAndView selectimages(HttpServletRequest request) {
        request.setAttribute("fileName", request.getParameter("fileName"));
        return new ModelAndView("com/tcsb/common/uploadImages");
    }

    /**
     * 保存图片
     *
     * @return
     */
    @RequestMapping(params = "saveImages")
    @ResponseBody
    public AjaxJson saveImages(HttpServletRequest request) {
        //获取图片业务模块名
        String ywName = request.getParameter("fileName");
        AjaxJson j = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            multipartRequest.setCharacterEncoding("UTF-8");
            String uploadbasepath = ResourceUtil.getConfigByName("ck.baseDir");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            //以当前时间命名文件
            String timeFile = DateUtils.getNowTimeFile(new Date());
            String realPath = uploadbasepath + "/images/" + ywName + "/" + timeFile + "/";
            //String realPath = uploadbasepath + "images/"+ywName+"/"+timeFile+"/";

            String _thumbsPath = uploadbasepath + "/_thumbs/Images/" + ywName + "/" + timeFile + "/";
            //String _thumbsPath = uploadbasepath +"_thumbs/Images/"+ywName+"/"+timeFile+"/";
            //String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + path;// 文件的硬盘真实路径
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();// 创建根目录
            }
            File file2 = new File(_thumbsPath);
            if (!file2.exists()) {
                file2.mkdirs();// 创建根目录
            }
            String fileName = "";
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile mf = entity.getValue();// 获取上传文件对象
                fileName = mf.getOriginalFilename();// 获取文件名
                String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
                String myfilename = "";
                String noextfilename = "";//不带扩展名
                noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + StringUtil.random(8);//自定义文件名称
                myfilename = noextfilename + "." + extend;//自定义文件名称
                String savePath = realPath + myfilename;// 文件保存全路径
                File savefile = new File(savePath);
                // 文件拷贝到指定硬盘目录
                FileCopyUtils.copy(mf.getBytes(), savefile);
                String files = getCkPath();
                attributes.put("img", "/images/" + ywName + "/" + timeFile + "/" + myfilename);

                attributes.put("thumbs", "/_thumbs/Images/" + ywName + "/" + timeFile + "/" + myfilename);
                Thumbnails.of(savePath).scale(0.5f).toFile(_thumbsPath + myfilename);
                //Thumbnails.of(savePath).forceSize(425, 280).toFile(_thumbsPath+ myfilename);
                //Thumbnails.of(savePath).forceSize(80, 80).toFile(realPath +"small/"+ myfilename);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        j.setMsg("文件添加成功");
        j.setAttributes(attributes);
        return j;

    }

    /**
     * 保存pc端图片
     *
     * @return
     */
    @RequestMapping(params = "savePcImages")
    @ResponseBody
    public AjaxJson savePcImages(HttpServletRequest request) {
        //获取图片业务模块名
        //String ywName = request.getParameter("fileName");
        String userId = request.getParameter("userId");
        TSUser user = null;
        if (StringUtil.isNotEmpty(userId)) {
            user = systemService.getEntity(TSUser.class, userId);
        }
        if (user == null) {
            throw new RuntimeException("该用户id不存在");
        }
        AjaxJson j = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            multipartRequest.setCharacterEncoding("UTF-8");
            String uploadbasepath = ResourceUtil.getConfigByName("ck.baseDir");

            uploadbasepath = uploadbasepath + "/" + userId;

            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            //以当前时间命名文件
            String timeFile = DateUtils.getNowTimeFile(new Date());
            String realPath = uploadbasepath + "/images/" + timeFile + "/";
            //String realPath = uploadbasepath + "images/"+ywName+"/"+timeFile+"/";

            String _thumbsPath = uploadbasepath + "/_thumbs/Images/" + timeFile + "/";
            //String _thumbsPath = uploadbasepath +"_thumbs/Images/"+ywName+"/"+timeFile+"/";
            //String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + path;// 文件的硬盘真实路径
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();// 创建根目录
            }
            File file2 = new File(_thumbsPath);
            if (!file2.exists()) {
                file2.mkdirs();// 创建根目录
            }
            String fileName = "";
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile mf = entity.getValue();// 获取上传文件对象
                fileName = mf.getOriginalFilename();// 获取文件名
                String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
                String myfilename = "";
                String noextfilename = "";//不带扩展名
                noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + StringUtil.random(8);//自定义文件名称
                myfilename = noextfilename + "." + extend;//自定义文件名称
                String savePath = realPath + myfilename;// 文件保存全路径
                File savefile = new File(savePath);
                // 文件拷贝到指定硬盘目录


                FileCopyUtils.copy(mf.getBytes(), savefile);
                String files = getCkPath();
                attributes.put("img", "/images/" + timeFile + "/" + myfilename);

                attributes.put("thumbs", "/_thumbs/Images/" + timeFile + "/" + myfilename);
                Thumbnails.of(savePath).scale(0.5f).toFile(_thumbsPath + myfilename);
                //Thumbnails.of(savePath).forceSize(425, 280).toFile(_thumbsPath+ myfilename);
                //Thumbnails.of(savePath).forceSize(80, 80).toFile(realPath +"small/"+ myfilename);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        j.setMsg("文件添加成功");
        j.setAttributes(attributes);
        return j;

    }

    /**
     * 根据图片路径删除硬盘上的图片
     *
     * @return
     */
    @RequestMapping(params = "delImages")
    @ResponseBody
    public AjaxJson delImages(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "删除图片成功";
        String imgPath = request.getParameter("imgs");
        String thumbsPath = request.getParameter("thumbs");
        String uploadbasepath = ResourceUtil.getConfigByName("ck.baseDir");
        FileUtils.delete(uploadbasepath + imgPath);
        FileUtils.delete(uploadbasepath + thumbsPath);
        j.setMsg(message);
        return j;
    }
}
