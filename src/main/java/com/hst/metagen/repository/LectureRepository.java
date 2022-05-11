package com.hst.metagen.repository;

import com.hst.metagen.entity.Lecture;
import com.hst.metagen.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Long> {
    List<Lecture> findLecturesByLectureStudents(Student student);
}
