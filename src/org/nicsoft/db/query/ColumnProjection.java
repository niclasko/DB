package org.nicsoft.DB.Query;

import org.nicsoft.DB.Query.Expression.Expression;
import org.nicsoft.DB.Query.Expression.ExpressionList;

public class ColumnProjection extends ExpressionList {

    private Select select;

    public ColumnProjection(Select select) {
        super(select);
        this.select = select;
    }

    public Expression $() {
        return this.addExpressionTree();
    }

    public void buildExpressions() {
        if(this.columns().size() > 0) {
            this.getExpressionLookup().setExpression(this.columns().lastElement());
        }
        for(Expression expression : this.columns()) {
            expression.finish();
            this.columnLookup.put(expression.alias(), expression);
        }
    }

    public Expression findColumn(String alias) {
        return this.columnLookup.get(alias);
    }

    public Expression getExpression(Expression expression) {
        return this.getExpressionLookup().getExpression(expression);
    }

    public Expression getExpression(String signature) {
        return this.getExpressionLookup().getExpression(signature);
    }

}
