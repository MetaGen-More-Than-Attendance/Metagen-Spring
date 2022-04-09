package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.User;
import com.hst.metagen.service.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto save(UserDto userDto);
    void updateResetPasswordToken(String token, String email);
    User getByResetPasswordToken(String token);
    User getUserByUserMail(String username);
    void updatePassword(User user, String newPassword);
}