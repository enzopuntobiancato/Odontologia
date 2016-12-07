package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.QTrabajo;
import com.utn.tesis.model.Trabajo;

import java.util.List;

public class TrabajoDao extends DaoBase<Trabajo> {

    QTrabajo trabajo = QTrabajo.trabajo;

    public List<Trabajo> findByFilters(String nombre, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(trabajo);
        if (nombre != null)
            query.where(trabajo.nombre.containsIgnoreCase(nombre));

        query = paginar(query, page, pageSize);
        return query.list(trabajo);
    }

    public List<Trabajo> findAllOrderByNombre() {
        JPAQuery query = new JPAQuery(em).from(trabajo)
                .orderBy(trabajo.nombre.asc());
        return query.list(trabajo);
    }
}
