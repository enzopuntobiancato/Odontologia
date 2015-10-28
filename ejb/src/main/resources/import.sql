
-- GRUPOS FUNCIONALIDAD (SERÁN ITEMS DEL MENÚ CON SUS CORRESPONDIENTES SUBITEMS)
insert into GrupoFuncionalidad(id, version, nombre) values (1, 0, 'Soporte');

insert into Funcionalidad(id, version, nombre, estadoAsociado, grupoFuncionalidadId) values (1, 0, 'Materia', 'materia.index', 1);
insert into Funcionalidad(id, version, nombre, estadoAsociado, grupoFuncionalidadId) values (2, 0, 'Practica odontologica', 'practicaOdontologica.index', 1);
insert into Funcionalidad(id, version, nombre, estadoAsociado, grupoFuncionalidadId) values (3, 0, 'Trabajo practico', 'trabajoPractico.index', 1);
insert into Funcionalidad(id, version, nombre, estadoAsociado, grupoFuncionalidadId) values (4, 0, 'Catedra', 'catedra.index', 1);
insert into Funcionalidad(id, version, nombre, estadoAsociado, grupoFuncionalidadId) values (5, 0, 'Usuario', 'usuario.index', 1);

-- ROLES
insert into Rol(id, version, nombre, descripcion, personaAsociada) values (1, 0, 'ADMINISTRADOR', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 'com.utn.tesis.model.Persona');
insert into Rol(id, version, nombre, descripcion, personaAsociada) values (2, 0, 'ALUMNO', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 'com.utn.tesis.model.Alumno');
insert into Rol(id, version, nombre, descripcion, personaAsociada) values (3, 0, 'PROFESOR', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 'com.utn.tesis.model.Profesor');
insert into Rol(id, version, nombre, descripcion, personaAsociada) values (4, 0, 'RESPONSABLE DE RECEPCION DE PACIENTES', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 'com.utn.tesis.model.Responsable');
insert into Rol(id, version, nombre, descripcion, personaAsociada) values (5, 0, 'ADMINISTRADOR ACADEMICO', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 'com.utn.tesis.model.Persona');
insert into Rol(id, version, nombre, descripcion, personaAsociada) values (6, 0, 'PACIENTE', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 'com.utn.tesis.model.Persona');
insert into Rol(id, version, nombre, descripcion, personaAsociada) values (7, 0, 'AUTORIDAD', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua', 'com.utn.tesis.model.Autoridad');

insert into Privilegio(id, version, funcionalidadId, esItemMenu, rol_id) values (1, 0, 1, 1, 1);
insert into Privilegio(id, version, funcionalidadId, esItemMenu, rol_id) values (2, 0, 2, 1, 1);
insert into Privilegio(id, version, funcionalidadId, esItemMenu, rol_id) values (3, 0, 3, 1, 1);
insert into Privilegio(id, version, funcionalidadId, esItemMenu, rol_id) values (4, 0, 4, 1, 1);
insert into Privilegio(id, version, funcionalidadId, esItemMenu, rol_id) values (5, 0, 5, 1, 1);

insert into Usuario(id, version, nombreUsuario, contrasenia, email, estadoAlta,fechaBaja,motivoBaja) values (1, 0, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@admin.com', 1, null, null);
insert into Usuario(id, version, nombreUsuario, contrasenia, email, estadoAlta,fechaBaja,motivoBaja) values (2, 0, 'enzo', '202cb962ac59075b964b07152d234b70', 'enzo@enzo.com', 1, null, null);
insert into usuario_x_rol(usuario_id, rol_id) values (1, 1);
insert into usuario_x_rol(usuario_id, rol_id) values (1, 5);
insert into usuario_x_rol(usuario_id, rol_id) values (2, 1);


-- insert into GrupoPracticaOdontologica(id, version, nombre) values (1, 0, 'CONSULTAS');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (2, 0, 'OPERATORIA DENTAL');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (3, 0, 'ENDODONCIA SIN OBTURACION');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (4, 0, 'PR0TESIS');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (5, 0, 'ODONTOLOGA PREVENTIVA');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (6, 0, 'ORTODONCIA Y ORTOPEDIA FUNCIONAL');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (7, 0, 'ODONTOPEDIATR�A');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (8, 0, 'PERIODONCIA');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (9, 0, 'RADIOLOG�A');
-- insert into GrupoPracticaOdontologica(id, version, nombre) values (10, 0, 'CIRUG�A BUCAL');

-- INSERT INTO PracticaOdontologica (id,version,estadoAlta,fechaBaja,motivoBaja,denominacion,observaciones,grupoId) VALUES (2,0,1,NULL,NULL,'EXAMEN, DIAGN�STICO, FICHADO Y PLAN DE TRATAMIENTO','Se considera primera consulta al examen, diagn�stico, fichado y plan de tratamiento. Como consecuencia del ex�men, el fichado deber� reflejar el estado actual de la boca, previo al tratamiento.',1);
-- INSERT INTO PracticaOdontologica (id,version,estadoAlta,fechaBaja,motivoBaja,denominacion,observaciones,grupoId) VALUES (3,0,1,NULL,NULL,'VISITA A DOMICILIO','Se considera consulta domiciliaria a la atenci�n de pacientes impedidos de trasladarse al consultorio del odont�logo.',1);
-- INSERT INTO PracticaOdontologica (id,version,estadoAlta,fechaBaja,motivoBaja,denominacion,observaciones,grupoId) VALUES (4,0,1,NULL,NULL,'CONSULTA DE URGENCIA','Se considera consulta de urgencia a toda prestaci�n que no constituye paso intermedio y/o final de tratamiento. Importante establecer el motivo de la misma.',1);
-- INSERT INTO PracticaOdontologica (id,version,estadoAlta,fechaBaja,motivoBaja,denominacion,observaciones,grupoId) VALUES (5,0,1,NULL,NULL,'OBTURACI�N CON AMALGAMA - CAVIDAD SIMPLE','Se reconocer� como obturaci�n simple de amalgama a aquellas en las que se haya practicado un adecuado tallado de la cavidad.',2);

-- insert into Materia(id, nombre, descripcion, nivel) values (1, 'Prostodoncia', 'lalalalal', 'PRIMERO');
-- insert into Materia(id, nombre, descripcion, nivel) values (2, 'Endodoncia', 'lalalalal', 'PRIMERO');
