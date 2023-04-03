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

    Map<String, List<String>> addressMap = new HashMap<>();

    /**
     * 클라이언트의 포트 정보는 지정해두고 사용하는게 맞고
     * 상대 클라이언트 측의 IP를 비롯한 정보를 불러와 직접 클라이언트 간의 통신을 할 수 있도록 해주는 것이 맞다.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String id = (String) session.getAttributes().get("id");
        List<String> addressList = addressMap.get(id);
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        String address = session.getRemoteAddress().toString().substring(1);
        addressList.add(address);
        addressMap.put(id, addressList);
        log.info("{} 채팅방에 {}가 접속", id, address);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String callee = message.getPayload();
        Gson gson = new Gson();
        List<String> addressList = addressMap.get(callee);
        String json = gson.toJson(addressList);
        TextMessage textMessage = new TextMessage(json);
        session.sendMessage(textMessage);
        log.info("{} 채팅방 접속 요청 : 클라이언트 목록 {}", message.getPayload(), json);
    }
}
