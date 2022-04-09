package com.hst.metagen.service.abstracts;

import com.hst.metagen.entity.Role;

public interface RoleService {
    Role getByRoleName(String roleName);
    void addRole(String roleName);
}
