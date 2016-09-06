package com.utn.tesis.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

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

    public static List<EstadoDiagnostico> FINAL_STATES = ImmutableList.of(SOLUCIONADO, SOLUCIONADO_EXTERNO, CANCELADO);
    public static List<EstadoDiagnostico> MANUAL_POSSIBLE_TRANSITIONS = ImmutableList.of(SOLUCIONADO_EXTERNO, CANCELADO);

}
