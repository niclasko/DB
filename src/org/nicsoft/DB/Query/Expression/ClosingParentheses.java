package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class ClosingParentheses extends Atom {
    public ClosingParentheses() {
        super(OperatorType.NONE, KeyWord.END_PARANTHESES);
        this.signature = ")";
    }
}
