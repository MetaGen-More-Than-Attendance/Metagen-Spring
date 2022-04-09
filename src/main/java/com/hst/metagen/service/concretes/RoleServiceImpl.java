package com.hst.metagen.service.concretes;

import com.hst.metagen.entity.Role;
import com.hst.metagen.repository.RoleRepository;
import com.hst.metagen.service.abstracts.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getByRoleName(String roleName) {
        return roleRepository.getByRoleName(roleName);
    }

    @Override
    public void addRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        roleRepository.save(role);
    }
}
