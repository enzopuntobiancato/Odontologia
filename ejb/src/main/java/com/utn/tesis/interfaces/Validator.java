/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.interfaces;

import com.utn.tesis.exception.SAPOValidationException;

/**
 *
 * @author Maxi
 */
public interface Validator {
    
    void validar() throws SAPOValidationException;
    
}
