package ru.kpfu.itis.service.auth.impl;

import org.mindrot.jbcrypt.BCrypt;
import ru.kpfu.itis.service.auth.PasswordEncoder;

public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
