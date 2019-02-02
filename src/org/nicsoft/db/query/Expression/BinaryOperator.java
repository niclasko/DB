package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class BinaryOperator extends Atom {

    private ICalculation expression;

    public BinaryOperator(OperatorType operatorType, KeyWord keyWord) {
        super(operatorType, keyWord);
        this.expression = new Calculation(this);
    }

    public void setExpression(ICalculation expression) {
        this.expression = expression;
    }

    public Object value() {
        return this.expression.calc(
            this.lhs().value(),
            this.rhs().value()
        );
    }

    protected ICalculation _dd() {
        return this.expression;
    }

    protected ICalculation _id() {
        return this.expression;
    }

    protected ICalculation _di() {
        return this.expression;
    }

    protected ICalculation _ii() {
        return this.expression;
    }

    protected ICalculation _ss() {
        return this.expression;
    }

    protected ICalculation _si() {
        return this.expression;
    }

    protected ICalculation _sd() {
        return this.expression;
    }

    protected ICalculation _is() {
        return this.expression;
    }

    protected ICalculation _ds() {
        return this.expression;
    }

}
