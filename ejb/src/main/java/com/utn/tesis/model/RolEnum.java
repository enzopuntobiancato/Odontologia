package com.utn.tesis.model;

public enum RolEnum {
    ADMINISTRADOR(Constants.ADMINISTRADOR) {
        @Override
        public String toString() {
            return "Administrador";
        }
    },
    ALUMNO(Constants.ALUMNO) {
        @Override
        public String toString() {
            return "Alumno";
        }
    },
    PROFESOR(Constants.PROFESOR) {
        @Override
        public String toString() {
            return "Profesor";
        }
    },
    RESPONSABLE_RECEPCION_PACIENTES(Constants.RESPONSABLE_RECEPCION_PACIENTES) {
        @Override
        public String toString() {
            return "Responsable de recepción de pacientes";
        }
    },
    ADMINISTRADOR_ACADEMICO(Constants.ADMINISTRADOR_ACADEMICO) {
        @Override
        public String toString() {
            return "Administrador académico";
        }
    },
    AUTORIDAD(Constants.AUTORIDAD) {
        @Override
        public String toString() {
            return "Autoridad";
        }
    };

    private String key;

    RolEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static RolEnum fromKey(String key) {
        for (RolEnum rol: RolEnum.values()) {
            if (rol.getKey().equals(key)) {
                return rol;
            }
        }
        return null;
    }

    public static class Constants {
        public static final String ADMINISTRADOR = "ADMINISTRADOR";
        public static final String ALUMNO = "ALUMNO";
        public static final String PROFESOR = "PROFESOR";
        public static final String RESPONSABLE_RECEPCION_PACIENTES = "RESPONSABLE_RECEPCION_PACIENTES";
        public static final String ADMINISTRADOR_ACADEMICO = "ADMINISTRADOR_ACADEMICO";
        public static final String AUTORIDAD = "AUTORIDAD";
    }
}
