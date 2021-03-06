package com.utn.tesis.util;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.model.Bajeable;
import com.utn.tesis.model.EntityBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 2/07/15
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public class MappingUtil {

    public static Object serializeWithView(Object toSerialize, Class view) {

        List<Class> views = new ArrayList<Class>(3);
        views.add(view);
        Class superClass = view.getSuperclass();
        while (superClass != Object.class) {
            views.add(superClass);
            superClass = superClass.getSuperclass();
        }

        try {
            if (toSerialize instanceof List) {
                for (int i = 0; i < ((List)toSerialize).size(); i++) {
                    ((List)toSerialize).set(i, serialize(((List)toSerialize).get(i), views));
                }
            } else {
                toSerialize = serialize(toSerialize, views);
            }
        } catch (Exception e) {
            Logger.getLogger(MappingUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return toSerialize;
    }

    private static Object serialize(Object toSerialize, List<Class> views) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class toSerializeClass = toSerialize.getClass();
        Method[] methods = toSerializeClass.getMethods();

        for(Method method: methods) {
            if (method.getName().startsWith("get")) {
                if (!isAnnotated(method, views)) {
                    // Atributo no perteneciente a la vista requerida, se setea null.
                    Method set = findSet(toSerializeClass, method.getName().substring(3, method.getName().length()), method.getReturnType());
                    if (set != null) {
                        set.invoke(toSerialize, new Object[] {null});
                    }
                } else {
                    // ATributo perteneciente a la vista requerida, chequeamos si el tipo de retorno pertenece al modelo. Si es asi, cargamos la misma vista del mismo.
                    boolean returnTypeBelongToModel;
                    if (method.getReturnType().equals(List.class)) {
                        Class genericReturnType = (Class)((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
                        returnTypeBelongToModel = genericReturnType.getSuperclass().equals(Bajeable.class) || genericReturnType.getSuperclass().equals(EntityBase.class);
                    } else {
                        Class returnTypeSuperClass = method.getReturnType().getSuperclass();
                        returnTypeBelongToModel = returnTypeSuperClass.equals(Bajeable.class) || returnTypeSuperClass.equals(EntityBase.class);
                    }
                    if (returnTypeBelongToModel) {
                        Object value = method.invoke(toSerialize);
                        value = serializeWithView(value, views.get(0));
                        Method set = findSet(toSerializeClass, method.getName().substring(3, method.getName().length()), method.getReturnType());
                        if (set!= null) {
                            set.invoke(toSerialize, new Object[] {value});
                        }
                    }
                }
            }

        }
        return toSerialize;
    }

    private static boolean isAnnotated(Method method, List<Class> views) {
        // The annotated methods always are gets
        boolean present = false;
        if (method.isAnnotationPresent(JsonMap.class)) {
            Annotation annotation = method.getAnnotation(JsonMap.class);
            JsonMap jsonMap = (JsonMap) annotation;

            present = views.contains(jsonMap.view());
        }
        return present;
    }

    private static Method findGet(Class clazz, String fieldName) throws NoSuchMethodException {
        String getMethodName = "get" + fieldName;
        return clazz.getMethod(getMethodName);

    }

    private static Method findSet(Class clazz, String fieldName, Class returnType) {
        String getMethodName = "set" + fieldName;
        try {
            return clazz.getMethod(getMethodName, new Class[]{returnType});
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
