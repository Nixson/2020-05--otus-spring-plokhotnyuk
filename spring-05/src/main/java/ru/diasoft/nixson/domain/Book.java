package ru.diasoft.nixson.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private Author author;
    private Genre genre;
    private String name;
    private String year;
    private String description;
}
