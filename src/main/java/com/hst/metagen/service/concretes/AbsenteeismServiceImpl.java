package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Absenteeism;
import com.hst.metagen.entity.Lecture;
import com.hst.metagen.entity.Semester;
import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.AbsenteeismRepository;
import com.hst.metagen.service.abstracts.AbsenteeismService;
import com.hst.metagen.service.abstracts.LectureService;
import com.hst.metagen.service.abstracts.SemesterService;
import com.hst.metagen.service.dtos.AbsenteeismDto;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import com.hst.metagen.service.requests.absenteeism.UpdateAbsenteeismRequest;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AbsenteeismServiceImpl implements AbsenteeismService {

    private final AbsenteeismRepository absenteeismRepository;
    private final LectureService lectureService;
    private final SemesterService semesterService;
    private final ModelMapperService modelMapperService;
    @Override
    public void save(CreateAbsenteeismRequest createAbsenteeismRequest) {
        Lecture lecture = lectureService.getLectureEntity(createAbsenteeismRequest.getLectureId());
        Semester semester = semesterService.getLastSemester();
        LocalDate startDate = createAbsenteeismRequest.getAbsenteeismDate();
        while (startDate.isBefore(semester.getEndDate())) {
            List<Student> students = lecture.getLectureStudents();
            for (Student student : students) {
                Absenteeism absenteeism = Absenteeism.builder()
                        .absenteeism(false)
                        .absenteeismDate(startDate)
                        .lecture(lecture)
                        .student(student).build();
                absenteeismRepository.save(absenteeism);
            }
            startDate = startDate.plusDays(7);
        }
    }

    @Override
    public AbsenteeismDto update(UpdateAbsenteeismRequest updateAbsenteeismRequest) {
        Absenteeism absenteeism = absenteeismRepository.getByAbsenteeismDateAndLecture_LectureIdAndStudent_StudentId(
                updateAbsenteeismRequest.getAbsenteeismDate(),
                updateAbsenteeismRequest.getLectureId(),
                updateAbsenteeismRequest.getStudentId()
        );

        absenteeism.setAbsenteeism(updateAbsenteeismRequest.isAbsenteeism());

        return modelMapperService.entityToDto(absenteeismRepository.save(absenteeism), AbsenteeismDto.class);
    }

    @Override
    public List<AbsenteeismDto> getStudentAndLectureAbsenteeisms(Long studentId, Long lectureId) {
        return modelMapperService.entityToDtoList(
                absenteeismRepository.getAbsenteeismByLecture_LectureIdAndStudent_StudentId(lectureId,studentId),
                AbsenteeismDto.class);
    }

    @Override
    public List<AbsenteeismDto> getLectureAbsenteesimsOnDate(Long lectureId, LocalDate localDate) {
        return null;
    }

    @Override
    public void deleteAll() {
        absenteeismRepository.deleteAll();
    }
}
