package com.example.socinet.controller;

import com.example.socinet.dto.UserDto;
import com.example.socinet.response.Response;
import com.example.socinet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("user")
    public ResponseEntity<?> getUserInfo(){
        UserDto userDto = userService.getUserInfo();
        Response response = Response.builder()
                .isSuccess(true)
                .message("Get user information success")
                .data(userDto)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserInfoById(@PathVariable Long id) throws Exception{
        UserDto userDto = userService.getUserInfoById(id);
        Response response = Response.builder()
                .isSuccess(true)
                .message("Get user info success")
                .data(userDto)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("user")
    public ResponseEntity<?> updateUserInfo(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String phone,
                                            @RequestParam(required = false) String school,
                                            @RequestParam(required = false) String address,
                                            @RequestParam(required = false) Boolean isMale,
                                            @RequestParam(required = false) MultipartFile avatar) throws Exception{
        UserDto userDto = userService.updateUserInfo(name, phone, school, address, isMale, avatar);
        Response response = Response.builder()
                .isSuccess(true)
                .message("Update info success")
                .data(userDto)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
