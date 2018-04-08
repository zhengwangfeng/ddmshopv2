package org.jeecgframework.core.util;

import java.util.List;

/**
 * @author jimmy
 *org.jeecgframework.core.util
 * 2017年4月12日
 */
public class PageBean<T> {
	 private int page; //当前页数  
	    private int totalCount;  //总记录数  
	    private int totalPage;  //总页数  
	    private int pageSize;   //每页显示的记录数  
	    private List<T> list; //每页显示数据记录的集合；  
		public PageBean(int page, int totalCount, int totalPage, int pageSize,
				List<T> list) {
			super();
			this.page = page;
			this.totalCount = totalCount;
			this.totalPage = totalPage;
			this.pageSize = pageSize;
			this.list = list;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
	
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public PageBean() {
			super();
		}
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		public void setTotalPage(int pageSize,int totalCount) {
			if(totalCount % pageSize ==0){  
	            totalPage=totalCount/pageSize;  
	        }else {  
	            totalPage= totalCount/pageSize +1;  
	        }  
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public List<T> getList() {
			return list;
		}
		public void setList(List<T> list) {
			this.list = list;
		}
		public int getTotalPage() {
			return totalPage;
		}
}
