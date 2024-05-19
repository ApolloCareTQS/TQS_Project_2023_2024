package com.apollocare.backend;

import com.apollocare.backend.messagingstompwebsocket.Call;
import com.apollocare.backend.messagingstompwebsocket.CallMessage;
import com.apollocare.backend.websockets.ScheduledPushMessages;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("fixedport")
@MockBean(ScheduledPushMessages.class)
public class CallScreenControllerTest {

    private static final Logger logger = Logger.getLogger(CallScreenControllerTest.class.getName());

    private WebSocketStompClient stompClient;

    private BlockingQueue<CallMessage> blockingQueue = new LinkedBlockingDeque<>();

    @Test
    public void testWebSocketConnection() throws Exception {
        stompClient = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session = stompClient.connectAsync("ws://localhost:8080/callscreen", new StompSessionHandlerAdapter() {
            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers,
                    byte[] payload, Throwable exception) {
                throw new RuntimeException("Failure in WebSocket handling", exception);
            }
        }).get(5, TimeUnit.SECONDS); 

        logger.info("WebSocket connection established.");

        session.subscribe("/topic/pushmessages", new CallStompFrameHandler());
        logger.info("Subscribed to /topic/pushmessages.");

        Call call = new Call();
        call.setCallType("TypeA");
        call.setCallNumber(12345L);
        call.setCallPost("Post1");

        session.send("/app/callscreen", call);
        logger.info("Sent call message to /app/callscreen.");

        CallMessage message = blockingQueue.poll(6, TimeUnit.SECONDS); 
        assertNotNull(message, "The message should not be null");

        logger.info("Received message: " + message);

        assertEquals("TypeA", message.getCallType());
        assertEquals(12345L, message.getCallNumber());
        assertEquals("Post1", message.getCallPost());
    }

    private class CallStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(org.springframework.messaging.simp.stomp.StompHeaders headers) {
            return CallMessage.class;
        }

        @Override
        public void handleFrame(org.springframework.messaging.simp.stomp.StompHeaders headers, Object payload) {
            logger.info("Received message frame: " + payload);
            blockingQueue.offer((CallMessage) payload);
        }

    }
}
