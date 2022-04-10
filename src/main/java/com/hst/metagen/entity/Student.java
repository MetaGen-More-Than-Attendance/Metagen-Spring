package com.hst.metagen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="students")
@PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "user_id")
public class Student extends User {

    @Column(name="student_id", insertable = false, updatable = false)
    private int studentId;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Absenteeism> absenteeisms;
}
