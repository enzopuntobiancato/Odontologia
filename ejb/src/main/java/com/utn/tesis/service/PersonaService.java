package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PersonaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Persona;

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
    PersonaDao dao;

    @Inject
    UsuarioService usuarioService;

    @Inject
    Validator validator;

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
}
