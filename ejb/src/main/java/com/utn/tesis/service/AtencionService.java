package com.utn.tesis.service;

import com.utn.tesis.data.daos.AtencionDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.mapping.dto.AtencionDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.ArchivoMapper;
import com.utn.tesis.mapping.mapper.AtencionMapper;
import com.utn.tesis.model.*;
import com.utn.tesis.model.odontograma.Odontograma;
import com.utn.tesis.model.odontograma.PiezaDental;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.ArrayList;
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
@Slf4j
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
    @Inject
    private HistoriaClinicaService historiaClinicaService;

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

    public Long save(AtencionDTO atencionDTO, UsuarioLogueadoDTO generadoPor) throws SAPOException {
        Atencion atencion = atencionMapper.fromDTO(atencionDTO);
        atencion.setFechaDeCarga(Calendar.getInstance());
        AsignacionPaciente asignacionPaciente = asignacionPacienteService.registrarMovimiento(atencionDTO.getAsignacionPaciente().getId(), generadoPor.getId(), EstadoAsignacionPaciente.ATENCION_REGISTRADA);
        atencion.setAsignacionPaciente(asignacionPaciente);
        save(atencion);
        HistoriaClinica hc = asignacionPaciente.getPaciente().getHistoriaClinica();
        hc.addAtencion(atencion);

        Atencion referenceForDiagnostico = null;
        String observacionesForDiagnostico = null;
        EstadoDiagnostico estadoDiagnostico ;
        Diagnostico diagnostico = asignacionPaciente.getDiagnostico();
        List<PiezaDental> piezasDentales = new ArrayList<PiezaDental>();

        if (atencionDTO.isDiagnosticoSolucionado()) {
            referenceForDiagnostico = atencion;
            estadoDiagnostico = EstadoDiagnostico.SOLUCIONADO;

            if (diagnostico.getPiezas() != null && diagnostico.getPiezas().size() > 0){
                //Guardar piezas
                realizarArregloEnPiezasDentales(diagnostico.getPiezas(), hc);
            }

        } else {
            observacionesForDiagnostico = atencionDTO.getMotivoNoSolucion();
            estadoDiagnostico = EstadoDiagnostico.PENDIENTE;
        }
        diagnosticoService.registrarMovimiento(
                diagnostico.getId(),
                generadoPor.getId(),
                estadoDiagnostico,
                observacionesForDiagnostico,
                referenceForDiagnostico
        );
        return atencion.getId();
    }

    public void saveRelatedDocs(Long atencionId, List<ArchivoDTO> documentaciones) throws SAPOException {
        Atencion atencion = findById(atencionId);
        if (atencion.getDocumentaciones() == null) {
            atencion.setDocumentaciones(new ArrayList<Archivo>());
        } else {
            atencion.getDocumentaciones().clear();
        }
        atencion.getDocumentaciones().addAll(archivoService.saveList(documentaciones));
    }

    public List<AtencionDTO> findByFilters(Long pacienteId, Long practicaId, Long catedraId, Long trabajoPracticoId, Date fechaDesde, Date fechaHasta, Long pageNumber, Long pageSize) {
        return atencionMapper.toDTOList(dao.findByFilters(pacienteId, practicaId, catedraId, trabajoPracticoId, fechaDesde, fechaHasta, pageNumber, pageSize));
    }

    public List<ArchivoDTO> findDocumentacionesByAtencion(Long atencionId) {
        return archivoMapper.toDTOList(dao.findDocumentacionesByAtencion(atencionId));
    }

    private boolean realizarArregloEnPiezasDentales(List<Integer> codigoPiezas,  HistoriaClinica hc) {
        try {
            Odontograma odontograma = hc.getOdontograma();
            odontograma.realizarArregloEnPiezasDentales(codigoPiezas);
            hc.setOdontograma(odontograma);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
