package com.project.erp.service.main.abstracts;

import com.project.erp.entity.main.Role;

public interface RoleService {
    Role createRole(Role role);
    Role getRoleByName(String roleName);
}
