package com.utn.tesis.randomizers;

import com.utn.tesis.model.*;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinitionBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 7/05/16
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public class HistoriaClnicaRandomizer {

    public static List<HistoriaClinica> createRandomHistoriasClinicas(int cantHC, int cantDetalles){
        Map<Integer, List<DetalleHistoriaClinica>> detPorHC = getDetalles(cantHC,cantDetalles);
        if (detPorHC ==  null || detPorHC.isEmpty()){
            return null;
        }
        List<HistoriaClinica> historiasClinicas = new ArrayList<HistoriaClinica>();
        EnhancedRandom hcRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .scanClasspathForConcreteTypes(true)
                .build();

        for (Map.Entry<Integer, List<DetalleHistoriaClinica>> entry : detPorHC.entrySet()) {
            HistoriaClinica hc = hcRandom.nextObject(HistoriaClinica.class, "detallesHC", "realizoHistoriaClinica",
                    "diagnostico", "atencion");
            hc.setDetallesHC(entry.getValue());
            historiasClinicas.add(hc);
        }
        return historiasClinicas;
    }

    public static List<DetalleHistoriaClinica> createRandomDetalles(int cant, Class clazz){
        List<DetalleHistoriaClinica> detalles = new ArrayList<DetalleHistoriaClinica>();
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .scanClasspathForConcreteTypes(true)
                .build();

        for (int i = 0; i < cant; i++) {
             DetalleHistoriaClinica detalleHistoriaClinica = (DetalleHistoriaClinica) enhancedRandom.nextObject(clazz);
            detalles.add(detalleHistoriaClinica);
        }
        return detalles;
    }

    private static Map<Integer, List<DetalleHistoriaClinica>> getDetalles(int cantHC, int cantDet){
        if(cantHC == 0 || cantDet == 0){
            return null;
        }
        Map<Integer,List<DetalleHistoriaClinica>> map = new HashMap<Integer, List<DetalleHistoriaClinica>>();

        for (int i = 0; i < cantHC; i++) {
             ArrayList<DetalleHistoriaClinica> detalles = new ArrayList<DetalleHistoriaClinica>();
            detalles.addAll(createRandomDetalles(cantDet, CampoEnumerable.class));
            detalles.addAll(createRandomDetalles(cantDet, CampoDetalle.class));
            detalles.addAll(createRandomDetalles(cantDet, CampoSiNo.class));
            map.put(i, detalles);
        }
        return map;
    }

}
