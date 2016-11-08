package com.utn.tesis.service;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PersonaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.mapping.dto.PersonaDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.RolEnum;
import com.utn.tesis.model.RolUsuario;
import com.utn.tesis.util.EncryptionUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.List;

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
    @Inject
    private EnumMapper enumMapper;

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
        if (persona.getUsuario().isFromFirstLogin()) {
            personaEntity.getUsuario().setUltimaConexion(Calendar.getInstance());
        }
        personaEntity.getUsuario().setContrasenia(encryptedPassword);
        personaEntity.getUsuario().setImagen(archivoService.save(imagen));
        for (RolUsuario rolUsuario : personaEntity.getUsuario().getRoles()) {
            if (!personaEntity.getClass().equals(rolUsuario.getPersona().getClass())) {
                personaEntity.populateTo(rolUsuario.getPersona());
            }
        }
    }

    private PersonaDTO mapPersonByRol(PersonaDTO personaDTO, String rol) {
        return RolService.rolToPerson.get(rol).cast(personaDTO);
    }

    public PersonaDTO findByUserByUsernameAndAuthtoken(String username, String authToken, String rolStr) {
        RolEnum rol = RolEnum.valueOf(rolStr);
        Persona persona = dao.findByUserByUsernameAndAuthtoken(username, authToken, rol);
        return personaMapper.toDTO(persona);
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
        entity.validar();
        List<? extends Persona> sameDocumentPersons = dao.validateByDocument(entity);
        if (!sameDocumentPersons.isEmpty()) {
            throw new SAPOValidationException(ImmutableMap.of("documento", "Número y tipo de documento ya registrado."));
        }
        super.bussinessValidation(entity);
    }
}
