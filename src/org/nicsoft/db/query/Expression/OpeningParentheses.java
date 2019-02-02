package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class OpeningParentheses extends Atom {

    public OpeningParentheses() {
        super(OperatorType.NONE, KeyWord.BEGIN_PARANTHESES);
        this.signature = "(";
    }

}
