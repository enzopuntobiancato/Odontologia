package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.UsuarioDao;
import com.utn.tesis.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 03/06/15
 * Time: 22:03
 */
@Stateless
public class UsuarioService extends BaseService<Usuario>{

    @Inject
    UsuarioDao dao;

    @Inject Validator validator;

    @Override
    DaoBase<Usuario> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public Usuario findByUserAndPassword(String userName, String password) {
        HashMap<String, Object> filter = new HashMap<String, Object>(2);
        filter.put("nombreUsuario", userName);
        filter.put("contraseña", password);
        List<Usuario> results = dao.findBy(filter);
        return results.size() == 1 ? results.get(0) : null;
    }

    public Usuario findByUsernameAndAuthToken(String authId, String authToken) {
        return this.findById(new Long(1));
    }

}


