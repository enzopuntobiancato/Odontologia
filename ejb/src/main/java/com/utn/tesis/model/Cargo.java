package com.utn.tesis.model;

public enum Cargo {

    ACADEMICO {
        @Override
        public String toString() {
            return "Académico";
        }
    },
    BEDEL {
        @Override
        public String toString() {
            return "Bedel";
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
    VICEDECANO {
        @Override
        public String toString() {
            return "Vicedecano";
        }
    },
    OTRO {
        @Override
        public String toString() {
            return "Otro";
        }
    };
}
