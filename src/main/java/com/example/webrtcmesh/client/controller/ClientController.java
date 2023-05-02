package com.example.webrtcmesh.client.controller;

import com.example.webrtcmesh.signalling.handler.SignallingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientController {

    @GetMapping("/client")
    public void clientGet() {

    }

    @GetMapping("/test")
    public void testGet() {
    }

    @GetMapping("/list")
    public void getRoomList(Model model){
        List<WebSocketSession> sessionList = new ArrayList<>();
        SignallingHandler.chatRoomMap.put("test1", sessionList);
        SignallingHandler.chatRoomMap.put("test2", sessionList);
        SignallingHandler.chatRoomMap.put("test3", sessionList);
        model.addAttribute("roomList", SignallingHandler.chatRoomMap.keySet());
    }

}
