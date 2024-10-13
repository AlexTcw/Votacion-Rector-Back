package com.example.sistemavotacionback.dao.institucion;

import com.example.sistemavotacionback.repository.InstitucionRepository;

public class InstitucionDaoImp implements InstitucionDao{
    private final InstitucionRepository institucionRepository;

    public InstitucionDaoImp(InstitucionRepository institucionRepository) {
        this.institucionRepository = institucionRepository;
    }
}
