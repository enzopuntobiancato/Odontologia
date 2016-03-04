package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.QTrabajo;
import com.utn.tesis.model.Trabajo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class TrabajoDao extends DaoBase<Trabajo> {

    QTrabajo trabajo = QTrabajo.trabajo;

    public List<Trabajo> findByFilters(String nombre, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(trabajo);
        if (nombre != null)
            query.where(trabajo.nombre.containsIgnoreCase(nombre));

        query = paginar(query, page, pageSize);
        return query.list(trabajo);
    }
}
