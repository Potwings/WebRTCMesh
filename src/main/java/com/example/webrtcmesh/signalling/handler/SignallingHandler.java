package com.example.webrtcmesh.signalling.handler;

import com.example.webrtcmesh.signalling.dto.ChatRoomDTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RequestMapping("/signalling")
@Slf4j
public class SignallingHandler extends TextWebSocketHandler {

    public static Map<String, List<WebSocketSession>> chatRoomMap = new HashMap<>();

    private static final String DELIMITER = ":";

    private static final String CREATE = "create";

    private static final String JOIN = "join";

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        String roomName = (String) session.getAttributes().get("roomName");
        for (WebSocketSession webSocketSession : chatRoomMap.get(roomName)) {
            if (webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomName = (String) session.getAttributes().get("roomName");
        List<WebSocketSession> sessionList = chatRoomMap.get(roomName);
        sessionList.add(session);
        log.warn("{} new Session Add : {} ", roomName, session);
    }
}
