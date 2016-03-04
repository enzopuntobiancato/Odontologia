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
public class AlumnoDao extends DaoBase<Alumno> {

    QAlumno alumno = QAlumno.alumno;

    public List<Alumno> findByFilters(String nombre, String apellido, String legajo, Usuario usuario, Documento documento) {
        JPAQuery query = new JPAQuery(em).from(alumno);

        if(nombre != null) {
            query.where(alumno.nombre.containsIgnoreCase(nombre));
        }
        if(apellido != null) {
            query.where(alumno.apellido.containsIgnoreCase(apellido));
        }
        if(legajo != null) {
            query.where(alumno.legajo.equalsIgnoreCase(legajo));
        }
        if(usuario != null) {
            query.where(alumno.usuario.id.eq(usuario.getId()));
        }
        if(documento != null) {
            query.where(alumno.documento.numero.eq(documento.getNumero())
                    .and(alumno.documento.tipoDocumento.eq(documento.getTipoDocumento())));
        }

        return query.list(alumno);
    }

}
