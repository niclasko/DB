package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Query.*;
import org.nicsoft.DB.Query.Aggregation.AggregateType;
import org.nicsoft.DB.Query.Aggregation.Count;
import org.nicsoft.DB.Query.Function.BaseFunction;
import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class Expression extends StatementBuilder {

    public static final String EXPRESSION_SPECIFIER = "EXPR";

    private Select select;
    private Stack<Atom> output;
    private Stack<Atom> operators;
    private Vector<Atom> expressionComponents;
    protected Atom expressionTreeRoot;
    private ExpressionList expressionList;
    private HashMap<String, ColumnReference> columnReferences;

    protected Expression parentExpression;

    private boolean withinAggregate = false;
    private boolean withinFunction = false;

    public Expression(Select select, ExpressionList expressionList) {
        super(OperatorType.NONE, KeyWord.UNKNOWN);
        this.select = select;
        this.output = new Stack<Atom>();
        this.operators = new Stack<Atom>();
        this.expressionComponents = new Vector<Atom>();
        this.expressionTreeRoot = null;
        this.expressionList = expressionList;
        this.columnReferences = new HashMap<String, ColumnReference>();
    }

    public Object value() {
        return this.expressionTreeRoot.value();
    }

    private boolean precedenceConditionIsMet(Atom atom1, Atom atom2) {

        OperatorType o1 = atom1.operatorType(), o2 = atom2.operatorType();

        if(o1.leftAssociativity() && o1.precedence() <= o2.precedence()) {
            return true;
        } else if(o1.rightAssociativity() && o1.precedence() < o2.precedence()) {
            return true;
        }

        return false;

    }

    // Shunting Yard algorithm for converting an infix expression to postfix notation (Reverse Polish Notation)
    public void addAtom(Atom atom) {

        if(!(this instanceof On)) {
            this.expressionList.getExpressionLookup().addExpressionComponent(atom);
        }
        this.expressionComponents.add(atom);

        if(atom.isOperator()) {

            if(operators.size() == 0) {

                operators.push(atom);

            } else if(operators.size() > 0) {

                while(!operators.isEmpty() && operators.peek().isOperator() && precedenceConditionIsMet(atom, operators.peek())) {

                    output.push(operators.pop());

                }

                operators.push(atom);

            }

        } else if(atom.keyWord() == KeyWord.BEGIN_PARANTHESES) {

            operators.push(atom);

        } else if(atom.keyWord() == KeyWord.END_PARANTHESES) {

            while(!operators.isEmpty() && operators.peek().keyWord() != KeyWord.BEGIN_PARANTHESES) {

                output.push(operators.pop());

            }

            if(operators.isEmpty() || operators.peek().keyWord() != KeyWord.BEGIN_PARANTHESES) {

                System.out.println("Mismatched parentheses. Missing left parentheses.");

                return;

            }

            operators.pop(); // Remove the left parentheses

        } else {

            output.push(atom);

        }

    }

    public void finish() {

        while(!operators.isEmpty()) {

            if(operators.peek().keyWord() == KeyWord.BEGIN_PARANTHESES ||
                    operators.peek().keyWord() == KeyWord.END_PARANTHESES) {

                System.out.println("Mismatched parentheses");

                return;

            }

            output.push(operators.pop());

        }

        this.expressionTreeRoot =
            this.buildExpressionTree(
                output.toArray(new Atom[output.size()])
            );

        if(this.expressionTreeRoot instanceof ColumnReference) {
            this.alias(this.expressionTreeRoot.alias());
        }

    }

    private Atom buildExpressionTree(Atom[] postFixExpression) {

        Stack<Atom> expressionTreeNodes = new Stack<Atom>();

        for(int i=0; i<postFixExpression.length; i++) {

            if(postFixExpression[i].isOperator()) {

                postFixExpression[i].setLeafNodes(
                    expressionTreeNodes.pop(),
                    expressionTreeNodes.pop()
                );

            }

            expressionTreeNodes.push(postFixExpression[i]);

        }

        if(expressionTreeNodes.isEmpty()) {
            return null;
        }

        return expressionTreeNodes.pop();

    }

    public StatementBuilder openingParentheses() {
        this.addAtom(new OpeningParentheses());
        return this;
    }

    public StatementBuilder closingParentheses() {
        this.addAtom(new ClosingParentheses());
        return this;
    }

    public Expression openingFunctionParentheses() {
        return this;
    }

    public Expression closingFunctionParentheses() {
        this.finish();
        return this.parentExpression;
    }

    public Expression Equals() {
        this.addAtom(new Equals());
        return this;
    }

    public Expression And() {
        this.addAtom(new And());
        return this;
    }

    public Expression Or() {
        this.addAtom(new Or());
        return this;
    }

    public Expression Plus() {
        this.addAtom(new Plus());
        return this;
    }

    public Expression Concatenate() {
        this.addAtom(new Concatenate());
        return this;
    }

    public Expression Minus() {
        this.addAtom(new Minus());
        return this;
    }

    public Expression MultiplyBy() {
        this.addAtom(new Multiply());
        return this;
    }

    public Expression DivideBy() {
        this.addAtom(new Divide());
        return this;
    }

    public Expression toPowerOf() {
        this.addAtom(new Power());
        return this;
    }

    public Expression binaryOperator(OperatorType operatorType) {
        switch(operatorType) {
            case PLUS:
                return this.Plus();
            case MINUS:
                return this.Minus();
            case MULTIPLY:
                return this.MultiplyBy();
            case DIVIDE:
                return this.DivideBy();
            case POWER:
                return this.toPowerOf();
            case EQUALS:
                return this.Equals();
        }
        return this;
    }

    public Cast cast() {
        Cast cast = new Cast(this);
        this.addAtom(cast);
        return cast;
    }

    public Expression OpeningParentheses() {
        this.addAtom(new OpeningParentheses());
        return this;
    }

    public Expression ClosingParentheses() {
        this.addAtom(new ClosingParentheses());
        return this;
    }

    public Expression $(String dataSetAlias, String columnName) {

        ColumnReference columnReference =
            new ColumnReference(
                this.select,
                dataSetAlias,
                columnName
            );

        this.select.columnReferences().add(columnReference);

        this.addAtom(columnReference);

        if(this.parentExpression != null) {
            this.withinAggregate =
                this.parentExpression.isWithinAggregate();
            this.withinFunction =
                this.parentExpression.isWithinFunction();
        }

        if(!this.withinAggregate) {
            this.expressionList.addColumnReference(columnReference);
            this.columnReferences.put(columnReference.lookupCode(), columnReference);
        }

        return this;

    }

    public Expression $(String value) {

        this.addAtom(new _String(value));

        return this;

    }

    public Expression $(Number value) {
        if(value instanceof Integer) {
            this.$((Integer)value);
        } else if(value instanceof Double) {
            this.$((Double)value);
        }
        return this;
    }

    public Expression $(Double value) {

        this.addAtom(new _Double(value));

        return this;

    }

    public Expression $(Integer value) {

        this.addAtom(new _Integer(value));

        return this;

    }

    public Expression $(BaseFunction function) {

        this.addAtom(function);

        return this;

    }

    public Expression count() {
        return new Count(this.select, this);
    }

    public Expression as(String alias) {
        this.alias(alias);
        return this;
    }

    public Expression $() {
        if(this.withinFunction) {
            Expression e = new Expression(this.select, this.expressionList);
            e.setWithinFunction(true);
            return e;
        }
        return this.expressionList.addExpressionTree();
    }

    public From innerJoin() {
        this.select.from().innerJoin();
        return this.select.from();
    }

    public From from() {
        return this.select.from();
    }

    public GroupBy groupBy() {
        return this.select.groupBy();
    }

    public Expression aggregateFunction(String tokenText) throws Exception {
        return AggregateType.find(tokenText).create(this.select, this);
    }

    public StatementBuilder bind() throws Exception {
        return this.select.bind();
    }

    public void debugPrint() throws Exception {
        this.select.debugPrint();
    }

    public Collection<ColumnReference> columnReferences() {
        return this.columnReferences.values();
    }

    public boolean hasColumnReferences() {
        return this.columnReferences.values().size() > 0;
    }

    public Vector<Atom> getExpressionComponents() {
        return this.expressionComponents;
    }

    // When it is needed to override the expression, e.g. with the results of a map reduce
    // aggregation
    public void setExpressionTreeRoot(Atom expressionTreeRoot) {
        this.expressionTreeRoot = expressionTreeRoot;
    }

    public boolean isWithinAggregate() {
        return this.withinAggregate;
    }

    public void setWithinAggregate(boolean withinAggregate) {
        this.withinAggregate = withinAggregate;
    }

    public boolean isWithinFunction() {
        return this.withinFunction;
    }

    public void setWithinFunction(boolean withinFunction) {
        this.withinFunction = withinFunction;
    }

    public Expression getExpression() {
        return this;
    }

    //--------------------------------------------------------------------------

    public String toString() {

        StringBuilder treeStringBuilder =
            new StringBuilder();

        Expression.print(
            treeStringBuilder,
            this.expressionTreeRoot,
            0
        );

        return treeStringBuilder.toString();

    }

    private static String rightPad(String stringToPad, int paddedLength, char padding) {

        StringBuilder paddedString = new StringBuilder(stringToPad);

        while(paddedString.length() < paddedLength) {

            paddedString.append(padding);

        }

        return paddedString.toString();

    }

    private static String leftPad(String stringToPad, int paddedLength, char padding) {

        StringBuilder paddedString = new StringBuilder(stringToPad);

        while(paddedString.length() < paddedLength) {

            paddedString.insert(0, padding);

        }

        return paddedString.toString();

    }

    private static void print(StringBuilder treeStringBuilder, Atom node, int depth) {

        if(node == null) {

            return;

        }

        if(node.parent() != null) {

            treeStringBuilder.append("\n");

        }

        treeStringBuilder.append(
            Expression.leftPad(
                " " + node.operatorType(),
                node.operatorType().toString().length() + 1 + depth + 1,
                '-'
            )
        );

        Expression.print(
            treeStringBuilder,
            node.lhs(),
            depth + 1
        );

        Expression.print(
            treeStringBuilder,
            node.rhs(),
            depth + 1
        );

    }

}
