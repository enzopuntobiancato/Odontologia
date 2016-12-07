package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Ciudad;
import com.utn.tesis.model.QBarrio;

import java.util.List;

public class BarrioDao extends DaoBase<Barrio> {

    QBarrio barrio = QBarrio.barrio;

    public List<Barrio> findByFilters(Ciudad ciudad, String nombre,
                                        Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(barrio);
        if (ciudad != null)
            query.where(barrio.ciudad.id.eq(ciudad.getId()));
        if (nombre != null)
            query.where(barrio.nombre.containsIgnoreCase(nombre));

        query = paginar(query, page, pageSize);
        return query.list(barrio);
    }

    public List<Barrio> findAllOrderByNombre() {
        JPAQuery query = new JPAQuery(em).from(barrio)
                .orderBy(barrio.nombre.asc());
        return query.list(barrio);
    }
}
