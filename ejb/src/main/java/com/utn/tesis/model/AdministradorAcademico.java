package com.utn.tesis.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administradores_academicos")
public class AdministradorAcademico extends Persona {
    private static final long serialVersionUID = -4926195025848228565L;
}
