package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoriaClinicaDao extends DaoBase<HistoriaClinica> {

    QHistoriaClinica historiaClinica = QHistoriaClinica.historiaClinica;

    public HistoriaClinica findByPaciente(Paciente paciente){
        JPAQuery query = new JPAQuery(em).from(historiaClinica);
        query.where(historiaClinica.eq(paciente.getHistoriaClinica()));
        return query.singleResult(historiaClinica);
    }

}
