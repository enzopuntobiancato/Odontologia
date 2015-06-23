package com.utn.tesis.service.initialization;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.*;
import com.utn.tesis.service.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
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
    GrupoPracticaOdontologicaService grupoPracticaOdontologicaService;
    ArrayList<GrupoPracticaOdontologica> grupoPracticaOdontologicasList = new ArrayList<GrupoPracticaOdontologica>();

    @Inject
    MateriaService materiaService;

    @Inject
    TrabajoPracticoService trabajoPracticoService;

    @Inject
    PracticaOdontologicaService practicaOdontologicaService;
    ArrayList<PracticaOdontologica> PracticaOdontologicasList = new ArrayList<PracticaOdontologica>();

    @Inject
    UsuarioService usuarioService;

        public void initializeData() throws SAPOException {
            if (!InitVariables.getInstance().isInitializationRunned()) {
                cargarMaterias();
                cargarUsuarios();
                cargarGrupoPracticaOdontologica();
                cargarPracticaOdontologica();
                cargarTrabajoPracticos();
                InitVariables.getInstance().setInitializationRunned(true);
            }
        }

        private void cargarUsuarios() throws SAPOException {
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("enzo");
            usuario.setContrasenia("123");
            usuario.setRol(new Rol(Rol.ADMIN));
            usuarioService.create(usuario);
        }

    private void cargarMaterias() throws SAPOException {
            Materia m1 = new Materia();
            m1.setNombre("Prostodoncia");
            m1.setNivel(Nivel.PRIMERO);
            m1.setDescripcion("Materia de primer año donde se adquieren los conocimientos iniciales relacionados.");

            Materia m2 = new Materia();
            m2.setNombre("Endodoncia");
            m2.setNivel(Nivel.SEGUNDO);
            m2.setDescripcion("Materia de segundo año donde se adquieren los conocimientos iniciales relacionados.");

            materiaService.create(m1);
            materiaService.create(m2);
        }

        private void cargarTrabajoPracticos() throws SAPOException {
            TrabajoPractico tp1 = new TrabajoPractico();
            tp1.setNombre("TP 1 - Diagnostico");
            tp1.setDescripcion("diagnosticar bla bla bla");
            tp1.setPracticaOdontologica(PracticaOdontologicasList.get(0));

            trabajoPracticoService.create(tp1);
        }

        private void cargarGrupoPracticaOdontologica() throws SAPOException {
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("OPERATORIA DENTAL")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("ENDODONCIA SIN OBTURACIÓN")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("PRÓTESIS")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("ODONTOLOGÍA PREVENTIVA")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("ORTODONCIA Y ORTOPEDIA FUNCIONAL")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("ODONTOPEDIATRÍA")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("PERIODONCIA")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("PROSTODONCIA")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("RADIOLOGÍA")));
            grupoPracticaOdontologicasList.add(grupoPracticaOdontologicaService.create(new GrupoPracticaOdontologica("CIRUGÍA BUCAL")));
        }

        private void cargarPracticaOdontologica() throws SAPOException {

            PracticaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1),"EXAMEN DIAGNÓSTICO, FICHADO Y PLAN DE TRATAMIENTO", "Se considera primera consulta al examen, diagnóstico, fichado y plan de tratamiento. Como consecuencia del exámen, el fichado deberá reflejar el estado actual de la boca, previo al tratamiento.")));
            PracticaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1),"VISITA A DOMICILIO", "Se considera consulta domiciliaria a la atención de pacientes impedidos de trasladarse al consultorio del odontólogo.")));
            PracticaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1),"CONSULTA DE URGENCIA", "Se considera consulta de urgencia a toda prestación que no constituye paso intermedio y/o final de tratamiento. Importante establecer el motivo de la misma.")));
            PracticaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1),"OBTURACIÓN CON AMALGAMA - CAVIDAD SIMPLE", "Se reconocerá como obturación simple de amalgama a aquellas en las que se haya practicado un adecuado tallado de la cavidad.")));
         }

}







