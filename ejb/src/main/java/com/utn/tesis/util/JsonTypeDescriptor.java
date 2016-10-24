package com.utn.tesis.util;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 23/10/16
 * Time: 18:43
 * To change this template use File | Settings | File Templates.
 */
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * @author Vlad Mihalcea
 */
public class JsonTypeDescriptor
        extends AbstractTypeDescriptor<Object> implements DynamicParameterizedType {

    private Class<?> jsonObjectClass;

    @Override
    public void setParameterValues(Properties parameters) {
        ParameterType p = ( (ParameterType) parameters.get( PARAMETER_TYPE ) );
        if(p != null) {
            jsonObjectClass = p.getReturnedClass();
        }
        else{
            jsonObjectClass = null;
        }
    }

    public JsonTypeDescriptor() {
        super( Object.class, new MutableMutabilityPlan<Object>() {
            @Override
            protected Object deepCopyNotNull(Object value) {
                return JacksonUtil.clone(value);
            }
        });
    }

    @Override
    public boolean areEqual(Object one, Object another) {
        if ( one == another ) {
            return true;
        }
        if ( one == null || another == null ) {
            return false;
        }
        return JacksonUtil.toJsonNode(JacksonUtil.toString(one)).equals(
                JacksonUtil.toJsonNode(JacksonUtil.toString(another)));
    }

    @Override
    public String toString(Object value) {
        if(value == null) return null;
        return JacksonUtil.toString(value);
    }

    @Override
    public Object fromString(String string) {
        if(string == null) return null;
        return JacksonUtil.fromString(string, jsonObjectClass);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public <X> X unwrap(Object value, Class<X> type, WrapperOptions options) {
        if ( value == null ) {
            return null;
        }
        if ( String.class.isAssignableFrom( type ) ) {
            return (X) toString(value);
        }
        if ( Object.class.isAssignableFrom( type ) ) {
            return (X) JacksonUtil.toJsonNode(toString(value));
        }
        throw unknownUnwrap( type );
    }

    @Override
    public <X> Object wrap(X value, WrapperOptions options) {
        if ( value == null ) {
            return null;
        }
        return fromString(value.toString());
    }

}
