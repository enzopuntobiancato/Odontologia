package com.utn.tesis.mapping.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 25/01/16
 * Time: 23:22
 */
public abstract class BaseDTO implements Serializable {
    private Long id;
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
