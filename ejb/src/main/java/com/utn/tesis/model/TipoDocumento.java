package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 19/05/15
 * Time: 23:16
 */
public enum TipoDocumento {

    DNI {
        @Override
        public String toString() {
            return "Documento nacional de identidad";
        }
    },
    PASAPORTE {
        @Override
        public String toString() {
            return "Pasaporte";
        }
    },
    LE {
        @Override
        public String toString() {
            return "Librera de enrolamiento";
        }
    },
    CI {
        @Override
        public String toString() {
            return "Cédula de identidad";
        }
    },
    LC {
        @Override
        public String toString() {
            return "Libreta Cívica";
        }
    };
}
