package com.hst.metagen.service.requests.lecture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLectureRequest {

    private Long lectureId;
    private String lectureName;
    private LocalDate lectureStartDate;
    private Long instructorId;
    private Long departmentId;

}
