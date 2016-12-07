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

    public HistoriaClinica findByPacienteId(Long pacienteId){
        QPaciente paciente = QPaciente.paciente;

        JPAQuery query = new JPAQuery(em).from(paciente);
        query.where(paciente.id.eq(pacienteId));
        query.innerJoin(paciente.historiaClinica, historiaClinica);

        return query.singleResult(historiaClinica);
    }



}
