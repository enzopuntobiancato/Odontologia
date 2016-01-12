package com.utn.tesis.service.authentication;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 02/06/15
 * Time: 22:21
 */
public class AuthLoginElement implements Serializable {
    private String username;
    private String password;

    public AuthLoginElement() {
    }

    public AuthLoginElement(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
