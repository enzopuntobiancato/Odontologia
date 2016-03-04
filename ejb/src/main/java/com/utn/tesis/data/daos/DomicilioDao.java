package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Domicilio;
import com.utn.tesis.model.QDomicilio;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class DomicilioDao extends DaoBase<Domicilio> {

    QDomicilio domicilio = QDomicilio.domicilio;

    public List<Domicilio> findByFilters(Barrio barrio, String calle, String numero, Integer codigoPostal,
                                           Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(domicilio);
        if (barrio != null)
            query.where(domicilio.barrio.id.eq(barrio.getId()));
        if (calle != null)
            query.where(domicilio.calle.containsIgnoreCase(calle));
        if (numero != null)
            query.where(domicilio.numero.equalsIgnoreCase(numero));
        if (codigoPostal != null)
            query.where(domicilio.codigoPostal.eq(codigoPostal));

        query = paginar(query, page, pageSize);
        return query.list(domicilio);
    }

}
