package org.jeecgframework.core.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangzf on 2016/6/6.
 */
public class SellerSmsClient {
    protected static String smsSvcUrl = "http://43.243.130.33:8860";	   //服务器URL 地址
    protected static String cust_code = "300489";									 //账号
    protected static String password = "7Z87YKJE8C";		 								//密码
    protected static String sp_code = "1069056370540851";                  //接入码（扩展码）

    public void sendSms(String mobiles, String content) throws IOException {
        sendSms(mobiles, content, sp_code, 0);
    }

    public void sendSms(String mobiles, String content, long task_id)
            throws IOException {
        sendSms(mobiles, content, sp_code, task_id);
    }

    public void sendSms(String mobiles, String content, String sp_code)
            throws IOException {
        sendSms(mobiles, content, sp_code, 0);
    }

    public static void sendSms(String mobiles, String content, String sp_code,
                        long task_id) throws IOException {
        String urlencContent = URLEncoder.encode(content,"utf-8");
        //String sign = MD5.getMD5((urlencContent + password).getBytes());
        String sign=MD5.sign(urlencContent, password, "utf-8");
        String postData = "content=" + urlencContent + "&destMobiles="
                + mobiles + "&sign=" + sign + "&cust_code=" + cust_code
                + "&sp_code=" + sp_code + "&task_id=" + task_id;
        //System.err.println(postData);
        URL myurl = new URL(smsSvcUrl);
        URLConnection urlc = myurl.openConnection();
        urlc.setReadTimeout(1000 * 30);
        urlc.setDoOutput(true);
        urlc.setDoInput(true);
        urlc.setAllowUserInteraction(false);

        DataOutputStream server = new DataOutputStream(urlc.getOutputStream());
        //System.out.println("发送数据=" + postData);

        server.write(postData.getBytes("utf-8"));
        server.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                urlc.getInputStream(), "utf-8"));
        String resXml = "", s = "";
        while ((s = in.readLine()) != null)
            resXml = resXml + s + "\r\n";
        in.close();
        System.out.println("接收数据=" + URLDecoder.decode(resXml,"utf-8"));
    }

    public static String sendMessage(String mobiles) throws IOException {
        String smsCode = generateRandom(true,4);
        String content = "欢迎使用点单么，您的验证码是 : \"" + smsCode.toString()  +"\",请于5分钟内进行输入。";
        sendSms(mobiles,content,sp_code,0);
        return smsCode;
    }

    public static void sendMessage1(String mobiles1,double money,String mobiles2)throws IOException{
        String date = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String content = "欢迎使用点单么，用户\"" +mobiles1+"\"在"+ date  +"充值"+money+"元。";
        sendSms(mobiles2,content,sp_code,0);
    }

    public static void sendMessageForAgency(String mobiles,String invite_code)throws IOException{
        String content = "尊敬的用户您好，恭喜您成为点单么代理商，代理商后台网址为：http://help.vhelpu.cn/tcsubang_agency，邀请码为："+invite_code+"，账户密码与app账户密码一致。若出现浏览器打不开的情况，请使用谷歌浏览器。感谢您对点单么的支持。";
        sendSms(mobiles,content,sp_code,0);
    }

    public static void sendMessageForCompany(String mobiles,String address)throws IOException{
        String content = "尊敬的用户您好，恭喜您成为点单么"+address+"分公司管理员，分公司后台管理网址为：http://help.vhelpu.cn，账户密码为手机号码。若出现浏览器打不开的情况，请使用谷歌浏览器。感谢您对点单么的支持。";
        sendSms(mobiles,content,sp_code,0);
    }
    
    
    public static void sendMessageForContent(String mobiles,String content)throws IOException{
       // String content = "尊敬的用户您好，恭喜您成为点单么"+address+"分公司管理员，分公司后台管理网址为：http://help.vhelpu.cn，账户密码为手机号码。若出现浏览器打不开的情况，请使用谷歌浏览器。感谢您对点单么的支持。";
        sendSms(mobiles,content,sp_code,0);
    }

    public static String generateRandom(boolean numberFlag, int length){
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    public static void main(String[] args) {
        SellerSmsClient client = new SellerSmsClient();
        try {
            client.sendMessage1("用户名",0.211,"18150166685");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
