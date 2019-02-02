package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Data.DataSetColumnReference;
import org.nicsoft.DB.Query.ColumnReference;
import org.nicsoft.DB.Query.Select;
import org.nicsoft.DB.Query.From;

import java.util.Stack;
import java.util.Vector;

public class ExpressionTree extends Atom {

    private Select select;
    private Stack<Atom> output;
    private Stack<Atom> operators;
    protected Atom expressionTreeRoot;

    public ExpressionTree(Select select) {
        super(OperatorType.NONE, SQLKeyWord.UNKNOWN);
        this.select = select;
        this.output = new Stack<Atom>();
        this.operators = new Stack<Atom>();
        this.expressionTreeRoot = null;
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

        if(atom.isOperator()) {

            if(operators.size() == 0) {

                operators.push(atom);

            } else if(operators.size() > 0) {

                while(!operators.isEmpty() && operators.peek().isOperator() && precedenceConditionIsMet(atom, operators.peek())) {

                    output.push(operators.pop());

                }

                operators.push(atom);

            }

        } else if(atom.sqlKeyWord() == SQLKeyWord.BEGIN_PARANTHESES) {

            operators.push(atom);

        } else if(atom.sqlKeyWord() == SQLKeyWord.END_PARANTHESES) {

            while(!operators.isEmpty() && operators.peek().sqlKeyWord() != SQLKeyWord.BEGIN_PARANTHESES) {

                output.push(operators.pop());

            }

            if(operators.isEmpty() || operators.peek().sqlKeyWord() != SQLKeyWord.BEGIN_PARANTHESES) {

                System.out.println("Mismatched parantheses. Missing left parantheses.");

                return;

            }

            operators.pop(); // Remove the left parentheses

        } else {

            output.push(atom);

        }

    }

    public void finish() {

        while(!operators.isEmpty()) {

            if(operators.peek().sqlKeyWord() == SQLKeyWord.BEGIN_PARANTHESES ||
                    operators.peek().sqlKeyWord() == SQLKeyWord.END_PARANTHESES) {

                System.out.println("Mismatched parentheses");

                return;

            }

            output.push(operators.pop());

        }

        this.expressionTreeRoot =
            this.buildExpressionTree(
                output.toArray(new Atom[output.size()])
            );

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

    public ExpressionTree Equals() {
        this.addAtom(new Equals());
        return this;
    }

    public ExpressionTree Plus() {
        this.addAtom(new Plus());
        return this;
    }

    public ExpressionTree Minus() {
        this.addAtom(new Minus());
        return this;
    }

    public ExpressionTree MultiplyBy() {
        this.addAtom(new Multiply());
        return this;
    }

    public ExpressionTree DivideBy() {
        this.addAtom(new Divide());
        return this;
    }

    public ExpressionTree toPowerOf() {
        this.addAtom(new Power());
        return this;
    }

    public Cast Cast() {
        Cast cast = new Cast(this);
        this.addAtom(cast);
        return cast;
    }

    public ExpressionTree OpeningParantheses() {
        this.addAtom(new OpeningParantheses());
        return this;
    }

    public ExpressionTree ClosingParantheses() {
        this.addAtom(new ClosingParantheses());
        return this;
    }

    public ExpressionTree $(String dataSetAlias, String columnName) {

        ColumnReference columnReference =
            new ColumnReference(
                this.select,
                dataSetAlias,
                columnName
            );

        this.select.columnReferences().add(columnReference);

        this.addAtom(columnReference);

        return this;

    }

    public ExpressionTree $(Double value) {

        this.addAtom(new _Double(value));

        return this;

    }

    public ExpressionTree $(Integer value) {

        this.addAtom(new _Integer(value));

        return this;

    }

    public ExpressionTree as(String alias) {
        this.alias(alias);
        return this;
    }

    public ExpressionTree $() {
        return this.select.columnProjection().$();
    }

    public From innerJoin() {
        this.select.from().innerJoin();
        return this.select.from();
    }

    public From from() {
        return this.select.from();
    }

    public Select bind() {
        this.select.bind();
        return this.select;
    }

    //--------------------------------------------------------------------------

    public String toString() {

        StringBuilder treeStringBuilder =
            new StringBuilder();

        ExpressionTree.print(
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
            ExpressionTree.leftPad(
                " " + node.operatorType(),
                node.operatorType().toString().length() + 1 + depth + 1,
                '-'
            )
        );

        ExpressionTree.print(
            treeStringBuilder,
            node.lhs(),
            depth + 1
        );

        ExpressionTree.print(
            treeStringBuilder,
            node.rhs(),
            depth + 1
        );

    }

}
