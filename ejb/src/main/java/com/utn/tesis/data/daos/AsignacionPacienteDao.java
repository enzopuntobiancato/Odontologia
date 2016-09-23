package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.mapping.dto.DiagnosticoSupport;
import com.utn.tesis.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class AsignacionPacienteDao extends DaoBase<AsignacionPaciente> {

    QAsignacionPaciente asignacionPaciente = QAsignacionPaciente.asignacionPaciente;

    public List<AsignacionPaciente> findByFilters(Long alumnoId, String nombreAlumno, String apellidoAlumno,
                                                  Documento documentoAlumno, Long profesorId, String nombrePaciente,
                                                  String apellidoPaciente, Documento documentoPaciente,
                                                  Long catedraId, EstadoAsignacionPaciente estado,
                                                  Long diagnosticoId, Calendar fechaCreacion,
                                                  Calendar fechaAsignacion, Long trabajoPracticoId, boolean dadosBaja,
                                                  Long page, Long pageSize) {

        QPaciente paciente = QPaciente.paciente;
        QAlumno alumno = QAlumno.alumno;

        JPAQuery query = new JPAQuery(em).from(asignacionPaciente);
        if (alumnoId != null)
            query.where(asignacionPaciente.alumno.id.eq(alumnoId));
        if (StringUtils.isNotBlank(nombreAlumno))
            query.where(asignacionPaciente.alumno.nombre.eq(nombreAlumno));
        if (StringUtils.isNotBlank(apellidoAlumno))
            query.where(asignacionPaciente.alumno.apellido.eq(apellidoAlumno));
        if (documentoAlumno != null) {
            query.innerJoin(asignacionPaciente.alumno, alumno);
            query.where(alumno.documento.numero.equalsIgnoreCase(documentoAlumno.getNumero())
                    .and(alumno.documento.tipoDocumento.eq(documentoAlumno.getTipoDocumento())));
        }
        if (profesorId != null)
            query.where(asignacionPaciente.autorizadoPor.id.eq(profesorId));
        if (StringUtils.isNotBlank(nombrePaciente))
            query.where(asignacionPaciente.paciente.nombre.eq(nombrePaciente));
        if (StringUtils.isNotBlank(apellidoPaciente))
            query.where(asignacionPaciente.paciente.apellido.eq(apellidoPaciente));
        if (documentoPaciente != null) {
            query.innerJoin(asignacionPaciente.paciente, paciente);
            query.where(paciente.documento.numero.equalsIgnoreCase(documentoPaciente.getNumero())
                    .and(paciente.documento.tipoDocumento.eq(documentoPaciente.getTipoDocumento())));
        }
        if (estado != null)
            query.where(asignacionPaciente.ultimoMovimiento.estado.eq(estado));
        if (catedraId != null)
            query.where(asignacionPaciente.trabajoPractico.catedras.any().id.eq(catedraId));
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

    public List<AsignacionPaciente> findAsignacionesConfirmadasAutorizadas(Long alumnoId, String nombreAlumno, String apellidoAlumno,
                                                    Documento documentoAlumno, Long profesorId, String nombrePaciente,
                                                    String apellidoPaciente, Documento documentoPaciente, Long trabajoPracticoId,
                                                    Long catedraId, Long page, Long pageSize) {

        QPaciente paciente = QPaciente.paciente;
        QAlumno alumno = QAlumno.alumno;
        List<EstadoAsignacionPaciente> estados = new ArrayList<EstadoAsignacionPaciente>();
        estados.add(EstadoAsignacionPaciente.AUTORIZADO);
        estados.add(EstadoAsignacionPaciente.CONFIRMADO);

        JPAQuery query = new JPAQuery(em).from(asignacionPaciente);
        if (alumnoId != null)
            query.where(asignacionPaciente.alumno.id.eq(alumnoId));
        if (StringUtils.isNotBlank(nombreAlumno))
            query.where(asignacionPaciente.alumno.nombre.eq(nombreAlumno));
        if (StringUtils.isNotBlank(apellidoAlumno))
            query.where(asignacionPaciente.alumno.apellido.eq(apellidoAlumno));
        if (documentoAlumno != null) {
            query.innerJoin(asignacionPaciente.alumno, alumno);
            query.where(alumno.documento.numero.equalsIgnoreCase(documentoAlumno.getNumero())
                    .and(alumno.documento.tipoDocumento.eq(documentoAlumno.getTipoDocumento())));
        }
        if (trabajoPracticoId != null)
            query.where(asignacionPaciente.trabajoPractico.id.eq(trabajoPracticoId));
        if (profesorId != null)
            query.where(asignacionPaciente.autorizadoPor.id.eq(profesorId));
        if (StringUtils.isNotBlank(nombrePaciente))
            query.where(asignacionPaciente.paciente.nombre.eq(nombrePaciente));
        if (StringUtils.isNotBlank(apellidoPaciente))
            query.where(asignacionPaciente.paciente.apellido.eq(apellidoPaciente));
        if (documentoPaciente != null) {
            query.innerJoin(asignacionPaciente.paciente, paciente);
            query.where(paciente.documento.numero.equalsIgnoreCase(documentoPaciente.getNumero())
                    .and(paciente.documento.tipoDocumento.eq(documentoPaciente.getTipoDocumento())));
        }
        if (catedraId != null)
            query.where(asignacionPaciente.trabajoPractico.catedras.any().id.eq(catedraId));

        query.where(asignacionPaciente.ultimoMovimiento.estado.in(estados));

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

        query = paginar(query, page, pageSize);

        List<Object[]> tuplas = query.list(diagnostico.id, diagnostico.fechaCreacion, diagnostico.practicaOdontologica.denominacion,
                paciente.apellido, paciente.nombre,
                paciente.documento.tipoDocumento, paciente.documento.numero, paciente.id);
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

            DiagnosticoSupport diagnosticoSupport = new DiagnosticoSupport(idDiagnostico, fechaCreacion,
                    denominacionPractica, apellido, nombre, tipoDocumento, numeroDocumento, pacienteId);
            resultados.add(diagnosticoSupport);
        }
        return resultados;
    }
}
