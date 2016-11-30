package com.utn.tesis.service;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.TrabajoPracticoDao;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.TrabajoPractico;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;

@Stateless
public class TrabajoPracticoService extends BaseService<TrabajoPractico> {

    @Inject
    private TrabajoPracticoDao dao;

    @Inject
    private Validator validator;

    @Override
    DaoBase<TrabajoPractico> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<TrabajoPractico> findByFilters(String nombre, Long grupoPracticaId, Long practicaId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        return dao.findByFilters(nombre, grupoPracticaId, practicaId, dadosBaja, pageNumber, pageSize);
    }

    public List<TrabajoPractico> findByCatedra(Long idMateria){
        return dao.findByCatedra(idMateria);
    }

    @Override
    protected void bussinessValidation(TrabajoPractico entity) throws SAPOValidationException {
        boolean executeNameValidation = true;
        if (!entity.isNew()) {
            TrabajoPractico persistedEntity = this.findById(entity.getId());
            executeNameValidation = !entity.getNombre().equalsIgnoreCase(persistedEntity.getNombre())
            && entity.getPracticaOdontologica().equals(persistedEntity.getPracticaOdontologica());
        }
        if (executeNameValidation) {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("nombre", entity.getNombre());
            filter.put("practicaOdontologica", entity.getPracticaOdontologica());

            List<TrabajoPractico> result = dao.findBy(filter);
            if (!result.isEmpty()) {
                HashMap<String, String> error = new HashMap<String, String>(1);
                error.put("nombre", "El trabajo práctico " + entity.getNombre() + " ya se encuentra registrado.");
                throw new SAPOValidationException(error);
            }
        }
        super.bussinessValidation(entity);
    }

    @Override
    protected void bussinessValidationBaja(TrabajoPractico entity) throws SAPOValidationException {
        boolean hasActiveCatedras = dao.hasActiveCatedras(entity);
        if (!hasActiveCatedras) {
            throw new SAPOValidationException(
                    ImmutableMap.of("referenceToEntity",
                            String.format("No puede dar de baja el trabajo práctico %s, el mismo está siendo referenciada por alguna cátedra activa", entity.getNombre())));
        }

        boolean hasActiveAsignaciones = dao.hasActiveAsignaciones(entity);
        if (!hasActiveAsignaciones) {
            throw new SAPOValidationException(
                    ImmutableMap.of("referenceToEntity",
                            String.format("No puede dar de baja el trabajo práctico %s, el mismo está siendo referenciada por alguna asignación de paciente activa", entity.getNombre())));
        }
    }
}
