package com.example.webrtcmesh.signalling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDTO {

    private String name;

    //추후 WebSocketSession을 포함한 사용자 객체로 변경
    private List<WebSocketSession> sessionList;
}
