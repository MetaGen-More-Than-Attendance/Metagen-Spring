package com.hst.metagen.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenteeismDto {
    private Long absenteeismId;
    private Long student;
    private boolean absenteeism;
    private LocalDate absenteeismDate;
    private String lectureName;
    private String userName;
    private String userSurname;
    private String identityNumber;
}
