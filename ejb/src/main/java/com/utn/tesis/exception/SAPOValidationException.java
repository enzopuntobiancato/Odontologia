package com.utn.tesis.exception;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 15/02/15
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class SAPOValidationException extends ValidationException {

    private Map<String, String> errors;

    public SAPOValidationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
