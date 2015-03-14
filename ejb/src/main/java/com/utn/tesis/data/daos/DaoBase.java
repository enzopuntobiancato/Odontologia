package com.utn.tesis.data.daos;

import com.utn.tesis.model.EntityBase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;


public abstract class DaoBase<T extends EntityBase> {


    @PersistenceContext(unitName = "primary")
    protected EntityManager em;


    Class<T> getGenericParamater() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T findById(Long idEntity) {
        return em.find(getGenericParamater(), idEntity);
    }

    public T create(T entity) {
        em.persist(entity);
        return entity;
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void delete(Long id) {
        em.remove(findById(id));
    }

    public void deleteLogical(T entity) {
        em.merge(entity);
    }
}
