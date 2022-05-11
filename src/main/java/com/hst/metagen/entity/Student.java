package com.hst.metagen.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="students")
@PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "user_id")
public class Student extends User {

    @Column(name="student_id", insertable = false, updatable = false)
    private Long studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Absenteeism> absenteeisms;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "lectureStudents")
    private List<Lecture> studentLectures = new ArrayList<>();

}
