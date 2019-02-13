package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class Multiply extends BinaryOperator {

    public Multiply() {
        super(OperatorType.MULTIPLY, KeyWord.MULTIPLY);
        this.signature = "*";
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

    protected ICalculation _dn() {
        return new D_N();
    }

    protected ICalculation _nd() {
        return new N_D();
    }

    protected ICalculation _in() {
        return new D_N();
    }

    protected ICalculation _ni() {
        return new N_D();
    }

    private class D_D implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return (double)lhs * (double)rhs;
        }
    }

    private class I_D implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return (int)lhs * (double)rhs;
        }
    }

    private class D_I implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return (double)lhs * (int)rhs;
        }
    }

    private class I_I implements ICalculation {
        public Integer calc(Object lhs, Object rhs) {
            return (int)lhs * (int)rhs;
        }
    }

    private class D_N implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return (double)lhs * (double)rhs;
        }
    }

    private class N_D implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return (double)lhs * (double)rhs;
        }
    }

    private class I_N implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return (double)lhs * (double)rhs;
        }
    }

    private class N_I implements ICalculation {
        public Double calc(Object lhs, Object rhs) {
            return (double)lhs * (double)rhs;
        }
    }

}
