package com.utn.tesis.model;

import java.util.Calendar;

public interface IBajeable {
    void darDeAlta();
    void darDeBaja(String motivo);
    void darDeBaja(String motivo, Calendar fechaBaja);
}
