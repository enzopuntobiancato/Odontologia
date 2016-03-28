package com.utn.tesis.service.initialization;

import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.*;
import com.utn.tesis.service.*;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;

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
    @Inject
    private GrupoFuncionalidadService grupoFuncionalidadService;
    @Inject
    private FuncionalidadService funcionalidadService;
    @Inject
    private PrivilegioService privilegioService;
    @Inject
    private CatedraService catedraService;


    private ArrayList<GrupoPracticaOdontologica> grupoPracticaOdontologicasList = new ArrayList<GrupoPracticaOdontologica>();
    private ArrayList<PracticaOdontologica> practicaOdontologicasList = new ArrayList<PracticaOdontologica>();
    private ArrayList<GrupoFuncionalidad> grupoFuncionalidadList = new ArrayList<GrupoFuncionalidad>();
    private ArrayList<Funcionalidad> funcionalidadList = new ArrayList<Funcionalidad>();
    private ArrayList<Privilegio> privilegioList = new ArrayList<Privilegio>();
    private ArrayList<Rol> rolList = new ArrayList<Rol>();
    private ArrayList<Materia> materiaList = new ArrayList<Materia>();
    private ArrayList<TrabajoPractico> trabajoPracticoList = new ArrayList<TrabajoPractico>();
    private ArrayList<Catedra> catedraList = new ArrayList<Catedra>();


    public boolean initializeData() throws SAPOException {
        try {

        //Materia
        this.cargarMaterias();
        this.cargarGrupoPracticaOdontologica();
        this.cargarPracticaOdontologica();
        this.cargarTrabajoPracticos();
        this.cargarCatedra();

        //Perfiles
        this.cargarGrupoFuncionalidad();
        this.cargarFuncionalidad();
        this.cargarPrivilegio();
        this.cargarRoles();

        //Usuarios y personas
        this.cargarUsuarios(); //admin enzo
        this.cargarPersonaAUsuario();

            return true;
        } catch (SAPOException e) {
            log.error("EXCEPCION: " + e.getException());
            log.error("EXCEPCION: " + e.getMessage());
            throw new SAPOException(e);
        }
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
        c1.addHorarios(new DiaHorario(Dia.LUNES, Calendar.getInstance(), Calendar.getInstance()));
        c1.addHorarios(new DiaHorario(Dia.MARTES, Calendar.getInstance(), Calendar.getInstance()));
        c1.addTrabajoPractico(trabajoPracticoList.get(0));
        c1.addTrabajoPractico(trabajoPracticoList.get(1));
        catedraList.add(catedraService.save(c1));

        Catedra c2 = new Catedra();
        c2.setDenominacion("A");
        c2.setMateria(materiaList.get(1));
        c2.addHorarios(new DiaHorario(Dia.MIERCOLES, Calendar.getInstance(), Calendar.getInstance()));
        c2.addHorarios(new DiaHorario(Dia.JUEVES, Calendar.getInstance(), Calendar.getInstance()));
        c2.addTrabajoPractico(trabajoPracticoList.get(2));
        c2.addTrabajoPractico(trabajoPracticoList.get(3));
        catedraList.add(catedraService.save(c2));
        log.info("CargarCatedra FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarGrupoFuncionalidad() throws SAPOException {
        log.info("CargarGrupoFuncionalidad INICIO");
        GrupoFuncionalidad gf = new GrupoFuncionalidad();
        gf.setNombre("Soporte");

        grupoFuncionalidadList.add(grupoFuncionalidadService.save(gf));
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
        f2.setNombre("Practica odontologica");
        f2.setEstadoAsociado("practicaOdontologica.index");
        f2.setGrupoFuncionalidad(grupoFuncionalidadList.get(0));
        funcionalidadList.add(funcionalidadService.save(f2));

        Funcionalidad f3 = new Funcionalidad();
        f3.setNombre("Trabajo practico");
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
        log.info("CargarFuncionalidad FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cargarPrivilegio() throws SAPOException {
        log.info("CargarPrivilegio INICIO");
        for(Funcionalidad f: funcionalidadList) {
            Privilegio p = new Privilegio();
            p.setFuncionalidad(f);
            p.setEsItemMenu(true);
            privilegioList.add(privilegioService.save(p));
        }
        log.info("CargarPrivilegio FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarRoles() throws SAPOException {
        log.info("CargarRoles INICIO");
        Rol rol = new Rol();
        rol.setNombre(Rol.ADMIN);
        rol.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
        rol.addPrivilegio(privilegioList.get(0));//materia
        rol.addPrivilegio(privilegioList.get(1));//Practica odontologica
        rol.addPrivilegio(privilegioList.get(2));//trabajo practico
        rol.addPrivilegio(privilegioList.get(3));//Catedra
        rol.addPrivilegio(privilegioList.get(4));//usuario
        rolList.add(rolService.create(rol));
        /*
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


        rolList.add(rolService.create(rol1));
        rolList.add(rolService.create(rol2));
        rolList.add(rolService.create(rol3));
        rolList.add(rolService.create(rol4));
        rolList.add(rolService.create(rol5));*/
        log.info("CargarRoles FINAL");
    }









    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarUsuarios() throws SAPOException {
        log.info("CargarUsuarios INICIO");
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("enzo");
        usuario.setContrasenia("123");
        usuario.setEmail("enzo@enzo.com");
        usuario.setRol(rolList.get(0));
        usuarioService.create(usuario);
        log.info("CargarUsuarios FINAL");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void cargarPersonaAUsuario() throws SAPOException {
        log.info("CargarPersonaAUsuario INICIO");
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

//        personaService.create(persona2);
//
//        Alumno persona3 = new Alumno();
//        persona3.setNombre("Carlos");
//        persona3.setApellido("Biancato");
//        persona3.setFechaCarga(Calendar.getInstance());
//        persona3.setDocumento(new Documento("11116591", TipoDocumento.DNI));
//        persona3.setUsuario(usuario2);
//        persona3.setLegajo("54452");
//
//        personaService.create(persona3);
        log.info("CargarPersonaAUsuario FINAL");
    }

}







