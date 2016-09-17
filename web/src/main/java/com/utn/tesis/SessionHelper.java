package com.utn.tesis;

import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.service.UsuarioService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class SessionHelper {
    @Inject
    private UsuarioService usuarioService;

    public UsuarioLogueadoDTO getUser(HttpServletRequest request) {
        String authId = request.getHeader(UsuarioLogueadoDTO.PARAM_AUTH_ID);
        String authToken = request.getHeader(UsuarioLogueadoDTO.PARAM_AUTH_TOKEN);
        UsuarioLogueadoDTO usuario = usuarioService.findDTOByUsernameAndAuthToken(authId, authToken);
        return usuario;
    }
}
