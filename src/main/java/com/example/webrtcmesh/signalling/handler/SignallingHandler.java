package com.example.webrtcmesh.signalling.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/signalling")
@Slf4j
public class SignallingHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
    }

    //클라이언트 단에서 구글 stun 서버와 통신 진행
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(WebSocketSession webSocketSession : sessionList){
            if(webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())){
                webSocketSession.sendMessage(message);
            }
        }
    }
}
