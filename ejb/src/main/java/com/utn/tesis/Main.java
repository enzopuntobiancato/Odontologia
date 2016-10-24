package com.utn.tesis;

import com.utn.tesis.model.odontograma.Odontograma;
import com.utn.tesis.util.JacksonUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 23/10/16
 * Time: 20:17
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String [] args) {

        String odonto = JacksonUtil.toString(Odontograma.createDefault());

        System.out.println(odonto);

        Odontograma o = (Odontograma) JacksonUtil.fromString(odonto, Odontograma.class);

        o.toString();
    }

}
