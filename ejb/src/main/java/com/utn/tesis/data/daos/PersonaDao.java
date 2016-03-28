package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 29/08/15
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class PersonaDao extends DaoBase<Persona> {

    QPersona $ = QPersona.persona;

    //Por el momento defino todos los metodos con el comodin $ y se deberian copiar y pegar en
    //el dao de cada clase que extienda de persona tal cual se encuentra aca el metodo.

    //NOTA: no logre hacer la extension de Qpersona a las Qclass de las clases hijas de persona

    private List<Persona> findByFilters(String nombre, String apellido, Documento documento,
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

    private List<Persona> findByNombreApellido(String nombApp, Long page, Long pageSize) {
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

    private List<Persona> findByRol(Rol rol, Long page, Long pageSize) {
        JPAQuery query = new JPAQuery(em).from($);
        if (rol != null)
            query.where($.usuario.rol.id.eq(rol.getId()));

        query = paginar(query, page, pageSize);
        return query.list($);
    }
}
