package com.utn.tesis.model;

public enum FileExtension {

    JPEG {
        @Override
        public String toString() {
            return "JPEG";
        }
    },
    JPG {
        @Override
        public String toString() {
            return "JPG";
        }
    },
    BMP {
        @Override
        public String toString() {
            return "BMP";
        }
    },
    PNG {
        @Override
        public String toString() {
            return "PNG";
        }
    },
    PDF {
        @Override
        public String toString() {
            return "PDF";
        }
    };
}
