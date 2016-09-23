package com.utn.tesis.data.daos;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.PredicateOperation;
import com.mysema.query.types.expr.BooleanExpression;
import com.utn.tesis.model.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 03/06/15
 * Time: 22:04
 */
public class UsuarioDao extends DaoBase<Usuario> {

    QUsuario usuario = QUsuario.usuario;

    public List<Usuario> findByFilters(String nombreUsuario, String email, Long rolId, boolean dadosBaja, Long pageNumber, Long pageSize) {

        JPAQuery query = new JPAQuery(em).from(usuario);
        if (nombreUsuario != null)
            query.where(usuario.nombreUsuario.startsWith(nombreUsuario));
        if (email != null)
            query.where(usuario.email.startsWith(email));
        if (rolId != null) {
            QRol rol = QRol.rol;
            query.join(usuario.rol, rol).where(rol.id.eq(rolId));
        }
        if (!dadosBaja)
            query.where(usuario.fechaBaja.isNull());

        query = paginar(query, pageNumber, pageSize);
        return query.list(usuario);
    }

    public Usuario findByUsernameAndPassword(String nombreUsuario, String contrasenia) {
        QUsuario usuario = QUsuario.usuario;

        JPAQuery query = new JPAQuery(em).from(usuario);
        query.where(usuario.nombreUsuario.eq(nombreUsuario).and(usuario.contrasenia.eq(contrasenia)).and(usuario.estadoAlta.eq(Bajeable.ALTA)));

        return query.uniqueResult(usuario);
    }

    public Usuario findByUsernameAndAuthToken(String nombreUsuario, String authToken) {
        QUsuario usuario = QUsuario.usuario;

        JPAQuery query = new JPAQuery(em).from(usuario);
        query.where(usuario.nombreUsuario.eq(nombreUsuario).and(usuario.authToken.eq(authToken)));

        return query.uniqueResult(usuario);
    }

    public Persona findPersonaByUsuario(Usuario usuario) {
        QPersona persona = QPersona.persona;
        JPAQuery query1 = new JPAQuery(em).from(persona)
                .where(persona.usuario.id.eq(usuario.getId()));

        return query1.uniqueResult(QPersona.persona);
    }

    public Persona findPersonaByUsuario(Long usuarioId) {
        QPersona persona = QPersona.persona;
        JPAQuery query1 = new JPAQuery(em).from(persona)
                .where(persona.usuario.id.eq(usuarioId));

        return query1.uniqueResult(QPersona.persona);
    }

    public List<Usuario> validateByUsername(String username, Long id) {
        BooleanBuilder queryBuilder = new BooleanBuilder(usuario.nombreUsuario.equalsIgnoreCase(username));
        if (id != null) {
            queryBuilder.and(usuario.id.ne(id));
        }
        return performQuery(usuario, queryBuilder);
    }

    public List<Usuario> validateByEmail(String email, Long id) {
        BooleanBuilder queryBuilder = new BooleanBuilder(usuario.email.equalsIgnoreCase(email));
        if (id != null) {
            queryBuilder.and(usuario.id.ne(id));
        }
        return performQuery(usuario, queryBuilder);
    }
}
