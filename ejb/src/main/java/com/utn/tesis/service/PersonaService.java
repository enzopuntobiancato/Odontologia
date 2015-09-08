package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PersonaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Persona;
import com.utn.tesis.util.FechaUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 29/08/15
 * Time: 18:12
 * To change this template use File | Settings | File Templates.
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
        entity.setUsuario(usuarioService.findByUsernameAndAuthToken(entity.getUsuario().getNombreUsuario(), entity.getUsuario().getAuthToken()));
        entity.setFechaCarga(Calendar.getInstance());
        return super.create(entity);
    }
}
