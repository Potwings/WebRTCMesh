package com.example.webrtcmesh.signalling.config;

import com.example.webrtcmesh.signalling.handler.SignallingHandler;
import com.example.webrtcmesh.signalling.intercepter.HttpSessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    static Map<String,List<WebSocketSession>> sessionList = new HashMap<>();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(signalingHandler(), "/signalling")
                .addInterceptors(httpSessionInterceptor())
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public WebSocketHandler signalingHandler(){
        return new SignallingHandler();
    }

    @Bean
    public HandshakeInterceptor httpSessionInterceptor(){
        return new HttpSessionInterceptor();
    }
}
