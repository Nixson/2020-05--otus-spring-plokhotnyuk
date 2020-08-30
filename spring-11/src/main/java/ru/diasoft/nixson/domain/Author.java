package ru.diasoft.nixson.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import ru.diasoft.nixson.dto.Identifiable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Identifiable {
    @Id
    private String id;

    @Indexed
    private String name;

}
