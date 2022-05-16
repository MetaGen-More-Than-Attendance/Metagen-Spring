package com.hst.metagen.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="absenteeisms")
public class Absenteeism {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "absenteeism_id")
    private Long absenteeismId;

    @Column(name = "absenteeism")
    private boolean absenteeism;

    @Column(name = "absenteeism_date")
    private LocalDate absenteeismDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    private Student student;
}
