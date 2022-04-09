package com.hst.metagen.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Absenteeism {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

}
