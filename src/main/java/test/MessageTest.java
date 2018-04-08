package test;

import com.pc.memberuser.service.TcsbPCMemberUserServiceI;
import com.pc.security.TokenMgr;
import com.pc.security.config.Constant;
import com.pc.security.model.CheckResult;
import com.pc.security.model.SubjectModel;
import com.pc.security.utils.SigNatureUtil;
import com.pc.util.PCDateUtil;
import com.tcsb.util.FirstLetterUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.PinyinUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import com.weixin.core.util.WeixinUtil;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

public class MessageTest {

    @Autowired
    private TcsbPCMemberUserServiceI tcsbPCMemberUserService;

    /**
     * 发送消息
     *
     * @return
     */
    @Test
    public void sendTemplateMessage() {

        String jsonmsg = "{\"data\":{\"keyword5\":{\"color\":\"#000000\",\"value\":\"1\"},"
                + "\"keyword6\":{\"color\":\"#000000\",\"value\":\"厦门市集美区软件园三期A区04幢1303之二\"},"
                + "\"keyword7\":{\"color\":\"#000000\",\"value\":\"0592-5588739\"},"
                + "\"keyword8\":{\"color\":\"#000000\",\"value\":\"\"},"
                + "\"keyword1\":{\"color\":\"#000000\"},\"keyword2\":{\"color\":\"#000000\",\"value\":\"2017-12-17 08:00\"},"
                + "\"keyword3\":{\"color\":\"#000000\",\"value\":\"0.01元(押金)\"},"
                + "\"keyword4\":{\"color\":\"#000000\",\"value\":\"点单么官方旗舰店\"}},"
                + "\"template_id\":\"Ro7S3qONk8wwDsAgbsf_CKIHiapf6YK2HllIMNqoKhc\","
                + "\"form_id\":\"wx20171218172416ab673aca040134027630\","
                + "\"touser\":\"oZdMf0TMWK658gFAKWoaMcYNOfq4\","
                + "\"page\":\"\"}";
//		

        System.out.println(jsonmsg);
        //jsonmsg = "{'offset':0,'count':5}";
        //+ "'form_id': 'wx2017121812083517a3b147b00839412582',"
        boolean result = false;
        //请求地址  
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
        //String requestUrl = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list?access_token=ACCESS_TOKEN";  
        requestUrl = requestUrl.replace("ACCESS_TOKEN", "5_of0IcAEqBb6pOzPFTjLhIM0s412UWYiDXzbYvcBx4VfxYIugSQitCQSgHEBLT8QDP2id-Iw3jyx0a3yjDrNtn7lLCsK5lRYASzgtzOWdQMEyNnFpAfCeJ-GSOjyT8JizwCo9VIphS6yDB09vNDUaAJARHY");
        //发送模板消息  
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "POST", jsonmsg);
        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
                System.out.println("模板消息发送成功errorCode:{" + errorCode + "},errmsg:{" + errorMsg + "}");
            } else {

                System.out.println("模板消息发送失败errorCode:{" + errorCode + "},errmsg:{" + errorMsg + "}");
            }
        }
        System.out.println(result);
    }

    @Test
    public void test2() {
//        String deskIds = "8a9ad8035f6b72ba015f7275e2ec00b1,8a9ad8035f6b72ba015f7275f18c00b3";
//        //8a9ad8035f6b72ba015f7275f18c00b3
//        String[] strings = deskIds.split(",");
//        for(String s : strings){
//            System.out.println(s);
//        }

        String str = PinyinUtil.getPinYinHeadChar("AAA");
        System.out.println(str);
    }

    @Test
    public void test3() {
        String str = null;
        try {

            SubjectModel sub = new SubjectModel(0001, "login");
            str = TokenMgr.createJWT(Constant.JWT_ID, TokenMgr.generalSubject(sub), Constant.JWT_TTL);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis());

        //String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3OHNhd2RmZjUiLCJzdWIiOiJ4aWFvdGlhbnRpYW4iLCJpYXQiOjE0OTgwMzE0NDIsImlzcyI6IjEyMi4xMTQuMjE0LjE0NyIsImV4cCI6MTQ5ODAzMjY0Mn0.0h_kDhyZLhnt8TRgbLsOnVT8eOUAqgFTEZP-XgIGuA";
        try {
            System.out.println(TokenMgr.parseJWT(str));
            CheckResult result = TokenMgr.validateJWT(str);
            System.out.println(result.getErrCode() + " " + result.isSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test04() {


        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    @Test
    public void test05() {
        String str = "666666";
        try {
            System.out.println(SigNatureUtil.encodeECB(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
