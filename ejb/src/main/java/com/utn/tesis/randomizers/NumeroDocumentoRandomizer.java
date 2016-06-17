package com.utn.tesis.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 12/05/16
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class NumeroDocumentoRandomizer implements Randomizer<String> {
    List<String> elementos = Arrays.asList("356728i7",
            "35672816","37672816","456i2816",
            "35f72815","37y72815","456y2815",
            "35672814","37y72814","456e2814",
            "35h72813","37y72813","456w2813",
            "35h72812","37672812","456q2812",
            "35a72811","37672811","456d2811",
            "35d72810","37672810","456f2810",
            "33k72182","37672182","436l8182",
            "33xe1812","3767g812","436ke812",
            "33ze1812","3767r812","43ru8812",
            "33ne3812","3767w812","43tr2812",
            "333m3812","3767a812","43y72812",
            "331ñ2812","3767e812","53u72812",
            "331y12","399728h2","5367p12",
            "33672814","3997o814","53672814",
            "3364728","39972g14","536781",
            "33q72984","3997d984","53t72984",
            "33d28174","3992s174","53w28174",
            "35728164","3992q164","55q28164",
            "3472r157","3992q157","54f28157",
            "34tr147","39r28147","54h28147",
            "3472r137","3t928137","547k8137",
            "3w728127","3y928127","547o8127",
            "347w8117","3u928117","547y8117",
            "397v8100","3o328100","597e8100",
            "39r29879");
    @Override
    public String getRandomValue() {
        Random r = new Random();
        int valorDado = r.nextInt(30)+1;  // Entre 0 y 5, más 1.
        return elementos.get(valorDado);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
