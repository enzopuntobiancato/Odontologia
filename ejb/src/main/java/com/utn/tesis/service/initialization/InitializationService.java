package com.utn.tesis.service.initialization;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.*;
import com.utn.tesis.service.*;
import com.utn.tesis.util.EncryptionUtils;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.BooleanRandomizer;
import io.github.benas.randombeans.randomizers.CalendarRandomizer;
import io.github.benas.randombeans.randomizers.StringRandomizer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

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
    @Inject
    private GrupoFuncionalidadService grupoFuncionalidadService;
    @Inject
    private FuncionalidadService funcionalidadService;
    @Inject
    private PrivilegioService privilegioService;
    @Inject
    private CatedraService catedraService;
    @Inject
    private PacienteService pacienteService;
    @Inject
    private HistoriaClinicaService historiaClinicaService;
    @Inject
    private AlumnoService alumnoService;

    private InitVariables initVariables = InitVariables.getInstance();


    private ArrayList<GrupoPracticaOdontologica> grupoPracticaOdontologicasList = new ArrayList<GrupoPracticaOdontologica>();
    private ArrayList<PracticaOdontologica> practicaOdontologicasList = new ArrayList<PracticaOdontologica>();
    private ArrayList<GrupoFuncionalidad> grupoFuncionalidadList = new ArrayList<GrupoFuncionalidad>();
    private ArrayList<Funcionalidad> funcionalidadList = new ArrayList<Funcionalidad>();
    private ArrayList<Privilegio> privilegioList = new ArrayList<Privilegio>();
    private ArrayList<Rol> rolList = new ArrayList<Rol>();
    private ArrayList<Materia> materiaList = new ArrayList<Materia>();
    private ArrayList<TrabajoPractico> trabajoPracticoList = new ArrayList<TrabajoPractico>();
    private ArrayList<Catedra> catedraList = new ArrayList<Catedra>();
    private ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
    private ArrayList<HistoriaClinica> historiaClinicas = new ArrayList<HistoriaClinica>();


    public boolean initializeData() throws SAPOException {
        if (!initVariables.isInitializationRunned()) {
            try {
                //Materia
                this.cargarMaterias();
                this.cargarGrupoPracticaOdontologica();
                this.cargarPracticaOdontologica();
                this.cargarTrabajoPracticos();
                this.cargarCatedra();

                //Perfiles
                this.cargarRoles();
                this.cargarGrupoFuncionalidad();
                this.cargarFuncionalidad();
                this.cargarPrivilegio();

                //Usuarios y personas
                this.cargarUsuarios(); //admin enzo
                this.cargarAlumnos();
                this.cargarHistoriasClinicas();
                this.cargarPacientes();


                initVariables.setInitializationRunned(Boolean.TRUE);
                return true;
            } catch (SAPOException e) {
                log.error("EXCEPCION: " + e.getException());
                log.error("EXCEPCION: " + e.getMessage());
                throw new SAPOException(e);
            }
        }
        return false;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarMaterias() throws SAPOException {
        log.info("CargarMaterias INICIO");

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

        materiaList.add(materiaService.create(m1));
        materiaList.add(materiaService.create(m2));
        materiaList.add(materiaService.create(m3));
        materiaList.add(materiaService.create(m4));
        materiaList.add(materiaService.create(m5));
        materiaList.add(materiaService.create(m6));
        log.info("CargarMaterias FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarGrupoPracticaOdontologica() throws SAPOException {
        log.info("CargarGrupoPracticaOdontologica INICIO");

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
        log.info("CargarGrupoPracticaOdontologica FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarPracticaOdontologica() throws SAPOException {
        log.info("CargarPracticaOdontologica INICIO");
        practicaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "EXAMEN DIAGNÓSTICO, FICHADO Y PLAN DE TRATAMIENTO", "Se considera primera consulta al examen, diagnóstico, fichado y plan de tratamiento. Como consecuencia del exámen, el fichado deberá reflejar el estado actual de la boca, previo al tratamiento.")));
        practicaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "VISITA A DOMICILIO", "Se considera consulta domiciliaria a la atención de pacientes impedidos de trasladarse al consultorio del odontólogo.")));
        practicaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "CONSULTA DE URGENCIA", "Se considera consulta de urgencia a toda prestación que no constituye paso intermedio y/o final de tratamiento. Importante establecer el motivo de la misma.")));
        practicaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "OBTURACIÓN CON AMALGAMA - CAVIDAD SIMPLE", "Se reconocerá como obturación simple de amalgama a aquellas en las que se haya practicado un adecuado tallado de la cavidad.")));
        log.info("CargarPracticaOdontologica FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarTrabajoPracticos() throws SAPOException {
        log.info("CargarTrabajoPracticos INICIO");
        TrabajoPractico tp1 = new TrabajoPractico();
        tp1.setNombre("TP 1 - Diagnostico");
        tp1.setDescripcion("diagnosticar bla bla bla");
        tp1.setPracticaOdontologica(practicaOdontologicasList.get(0));

        trabajoPracticoList.add(trabajoPracticoService.create(tp1));

        TrabajoPractico tp2 = new TrabajoPractico();
        tp2.setNombre("Lorem ipsum dolor");
        tp2.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum risus massa, pharetra maximus felis elementum eget. Aliquam erat volutpat. Praesent mollis ullamcorper mollis. Cras porta varius tincidunt. Duis accumsan rutrum tellus, non pulvinar velit. Nam vel justo et justo porta pellentesque at eget diam.");
        tp2.setPracticaOdontologica(practicaOdontologicasList.get(0));

        trabajoPracticoList.add(trabajoPracticoService.create(tp2));

        TrabajoPractico tp3 = new TrabajoPractico();
        tp3.setNombre("Morbi at ante diam");
        tp3.setDescripcion("Morbi at ante diam. Integer auctor vulputate nulla id sollicitudin. Nam ac pellentesque quam, sed finibus ipsum. Suspendisse sodales sodales consectetur. Nunc quis risus metus. Morbi in turpis diam. Aenean quis libero lacinia, ornare erat et, mattis purus. Sed scelerisque eleifend elit, sit amet venenatis elit lobortis quis.");
        tp3.setPracticaOdontologica(practicaOdontologicasList.get(0));

        trabajoPracticoList.add(trabajoPracticoService.create(tp3));

        TrabajoPractico tp4 = new TrabajoPractico();
        tp4.setNombre("Maecenas interdum justo");
        tp4.setDescripcion("Maecenas interdum justo arcu, ut volutpat lacus vestibulum vel. Cras eu mi et lorem auctor aliquet. Mauris mollis ultricies eros ut pretium. In magna ante, efficitur sed dui eu, fringilla condimentum mi. Donec pulvinar convallis ligula, in consectetur nisi varius ut. ");
        tp4.setPracticaOdontologica(practicaOdontologicasList.get(1));

        trabajoPracticoList.add(trabajoPracticoService.create(tp4));

        TrabajoPractico tp5 = new TrabajoPractico();
        tp5.setNombre("Vivamus lacus lorem");
        tp5.setDescripcion("Vivamus lacus lorem, euismod vitae lobortis vitae, varius a mi. Ut rhoncus tellus id vehicula facilisis. Maecenas nunc metus, sagittis ut urna quis, faucibus feugiat nisi. In hac habitasse platea dictumst. ");
        tp5.setPracticaOdontologica(practicaOdontologicasList.get(1));

        trabajoPracticoList.add(trabajoPracticoService.create(tp5));

        TrabajoPractico tp6 = new TrabajoPractico();
        tp6.setNombre("Duis sapien sapien");
        tp6.setDescripcion("Duis sapien sapien, tincidunt ut viverra at, tempor nec nisl. Nullam vel massa nec urna consectetur ultricies ut non nunc. Morbi vitae aliquet turpis");
        tp6.setPracticaOdontologica(practicaOdontologicasList.get(3));

        trabajoPracticoList.add(trabajoPracticoService.create(tp6));
        log.info("CargarTrabajoPractico FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarCatedra() throws SAPOException {
        log.info("CargarCatedra INICIO");
        Catedra c1 = new Catedra();
        c1.setDenominacion("A");
        c1.setMateria(materiaList.get(0));
        c1.addHorario(new DiaHorario(Dia.LUNES, Calendar.getInstance(), Calendar.getInstance()));
        c1.addHorario(new DiaHorario(Dia.MARTES, Calendar.getInstance(), Calendar.getInstance()));
        c1.addTrabajoPractico(trabajoPracticoList.get(0));
        c1.addTrabajoPractico(trabajoPracticoList.get(1));
        catedraList.add(catedraService.save(c1));

        Catedra c2 = new Catedra();
        c2.setDenominacion("A");
        c2.setMateria(materiaList.get(1));
        c2.addHorario(new DiaHorario(Dia.MIERCOLES, Calendar.getInstance(), Calendar.getInstance()));
        c2.addHorario(new DiaHorario(Dia.JUEVES, Calendar.getInstance(), Calendar.getInstance()));
        c2.addTrabajoPractico(trabajoPracticoList.get(2));
        c2.addTrabajoPractico(trabajoPracticoList.get(3));
        catedraList.add(catedraService.save(c2));
        log.info("CargarCatedra FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarGrupoFuncionalidad() throws SAPOException {
        log.info("CargarGrupoFuncionalidad INICIO");
        GrupoFuncionalidad soporte = new GrupoFuncionalidad();
        soporte.setNombre("Soporte");
        grupoFuncionalidadList.add(grupoFuncionalidadService.save(soporte));

        GrupoFuncionalidad pacientes = new GrupoFuncionalidad();
        pacientes.setNombre("Pacientes");
        grupoFuncionalidadList.add(grupoFuncionalidadService.save(pacientes));

        GrupoFuncionalidad estadisticas = new GrupoFuncionalidad();
        estadisticas.setNombre("Análisis de datos");
        grupoFuncionalidadList.add(grupoFuncionalidadService.save(estadisticas));

        GrupoFuncionalidad ayuda = new GrupoFuncionalidad();
        ayuda.setNombre("Ayuda");
        grupoFuncionalidadList.add(grupoFuncionalidadService.save(ayuda));

        log.info("CargarGrupoFuncionalidad FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarFuncionalidad() throws SAPOException {
        log.info("CargarFuncionalidad INICIO");
        Funcionalidad f1 = new Funcionalidad();
        f1.setNombre("Materia");
        f1.setEstadoAsociado("materia.index");
        f1.setGrupoFuncionalidad(grupoFuncionalidadList.get(0));
        funcionalidadList.add(funcionalidadService.save(f1));

        Funcionalidad f2 = new Funcionalidad();
        f2.setNombre("Práctica odontológica");
        f2.setEstadoAsociado("practicaOdontologica.index");
        f2.setGrupoFuncionalidad(grupoFuncionalidadList.get(0));
        funcionalidadList.add(funcionalidadService.save(f2));

        Funcionalidad f3 = new Funcionalidad();
        f3.setNombre("Trabajo práctico");
        f3.setEstadoAsociado("trabajoPractico.index");
        f3.setGrupoFuncionalidad(grupoFuncionalidadList.get(0));
        funcionalidadList.add(funcionalidadService.save(f3));

        Funcionalidad f4 = new Funcionalidad();
        f4.setNombre("Catedra");
        f4.setEstadoAsociado("catedra.index");
        f4.setGrupoFuncionalidad(grupoFuncionalidadList.get(0));
        funcionalidadList.add(funcionalidadService.save(f4));

        Funcionalidad f5 = new Funcionalidad();
        f5.setNombre("Usuario");
        f5.setEstadoAsociado("usuario.index");
        f5.setGrupoFuncionalidad(grupoFuncionalidadList.get(0));
        funcionalidadList.add(funcionalidadService.save(f5));

        Funcionalidad f6 = new Funcionalidad();
        f6.setNombre("Paciente");
        f6.setEstadoAsociado("paciente.index");
        f6.setGrupoFuncionalidad(grupoFuncionalidadList.get(1));
        funcionalidadList.add(funcionalidadService.save(f6));

        Funcionalidad f7 = new Funcionalidad();
        f7.setNombre("Reportes y estadísticas");
        f7.setEstadoAsociado("estadisticas");
        f7.setGrupoFuncionalidad(grupoFuncionalidadList.get(2));
        funcionalidadList.add(funcionalidadService.save(f7));

        log.info("CargarFuncionalidad FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarPrivilegio() throws SAPOException {
        log.info("CargarPrivilegio INICIO");
        for(Funcionalidad f: funcionalidadList) {
            Privilegio p = new Privilegio();
            p.setFuncionalidad(f);
            p.setEsItemMenu(true);
            p.setRol(rolList.get(0));
            privilegioList.add(privilegioService.save(p));
        }
        log.info("CargarPrivilegio FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarRoles() throws SAPOException {
        log.info("CargarRoles INICIO");
        Rol administrador = new Rol();
        administrador.setNombre(RolEnum.ADMINISTRADOR);
        administrador.setDescripcion("Perfil técnico. Tiene acceso a todo el sistema.");
        rolList.add(rolService.create(administrador));

        Rol alumno = new Rol();
        alumno.setNombre(RolEnum.ALUMNO);
        alumno.setDescripcion("Estudiante. Puede buscar pacientes, asignárselos y registrar atenciones.");
        rolList.add(rolService.create(alumno));

        Rol profesor = new Rol();
        profesor.setNombre(RolEnum.PROFESOR);
        profesor.setDescripcion("Profesor de la facultad. Puede ver atenciones hechas por alumnos y deberá autorizar previamente.");
        rolList.add(rolService.create(profesor));

        Rol responsable = new Rol();
        responsable.setNombre(RolEnum.RESPONSABLE_RECEPCION_PACIENTES);
        responsable.setDescripcion("Odontólogo matriculado. Recibe los pacientes, los registra y diagnostica.");
        rolList.add(rolService.create(responsable));

        Rol adminAcademico = new Rol();
        adminAcademico.setNombre(RolEnum.ADMINISTRADOR_ACADEMICO);
        adminAcademico.setDescripcion("Perfil académico. Se encarga de materias, trabajos prácticos, cátedras, etc.");
        rolList.add(rolService.create(adminAcademico));

        Rol autoridad = new Rol();
        autoridad.setNombre(RolEnum.AUTORIDAD);
        autoridad.setDescripcion("Autoridad de la facultad. Perfil de \"alto nivel\", puede ver estadísticas y reportes.");
        rolList.add(rolService.create(autoridad));

        log.info("CargarRoles FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarHistoriasClinicas() throws SAPOException {
        log.info("CARGA HISTORIAS CLINICAS INICIO");
        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int numero = r.nextInt(10000)+1;  // Entre 0 y 5, más 1.
            HistoriaClinica hc = HistoriaClinica.createDefault();
            hc.setNumero(numero);

            for(DetalleHistoriaClinica detalle : hc.getDetallesHC()){
                if(detalle instanceof CampoDetalle){
                    String randomString = StringRandomizer.aNewStringRandomizer().getRandomValue();
                    ((CampoDetalle) detalle).setOnly_detalle(randomString);
                } else if(detalle instanceof CampoSiNo){
                    Boolean randomBoolean = BooleanRandomizer.aNewBooleanRandomizer().getRandomValue();
                    ((CampoSiNo) detalle).setSiNo(true);
                } else if (detalle instanceof CampoFecha){
                    Calendar fecha = CalendarRandomizer.aNewCalendarRandomizer().getRandomValue();
                    ((CampoFecha) detalle).setFecha(fecha);
                } else if (detalle instanceof CampoEnumerable){
                    Boolean randomBoolean = BooleanRandomizer.aNewBooleanRandomizer().getRandomValue();
                    ((CampoEnumerable) detalle).setChecked(randomBoolean);
                }
            }
            historiaClinicas.add(historiaClinicaService.save(hc));
        }
        log.info("CARGA HISTORIAS CLINICAS FIN");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarPacientes() throws SAPOException {
        log.info("CARGA PACIENTES INICIO");
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .scanClasspathForConcreteTypes(true)
                .build();
        for (int i = 0; i < 10; i++) {
            Paciente p = enhancedRandom.nextObject(Paciente.class);
            p.setHistoriaClinica(historiaClinicas.get(i));
            pacientes.add(pacienteService.save(p));
        }
        log.info("CARGA PACIENTES FIN");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarUsuarios() throws SAPOException {
        log.info("CargarUsuariosPersonas INICIO");
        createUsuarioYPersona("admin", "Enzo", "Biancato", rolList.get(0), new Administrador());
        Alumno alumno = new Alumno();
        alumno.setLegajo("1233456");
        createUsuarioYPersona("alumno", "Ignacio", "López Arzuaga", rolList.get(1), alumno);
        Profesor profesor = new Profesor();
        profesor.setLegajo(1234);
        profesor.setProfesion("Odontólogo");
        createUsuarioYPersona("profesor", "Alexis", "Spesot", rolList.get(2), profesor);
        createUsuarioYPersona("responsable", "Maximiliano", "Barros", rolList.get(3), new ResponsableRecepcion());
        createUsuarioYPersona("adminAcademico", "Mauro", "Barros", rolList.get(4), new AdministradorAcademico());
        Autoridad autoridad = new Autoridad();
        autoridad.setCargo(Cargo.DECANO);
        createUsuarioYPersona("autoridad", "La vieja", "Savi", rolList.get(5), autoridad);
        log.info("CargarUsuariospersonas FINAL");
    }

    private void createUsuarioYPersona(String nombreUsuario, String nombre, String apellido, Rol rol, Persona persona) throws SAPOException {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasenia(EncryptionUtils.encryptMD5A("123"));
        usuario.setEmail(RandomStringUtils.randomAlphabetic(10) + "@domain.com");
        usuario.setRol(rol);
        usuarioService.create(usuario);

        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setFechaCarga(Calendar.getInstance());
        persona.setDocumento(new Documento(RandomStringUtils.randomNumeric(8), TipoDocumento.DNI));
        persona.setUsuario(usuario);
        personaService.create(persona);

        log.info("CargarPersonaAUsuario FINAL");
    }

    private void cargarAlumnos() throws SAPOException {
        log.info("CARGA ALUMNOS INICIO");
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .scanClasspathForConcreteTypes(true)
                .build();
        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int numero = r.nextInt(100000000)+1;  // Entre 0 y 5, más 1.
            Alumno a = enhancedRandom.nextObject(Alumno.class);
            a.setLegajo(numero + "");
            alumnoService.save(a);
        }
        log.info("CARGA ALUMNOS FIN");

    }
}







