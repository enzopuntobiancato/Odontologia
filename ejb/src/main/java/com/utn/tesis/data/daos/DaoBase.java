package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.EntityBase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;


public abstract class DaoBase<E extends EntityBase> {


    @PersistenceContext(unitName = "primary")
    protected EntityManager em;


    Class<E> getGenericParamater() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public E findById(Long idEntity) {
        return em.find(getGenericParamater(), idEntity);
    }

    public E create(E entity) {
        em.persist(entity);
        return entity;
    }

    public void update(E entity) {
        em.merge(entity);
    }

    public void delete(Long id) {
        em.remove(findById(id));
    }

    public void deleteLogical(E entity) {
        em.merge(entity);
    }

    public <S extends E> S save(S entity) {
        em.persist(entity);
        return (S) entity;
    }

    public JPAQuery paginar(JPAQuery query, Long page, Long pageSize) {
        if(query != null){
        pageSize = pageSize != null ? pageSize : 5;
        query.offset(page != null ? (page*(pageSize)) : 0);
        query.limit(pageSize + 1);
        }
        return query;
    }
     /**
    public List<E> findAll() {
        String className = ((Class<E>)(((ParameterizedType) (((Class<E>) getClass()).getGenericSuperclass())).getActualTypeArguments()[0])).getName();
        String q = "SELECT e FROM " + className +" e";
        Query query = em.createQuery(q);
        List<E> result = query.getResultList();
        return result;
    }

    public void flush() {
        em.flush();
    }

    public E saveAndFlush(E entity) {
        em.persist(entity);
        em.flush();
        return (E) entity;
    }

    public long count() {
        String className = ((Class<E>)(((ParameterizedType) (((Class<E>) getClass()).getGenericSuperclass())).getActualTypeArguments()[0])).getName();
        String q = "SELECT COUNT(all) FROM " + className +";";
        Query query = em.createQuery(q);
        Long result = (Long) query.getSingleResult();
        return result;
    }

    public void delete(E entity) {
        String className = ((Class<E>)(((ParameterizedType) (((Class<E>) getClass()).getGenericSuperclass())).getActualTypeArguments()[0])).getName();
        String q = "DELETE FROM " + className +" e WHERE e.id = "+ entity.getId() +";";
        Query query = em.createQuery(q);
        query.executeUpdate();
    }

    public <S extends E> S findOne(Long id) {
        String className = ((Class<E>)(((ParameterizedType) (((Class<E>) getClass()).getGenericSuperclass())).getActualTypeArguments()[0])).getName();
        String q = "SELECT e FROM " + className +" e WHERE e.id = "+ id +";";
        Query query = em.createQuery(q);
        return (S) query.getSingleResult();
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <S extends E> S reload(S entity, int depth) {

        if (entity instanceof EntityBase) {
            entity = (S) findOne(((EntityBase) entity).getId());
        } else {
            entity = save(entity);
        }
        return Collections.reload(entity, depth);
    }
                     */

}
