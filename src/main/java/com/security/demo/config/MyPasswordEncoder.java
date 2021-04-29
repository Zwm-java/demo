package com.security.demo.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String s1 = charSequence.toString();
        return Objects.equals(s1,s);
    }
}
