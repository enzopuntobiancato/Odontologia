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
import java.util.Calendar;
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
    private GrupoPracticaOdontologicaService grupoPracticaOdontologicaService;
    @Inject
    private MateriaService materiaService;
    @Inject
    private TrabajoPracticoService trabajoPracticoService;
    @Inject
    private PracticaOdontologicaService practicaOdontologicaService;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private RolService rolService;
    @Inject
    private PersonaService personaService;

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
                    System.out.println("e.getMessage() = " + e.getMessage());
                    log.error("Error durante la inicialización de los datos en {}.", initMethod.getMethod().getName(), e);
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
    private void cargarRoles() throws Exception {
        Rol rol = Rol.builder()
                .nombre(Rol.ADMIN)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
                .build();
        Rol rol1 = Rol.builder()
                .nombre(Rol.ADMIN_ACADEMICO)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
                .build();
        Rol rol2 = Rol.builder()
                .nombre(Rol.ALUMNO)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
                .build();
        Rol rol3 = Rol.builder()
                .nombre(Rol.AUTORIDAD)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
                .build();
        Rol rol4 = Rol.builder()
                .nombre(Rol.PROFESOR)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
                .build();
        Rol rol5 = Rol.builder()
                .nombre(Rol.RESPONSABLE_RECEPCION_PACIENTES)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
                .build();

        rolService.create(rol);
        rolService.create(rol1);
        rolService.create(rol2);
        rolService.create(rol3);
        rolService.create(rol4);
        rolService.create(rol5);
    }

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

        Materia m3 = new Materia();
        m3.setNombre("Sed volutpat");
        m3.setNivel(Nivel.PRIMERO);
        m3.setDescripcion("Sed volutpat turpis sed sem semper, id vestibulum lacus eleifend. In auctor urna id diam gravida scelerisque id vitae est..");

        Materia m4 = new Materia();
        m4.setNombre("Pellentesque");
        m4.setNivel(Nivel.SEGUNDO);
        m4.setDescripcion("Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus quis ornare neque.");

        Materia m5 = new Materia();
        m5.setNombre("Quisque");
        m5.setNivel(Nivel.PRIMERO);
        m5.setDescripcion("Quisque facilisis lectus eget urna pellentesque, ac mattis mauris ultricies. Phasellus quis dui semper, tristique massa eget, sagittis quam. Nulla facilis");

        Materia m6 = new Materia();
        m6.setNombre("Magna");
        m6.setNivel(Nivel.SEGUNDO);
        m6.setDescripcion("Magna ante, efficitur sed dui eu, fringilla condimentum mi. Donec pulvinar convallis ligula, in consectetur nisi varius ut. ");

        materiaService.create(m1);
        materiaService.create(m2);
        materiaService.create(m3);
        materiaService.create(m4);
        materiaService.create(m5);
        materiaService.create(m6);
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

        TrabajoPractico tp2 = new TrabajoPractico();
        tp2.setNombre("Lorem ipsum dolor");
        tp2.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum risus massa, pharetra maximus felis elementum eget. Aliquam erat volutpat. Praesent mollis ullamcorper mollis. Cras porta varius tincidunt. Duis accumsan rutrum tellus, non pulvinar velit. Nam vel justo et justo porta pellentesque at eget diam.");
        tp2.setPracticaOdontologica(PracticaOdontologicasList.get(0));

        trabajoPracticoService.create(tp2);

        TrabajoPractico tp3 = new TrabajoPractico();
        tp3.setNombre("Morbi at ante diam");
        tp3.setDescripcion("Morbi at ante diam. Integer auctor vulputate nulla id sollicitudin. Nam ac pellentesque quam, sed finibus ipsum. Suspendisse sodales sodales consectetur. Nunc quis risus metus. Morbi in turpis diam. Aenean quis libero lacinia, ornare erat et, mattis purus. Sed scelerisque eleifend elit, sit amet venenatis elit lobortis quis.");
        tp3.setPracticaOdontologica(PracticaOdontologicasList.get(0));

        trabajoPracticoService.create(tp3);

        TrabajoPractico tp4 = new TrabajoPractico();
        tp4.setNombre("Maecenas interdum justo");
        tp4.setDescripcion("Maecenas interdum justo arcu, ut volutpat lacus vestibulum vel. Cras eu mi et lorem auctor aliquet. Mauris mollis ultricies eros ut pretium. In magna ante, efficitur sed dui eu, fringilla condimentum mi. Donec pulvinar convallis ligula, in consectetur nisi varius ut. ");
        tp4.setPracticaOdontologica(PracticaOdontologicasList.get(1));

        trabajoPracticoService.create(tp4);

        TrabajoPractico tp5 = new TrabajoPractico();
        tp5.setNombre("Vivamus lacus lorem");
        tp5.setDescripcion("Vivamus lacus lorem, euismod vitae lobortis vitae, varius a mi. Ut rhoncus tellus id vehicula facilisis. Maecenas nunc metus, sagittis ut urna quis, faucibus feugiat nisi. In hac habitasse platea dictumst. ");
        tp5.setPracticaOdontologica(PracticaOdontologicasList.get(1));

        trabajoPracticoService.create(tp5);

        TrabajoPractico tp6 = new TrabajoPractico();
        tp6.setNombre("Duis sapien sapien");
        tp6.setDescripcion("Duis sapien sapien, tincidunt ut viverra at, tempor nec nisl. Nullam vel massa nec urna consectetur ultricies ut non nunc. Morbi vitae aliquet turpis");
        tp6.setPracticaOdontologica(PracticaOdontologicasList.get(3));

        trabajoPracticoService.create(tp6);
    }

    @RunOnInit(order = 5)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarPersonaAUsuario() throws SAPOException {
        Usuario usuario = usuarioService.findById(1L);

        Persona persona = new Administrador();
        persona.setNombre("Enzo");
        persona.setApellido("Biancato");
        persona.setFechaCarga(Calendar.getInstance());
        persona.setDocumento(new Documento("34880696", TipoDocumento.DNI));
        persona.setUsuario(usuario);

        personaService.create(persona);

        Usuario usuario2 = usuarioService.findById(2L);

        Persona persona2 = new Administrador();
        persona2.setNombre("Carlos");
        persona2.setApellido("Bianchi");
        persona2.setFechaCarga(Calendar.getInstance());
        persona2.setDocumento(new Documento("11116591", TipoDocumento.DNI));
        persona2.setUsuario(usuario2);

        personaService.create(persona2);

        Alumno persona3 = new Alumno();
        persona3.setNombre("Carlos");
        persona3.setApellido("Biancato");
        persona3.setFechaCarga(Calendar.getInstance());
        persona3.setDocumento(new Documento("11116591", TipoDocumento.DNI));
        persona3.setUsuario(usuario2);
        persona3.setLegajo("54452");

        personaService.create(persona3);
    }

}







