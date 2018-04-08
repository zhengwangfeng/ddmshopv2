package org.jeecgframework.web.demo.service.test;

import java.util.List;


public interface JeecgProcedureServiceI{
	public List queryDataByProcedure(String tableName,String fields,String whereSql);

	/**
	 * @param sql
	 * @return
	 */
	List executeDataByProcedure(String sql);

	/**
	 * @param sql
	 * @return
	 */
	void executeByProcedure(String sql,Object... object);
}
