package com.utn.tesis.service;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.RolDao;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.PrivilegioMapper;
import com.utn.tesis.mapping.mapper.RollMapper;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;

@Stateless
public class RolService extends BaseService<Rol> {

    public static final Map<String, Class<? extends PersonaDTO>> rolToPerson = ImmutableMap.<String, Class<? extends PersonaDTO>>builder()
            .put(RolEnum.ADMINISTRADOR.getKey(), AdministradorDTO.class)
            .put(RolEnum.ADMINISTRADOR_ACADEMICO.getKey(), AdministradorAcademicoDTO.class)
            .put(RolEnum.ALUMNO.getKey(), AlumnoDTO.class)
            .put(RolEnum.AUTORIDAD.getKey(), AutoridadDTO.class)
            .put(RolEnum.PROFESOR.getKey(), ProfesorDTO.class)
            .put(RolEnum.RESPONSABLE_RECEPCION_PACIENTES.getKey(), ResponsableRecepcionDTO.class)
            .build();

    public static final Map<String, Class<? extends Persona>> rolToPersonEntity = ImmutableMap.<String, Class<? extends Persona>>builder()
            .put(RolEnum.ADMINISTRADOR.getKey(), Administrador.class)
            .put(RolEnum.ADMINISTRADOR_ACADEMICO.getKey(), AdministradorAcademico.class)
            .put(RolEnum.ALUMNO.getKey(), Alumno.class)
            .put(RolEnum.AUTORIDAD.getKey(), Autoridad.class)
            .put(RolEnum.PROFESOR.getKey(), Profesor.class)
            .put(RolEnum.RESPONSABLE_RECEPCION_PACIENTES.getKey(), ResponsableRecepcion.class)
            .build();

    @Inject
    private RolDao dao;
    @Inject
    private Validator validator;
    @Inject
    private PrivilegioMapper privilegioMapper;
    @Inject
    private RollMapper rollMapper;
    @Inject
    private PrivilegioService privilegioService;
    @Inject
    private FuncionalidadService funcionalidadService;

    @Override
    DaoBase<Rol> getDao() {
        return this.dao;
    }

    @Override
    Validator getValidator() {
        return this.validator;
    }

    public Rol findByRolEnum(RolEnum rolEnum) {
        return dao.findByRolEnum(rolEnum);
    }

    public List<PrivilegioDTO> findPrivilegiosByRolKey(String rol) {
        return privilegioMapper.toDTOList(dao.findPrivilegiosByRol(rol));
    }

    public List<RolEditDTO> findAllRolEditOrderByNombre() {
        List<Rol> entities = dao.findAllOrderByNombre();
        return rollMapper.toRolEditDTOList(entities);
    }

    public List<RolDTO> findAllRolesOrderByNombre() {
        List<Rol> entities = dao.findAllOrderByNombre();
        return rollMapper.toDTOList(entities);
    }

    public void savePermisos(List<RolEditDTO> roles) {
        for (RolEditDTO rolEditDTO : roles) {
            Rol rol = this.findById(rolEditDTO.getId());
            rol.getPrivilegios().clear();
            for (PrivilegioDTO privilegioDTO : rolEditDTO.getPrivilegios()) {
                Privilegio privilegio;
                if (privilegioDTO.getId() != null) {
                    privilegio = privilegioService.findById(privilegioDTO.getId());
                } else {
                    Funcionalidad funcionalidad = funcionalidadService.findById(privilegioDTO.getFuncionalidad().getId());
                    privilegio = new Privilegio(funcionalidad, rol);
                }
                rol.getPrivilegios().add(privilegio);
            }
        }
    }
}
