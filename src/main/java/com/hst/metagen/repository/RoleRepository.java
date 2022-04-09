package com.hst.metagen.repository;

import com.hst.metagen.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role getByRoleName(String roleName);
}
