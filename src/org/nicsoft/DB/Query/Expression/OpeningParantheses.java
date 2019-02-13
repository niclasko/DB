package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.OperatorType;
import org.nicsoft.DB.Parser.SQL.KeyWord;

public class OpeningParantheses extends Atom {

    public OpeningParantheses() {
        super(OperatorType.NONE, KeyWord.BEGIN_PARANTHESES);
    }

}
