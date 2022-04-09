package com.hst.metagen.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String surname;

    private String identityNumber;

    @OneToOne
    private Lecture givenLectures;
}







