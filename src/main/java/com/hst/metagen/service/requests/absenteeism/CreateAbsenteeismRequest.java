package com.hst.metagen.service.requests.absenteeism;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAbsenteeismRequest {

    private LocalDate absenteeismDate;
    private Long lectureId;

}

