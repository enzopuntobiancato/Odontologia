package com.utn.tesis.model;

public enum Sexo {

    FEMENINO{
        @Override
        public String toString() {
            return "Femenino";
        }
    },
    MASCULINO{
        @Override
        public String toString() {
            return "Masculino";
        }
    };

}
