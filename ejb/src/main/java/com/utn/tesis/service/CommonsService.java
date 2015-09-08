package com.utn.tesis.service;

import com.utn.tesis.data.daos.GrupoPracticaOdontologicaDao;
import com.utn.tesis.data.daos.RolDao;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class CommonsService {

    @Inject
    GrupoPracticaOdontologicaDao grupoPracticaOdontologicaDao;

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

    public List<TipoDocumento> findAllTiposDocumento() {
        return new ArrayList<TipoDocumento>(Arrays.asList(TipoDocumento.values()));
    }
}
