package com.ama.don.admin.temp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MakePW {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "minha0726";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("password: " + encodedPassword);
    }
}
