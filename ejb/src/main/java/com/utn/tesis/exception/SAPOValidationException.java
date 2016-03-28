package com.utn.tesis.exception;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class SAPOValidationException extends ValidationException {

    private Map<String, String> errors;

    public SAPOValidationException(Map<String, String> errors) {
        this.errors = errors;
        if(this.errors != null && !this.errors.isEmpty()) {
            StringBuffer logger = new StringBuffer().append("\n SAPO VALIDATION EXCEPTION: ");
            for (Map.Entry<String, String> entry : errors.entrySet())
            {
                logger.append("\n KEY: " + entry.getKey());
                logger.append("\n VALUE: " + entry.getValue());
            }
            log.error(logger.toString());
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
