package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PersonaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.mapping.dto.PersonaDTO;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.Persona;
import com.utn.tesis.util.EncryptionUtils;

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
        entity.setFechaCarga(Calendar.getInstance());
        return super.create(entity);
    }

    public void update(PersonaDTO persona, ArchivoDTO imagen) throws SAPOException {
        Persona personaEntity = this.findById(persona.getId());
        personaMapper.updateFromDTO(persona, personaEntity);

        String encryptedPassword = EncryptionUtils.encryptMD5A(persona.getUsuario().getPassword());
        personaEntity.getUsuario().setContrasenia(encryptedPassword);
        personaEntity.getUsuario().setImagen(archivoService.save(imagen));
        personaEntity.getUsuario().setUltimaConexion(Calendar.getInstance());
        this.save(personaEntity);
    }

}
