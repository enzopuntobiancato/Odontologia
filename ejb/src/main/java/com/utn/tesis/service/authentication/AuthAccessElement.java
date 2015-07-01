package com.utn.tesis.service.authentication;

import com.utn.tesis.model.Privilegio;
import com.utn.tesis.model.Rol;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 02/06/15
 * Time: 22:43
 */
public class AuthAccessElement implements Serializable {

    public static final String PARAM_AUTH_ID = "auth-id";
    public static final String PARAM_AUTH_TOKEN = "auth-token";

    private String authId;
    private String authToken;
    private String authPermission;
    private List<Privilegio> permission;
    private boolean hasMoreRoles;
    private List<Rol> roles;

    public AuthAccessElement() {
    }

    public AuthAccessElement(String authId, String authToken, String authPermission, List<Privilegio> permission, boolean hasMoreRoles) {
        this.authId = authId;
        this.authToken = authToken;
        this.authPermission = authPermission;
        this.permission = permission;
        this.hasMoreRoles = hasMoreRoles;
    }

    public AuthAccessElement(List<Rol> roles) {
        this.roles = roles;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthPermission() {
        return authPermission;
    }

    public void setAuthPermission(String authPermission) {
        this.authPermission = authPermission;
    }

    public List<Privilegio> getPermission() {
        return permission;
    }

    public void setPermission(List<Privilegio> permission) {
        this.permission = permission;
    }

    public boolean isHasMoreRoles() {
        return hasMoreRoles;
    }

    public void setHasMoreRoles(boolean hasMoreRoles) {
        this.hasMoreRoles = hasMoreRoles;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
