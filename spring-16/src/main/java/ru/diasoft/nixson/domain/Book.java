package ru.diasoft.nixson.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {
    @Id
    private String id;

    @Indexed
    private String name;

    private String year;

    private String description;

    private List<Author> authors;

    private List<Genre> genres;

    private List<Comment> comments;
}
