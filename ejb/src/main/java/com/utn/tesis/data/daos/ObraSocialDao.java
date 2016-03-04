package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.ObraSocial;
import com.utn.tesis.model.QObraSocial;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class ObraSocialDao extends DaoBase<ObraSocial> {

    QObraSocial obraSocial = QObraSocial.obraSocial;

    public List<ObraSocial> findByFilters(String nombre, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(obraSocial);
        if (nombre != null)
            query.where(obraSocial.nombre.containsIgnoreCase(nombre));

        query = paginar(query, page, pageSize);
        return query.list(obraSocial);
    }

}
