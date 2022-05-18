package com.hst.metagen.service.requests.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentDto {
    private List<Long> studentIds;
}
