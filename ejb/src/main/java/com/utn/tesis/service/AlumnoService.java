package com.utn.tesis.service;

import com.utn.tesis.data.daos.AlumnoDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.mapping.dto.AlumnoDTO;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class AlumnoService extends BaseService<Alumno> {
    @Inject
    private AlumnoDao dao;
    @Inject
    private Validator validator;
    @Inject
    private PersonaMapper personaMapper;

    @Override
    DaoBase<Alumno> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<AlumnoDTO> findByFilters(String nombre, String apellido, Documento documento,
                                        Usuario usuario, Sexo sexo, Long page, Long pageSize) {
        return personaMapper.toAlumnoDTOList(dao.findByFilters(nombre, apellido, documento, usuario, sexo, page, pageSize));
    }

//    public List<Alumno> findByRol(Rol rol, Long page, Long pageSize) {
//        return dao.findByRol(rol, page, pageSize);
//    }

    public List<AlumnoDTO> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        return personaMapper.toAlumnoDTOList(dao.findByNombreApellido(nombApp, page, pageSize));
    }
}
