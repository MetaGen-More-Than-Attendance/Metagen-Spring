package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Absenteeism;
import com.hst.metagen.entity.Lecture;
import com.hst.metagen.entity.Semester;
import com.hst.metagen.entity.Student;
import com.hst.metagen.repository.AbsenteeismRepository;
import com.hst.metagen.repository.LectureRepository;
import com.hst.metagen.repository.StudentRepository;
import com.hst.metagen.service.abstracts.AbsenteeismService;
import com.hst.metagen.service.abstracts.LectureService;
import com.hst.metagen.service.abstracts.MailSenderService;
import com.hst.metagen.service.abstracts.SemesterService;
import com.hst.metagen.service.dtos.AbsenteeismDto;
import com.hst.metagen.service.dtos.AbsenteeismResponse;
import com.hst.metagen.service.requests.absenteeism.CreateAbsenteeismRequest;
import com.hst.metagen.service.requests.absenteeism.UpdateAbsenteeismRequest;
import com.hst.metagen.util.exception.AbsenteeismNotFoundException;
import com.hst.metagen.util.mapping.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.hst.metagen.util.Message.DISCONTINUOUS;

@Service
@RequiredArgsConstructor
public class AbsenteeismServiceImpl implements AbsenteeismService {

    private final AbsenteeismRepository absenteeismRepository;
    private final SemesterService semesterService;
    private final ModelMapperService modelMapperService;
    private final LectureRepository lectureRepository;
    private final MailSenderService mailSenderService;
    private final StudentRepository studentRepository;
    @Override
    public void save(CreateAbsenteeismRequest createAbsenteeismRequest) throws MessagingException, UnsupportedEncodingException {
        Lecture lecture = lectureRepository.getById(createAbsenteeismRequest.getLectureId());
        Semester semester = semesterService.getLastSemester();
        LocalDate startDate = createAbsenteeismRequest.getAbsenteeismDate();
        while (startDate.isBefore(semester.getEndDate())) {
            List<Student> students = lecture.getLectureStudents();
            for (Student student : students) {
                boolean hasAlreadyAbsenteeism = absenteeismRepository.existsAbsenteeismByStudent_StudentIdAndLecture_LectureIdAndAbsenteeismDate(student.getStudentId(),lecture.getLectureId(),startDate);
                if (Boolean.TRUE.equals(hasAlreadyAbsenteeism)){
                    continue;
                }
                Absenteeism absenteeism = Absenteeism.builder()
                        .absenteeism(false)
                        .absenteeismDate(startDate)
                        .lecture(lecture)
                        .student(student).build();
                absenteeism = absenteeismRepository.save(absenteeism);
            }
            startDate = startDate.plusDays(7);
        }
    }

    @Override
    public AbsenteeismDto update(UpdateAbsenteeismRequest updateAbsenteeismRequest) throws MessagingException, UnsupportedEncodingException {
        Absenteeism absenteeism = absenteeismRepository.getByAbsenteeismDateAndLecture_LectureIdAndStudent_StudentId(
                updateAbsenteeismRequest.getAbsenteeismDate(),
                updateAbsenteeismRequest.getLectureId(),
                updateAbsenteeismRequest.getStudentId()
        );
        if (Objects.isNull(absenteeism)){
            throw new AbsenteeismNotFoundException("Absenteeism is not found");
        }
        Student student = studentRepository.getById(updateAbsenteeismRequest.getStudentId());
        Lecture lecture = lectureRepository.getById(updateAbsenteeismRequest.getLectureId());
        absenteeism.setAbsenteeism(updateAbsenteeismRequest.isAbsenteeism());

        String mail = student.getUserMail();
        String lectureName = lecture.getLectureName();
        //mailSenderService.sendInfoEmail(mail, DISCONTINUOUS, lectureName);
        return modelMapperService.entityToDto(absenteeismRepository.save(absenteeism), AbsenteeismDto.class);
    }

    @Override
    public Map<Object, Object> getStudentAndLectureAbsenteeisms(Long studentId, Long lectureId) {
        Semester semester = semesterService.getLastSemester();
        List<AbsenteeismDto> absenteeismDtoList = modelMapperService.entityToDtoList(
                absenteeismRepository.getAbsenteeismByLecture_LectureIdAndStudent_StudentIdAndAbsenteeismDateGreaterThanEqualAndAbsenteeismDateLessThanEqualOrderByAbsenteeismDate(lectureId,studentId,semester.getStartDate(),semester.getEndDate()),
                AbsenteeismDto.class);
        absenteeismDtoList = absenteeismDtoList.stream().filter(absenteeismDto -> absenteeismDto.getAbsenteeismDate().isBefore(LocalDate.now().plusDays(1))).collect(Collectors.toList());

        return convertToMap(absenteeismDtoList);
    }

    @Override
    public AbsenteeismResponse getStudentAbsenteeisms(Long studentId, Long lectureId) {
        Semester semester = semesterService.getLastSemester();
        List<Absenteeism> absenteeisms = absenteeismRepository.getAbsenteeismByLecture_LectureIdAndStudent_StudentIdAndAbsenteeismDateGreaterThanEqualAndAbsenteeismDateLessThanEqualOrderByAbsenteeismDate(lectureId,studentId,semester.getStartDate(),semester.getEndDate());
        int count = absenteeisms.size();
        List<AbsenteeismDto> absenteeismDtoList = modelMapperService.entityToDtoList(
                absenteeisms,
                AbsenteeismDto.class);
        absenteeismDtoList = absenteeismDtoList.stream().filter(absenteeismDto -> absenteeismDto.getAbsenteeismDate().isBefore(LocalDate.now().plusDays(1))).collect(Collectors.toList());

        return convertToResponseForStudent(lectureId, absenteeismDtoList, count);
    }

    @Override
    public AbsenteeismResponse getLectureAbsenteesimsOnDate(Long lectureId, LocalDate localDate) {
        List<AbsenteeismDto> absenteeismDtoList = modelMapperService.entityToDtoList(
                absenteeismRepository.getAbsenteeismByLecture_LectureIdAndAbsenteeismDate(lectureId, localDate),
                AbsenteeismDto.class);

        List<Student> students = lectureRepository.getById(lectureId).getLectureStudents();

        Semester semester = semesterService.getLastSemester();

        int count = absenteeismRepository.getAbsenteeismByLecture_LectureIdAndStudent_StudentIdAndAbsenteeismDateGreaterThanEqualAndAbsenteeismDateLessThanEqualOrderByAbsenteeismDate(lectureId,students.get(0).getStudentId(),semester.getStartDate(),semester.getEndDate()).size();

        return convertToResponse(lectureId, absenteeismDtoList, count);
    }

    @Override
    public AbsenteeismResponse getLectureAbsenteesims(Long lectureId, Long semesterId) {
        Semester semester = semesterService.getBySemesterId(semesterId);
        List<Absenteeism> absenteeisms = absenteeismRepository.getAbsenteeismByLecture_LectureIdAndAbsenteeismDateGreaterThanEqualAndAbsenteeismDateLessThanEqualOrderByAbsenteeismDate(lectureId,semester.getStartDate(),semester.getEndDate());
        List<AbsenteeismDto> absenteeismDtoList = modelMapperService.entityToDtoList(
                absenteeisms,
                AbsenteeismDto.class);
        absenteeismDtoList = absenteeismDtoList.stream().filter(absenteeismDto -> absenteeismDto.getAbsenteeismDate().isBefore(LocalDate.now().plusDays(1))).collect(Collectors.toList());

        List<Student> students = lectureRepository.getById(lectureId).getLectureStudents();
        int count = absenteeisms.size()/ students.size();

        return convertToResponse(lectureId, absenteeismDtoList, count);
    }

    public String getAttendanceInfo(Long lectureId, int count, int disConCount) {
        Integer percentage = lectureRepository.getById(lectureId).getLectureAttendancePercentage();

        boolean continuous = ((disConCount / count) * 100) < percentage;
        if (continuous) {
            return "Continuous";
        }
        return "Discontinuous";
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

    private AbsenteeismResponse convertToResponse(Long lectureId, List<AbsenteeismDto> absenteeismDtoList, int count) {

        LinkedHashSet<LocalDate> dates = new LinkedHashSet<>();
        LinkedHashSet<String> students = new LinkedHashSet<>();

        LinkedList<Object> head = new LinkedList<>();
        LinkedList<LinkedList<Object>> body = new LinkedList<>();

        head.add("Name-Surname");

        for (AbsenteeismDto absenteeismDto : absenteeismDtoList) {
            dates.add(absenteeismDto.getAbsenteeismDate());
            students.add(getName(absenteeismDto.getUserName(), absenteeismDto.getUserSurname()));
        }

        head.addAll(dates);
        head.add("Attendance");

        for (String student : students) {
            LinkedList<Object> row = new LinkedList<>();
            row.add(student);

            int disConCount = 0;
            for (AbsenteeismDto absenteeismDto : absenteeismDtoList) {
                if (student.equalsIgnoreCase(getName(absenteeismDto.getUserName(), absenteeismDto.getUserSurname()))) {
                    row.add(absenteeismDto.isAbsenteeism());
                    if (!absenteeismDto.isAbsenteeism()) {
                        disConCount++;
                    }
                }
            }

            row.add(getAttendanceInfo(lectureId, count, disConCount));

            body.add(row);
        }


        return new AbsenteeismResponse(head, body);
    }

    private AbsenteeismResponse convertToResponseForStudent(Long lectureId, List<AbsenteeismDto> absenteeismDtoList, int count) {

        LinkedHashSet<LocalDate> dates = new LinkedHashSet<>();

        LinkedList<Object> head = new LinkedList<>();
        LinkedList<LinkedList<Object>> body = new LinkedList<>();
        int disConCount = 0;

        head.add("Date");
        head.add("Attendance");

        for (AbsenteeismDto absenteeismDto : absenteeismDtoList) {
            dates.add(absenteeismDto.getAbsenteeismDate());
        }

        for (LocalDate date : dates) {
            LinkedList<Object> row = new LinkedList<>();
            row.add(date);

            for (AbsenteeismDto absenteeismDto : absenteeismDtoList) {
                if (date.isEqual(absenteeismDto.getAbsenteeismDate())) {
                    row.add(absenteeismDto.isAbsenteeism());
                    if (!absenteeismDto.isAbsenteeism()) {
                        disConCount++;
                    }
                }
            }

            body.add(row);
        }

        LinkedList<Object> row = new LinkedList<>();
        row.add("Attendance");
        row.add(getAttendanceInfo(lectureId, count, disConCount));
        body.add(row);


        return new AbsenteeismResponse(head, body);
    }

    private String getName(String name, String surname) {
        return name + " " + surname;
    }
}
