package com.apiservice.common.utils;


public enum ReturnMessageEnum {
        QUERYTRUE("1","查询成功"),
        ADDSUCCESS("2","添加成功"),
        UPDATESUCEESS("3","更新成功"),
        SENDSUCEESS("4","发送成功"),
        SENDFAILE("5","发送失败"),
        SMSISSEND("6","短信已发送"),
        BADREQUEST("400","Bad Request!"),
        NOTAUTHORIZATION("401", "NotAuthorization"),
        METHODNOTALLOWED("405", "Method Not Allowed"),
        NOTACCEPTABLE("406", "Not Acceptable"),
        INTERNALSERVERERROR("500", "Internal Server Error"),
        RUNTIMEEXCEPTION("1000", "[服务器]运行时异常"),
        NULLPOINTEXCEPTION("1001", "[服务器]空值异常"),
        CLASSCATEXCEPTION("1002", "[服务器]数据类型转换异常"),
        IOEXCEPTION("1003", "[服务器]IO异常"),
        NOSUCHMETHODEXCEPTION("1004", "[服务器]未知方法异常"),
        INDEXOUTOFBOUNDSEXCEPTION("1005", "[服务器]数组越界异常"),
        NETCONNECTEXCEPTION("1006", "[服务器]网络异常"),
        SHOPNOTEXIT("1034", "店铺不存在"),
        LACKVALUEORNULL("2010", "缺少参数或值为空"),
        INVALIDTOKEN("2020", "无效的Token"),
        WEIXINUSERNOTEXIT("2030", "微信用户不存在"),
        SMSISOUTOFDATE("2031","短信已过期");
       
        private String status;
        private String msg;
        ReturnMessageEnum(String status, String msg){
        	this.status = status;
        	this.msg =msg;
        }
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public static void main(String[] args) {
			System.out.println(ReturnMessageEnum.BADREQUEST.getStatus());;
		}
        
}