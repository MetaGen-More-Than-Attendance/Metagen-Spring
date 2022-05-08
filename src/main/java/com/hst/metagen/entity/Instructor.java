package com.hst.metagen.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="instructors")
@PrimaryKeyJoinColumn(name = "instructor_id", referencedColumnName = "user_id")
public class Instructor extends User {

    @Column(name="instructor_id", insertable = false, updatable = false)
    private Long instructorId;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lecture> lectures;

}







