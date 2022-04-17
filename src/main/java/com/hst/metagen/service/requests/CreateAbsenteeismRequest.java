package com.hst.metagen.service.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAbsenteeismRequest {

    private boolean absenteeism;
    private LocalDate absenteeismDate;
    private Long lectureId;
    private Long studentId;

}
