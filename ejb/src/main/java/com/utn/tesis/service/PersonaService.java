package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PersonaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;

/**
 * User: Enzo
 * Date: 29/08/15
 * Time: 18:12
 */
@Stateless
public class PersonaService extends BaseService<Persona> {

    @Inject
    private PersonaDao dao;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private Validator validator;

    @Inject
    private PersonaMapper personaMapper;

    @Inject
    private ArchivoService archivoService;

    @Override
    DaoBase<Persona> getDao() {
        return this.dao;
    }

    @Override
    Validator getValidator() {
        return this.validator;
    }

    @Override
    public Persona create(Persona entity) throws SAPOException {
        usuarioService.update(entity.getUsuario());
        entity.setFechaCarga(Calendar.getInstance());
        return super.create(entity);
    }

    public void update(PersonaDTO persona, ArchivoDTO imagen) throws SAPOException {
        Persona personaEntity = this.findById(persona.getId());
        personaMapper.updateFromDTO(persona, personaEntity);

        personaEntity.getUsuario().setImagen(archivoService.save(imagen));
        this.save(personaEntity);
    }

}
