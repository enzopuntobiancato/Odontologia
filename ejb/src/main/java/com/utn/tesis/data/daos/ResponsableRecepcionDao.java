package com.utn.tesis.data.daos;


import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/01/16
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class ResponsableRecepcionDao extends DaoBase<ResponsableRecepcion> {

    QResponsableRecepcion responsableRecepcion = QResponsableRecepcion.responsableRecepcion;

    public List<ResponsableRecepcion> findByFilters(String nombre, String apellido, Usuario usuario, Documento documento) {
        JPAQuery query = new JPAQuery(em).from(responsableRecepcion);

        if(nombre != null) {
            query.where(responsableRecepcion.nombre.containsIgnoreCase(nombre));
        }
        if(apellido != null) {
            query.where(responsableRecepcion.apellido.containsIgnoreCase(apellido));
        }
        if(usuario != null) {
            query.where(responsableRecepcion.usuario.id.eq(usuario.getId()));
        }
        if(documento != null) {
            query.where(responsableRecepcion.documento.numero.eq(documento.getNumero())
                    .and(responsableRecepcion.documento.tipoDocumento.eq(documento.getTipoDocumento())));
        }

        return query.list(responsableRecepcion);
    }

}
