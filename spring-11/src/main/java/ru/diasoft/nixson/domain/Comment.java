package ru.diasoft.nixson.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import ru.diasoft.nixson.dto.Identifiable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Identifiable {
    @Id
    private String id;

    private String content;
}
