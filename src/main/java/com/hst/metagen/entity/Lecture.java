package com.hst.metagen.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;

@Entity
@Data
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="instructor_id", nullable=false)
    private Instructor instructor;

    @OneToOne
    private Absenteeism absenteeism;
}
