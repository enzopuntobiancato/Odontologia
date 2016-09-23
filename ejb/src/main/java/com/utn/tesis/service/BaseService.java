package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.IBajeable;
import com.utn.tesis.model.SuperEntityBase;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by enzo on 09/02/2015.
 */
@Slf4j
public abstract class BaseService<T extends SuperEntityBase> {

    abstract DaoBase<T> getDao();

    abstract Validator getValidator();

    public T save(T entity) throws SAPOException {
        validate(entity, getValidator());
        return getDao().save(entity);
    }

    public T findById(Long id) {

        return getDao().findById(id);
    }

    public T create(T entity) throws SAPOException {
        validate(entity, getValidator());
        return getDao().create(entity);
    }

    public T update(T entity) throws SAPOException {
        validate(entity, getValidator());
        return getDao().update(entity);
    }

    public T remove(Long id, String motivoBaja) throws SAPOException {
        T entity = getDao().findById(id);
        ((IBajeable) entity).darDeBaja(motivoBaja);
        getDao().deleteLogical(entity);
        return entity;
    }

    public void restore(Long id) {
        T entity = getDao().findById(id);
        ((IBajeable) entity).darDeAlta();
        getDao().update(entity);
    }

    public List<T> findAll() {
        return getDao().findAll();
    }


    public List<T> save(List<T> entities) throws SAPOException{
        List<T> result = new ArrayList<T>();
        for (T entity : entities){
            result.add(save(entity));
        }
        return result;
    }

    public <S extends T> S reload(S entity, int depth) {
        return getDao().reload(entity,depth);
    }

    public <S extends T> List<S> reloadList(List<S> entities, int depth) {
        List<S> result = new ArrayList<S>();
        for (S entity : entities) {
            result.add(reload(entity,depth));
        }
        return result;
    }

    /**
     * Template method que llama a las validaciones de restricciones de modelo y a las validaciones de negocio.
     * @param entity
     * @param validator
     * @throws SAPOException

    public <S extends T> S findOne(Long id) {
        return (S) getDao().findOne(id);
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <S extends T> S reload(S entity, int depth) {
        if (entity instanceof EntityBase) {
            entity = (S) findOne(((EntityBase) entity).getId());
        } else {
            try {
                entity = (S)save(entity);
            } catch (SAPOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return Collections.reload(entity, depth);
    }


    /*
    Template method que llama a las validaciones de restricciones de modelo y a las validaciones de negocio.
     */
    void validate(T entity, Validator validator) throws SAPOException {
        try {
            constraintValidation(entity, validator);
            bussinessValidation(entity);
        } catch (ConstraintViolationException cve) {
            log.error(cve.getMessage(), cve);
            throw new SAPOException(cve);
        } catch (SAPOValidationException sve) {
            log.error(sve.getMessage(), sve);
            throw new SAPOException(sve);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SAPOException(e);
        }
    }

    /**
     * Validación de las restricciones definidas sobre la entity.
     * @param entity Entidad sobre la que se ejecutan las validaciones definidas de acuerdo a las @Annotation en sus campos
     * @param validator Validador
     * @throws ConstraintViolationException
     */
    private void constraintValidation(T entity, Validator validator) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    /**
     * Validaciones de negocio (se definen en las clases hijas). Si alguna restricción de negocio no se cumple lanzar ValidationException.
     * @param entity Entidad sobre la que se ejecutan las validaciones
     * @throws SAPOValidationException
     */
    protected void bussinessValidation(T entity) throws SAPOValidationException {
        entity.validar();
    }
}
