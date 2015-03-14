package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.EntityBase;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by aleBurgos on 09/02/2015.
 */
public abstract class BaseService<T extends EntityBase> {

    private DaoBase<T> daoBase;

    public abstract T findById(Long idEntity);

    public abstract T create(T entity) throws SAPOException;

    public abstract void update(T entity) throws SAPOException;

    /*
    Template method que llama a las validaciones de restricciones de modelo y a las validaciones de negocio.
     */
    protected void validate(T entity, Validator validator) throws SAPOException {
        try {
            constraintValidation(entity, validator);
            bussinessValidation(entity);
        } catch (Exception e) {
            throw new SAPOException(e);
        }

    }

    /*
    Validación de las restricciones definidas sobre la entity.
     */
    private void constraintValidation(T entity, Validator validator) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    /*
    Validaciones de negocio (se definen en las clases hijas). Si alguna restricción de negocio no se cumple lanzar ValidationException.
     */
    public abstract void bussinessValidation(T entity) throws SAPOValidationException;

}
