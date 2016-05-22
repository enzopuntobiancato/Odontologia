package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Predicate;
import com.utn.tesis.model.Bajeable;
import com.utn.tesis.model.SuperEntityBase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;


public abstract class DaoBase<E extends SuperEntityBase> {

    @PersistenceContext(unitName = "primary")
    protected EntityManager em;


    Class<E> getGenericParameter() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public E findById(Long idEntity) {
        return em.find(getGenericParameter(), idEntity);
    }

    public E create(E entity) {
        em.persist(entity);
        return entity;
    }

    public E update(E entity) {
        return em.merge(entity);
    }

    public void delete(Long id) {
        em.remove(findById(id));
    }

    public void deleteLogical(E entity) {
        em.merge(entity);
    }

    public E save(E entity) {
        if (entity.isNew()) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        return entity;
    }

    public JPAQuery paginar(JPAQuery query, Long page, Long pageSize) {
        if (query != null) {
            pageSize = pageSize != null ? pageSize : 5;
            query.offset(page != null ? (page * (pageSize)) : 0);
            query.limit(pageSize + 1);
        }
        return query;
    }

    public List<E> performQuery(EntityPath<E> from, Predicate restrictions) {
        JPAQuery query = new JPAQuery(em).from(from).where(restrictions);
        return query.list(from);
    }

    /**
     * Busca todas las ocurrencias en la base de datos de la entidad teniendo en cuenta si es Bajeable.
     * Si lo es solo trae aquellas que no se encuentren dadas de baja.
     *
     * @return La lista de las entidades activas
     */
    public List<E> findAll() {
        String q = "SELECT e FROM " + getGenericParameter().getSimpleName() + " e";
        if (getGenericParameter().getSuperclass().equals(Bajeable.class)) {
            q = q.concat(" where e.fechaBaja is null");
        }
        Query query = em.createQuery(q);
        List<E> result = query.getResultList();
        return result;
    }

    /**
     * Método que recibe un mapa de objetos para luego recorrerlo y armar una consulta JPA.
     * @param filter Mapa de objetos,
     * @return
     */
    public List<E> findBy(HashMap<String, Object> filter) {
        StringBuilder q = new StringBuilder("SELECT e FROM " + getGenericParameter().getSimpleName() + " e ");
        if(!filter.isEmpty()){
           q.append("WHERE ");
           int i = 0;
           for (String column : filter.keySet()){
               i++;
               if(filter.get(column) instanceof String){
                   q.append(" e."+column + " = :" + column);
               }else if(filter.get(column) == null){
                   q.append(" e." + column + " IS NULL");
               }else {
                   q.append(column + "= :" + column);
               }

               if (i < filter.size()) {
                   q.append(" AND ");
               }
           }
        }
        Query query = em.createQuery(q.toString());
        for (String column : filter.keySet()){
            if(filter.get(column) != null){
                query.setParameter(column,filter.get(column));
            }
        }
        List<E> result = query.getResultList();
        return result;
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
