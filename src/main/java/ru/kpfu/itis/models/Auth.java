package ru.kpfu.itis.models;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Auth {
    private Long id;
    private User user;
    private String cookieValue;
}
