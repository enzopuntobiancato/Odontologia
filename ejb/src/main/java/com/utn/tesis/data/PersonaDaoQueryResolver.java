package com.utn.tesis.data;

import com.utn.tesis.model.Persona;

import java.util.List;

public interface PersonaDaoQueryResolver {
    boolean supports(String rol);
    List<? extends Persona> validateByDocument(Persona entity);
}
