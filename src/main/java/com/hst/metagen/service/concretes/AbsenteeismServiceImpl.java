package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Absenteeism;
import com.hst.metagen.entity.Lecture;
import com.hst.metagen.entity.Semester;
import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.AbsenteeismRepository;
import com.hst.metagen.service.abstracts.AbsenteeismService;
import com.hst.metagen.service.abstracts.LectureService;
import com.hst.metagen.service.abstracts.SemesterService;
import com.hst.metagen.service.dtos.AbsenteeismDate;
import com.hst.metagen.service.dtos.AbsenteeismDto;
import com.hst.metagen.service.dtos.AbsenteeismResponse;
import com.hst.metagen.service.dtos.AbsenteeismStudent;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import com.hst.metagen.service.requests.absenteeism.UpdateAbsenteeismRequest;
import com.hst.metagen.util.exception.AbsenteeismNotFoundException;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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
        if (Objects.isNull(absenteeism)){
            throw new AbsenteeismNotFoundException("Absenteeism is not found");
        }
        absenteeism.setAbsenteeism(updateAbsenteeismRequest.isAbsenteeism());

        return modelMapperService.entityToDto(absenteeismRepository.save(absenteeism), AbsenteeismDto.class);
    }

    @Override
    public Map<Object, Object> getStudentAndLectureAbsenteeisms(Long studentId, Long lectureId, Long semesterId) {
        Semester semester = semesterService.getBySemesterId(semesterId);
        List<AbsenteeismDto> absenteeismDtoList = modelMapperService.entityToDtoList(
                absenteeismRepository.getAbsenteeismByLecture_LectureIdAndStudent_StudentIdAndAbsenteeismDateGreaterThanEqualAndAbsenteeismDateLessThanEqualOrderByAbsenteeismDate(lectureId,studentId,semester.getStartDate(),semester.getEndDate()),
                AbsenteeismDto.class);

        Map<Object, Object> map = convertToMap(absenteeismDtoList);

        return map;
    }

    @Override
    public AbsenteeismResponse getLectureAbsenteesimsOnDate(Long lectureId, LocalDate localDate) {
        List<AbsenteeismDto> absenteeismDtoList = modelMapperService.entityToDtoList(
                absenteeismRepository.getAbsenteeismByLecture_LectureIdAndAbsenteeismDate(lectureId, localDate),
                AbsenteeismDto.class);

        return convertToResponse(absenteeismDtoList);
    }

    @Override
    public AbsenteeismResponse getLectureAbsenteesims(Long lectureId, Long semesterId) {
        Semester semester = semesterService.getBySemesterId(semesterId);
        List<AbsenteeismDto> absenteeismDtoList = modelMapperService.entityToDtoList(
                absenteeismRepository.getAbsenteeismByLecture_LectureIdAndAbsenteeismDateGreaterThanEqualAndAbsenteeismDateLessThanEqualOrderByAbsenteeismDate(lectureId,semester.getStartDate(),semester.getEndDate()),
                AbsenteeismDto.class);

        return convertToResponse(absenteeismDtoList);
    }

    @Override
    public void deleteAll() {
        absenteeismRepository.deleteAll();
    }

    private Map<Object, Object> convertToMap(List<AbsenteeismDto> absenteeismDtoList) {
        Map<Object, Object> map = new LinkedHashMap<>();
        LinkedHashSet<LocalDate> dates = new LinkedHashSet<>();

        for (AbsenteeismDto absenteeismDto : absenteeismDtoList) {
            dates.add(absenteeismDto.getAbsenteeismDate());
        }

        dates.forEach(date -> map.put(date, new LinkedHashMap<>()));


        for (AbsenteeismDto absenteeismDto : absenteeismDtoList) {
            LinkedHashMap<String, Boolean> absenteeismMap = (LinkedHashMap<String, Boolean>) map.get(absenteeismDto.getAbsenteeismDate());
            String name = getName(absenteeismDto.getUserName(), absenteeismDto.getUserSurname());
            absenteeismMap.put(name, absenteeismDto.isAbsenteeism());
            map.put(absenteeismDto.getAbsenteeismDate(), absenteeismMap);
        }

        return map;
    }

    private AbsenteeismResponse convertToResponse(List<AbsenteeismDto> absenteeismDtoList) {

        AbsenteeismResponse absenteeismResponse = new AbsenteeismResponse();

        LinkedList<LocalDate> dates = new LinkedList<>();
        LinkedList<AbsenteeismDate> absenteeismDates = new LinkedList<>();
        LinkedHashSet<String> students = new LinkedHashSet<>();

        for (AbsenteeismDto absenteeismDto : absenteeismDtoList) {
            dates.add(absenteeismDto.getAbsenteeismDate());
            students.add(getName(absenteeismDto.getUserName(), absenteeismDto.getUserSurname()));
        }

        for (LocalDate localDate : dates) {
            LinkedList<AbsenteeismStudent> absenteeismStudents = new LinkedList<>();

            for (AbsenteeismDto absenteeismDto : absenteeismDtoList) {
                if (localDate.isEqual(absenteeismDto.getAbsenteeismDate())) {
                    absenteeismStudents.add(new AbsenteeismStudent(getName(absenteeismDto.getUserName(), absenteeismDto.getUserSurname()), absenteeismDto.isAbsenteeism()));
                }
            }

            absenteeismDates.add(new AbsenteeismDate(localDate,absenteeismStudents));
        }

        absenteeismResponse.setAbsenteisms(absenteeismDates);
        absenteeismResponse.setStudents(students);

        return absenteeismResponse;
    }

    private String getName(String name, String surname) {
        return name + " " + surname;
    }
}
