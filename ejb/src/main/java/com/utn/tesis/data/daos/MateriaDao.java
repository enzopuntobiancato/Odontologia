package com.utn.tesis.data.daos;

import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;

import javax.persistence.Query;
import java.util.List;

public class MateriaDao extends DaoBase<Materia> {

    public List<Materia> findByFilters(String nombre, Nivel nivel) {
        StringBuilder q = new StringBuilder("SELECT e FROM Materia e");

        q.append(" WHERE (e.nombre LIKE :nombre OR :nombre IS NULL)");
        q.append(" AND (e.nivel = :nivel OR :nivel IS NULL)");

        Query query = em.createQuery(q.toString());
        query.setParameter("nombre", nombre == null ? null : nombre + "%");
        query.setParameter("nivel", nivel);
        List<Materia> result = query.getResultList();

        return result;
    }

    public List<Materia> findAll() {
        String q = "SELECT e FROM Materia e";
        Query query = em.createQuery(q);
        List<Materia> result = query.getResultList();
        return result;
    }
}
