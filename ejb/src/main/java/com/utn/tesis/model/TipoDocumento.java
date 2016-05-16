package com.utn.tesis.model;

public enum TipoDocumento {

    DNI {
        @Override
        public String toString() {
            return "DNI";
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
            return "Libreta de enrolamiento";
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
