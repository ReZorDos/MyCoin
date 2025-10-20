package ru.kpfu.itis.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    private String email;
    private String password;
}
