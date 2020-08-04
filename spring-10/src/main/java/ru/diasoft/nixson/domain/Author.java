package ru.diasoft.nixson.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    private String id;

    @Indexed
    private String name;

}
