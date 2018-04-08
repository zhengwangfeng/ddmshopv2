package com.pc.security;


import com.pc.security.config.Constant;
import com.pc.security.model.CommonResult;

public class ResponseMgr {
	
	/**
	 *
	 * @return
	 */
	public static String success() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_SUCCESS, "success");
		return commonResult.general();
	}
	
	/**
	 *
	 * @param data
	 * @return
	 */
	public static String successWithData(Object data) {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_SUCCESS_DATA, data, "success");
		return commonResult.general();
	}
	
	/**
	 *
	 * @return
	 */
	public static String err() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_EXCEPTION, "无token参数");
		return commonResult.general();
	}

	/**
	 *
	 * @param data
	 * @return
	 */
	public static String errWhitData(Object data) {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_EXCEPTION_DATA, data, "?????????");
		return commonResult.general();
	}
	
	/**
	 *
	 * @param data
	 * @return
	 */
	public static String errWhitData(String msg, Object data) {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_EXCEPTION_DATA, data, msg);
		return commonResult.general();
	}
	
	/**
	 * 用户未登录
	 * @return
	 */
	public static String noLogin() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_NOLOGIN, "用户未登录");
		return commonResult.general();
	}
	
	/**
	 * 用户不存在
	 * @return
	 */
	public static String noExist() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_NOEXIST, "用户不存在");
		return commonResult.general();
	}
	
	/**
	 * 没有权限
	 * @return
	 */
	public static String noAuth() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_NOAUTH, "没有权限");
		return commonResult.general();
	}
	
	/**
	 * 登录超时
	 * @return
	 */
	public static String loginExpire() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_LOGINEXPIRE, "登录超时");
		return commonResult.general();
	}
}
