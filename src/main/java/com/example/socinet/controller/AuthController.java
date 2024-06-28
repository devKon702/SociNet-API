package com.example.socinet.controller;

import com.example.socinet.request.SignInRequest;
import com.example.socinet.request.SignUpRequest;
import com.example.socinet.response.AuthResponse;
import com.example.socinet.response.Response;
import com.example.socinet.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest body) throws Exception {
        Response response = authService.signUp(body);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest body, HttpServletRequest request) throws Exception{
        log.info(request.getRemoteAddr());
        AuthResponse response = authService.signIn(body);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
