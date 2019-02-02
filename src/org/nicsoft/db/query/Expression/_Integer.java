package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;
import org.nicsoft.DB.Query.Expression.Atom;

public class _Integer extends Atom {

    private Integer value;

    public _Integer(Integer value) {
        super(OperatorType.NONE, KeyWord.NUMBER_ATOM);
        this.value = value;
        this.signature = value + "";
    }

    public Integer value() {
        return this.value;
    }

}
