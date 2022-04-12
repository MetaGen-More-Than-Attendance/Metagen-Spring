package com.hst.metagen.service.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentDto {
    private String studentId;

    private String name;

    private String surname;

    private String identityNumber;

    private String password;

    private String photoPath;

}
