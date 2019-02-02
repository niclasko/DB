package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class And extends Atom {

    public And() {
        super(OperatorType.AND, KeyWord.AND);
    }

    public Object value() {
        return (boolean)this.lhs().value() && (boolean)this.lhs().value();
    }
}
