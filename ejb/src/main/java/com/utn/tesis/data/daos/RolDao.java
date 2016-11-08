package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.QRol;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.RolEnum;

public class RolDao extends DaoBase<Rol> {
    QRol rol = QRol.rol;

    public Rol findByRolEnum(RolEnum rolEnum) {
        JPAQuery query = new JPAQuery(em).from(rol);
        query.where(rol.nombre.eq(rolEnum));
        return query.singleResult(rol);
    }
}
