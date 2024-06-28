package com.example.socinet.service;

import com.example.socinet.dto.AccountDto;
import com.example.socinet.entity.Account;
import com.example.socinet.entity.Role;
import com.example.socinet.entity.User;
import com.example.socinet.jwt.JwtProvider;
import com.example.socinet.repository.AccountRepository;
import com.example.socinet.repository.UserRepository;
import com.example.socinet.request.SignInRequest;
import com.example.socinet.request.SignUpRequest;
import com.example.socinet.response.AuthResponse;
import com.example.socinet.response.Response;
import com.example.socinet.security.AccountDetail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public Response signUp(SignUpRequest signUpRequest) throws Exception {
        // Kiểm tra username có trùng không
        if(accountRepo.existsByUsername(signUpRequest.getUsername())){
            throw new Exception("Username has been used");
        }
        // Kiểm tra độ dài password
        if(signUpRequest.getPassword().length() < 6){
            throw new Exception("Password must > 6 characters");
        }
        // Kiểm tra email có trùng không
        if(accountRepo.existsByEmail(signUpRequest.getEmail())){
            throw new Exception("Email has bean used");
        }
        // Build user
        User user = User.builder()
                .name(signUpRequest.getName())
                .isMale(true)
                .build();
        // Build account
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER"));
        Account account = Account.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .isActive(true)
                .roles(roles)
                .isEmailAuth(false)
                .user(userRepo.save(user))
                .build();

        AccountDto accountDto = new AccountDto(accountRepo.save(account));
        return Response.builder()
                .isSuccess(true)
                .message("Sign up success")
                .data(accountDto)
                .build();
    }

    public AuthResponse signIn(SignInRequest signInRequest) throws Exception{
        // Xác thực
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsername(),
                        signInRequest.getPassword()));
        Optional<Account> account = accountRepo.findAccountByUsername(signInRequest.getUsername());
        // Xác thực thành công
        if(account.isPresent() && authentication.isAuthenticated()){
            AccountDto accountDto = new AccountDto(account.get());
            String accessToken = jwtProvider.generateAccessToken(accountDto.getUsername());
            String refreshToken = jwtProvider.generateRefreshToken(accountDto.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .account(accountDto)
                    .isSuccess(true)
                    .build();
        } else{
            throw new Exception("Authentication failed");
        }
    }
}
