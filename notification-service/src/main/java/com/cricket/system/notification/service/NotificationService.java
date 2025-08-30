package com.cricket.system.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "player-events", groupId = "notification-group")
    public void handlePlayerEvents(String message) {
        System.out.println("Received player event: " + message);
        // Send notification to WebSocket clients
        messagingTemplate.convertAndSend("/topic/notifications", 
            new NotificationMessage("PLAYER_EVENT", message));
    }

    @KafkaListener(topics = "match-events", groupId = "notification-group")
    public void handleMatchEvents(String message) {
        System.out.println("Received match event: " + message);
        messagingTemplate.convertAndSend("/topic/notifications", 
            new NotificationMessage("MATCH_EVENT", message));
    }

    @KafkaListener(topics = "team-events", groupId = "notification-group")
    public void handleTeamEvents(String message) {
        System.out.println("Received team event: " + message);
        messagingTemplate.convertAndSend("/topic/notifications", 
            new NotificationMessage("TEAM_EVENT", message));
    }

    public static class NotificationMessage {
        private String type;
        private String message;
        private long timestamp;

        public NotificationMessage(String type, String message) {
            this.type = type;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
}