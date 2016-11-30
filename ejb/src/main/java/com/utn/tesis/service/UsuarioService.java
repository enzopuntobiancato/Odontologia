package com.utn.tesis.service;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.UsuarioDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.mail.MailService;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.*;
import com.utn.tesis.model.*;
import com.utn.tesis.util.Collections;
import com.utn.tesis.util.EncryptionUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UsuarioService extends BaseService<Usuario> {
    @Inject
    private UsuarioDao dao;
    @Inject
    private Validator validator;
    @Inject
    private RolService rolService;
    @Inject
    private ArchivoService archivoService;
    @Inject
    private PersonaService personaService;
    @Inject
    private UsuarioMapper usuarioMapper;
    @Inject
    private MailService mailService;
    @Inject
    private RolUsuarioMapper rolUsuarioMapper;
    @Inject
    private EnumMapper enumMapper;
    @Inject
    private PrivilegioMapper privilegioMapper;
    @Inject
    private UsuarioConsultaMapper usuarioConsultaMapper;
    @Inject
    private DocumentoMapper documentoMapper;
    @Inject
    private RolUsuarioMapperWithPerson rolUsuarioMapperWithPerson;
    @Inject
    private PersonaMapper personaMapper;

    @Override
    protected void bussinessValidation(Usuario entity) throws SAPOValidationException {
        super.bussinessValidation(entity);
        List<Usuario> usuarios = dao.validateByUsername(entity.getNombreUsuario(), entity.getId());
        if (!usuarios.isEmpty()) {
            throw new SAPOValidationException(ImmutableMap.of("nombreUsuario", "Nombre de usuario no disponible."));
        }
        usuarios = dao.validateByEmail(entity.getEmail(), entity.getId());
        if (!usuarios.isEmpty()) {
            throw new SAPOValidationException(ImmutableMap.of("email", "E-mail ya registrado."));
        }
    }

    @Override
    DaoBase<Usuario> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public Usuario findByUserAndPassword(String userName, String password) {
        Usuario usuario;
        try {
            usuario = dao.findByUsernameAndPassword(userName, EncryptionUtils.encryptMD5A1(password));
        } catch (NoSuchAlgorithmException e) {
            usuario = dao.findByUsernameAndPassword(userName, EncryptionUtils.encryptMD5A2(password));
        }
        return Collections.reload(usuario, 3);
    }

    public Usuario findByUsernameAndAuthToken(String authId, String authToken) {
        return dao.findByUsernameAndAuthToken(authId, authToken);
    }

    public UsuarioLogueadoDTO findDTOByUsernameAndAuthToken(String authId, String authToken) {
        UsuarioLogueadoDTO usuarioLogueadoDTO = null;
        Usuario usuario = dao.findByUsernameAndAuthToken(authId, authToken);
        if (usuario != null) {
            usuarioLogueadoDTO = UsuarioLogueadoDTO.valueOf(usuario);
        }
        return usuarioLogueadoDTO;
    }

    public UsuarioDTO findUsuarioDTOByUsernameAndAuthToken(String authId, String authToken) {
        UsuarioDTO usuarioDTO = null;
        Usuario usuario = dao.findByUsernameAndAuthToken(authId, authToken);
        if (usuario != null) {
            usuarioDTO = usuarioMapper.fromUsuario(usuario);
        }
        return usuarioDTO;
    }

    public List<UsuarioConsultaDTO> findByFilters(String nombreUsuario, String email, Long rolId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        List<Usuario> results = dao.findByFilters(nombreUsuario, email, rolId, dadosBaja, pageNumber, pageSize);
        List<UsuarioConsultaDTO> dtos = new ArrayList<UsuarioConsultaDTO>();
        for (Usuario usuario : results) {
            UsuarioConsultaDTO usuarioConsultaDTO = usuarioConsultaMapper.personaToUsuarioConsultaDTO(this.findPersonaByUsuario(usuario).get(0));
            dtos.add(usuarioConsultaDTO);
        }
        return dtos;
    }

    public String saveUsuario(Persona persona, Usuario usuario) throws SAPOException {
        List<Persona> personas = new ArrayList<Persona>();
        for (RolUsuario rolUsuario : usuario.getRoles()) {
            rolUsuario.setRol(rolService.findById(rolUsuario.getRol().getId()));
            Persona rolPerson;
            try {
                rolPerson = RolService.rolToPersonEntity.get(rolUsuario.getRol().getNombre().getKey()).newInstance();
                rolPerson.setDocumento(new Documento());
            } catch (InstantiationException e) {
                throw new SAPOException(e);
            } catch (IllegalAccessException e) {
                throw new SAPOException(e);
            }
            persona.populateTo(rolPerson);
            personaService.create(rolPerson);
            rolUsuario.setPersona(rolPerson);
            personas.add(rolPerson);
        }
        String password = RandomStringUtils.randomAlphanumeric(5);
        usuario.setContrasenia(password);
        usuario.setContrasenia(EncryptionUtils.encryptMD5A(usuario.getContrasenia()));

        usuario = create(usuario);
        for (Persona personaPersisted : personas) {
            personaPersisted.setUsuario(usuario);
        }
        return password;
    }

    public List<Persona> findPersonaByUsuario(Usuario usuario) {
        return dao.findPersonaByUsuario(usuario);
    }

    public Persona findPersonaByUserAndRol(Long idUsuario, RolEnum rol) {
        return dao.findPersonaByUsuarioAndRol(idUsuario, rol);
    }

    public UsuarioLogueadoDTO fetchUser(Long id, EnumDTO rolSelected) {
        Usuario user = findById(id);
        UsuarioLogueadoDTO usuarioLogueado = UsuarioLogueadoDTO.valueOf(user);
        Rol rol = rolService.findByRolEnum(enumMapper.rolEnumFromDTO(rolSelected));
        usuarioLogueado.setRol(enumMapper.rolEnumToDTO(rol.getNombre()));
        return usuarioLogueado;
    }

    public List<RolUsuarioDTO> findRolesByUser(Long userId) {
        return rolUsuarioMapper.toDTOList(dao.findRolesByUser(userId));
    }

    public UsuarioViewEditDTO findUsuarioView(Long usuarioId) {
        Usuario usuario = findById(usuarioId);
        UsuarioViewEditDTO usuarioViewDTO = usuarioMapper.toUsuarioViewDTO(usuario);
        usuarioViewDTO.setDocumento(documentoMapper.toDTO(usuario.retrieveFirstPerson().getDocumento()));
        usuarioViewDTO.setSexo(enumMapper.sexoToDTO(usuario.retrieveFirstPerson().getSexo()));
        usuarioViewDTO.setRoles(rolUsuarioMapperWithPerson.toDTOList(usuario.getRoles()));
        return usuarioViewDTO;
    }

    public void updateUser(UsuarioViewEditDTO usuarioDTO) throws IllegalAccessException, InstantiationException, SAPOException {
        Usuario usuario = findById(usuarioDTO.getId());
        usuarioMapper.updateFromDTO(usuarioDTO, usuario);

        AdministradorDTO anyPerson = new AdministradorDTO();
        anyPerson.setApellido(usuarioDTO.getApellido());
        anyPerson.setNombre(usuarioDTO.getNombre());
        anyPerson.setSexo(usuarioDTO.getSexo());
        anyPerson.setDocumento(usuarioDTO.getDocumento());
        anyPerson.setFechaNacimiento(usuarioDTO.getFechaNacimiento());

        List<RolUsuario> rolesUsuario = new ArrayList<RolUsuario>();
        for (RolUsuarioDTO rolUsuarioDTO : usuarioDTO.getRoles()) {
            Rol rol = rolService.findByRolEnum(RolEnum.valueOf(rolUsuarioDTO.getRol().getNombre().getKey()));
            Persona persona = null;
            if (rolUsuarioDTO.getPersona().getId() != null) {
                persona = personaService.findById(rolUsuarioDTO.getPersona().getId());
                personaMapper.updateFromDTO(rolUsuarioDTO.getPersona(), persona);
            } else {
                persona = personaMapper.fromDTO(rolUsuarioDTO.getPersona());
            }
            anyPerson.populateTo(persona);
            if (persona.isNew()) {
                personaService.create(persona);
            }
            RolUsuario rolUsuario = new RolUsuario();
            rolUsuario.setRol(rol);
            rolUsuario.setPersona(persona);
            rolesUsuario.add(rolUsuario);
            persona.setUsuario(usuario);
        }
        usuario.setRoles(rolesUsuario);
    }

    public void recuperarPassword(String email) throws SAPOException {
        Usuario usuario = dao.findByEmail(email);
        if (usuario == null) {
            throw new SAPOException(new SAPOValidationException(ImmutableMap.of("email", "El correo electrónico no se encuentra registrado.")));
        }
        String newPassword = RandomStringUtils.randomAlphanumeric(5);
        mailService.sendRecuperarPasswordMail(usuario.getEmail(), usuario.retrieveFirstPerson().getNombre(), usuario.getNombreUsuario(), newPassword);
        usuario.setContrasenia(EncryptionUtils.encryptMD5A(newPassword));
    }

    public PersonaDTO findPersonaDTOByUserAndRol(Long idUsuario, RolEnum rol) {
        return personaMapper.toDTO(this.findPersonaByUserAndRol(idUsuario, rol));
    }

    public void saveUserImage(Long userId, ArchivoDTO archivoDTO) throws SAPOException {
        Usuario usuario = findById(userId);
        usuario.setImagen(archivoService.save(archivoDTO));
    }
}


