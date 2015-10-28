package com.utn.tesis.service.initialization;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 27/10/15
 * Time: 22:18
 */
@AllArgsConstructor
@Getter
public class InitMethod implements Comparable<InitMethod> {

    private final Method method;
    private final Integer order;

    @Override
    public int compareTo(InitMethod o) {
        return this.getOrder().compareTo(o.getOrder());
    }
}
