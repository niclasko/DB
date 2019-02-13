package org.nicsoft.DB.Query.Expression;

import java.util.HashMap;

public class ExpressionLookupTrie {
    private HashMap<Object, ExpressionLookupTrie> children;
    private Expression expression;

    public ExpressionLookupTrie() {
        this.expression = null;
    }

    public ExpressionLookupTrie addChild(Atom node) {
        if(this.children == null) {
            this.children = new HashMap<Object, ExpressionLookupTrie>();
        }
        ExpressionLookupTrie branch = new ExpressionLookupTrie();
        this.children.put(node.signature(), branch);
        return branch;
    }

    public ExpressionLookupTrie getChild(Atom node) {
        return this.getChild(node.signature().toString());
    }

    public ExpressionLookupTrie getChild(String signature) {
        if(this.children == null) {
            return null;
        }
        return this.children.get(signature);
    }

    public boolean hasExpression() {
        return this.expression != null;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Object[] keys() {
        return this.children.keySet().toArray(new Object[]{});
    }

}
