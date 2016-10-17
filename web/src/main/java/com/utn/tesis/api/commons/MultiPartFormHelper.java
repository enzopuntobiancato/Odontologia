package com.utn.tesis.api.commons;

import com.utn.tesis.api.ObjectMapperProvider;
import com.utn.tesis.exception.SAPORuntimeException;
import com.utn.tesis.model.FileExtension;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 7/02/16
 * Time: 18:14
 */
@ApplicationScoped
public class MultiPartFormHelper {
    public static final String FILE = "file";
    public static final String NAME = "name";
    public static final String EXTENSION = "extension";

    private Map<String, Object> loadFileMap(InputPart inputPart) {
        Map<String, Object> fileMap = new HashMap<String, Object>(3);
        fileMap.put(NAME, parseFileName(inputPart.getHeaders()));
        fileMap.put(EXTENSION, parseFileExtension(inputPart.getMediaType().toString()));
        try {
            fileMap.put(FILE, inputPart.getBody(InputStream.class, null));
        } catch (IOException e) {
            throw new SAPORuntimeException("An error happened");
        }
        return fileMap;
    }

    public Map<String, Object> retrieveFile(Map<String, List<InputPart>> formParts, String key) {
        List<InputPart> filePart = formParts.get(key);
        if (filePart != null) {
            if (filePart.size() > 1) {
                throw new SAPORuntimeException("Only one file expected");
            }
            return loadFileMap(filePart.get(0));
        }
        return null;
    }

    public List<Map<String, Object>> retrieveFiles(Map<String, List<InputPart>> formParts, String key) {
        Set<String> keys = formParts.keySet();
        List<InputPart> filesParts = new ArrayList<InputPart>();
        for (String aKey: keys) {
            if (aKey.startsWith(key)) {
                filesParts.add(formParts.get(aKey).get(0));
            }
        }
        List<Map<String, Object>> filesMaps = new ArrayList<Map<String, Object>>();
        for (InputPart inputPart : filesParts) {
            filesMaps.add(loadFileMap(inputPart));
        }
        return filesMaps;
    }

    private String parseFileName(MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {
            if (name.trim().startsWith("filename")) {
                String[] tmp = name.split("=");
                return tmp[1].trim().replace("\"", "");
            }
        }
        return UUID.randomUUID().toString();
    }

    public Object retrieveObject(Map<String, List<InputPart>> formParts, String key, Class objectType) {
        List<InputPart> objects = formParts.get(key);
        if (objects.size() > 1) {
            throw new SAPORuntimeException("More than one object found");
        }
        Object result;
        try {
            InputPart object = objects.get(0);
            String json = object.getBody(String.class, null);
            ObjectMapperProvider omp = new ObjectMapperProvider();
            ObjectMapper om = omp.getContext(ObjectMapper.class);
            result = om.readValue(json, objectType);
        } catch (IOException e) {
            throw new SAPORuntimeException("An error happened");
        }
        return result;
    }

    public FileExtension parseFileExtension(String mimeType) {
        FileExtension[] values = FileExtension.values();
        for (FileExtension fe : values) {
            if (mimeType.equals(fe.getMimeType())) {
                return fe;
            }
        }
        return FileExtension.NONE;
    }
}
