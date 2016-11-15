package com.utn.tesis.util;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;

@ApplicationScoped
public class FileUtils {
    public byte[] fileToArrayByte(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        byte[] buff = new byte[(int) file.length()];
        inputStream.read(buff, 0, (int) file.length());

        byte[] fileContent;
        ByteArrayInputStream buffer = new ByteArrayInputStream(buff);
        fileContent = new byte[buffer.available()];
        buffer.read(fileContent, 0, buffer.available());
        return fileContent;
    }
}
