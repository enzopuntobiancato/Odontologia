package com.utn.tesis.util;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 23/10/16
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * Descriptor for a Json type.
 *
 * @author Vlad MIhalcea
 *
 */
public class JsonBinaryType
        extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {

    public JsonBinaryType() {
        super( JsonBinarySqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
    }

    public String getName() {
        return "jsonb";
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }

}