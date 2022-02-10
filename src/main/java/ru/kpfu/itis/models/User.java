package ru.kpfu.itis.models;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String login;
    private String passwordHash;
    private String lastName;
    private String firstName;
}
