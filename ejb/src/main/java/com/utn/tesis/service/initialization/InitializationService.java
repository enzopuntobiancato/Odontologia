package com.utn.tesis.service.initialization;

import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.MateriaService;
import com.utn.tesis.service.PracticaOdontologicaService;
import com.utn.tesis.service.TrabajoPracticoService;
import com.utn.tesis.service.UsuarioService;

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

    @Inject
    TrabajoPracticoService trabajoPracticoService;

    @Inject
    PracticaOdontologicaService practicaOdontologicaService;

    @Inject
    UsuarioService usuarioService;


    public Boolean initializeData() {
        boolean result = !InitVariables.getInstance().isInitializationRunned();
        if (!InitVariables.getInstance().isInitializationRunned()) {
            Materia m1 = new Materia();
            m1.setNombre("Prostodoncia");
            m1.setNivel(Nivel.PRIMERO);
            m1.setDescripcion("Materia de primer anio donde se adquieren los conocimientos iniciales relacionados.");

            Materia m2 = new Materia();
            m2.setNombre("Endodoncia");
            m2.setNivel(Nivel.SEGUNDO);
            m2.setDescripcion("Materia de segundo anio donde se adquieren los conocimientos iniciales relacionados.");

//            List<PracticaOdontologica> practicas = practicaOdontologicaService.findAll();
//            TrabajoPractico tp1 = new TrabajoPractico();
//            tp1.setNombre("Trabajo practico 1");
//            tp1.setDescripcion("lalal");
//            tp1.setPracticaOdontologica(practicas.get(0));

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("enzo");
            usuario.setContrasenia("123");
            usuario.setRol(new Rol(Rol.ADMIN));

            //EJECUTAR GUARDADOS Y CATCHEAR EXCEPTION PARA NO CLAVAR LA APP.
            try {
                materiaService.create(m1);
                materiaService.create(m2);
//                trabajoPracticoService.create(tp1);
                usuarioService.create(usuario);
                InitVariables.getInstance().setInitializationRunned(true);
            } catch (Exception e) {
                Logger.getLogger(InitializationService.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return result;
    }
}
