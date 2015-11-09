package com.utn.tesis.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 9/11/15
 * Time: 0:57
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SAPORuntimeException extends RuntimeException {
    private String message;
}
