package com.hst.metagen.service.requests.absenteeism;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAbsenteeismRequest {

    private LocalDate absenteeismDate;
    private boolean absenteeism;
    private Long lectureId;
    private Long studentId;
}
