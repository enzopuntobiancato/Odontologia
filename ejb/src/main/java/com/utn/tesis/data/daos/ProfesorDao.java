package com.utn.tesis.data.daos;


import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/01/16
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class ProfesorDao extends DaoBase<Profesor> {

    QProfesor profesor = QProfesor.profesor;

    public List<Profesor> findByFilters(String nombre, String apellido, Integer legajo, Usuario usuario, Documento documento) {
        JPAQuery query = new JPAQuery(em).from(profesor);

        if(nombre != null) {
            query.where(profesor.nombre.containsIgnoreCase(nombre));
        }
        if(apellido != null) {
            query.where(profesor.apellido.containsIgnoreCase(apellido));
        }
        if(legajo != null) {
            query.where(profesor.legajo.eq(legajo));
        }
        if(usuario != null) {
            query.where(profesor.usuario.id.eq(usuario.getId()));
        }
        if(documento != null) {
            query.where(profesor.documento.numero.eq(documento.getNumero())
                    .and(profesor.documento.tipoDocumento.eq(documento.getTipoDocumento())));
        }

        return query.list(profesor);
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

}
