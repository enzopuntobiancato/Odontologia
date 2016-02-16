package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.UsuarioConsultaDTO;
import com.utn.tesis.model.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 8/02/16
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
 @Mapper(componentModel = "cdi", uses = {RollMapper.class})
public interface UsuarioConsultaMapper{

    @Mappings({
            @Mapping(source = "usuario.email", target = "email"),
            @Mapping(source = "usuario.nombreUsuario", target = "nombreUsuario"),
            @Mapping(source = "usuario.fechaBaja", target = "fechaBaja"),
            @Mapping(source = "usuario.motivoBaja", target = "motivoBaja"),
            @Mapping(target = "tipoDocumento", expression = "java(persona.getDocumento().getTipoDocumento().toString())"),
            @Mapping(source = "documento.numero", target = "numero"),
            @Mapping(source = "usuario.rol", target = "rol"),
            @Mapping(source = "usuario.id", target = "idUsuario")
    }
    )
    UsuarioConsultaDTO personaToUsuarioConsultaDTO(Persona persona);

//    Persona usuarioConsultaToPersona(UsuarioConsultaDTO usuarioConsultaDTO);
}
