package com.utn.tesis.data.daos;


import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CatedraDao extends DaoBase<Catedra> {

    QCatedra catedra = QCatedra.catedra;

    public List<Catedra> findByFilters(String denominacion, Long materiaId, boolean dadosBaja, Long pageNumber, Long pageSize) {

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

    public List<Catedra> findCatedrasByDenomicacion(String denominacion){
        JPAQuery query = new JPAQuery(em).from(catedra);
        if(StringUtils.isNotBlank(denominacion)){
            query.where(catedra.denominacion.startsWithIgnoreCase(denominacion));
        }
        List<Catedra> catedras = query.list(catedra);
        return catedras;
    }

    public List<Catedra> findCatedrasByProfesor(Profesor p){
        QProfesor profesor = QProfesor.profesor;

        JPAQuery query = new JPAQuery(em);
        query.from(profesor);
        query.innerJoin(profesor.catedras, catedra);
        if (profesor != null) {
            query.where(profesor.eq(p));
        }

        List<Catedra> tuplas = query.list(catedra);
        return tuplas;
    }

    public List<Catedra> findCatedrasByProfesor(Long id){
        QProfesor profesor = QProfesor.profesor;

        JPAQuery query = new JPAQuery(em);
        query.from(profesor);
        query.innerJoin(profesor.catedras, catedra);
        if (profesor != null) {
            query.where(profesor.id.eq(id));
        }

        List<Catedra> tuplas = query.list(catedra);
        return tuplas;
    }

    public List<Catedra> findCatedrasByPractica(Long practicaId) {
        QTrabajoPractico trabajoPractico = QTrabajoPractico.trabajoPractico;

        JPAQuery query = new JPAQuery(em);
        query.from(catedra).innerJoin(catedra.trabajosPracticos, trabajoPractico);
        query.where(trabajoPractico.practicaOdontologica.id.eq(practicaId));
        return query.listDistinct(catedra);
    }
}
