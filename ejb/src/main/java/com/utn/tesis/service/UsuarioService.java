package com.utn.tesis.service;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.UsuarioDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.mail.MailService;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.UsuarioMapper;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.util.Collections;
import com.utn.tesis.util.EncryptionUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.security.NoSuchAlgorithmException;
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
            usuario =  dao.findByUsernameAndPassword(userName, EncryptionUtils.encryptMD5A2(password));
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
            usuarioLogueadoDTO = usuarioMapper.toUsuarioLogueadoDTO(usuario);
        }
        return usuarioLogueadoDTO;
    }

    public List<Usuario> findByFilters(String nombreUsuario, String email, Long rolId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        return dao.findByFilters(nombreUsuario, email, rolId, dadosBaja, pageNumber, pageSize);
    }

    public String saveUsuario(Persona persona, Usuario usuario) throws SAPOException {
        String password = RandomStringUtils.randomAlphanumeric(5);
        usuario.setContrasenia(password);
        usuario.setContrasenia(EncryptionUtils.encryptMD5A(usuario.getContrasenia()));
        usuario = create(usuario);
        persona.setUsuario(usuario);
        personaService.create(persona);
        return password;
    }

    public Persona findPersonaByUsuario(Usuario usuario) {
        return dao.findPersonaByUsuario(usuario);
    }

    public UsuarioLogueadoDTO fetchUser(Long id) {
        Usuario user = findById(id);
        return usuarioMapper.toUsuarioLogueadoDTO(user);
    }
}


