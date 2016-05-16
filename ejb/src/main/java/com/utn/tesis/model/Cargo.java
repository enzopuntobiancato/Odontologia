package com.utn.tesis.model;

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
