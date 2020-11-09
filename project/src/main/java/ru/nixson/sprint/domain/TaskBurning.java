package ru.nixson.sprint.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "taskburning")
public class TaskBurning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long task;

    @Column(name = "usr")
    private String user;

    @Column
    private Integer dtime;

    @Column
    private Date bdate;
}
