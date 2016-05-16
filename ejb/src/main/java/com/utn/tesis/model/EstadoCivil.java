package com.utn.tesis.model;

public enum EstadoCivil {

    SOLTERO {
        @Override
        public String toString() {
            return "Soltero";
        }
    },
    CASADO {
        @Override
        public String toString() {
            return "Casado";
        }
    },
    VIUDO {
        @Override
        public String toString() {
            return "Viudo";
        }
    },
    DIVORCIADO {
        @Override
        public String toString() {
            return "Divorciado";
        }
    }
}
