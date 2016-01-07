package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 06/01/16
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
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
