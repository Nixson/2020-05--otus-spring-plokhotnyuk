package ru.diasoft.nixson.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private Long id;
    private String name;
}
