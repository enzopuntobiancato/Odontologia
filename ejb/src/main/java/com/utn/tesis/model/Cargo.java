package com.utn.tesis.model;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 11/01/16
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public enum Cargo {

    BEDEL {
        @Override
        public String toString() {
            return "Bedel";
        }
    },
    ACADEMICO {
        @Override
        public String toString() {
            return "Academico";
        }
    },
    VICEDECANO {
        @Override
        public String toString() {
            return "Vicedecano";
        }
    },
    DECANO {
        @Override
        public String toString() {
            return "Decano";
        }
    },
    DIRECTOR {
        @Override
        public String toString() {
            return "Director";
        }
    },
    OTRO {
        @Override
        public String toString() {
            return "Otro";
        }
    };
}
