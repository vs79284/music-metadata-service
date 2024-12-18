package com.assignmnt.ice.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;


@Component
@Scope("prototype")
public class Helper {

    public String generateId(String title){
        try {
            String input = title ;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(hash).substring(0, 16); // 16-char ID
        } catch (Exception e) {
            throw new RuntimeException("Error generating ID", e);
        }
    }
}
