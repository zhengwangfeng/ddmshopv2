/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月6日 下午12:22:01
 *//*
package com.applet.appletutil;

*//**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月6日 下午12:22:01
 *//*
public class AppletPayUtil {

	 *//**
     * 
     * 关闭订单
     * @param WxPayData inputObj 提交给关闭订单API的参数
     * @param int timeOut 接口超时时间
     * @throws WxPayException
     * @return 成功时返回，其他抛异常
     *//*
     public static WxPayData CloseOrder(WxPayData inputObj, int timeOut = 6)
     {
         string url = "https://api.mch.weixin.qq.com/pay/closeorder";
         //检测必填参数
         if(!inputObj.IsSet("out_trade_no"))
         {
             throw new WxPayException("关闭订单接口中，out_trade_no必填！");
         }

         inputObj.SetValue("appid",WxPayConfig.APPID);//公众账号ID
         inputObj.SetValue("mch_id",WxPayConfig.MCHID);//商户号
         inputObj.SetValue("nonce_str",GenerateNonceStr());//随机字符串     
         inputObj.SetValue("sign",inputObj.MakeSign());//签名
         string xml = inputObj.ToXml();

         var start = DateTime.Now;//请求开始时间

         string response = HttpService.Post(xml, url, false, timeOut);

         var end = DateTime.Now;
         int timeCost = (int)((end - start).TotalMilliseconds);

         WxPayData result = new WxPayData();
         result.FromXml(response);

         ReportCostTime(url, timeCost, result);//测速上报

         return result;
     }
         *//**
     * 
     * 撤销订单API接口
     * @param WxPayData inputObj 提交给撤销订单API接口的参数，out_trade_no和transaction_id必填一个
     * @param int timeOut 接口超时时间
     * @throws WxPayException
     * @return 成功时返回API调用结果，其他抛异常
     *//*
     public static WxPayData Reverse(WxPayData inputObj, int timeOut = 6)
     {
         string url = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
         //检测必填参数
         if (!inputObj.IsSet("out_trade_no") && !inputObj.IsSet("transaction_id"))
         {
             throw new WxPayException("撤销订单API接口中，参数out_trade_no和transaction_id必须填写一个！");
         }

         inputObj.SetValue("appid", WxPayConfig.APPID);//公众账号ID
         inputObj.SetValue("mch_id", WxPayConfig.MCHID);//商户号
         inputObj.SetValue("nonce_str", GenerateNonceStr());//随机字符串
         inputObj.SetValue("sign", inputObj.MakeSign());//签名
         string xml = inputObj.ToXml();

         var start = DateTime.Now;//请求开始时间

         Log.Debug("WxPayApi", "Reverse request : " + xml);

         string response = HttpService.Post(xml, url, true, timeOut);

         Log.Debug("WxPayApi", "Reverse response : " + response);

         var end = DateTime.Now;
         int timeCost = (int)((end - start).TotalMilliseconds);

         WxPayData result = new WxPayData();
         result.FromXml(response);

         ReportCostTime(url, timeCost, result);//测速上报

         return result;
     }
	
	
}
*/