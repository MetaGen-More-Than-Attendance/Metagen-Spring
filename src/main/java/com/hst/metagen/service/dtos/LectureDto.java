package com.hst.metagen.service.dtos;

import com.hst.metagen.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto {
    private Long lectureId;
    private Long instructorId;
    private String instructorName;
    private String lectureName;
    private LocalDate lectureStartDate;
    private String departmentName;
    private List<Long> studentIds;
}
