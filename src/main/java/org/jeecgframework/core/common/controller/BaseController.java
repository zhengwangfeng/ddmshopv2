package org.jeecgframework.core.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.interceptors.DateConvertEditor;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apiservice.common.utils.ReturnFormat;
import com.tcsb.shop.entity.TcsbShopEntity;


/**
 * 基础控制器，其他控制器需集成此控制器获得initBinder自动转换的功能
 * @author  张代浩
 * 
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd hh:mm:ss");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(
//				dateFormat, true));
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

	/**
	 * 分页公共方法(非easyui)
	 * 
	 * @author Alexander
	 * @date 20131022
	 */
	public List<?> pageBaseMethod(HttpServletRequest request,
			DetachedCriteria dc, CommonService commonService, int pageRow) {
		// 当前页
		// 总条数
		// 总页数

		int currentPage = 1;

		int totalRow = 0;
		int totalPage = 0;
		// 获取当前页
		String str_currentPage = request.getParameter("str_currentPage");
		currentPage = str_currentPage == null || "".equals(str_currentPage) ? 1
				: Integer.parseInt(str_currentPage);
		// 获取每页的条数
		String str_pageRow = request.getParameter("str_pageRow");
		pageRow = str_pageRow == null || "".equals(str_pageRow) ? pageRow
				: Integer.parseInt(str_pageRow);

		// 统计的总行数
		dc.setProjection(Projections.rowCount());

		totalRow = Integer.parseInt(commonService.findByDetached(dc).get(0)
				.toString());
		totalPage = (totalRow + pageRow - 1) / pageRow;

		currentPage = currentPage < 1 ? 1 : currentPage;
		currentPage = currentPage > totalPage ? totalPage : currentPage;
		// 清空统计函数
		dc.setProjection(null);
		// dc.setResultTransformer(dc.DISTINCT_ROOT_ENTITY);
		List<?> list = commonService.pageList(dc, (currentPage - 1) * pageRow,
				pageRow);

		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageRow", pageRow);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("totalPage", totalPage);
		return list;
	}

    /**
     * 抽取由逗号分隔的主键列表
     *
     * @param ids
     *            由逗号分隔的多个主键值
     * @return 主键类表
     * @author 张国明 2014-8-21 21:57:16
     */
    protected List<String> extractIdListByComma(String ids) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.hasText(ids)) {
            for (String id : ids.split(",")) {
                if (StringUtils.hasLength(id)) {
                    result.add(id.trim());
                }
            }
        }

        return result;
    }
    
    /**
     * 获取当前用户 
     * @return
     */
    public TSUser getCurrentUser() {
        return ResourceUtil.getSessionUserName();
    }
    
    /**
     * 是否是admin 
     * @return
     */
    public boolean checkAdmin() {
        TSUser user = ResourceUtil.getSessionUserName();
        if (user != null && "admin".equals(user.getUserName())) {
            return true;
        }
        return false;
    }
    
    public void filterUserAdmin(CriteriaQuery cq) {
        if (checkAdmin() == false) {
            cq.notEq("createBy", "admin");
        }
    }
    /**
     * 过滤Ck上传图片的路径 
     * @param url
     * @return
     */
    public String filterCkPath(String url) {
        String files = ResourceUtil.getConfigByName("ck.userfiles");
        if (url != null) url = url.replace(files, "");
        return url;
    }
    /**
     * 获取CK路径 
     * @return
     */
    public String getCkPath() {
        return ResourceUtil.getConfigByName("ck.userfiles");
    }
    
    
    public String getAppletCkPath() {
        return ResourceUtil.getConfigByName("applet.ck.userfiles");
    }
	
}
