package org.nicsoft.DB.Query.Function;

import org.nicsoft.DB.Query.Expression.Atom;

import java.util.Vector;

public abstract class ParametricFunction extends BaseFunction {

    protected Vector<Atom> parameters;

    public ParametricFunction() {
        this.isParametric = true;
        this.parameters = new Vector<Atom>();
    }

    public void addParameter(Atom parameter) {
        this.parameters.add(parameter);
    }

}
