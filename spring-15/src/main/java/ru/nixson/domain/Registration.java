package ru.nixson.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Registration {
    @Builder.Default
    private UUID regId = UUID.randomUUID();
    private boolean success;
    private Student student;
}
