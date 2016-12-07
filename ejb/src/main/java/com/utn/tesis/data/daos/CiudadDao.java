package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Ciudad;
import com.utn.tesis.model.Provincia;
import com.utn.tesis.model.QCiudad;

import java.util.List;

public class CiudadDao extends DaoBase<Ciudad> {

    QCiudad ciudad = QCiudad.ciudad;

    public List<Ciudad> findByFilters(Provincia provincia, String nombre,
                                      Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(ciudad);
        if (provincia != null)
            query.where(ciudad.provincia.id.eq(provincia.getId()));
        if (nombre != null)
            query.where(ciudad.nombre.containsIgnoreCase(nombre));

        query = paginar(query, page, pageSize);
        return query.list(ciudad);
    }

    public List<Ciudad> findAllOrderByNombre() {
        JPAQuery query = new JPAQuery(em).from(ciudad)
                .orderBy(ciudad.nombre.asc());
        return query.list(ciudad);
    }
}
