package com.utn.tesis.model;

import com.utn.tesis.interfaces.Validator;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 2/02/16
 * Time: 13:40
 */
@MappedSuperclass
public abstract class SuperEntityBase implements Serializable, Validator {

    public abstract Long getId();

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isNew() {
        if (version == null) {
            return true;
        }
        return false;
    }
}
