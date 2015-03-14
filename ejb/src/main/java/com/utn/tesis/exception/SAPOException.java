package com.utn.tesis.exception;

import javax.ejb.EJBException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 15/02/15
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class SAPOException extends Exception implements Serializable {

    private static final long serialVersionUID = 796770993296843510L;
    Exception exception;

    public SAPOException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
