package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class Power extends BinaryOperator {

    public Power() {
        super(OperatorType.POWER, KeyWord.POWER);
        this.signature = "";
    }

    protected ICalculation _dd() {
        return new D_D();
    }

    protected ICalculation _id() {
        return new I_D();
    }

    protected ICalculation _di() {
        return new D_I();
    }

    protected ICalculation _ii() {
        return new I_I();
    }

    private class D_D implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return Math.pow((double)lhs, (double)rhs);
        }
    }

    private class I_D implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return Math.pow((int)lhs, (double)rhs);
        }
    }

    private class D_I implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return Math.pow((double)lhs, (int)rhs);
        }
    }

    private class I_I implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return Math.pow((int)lhs, (int)rhs);
        }
    }

}