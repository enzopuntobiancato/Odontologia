package com.utn.tesis.model;

public enum Sexo {

    MASCULINO{
        @Override
        public String toString() {
            return "Masculino";
        }
    },
    FEMENINO{
        @Override
        public String toString() {
            return "Femenino";
        }
    };

}
