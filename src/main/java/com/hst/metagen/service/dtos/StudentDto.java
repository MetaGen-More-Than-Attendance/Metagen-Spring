package com.hst.metagen.service.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long studentId;

    private String userName;

    private String userSurname;

    private String identityNumber;

    private String photoPath;

    private String photo;

    private String userMail;

    private Long departmentId;

    private String userPassword;

    private String resetPasswordToken;

}
