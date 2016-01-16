package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 13/01/16
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
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
