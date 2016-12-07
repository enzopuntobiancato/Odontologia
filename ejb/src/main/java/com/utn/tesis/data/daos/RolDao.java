package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.*;

import java.util.List;

public class RolDao extends DaoBase<Rol> {
    QRol rol = QRol.rol;

    public Rol findByRolEnum(RolEnum rolEnum) {
        JPAQuery query = new JPAQuery(em).from(rol);
        query.where(rol.nombre.eq(rolEnum));
        return query.singleResult(rol);
    }

    public List<Privilegio> findPrivilegiosByRol(String rolKey) {
        QPrivilegio privilegio = QPrivilegio.privilegio;
        QFuncionalidad funcionalidad = QFuncionalidad.funcionalidad;
        QGrupoFuncionalidad grupoFuncionalidad = QGrupoFuncionalidad.grupoFuncionalidad;
        JPAQuery query = new JPAQuery(em).from(rol)
                .innerJoin(rol.privilegios, privilegio)
                .innerJoin(privilegio.funcionalidad, funcionalidad)
                .leftJoin(funcionalidad.grupoFuncionalidad, grupoFuncionalidad)
                .where(rol.nombre.eq(RolEnum.valueOf(rolKey)))
                .orderBy(grupoFuncionalidad.nombre.asc(), funcionalidad.nombre.asc());
        return query.list(privilegio);
    }

    public List<Rol> findAllOrderByNombre() {
        JPAQuery query = new JPAQuery(em).from(rol)
                .orderBy(rol.nombre.asc());
        return query.list(rol);
    }
}
