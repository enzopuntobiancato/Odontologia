package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 13/01/16
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */
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
