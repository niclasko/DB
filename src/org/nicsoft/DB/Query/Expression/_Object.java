package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class _Object extends Atom {

    private Object value;

    public _Object(Object value) {
        super(OperatorType.NONE, KeyWord.NUMBER_ATOM);
        this.value = value;
        this.signature = value + "";
    }

    public Object value() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
