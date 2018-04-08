package org.jeecgframework.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumberGenerateUtil {
	public static String getOrderNumber(){
		  String date = new SimpleDateFormat("yyyyMMdd").format(new Date());  
	        String seconds = new SimpleDateFormat("HHmmss").format(new Date());  
	        return date+getTwo()+seconds+getTwo();
	}
	/** 
     * 产生随机的2位数 
     * @return 
     */  
    public static String getTwo(){  
        Random rad=new Random();  
  
        String result  = rad.nextInt(100) +"";  
  
        if(result.length()==1){  
            result = "0" + result;  
        }  
        return result;  
    }  
    public static void main(String[] args) {
    	System.out.println(getOrderNumber());
	}
}
