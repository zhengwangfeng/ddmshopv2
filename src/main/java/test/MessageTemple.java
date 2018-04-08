package test;

import java.util.Map;

public class MessageTemple {
	
	private String touser;
	private String template_id;
	private String page;
	private String form_id;
	private Map<String,Object> data;
	private String emphasis_keyword;
	//private String prepay_id;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getForm_id() {
		return form_id;
	}
	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public String getEmphasis_keyword() {
		return emphasis_keyword;
	}
	public void setEmphasis_keyword(String emphasis_keyword) {
		this.emphasis_keyword = emphasis_keyword;
	}
//	public String getPrepay_id() {
//		return prepay_id;
//	}
//	public void setPrepay_id(String prepay_id) {
//		this.prepay_id = prepay_id;
//	}
	public Map<String,Object> getData() {
		return data;
	}
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	

}
