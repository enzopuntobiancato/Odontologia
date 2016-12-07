package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.Calendar;
import java.util.Date;
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

    public List<Atencion> findByFilters(Long pacienteId, Long practicaId, Long catedraId, Long trabajoPracticoId, Date fechaDesde, Date fechaHasta, Long pageNumber, Long pageSize) {
        QPaciente paciente = QPaciente.paciente;
        QHistoriaClinica historiaClinica = QHistoriaClinica.historiaClinica;
        QAsignacionPaciente asignacionPaciente = QAsignacionPaciente.asignacionPaciente;
        QTrabajoPractico trabajoPractico = QTrabajoPractico.trabajoPractico;
        QCatedra catedra = QCatedra.catedra;

        JPAQuery query = new JPAQuery(em)
                .from(paciente)
                .innerJoin(paciente.historiaClinica, historiaClinica)
                .innerJoin(historiaClinica.atencion, atencion)
                .innerJoin(atencion.asignacionPaciente, asignacionPaciente)
                .rightJoin(asignacionPaciente.catedra, catedra)
                .innerJoin(asignacionPaciente.trabajoPractico, trabajoPractico);
        query.where(paciente.id.eq(pacienteId));

        if (practicaId != null) {
            query.where(trabajoPractico.practicaOdontologica.id.eq(practicaId));
        }
        if (catedraId != null) {
            query.where(catedra.id.eq(catedraId));
        }
        if (trabajoPracticoId != null) {
            query.where(trabajoPractico.id.eq(trabajoPracticoId));
        }
        if (fechaDesde != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaDesde);
            query.where(atencion.fechaAtencion.goe(calendar));
        }
        if (fechaHasta != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaHasta);
            query.where(atencion.fechaAtencion.loe(calendar));
        }
        query = paginar(query, pageNumber, pageSize);
        return query.list(atencion);
    }

    public List<Archivo> findDocumentacionesByAtencion(Long atencionId) {
        QArchivo archivo = QArchivo.archivo;
        JPAQuery query = new JPAQuery(em).from(atencion).innerJoin(atencion.documentaciones, archivo);
        query.where(atencion.id.eq(atencionId));
        return query.list(archivo);
    }

    public Atencion findAtencionByAsignacion(Long asignacionPacienteId) {

        JPAQuery query = new JPAQuery(em).from(atencion);
        if (asignacionPacienteId != null)
            query.where(atencion.asignacionPaciente.id.eq(asignacionPacienteId));
        return query.uniqueResult(atencion);
    }
}
