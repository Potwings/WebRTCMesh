package com.example.webrtcmesh.signalling.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/signalling")
@Slf4j
public class SignallingHandler extends TextWebSocketHandler {

    Map<String, InetSocketAddress> addressList = new HashMap<>();

    /**
     * 클라이언트의 포트 정보는 지정해두고 사용하는게 맞고
     * 상대 클라이언트 측의 IP를 비롯한 정보를 불러와 직접 클라이언트 간의 통신을 할 수 있도록 해주는 것이 맞다.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        InetSocketAddress remoteAddress = session.getRemoteAddress();
        String id = (String)session.getAttributes().get("id");
        addressList.put(id, remoteAddress);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String callee = message.getPayload();
        TextMessage textMessage = new TextMessage(addressList.get(callee).toString().substring(1));
        session.sendMessage(textMessage);
        log.warn("{}로부터 {} 받음", session.getId(), message.getPayload());
    }
}
