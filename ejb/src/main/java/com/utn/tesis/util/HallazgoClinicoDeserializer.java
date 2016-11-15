package com.utn.tesis.util;

import com.utn.tesis.model.odontograma.EstadoHallazgoClinico;
import com.utn.tesis.model.odontograma.HallazgoClinico;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 12/11/16
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class HallazgoClinicoDeserializer extends JsonDeserializer<HallazgoClinico> {

    @Override
    public HallazgoClinico deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);

            final String nombre = node.get("nombre").asText();
            final String color = node.get("color") != null ? node.get("color").asText() : null;
            EstadoHallazgoClinico estado = null;
            if (node.get("estado") != null && StringUtils.isNotBlank(node.get("estado").asText())) {
                estado = EstadoHallazgoClinico.valueOf(node.get("estado").asText());
            }
            final String className = node.get("type").asText();

            Class clazz = Class.forName(className);
            HallazgoClinico hallazgoClinico = (HallazgoClinico) clazz.newInstance();
            hallazgoClinico.setEstado(estado);
            return hallazgoClinico;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
