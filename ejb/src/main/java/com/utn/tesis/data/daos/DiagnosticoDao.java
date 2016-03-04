package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Diagnostico;
import com.utn.tesis.model.MovimientoDiagnostico;
import com.utn.tesis.model.PracticaOdontologica;
import com.utn.tesis.model.QDiagnostico;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class DiagnosticoDao extends DaoBase<Diagnostico> {

    QDiagnostico diagnostico = QDiagnostico.diagnostico;

    public List<Diagnostico> findByFilters(Calendar fechaCreacion, MovimientoDiagnostico movimiento,
                                           PracticaOdontologica practicaOdontologica, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(diagnostico);
        if (fechaCreacion != null)
            query.where(diagnostico.fechaCreacion.eq(fechaCreacion));
        if (movimiento != null)
            query.where(diagnostico.movimientoDiagnostico.any().eq(movimiento));
        if (practicaOdontologica != null)
            query.where(diagnostico.practicaOdontologica.id.eq(practicaOdontologica.getId()));

        query = paginar(query, page, pageSize);
        return query.list(diagnostico);
    }

}
