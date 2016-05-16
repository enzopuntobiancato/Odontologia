package com.utn.tesis.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administradores")
public class Administrador extends Persona {
    private static final long serialVersionUID = 8614636872166928499L;
}
