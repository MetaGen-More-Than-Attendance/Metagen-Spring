package com.hst.metagen.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenteeismDate {
    LocalDate date;
    LinkedList<AbsenteeismStudent> absenteeismStudents;
}
