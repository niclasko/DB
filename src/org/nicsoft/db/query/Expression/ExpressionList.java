package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Query.ColumnReference;
import org.nicsoft.DB.Query.Select;

import java.util.Vector;
import java.util.HashMap;
import java.util.Collection;

public class ExpressionList {

    private Select select;
    private Vector<Expression> columns;
    protected HashMap<String, Expression> columnLookup;
    private Expression currentExpression;
    private HashMap<String, ColumnReference> columnReferences;
    private ExpressionLookup expressionLookup;

    public ExpressionList(Select select) {
        this.select = select;
        this.columns = new Vector<Expression>();
        this.columnLookup = new HashMap<String, Expression>();
        this.columnReferences = new HashMap<String, ColumnReference>();
        this.expressionLookup = new ExpressionLookup();
    }

    public Expression addExpressionTree() {
        if(this.currentExpression != null) {
            this.expressionLookup.setExpression(this.currentExpression);
        }
        this.currentExpression = new Expression(this.select, this);
        this.currentExpression.alias(
            Expression.EXPRESSION_SPECIFIER + this.columns.size()
        );
        this.columns.add(this.currentExpression);
        this.expressionLookup.newExpression();
        return this.currentExpression;
    }

    public void addColumnReference(ColumnReference columnReference) {
        this.columnReferences.put(columnReference.lookupCode(), columnReference);
    }

    public boolean hasColumnReference(ColumnReference columnReference) {
        return this.columnReferences.containsKey(columnReference.lookupCode());
    }

    public ColumnReference getColumnReference(ColumnReference columnReference) {
        return this.columnReferences.get(columnReference.lookupCode());
    }

    public Vector<Expression> columns() {
        return this.columns;
    }

    public Collection<ColumnReference> columnReferences() {
        return this.columnReferences.values();
    }

    public ExpressionLookup getExpressionLookup() {
        return this.expressionLookup;
    }

    public String toString() {
        return this.columnReferences.keySet().toString();
    }

}
