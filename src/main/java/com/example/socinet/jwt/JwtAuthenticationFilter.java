package com.example.socinet.jwt;

import com.example.socinet.security.AccountDetail;
import com.example.socinet.security.AccountDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AccountDetailService accountDetailService;

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra header Authorization có jwt token ko
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(request);
            if(StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)){
                String username = jwtProvider.getSubjectFromJwt(jwt);
                AccountDetail accountDetail = (AccountDetail) accountDetailService.loadUserByUsername(username);
                if(accountDetail != null){
                    // Nếu hợp lệ, set thông tin cho security context
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(accountDetail, null, accountDetail.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch(Exception e){
            log.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
