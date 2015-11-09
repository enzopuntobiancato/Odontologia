package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 02/06/15
 * Time: 22:21
 */
public class LoginDTO extends BaseDTO {
    private static final long serialVersionUID = -7401432962765233207L;

    private String username;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
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
