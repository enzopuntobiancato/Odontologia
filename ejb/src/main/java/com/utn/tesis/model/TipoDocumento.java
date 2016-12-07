package com.utn.tesis.model;

public enum TipoDocumento {
    CI {
        @Override
        public String toString() {
            return "Cédula de identidad";
        }
    },
    DNI {
        @Override
        public String toString() {
            return "DNI";
        }
    },
    LC {
        @Override
        public String toString() {
            return "Libreta Cívica";
        }
    },
    LE {
        @Override
        public String toString() {
            return "Libreta de enrolamiento";
        }
    },
    PASAPORTE {
        @Override
        public String toString() {
            return "Pasaporte";
        }
    };
}
