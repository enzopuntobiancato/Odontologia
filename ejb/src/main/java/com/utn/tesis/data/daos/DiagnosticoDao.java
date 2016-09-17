package com.utn.tesis.data.daos;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.Calendar;
import java.util.Date;
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
            query.where(diagnostico.movimientos.any().eq(movimiento));
        if (practicaOdontologica != null)
            query.where(diagnostico.practicaOdontologica.id.eq(practicaOdontologica.getId()));

        query = paginar(query, page, pageSize);
        return query.list(diagnostico);
    }

    public List<Diagnostico> findByFilters(Long practicaId,
                                              EstadoDiagnostico estado,
                                              Date fechaCreacionDesde,
                                              Date fechaCreacionHasta,
                                              Long pacienteId,
                                              Long pageNumber,
                                              Long pageSize) {
        QMovimientoDiagnostico movimientoDiagnostico = QMovimientoDiagnostico.movimientoDiagnostico;
        QHistoriaClinica historiaClinica = QHistoriaClinica.historiaClinica;
        QPaciente paciente = QPaciente.paciente;

        JPAQuery query = new JPAQuery(em).from(paciente)
                .innerJoin(paciente.historiaClinica, historiaClinica)
                .innerJoin(historiaClinica.diagnostico, diagnostico)
                .innerJoin(diagnostico.ultimoMovimiento, movimientoDiagnostico);

        BooleanBuilder expression = new BooleanBuilder(paciente.id.eq(pacienteId));
        expression.and(practicaId != null ? diagnostico.practicaOdontologica.id.eq(practicaId) : null);
        expression.and(estado != null ? movimientoDiagnostico.estado.eq(estado) : null);
        if (fechaCreacionDesde != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaCreacionDesde);
            expression.and(diagnostico.fechaCreacion.goe(calendar));
        }
        if (fechaCreacionHasta != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaCreacionHasta);
            expression.and(diagnostico.fechaCreacion.loe(calendar));
        }
        query.where(expression);
        query = paginar(query, pageNumber, pageSize);
        return query.list(diagnostico);
    }

    public List<Diagnostico> findAllByPaciente(Long pacienteId) {
        QPaciente paciente = QPaciente.paciente;
        QHistoriaClinica historiaClinica = QHistoriaClinica.historiaClinica;
        JPAQuery query = new JPAQuery(em).from(paciente)
                .innerJoin(paciente.historiaClinica, historiaClinica)
                .innerJoin(historiaClinica.diagnostico, diagnostico);
        query.where(paciente.id.eq(pacienteId));
        return query.list(diagnostico);
    }

    public List<Diagnostico> findOpenByPaciente(Long pacienteId) {
        QPaciente paciente = QPaciente.paciente;
        QHistoriaClinica historiaClinica = QHistoriaClinica.historiaClinica;
        QMovimientoDiagnostico ultimoMovimiento = QMovimientoDiagnostico.movimientoDiagnostico;
        JPAQuery query = new JPAQuery(em).from(paciente)
                .innerJoin(paciente.historiaClinica, historiaClinica)
                .innerJoin(historiaClinica.diagnostico, diagnostico)
                .innerJoin(diagnostico.ultimoMovimiento, ultimoMovimiento);
        query.where(
                paciente.id.eq(pacienteId)
                .and(ultimoMovimiento.estado.notIn(EstadoDiagnostico.FINAL_STATES)));
        return query.list(diagnostico);
    }

}
