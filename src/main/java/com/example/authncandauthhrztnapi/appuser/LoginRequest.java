package com.example.authncandauthhrztnapi.appuser;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class LoginRequest {
    private final  String email;
    private final String password;
}
