package org.nicsoft.DB.Query.Expression;

public class ExpressionLookup {

    private ExpressionLookupTrie expressionLookupTrie;
    private ExpressionLookupTrie currentExpressionLookupTrieBranch;

    public ExpressionLookup() {
        this.expressionLookupTrie = new ExpressionLookupTrie();
        this.currentExpressionLookupTrieBranch = this.expressionLookupTrie;
    }

    public void newExpression() {
        this.currentExpressionLookupTrieBranch = this.expressionLookupTrie;
    }

    public void addExpressionComponent(Atom expressionComponent) {
        this.currentExpressionLookupTrieBranch =
            this.currentExpressionLookupTrieBranch.addChild(expressionComponent);
    }

    public void setExpression(Expression expression) {
        this.currentExpressionLookupTrieBranch.setExpression(expression);
    }

    private boolean lookup(Atom expressionComponent) {
        return (this.currentExpressionLookupTrieBranch = this.currentExpressionLookupTrieBranch.getChild(expressionComponent)) != null;
    }

    private boolean lookup(String signature) {
        return (this.currentExpressionLookupTrieBranch = this.currentExpressionLookupTrieBranch.getChild(signature)) != null;
    }

    public boolean hasExpression(Expression expression) {
        this.newExpression();
        for(Atom expressionComponent : expression.getExpressionComponents()) {
            if(!this.lookup(expressionComponent)) {
                return false;
            }
        }
        return this.currentExpressionLookupTrieBranch.hasExpression();
    }

    public Expression getExpression(Expression expression) {
        this.newExpression();
        for(Atom expressionComponent : expression.getExpressionComponents()) {
            if(!this.lookup(expressionComponent)) {
                return null;
            }
        }
        return this.currentExpressionLookupTrieBranch.getExpression();
    }

    public Expression getExpression(String signature) {
        this.newExpression();
        if(!this.lookup(signature)) {
            return null;
        }
        return this.currentExpressionLookupTrieBranch.getExpression();
    }

}
