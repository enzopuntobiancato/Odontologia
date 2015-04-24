package com.utn.tesis.data.daos;


import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.QCatedra;

import javax.persistence.Query;
import java.util.List;

public class CatedraDao extends DaoBase<Catedra> {

    public List<Catedra> findByFilters(Materia mat, String denom) {
        QCatedra catedra = QCatedra.catedra;

        JPAQuery query = new JPAQuery(em).from(catedra);
        if (mat != null)
            query.where(catedra.materia.eq(mat));
        if (denom != null)
            query.where(catedra.denominacion.eq(denom));
        return query.list(catedra);
    }

    public List<Catedra> findAll() {
        String q = "SELECT e FROM Catedra e";
        Query query = em.createQuery(q);
        List<Catedra> result = query.getResultList();
        return result;
    }

}
