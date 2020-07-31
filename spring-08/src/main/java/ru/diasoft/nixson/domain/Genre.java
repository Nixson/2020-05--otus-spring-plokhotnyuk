package ru.diasoft.nixson.domain;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    private String id;

    @Indexed
    private String name;

}
