package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 14/01/16
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
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
