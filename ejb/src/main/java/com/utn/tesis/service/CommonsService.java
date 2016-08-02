package com.utn.tesis.service;

import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.*;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class CommonsService {

    @Inject
    private GrupoPracticaOdontologicaService grupoPracticaOdontologicaService;
    @Inject
    private EnumMapper enumMapper;
    @Inject
    private RolService rolService;
    @Inject
    private ObraSocialService obraSocialService;
    @Inject
    private TrabajoService trabajoService;
    @Inject
    private GrupoPracticaOdontologicaMapper grupoPracticaMapper;
    @Inject
    private RollMapper rollMapper;
    @Inject
    private TrabajoMapper trabajoMapper;
    @Inject
    private ObraSocialMapper obraSocialMapper;
    @Inject
    private BarrioService barrioService;
    @Inject
    private CiudadService ciudadService;


    public List<EnumDTO> findAllNiveles() {
        return enumMapper.nivelListToDTOList(Arrays.asList(Nivel.values()));
    }

    public List<EnumDTO> findAllDias() {
        List<Dia> result = new ArrayList<Dia>(Arrays.asList(Dia.values()));
        return enumMapper.diaListToDTOList(result);
    }

    public List<GrupoPracticaOdontologicaDTO> findAllGruposPracticaOdontologica() {
        return grupoPracticaMapper.toDTOList(grupoPracticaOdontologicaService.findAll());
    }

    public List<RolDTO> findAllRoles() {
        return rollMapper.toDTOList(rolService.findAll());
    }

    public Rol findRolById(long id){
        return rolService.findById(id);
    }

    public List<EnumDTO> findAllTiposDocumento() {
        return enumMapper.tipoDocumentoListToDTOList(Arrays.asList(TipoDocumento.values()));
    }

    public List<EnumDTO> findAllSexos() {
        return enumMapper.sexoListToDTOList(Arrays.asList(Sexo.values()));
    }

    public List<EnumDTO> findAllCargos() {
        return enumMapper.cargoListToDTOList(Arrays.asList(Cargo.values()));
    }

    public List<EnumDTO> findAllEstadosCivil() {
        return enumMapper.estadoCivilListToDTOList(Arrays.asList(EstadoCivil.values()));
    }

    public List<EnumDTO> findAllNivelesEstudio(){
        return enumMapper.nivelEstudioListToDTOList(Arrays.asList(NivelEstudio.values()));
    }

    public List<ObraSocialDTO> findAllObrasSociales(){
        return obraSocialMapper.toDTOList(obraSocialService.findAll());
    }

    public List<TrabajoDTO> findAllTrabajos(){
        return trabajoMapper.toDTOList(trabajoService.findAll());
    }

    public List<EnumDTO> findAllNacionalidades() {
        return enumMapper.nacionalidadListToDTOList(Arrays.asList(Nacionalidad.values()));
    }

    public Barrio findBarrioById(Long barrioId) {
        return barrioService.findById(barrioId);
    }

    public Ciudad findCiudadById(Long id) {
        return ciudadService.findById(id);
    }
}
