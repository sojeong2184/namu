package com.durianfirst.durian.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    private String roomId;
    private String mid;
    private Set<WebSocketSession> sessions = new HashSet<>();
    private LocalDateTime regDate;

    @Builder
    public ChatRoom(String roomId, String mid, LocalDateTime regDate) {
        this.roomId = roomId;
        this.mid = mid;
        this.regDate = regDate;
    }
}
