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

    public List<TrabajoPractico> findByMateria(Long idMateria){
        List<TrabajoPractico> trabajoPracticos = new ArrayList<TrabajoPractico>();
        if(idMateria == null){
            return trabajoPracticos;
        }
        JPAQuery query = new JPAQuery(em).from(trabajoPractico);
        query.where(trabajoPractico.catedras.any().id.eq(idMateria));
        List<TrabajoPractico> result = query.list(trabajoPractico);
        return query.list(trabajoPractico);
    }
}
