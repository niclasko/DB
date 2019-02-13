package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class Concatenate extends BinaryOperator {

    public Concatenate() {
        super(OperatorType.CONCATENATE, KeyWord.CONCATENATE);
        this.signature = "||";
    }

    protected ICalculation _dd() {
        return new S_S();
    }

    protected ICalculation _id() {
        return new S_S();
    }

    protected ICalculation _di() {
        return new S_S();
    }

    protected ICalculation _ii() {
        return new S_S();
    }

    protected ICalculation _ss() {
        return new S_S();
    }

    protected ICalculation _si() {
        return new S_S();
    }

    protected ICalculation _sd() {
        return new S_S();
    }

    protected ICalculation _is() {
        return new S_S();
    }

    protected ICalculation _ds() {
        return new S_S();
    }

    private class S_S implements ICalculation {
        public Object calc(Object lhs, Object rhs) {
            return lhs.toString() + rhs.toString();
        }
    }

}
