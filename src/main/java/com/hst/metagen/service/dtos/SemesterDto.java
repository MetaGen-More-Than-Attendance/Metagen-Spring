package com.hst.metagen.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterDto {
    private Long semesterId;
    private String semesterName;
    private LocalDate startDate;
    private LocalDate endDate;
}
