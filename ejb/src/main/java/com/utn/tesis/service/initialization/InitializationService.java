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
import java.util.*;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Slf4j
public class InitializationService {
    private static final int MATERIAS_IDX = 0;
    private static final int PRACTICAS_IDX = 1;
    private static final int TPS_IDX = 2;
    private static final int CATEDRAS_IDX = 3;
    private static final int CONS_INFO_TP_IDX = 4;
    private static final int ASIGNAR_PROF_CAT_IDX = 5;
    private static final int USUARIOS_IDX = 6;
    private static final int RESPALDO_IDX = 7;
    private static final int CONS_ASIG_IDX = 8;
    private static final int AUTORIZAR_ASIG_IDX = 9;
    private static final int CONS_PACIENTES_IDX = 10;
    private static final int BONO_IDX = 11;
    private static final int REG_ALUMNO_IDX = 12;
    private static final int CONS_ALUMNOS_IDX = 13;
    private static final int ESTADISTICAS_IDX = 14;
    private static final int PERMISOS_IDX = 15;

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
    @Inject
    private AsignacionPacienteService asignacionPacienteService;
    @Inject
    private DiagnosticoService diagnosticoService;
    @Inject
    private ProfesorService profesorService;
    @Inject
    private MovimientoAsignacionPacienteService movimientoAsignacionPacienteService;
    @Inject
    private MovimientoDiagnosticoService movimientoDiagnosticoService;

    private ArrayList<GrupoPracticaOdontologica> grupoPracticaOdontologicasList = new ArrayList<GrupoPracticaOdontologica>();
    private ArrayList<PracticaOdontologica> practicaOdontologicasList = new ArrayList<PracticaOdontologica>();
    private ArrayList<GrupoFuncionalidad> grupoFuncionalidadList = new ArrayList<GrupoFuncionalidad>();
    private ArrayList<Funcionalidad> funcionalidadList = new ArrayList<Funcionalidad>();
    private ArrayList<Rol> rolList = new ArrayList<Rol>();
    private ArrayList<Materia> materiaList = new ArrayList<Materia>();
    private ArrayList<TrabajoPractico> trabajoPracticoList = new ArrayList<TrabajoPractico>();
    private ArrayList<Catedra> catedraList = new ArrayList<Catedra>();
    private ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
    private ArrayList<HistoriaClinica> historiaClinicas = new ArrayList<HistoriaClinica>();
    private ArrayList<Diagnostico> diagnosticos = new ArrayList<Diagnostico>();
    private List<MovimientoAsignacionPaciente> movimientoAsignacionPacientes = new ArrayList<MovimientoAsignacionPaciente>();
    private List<AsignacionPaciente> asignaciones;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<AsignacionPaciente> realoadAll(List<AsignacionPaciente> asignaciones) {
        log.error("inicio reload");
        Usuario u = usuarioService.findAll().get(0);
        u = usuarioService.reload(u, 2);

        AsignacionPaciente asignacionPaciente = asignacionPacienteService.findById(1l);
        asignaciones = asignacionPacienteService.reloadList(asignaciones, 2);
        asignacionPaciente = asignacionPacienteService.reload(asignacionPaciente, 2);
        log.error("fin reload");
        return asignaciones;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarMaterias() throws SAPOException {
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
    public void cargarGrupoPracticaOdontologica() throws SAPOException {
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
    public void cargarPracticaOdontologica() throws SAPOException {
        log.info("CargarPracticaOdontologica INICIO");
        practicaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "EXAMEN DIAGNÓSTICO, FICHADO Y PLAN DE TRATAMIENTO", "Se considera primera consulta al examen, diagnóstico, fichado y plan de tratamiento. Como consecuencia del exámen, el fichado deberá reflejar el estado actual de la boca, previo al tratamiento.")));
        practicaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "VISITA A DOMICILIO", "Se considera consulta domiciliaria a la atención de pacientes impedidos de trasladarse al consultorio del odontólogo.")));
        practicaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "CONSULTA DE URGENCIA", "Se considera consulta de urgencia a toda prestación que no constituye paso intermedio y/o final de tratamiento. Importante establecer el motivo de la misma.")));
        practicaOdontologicasList.add(practicaOdontologicaService.create(new PracticaOdontologica(grupoPracticaOdontologicasList.get(1), "OBTURACIÓN CON AMALGAMA - CAVIDAD SIMPLE", "Se reconocerá como obturación simple de amalgama a aquellas en las que se haya practicado un adecuado tallado de la cavidad.")));
        log.info("CargarPracticaOdontologica FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarTrabajoPracticos() throws SAPOException {
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
    public void cargarCatedra() throws SAPOException {
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
        c2.setDenominacion("B");
        c2.setMateria(materiaList.get(1));
        c2.addHorario(new DiaHorario(Dia.MIERCOLES, Calendar.getInstance(), Calendar.getInstance()));
        c2.addHorario(new DiaHorario(Dia.JUEVES, Calendar.getInstance(), Calendar.getInstance()));
        c2.addTrabajoPractico(trabajoPracticoList.get(2));
        c2.addTrabajoPractico(trabajoPracticoList.get(3));
        catedraList.add(catedraService.save(c2));

        Catedra c3 = new Catedra();
        c2.setDenominacion("C");
        c2.setMateria(materiaList.get(1));
        c2.addHorario(new DiaHorario(Dia.MIERCOLES, Calendar.getInstance(), Calendar.getInstance()));
        c2.addHorario(new DiaHorario(Dia.JUEVES, Calendar.getInstance(), Calendar.getInstance()));
        c2.addTrabajoPractico(trabajoPracticoList.get(4));
        c2.addTrabajoPractico(trabajoPracticoList.get(5));
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

        GrupoFuncionalidad asignaciones = new GrupoFuncionalidad();
        asignaciones.setNombre("Asignaciones de paciente");
        grupoFuncionalidadList.add(grupoFuncionalidadService.save(asignaciones));

        GrupoFuncionalidad datosAcademicos = new GrupoFuncionalidad();
        datosAcademicos.setNombre("Datos académicos");
        grupoFuncionalidadList.add(grupoFuncionalidadService.save(datosAcademicos));

        GrupoFuncionalidad alumnos = new GrupoFuncionalidad();
        alumnos.setNombre("Alumnos");
        grupoFuncionalidadList.add(grupoFuncionalidadService.save(alumnos));

        log.info("CargarGrupoFuncionalidad FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarFuncionalidad() throws SAPOException {
        log.info("CargarFuncionalidad INICIO");
        GrupoFuncionalidad soporte = grupoFuncionalidadList.get(0);
        GrupoFuncionalidad pacientes = grupoFuncionalidadList.get(1);
        GrupoFuncionalidad asignaciones = grupoFuncionalidadList.get(2);
        GrupoFuncionalidad datosAcademicos = grupoFuncionalidadList.get(3);
        GrupoFuncionalidad alumnos = grupoFuncionalidadList.get(4);

        // DATOS ACADEMICOS
        addFuncionalidad("Materias", "materia.index", datosAcademicos, true, MATERIAS_IDX);
        addFuncionalidad("Prácticas odontológicas", "practicaOdontologica.index", datosAcademicos, true, PRACTICAS_IDX);
        addFuncionalidad("Trabajos prácticos", "trabajoPractico.index", datosAcademicos, true, TPS_IDX);
        addFuncionalidad("Cátedras", "catedra.index", datosAcademicos, true, CATEDRAS_IDX);
        addFuncionalidad("Consultar información de trabajos prácticos", "", datosAcademicos, true, CONS_INFO_TP_IDX);
        addFuncionalidad("Asignar profesores a cátedras", "profesor.editCatedrasProfesor", datosAcademicos, true, ASIGNAR_PROF_CAT_IDX);

        // SOPORTE
        addFuncionalidad("Usuarios", "usuario.index", soporte, true, USUARIOS_IDX);
        addFuncionalidad("Respaldo de datos de sistema", "", soporte, true, RESPALDO_IDX);

        // ASIGNACIONES
        addFuncionalidad("Consultar asignaciones", "asignacion.index", asignaciones, true, CONS_ASIG_IDX);
        addFuncionalidad("Autorizar asignaciones", "asignacion.autorizar", asignaciones, true, AUTORIZAR_ASIG_IDX);

        // PACIENTES
        addFuncionalidad("Consultar pacientes", "paciente.index", pacientes, true, CONS_PACIENTES_IDX);
        addFuncionalidad("Emitir bono de consulta", "", pacientes, true, BONO_IDX);

        // ALUMNOS
        addFuncionalidad("Registrar alumno", "usuario.registerAlumno", alumnos, true, REG_ALUMNO_IDX);
        addFuncionalidad("Consultar alumnos", "", alumnos, true, CONS_ALUMNOS_IDX);

        // ESTADISTICOAS
        addFuncionalidad("Módulo de estadísticas", "estadisticas", null, true, ESTADISTICAS_IDX);
        addFuncionalidad("Administrar permisos de rol", "permisos.administrate", soporte, true, PERMISOS_IDX);
        log.info("CargarFuncionalidad FINAL");
    }

    private void addFuncionalidad(String nombre, String estadoAsociado, GrupoFuncionalidad grupo, boolean isItemMenu, int idx) throws SAPOException {
        Funcionalidad f = new Funcionalidad();
        f.setNombre(nombre);
        f.setEstadoAsociado(estadoAsociado);
        f.setGrupoFuncionalidad(grupo);
        f.setEsItemMenu(isItemMenu);
        funcionalidadList.add(idx, funcionalidadService.save(f));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarPrivilegio() throws SAPOException {
        log.info("CargarPrivilegio INICIO");
        Rol administrador = rolList.get(0);
        Rol alumno = rolList.get(1);
        Rol profesor = rolList.get(2);
        Rol rrp = rolList.get(3);
        Rol adminAcademico = rolList.get(4);
        Rol autoridad = rolList.get(5);

        // ADMINISTRADOR
        for (Funcionalidad f : funcionalidadList) {
            addPrivilegio(f, administrador);
        }

        // ALUMNO
        addPrivilegio(funcionalidadList.get(CONS_ASIG_IDX), alumno);
        addPrivilegio(funcionalidadList.get(CONS_INFO_TP_IDX), alumno);

        // PROFESOR
        addPrivilegio(funcionalidadList.get(AUTORIZAR_ASIG_IDX), profesor);
        addPrivilegio(funcionalidadList.get(CONS_ASIG_IDX), profesor);
        addPrivilegio(funcionalidadList.get(CONS_ALUMNOS_IDX), profesor);
        addPrivilegio(funcionalidadList.get(CONS_PACIENTES_IDX), profesor);
        addPrivilegio(funcionalidadList.get(CONS_INFO_TP_IDX), profesor);
        addPrivilegio(funcionalidadList.get(ESTADISTICAS_IDX), profesor);

        // RESPONSABLE DE RECEPCION
        addPrivilegio(funcionalidadList.get(CONS_PACIENTES_IDX), rrp);
        addPrivilegio(funcionalidadList.get(CONS_ASIG_IDX), rrp);
        addPrivilegio(funcionalidadList.get(CONS_INFO_TP_IDX), rrp);
        addPrivilegio(funcionalidadList.get(REG_ALUMNO_IDX), rrp);
        addPrivilegio(funcionalidadList.get(BONO_IDX), rrp);

        // ADMINISTRADOR ACADÉMICO
        addPrivilegio(funcionalidadList.get(MATERIAS_IDX), adminAcademico);
        addPrivilegio(funcionalidadList.get(PRACTICAS_IDX), adminAcademico);
        addPrivilegio(funcionalidadList.get(TPS_IDX), adminAcademico);
        addPrivilegio(funcionalidadList.get(CATEDRAS_IDX), adminAcademico);
        addPrivilegio(funcionalidadList.get(ASIGNAR_PROF_CAT_IDX), adminAcademico);

        // AUTORIDAD
        addPrivilegio(funcionalidadList.get(ESTADISTICAS_IDX), autoridad);
        addPrivilegio(funcionalidadList.get(CONS_INFO_TP_IDX), autoridad);
        addPrivilegio(funcionalidadList.get(CONS_ALUMNOS_IDX), autoridad);
        log.info("CargarPrivilegio FINAL");
    }

    private void addPrivilegio(Funcionalidad f, Rol rol) throws SAPOException {
        Privilegio privilegio = new Privilegio(f, rol);
        privilegioService.save(privilegio);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarRoles() throws SAPOException {
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
    public void cargarDiagnosticos() throws SAPOException {
        log.info("COMIENZO CARGA DE DIAGNOSTICOS");
        List<PracticaOdontologica> practicaOdontologicas = practicaOdontologicaService.findAll();
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .scanClasspathForConcreteTypes(true)
                .build();
        List<MovimientoDiagnostico> movimientos = movimientoDiagnosticoService.findAll();
        for (int i = 0; i < 60; i++) {
            Random random = new Random();
            int numero = random.nextInt(3 - 0 + 1) + 0;
            MovimientoDiagnostico movD = movimientos.get(i);
            List<MovimientoDiagnostico> movs = new ArrayList<MovimientoDiagnostico>();
            movs.add(movD);
            Diagnostico diagnostico = enhancedRandom.nextObject(Diagnostico.class);
            diagnostico.setId(null);
            diagnostico.setVersion(null);
            diagnostico.setMovimientos(movs);
            diagnostico.setUltimoMovimiento(movD);
            diagnostico.setPracticaOdontologica(practicaOdontologicas.get(numero));
            diagnosticos.add(diagnosticoService.save(diagnostico));
        }

        log.info("FIN CARGA DE DIAGNOSTICOS");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarHistoriasClinicas() throws SAPOException {
        log.info("CARGA HISTORIAS CLINICAS INICIO");

        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int numero = r.nextInt(10000) + 1;  // Entre 0 y 5, más 1.
            HistoriaClinica hc = HistoriaClinica.createDefault();
            hc.setNumero(numero);

            for (DetalleHistoriaClinica detalle : hc.getDetallesHC()) {
                if (detalle instanceof CampoDetalle) {
                    String randomString = StringRandomizer.aNewStringRandomizer().getRandomValue();
                    ((CampoDetalle) detalle).setOnly_detalle(randomString);
                } else if (detalle instanceof CampoSiNo) {
                    Boolean randomBoolean = BooleanRandomizer.aNewBooleanRandomizer().getRandomValue();
                    ((CampoSiNo) detalle).setSiNo(true);
                } else if (detalle instanceof CampoFecha) {
                    Calendar fecha = CalendarRandomizer.aNewCalendarRandomizer().getRandomValue();
                    ((CampoFecha) detalle).setFecha(fecha);
                } else if (detalle instanceof CampoEnumerable) {
                    Boolean randomBoolean = BooleanRandomizer.aNewBooleanRandomizer().getRandomValue();
                    ((CampoEnumerable) detalle).setChecked(randomBoolean);
                }
            }
            List<Diagnostico> diagnosticoList = diagnosticoService.findByFilters(null, null, null, new Long(i), 1L);
//            List<Diagnostico> diagnosticoList2 = diagnosticoList.subList(2 * i, (2 * i + 2));
            hc.setDiagnostico(diagnosticoList);
            historiaClinicas.add(historiaClinicaService.save(hc));
        }
        log.info("CARGA HISTORIAS CLINICAS FIN");

        List<HistoriaClinica> listHc = historiaClinicaService.findAll();
        log.info("Historias clinicas traidas de BD");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarPacientes() throws SAPOException {
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
    public void cargarUsuarios() throws SAPOException {
        log.info("CargarUsuariosPersonas INICIO");
        createUsuarioYPersona("admin", "Enzo", "Biancato", rolList.get(0), new Administrador(), false);
        Alumno alumno = new Alumno();
        alumno.setLegajo("1233456");
        createUsuarioYPersona("alumno", "Ignacio", "López Arzuaga", rolList.get(1), alumno, true);

        Profesor profesor = new Profesor();
        profesor.setProfesion("Odontólogo");
        profesor.setCatedras(Arrays.asList(catedraList.get(0)));
        createUsuarioYPersona("profesor", "Alexis", "Spesot", rolList.get(2), profesor, true);

        Profesor profesor2 = new Profesor();
        profesor2.setProfesion("Odontólogo");
        createUsuarioYPersona("profesor2", "Juan", "Pérez", rolList.get(2), profesor2, true);

        Profesor profesor3 = new Profesor();
        profesor3.setProfesion("Odontólogo");
        createUsuarioYPersona("profesor3", "Pedro", "Martinez", rolList.get(2), profesor3, true);

        Profesor profesor4 = new Profesor();
        profesor4.setProfesion("Odontólogo");
        createUsuarioYPersona("profesor4", "Santiago", "Spinosa", rolList.get(2), profesor4, true);

        Profesor profesor5 = new Profesor();
        profesor5.setProfesion("Odontólogo");
        createUsuarioYPersona("profesor5", "Alexis", "Spesot", rolList.get(2), profesor5, true);

        Profesor profesor6 = new Profesor();
        profesor6.setProfesion("Odontólogo");
        createUsuarioYPersona("profesor6", "Alexis", "Spesot", rolList.get(2), profesor6, true);

        Profesor profesor7 = new Profesor();
        profesor7.setProfesion("Odontólogo");
        createUsuarioYPersona("profesor7", "Agustin", "Cardeilac", rolList.get(2), profesor7, true);

        createUsuarioYPersona("responsable", "Maximiliano", "Barros", rolList.get(3), new ResponsableRecepcion(), true);
        createUsuarioYPersona("adminAcademico", "Mauro", "Barros", rolList.get(4), new AdministradorAcademico(), true);
        Autoridad autoridad = new Autoridad();
        autoridad.setCargo(Cargo.DECANO);
        createUsuarioYPersona("autoridad", "Una", "Autoridad", rolList.get(5), autoridad, true);
        log.info("CargarUsuariospersonas FINAL");
    }

    private void createUsuarioYPersona(String nombreUsuario, String nombre, String apellido, Rol rol, Persona persona, boolean avoidFirstLogin) throws SAPOException {
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setFechaCarga(Calendar.getInstance());
        persona.setDocumento(new Documento(RandomStringUtils.randomNumeric(8), TipoDocumento.DNI));
        if (avoidFirstLogin) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1989, 10, 9);
            persona.setFechaNacimiento(calendar);
            persona.setSexo(Sexo.MASCULINO);
        }
        personaService.create(persona);

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasenia(EncryptionUtils.encryptMD5A("123"));
        usuario.setEmail(RandomStringUtils.randomAlphabetic(10) + "@domain.com");
        if (avoidFirstLogin) {
            usuario.setUltimaConexion(Calendar.getInstance());
        }
        RolUsuario rolUsuario = new RolUsuario();
        rolUsuario.setRol(rol);
        rolUsuario.setPersona(persona);
        usuario.getRoles().add(rolUsuario);
        Profesor profesor = null;
        if (rol.getNombre() == RolEnum.ADMINISTRADOR) {
            RolUsuario rolUsuario2 = new RolUsuario();
            rolUsuario2.setRol(rolList.get(2));
            profesor = new Profesor();
            profesor.setNombre(nombre);
            profesor.setApellido(apellido);
            profesor.setFechaCarga(Calendar.getInstance());
            profesor.setDocumento(new Documento(RandomStringUtils.randomNumeric(8), TipoDocumento.DNI));
            if (avoidFirstLogin) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(1989, 10, 9);
                profesor.setFechaNacimiento(calendar);
                profesor.setSexo(Sexo.MASCULINO);
            }
            personaService.create(profesor);
            rolUsuario2.setPersona(profesor);
            usuario.getRoles().add(rolUsuario2);
        }
        usuarioService.create(usuario);
        persona.setUsuario(usuario);
        if (profesor != null) {
            profesor.setUsuario(usuario);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarProfesores() throws SAPOException{
        log.info("CARGA PROFESORES INICIO");
        for(int i = 0; i < 2 ; i++){
            String nombre = StringRandomizer.aNewStringRandomizer().getRandomValue();
            Profesor profesor = new Profesor();
            createUsuarioYPersona(nombre, nombre, nombre + "apellido", rolList.get(2), profesor, true);
        }

        log.info("CARGA PROFESORES FIN");

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarAlumnos() throws SAPOException {
        log.info("CARGA ALUMNOS INICIO");
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .scanClasspathForConcreteTypes(true)
                .build();
        List<Usuario> users = new ArrayList<Usuario>();
        for(int i = 0; i < 10 ; i++){
            String nombre = StringRandomizer.aNewStringRandomizer().getRandomValue();
            Alumno alumno = new Alumno();
            createUsuarioYPersona(nombre, nombre, nombre + "apellido", rolList.get(2), alumno, true);
        }

        log.info("CARGA ALUMNOS FIN");

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarAsignaciones() throws SAPOException {
        log.info("CARGA DE ASIGNACIONES");
        asignaciones = new ArrayList<AsignacionPaciente>();
        List<Alumno> alumnos = alumnoService.findByFilters(null, null, null, null, null, 1L, 5L);
        List<TrabajoPractico> trabajoPracticos = trabajoPracticoService.findAll();
        List<Paciente> pacientesAux = pacienteService.findAll();
        List<Diagnostico> diagnosticoList = diagnosticoService.findAll();
        Usuario usuarioCreador = usuarioService.findAll().get(0);

        int numDiag = 0;
        for (Alumno alumno : alumnos) {
            for (TrabajoPractico trabajoPractico : trabajoPracticos) {
                Random random = new Random();
                int numero = random.nextInt(8 - 0 + 1) + 0;

                List<MovimientoAsignacionPaciente> movimientos = new ArrayList<MovimientoAsignacionPaciente>();

                MovimientoAsignacionPaciente movimientoAsignacionPaciente = new MovimientoAsignacionPaciente(
                        EstadoAsignacionPaciente.PENDIENTE, CalendarRandomizer.aNewCalendarRandomizer().getRandomValue(),
                        usuarioCreador);
                movimientos.add(movimientoAsignacionPaciente);

                AsignacionPaciente asignacionPaciente = new AsignacionPaciente();
                asignacionPaciente.setDiagnostico(diagnosticoList.get(numDiag));
                asignacionPaciente.setPaciente(pacientesAux.get(numero));
                asignacionPaciente.setAlumno(alumno);
                asignacionPaciente.setTrabajoPractico(trabajoPractico);
                asignacionPaciente.setFechaAsignacion(CalendarRandomizer.aNewCalendarRandomizer().getRandomValue());
                asignacionPaciente.setFechaCreacion(CalendarRandomizer.aNewCalendarRandomizer().getRandomValue());
                asignacionPaciente.setMovimientoAsignacionPaciente(movimientos);
                asignacionPaciente.setUltimoMovimiento(movimientoAsignacionPaciente);
                asignaciones.add(asignacionPacienteService.save(asignacionPaciente));
                numDiag++;
            }
        }
        log.info("FIN CARGA DE ASIGNACIONES");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarMovimientos() throws SAPOException {
        log.info("Inicia carga de movimientos");
        Usuario usuarioCreador = usuarioService.findAll().get(0);
        for (int i = 0; i < 60; i++) {
            MovimientoDiagnostico movD = new MovimientoDiagnostico();
            movD.setEstado(EstadoDiagnostico.PENDIENTE);
            movD.setGeneradoPor(usuarioCreador);
            movimientoDiagnosticoService.save(movD);
        }

        log.info("Fin carga de movimientos");
    }
}







