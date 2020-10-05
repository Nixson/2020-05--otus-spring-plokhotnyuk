package ru.diasoft.nixson.libConvert.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "genre")
public class Genre {
    @Id
    private String id;
    private String name;
    private String bookId;
}
