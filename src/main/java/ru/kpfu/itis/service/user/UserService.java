package ru.kpfu.itis.service.user;

import java.util.UUID;

public interface UserService {

    void changeUserBalance(UUID userId, String type, String sum);

    double getUserBalance(UUID userId);

    String getNicknameOfUser(UUID userId);

}
