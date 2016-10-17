package com.utn.tesis.service;

import com.utn.tesis.data.daos.AtencionDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.mapping.dto.AtencionDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.ArchivoMapper;
import com.utn.tesis.mapping.mapper.AtencionMapper;
import com.utn.tesis.model.AsignacionPaciente;
import com.utn.tesis.model.Atencion;
import com.utn.tesis.model.EstadoAsignacionPaciente;
import com.utn.tesis.model.EstadoDiagnostico;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/03/16
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AtencionService extends BaseService<Atencion> {
    @Inject
    private AtencionDao dao;
    @Inject
    private Validator validator;
    @Inject
    private AtencionMapper atencionMapper;
    @Inject
    private AsignacionPacienteService asignacionPacienteService;
    @Inject
    private DiagnosticoService diagnosticoService;
    @Inject
    private ArchivoService archivoService;
    @Inject
    private ArchivoMapper archivoMapper;

    @Override
    DaoBase<Atencion> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Atencion> findByFilters(Calendar fechaAtencion, AsignacionPaciente asignacionPaciente,
                                        Long page, Long pageSize) {
        return dao.findByFilters(fechaAtencion, asignacionPaciente, page, pageSize);
    }

    public void save(AtencionDTO atencionDTO, List<ArchivoDTO> documentaciones, UsuarioLogueadoDTO generadoPor) throws SAPOException {
        Atencion atencion = atencionMapper.fromDTO(atencionDTO);
        atencion.setFechaDeCarga(Calendar.getInstance());
        AsignacionPaciente asignacionPaciente = asignacionPacienteService.registrarMovimiento(atencionDTO.getAsignacionPaciente().getId(), generadoPor.getId(), EstadoAsignacionPaciente.ATENCION_REGISTRADA);
        atencion.setAsignacionPaciente(asignacionPaciente);
        atencion.setDocumentaciones(archivoService.saveList(documentaciones));
        save(atencion);
        asignacionPaciente.getPaciente().getHistoriaClinica().addAtencion(atencion);

        Atencion referenceForDiagnostico = null;
        String observacionesForDiagnostico = null;
        EstadoDiagnostico estadoDiagnostico ;

        if (atencionDTO.isDiagnosticoSolucionado()) {
            referenceForDiagnostico = atencion;
            estadoDiagnostico = EstadoDiagnostico.SOLUCIONADO;
        } else {
            observacionesForDiagnostico = atencionDTO.getMotivoNoSolucion();
            estadoDiagnostico = EstadoDiagnostico.PENDIENTE;
        }
        diagnosticoService.registrarMovimiento(
                asignacionPaciente.getDiagnostico().getId(),
                generadoPor.getId(),
                estadoDiagnostico,
                observacionesForDiagnostico,
                referenceForDiagnostico
        );
    }

    public List<AtencionDTO> findByFilters(Long pacienteId, Long practicaId, Long catedraId, Long trabajoPracticoId, Date fechaDesde, Date fechaHasta, Long pageNumber, Long pageSize) {
        return atencionMapper.toDTOList(dao.findByFilters(pacienteId, practicaId, catedraId, trabajoPracticoId, fechaDesde, fechaHasta, pageNumber, pageSize));
    }

    public List<ArchivoDTO> findDocumentacionesByAtencion(Long atencionId) {
        return archivoMapper.toDTOList(dao.findDocumentacionesByAtencion(atencionId));
    }
}
