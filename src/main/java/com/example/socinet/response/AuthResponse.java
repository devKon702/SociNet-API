package com.example.socinet.response;

import com.example.socinet.dto.AccountDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private AccountDto account;
    @JsonProperty("isSuccess")
    private boolean isSuccess;
}
