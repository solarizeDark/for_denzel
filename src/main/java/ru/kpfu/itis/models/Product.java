package ru.kpfu.itis.models;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Product {
    private Long id;
    private String title;
    private Double cost;
    private String description;
}
