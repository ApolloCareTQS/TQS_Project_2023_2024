package com.apollocare.backend.controller;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.apollocare.backend.messagingstompwebsocket.CallMessage;
import com.apollocare.backend.messagingstompwebsocket.Call;

@Controller
public class CallScreenController {

  @MessageMapping("/callscreen")
  @SendTo("/topic/pushmessages")
  public CallMessage send(Call call) throws Exception {
    final String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
    return new CallMessage(call.getCallType(), call.getCallNumber(), call.getCallPost(), time); 
  }

}