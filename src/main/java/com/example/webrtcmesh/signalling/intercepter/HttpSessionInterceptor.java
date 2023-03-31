package com.example.webrtcmesh.signalling.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.thymeleaf.util.StringUtils;

import java.util.Map;

@Slf4j
public class HttpSessionInterceptor implements HandshakeInterceptor {

    //HandShake 시작하기 전 호출되는 메소드
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        ServletServerHttpRequest sshreq = (ServletServerHttpRequest) request;
        HttpServletRequest servletRequest = sshreq.getServletRequest();
        String id = (String)servletRequest.getParameter("id");
        if(!StringUtils.isEmpty(id)){
            log.warn("id : " + id);
            attributes.put("id", id);
        }
        return true;
    }

    //HandShake 시작 후 호출되는 메소드
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
