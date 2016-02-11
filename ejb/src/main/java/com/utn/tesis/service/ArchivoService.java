package com.utn.tesis.service;

import com.google.common.base.Preconditions;
import com.utn.tesis.data.daos.ArchivoDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPORuntimeException;
import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.mapping.mapper.ArchivoMapper;
import com.utn.tesis.model.Archivo;
import com.utn.tesis.model.FileExtension;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.io.*;
import java.util.UUID;

/**
 * User: Enzo
 * Date: 8/11/15
 * Time: 23:26
 */
@Stateless
@LocalBean
@Slf4j
public class ArchivoService extends BaseService<Archivo> {
    private static final String PATH = "C:\\sapo";

    @Inject
    private ArchivoDao archivoDao;
    @Inject
    private Validator validator;
    @Inject
    private ArchivoMapper archivoMapper;

    @Override
    DaoBase<Archivo> getDao() {
        return this.archivoDao;
    }

    @Override
    Validator getValidator() {
        return this.validator;
    }

    public Archivo save(ArchivoDTO dto) throws SAPOException {
        Archivo entity = null;
        if (dto !=  null) {
            Archivo archivo = dto.getId() != null ? this.findById(dto.getId()) : new Archivo();
            archivoMapper.updateFromDTO(dto, archivo);
            archivo.setRuta(this.saveToDisk(dto.getArchivo(), dto.getExtension()));
            archivo = this.save(archivo);
        }
        return entity;
    }

    public String saveToDisk(@Nonnull InputStream uploadedInputStream, @Nonnull FileExtension extension) throws SAPORuntimeException {
        Preconditions.checkNotNull(extension);
        Preconditions.checkArgument(extension != FileExtension.NONE);
        try {
            File path = new File(PATH);
            if (!path.exists()) {
                path.createNewFile();
            }
            int read;
            byte[] bytes = new byte[1024];
            String filePath = String.format("%s%s%s.%s", PATH, File.separator, UUID.randomUUID(), extension.getName());
            OutputStream outputStream = new FileOutputStream(new File(filePath));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            return filePath;
        } catch (IOException e) {
            log.error("Error al intentar guardar archivo.", e);
            throw new SAPORuntimeException(e.getMessage());
        }
    }

    public ArchivoDTO findArchivo(Long id) throws FileNotFoundException {
        Archivo entity = this.findById(id);
        ArchivoDTO dto = null;
        if (entity != null) {
            dto = archivoMapper.toDTO(entity);
            dto.setArchivo(new FileInputStream(entity.getRuta()));
        }
        return dto;
    }
}
