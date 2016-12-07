package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Provincia;
import com.utn.tesis.model.QProvincia;

import java.util.List;

public class ProvinciaDao extends DaoBase<Provincia> {

    QProvincia provincia = QProvincia.provincia;

    public List<Provincia> findByFilters(String nombre, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(provincia);
        if (nombre != null)
            query.where(provincia.nombre.containsIgnoreCase(nombre));

        query = paginar(query, page, pageSize);
        return query.list(provincia);
    }

    public List<Provincia> findAllOrderByNombre() {
        JPAQuery query = new JPAQuery(em).from(provincia)
                .orderBy(provincia.nombre.asc());
        return query.list(provincia);
    }
}
