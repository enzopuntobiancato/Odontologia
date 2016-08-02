package com.utn.tesis.data.daos;


import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.data.PersonaDaoQueryResolver;
import com.utn.tesis.model.*;

import java.util.Arrays;
import java.util.List;

public class ResponsableRecepcionDao extends DaoBase<ResponsableRecepcion> implements PersonaDaoQueryResolver {

    QResponsableRecepcion responsableRecepcion, $ = QResponsableRecepcion.responsableRecepcion;

    public List<ResponsableRecepcion> findByFilters(String nombre, String apellido, Documento documento,
                                         Usuario usuario, Sexo sexo, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from($);
        if (nombre != null)
            query.where($.nombre.containsIgnoreCase(nombre));
        if (apellido != null)
            query.where($.apellido.containsIgnoreCase(apellido));
        if (documento != null)
            query.where($.documento.numero.equalsIgnoreCase(documento.getNumero())
                    .and($.documento.tipoDocumento.eq(documento.getTipoDocumento())));
        if (usuario != null)
            query.where($.usuario.id.eq(usuario.getId()));

        query = paginar(query, page, pageSize);
        return query.list($);
    }

    public List<ResponsableRecepcion> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        JPAQuery query = new JPAQuery(em).from($);
        if (nombApp != null) {
            List<String> filtros = Arrays.asList(nombApp.split(" "));
            for(String s: filtros) {
                query.where($.nombre.containsIgnoreCase(s)
                        .or($.apellido.containsIgnoreCase(s)));
            }
        }

        query = paginar(query, page, pageSize);
        return query.list($);
    }

    public List<ResponsableRecepcion> findByRol(Rol rol, Long page, Long pageSize) {
        JPAQuery query = new JPAQuery(em).from($);
        if (rol != null)
            query.where($.usuario.rol.id.eq(rol.getId()));

        query = paginar(query, page, pageSize);
        return query.list($);
    }

    @Override
    public boolean supports(RolEnum rol) {
        return  RolEnum.RESPONSABLE_RECEPCION_PACIENTES == rol;
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
