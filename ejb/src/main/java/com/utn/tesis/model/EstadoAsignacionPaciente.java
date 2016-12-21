package com.utn.tesis.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum EstadoAsignacionPaciente {

    PENDIENTE {
        @Override
        public String toString() {
            return "Pendiente";
        }
    },
    CANCELADO {
        @Override
        public String toString() {
            return "Cancelado";
        }
    },
    CONFIRMADO {
        @Override
        public String toString() {
            return "Confirmado";
        }
    },
    AUTORIZADO {
        @Override
        public String toString() {
            return "Autorizado";
        }
    },
    ANULADO {
        @Override
        public String toString() {
            return "Anulado";
        }
    },
    ATENCION_REGISTRADA {
        @Override
        public String toString() {
            return "Atención registrada";
        }
    },
    NO_REGISTRA_ATENCION {
        @Override
        public String toString() {
            return "No registra atención";
        }
    };

    public static List<EstadoAsignacionPaciente> FINAL_STATES = ImmutableList.of(
            NO_REGISTRA_ATENCION,
            ATENCION_REGISTRADA,
            ANULADO,
            CANCELADO
    );

}
