package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.PracticaOdontologica;
import com.utn.tesis.model.QGrupoPracticaOdontologica;
import com.utn.tesis.model.QPracticaOdontologica;

import java.util.List;

/**
 * User: Enzo
 * Date: 07/04/15
 * Time: 21:06
 */
public class PracticaOdontologicaDao extends DaoBase<PracticaOdontologica> {

    public List<PracticaOdontologica> findByFilters(String denominacion, Long idGrupoPractica, boolean dadosBaja, Long page, Long pageSize) {
        QPracticaOdontologica practica = QPracticaOdontologica.practicaOdontologica;

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

}
