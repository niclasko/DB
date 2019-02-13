package org.nicsoft.DB.Query.Aggregation;

public abstract class ValueInitializer {

    private static final Double INIT_DOUBLE = 0.0;
    private static final Integer INIT_INTEGER = 0;
    private static final Float INIT_FLOAT = 0.0f;
    private static final Object INIT_OBJECT = null;
    private static final String INIT_STRING = "";

    public static Object initialValue(Object value) {
        if(value == null) {
            return null;
        }
        if(value.getClass() == Double.class) {
            return ValueInitializer.INIT_DOUBLE;
        } else if(value.getClass() == Integer.class) {
            return ValueInitializer.INIT_INTEGER;
        } else if(value.getClass() == Float.class) {
            return ValueInitializer.INIT_FLOAT;
        } else if(value.getClass() == Object.class) {
            return ValueInitializer.INIT_OBJECT;
        } else if(value.getClass() == String.class) {
            return ValueInitializer.INIT_STRING;
        }
        return null;
    }
}
