package com.utn.tesis.service.authentication;

import com.utn.tesis.mapping.dto.LoginDTO;
import com.utn.tesis.mapping.dto.RolUsuarioDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.mapping.mapper.PrivilegioMapper;
import com.utn.tesis.mapping.mapper.RolUsuarioMapper;
import com.utn.tesis.mapping.mapper.UsuarioMapper;
import com.utn.tesis.model.Rol;
import com.utn.tesis.model.RolUsuario;
import com.utn.tesis.model.Usuario;
import com.utn.tesis.service.RolService;
import com.utn.tesis.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

@Stateless
@Slf4j
public class AuthService {
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private UsuarioMapper usuarioMapper;
    @Inject
    private EnumMapper enumMapper;
    @Inject
    private PrivilegioMapper privilegioMapper;
    @Inject
    private RolUsuarioMapper rolUsuarioMapper;
    @Inject
    private RolService rolService;

    public UsuarioLogueadoDTO login(LoginDTO loginElement) {
        UsuarioLogueadoDTO usuarioLogueado = null;
        Usuario user = usuarioService.findByUserAndPassword(loginElement.getUsername(), loginElement.getPassword());
        if (user != null) {
            user.setAuthToken(UUID.randomUUID().toString());
            boolean firstLogin = user.getUltimaConexion() == null;
            user.setUltimaConexion(firstLogin ? null : Calendar.getInstance());
            usuarioLogueado = UsuarioLogueadoDTO.valueOf(user);
            if (user.getRoles().size() == 1) {
                return login(usuarioLogueado, user.getRoles().get(0));
            } else {
                return usuarioLogueado;
            }
        }
        return usuarioLogueado;
    }

    public UsuarioLogueadoDTO selectRol(UsuarioLogueadoDTO usuarioLogueadoDTO, RolUsuarioDTO rolUsuario) {
        Usuario usuario = usuarioService.findByUsernameAndAuthToken(usuarioLogueadoDTO.getNombreUsuario(), usuarioLogueadoDTO.getAuthToken());
        usuario.setAuthToken(UUID.randomUUID().toString());
        UsuarioLogueadoDTO usuarioLogueado = UsuarioLogueadoDTO.valueOf(usuario);
        return login(usuarioLogueado, rolUsuarioMapper.fromDTO(rolUsuario));
    }

    private UsuarioLogueadoDTO login(UsuarioLogueadoDTO usuario, RolUsuario rolUsuario) {
        Rol rol = rolService.findById(rolUsuario.getRol().getId());
        usuario.setRol(enumMapper.rolEnumToDTO(rol.getNombre()));
        return usuario;
    }

    public boolean isAuthorized(String authId, String authToken, String rol, Set<String> rolesAllowed) {
        Usuario user = usuarioService.findByUsernameAndAuthToken(authId, authToken);

//        if (user != null) {
//            if (user.getRol().getNombre() == RolEnum.ADMINISTRADOR) {
//                return true;
//            } else {
//                return rolesAllowed.contains(user.getRol());
//            }
//        } else {
//            return false;
//        }
        return true;
    }
}
