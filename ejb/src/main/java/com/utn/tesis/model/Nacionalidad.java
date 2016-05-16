package com.utn.tesis.model;

public enum Nacionalidad {
    ARGENTINO_NATIVO {
        @Override
        public String toString() {
            return "Argentino nativo";
        }
    },
    ARGENTINO_NO_NATIVO {
        @Override
        public String toString() {
            return "Argentino no nativo";
        }
    },
    EXTRANJERO {
        @Override
        public String toString() {
            return "Extranjero";
        }
    }
}
