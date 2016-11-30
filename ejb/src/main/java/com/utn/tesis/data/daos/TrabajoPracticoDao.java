package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TrabajoPracticoDao extends DaoBase<TrabajoPractico> {

    QTrabajoPractico trabajoPractico = QTrabajoPractico.trabajoPractico;
    QCatedra catedra = QCatedra.catedra;

    public List<TrabajoPractico> findByFilters(String nombre, Long grupoPracticaId, Long practicaId, boolean dadosBaja, Long pageNumber, Long pageSize) {

        QPracticaOdontologica practicaOdontologica = QPracticaOdontologica.practicaOdontologica;
        JPAQuery query = new JPAQuery(em).from(trabajoPractico);
        query.innerJoin(trabajoPractico.practicaOdontologica, practicaOdontologica);
        if (StringUtils.isNotBlank(nombre)) {
            query.where(trabajoPractico.nombre.startsWith(nombre));
        }
        if (practicaId != null) {
            query.where(practicaOdontologica.id.eq(practicaId));
        } else if (grupoPracticaId != null) {
            query.where(practicaOdontologica.grupo.id.eq(grupoPracticaId));
        }
        if (!dadosBaja) {
            query.where(trabajoPractico.fechaBaja.isNull());
        }
        query = paginar(query, pageNumber, pageSize);
        return query.list(trabajoPractico);
    }

    public List<TrabajoPractico> findByCatedra(Long idMateria){
        List<TrabajoPractico> trabajoPracticos = new ArrayList<TrabajoPractico>();
        if(idMateria == null){
            return trabajoPracticos;
        }
        JPAQuery query = new JPAQuery(em).from(trabajoPractico);
        query.where(trabajoPractico.catedras.any().id.eq(idMateria));
        List<TrabajoPractico> result = query.list(trabajoPractico);
        return query.list(trabajoPractico);
    }

    public boolean hasActiveCatedras(TrabajoPractico entity) {
        JPAQuery query = new JPAQuery(em).from(catedra)
                .innerJoin(catedra.trabajosPracticos, trabajoPractico)
                .where(trabajoPractico.id.eq(entity.getId()).and(catedra.fechaBaja.isNull()));

        List<Catedra> catedras = query.list(catedra);
        return catedras.isEmpty();
    }

    public boolean hasActiveAsignaciones(TrabajoPractico entity) {
        QAsignacionPaciente asignacionPaciente = QAsignacionPaciente.asignacionPaciente;
        QMovimientoAsignacionPaciente movimientoAsignacionPaciente = QMovimientoAsignacionPaciente.movimientoAsignacionPaciente;

        JPAQuery query = new JPAQuery(em).from(asignacionPaciente)
                .innerJoin(asignacionPaciente.trabajoPractico, trabajoPractico)
                .innerJoin(asignacionPaciente.ultimoMovimiento, movimientoAsignacionPaciente)
                .where(trabajoPractico.id.eq(entity.getId()).and(movimientoAsignacionPaciente.estado.notIn(EstadoAsignacionPaciente.FINAL_STATES)));

        List<AsignacionPaciente> asignacionPacientes = query.list(asignacionPaciente);
        return asignacionPacientes.isEmpty();
    }
}
