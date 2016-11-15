package com.utn.tesis.data.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import com.utn.tesis.model.Backup;
import com.utn.tesis.model.QBackup;

import java.util.Calendar;
import java.util.List;

public class BackupDao extends DaoBase<Backup> {
    public List<Backup> findFilters(Calendar fechaGeneracion, Long pageNumber, Long pageSize) {
        QBackup backup = QBackup.backup;
        JPAQuery query = new JPAQuery(em).from(backup);
        if (fechaGeneracion != null) {
            query.where(backup.fechaGeneracion.eq(fechaGeneracion));
        }
        query = paginar(query, pageNumber, pageSize);
        return query.list(backup);
    }
}
