package com.apollocare.backend.websockets;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.apollocare.backend.messagingstompwebsocket.CallMessage;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class ScheduledPushMessages {

    private static final String[] CALL_TYPES = {"A", "B", "C", "D"};
    private static final String[] CALL_POSTS = {"P01", "P02", "P03", "P04", "P05"};
    private static final Random RANDOM = new SecureRandom();

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ScheduledPushMessages(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public CallMessage generateMessage() {
        String callType = CALL_TYPES[RANDOM.nextInt(CALL_TYPES.length)];
        Long callNumber = RANDOM.nextLong(1000) + 1;
        String callPost = CALL_POSTS[RANDOM.nextInt(CALL_POSTS.length)];
        final String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        return new CallMessage(callType, callNumber, callPost, time);
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        CallMessage newMessage = generateMessage();
        simpMessagingTemplate.convertAndSend("/topic/pushmessages", newMessage);
    }
    
}
