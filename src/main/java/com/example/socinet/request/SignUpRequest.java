package com.example.socinet.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequest {
    private String username ="";
    private String password = "";
    private String name = "";
    private String email = "";
}
