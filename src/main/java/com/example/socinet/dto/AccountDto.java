package com.example.socinet.dto;

import com.example.socinet.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class AccountDto {
    String username;
    @JsonIgnore
    String password;
    String email;
    @JsonProperty("isEmailAuth")
    boolean isEmailAuth;
    @JsonProperty("isActive")
    boolean isActive;
    Set<String> roles;
    UserDto user;
    public AccountDto(Account account){
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.email = account.getEmail();
        this.isEmailAuth = account.isEmailAuth();
        this.isActive = account.isActive();
        roles = new HashSet<>();
        account.getRoles().stream().forEach(role -> roles.add(role.getRole()));
        user = new UserDto(account.getUser());
    }
}
