package com.utn.tesis.service;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PersonaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.mapping.dto.PersonaDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.util.EncryptionUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
        if (persona.getUsuario().isFromFirstLogin()) {
            personaEntity.getUsuario().setUltimaConexion(Calendar.getInstance());
        }
        String encryptedPassword;
        if (persona.getUsuario().isChangePassword()) {
            if (!persona.getUsuario().isFromFirstLogin()) {
                validateUserPasswordWhenChangingIt(personaEntity, persona);
            }
            encryptedPassword = EncryptionUtils.encryptMD5A(persona.getUsuario().getPassword());
        } else {
            encryptedPassword = personaEntity.getUsuario().getContrasenia();
        }
        personaMapper.updateFromDTO(persona, personaEntity);
        personaEntity.getUsuario().setContrasenia(encryptedPassword);
        personaEntity.getUsuario().setImagen(archivoService.save(imagen));
        this.save(personaEntity);
    }

    private PersonaDTO mapPersonByRol(PersonaDTO personaDTO, String rol) {
        return UsuarioLogueadoDTO.rolToPerson.get(rol).cast(personaDTO);
    }

    public Persona findByUserByUsernameAndAuthtoken(String username, String authToken) {
        return dao.findByUserByUsernameAndAuthtoken(username, authToken);
    }

    private void validateUserPasswordWhenChangingIt(Persona persistedEntity, PersonaDTO newPerson) throws SAPOException {
        String encriptedNewPassword = EncryptionUtils.encryptMD5A(newPerson.getUsuario().getCurrentPassword());
        if (!persistedEntity.getUsuario().getContrasenia().equals(encriptedNewPassword)) {
            SAPOValidationException sve = new SAPOValidationException(ImmutableMap.of("contrasenia", "La contraseña actual es incorrecta."));
            throw new SAPOException(sve);
        }
    }

    @Override
    protected void bussinessValidation(Persona entity) throws SAPOValidationException {
        List<Usuario> userWithSameUsername = dao.findUserByUsername(entity.getUsuario().getNombreUsuario());
        if (!userWithSameUsername.isEmpty() && !userWithSameUsername.get(0).getId().equals(entity.getUsuario().getId())) {
            throw new SAPOValidationException(ImmutableMap.of("nombreUsuario","El nombre de usuario no está disponible."));
        }
        super.bussinessValidation(entity);
    }
}
