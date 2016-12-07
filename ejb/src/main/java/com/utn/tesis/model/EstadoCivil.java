package com.utn.tesis.model;

public enum EstadoCivil {

    CASADO {
        @Override
        public String toString() {
            return "Casado";
        }
    },
    DIVORCIADO {
        @Override
        public String toString() {
            return "Divorciado";
        }
    },
    SOLTERO {
        @Override
        public String toString() {
            return "Soltero";
        }
    },
    VIUDO {
        @Override
        public String toString() {
            return "Viudo";
        }
    }
}
