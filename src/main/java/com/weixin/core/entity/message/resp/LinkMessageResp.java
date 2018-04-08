package com.weixin.core.entity.message.resp;


/**
 * @author jimmy
 *com.weixin.core.entity.message.resp
 * 2017年4月7日
 */
public class LinkMessageResp extends BaseMessageResp{
	// 链接消息标题 标题
    private String title;
    //消息描述 
    private String Description;
    //消息链接 
    private String Url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
}
