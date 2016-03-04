package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class AsignacionPacienteDao extends DaoBase<AsignacionPaciente> {

    QAsignacionPaciente asignacionPaciente = QAsignacionPaciente.asignacionPaciente;

    public List<AsignacionPaciente> findByFilters(Alumno alumno, Profesor profesor, Diagnostico diagnostico, Calendar fechaCreacion,
                                       Calendar fechaAsignacion, TrabajoPractico trabajoPractico, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(asignacionPaciente);
        if (alumno != null)
            query.where(asignacionPaciente.alumno.id.eq(alumno.getId()));
        if (profesor != null)
            query.where(asignacionPaciente.autorizadoPor.id.eq(profesor.getId()));
        if (diagnostico != null)
            query.where(asignacionPaciente.diagnostico.id.eq(diagnostico.getId()));
        if (trabajoPractico != null)
            query.where(asignacionPaciente.trabajoPractico.id.eq(trabajoPractico.getId()));
        if (fechaCreacion != null)
            query.where(asignacionPaciente.fechaCreacion.eq(fechaCreacion));
        if (fechaAsignacion != null)
            query.where(asignacionPaciente.fechaAsignacion.eq(fechaAsignacion));

        query = paginar(query, page, pageSize);
        return query.list(asignacionPaciente);
    }

}
