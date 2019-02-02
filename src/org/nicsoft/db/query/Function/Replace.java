package org.nicsoft.DB.Query.Function;

import org.nicsoft.DB.Query.Expression.Atom;

public class Replace extends ParametricFunction {

    private Atom valueToSearchIn;
    private String regexValueToSearchFor;
    private String valueToReplaceWith;

    public String value() {
        return this.valueToSearchIn.value().toString().replaceAll(
            regexValueToSearchFor,
            valueToReplaceWith
        );
    }

    public void initialize() throws Exception {
        if(super.parameters.size() != 3) {
            throw new Exception("Replace expects 3 parameters");
        }
        this.valueToSearchIn = super.parameters.get(0);
        this.regexValueToSearchFor = super.parameters.get(1).value().toString();
        this.valueToReplaceWith = super.parameters.get(2).value().toString();
    }
}
