package com.hst.metagen.repository;

import com.hst.metagen.entity.Absenteeism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenteeismRepository extends JpaRepository<Absenteeism,Long> {

    Absenteeism getByAbsenteeismDateAndLecture_LectureIdAndStudent_StudentId(LocalDate absenteeismDate, Long lectureId, Long studentId);

    List<Absenteeism> getAbsenteeismByLecture_LectureIdAndStudent_StudentIdAndAbsenteeismDateGreaterThanEqualAndAbsenteeismDateLessThanEqualOrderByAbsenteeismDate(Long lectureId,Long studentId, LocalDate startDate, LocalDate endDate);

    List<Absenteeism> getAbsenteeismByLecture_LectureIdAndAbsenteeismDate(Long lectureId, LocalDate absenteeismDate);

    List<Absenteeism> getAbsenteeismByLecture_LectureIdAndAbsenteeismDateGreaterThanEqualAndAbsenteeismDateLessThanEqualOrderByAbsenteeismDate(Long lectureId, LocalDate startDate, LocalDate endDate);

    List<Absenteeism> getAbsenteeismByStudent_StudentId(Long studentId);

}
