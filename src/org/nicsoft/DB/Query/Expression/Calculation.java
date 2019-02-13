package org.nicsoft.DB.Query.Expression;

/**
 * Created by nkjalloh on 05/01/2017.
 */
public class Calculation implements ICalculation {

    private BinaryOperator binaryOperator;

    public Calculation(BinaryOperator binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    public Object calc(Object lhs, Object rhs) {

        if(lhs == null && rhs != null) {
            if(rhs.getClass() == Integer.class) {
                this.binaryOperator.setExpression(this.binaryOperator._ii());
            } else if(rhs.getClass() == Double.class) {
                this.binaryOperator.setExpression(this.binaryOperator._dd());
            } else if(rhs.getClass() == String.class) {
                this.binaryOperator.setExpression(this.binaryOperator._ss());
            }
            return rhs;
        }

        if(rhs == null && lhs != null) {
            if(lhs.getClass() == Integer.class) {
                this.binaryOperator.setExpression(this.binaryOperator._ii());
            } else if(lhs.getClass() == Double.class) {
                this.binaryOperator.setExpression(this.binaryOperator._dd());
            } else if(lhs.getClass() == String.class) {
                this.binaryOperator.setExpression(this.binaryOperator._ss());
            }
            return lhs;
        }

        if(lhs.getClass() == Double.class && rhs.getClass() == Double.class) {
            this.binaryOperator.setExpression(this.binaryOperator._dd());
        } else if(lhs.getClass() == Integer.class && rhs.getClass() == Double.class) {
            this.binaryOperator.setExpression(this.binaryOperator._id());
        } else if(lhs.getClass() == Double.class && rhs.getClass() == Integer.class) {
            this.binaryOperator.setExpression(this.binaryOperator._di());
        } else if(lhs.getClass() == Integer.class && rhs.getClass() == Integer.class) {
            this.binaryOperator.setExpression(this.binaryOperator._ii());
        } else if(lhs.getClass() == String.class && rhs.getClass() == String.class) {
            this.binaryOperator.setExpression(this.binaryOperator._ss());
        } else if(lhs.getClass() == String.class && rhs.getClass() == Double.class) {
            this.binaryOperator.setExpression(this.binaryOperator._sd());
        } else if(lhs.getClass() == Double.class && rhs.getClass() == String.class) {
            this.binaryOperator.setExpression(this.binaryOperator._ds());
        } else if(lhs.getClass() == String.class && rhs.getClass() == Integer.class) {
            this.binaryOperator.setExpression(this.binaryOperator._si());
        } else if(lhs.getClass() == Integer.class && rhs.getClass() == String.class) {
            this.binaryOperator.setExpression(this.binaryOperator._is());
        }
        return this.binaryOperator.value();
    }
}
