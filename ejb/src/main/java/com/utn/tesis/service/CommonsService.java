package com.utn.tesis.service;

import com.utn.tesis.model.Dia;
import com.utn.tesis.model.Nivel;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class CommonsService {

    public List<Nivel> findAllNiveles()
    {
        List<Nivel> result = new ArrayList<Nivel>(Arrays.asList(Nivel.values()));
        return result;
    }

    public List<Dia> findAllDias()
    {
        List<Dia> result = new ArrayList<Dia>(Arrays.asList(Dia.values()));
        return result;
    }
}
