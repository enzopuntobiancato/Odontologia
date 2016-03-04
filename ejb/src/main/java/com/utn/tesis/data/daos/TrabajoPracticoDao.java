package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 13/05/15
 * Time: 13:15
 */
public class TrabajoPracticoDao extends DaoBase<TrabajoPractico> {

    QTrabajoPractico trabajoPractico = QTrabajoPractico.trabajoPractico;

    public List<TrabajoPractico> findByFilters(String nombre, Long grupoPracticaId, Long practicaId, boolean dadosBaja, Long pageNumber, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(trabajoPractico);
        if (nombre != null) {
            query.where(trabajoPractico.nombre.startsWith(nombre));
        }
        if (practicaId != null) {
            query.where(trabajoPractico.practicaOdontologica.id.eq(practicaId));
        } else if (grupoPracticaId != null) {
            QPracticaOdontologica practica = QPracticaOdontologica.practicaOdontologica;
            query.where(trabajoPractico.practicaOdontologica.in(
                    new JPAQuery(em).from(practica).where(
                            practica.grupo.id.eq
                                    (grupoPracticaId)).list(practica)));
        }
        if (!dadosBaja) {
            query.where(trabajoPractico.fechaBaja.isNull());
        }
        query = paginar(query, pageNumber, pageSize);
        return query.list(trabajoPractico);
    }
}
