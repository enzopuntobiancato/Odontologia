package com.utn.tesis.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 10/05/16
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public class GrupoRandomizer implements Randomizer<Integer> {
    List<Integer> grupos = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11);

    @Override
    public Integer getRandomValue() {
        Random r = new Random();
        int valorDado = r.nextInt(9)+1;  // Entre 0 y 5, más 1.
        return grupos.get(valorDado);
    }
}
