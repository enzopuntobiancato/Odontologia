package com.utn.tesis.service;

import com.utn.tesis.data.daos.BackupDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.BackupDTO;
import com.utn.tesis.mapping.mapper.BackupMapper;
import com.utn.tesis.model.Backup;
import com.utn.tesis.util.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Stateless
public class BackupService extends BaseService<Backup> {
    @Inject
    private BackupDao backupDao;
    @Inject
    private Validator validator;
    @Inject
    private BackupGenerator backupGenerator;
    @Inject
    private BackupMapper backupMapper;
    @Inject
    private FileUtils fileUtils;

    @Override
    DaoBase<Backup> getDao() {
        return backupDao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public void generateNewBackup() throws SAPOException {
        Backup backup = new Backup();
        backup.setFechaGeneracion(Calendar.getInstance());
        File backupFile = backupGenerator.backupDatabase();
        backup.setNombre(backupFile.getName());
        backup.setPath(backupFile.getAbsolutePath());
        this.save(backup);
    }

    public List<BackupDTO> findByFilters(Date fechaGeneracion, Long pageNumber, Long pageSize) {
        Calendar calendar = null;
        if (fechaGeneracion != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(fechaGeneracion);
        }
        return backupMapper.toDTOList(backupDao.findFilters(calendar, pageNumber, pageSize));
    }

    public Pair<String, byte[]> findBackup(Long idBackup) throws IOException {
        Backup backup = this.findById(idBackup);
        File file = new File(backup.getPath());
        return Pair.of(backup.getNombre(), fileUtils.fileToArrayByte(file));
    }
}
