package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.OperatorType;
import org.nicsoft.DB.Parser.SQL.KeyWord;

public class ClosingParantheses extends Atom {
    public ClosingParantheses() {
        super(OperatorType.NONE, KeyWord.END_PARANTHESES);
    }
}
