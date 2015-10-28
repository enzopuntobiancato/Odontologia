package com.utn.tesis.service.initialization;

import com.utn.tesis.annotation.RunOnInit;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.*;
import com.utn.tesis.service.*;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.*;
import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 28/05/15
 * Time: 22:15
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Slf4j
public class InitializationService {
    @Inject
    GrupoPracticaOdontologicaService grupoPracticaOdontologicaService;
    @Inject
    MateriaService materiaService;
    @Inject
    TrabajoPracticoService trabajoPracticoService;
    @Inject
    PracticaOdontologicaService practicaOdontologicaService;
    @Inject
    UsuarioService usuarioService;

    private final ArrayList<GrupoPracticaOdontologica> grupoPracticaOdontologicasList = new ArrayList<GrupoPracticaOdontologica>();
    private final ArrayList<PracticaOdontologica> PracticaOdontologicasList = new ArrayList<PracticaOdontologica>();

    private List<InitMethod> getMethodsToRun() {
        Method[] allDeclaredMethods = this.getClass().getDeclaredMethods();
        List<InitMethod> methodsToRun = new ArrayList<InitMethod>(allDeclaredMethods.length);

        for (Method method : allDeclaredMethods) {
            if (method.isAnnotationPresent(RunOnInit.class)) {
                methodsToRun.add(new InitMethod(method, method.getAnnotation(RunOnInit.class).order()));
            }
        }
        Collections.sort(methodsToRun);
        return methodsToRun;
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean initializeData() throws SAPOException {
        boolean execute = !InitVariables.getInstance().isInitializationRunned();
        if (execute) {
            boolean someError = false;
            List<InitMethod> methodsToRun = this.getMethodsToRun();

            for (InitMethod initMethod : methodsToRun) {
                try {
                    initMethod.getMethod().invoke(this);
                } catch (Exception e) {
                    someError = true;
                    log.error("Error durante la inicialización de los datos en {}. Causa: {}", initMethod.getMethod().getName(), e.getCause().toString());
                }
            }
            InitVariables.getInstance().setInitializationRunned(true);
            if (someError) {
                throw new SAPOException();
            }
        }
        return execute;
    }

//    private void cargarUsuarios() throws SAPOException {
//        Usuario usuario = new Usuario();
//        usuario.setNombreUsuario("enzo");
//        usuario.setContrasenia("123");
//        usuario.setRol();
//        usuarioService.create(usuario);
//    }

    @RunOnInit(order = 1)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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

    @RunOnInit(order = 2)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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

    @RunOnInit(order = 3)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarPracticaOdontologica() throws SAPOException {

        PracticaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "EXAMEN DIAGNÓSTICO, FICHADO Y PLAN DE TRATAMIENTO", "Se considera primera consulta al examen, diagnóstico, fichado y plan de tratamiento. Como consecuencia del exámen, el fichado deberá reflejar el estado actual de la boca, previo al tratamiento.")));
        PracticaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "VISITA A DOMICILIO", "Se considera consulta domiciliaria a la atención de pacientes impedidos de trasladarse al consultorio del odontólogo.")));
        PracticaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "CONSULTA DE URGENCIA", "Se considera consulta de urgencia a toda prestación que no constituye paso intermedio y/o final de tratamiento. Importante establecer el motivo de la misma.")));
        PracticaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "OBTURACIÓN CON AMALGAMA - CAVIDAD SIMPLE", "Se reconocerá como obturación simple de amalgama a aquellas en las que se haya practicado un adecuado tallado de la cavidad.")));
    }

    @RunOnInit(order = 4)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarTrabajoPracticos() throws SAPOException {
        TrabajoPractico tp1 = new TrabajoPractico();
        tp1.setNombre("TP 1 - Diagnostico");
        tp1.setDescripcion("diagnosticar bla bla bla");
        tp1.setPracticaOdontologica(PracticaOdontologicasList.get(0));

        trabajoPracticoService.create(tp1);
    }

}







