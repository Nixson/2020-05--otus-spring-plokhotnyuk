package ru.diasoft.nixson.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GenreDto {
    private String id;
    private String bookId;
    private String name;
}
