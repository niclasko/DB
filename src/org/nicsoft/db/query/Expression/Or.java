package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class Or extends Atom {

    public Or() {
        super(OperatorType.OR, KeyWord.OR);
    }

    public Object value() {
        return (boolean)this.lhs().value() && (boolean)this.lhs().value();
    }
}
