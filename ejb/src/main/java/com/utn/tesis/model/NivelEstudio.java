package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 13/01/16
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public enum NivelEstudio {

    PRIMARIO {
        @Override
        public String toString() {
            return "Primario";
        }
    },
    PRIMARIO_INCOMPLETO {
        @Override
        public String toString() {
            return "Primario incompleto";
        }
    },
    SECUNDARIO {
        @Override
        public String toString() {
            return "Secundario";
        }
    },
    SECUNDARIO_INCOMPLETO {
        @Override
        public String toString() {
            return "Secundario incompleto";
        }
    },
    TERCIARIO {
        @Override
        public String toString() {
            return "Terciario";
        }
    },
    TERCIARIO_INCOMPLETO {
        @Override
        public String toString() {
            return "Terciario incompleto";
        }
    },
    UNIVERSITARIO {
        @Override
        public String toString() {
            return "Universitario";
        }
    },
    UNIVERSITARIO_INCOMPLETO {
        @Override
        public String toString() {
            return "Universitario incompleto";
        }
    },
    POST_UNIVERSITARIO {
        @Override
        public String toString() {
            return "Postuniversitario";
        }
    },
    NO_POSEE {
        @Override
        public String toString() {
            return "No posee";
        }
    };

}
