package com.apollocare.backend;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.apollocare.backend.messagingstompwebsocket.CallMessage;
import com.apollocare.backend.websockets.ScheduledPushMessages;

class ScheduledPushMessagesTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    private ScheduledPushMessages scheduledPushMessages;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduledPushMessages = spy(new ScheduledPushMessages(simpMessagingTemplate));
    }

    @Test
    void testGenerateMessage() {
        CallMessage message = scheduledPushMessages.generateMessage();

        assertNotNull(message);
        assertTrue(message.getCallType().matches("[A-D]"));
        assertTrue(message.getCallNumber() > 0 && message.getCallNumber() <= 1000);
        assertTrue(message.getCallPost().matches("P0[1-5]"));
        assertNotNull(message.getTimestamp());
    }

    @Test
    void testSendMessage() {
        CallMessage message = new CallMessage("A", 1L, "P01", "12:00:00");

        when(scheduledPushMessages.generateMessage()).thenReturn(message);

        scheduledPushMessages.sendMessage();

        verify(simpMessagingTemplate).convertAndSend("/topic/pushmessages", message);
    }
}
