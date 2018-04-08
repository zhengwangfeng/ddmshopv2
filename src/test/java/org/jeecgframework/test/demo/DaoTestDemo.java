package org.jeecgframework.test.demo;

import java.util.List;

import org.jeecgframework.core.common.dao.impl.CommonDao;
import org.jeecgframework.core.junit.AbstractUnitTest;
import org.jeecgframework.web.demo.dao.test.JeecgProcedureDao;
import org.jeecgframework.web.demo.service.test.JeecgProcedureServiceI;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * DAO 测试DEMO
 * @author 许国杰
 *
 */
public class DaoTestDemo extends AbstractUnitTest{
	@Autowired
	private CommonDao commonDao ;
	@Autowired
	private JeecgProcedureServiceI jeecgProcedureService;
	
	@Test
	public void testGetUserRole() {
		TSUser user = new TSUser();
		user.setUserName("admin");
		user.setPassword("c44b01947c9e6e3f");
		TSUser user2 = commonDao.getUserByUserIdAndUserNameExits(user);
		assert(user2.getUserName().equals(user.getUserName()));
	}
	
	
	@Test
	public void testProcedure() {
		//String sql = "call sp_name(?,?,?)";
		String sql = "call sp_name('?0','?1','?2')";
		jeecgProcedureService.executeByProcedure(sql,"00145","nanan","opendssss");
		System.out.println("================================");
		System.out.println(jeecgProcedureService.queryDataByProcedure("tab1", "id", ""));
	}
	
	
	
}
