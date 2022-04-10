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
@Table(name="instructors")
@PrimaryKeyJoinColumn(name = "instructor_id", referencedColumnName = "user_id")
public class Instructor extends User {

    @Column(name="instructor_id", insertable = false, updatable = false)
    private int instructorId;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Lecture> lectures;

}







