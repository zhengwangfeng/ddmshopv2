package org.jeecgframework.core.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

public class JSONObjectMapper extends ObjectMapper{
	 public JSONObjectMapper() {
	        CustomSerializerFactory factory = new CustomSerializerFactory();  
	        factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {  
	            @Override  
	            public void serialize(Date value, JsonGenerator jsonGenerator,  
	                    SerializerProvider provider) throws IOException, JsonProcessingException {  
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                jsonGenerator.writeString(sdf.format(value));  
	            }  
	        });  
//	     // 空值处理为空串  
//	        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()  
//	        {  
	//  
//	            @Override  
//	            public void serialize(  
//	                    Object value,  
//	                    JsonGenerator jg,  
//	                    SerializerProvider sp) throws IOException, JsonProcessingException  
//	            {  
//	                jg.writeString("");  
//	            }  
//	        });  
//	        this.setSerializerFactory(factory);  
	    }

	    /**
	     * 将对象转换成json字符串格式（默认将转换所有的属性）
	     * 
	     * @param o
	     * @return
	     */
	    public static String toJsonStr(Object value){
	    	try{
	    		return new ObjectMapper().writeValueAsString(value).replaceAll("null", "");
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		return null;
	    	}
	    }
	    
	    /**
	     * 将对象转换成json字符串格式（默认将转换所有的属性）
	     * 
	     * @param o
	     * @return
	     */
	    public static <T> T jsonToObject(String json, Class<T> c){  
	        T o = null;  
	        try{  
	            o = new ObjectMapper().readValue(json, c);  
	        } catch (IOException e){  
	            // 处理异常  
	        }  
	        return o;  
	    }
}
