package com.hst.metagen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @NotNull(message = "Name field is mandatory")
    @Column(name="user_name")
    private String userName;

    @NotNull(message = "Surname field is mandatory")
    @Column(name="user_surname")
    private String userSurname;

    @Column(name = "identity_number")
    @NotNull(message = "Identity Number field is mandatory")
    private String identityNumber;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name="user_mail")
    @Email(message = "Email is not valid")
    private String userMail;

    @Column(name="user_password")
    private String userPassword;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> userRoles;
}
