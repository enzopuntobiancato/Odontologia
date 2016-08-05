package com.utn.tesis.data;

import com.utn.tesis.model.Persona;
import com.utn.tesis.model.RolEnum;

import java.util.List;

public interface PersonaDaoQueryResolver {
    boolean supports(RolEnum rol);
    List<? extends Persona> validateByDocument(Persona entity);
}
