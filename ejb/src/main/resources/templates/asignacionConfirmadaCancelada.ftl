<html>
<body>
<table style="width:100%;border-style:none;border-collapse:collapse;" border="0">
    <tr style="background-color: #dcdcdc;">
        <th style="width:20%;"><img src="${logoUrl}"/></th>
        <th><h1 style="font-style: italic;">Sistema de administración de pacientes odontológicos</h1></th>
    </tr>
    <tr>
        <td style="padding-top:10px;padding-left:20px;padding-right:20px;text-align: justify;" colspan="2">

            <h2>Estimado ${pacienteApellido} ${pacienteNombre},</h2>

            <p>
              Una asignación a su nombre previamente confirmada ha sido cancelada.
               A continuación encontrará algunos datos relacionados. Ud. NO tiene que asistir en la fecha que se detalla abajo.
            </p>

            <p>
                            <h3>Datos del alumno:<h3>
                        <h4>Nombre: ${alumnoNombre}</h4>
                        <h4>Apellido: ${alumnoApellido}</h4>
                        <h4>Correo electrónico: ${alumnoEmail}</h4>
                        </p>

            <p>
                <h2>Datos de la asignación:<h2>
            <h3><b>Fecha: ${fechaAsignacion}</b></h3>
            <h3><b>Cátedra: ${catedraAsignacion}</b></h3>
            <h3><b>Práctica a realizar: ${practicaAsignacion}</b></h3>
            </p>
        </td>
    </tr>
</table>
</body>
</html>