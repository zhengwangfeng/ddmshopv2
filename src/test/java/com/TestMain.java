/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月25日 下午4:36:28
 */
package com;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月25日 下午4:36:28
 */



import net.sf.json.JSONObject;
//对接口进行测试
public class TestMain {
	
	public static void main(String[] args) throws Exception{
		
			URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" +
                    "Opxwyh5qnXv_HjiIWfOZnH3qF22fgUpMtgQf8EA_X3ykgV-F9T1gUQVobL4MQxeZgcqFr8eBQZb9CFhiHhZa3oHOvNwpqJcPRonVKRjqjTgMQRbAIAVZA");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);

            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            conn.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    conn.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.element("page", "pages/index/index");
            obj.element("scene", "20171023222220665136772540101225");

            out.writeBytes(obj.toString());
            out.flush();

            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File("D:\\BeautyGir8.jpg");
            //创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            outStream.write(data);

            //关闭输出流
            conn.disconnect();
            out.close();
            outStream.close();
            
	}
	
	
	
	
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		inStream.close();

		return outstream.toByteArray();
	}
	
}
