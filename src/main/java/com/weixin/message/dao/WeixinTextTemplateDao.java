package com.weixin.message.dao;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

import com.weixin.weixintexttemplate.entity.WeixinTexttemplateEntity;

@MiniDao
public interface WeixinTextTemplateDao {

	/**
	 * 根据[accountid][templateName]获取对应的文本消息
	 * @param accountid
	 * @return
	 */
	@Sql("select * from weixin_texttemplate where accountid=:accountid and templatename=:templatename")
	@Arguments( { "accountid", "templateName"})
	public WeixinTexttemplateEntity getTextTemplate(String accountid,String templatename);
}
