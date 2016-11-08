package com.utn.tesis.data;

import com.utn.tesis.model.Persona;
import com.utn.tesis.model.RolEnum;

import java.util.List;

public interface PersonaDaoQueryResolver {
    <T extends Persona> boolean supports(Class<T> personaClass);
    List<? extends Persona> validateByDocument(Persona entity);
}
