package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.model.QMateria;

import javax.persistence.Query;
import java.util.List;

public class MateriaDao extends DaoBase<Materia> {

    public List<Materia> findByFilters(String nombre, Nivel nivel) {
        QMateria materia = QMateria.materia;

        JPAQuery query = new JPAQuery(em).from(materia);
        if (nombre != null)
           query.where(materia.nombre.startsWith(nombre));
        if (nivel != null)
           query.where(materia.nivel.eq(nivel));
        return query.list(materia);
    }

    public List<Materia> findAll() {
        String q = "SELECT e FROM Materia e";
        Query query = em.createQuery(q);
        List<Materia> result = query.getResultList();
        return result;
    }
}
