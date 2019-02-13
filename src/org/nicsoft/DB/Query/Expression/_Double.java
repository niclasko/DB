package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

/**
 * Created by nkjalloh on 31/12/2016.
 */
public class _Double extends Atom {

    private Double value;

    public _Double(double value) {
        super(OperatorType.NONE, KeyWord.NUMBER_ATOM);
        this.value = value;
        this.signature = value + "";
    }

    public Double value() {
        return this.value;
    }

}
