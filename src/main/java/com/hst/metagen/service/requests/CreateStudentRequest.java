package com.hst.metagen.service.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequest {

    @NotNull(message = "Name field is mandatory")
    private String userName;

    @NotNull(message = "Surname field is mandatory")
    private String userSurname;

    @NotNull(message = "Identity Number field is mandatory")
    private String identityNumber;

    @Email(message = "Email is not valid")
    @NotNull(message = "Mail field is mandatory")
    private String userMail;

    @NotNull(message = "Password field is mandatory")
    private String userPassword;

    @NotNull(message = "Department field is mandatory")
    private Long departmentId;

    @NotNull(message = "Image field is mandatory")
    private String imageBase64;

}
