package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class MovimientoDiagnosticoDao extends DaoBase<MovimientoDiagnostico> {

    QMovimientoDiagnostico movimientoDiagnostico = QMovimientoDiagnostico.movimientoDiagnostico;

    public List<MovimientoDiagnostico> findByFilters(Atencion atencion, EstadoDiagnostico estado, Calendar fechaMovimiento,
                                                            Usuario generadoPor, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(movimientoDiagnostico);
        if (atencion != null)
            query.where(movimientoDiagnostico.atencion.id.eq(atencion.getId()));
        if (estado != null)
            query.where(movimientoDiagnostico.estado.eq(estado));
        if (fechaMovimiento != null)
            query.where(movimientoDiagnostico.fechaMovimiento.eq(fechaMovimiento));
        if (generadoPor != null)
            query.where(movimientoDiagnostico.generadoPor.id.eq(generadoPor.getId()));

        query = paginar(query, page, pageSize);
        return query.list(movimientoDiagnostico);
    }
}
