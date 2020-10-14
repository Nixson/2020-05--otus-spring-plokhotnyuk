package ru.nixson.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private String group;
    private int evaluation;
    private boolean success;
    private boolean bad;
}
