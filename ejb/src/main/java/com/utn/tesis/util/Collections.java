/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Maxi
 */
public class Collections {

    public static <T extends Object> T reload(T o, int profundidad) {
        if (o == null) {
            return o;
        }
        if (profundidad < 1) {
            return o;
        }

// recuperar fields
        List<Field> fields = getFieldsOfClass(o.getClass());
        for (Field f : fields) {
// recuperar el tipo de field
            Class<?> type = f.getType();

// restringir el procesamiento de propiedades estaticas o transient
            if (Modifier.isStatic(type.getModifiers()) || Modifier.isTransient(type.getModifiers())) {
                continue;
            }
// restringir el procesamiento de las propiedades que tengan la anotacion @Transient
            for (Annotation a : type.getAnnotations()) {
                if (a.getClass().getSimpleName().toLowerCase().contains("transient")) {
                    continue;
                }
            }

// verificar si es un mapa (recorrerlo: keys y values)
            if (type.equals(Map.class)) {
                Map mapa = (Map) getValue(f, o);
                if (mapa == null) {
//                    log.warn("No se puede recuperar el mapa");
// se pasa al siguiente. Imposible recorrer un mapa nulo.
                    continue;
                }
                if (mapa.values() != null) {
//                    log.debug("Despertando mapa [values]... tamaño {}", mapa.values().size());
                    for (Object inc : mapa.values()) {
                        reload(inc, profundidad - 1);
                    }
                }
                if (mapa.keySet() != null) {
//                    log.debug("Despertando mapa [keySet]... tamaño {}", mapa.keySet().size());
                    for (Object inc : mapa.keySet()) {
                        reload(inc, profundidad - 1);
                    }
                }
                continue;
            }

// si es una coleccion, navegarla.
            if (type.equals(Set.class) || type.equals(List.class)) {
                Collection coleccion = (Collection) getValue(f, o);
                if (coleccion == null) {
//                    log.warn("No se puede recuperar la coleccion [set o list]");
// SE PASA AL SIGUIENTE. IMPOSIBLE RECORRER UNA COLECCION NULA.
                    continue;
                }
//                log.debug("Despertando coleccion {}... tamaño {}", coleccion.getClass().getSimpleName(), coleccion.size());
                for (Object inc : coleccion) {
                    reload(inc, profundidad - 1);
                }
                continue;
            }

// si es IEntidad o IGenericEntity
            if (Generic.class.isAssignableFrom(type)) {
                reload(getValue(f, o), profundidad - 1);
                continue;
            }
        }
        return o;
    }

    private static Object getValue(Field f, Object o) {
        try {
// permitir acceso a la propiedad si es privada.
            f.setAccessible(true);
// retornar el valor.
            return f.get(o);
        } catch (IllegalArgumentException ex) {
//            log.debug("{}", ex.getMessage());
        } catch (IllegalAccessException ex) {
//            log.debug("{}", ex.getMessage());
        }
        return null;
    }

    private static List<Field> getFieldsOfClass(Class clazz) {
        List<Field> fields = new ArrayList<Field>();
        Class<?> tmpClass = clazz;
        do {
            fields.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
            tmpClass = tmpClass.getSuperclass();
        } while (tmpClass != null);

        return fields;
    }
}
