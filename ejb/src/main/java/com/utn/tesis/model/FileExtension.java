package com.utn.tesis.model;

import lombok.Getter;

/**
 * User: Enzo
 * Date: 9/11/15
 * Time: 22:49
 */
@Getter
public enum FileExtension {
    JPG("jpg", "image/jpeg") {
        @Override
        public boolean isImage() {
            return true;
        }
    },
    PNG("png", "image/png") {
        @Override
        public boolean isImage() {
            return true;
        }
    },
    PDF("pdf", "application/pdf") {
        @Override
        public boolean isPdf() {
            return true;
        }
    },
    BMP("bmp", "image/bmp") {
       @Override
        public boolean isImage() {
           return true;
       }
    },
    NONE;

    private final String name;
    private final String mimeType;

    private FileExtension(String name, String mimeType) {
        this.name = name;
        this.mimeType = mimeType;
    }

    private FileExtension() {
        this.name = null;
        this.mimeType = null;
    }

    public boolean isImage() {
        return false;
    }

    public boolean isPdf() {
        return false;
    }
}
