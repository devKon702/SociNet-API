package com.example.socinet.dto;

import com.example.socinet.entity.Account;
import com.example.socinet.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class UserDto {
    Long id;
    String name;
    String phone;
    String school;
    String address;
    String avatarUrl;
    @JsonProperty("isMale")
    boolean isMale;
    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.school = user.getSchool();
        this.address = user.getAddress();
        this.avatarUrl = user.getAvatarUrl();
        this.isMale = user.isMale();
    }
}
