package com.example.sistemavotacionback.dao.roles;

import com.example.sistemavotacionback.repository.RolesRepository;

public class RolesDaoImp implements RolesDao {

    private final RolesRepository rolesRepository;

    public RolesDaoImp(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }
}
