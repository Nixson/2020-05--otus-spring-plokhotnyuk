package ru.nixson.sprint.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BurnDownDto {
    private LocalDate bdate;
    private Long dtime;
}
