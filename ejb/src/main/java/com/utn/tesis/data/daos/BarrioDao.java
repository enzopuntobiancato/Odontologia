package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Ciudad;
import com.utn.tesis.model.QBarrio;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
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
}
