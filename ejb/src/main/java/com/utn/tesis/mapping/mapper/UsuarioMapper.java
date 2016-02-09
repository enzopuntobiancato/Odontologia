package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.UsuarioDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 13:39
 */
@Mapper(componentModel = "cdi", uses = {PrivilegioMapper.class, RollMapper.class})
public interface UsuarioMapper {

    @Mappings({
            @Mapping(source = "rol.nombre", target = "rol"),
            @Mapping(source = "rol.privilegios", target = "permisos"),
            @Mapping(target = "firstLogin", expression = "java(usuario.getUltimaConexion() == null)")
    })
    UsuarioLogueadoDTO toUsuarioLogueadoDTO(Usuario usuario);

    UsuarioDTO fromUsuario(Usuario usuario);


    Usuario fromUsuarioDTO(UsuarioDTO usuarioDTO);
}
