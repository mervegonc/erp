package com.project.erp.service.main.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.erp.entity.main.Role;
import com.project.erp.repository.main.RoleRepository;
import com.project.erp.service.main.abstracts.RoleService;

@Service
public class RoleManager implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
