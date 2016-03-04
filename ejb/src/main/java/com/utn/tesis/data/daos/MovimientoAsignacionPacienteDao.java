package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.EstadoAsignacionPaciente;
import com.utn.tesis.model.MovimientoAsignacionPaciente;
import com.utn.tesis.model.QMovimientoAsignacionPaciente;
import com.utn.tesis.model.Usuario;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */
public class MovimientoAsignacionPacienteDao extends DaoBase<MovimientoAsignacionPaciente> {

    QMovimientoAsignacionPaciente movimientoAsignacionPaciente = QMovimientoAsignacionPaciente.movimientoAsignacionPaciente;

    public List<MovimientoAsignacionPaciente> findByFilters(EstadoAsignacionPaciente estado, Calendar fechaMovimiento,
                                                            Usuario generadoPor, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(movimientoAsignacionPaciente);
        if (estado != null)
            query.where(movimientoAsignacionPaciente.estado.eq(estado));
        if (fechaMovimiento != null)
            query.where(movimientoAsignacionPaciente.fechaMovimiento.eq(fechaMovimiento));
        if (generadoPor != null)
            query.where(movimientoAsignacionPaciente.generadoPor.id.eq(generadoPor.getId()));

        query = paginar(query, page, pageSize);
        return query.list(movimientoAsignacionPaciente);
    }
}
