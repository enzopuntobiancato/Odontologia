package com.utn.tesis.model;

public enum EstadoDiagnostico {
    RESERVADO {
        @Override
        public String toString() {
            return "Reservado";
        }
    },
    PENDIENTE {
        @Override
        public String toString() {
            return "Pendiente";
        }
    },
    SOLUCIONADO {
        @Override
        public String toString() {
            return "Solucionado";
        }
    },
    SOLUCIONADO_EXTERNO {
        @Override
        public String toString() {
            return "Solucionado externo";
        }
    },
    CANCELADO {
        @Override
        public String toString() {
            return "Cancelado";
        }
    };
}
