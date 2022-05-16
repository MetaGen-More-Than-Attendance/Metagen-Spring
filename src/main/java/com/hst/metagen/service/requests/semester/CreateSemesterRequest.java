package com.hst.metagen.service.requests.semester;

import com.hst.metagen.entity.SemesterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSemesterRequest {

    String semesterName;
    LocalDate startDate;
    LocalDate endDate;

}
