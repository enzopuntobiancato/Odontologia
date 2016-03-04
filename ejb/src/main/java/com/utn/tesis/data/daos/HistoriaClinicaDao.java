package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */
public class HistoriaClinicaDao extends DaoBase<HistoriaClinica> {

    QHistoriaClinica historiaClinica = QHistoriaClinica.historiaClinica;

    public List<HistoriaClinica> findByFilters(Integer numero, Calendar fechaApertura, Usuario realizoHC,
                                               Atencion atencion, Diagnostico diagnostico, DetalleHistoriaClinica detalleHC,
                                               Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(historiaClinica);
        if (numero != null)
            query.where(historiaClinica.numero.eq(numero));
        if (fechaApertura != null)
            query.where(historiaClinica.fechaApertura.eq(fechaApertura));
        if (realizoHC != null)
            query.where(historiaClinica.realizoHistoriaClinica.id.eq(realizoHC.getId()));
        if (atencion != null)
            query.where(historiaClinica.atencion.any().id.eq(atencion.getId()));
        if (diagnostico != null)
            query.where(historiaClinica.diagnostico.any().id.eq(diagnostico.getId()));
        if (detalleHC != null)
            query.where(historiaClinica.detallesHC.any().eq(detalleHC));

        query = paginar(query, page, pageSize);
        return query.list(historiaClinica);
    }

}
