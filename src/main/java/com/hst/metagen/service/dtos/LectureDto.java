package com.hst.metagen.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto {
    private Long lectureId;
    private String lectureName;
    private LocalDate lectureStartDate;
    private String departmentName;
}
