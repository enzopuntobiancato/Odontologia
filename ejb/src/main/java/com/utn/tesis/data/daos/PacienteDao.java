package com.utn.tesis.data.daos;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.Arrays;
import java.util.List;

public class PacienteDao extends DaoBase<Paciente> {

    QPaciente paciente, $ = QPaciente.paciente;

    public List<Paciente> findByFilters(String nombre, String apellido, Documento documento, Sexo sexo, boolean dadosBaja, Long page, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from($);
        if (nombre != null)
            query.where($.nombre.startsWithIgnoreCase(nombre));
        if (sexo != null)
            query.where($.sexo.eq(sexo));
        if (apellido != null)
            query.where($.apellido.startsWithIgnoreCase(apellido));
        if (documento.getNumero() != null) {
            query.where($.documento.numero.equalsIgnoreCase(documento.getNumero()));
        }
        if (documento.getTipoDocumento() != null) {
            query.where($.documento.tipoDocumento.eq(documento.getTipoDocumento()));
        }
        if (!dadosBaja) {
            query.where($.fechaBaja.isNull());
        }

        query = paginar(query, page, pageSize);
        return query.list($);
    }

    public List<Paciente> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        JPAQuery query = new JPAQuery(em).from($);
        if (nombApp != null) {
            List<String> filtros = Arrays.asList(nombApp.split(" "));
            for (String s : filtros) {
                query.where($.nombre.containsIgnoreCase(s)
                        .or($.apellido.containsIgnoreCase(s)));
            }
        }

        query = paginar(query, page, pageSize);
        return query.list($);
    }

//    public List<Paciente> findByRol(Rol rol, Long page, Long pageSize) {
//        JPAQuery query = new JPAQuery(em).from($);
//        if (rol != null)
//            query.where($.usuario.rol.id.eq(rol.getId()));
//
//        query = paginar(query, page, pageSize);
//        return query.list($);
//    }

    public List<Paciente> validateByDocument(Paciente entity) {
        BooleanBuilder expression = new BooleanBuilder($.documento.eq(entity.getDocumento()));
        if (entity.getId() !=  null) {
            expression.and($.id.ne(entity.getId()));
        }
        return new JPAQuery(em).from($).where(expression).list($);
    }

    public List<Paciente> validateByEmail(Paciente paciente) {
        BooleanBuilder expression = new BooleanBuilder($.email.eq(paciente.getEmail()));
        if (paciente.getId() != null) {
            expression.and($.id.ne(paciente.getId()));
        }
        return new JPAQuery(em).from($).where(expression).list($);
    }

    public List<Archivo> findDocumentacionesByPaciente(Long pacienteId) {
        QHistoriaClinica historiaClinica = QHistoriaClinica.historiaClinica;
        QArchivo archivo = QArchivo.archivo;
        JPAQuery query = new JPAQuery(em).from($);

        query.innerJoin($.historiaClinica, historiaClinica)
                .innerJoin(historiaClinica.documentacion, archivo);
        query.where($.id.eq(pacienteId));

        return query.list(archivo);
    }
}
