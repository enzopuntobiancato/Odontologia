package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 03/06/15
 * Time: 21:58
 */
public enum Rol {

    ADMIN {
        @Override
        public String toString() {
            return "ADMIN";
        }
    },
    ALUMNO {
        @Override
        public String toString() {
            return "ALUMNO";
        }
    }
}
