package com.utn.tesis.model;

public enum Dia {
    LUNES {
        @Override
        public String toString() {
            return "Lunes";
        }
    },
    MARTES {
        @Override
        public String toString() {
            return "Martes";
        }
    },
    MIERCOLES {
        @Override
        public String toString() {
            return "Miércoles";
        }
    },
    JUEVES {
        @Override
        public String toString() {
            return "Jueves";
        }
    },
    VIERNES {
        @Override
        public String toString() {
            return "Viernes";
        }
    },
    SABADO {
        @Override
        public String toString() {
            return "Sábado";
        }
    },
    DOMINGO {
        @Override
        public String toString() {
            return "Domingo";
        }
    };

}
