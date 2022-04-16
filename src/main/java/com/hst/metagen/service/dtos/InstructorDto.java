package com.hst.metagen.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDto {
    private Long instructorId;

    private String name;

    private String surname;

    private String identityNumber;

    private String password;

    private String photoPath;
}
