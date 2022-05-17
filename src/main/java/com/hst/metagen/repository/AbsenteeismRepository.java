package com.hst.metagen.repository;

import com.hst.metagen.entity.Absenteeism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenteeismRepository extends JpaRepository<Absenteeism,Long> {

    Absenteeism getByAbsenteeismDateAndLecture_LectureIdAndStudent_StudentId(LocalDate absenteeismDate, Long lectureId, Long studentId);

    List<Absenteeism> getAbsenteeismByLecture_LectureIdAndStudent_StudentId(Long lectureId,Long studentId);
}
