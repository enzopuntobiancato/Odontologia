package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.GrupoPracticaOdontologica;
import com.utn.tesis.model.QGrupoPracticaOdontologica;
import com.utn.tesis.model.QPracticaOdontologica;

import java.util.List;

/**
 * User: enzo
 * Date: 10/05/15
 * Time: 18:18
 */
public class GrupoPracticaOdontologicaDao extends DaoBase<GrupoPracticaOdontologica> {
private final QGrupoPracticaOdontologica grupo = QGrupoPracticaOdontologica.grupoPracticaOdontologica;

    public List<GrupoPracticaOdontologica> findAllOrderByNombre() {
        JPAQuery query = new JPAQuery(em).from(grupo)
                .orderBy(grupo.nombre.asc());
        return query.list(grupo);
    }
}
