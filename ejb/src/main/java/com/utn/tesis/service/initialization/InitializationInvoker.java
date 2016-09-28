package com.utn.tesis.service.initialization;

import com.utn.tesis.exception.SAPOException;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Slf4j
@Stateless
public class InitializationInvoker {
    @Inject
    private InitializationService initializationService;
    private InitVariables initVariables = InitVariables.getInstance();

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public boolean initializeData() throws SAPOException {
        if (!initVariables.isInitializationRunned()) {
            try {
                //Materia
                initializationService.cargarMaterias();
                initializationService.cargarGrupoPracticaOdontologica();
                initializationService.cargarPracticaOdontologica();
                initializationService.cargarTrabajoPracticos();
                initializationService.cargarCatedra();

                //Perfiles
                initializationService.cargarRoles();
                initializationService.cargarGrupoFuncionalidad();
                initializationService.cargarFuncionalidad();
                initializationService.cargarPrivilegio();

                //Usuarios y personas
                initializationService.cargarUsuarios(); //admin enzo
                initializationService.cargarAlumnos();
                initializationService.cargarProfesores();
                initializationService.cargarMovimientos();
                initializationService.cargarDiagnosticos();
//                cargarMovimientos();
                initializationService.cargarHistoriasClinicas();
                initializationService.cargarPacientes();
                initializationService.cargarAsignaciones();

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
}
