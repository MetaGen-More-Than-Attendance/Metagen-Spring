package com.hst.metagen.repository;

import com.hst.metagen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByUserMail(String userMail);
    User getByResetPasswordToken(String token);
    Boolean existsByUserMail(String userMail);
}
