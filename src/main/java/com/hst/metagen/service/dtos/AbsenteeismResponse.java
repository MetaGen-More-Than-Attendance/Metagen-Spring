package com.hst.metagen.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenteeismResponse {

    LinkedList<AbsenteeismDate> absenteisms;
    LinkedHashSet<String> students;
}
