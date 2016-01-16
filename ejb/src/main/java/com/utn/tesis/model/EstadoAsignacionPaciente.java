package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 13/01/16
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
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
            return "Atencion registrada";
        }
    },
    NO_REGISTRA_ATENCION {
        @Override
        public String toString() {
            return "No registra atencion";
        }
    };

}
