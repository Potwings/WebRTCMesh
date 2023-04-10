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

    //클라이언트 아이디 설정 추후 개발
    //Map<String, String> idMap = new HashMap<>();

    Map<String, List<String>> chatRoomMap = new HashMap<>();

    private static final String DELIMITER = ":";

    private static final String CREATE = "create";

    private static final String JOIN = "join";

    /**
     * 클라이언트의 포트 정보는 지정해두고 사용하는게 맞고
     * 상대 클라이언트 측의 IP를 비롯한 정보를 불러와 직접 클라이언트 간의 통신을 할 수 있도록 해주는 것이 맞다.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String id = (String) session.getAttributes().get("id");
        String clientInfo = session.getRemoteAddress().toString().substring(1);
        //idMap.put(clientInfo, id);
        log.info("사용자 접속 : id={}, clientInfo={}", id, clientInfo);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String msg = message.getPayload();
        int headerIndex = msg.indexOf(DELIMITER);
        String header = msg.substring(0, headerIndex);
        String body = msg.substring(headerIndex);
        List<String> chatRoom = null;
        switch (header) {
            case CREATE:
                chatRoom = new ArrayList<>();
                chatRoom.add(session.getRemoteAddress().toString());
                chatRoomMap.put(body, chatRoom);
                break;

            case JOIN:
                chatRoom = chatRoomMap.get(body);
                chatRoom.add(session.getRemoteAddress().toString());
                chatRoomMap.put(body, chatRoom);
                break;
        }
        Gson gson = new Gson();
        String json = gson.toJson(chatRoom);
        TextMessage textMessage = new TextMessage(json);
        session.sendMessage(textMessage);
        log.info("{} 채팅방 접속 요청 : 클라이언트 목록 {}", message.getPayload(), json);
    }
}
