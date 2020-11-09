package ru.nixson.sprint.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandDto {
    private Long id;
    private String name;
    private Long countUser;
}
