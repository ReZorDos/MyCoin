package ru.kpfu.itis.service.user.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.repository.UserRepository;
import ru.kpfu.itis.service.user.UserService;

import java.util.UUID;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void changeUserBalance(UUID userId, String type, String sum) {
        userRepository.updateUserBalance(userId, type, Double.parseDouble(sum));
    }

    @Override
    public double getUserBalance(UUID userId) {
        return userRepository.getUserBalance(userId);
    }

    @Override
    public String getNicknameOfUser(UUID userId) {
        return userRepository.findNickNameOfUser(userId);
    }

}
