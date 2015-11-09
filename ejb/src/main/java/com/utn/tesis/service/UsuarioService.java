package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.UsuarioDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.util.EncryptionUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 03/06/15
 * Time: 22:03
 */
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

    @Override
    DaoBase<Usuario> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public Usuario findByUserAndPassword(String userName, String password) {
        try {
            return dao.findByUsernameAndPassword(userName, EncryptionUtils.encryptMD5A1(password));
        } catch (NoSuchAlgorithmException e) {
            return dao.findByUsernameAndPassword(userName, EncryptionUtils.encryptMD5A2(password));
        }
    }

    public Usuario findByUsernameAndAuthToken(String authId, String authToken) {
        return dao.findByUsernameAndAuthToken(authId, authToken);
    }

    @Override
    public Usuario create(Usuario entity) throws SAPOException {
        // Enviar e-mail con la contraseña antes de encriptar la misma

        //encriptamos la contraseña
        try {
            entity.setContrasenia(EncryptionUtils.encryptMD5A1(entity.getContrasenia()));
        } catch (NoSuchAlgorithmException e) {
            entity.setContrasenia(EncryptionUtils.encryptMD5A2(entity.getContrasenia()));
        }

        return super.create(entity);
    }

    public List<Usuario> findByFilters(String nombreUsuario, String email, Long rolId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        return dao.findByFilters(nombreUsuario, email, rolId, dadosBaja, pageNumber, pageSize);
    }
}


