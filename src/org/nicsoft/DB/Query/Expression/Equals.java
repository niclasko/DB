package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

/**
 * Created by nkjalloh on 14/11/2016.
 */
public class Equals extends Atom {
    public Equals() {
        super(OperatorType.EQUALS, KeyWord.EQUALS);
        this.signature = "=";
    }

    public Object value() {
        return this.lhs().value().equals(this.rhs().value());
    }
}
