package com.tcsb.common;

import net.sf.json.JSONObject;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/tcsbClientController")
public class TcsbClientController {
    private volatile Map<String,CommonService> services;
    private static Map<String,String> servicesName;
    private static Map<String,String> servicesAuthority;
    private static Map<String,String> servicesEntity;

    static{
        if(servicesEntity==null && servicesName==null && servicesAuthority == null){

            servicesName = new HashMap<String,String>();
            servicesAuthority = new HashMap<String,String>();
            for (NoticeEnum e : NoticeEnum.values()) {

                servicesName.put(e.getServiceCode(),e.getServiceName());
                servicesAuthority.put(e.getServiceCode(),e.getAuthority());
            }
        }
        servicesAuthority = Collections.unmodifiableMap(servicesAuthority);
        servicesName = Collections.unmodifiableMap(servicesName);
    }

    @RequestMapping(params = "notice")
    public Map<String,String> receiveNotice(String str) {
        if(services==null){
            initServices();
        }
        if(servicesEntity==null){
            initServicesEntity();
        }

        JSONObject json = JSONObject.fromObject(str);
        String serviceCode = (String)json.get("serviceCode");
        String serviceName = servicesName.get(serviceCode);
        json.remove("serviceCode");
        String operation = (String)json.get("operation");
        json.remove("operation");

        try{

            Object obj = services.get(serviceName).getEntity(Class.forName(servicesEntity.get(serviceName)),(String)json.get("id"));

        }catch (ClassNotFoundException e){


        }


        return null;
    }

    private String getEntityClassNameByService(String name){
        Method[] methods = services.get(name).getClass().getMethods();
        for(Method m : methods){
            if(m.getName().equals("save")){
                for(Class c : m.getParameterTypes()){
                    if(m.getParameterTypes().length>0 && m.getParameterTypes().length==1){
                        if(c.getName().contains("tcsb") || c.getName().contains("weixin")){
                            return c.getName();
                        }
                    }
                }
            }
        }
        return null;
    }

    private void initServicesEntity(){
        synchronized (TcsbClientController.class){
            if(servicesEntity==null){
                servicesEntity = new HashMap<String,String>();
                for (NoticeEnum e : NoticeEnum.values()) {
                    servicesEntity.put(e.getServiceName(),getEntityClassNameByService(e.getServiceName()));
                }
            }
            servicesEntity = Collections.unmodifiableMap(servicesEntity);
        }
    }

    private void initServices(){
        synchronized (TcsbClientController.class){
            if(services==null){
                services = new HashMap<String,CommonService>();
                ApplicationContext context = ApplicationContextUtil.getContext();
                for (NoticeEnum e : NoticeEnum.values()) {
                    services.put(e.getServiceName(),(CommonService) context.getBean(e.getServiceName()));
                }
            }
            services = Collections.unmodifiableMap(services);
        }
    }

    public Map<String, CommonService> getServices() {
        return services;
    }

    public static Map<String, String> getServicesName() {
        return servicesName;
    }

    public static Map<String, String> getServicesAuthority() {
        return servicesAuthority;
    }

    public static Map<String, String> getServicesEntity() {
        return servicesEntity;
    }

    public static String captureStr(String str){
        char[] cs = str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
    public static String lowerStr(String str){
        char[] cs = str.toCharArray();
        cs[0]+=32;
        return String.valueOf(cs);
    }

}
