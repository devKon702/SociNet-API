package com.example.socinet.util;

import com.example.socinet.security.AccountDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static AccountDetail getAccountDetail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AccountDetail) authentication.getPrincipal();
    }
}
