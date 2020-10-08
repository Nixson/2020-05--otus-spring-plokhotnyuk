package ru.diasoft.nixson.libConvert.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    private String name;
    private String year;
    private String description;

    private List<Author> authors;
    private List<Genre> genres;
}
