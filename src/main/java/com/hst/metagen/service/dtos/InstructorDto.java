package com.hst.metagen.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDto {
    private Long instructorId;

    private String userName;

    private String userSurname;

    private String identityNumber;

    private String userPassword;

    private String userMail;

    private String photoPath;
}
