package com.utn.tesis.service;

import com.utn.tesis.data.daos.GrupoPracticaOdontologicaDao;
import com.utn.tesis.data.daos.RolDao;
import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class CommonsService {

    @Inject
    private GrupoPracticaOdontologicaDao grupoPracticaOdontologicaDao;
    @Inject
    private EnumMapper enumMapper;

    @Inject
    RolDao rolDao;

    public List<Nivel> findAllNiveles() {
        List<Nivel> result = new ArrayList<Nivel>(Arrays.asList(Nivel.values()));
        return result;
    }

    public List<Dia> findAllDias() {
        List<Dia> result = new ArrayList<Dia>(Arrays.asList(Dia.values()));
        return result;
    }

    public List<GrupoPracticaOdontologica> findAllGruposPracticaOdontologica() {
        List<GrupoPracticaOdontologica> result = grupoPracticaOdontologicaDao.findAll();

        return result;
    }

    public List<Rol> findAllRoles() {
        List<Rol> result = rolDao.findAll();

        return result;
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
