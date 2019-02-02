package org.nicsoft.DB.Query.Expression;

public class Convert implements IConvert {

    private Class type;

    public Convert(Class type) {
        this.type = type;
    }

    public Object cast(Object value) {
        return this.type.cast(value);
    }
}