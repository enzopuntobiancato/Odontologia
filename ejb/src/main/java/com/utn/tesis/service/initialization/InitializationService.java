package com.utn.tesis.service.initialization;

import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.service.MateriaService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 28/05/15
 * Time: 22:15
 */
@Stateless
public class InitializationService {


    @Inject
    MateriaService materiaService;

    public void initializeData() {
        if (!InitVariables.getInstance().isInitializationRunned()) {
            Materia m1 = new Materia();
            m1.setNombre("Prostodoncia");
            m1.setNivel(Nivel.PRIMERO);
            m1.setDescripcion("Materia de primer año donde se adquieren los conocimientos iniciales relacionados.");

            Materia m2 = new Materia();
            m2.setNombre("Endodoncia");
            m2.setNivel(Nivel.SEGUNDO);
            m2.setDescripcion("Materia de segundo año donde se adquieren los conocimientos iniciales relacionados.");

            //EJECUTAR GUARDADOS Y CATCHEAR EXCEPTION PARA NO CLAVAR LA APP.
            try {
                materiaService.create(m1);
                materiaService.create(m2);
                InitVariables.getInstance().setInitializationRunned(true);
            } catch (Exception e) {
                Logger.getLogger(InitializationService.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
