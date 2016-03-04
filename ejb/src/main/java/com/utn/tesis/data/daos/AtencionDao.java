package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.AsignacionPaciente;
import com.utn.tesis.model.Atencion;
import com.utn.tesis.model.QAtencion;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class AtencionDao extends DaoBase<Atencion> {

    QAtencion atencion = QAtencion.atencion;

    public List<Atencion> findByFilters(Calendar fechaAtencion, AsignacionPaciente asignacionPaciente,
                                        Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(atencion);
        if (fechaAtencion != null)
            query.where(atencion.fechaAtencion.eq(fechaAtencion));
        if (asignacionPaciente != null)
            query.where(atencion.asignacionPaciente.id.eq(asignacionPaciente.getId()));

        query = paginar(query, page, pageSize);
        return query.list(atencion);
    }
}
