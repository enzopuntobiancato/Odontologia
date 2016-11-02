package com.utn.tesis.data.daos;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.data.PersonaDaoQueryResolver;
import com.utn.tesis.model.Administrador;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.QAdministrador;
import com.utn.tesis.model.RolEnum;

import java.util.List;

public class AdministradorDao extends DaoBase<Administrador> implements PersonaDaoQueryResolver {

    private static final QAdministrador $ = QAdministrador.administrador;

    @Override
    public <T extends Persona> boolean supports(Class<T> personaClass) {
        return personaClass.equals(Administrador.class);
    }

    @Override
    public List<Administrador> validateByDocument(Persona entity) {
        BooleanBuilder expression = new BooleanBuilder($.documento.eq(entity.getDocumento()));
        if (entity.getId() !=  null) {
            expression.and($.id.ne(entity.getId()));
        }
        return new JPAQuery(em).from($).where(expression).list($);
    }
}
