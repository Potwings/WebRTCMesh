package com.example.webrtcmesh.signalling.controller;

import com.example.webrtcmesh.signalling.handler.SignallingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void getList() {
    }

    @PostMapping("/list")
    @ResponseBody
    public List<String> postList() {
        List<WebSocketSession> sessionList = new ArrayList<>();
        SignallingHandler.chatRoomMap.put("test", sessionList);
        SignallingHandler.chatRoomMap.put("test1", sessionList);
        SignallingHandler.chatRoomMap.put("test2", sessionList);
        List<String> testList = SignallingHandler.chatRoomMap.keySet().stream().collect(Collectors.toList());
        return testList;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRoomPost(String roomName) {
        List<WebSocketSession> sessionList = new ArrayList<>();
        SignallingHandler.chatRoomMap.put(roomName, sessionList);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
