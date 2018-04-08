package com.applet.message.service.impl;

import com.applet.message.service.AppletMessageServiceI;
import com.applet.message.entity.UsefulToken;
import com.applet.message.vo.AppletOrderSuccessVO;
import com.applet.message.vo.AppletReservationSuccessVO;
import com.applet.message.vo.MapRows;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.util.ConfigUtil;
import com.weixin.core.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Service("appletMessageService")
@Transactional
public class AppletMessageService extends CommonServiceImpl implements AppletMessageServiceI {

    public void demo() {
//生成微信通知消息
//        AppletOrderSuccessVO appletOrderSuccessVO = new AppletOrderSuccessVO();
//        appletOrderSuccessVO.setOpenId(orderMap.get("openid"));
//        appletOrderSuccessVO.setPrePayId(orderMap.get("prepay_id"));
//        appletOrderSuccessVO.setOrderId(orderId);
//        TcsbOrderEntity tcsbOrderEntity = systemService.get(TcsbOrderEntity.class,orderId);
//        TcsbShopEntity tcsbShopEntity = systemService.get(TcsbShopEntity.class,tcsbOrderParentEntity.getShopId());
//        TcsbDeskEntity tcsbDeskEntity = systemService.get(TcsbDeskEntity.class,tcsbOrderParentEntity.getDeskId());
//        appletOrderSuccessVO.setShopName(tcsbShopEntity.getName());
//        appletOrderSuccessVO.setAddress(tcsbShopEntity.getAddress());
//        appletOrderSuccessVO.setDeskName(tcsbDeskEntity.getDeskName());
//        appletOrderSuccessVO.setShopTelephone(tcsbShopEntity.getPhone());
//        appletOrderSuccessVO.setTotalPrice(String.valueOf(tcsbOrderEntity.getTotalPrice()));
//        appletOrderSuccessVO.setNote(tcsbOrderEntity.getNote());
//        appletMessageService.sendOrderSuccessMessage(appletOrderSuccessVO);
    }

    public AjaxJsonApi sendOrderMessage(String orderId, String openId, String prePayId, String totalPrice) {
        AppletOrderSuccessVO appletOrderSuccessVO = convertOrderId(orderId, openId, prePayId, totalPrice);
        AjaxJsonApi ajaxJsonApi = sendOrderSuccessMessage(appletOrderSuccessVO);
        return ajaxJsonApi;
    }

    public AjaxJsonApi sendReservationMessage(String reservationId) {
        AppletReservationSuccessVO appletReservationSuccessVO = convertReservationIdToMessage(reservationId);
        AjaxJsonApi ajaxJsonApi = sendPcReservationSuccessMessage(appletReservationSuccessVO);
        return ajaxJsonApi;
    }

    /**
     * @param reservationId 预订单id
     * @return
     */
    public AppletReservationSuccessVO convertReservationIdToMessage(String reservationId) {
        AppletReservationSuccessVO appletReservationSuccessVO = new AppletReservationSuccessVO();

        TcsbDeskReservationEntity tcsbDeskReservationEntity = get(TcsbDeskReservationEntity.class, reservationId);
        appletReservationSuccessVO.setOpenId(tcsbDeskReservationEntity.getUserId());
        appletReservationSuccessVO.setPrePayId(tcsbDeskReservationEntity.getPrepayId());
        //有押金
        String payType = "";
        if (tcsbDeskReservationEntity.getIsDeposit().equals("1")) {
            TcsbDepositDeltaEntity tcsbDepositDeltaEntity = get(TcsbDepositDeltaEntity.class,tcsbDeskReservationEntity.getDepositDeltaId());
            appletReservationSuccessVO.setOrderNo(tcsbDepositDeltaEntity.getOrderNo());
            payType = "押金";
            appletReservationSuccessVO.setTotalPrice(tcsbDepositDeltaEntity.getReserveDeposit()+"元("+ payType + ")");
        } else if (tcsbDeskReservationEntity.getIsDeposit().equals("0")) {
            appletReservationSuccessVO.setOrderNo(tcsbDeskReservationEntity.getNo());

            payType = "全额";
            appletReservationSuccessVO.setTotalPrice(tcsbDeskReservationEntity.getOnlinePrice()+"元("+ payType + ")");
        }

        appletReservationSuccessVO.setNote(StringUtils.isEmpty(tcsbDeskReservationEntity.getNote())?"":tcsbDeskReservationEntity.getNote());
        appletReservationSuccessVO.setNumber(String.valueOf(tcsbDeskReservationEntity.getNum()));
        appletReservationSuccessVO.setReservationId(tcsbDeskReservationEntity.getId());
        appletReservationSuccessVO.setReservationTime(DateUtils.date2Str(tcsbDeskReservationEntity.getOrderTime(), DateUtils.time_sdf).toString());

        TcsbDeskEntity tcsbDeskEntity = get(TcsbDeskEntity.class,tcsbDeskReservationEntity.getDeskId());
        appletReservationSuccessVO.setDeskName(tcsbDeskEntity.getDeskName());
        TcsbShopEntity tcsbShopEntity = get(TcsbShopEntity.class, tcsbDeskReservationEntity.getShopId());
        appletReservationSuccessVO.setAddress(tcsbShopEntity.getAddress());
        appletReservationSuccessVO.setShopTelephone(tcsbShopEntity.getPhone());
        appletReservationSuccessVO.setShopName(tcsbShopEntity.getName());
        return appletReservationSuccessVO;
    }


    public AppletOrderSuccessVO convertOrderId(String orderId, String openId, String prePayId, String totalPrice) {
        AppletOrderSuccessVO appletOrderSuccessVO = new AppletOrderSuccessVO();
        appletOrderSuccessVO.setOpenId(openId);
        appletOrderSuccessVO.setPrePayId(prePayId);
        TcsbOrderEntity tcsbOrderEntity = get(TcsbOrderEntity.class, orderId);
        TcsbOrderParentEntity tcsbOrderParentEntity = get(TcsbOrderParentEntity.class, tcsbOrderEntity.getOrderParentId());
        appletOrderSuccessVO.setTotalPrice(totalPrice);
        appletOrderSuccessVO.setNote(tcsbOrderEntity.getNote());
        TcsbDeskEntity tcsbDeskEntity = get(TcsbDeskEntity.class, tcsbOrderParentEntity.getDeskId());
        appletOrderSuccessVO.setDeskName(tcsbDeskEntity.getDeskName());
        appletOrderSuccessVO.setOrderId(orderId);
        TcsbShopEntity tcsbShopEntity = get(TcsbShopEntity.class, tcsbOrderParentEntity.getShopId());
        appletOrderSuccessVO.setAddress(tcsbShopEntity.getAddress());
        appletOrderSuccessVO.setShopTelephone(tcsbShopEntity.getPhone());
        appletOrderSuccessVO.setShopName(tcsbShopEntity.getName());
        return appletOrderSuccessVO;
    }

//    public AjaxJsonApi sendReservationSuccessMessage(AppletReservationSuccessVO appletReservationSuccessVO) {
//        JSONObject obj = new JSONObject();
//
//        JSONObject reservationId = new JSONObject();
//        reservationId.element("value", appletReservationSuccessVO.getReservationId());
//        reservationId.element("color", "#000000");
//        obj.element("keyword1", reservationId);
//
//        JSONObject reservationTime = new JSONObject();
//        reservationTime.element("value", appletReservationSuccessVO.getReservationTime());
//        reservationTime.element("color", "#000000");
//        obj.element("keyword2", reservationTime);
//
//        JSONObject totalPrice = new JSONObject();
//        totalPrice.element("value", appletReservationSuccessVO.getTotalPrice());
//        totalPrice.element("color", "#000000");
//        obj.element("keyword3", totalPrice);
//
//        JSONObject shopName = new JSONObject();
//        shopName.element("value", appletReservationSuccessVO.getShopName());
//        shopName.element("color", "#000000");
//        obj.element("keyword4", shopName);
//
//        JSONObject number = new JSONObject();
//        number.element("value", appletReservationSuccessVO.getNumber());
//        number.element("color", "#173177");
//        obj.element("keyword5", number);
//
//        JSONObject address = new JSONObject();
//        address.element("value", appletReservationSuccessVO.getAddress());
//        address.element("color", "#000000");
//        obj.element("keyword6", address);
//
//        JSONObject shopTelephone = new JSONObject();
//        shopTelephone.element("value", appletReservationSuccessVO.getShopTelephone());
//        shopTelephone.element("color", "#000000");
//        obj.element("keyword7", shopTelephone);
//
//        JSONObject note = new JSONObject();
//        note.element("value", appletReservationSuccessVO.getNote());
//        note.element("color", "#000000");
//        obj.element("keyword8", note);
//        System.out.print(obj.toString());
//
//        AjaxJsonApi ajaxJsonApi = sendMessage(appletReservationSuccessVO.getOpenId(), "Ro7S3qONk8wwDsAgbsf_CKIHiapf6YK2HllIMNqoKhc", null, appletReservationSuccessVO.getPrePayId(), obj.toString(), null, "keyword1.DATA");
//        return ajaxJsonApi;
//    }

//    public AjaxJsonApi sendReservationSuccessMessage(AppletReservationSuccessVO appletReservationSuccessVO) {
//        MapRows mapRows = new MapRows(8);
//        mapRows.get(1).setValue(appletReservationSuccessVO.getReservationId());
//        mapRows.get(2).setValue(appletReservationSuccessVO.getReservationTime());
//        mapRows.get(3).setValue(appletReservationSuccessVO.getTotalPrice());
//        mapRows.get(4).setValue(appletReservationSuccessVO.getShopName());
//        mapRows.get(5).setValue(appletReservationSuccessVO.getNumber());
//        mapRows.get(6).setValue(appletReservationSuccessVO.getAddress());
//        mapRows.get(7).setValue(appletReservationSuccessVO.getShopTelephone());
//        mapRows.get(8).setValue(appletReservationSuccessVO.getNote());
//
//        AjaxJsonApi ajaxJsonApi = sendMessage(appletReservationSuccessVO.getOpenId(), "Ro7S3qONk8wwDsAgbsf_CKIHiapf6YK2HllIMNqoKhc", null, appletReservationSuccessVO.getPrePayId(), mapRows.toJSON(), null, "keyword1.DATA");
//        return ajaxJsonApi;
//    }
    public AjaxJsonApi sendPcReservationSuccessMessage(AppletReservationSuccessVO appletReservationSuccessVO) {
        MapRows mapRows = new MapRows(9);
        mapRows.get(1).setValue(appletReservationSuccessVO.getOrderNo());
        mapRows.get(2).setValue(appletReservationSuccessVO.getReservationTime());
        mapRows.get(3).setValue(appletReservationSuccessVO.getTotalPrice());
        mapRows.get(4).setValue(appletReservationSuccessVO.getShopName());
        mapRows.get(5).setValue(appletReservationSuccessVO.getDeskName());
        mapRows.get(6).setValue(appletReservationSuccessVO.getNumber());
        mapRows.get(7).setValue(appletReservationSuccessVO.getAddress());
        mapRows.get(8).setValue(appletReservationSuccessVO.getShopTelephone());
        mapRows.get(9).setValue(appletReservationSuccessVO.getNote());

        AjaxJsonApi ajaxJsonApi = sendMessage(appletReservationSuccessVO.getOpenId(), "NOMSP2y7ks2rnKXImSaqkTSCezVaIwlTYRojKB5ZFkI", "pages/order/order", appletReservationSuccessVO.getPrePayId(), mapRows.toJSON(), null, null);
        return ajaxJsonApi;
    }

    public AjaxJsonApi sendOrderSuccessMessage(AppletOrderSuccessVO appletOrderSuccessVO) {
        JSONObject obj = new JSONObject();

        JSONObject orderId = new JSONObject();
        orderId.element("value", appletOrderSuccessVO.getOrderId());
        orderId.element("color", "#000000");
        obj.element("keyword1", orderId);

        JSONObject totalPrice = new JSONObject();
        totalPrice.element("value", appletOrderSuccessVO.getTotalPrice());
        totalPrice.element("color", "#000000");
        obj.element("keyword2", totalPrice);

        JSONObject shopName = new JSONObject();
        shopName.element("value", appletOrderSuccessVO.getShopName());
        shopName.element("color", "#000000");
        obj.element("keyword3", shopName);

        JSONObject deskName = new JSONObject();
        deskName.element("value", appletOrderSuccessVO.getDeskName());
        deskName.element("color", "#000000");
        obj.element("keyword4", deskName);

        JSONObject address = new JSONObject();
        address.element("value", appletOrderSuccessVO.getAddress());
        address.element("color", "#000000");
        obj.element("keyword5", address);

        JSONObject shopTelephone = new JSONObject();
        shopTelephone.element("value", appletOrderSuccessVO.getShopTelephone());
        shopTelephone.element("color", "#000000");
        obj.element("keyword6", shopTelephone);

        JSONObject note = new JSONObject();
        note.element("value", appletOrderSuccessVO.getNote());
        note.element("color", "#000000");
        obj.element("keyword7", note);
        System.out.print(obj.toString());

        AjaxJsonApi jsonObject = sendMessage(appletOrderSuccessVO.getOpenId(), "WfDvJ5S_IdV4EpvxXnBmOm_3SR6DITtMqKrefhTU8I0", null, appletOrderSuccessVO.getPrePayId(), obj.toString(), null, "keyword1.DATA");

        return jsonObject;
    }

    @Override
    public AjaxJsonApi sendMessage(String openId, String templateId, String page, String formId, String data, String color, String emphasisKeyword) {
        String urlString = ConfigUtil.MESSAGE_TEMPLATE_SEND;//微信消息推送接口
        AccessToken accessToken = WeixinUtil.getAppletAccessToken();
        //String token = "5_E7J0ZaXmThnYVdkbiKRfRHXUTllCx0A9KYjF0nvMRtJgLZHFO5_CcpGoP7S9Ekrbq3V_VQ2W9Kz97zbxpSy2m6SJQDdfB9iw86cxOrsYJ25MWdyQz8eb1hqfo-05JsAKkAy2hd-H0uAZnVi1MZRfADASFX";
        urlString = urlString.replace("ACCESS_TOKEN", accessToken.getToken());
        JSONObject obj = new JSONObject();
        obj.element("touser", openId);
        obj.element("template_id", templateId);
        if (StringUtils.isNotEmpty(page)) {
            obj.element("page", page);
        }
        if (StringUtils.isNotEmpty(color)) {
            obj.element("color", color);
        }
        if (StringUtils.isNotEmpty(emphasisKeyword)) {
            obj.element("emphasis_keyword", emphasisKeyword);
        }
        obj.element("form_id",formId );
        obj.element("data", data);
        System.out.print(obj.toString());
        //发送模板消息
        JSONObject jsonObject = WeixinUtil.httpRequest(urlString, "POST", obj.toString());
        if(null != jsonObject){
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if(0 == errorCode){
                System.out.println("模板消息发送成功errorCode:{"+errorCode+"},errmsg:{"+errorMsg+"}");
            }else{
                System.out.println("模板消息发送失败errorCode:{"+errorCode+"},errmsg:{"+errorMsg+"}");
            }
        }




//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection connection = (HttpURLConnection) url
//                    .openConnection();
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setRequestMethod("POST");
//            connection.setUseCaches(false);
//            connection.setInstanceFollowRedirects(true);
//            connection.setRequestProperty("Content-Type",
//                    "application/json");
//
//            connection.connect();
//
//            //POST请求
//            DataOutputStream out = new DataOutputStream(
//                    connection.getOutputStream());
//            out.writeBytes(obj.toString());
//            out.flush();
//            out.close();
//            //读取响应
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));
//            String lines;
//            StringBuffer sb = new StringBuffer("");
//            while ((lines = reader.readLine()) != null) {
//                lines = new String(lines.getBytes(), "utf-8");
//                sb.append(lines);
//            }
//            System.out.println(sb);
//            System.out.println(sb.toString());
//            reader.close();
//            // 断开连接
//            connection.disconnect();
//////            JSONObject jsonObject = JSONObject.fromObject(sb.toString());
//////            AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
////            if (jsonObject.get("errcode").equals("0")) {
////                ajaxJsonApi.setSuccess(true);
////                ajaxJsonApi.setMsg(jsonObject.get("errmsg").toString());
////            } else {
////                ajaxJsonApi.setSuccess(false);
////                ajaxJsonApi.setMsg(jsonObject.get("errmsg").toString());
////            }
////            return ajaxJsonApi;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return null;
    }
}
