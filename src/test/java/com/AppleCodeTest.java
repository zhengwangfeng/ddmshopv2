/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月2日 下午5:07:11
 */
package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import net.sf.json.JSONObject;


/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月2日 下午5:07:11
 */
public class AppleCodeTest {
	
	public static void main(String[] args) throws Exception{
		//String result = HttpRequest.sendPost("http://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+access_token, path);
       // System.out.println(id);
		String access_token = "hvNwXcYq0T8EvJ_HYVDSXLncXOwwS4aoNQEKbVytPRYFl4adDCAtY_NdnUA-JAiLdNbYfLc1X871PKu-yO6PlpYwtaXkmYilJzUgGeoJ2tQC4ClWCLrZDlKZo7fgvvJcHLThAJAPTB";
        //String url ="https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=";//小程序二维码
        //String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=";
        String url ="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
        
        
        
         Map<String, Object> map = new HashMap<String, Object>();
         //map.put("path", "pages/index/index");
        
         map.put("page", "pages/home/home");//你二维码中跳向的地址
         map.put("scene", "8a9ccf875c3f33ab015c427f271a00e6");//图片大小
         JSONObject json = JSONObject.fromObject(map);
         System.out.println(json);
        HttpResponse  res= AppleCodeTest.httpPostWithJSON(url
                     + access_token, json.toString());
        
        
        
         System.out.println(res);
		
	}
	
	
	public static HttpResponse httpPostWithJSON(String url, String json)
            throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
  
        StringEntity se = new StringEntity(json);
        se.setContentType("application/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                        "UTF-8"));
        httpPost.setEntity(se);
        // httpClient.execute(httpPost);
        HttpResponse response = httpClient.execute(httpPost);
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                    InputStream instreams = resEntity.getContent(); 
                    String uploadSysUrl = "D://erweima/";
                    File saveFile = new File(uploadSysUrl+"home.png");
                       // 判断这个文件（saveFile）是否存在
                       if (!saveFile.getParentFile().exists()) {
                           // 如果不存在就创建这个文件夹
                           saveFile.getParentFile().mkdirs();
                       }
                saveToImgByInputStream(instreams, uploadSysUrl,"home.png");
            }
        }
        httpPost.abort();
        return response;
    } 
	
	
    /* @param instreams 二进制流
    * @param imgPath 图片的保存路径
    * @param imgName 图片的名称
    * @return
    *      1：保存正常
    *      0：保存失败
    */
   public static int saveToImgByInputStream(InputStream instreams,String imgPath,String imgName){
  
       int stateInt = 1;
       if(instreams != null){
           try {
               File file=new File(imgPath+imgName);//可以是任何图片格式.jpg,.png等
               FileOutputStream fos=new FileOutputStream(file);
                    
               byte[] b = new byte[1024];
               int nRead = 0;
               while ((nRead = instreams.read(b)) != -1) {
                   fos.write(b, 0, nRead);
               }
               fos.flush();
               fos.close();                
           } catch (Exception e) {
               stateInt = 0;
               e.printStackTrace();
           } finally {
               try {
                instreams.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           }
       }
       return stateInt;
   }
	

}
