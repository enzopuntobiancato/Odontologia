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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stateless
@LocalBean
@Slf4j
public class ArchivoService extends BaseService<Archivo> {
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static final String WIN_PATH = "C:\\sapo";
    private static final String LIN_PATH = System.getProperty("user.home") + File.separator + "sapo_files";
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
        if (dto !=  null && dto.getExtension() != FileExtension.NONE) {
            entity = dto.getId() != null ? this.findById(dto.getId()) : new Archivo();
            archivoMapper.updateFromDTO(dto, entity);
            entity.setRuta(this.saveToDisk(dto.getArchivo(), dto.getExtension()));
            this.save(entity);
        }
        return entity;
    }

    public List<Archivo> saveList(List<ArchivoDTO> archivoDTOs) throws SAPOException {
        List<Archivo> archivos = new ArrayList<Archivo>();
        for (ArchivoDTO archivoDTO: archivoDTOs) {
            Archivo archivo = save(archivoDTO);
            if (archivo != null) {
                archivos.add(archivo);
            }
        }
        return archivos;
    }

    public String saveToDisk(@Nonnull InputStream uploadedInputStream, @Nonnull FileExtension extension) throws SAPORuntimeException {
        Preconditions.checkNotNull(extension);
        Preconditions.checkArgument(extension != FileExtension.NONE);
        try {
            String pathToUse;
            if (OS.indexOf("linux") >= 0) {
                pathToUse = LIN_PATH;
            } else {
                pathToUse = WIN_PATH;
            }
            File path = new File(pathToUse);
            if (!path.exists()) {
                path.mkdir();
            }
            int read;
            byte[] bytes = new byte[1024];
            String filePath = String.format("%s%s%s.%s", path.getAbsolutePath(), File.separator, UUID.randomUUID(), extension.getName());
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

    public ArchivoDTO findArchivo(Long id) {
        Archivo entity = this.findById(id);
        ArchivoDTO dto = null;
        if (entity != null) {
            dto = archivoMapper.toDTO(entity);
        }
        return dto;
    }

    public byte[] findFile(Long id) throws IOException {
        Archivo entity = this.findById(id);
        File f = new File(entity.getRuta());
        InputStream inputStream = new FileInputStream(f);
        byte[] buff = new byte[(int) f.length()];
        inputStream.read(buff, 0, (int) f.length());

        byte[] fileContent;
        ByteArrayInputStream buffer = new ByteArrayInputStream(buff);
        fileContent = new byte[buffer.available()];
        buffer.read(fileContent, 0, buffer.available());
        return fileContent;
    }


}
