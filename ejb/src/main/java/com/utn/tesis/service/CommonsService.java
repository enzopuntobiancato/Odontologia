package com.utn.tesis.service;

import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.mapping.dto.GrupoPracticaOdontologicaDTO;
import com.utn.tesis.mapping.dto.RolDTO;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.mapping.mapper.GrupoPracticaOdontologicaMapper;
import com.utn.tesis.mapping.mapper.RollMapper;
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
    private GrupoPracticaOdontologicaMapper grupoPracticaMapper;
    @Inject
    private RollMapper rollMapper;

    public List<EnumDTO> findAllNiveles() {
        return enumMapper.nivelListToDTOList(Arrays.asList(Nivel.values()));
    }

    public List<Dia> findAllDias() {
        List<Dia> result = new ArrayList<Dia>(Arrays.asList(Dia.values()));
        return result;
    }

    public List<GrupoPracticaOdontologicaDTO> findAllGruposPracticaOdontologica() {
        return grupoPracticaMapper.toDTOList(grupoPracticaOdontologicaService.findAll());
    }

    public List<RolDTO> findAllRoles() {
        List<RolDTO> result = new ArrayList<RolDTO>();
        List<Rol> roles = rolService.findAll();
        for (Rol rol : roles){
             result.add(rollMapper.toRolDTO(rol));
        }
        return result;
    }

    public Rol findRolById(long id){
        return rolService.findById(id);
    }

    public List<EnumDTO> findAllTiposDocumento() {
        List<TipoDocumento> tipoDocumentoList = Arrays.asList(TipoDocumento.values());
        return enumMapper.tipoDocumentoListToDTOList(tipoDocumentoList);
    }

    public List<EnumDTO> findAllSexos() {
        List<Sexo> sexoList = Arrays.asList(Sexo.values());
        return enumMapper.sexoListToDTOList(sexoList);
    }

    public List<EnumDTO> findAllCargos() {
        return enumMapper.cargoListToDTOList(Arrays.asList(Cargo.values()));
    }
}
