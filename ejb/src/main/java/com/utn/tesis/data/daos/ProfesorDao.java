package com.utn.tesis.data.daos;


import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.utn.tesis.data.PersonaDaoQueryResolver;
import com.utn.tesis.model.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfesorDao extends DaoBase<Profesor> implements PersonaDaoQueryResolver {

    QProfesor profesor, $ = QProfesor.profesor;

    public List<Profesor> findByFilters(String nombre, String apellido, Documento documento,
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

    public List<Profesor> findByNombreApellido(String nombApp, Long page, Long pageSize) {
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

    public List<Profesor> findByRol(Rol rol, Long page, Long pageSize) {
        JPAQuery query = new JPAQuery(em).from($);
        if (rol != null)
            query.where($.usuario.rol.id.eq(rol.getId()));

        query = paginar(query, page, pageSize);
        return query.list($);
    }



    public List<Profesor> findByCatedra(Catedra catedra){
        JPAQuery query = new JPAQuery(em).from(profesor);

        if(catedra != null) {
            query.where(profesor.catedras.any().id.eq(catedra.getId()));
        }
        else {
            return null;
        }

        return query.list(profesor);
    }

    public List<Profesor> findProfesoresByApellido(String apellidoNombre, String legajo){
        List<Profesor> profesores;

        JPAQuery query = new JPAQuery(em).from($);
        if(StringUtils.isNotBlank(apellidoNombre)){
            query.where($.apellido.containsIgnoreCase(apellidoNombre));
        } else {
            query.where($.legajo.like(legajo));
        }
        profesores = query.list($);
        return profesores;
    }

    @Override
    public boolean supports(RolEnum rol) {
        return RolEnum.PROFESOR == rol;
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
