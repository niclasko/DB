package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Query.Select;

import java.util.Vector;
import java.util.HashMap;

public class ExpressionTreeList {

    private Select select;
    private Vector<ExpressionTree> columns;
    protected HashMap<String, ExpressionTree> columnLookup;
    private ExpressionTree currentExpressionTree;

    public ExpressionTreeList(Select select) {
        this.select = select;
        this.columns = new Vector<ExpressionTree>();
        this.columnLookup = new HashMap<String, ExpressionTree>();
    }

    public ExpressionTree addExpressionTree() {
        this.currentExpressionTree = new ExpressionTree(this.select);
        this.columns.add(this.currentExpressionTree);
        return this.currentExpressionTree;
    }

    public Vector<ExpressionTree> columns() {
        return this.columns;
    }

}
