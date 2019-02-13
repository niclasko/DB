package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

/**
 * Created by nkjalloh on 31/12/2016.
 */
public class _String extends Atom {

    private String value;

    public _String(String value) {
        super(OperatorType.NONE, KeyWord.LITERAL);
        this.value = value;
        this.signature = value;
    }

    public Object value() {
        return this.value;
    }

}