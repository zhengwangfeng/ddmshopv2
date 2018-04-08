/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年5月24日 下午5:50:20
 */
package com.milanosoft.RCS.web.webSocket.config;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年5月24日 下午5:50:20
 */

import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.EnableWebMvc;  
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;  
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.config.annotation.EnableWebSocket;  
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;  
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;  
  
import org.springframework.context.annotation.Bean;  
  
@Configuration  
@EnableWebMvc  
@EnableWebSocket  
public class WebSocketConfig extends WebMvcConfigurerAdapter implements  
        WebSocketConfigurer {  
  
    public WebSocketConfig() {  
    }  
  
    @Override  
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {  
        registry.addHandler(systemWebSocketHandler(), "/websck").addInterceptors(new HandshakeInterceptor());  
  
        System.out.println("registed!");  
        registry.addHandler(systemWebSocketHandler(), "/sockjs/websck").addInterceptors(new HandshakeInterceptor())  
                .withSockJS();  
  
    }  
  
    @Bean  
    public WebSocketHandler systemWebSocketHandler() {  
        //return new InfoSocketEndPoint();  
        return new SystemWebSocketHandler();  
    }  
  
}  