package com.apollocare.backend.messagingstompwebsocket;

public class Call {
    private String callType; 
    private Long callNumber;
    private String callPost;

    public Call(String callType, Long callNumber, String callPost) {
        this.callType = callType;
        this.callNumber = callNumber;
        this.callPost = callPost;
    }

    public Call() {
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
}
