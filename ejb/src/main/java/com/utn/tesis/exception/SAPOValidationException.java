package com.utn.tesis.exception;

import javax.validation.ValidationException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 15/02/15
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class SAPOValidationException extends ValidationException {

    private HashMap<String, String> errors;

    public SAPOValidationException(HashMap<String, String> errors) {
        this.errors = errors;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
