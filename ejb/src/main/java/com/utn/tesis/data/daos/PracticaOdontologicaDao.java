package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * User: Enzo
 * Date: 07/04/15
 * Time: 21:06
 */
public class PracticaOdontologicaDao extends DaoBase<PracticaOdontologica> {

    QPracticaOdontologica practica = QPracticaOdontologica.practicaOdontologica;

    public List<PracticaOdontologica> findByFilters(String denominacion, Long idGrupoPractica, boolean dadosBaja, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(practica);
        if (denominacion != null) {
            query.where(practica.denominacion.startsWith(denominacion));
        }

        if (idGrupoPractica != null) {
            QGrupoPracticaOdontologica grupoPractica = QGrupoPracticaOdontologica.grupoPracticaOdontologica;
//            query.where(practica.grupo.eq(new JPAQuery(em).from(grupoPractica).where(grupoPractica.id.eq(idGrupoPractica)).uniqueResult(grupoPractica)));
            query.where(practica.grupo.id.eq(idGrupoPractica));

        }
        if (!dadosBaja) {
            query.where(practica.fechaBaja.isNull());
        }
        query = paginar(query, page, pageSize);
        return query.list(practica);
    }

    public List<PracticaOdontologica> findByDenominacion(String denominacion) {
        JPAQuery query = new JPAQuery(em).from(practica);
        query.where(practica.denominacion.containsIgnoreCase(denominacion));
        return query.list(practica);
    }

    public List<PracticaOdontologica> findByDenominacionCatedraAndPractico(String denominacion, Long catedraId, Long trabajoPracticoId) {
        if (StringUtils.isBlank(denominacion)) {
            return Collections.emptyList();
        }
        QTrabajoPractico trabajoPractico = QTrabajoPractico.trabajoPractico;
        QCatedra catedra = QCatedra.catedra;
        JPAQuery query = new JPAQuery(em);
        query.from(catedra)
                .rightJoin(catedra.trabajosPracticos, trabajoPractico)
                .rightJoin(trabajoPractico.practicaOdontologica, practica);

        query.where(practica.denominacion.containsIgnoreCase(denominacion));
        if (catedraId != null) {
            query.where(catedra.id.eq(catedraId));
        }
        if (trabajoPracticoId != null) {
            query.where(trabajoPractico.id.eq(trabajoPracticoId));
        }

        return query.listDistinct(practica);
    }

    public boolean hasActiveCatedras(PracticaOdontologica entity) {
        QTrabajoPractico trabajoPractico = QTrabajoPractico.trabajoPractico;
        JPAQuery query = new JPAQuery(em).from(trabajoPractico)
                .innerJoin(trabajoPractico.practicaOdontologica, practica)
                .where(practica.id.eq(entity.getId()).and(trabajoPractico.fechaBaja.isNull()));

        List<TrabajoPractico> trabajoPracticos = query.list(trabajoPractico);
        return trabajoPracticos.isEmpty();
    }

    public boolean hasActiveDiagnosticos(PracticaOdontologica entity) {
        QDiagnostico diagnostico = QDiagnostico.diagnostico;
        QMovimientoDiagnostico movimientoDiagnostico = QMovimientoDiagnostico.movimientoDiagnostico;
        JPAQuery query = new JPAQuery(em).from(diagnostico)
                .innerJoin(diagnostico.practicaOdontologica, practica)
                .innerJoin(diagnostico.ultimoMovimiento, movimientoDiagnostico)
                .where(practica.id.eq(entity.getId()).and(movimientoDiagnostico.estado.notIn(EstadoDiagnostico.FINAL_STATES)));

        List<Diagnostico> diagnosticos = query.list(diagnostico);
        return diagnosticos.isEmpty();
    }

}
