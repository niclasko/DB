package org.nicsoft.DB.Query.Expression;

/**
 * Created by nkjalloh on 03/01/2017.
 */
public class Cast extends Expression {

    private Class<?> type;

    private IConvert conversion;

    public Cast(Expression parentExpression) {
        super(
            parentExpression.from().getSelect(),
            parentExpression.from().getSelect().columnProjection()
        );
        this.parentExpression = parentExpression;
        this.type = null;
    }

    public Expression as(Class<?> type) {
        this.type = type;
        super.finish();
        this.setConversionMethod();
        super.setValueMethodReturnType(type);
        return this;
    }

    private void setConversionMethod() {
        if(this.type == Double.class && super.getValueMethodReturnType() == Object.class) {
            this.conversion = new Convert_S_to_D();
        } else if(this.type == Integer.class && super.getValueMethodReturnType() == Object.class) {
            this.conversion = new Convert_S_to_I();
        } else {
            this.conversion = new Convert(this.type);
        }
    }

    public Object value() {
        return this.conversion.cast(super.value());
    }

    public String alias() {
        return (this.alias() == null ? this.expressionTreeRoot.alias() : this.alias());
    }

    public Cast $(String dataSetAlias, String columnName) {
        super.$(dataSetAlias, columnName);
        return this;
    }

    public Expression $() {
        Expression e = super.$();
        e.setWithinAggregate(this.isWithinAggregate());;
        return e;
    }

    private class Convert_S_to_D implements IConvert {
        public Double cast(Object value) {
            return Double.parseDouble((String)value);
        }
    }

    private class Convert_S_to_I implements IConvert {
        public Integer cast(Object value) {
            return Integer.parseInt((String)value);
        }
    }

}
