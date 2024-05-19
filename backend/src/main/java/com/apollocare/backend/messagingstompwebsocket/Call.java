package com.apollocare.backend.messagingstompwebsocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Call {
    private String callType;
    private Long callNumber;
    private String callPost;
}
