package com.utn.tesis.data.daos;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.data.PersonaDaoQueryResolver;
import com.utn.tesis.model.AdministradorAcademico;
import com.utn.tesis.model.Persona;
import com.utn.tesis.model.QAdministradorAcademico;
import com.utn.tesis.model.RolEnum;

import java.util.List;

public class AdministradorAcademicoDao extends DaoBase<AdministradorAcademico> implements PersonaDaoQueryResolver {

    QAdministradorAcademico $ = QAdministradorAcademico.administradorAcademico;

    @Override
    public boolean supports(RolEnum rol) {
        return RolEnum.ADMINISTRADOR_ACADEMICO == rol;
    }

    @Override
    public List<? extends Persona> validateByDocument(Persona entity) {
        BooleanBuilder expression = new BooleanBuilder($.documento.eq(entity.getDocumento()));
        if (entity.getId() !=  null) {
            expression.and($.id.ne(entity.getId()));
        }
        return new JPAQuery(em).from($).where(expression).list($);
    }
}
