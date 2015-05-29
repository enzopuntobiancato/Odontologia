package com.utn.tesis.data.daos;


import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.model.QCatedra;

import java.util.List;

public class CatedraDao extends DaoBase<Catedra> {

    public List<Catedra> findByFilters(String denominacion, Long materiaId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        QCatedra catedra = QCatedra.catedra;

        JPAQuery query = new JPAQuery(em).from(catedra);

        if (denominacion != null) {
            query.where(catedra.denominacion.startsWith(denominacion));
        }
        if (materiaId != null) {
            query.where(catedra.materia.id.eq(materiaId));
        }
        if (!dadosBaja){
            query.where(catedra.fechaBaja.isNull());
        }
        query = paginar(query, pageNumber, pageSize);
        return query.list(catedra);
    }

}
