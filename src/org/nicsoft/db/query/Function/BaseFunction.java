package org.nicsoft.DB.Query.Function;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;
import org.nicsoft.DB.Query.Expression.Atom;

public class BaseFunction extends Atom {

    protected boolean isParametric;

    public BaseFunction() {
        super(OperatorType.NONE, KeyWord.FUNCTION_NAME);
        this.isParametric = false;
    }

    public boolean isParametric() {
        return this.isParametric;
    }

    public void initialize() throws Exception {
        ;
    }

}
