package ru.kpfu.itis.service.auth;

public interface PasswordEncoder {

    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);

}
