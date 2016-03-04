package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Provincia;
import com.utn.tesis.model.QProvincia;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 19/02/16
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class ProvinciaDao extends DaoBase<Provincia> {

    QProvincia provincia = QProvincia.provincia;

    public List<Provincia> findByFilters(String nombre, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(provincia);
        if (nombre != null)
            query.where(provincia.nombre.containsIgnoreCase(nombre));

        query = paginar(query, page, pageSize);
        return query.list(provincia);
    }
}
