package com.utn.tesis.model;

import com.utn.tesis.interfaces.Validator;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class EntityBase implements Serializable, Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    //GETTERS AND SETTERS
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

    public boolean isNew() {
        if (id == null && version == null) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 73 * hash + (this.version != null ? this.version.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityBase other = (EntityBase) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.version != other.version && (this.version == null || !this.version.equals(other.version))) {
            return false;
        }
        return true;
    }
}
