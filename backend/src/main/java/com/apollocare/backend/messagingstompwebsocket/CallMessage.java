package com.apollocare.backend.messagingstompwebsocket;

public class CallMessage {
    private String callType; 
    private Long callNumber;
    private String callPost;
    private String timestamp;

    public CallMessage() {}

    public CallMessage(String callType, Long callNumber, String callPost, String timestamp) {
        this.callType = callType;
        this.callNumber = callNumber;
        this.callPost = callPost;
        this.timestamp = timestamp;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Long getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(Long callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallPost() {
        return callPost;
    }

    public void setCallPost(String callPost) {
        this.callPost = callPost;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp= timestamp;
    }
}
