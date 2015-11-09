package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.model.QMateria;

import java.util.List;

public class MateriaDao extends DaoBase<Materia> {

    public List<Materia> findByFilters(String nombre, Nivel nivel, boolean dadosBaja,
                                       Long page, Long pageSize) {
        QMateria materia = QMateria.materia;

        JPAQuery query = new JPAQuery(em).from(materia);
        if (nombre != null)
            query.where(materia.nombre.startsWith(nombre));
        if (nivel != null)
            query.where(materia.nivel.eq(nivel));
        if (!dadosBaja)
            query.where(materia.fechaBaja.isNull());
        query = paginar(query, page, pageSize);
        return query.list(materia);
    }


    public Materia findByNombre(String nombre) {
        QMateria materia = QMateria.materia;

        JPAQuery query = new JPAQuery(em).from(materia);
        query.where(materia.nombre.equalsIgnoreCase(nombre));

        List<Materia> result = query.list(materia);
        return result.isEmpty() ? null : result.get(0);
    }
}
