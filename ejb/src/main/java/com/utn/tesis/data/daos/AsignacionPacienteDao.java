package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.mapping.dto.DiagnosticoSupport;
import com.utn.tesis.model.*;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AsignacionPacienteDao extends DaoBase<AsignacionPaciente> {

    QAsignacionPaciente asignacionPaciente = QAsignacionPaciente.asignacionPaciente;

    public List<AsignacionPaciente> findByFilters(Long alumnoId,
                                                  String nombreAlumno,
                                                  String apellidoAlumno,
                                                  TipoDocumento tipoDocumentoAlumno,
                                                  String numeroDocumentoAlumno,
                                                  Long profesorId,
                                                  boolean isProfe,
                                                  List<Long> catedrasProfe,
                                                  String nombrePaciente,
                                                  String apellidoPaciente,
                                                  TipoDocumento tipoDocumentoPaciente,
                                                  String numeroDocumentoPaciente,
                                                  Long catedraId,
                                                  EstadoAsignacionPaciente estado,
                                                  Long diagnosticoId,
                                                  Calendar fechaCreacion,
                                                  Calendar fechaAsignacion,
                                                  Long trabajoPracticoId,
                                                  Long page,
                                                  Long pageSize) {

        QPaciente paciente = QPaciente.paciente;
        QAlumno alumno = QAlumno.alumno;

        JPAQuery query = new JPAQuery(em).from(asignacionPaciente)
                .innerJoin(asignacionPaciente.paciente, paciente)
                .innerJoin(asignacionPaciente.alumno, alumno);
        if (alumnoId != null)
            query.where(alumno.id.eq(alumnoId));
        if (StringUtils.isNotBlank(nombreAlumno))
            query.where(alumno.nombre.startsWithIgnoreCase(nombreAlumno));
        if (StringUtils.isNotBlank(apellidoAlumno))
            query.where(alumno.apellido.startsWithIgnoreCase(apellidoAlumno));
        if (tipoDocumentoAlumno != null)
            query.where(alumno.documento.tipoDocumento.eq(tipoDocumentoAlumno));
        if (StringUtils.isNotBlank(numeroDocumentoAlumno))
            query.where(alumno.documento.numero.startsWith(numeroDocumentoAlumno));
        if (profesorId != null)
            query.where(asignacionPaciente.autorizadoPor.id.eq(profesorId));
        if (StringUtils.isNotBlank(nombrePaciente))
            query.where(paciente.nombre.startsWithIgnoreCase(nombrePaciente));
        if (StringUtils.isNotBlank(apellidoPaciente))
            query.where(paciente.apellido.startsWithIgnoreCase(apellidoPaciente));
        if (tipoDocumentoPaciente != null)
            query.where(paciente.documento.tipoDocumento.eq(tipoDocumentoPaciente));
        if (StringUtils.isNotBlank(numeroDocumentoPaciente))
            query.where(paciente.documento.numero.startsWith(numeroDocumentoPaciente));
        if (estado != null)
            query.where(asignacionPaciente.ultimoMovimiento.estado.eq(estado));
        if (catedraId != null)
            query.where(asignacionPaciente.catedra.id.eq(catedraId));
        if (isProfe)
            if (catedrasProfe != null && !catedrasProfe.isEmpty())
                query.where(asignacionPaciente.catedra.id.in(catedrasProfe));
            else
                return Collections.emptyList();
        if (diagnosticoId != null)
            query.where(asignacionPaciente.diagnostico.id.eq(diagnosticoId));
        if (trabajoPracticoId != null)
            query.where(asignacionPaciente.trabajoPractico.id.eq(trabajoPracticoId));
        if (fechaCreacion != null)
            query.where(asignacionPaciente.fechaCreacion.eq(fechaCreacion));
        if (fechaAsignacion != null)
            query.where(asignacionPaciente.fechaAsignacion.eq(fechaAsignacion));

        query = paginar(query, page, pageSize);
        return query.list(asignacionPaciente);
    }

    public List<AsignacionPaciente> findAsignacionesConfirmadas(String nombreAlumno,
                                                                String apellidoAlumno,
                                                                String tipoDocumentoAlumno,
                                                                String numeroDocumentoAlumno,
                                                                Long catedraId,
                                                                Long trabajoPracticoId,
                                                                Calendar fechaAsignacion) {

        QAlumno alumno = QAlumno.alumno;

        JPAQuery query = new JPAQuery(em).from(asignacionPaciente)
                .innerJoin(asignacionPaciente.alumno, alumno);
        if (StringUtils.isNotBlank(nombreAlumno))
            query.where(alumno.nombre.startsWithIgnoreCase(nombreAlumno));
        if (StringUtils.isNotBlank(apellidoAlumno))
            query.where(alumno.apellido.startsWithIgnoreCase(apellidoAlumno));
        if (StringUtils.isNotBlank(tipoDocumentoAlumno)) {
            query.where(alumno.documento.tipoDocumento.eq(TipoDocumento.valueOf(tipoDocumentoAlumno)));
        }
        if(StringUtils.isNotBlank(numeroDocumentoAlumno))
            query.where(alumno.documento.numero.startsWithIgnoreCase(numeroDocumentoAlumno));
        if (trabajoPracticoId != null)
            query.where(asignacionPaciente.trabajoPractico.id.eq(trabajoPracticoId));
        if (catedraId != null)
            query.where(asignacionPaciente.catedra.id.eq(catedraId));
        if (fechaAsignacion != null)
            query.where(asignacionPaciente.fechaAsignacion.eq(fechaAsignacion));

        query.where(asignacionPaciente.ultimoMovimiento.estado.eq(EstadoAsignacionPaciente.CONFIRMADO));

        return query.list(asignacionPaciente);
    }

    /**
     * Metodo que indica si el alumno está autorizado para registrar una nueva asignacion para un trabajo practico
     * determinado. Puede tener un maximo de tres asignaciones.
     *
     * @return
     */
    public boolean estaAutorizado(Long alumnoId, Long trabajoPracticoId) {
        JPAQuery query = new JPAQuery(em).from(asignacionPaciente);
        query.where(asignacionPaciente.alumno.id.eq(alumnoId)
                .and(asignacionPaciente.trabajoPractico.id.eq(trabajoPracticoId)));
        return query.count() >= 3 ? false : true;
    }

    public List<DiagnosticoSupport> getDiagnosticosSupport(Documento documentoPaciente,
                                                           Long catedraId, Long trabajoPracticoId,
                                                           Calendar fechaAsignacion,
                                                           Long page, Long pageSize) {
        QDiagnostico diagnostico = QDiagnostico.diagnostico;
        QHistoriaClinica historiaClinica = QHistoriaClinica.historiaClinica;
        QPracticaOdontologica practicaOdontologica = QPracticaOdontologica.practicaOdontologica;
        QTrabajoPractico trabajoPractico = QTrabajoPractico.trabajoPractico;
        QPaciente paciente = QPaciente.paciente;

        JPAQuery query = new JPAQuery(em);
        query.from(paciente, trabajoPractico);
        query.innerJoin(paciente.historiaClinica, historiaClinica);
        query.innerJoin(historiaClinica.diagnostico, diagnostico);
        query.innerJoin(diagnostico.practicaOdontologica, practicaOdontologica);
        query.innerJoin(trabajoPractico.practicaOdontologica, practicaOdontologica);
        if (trabajoPracticoId != null) {
            query.where(trabajoPractico.id.eq(trabajoPracticoId));
        }
        if (catedraId != null) {
            query.where(trabajoPractico.catedras.any().id.eq(catedraId));
        }
        if (fechaAsignacion != null) {
            query.where(asignacionPaciente.fechaAsignacion.eq(fechaAsignacion));
        }
        if (documentoPaciente != null) {
            query.where(paciente.documento.eq(documentoPaciente));
        }
        query.where(diagnostico.ultimoMovimiento.estado.ne(EstadoDiagnostico.RESERVADO));
        query = paginar(query, page, pageSize);

        List<Object[]> tuplas = query.list(diagnostico.id, diagnostico.fechaCreacion, diagnostico.practicaOdontologica.denominacion,
                paciente.apellido, paciente.nombre,
                paciente.documento.tipoDocumento, paciente.documento.numero, paciente.id, trabajoPractico.nombre,
                paciente.email, paciente.telefono, paciente.celular);
        List<DiagnosticoSupport> resultados = new ArrayList<DiagnosticoSupport>();
        if (tuplas.isEmpty()) {
            return resultados;
        }

        for (Object[] tupla : tuplas) {
            Long idDiagnostico = (Long) tupla[0];
            Calendar fechaCreacion = (Calendar) tupla[1];
            String denominacionPractica = (String) tupla[2];
            String apellido = (String) tupla[3];
            String nombre = (String) tupla[4];
            String tipoDocumento = (tupla[5]).toString();
            String numeroDocumento = (String) tupla[6];
            Long pacienteId = (Long) tupla[7];
            String nombreTrabajoPractico = (String) tupla[8];
            String email = (String) tupla[9];
            String telefono = (String) tupla[10];
            String celular = (String) tupla[11];

            DiagnosticoSupport diagnosticoSupport = new DiagnosticoSupport(idDiagnostico,fechaCreacion, denominacionPractica,
                    apellido, nombre, tipoDocumento, numeroDocumento, nombreTrabajoPractico, pacienteId, email, telefono,
                    celular);

            resultados.add(diagnosticoSupport);
        }
        return resultados;
    }
}
