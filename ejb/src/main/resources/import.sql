
SET NAMES 'utf8';
insert into GrupoPracticaOdontologica(id, version, nombre) values (1, 0, 'CONSULTAS');
insert into GrupoPracticaOdontologica(id, version, nombre) values (2, 0, 'OPERATORIA DENTAL');
insert into GrupoPracticaOdontologica(id, version, nombre) values (3, 0, 'ENDODONCIA SIN OBTURACIÓN');
insert into GrupoPracticaOdontologica(id, version, nombre) values (4, 0, 'PRÓTESIS');
insert into GrupoPracticaOdontologica(id, version, nombre) values (5, 0, 'ODONTOLOGÍA PREVENTIVA');
insert into GrupoPracticaOdontologica(id, version, nombre) values (6, 0, 'ORTODONCIA Y ORTOPEDIA FUNCIONAL');
insert into GrupoPracticaOdontologica(id, version, nombre) values (7, 0, 'ODONTOPEDIATRÍA');
insert into GrupoPracticaOdontologica(id, version, nombre) values (8, 0, 'PERIODONCIA');
insert into GrupoPracticaOdontologica(id, version, nombre) values (9, 0, 'RADIOLOGÍA');
insert into GrupoPracticaOdontologica(id, version, nombre) values (10, 0, 'CIRUGÍA BUCAL');

INSERT INTO PracticaOdontologica (id,version,estadoAlta,fechaBaja,motivoBaja,denominacion,observaciones,grupoId) VALUES (2,0,1,NULL,NULL,'EXAMEN, DIAGNÓSTICO, FICHADO Y PLAN DE TRATAMIENTO','Se considera primera consulta al examen, diagnóstico, fichado y plan de tratamiento. Como consecuencia del exámen, el fichado deberá reflejar el estado actual de la boca, previo al tratamiento.',1);
INSERT INTO PracticaOdontologica (id,version,estadoAlta,fechaBaja,motivoBaja,denominacion,observaciones,grupoId) VALUES (3,0,1,NULL,NULL,'VISITA A DOMICILIO','Se considera consulta domiciliaria a la atención de pacientes impedidos de trasladarse al consultorio del odontólogo.',1);
INSERT INTO PracticaOdontologica (id,version,estadoAlta,fechaBaja,motivoBaja,denominacion,observaciones,grupoId) VALUES (4,0,1,NULL,NULL,'CONSULTA DE URGENCIA','Se considera consulta de urgencia a toda prestación que no constituye paso intermedio y/o final de tratamiento. Importante establecer el motivo de la misma.',1);
INSERT INTO PracticaOdontologica (id,version,estadoAlta,fechaBaja,motivoBaja,denominacion,observaciones,grupoId) VALUES (5,0,1,NULL,NULL,'OBTURACIÓN CON AMALGAMA - CAVIDAD SIMPLE','Se reconocerá como obturación simple de amalgama a aquellas en las que se haya practicado un adecuado tallado de la cavidad.',2);

-- insert into Materia(id, nombre, descripcion, nivel) values (1, 'Prostodoncia', 'lalalalal', 'PRIMERO');
-- insert into Materia(id, nombre, descripcion, nivel) values (2, 'Endodoncia', 'lalalalal', 'PRIMERO');
