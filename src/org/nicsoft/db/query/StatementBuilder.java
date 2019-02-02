package org.nicsoft.DB.Query;

import org.nicsoft.DB.Query.Expression.Atom;
import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;
import org.nicsoft.DB.Data.DataSet;
import org.nicsoft.DB.Query.Expression.Expression;
import org.nicsoft.DB.Query.Function.BaseFunction;

public abstract class StatementBuilder extends Atom {
    public StatementBuilder() {
        super(OperatorType.NONE, KeyWord.UNKNOWN);
    }
    public StatementBuilder(OperatorType operatorType, KeyWord keyWord) {
        super(operatorType, keyWord);
    }
    public StatementBuilder select() throws Exception { return this; }
    public StatementBuilder top(int n) { return this; }
    public StatementBuilder star() { return this; }
    public StatementBuilder star(String dataSetAlias) { return this; }
    public StatementBuilder from() throws Exception { return this; }
    public StatementBuilder groupBy() { return this; }
    public StatementBuilder openingParentheses() { return this; }
    public StatementBuilder closingParentheses() { return this; }
    public StatementBuilder openingFunctionParentheses() { return this; }
    public StatementBuilder closingFunctionParentheses() { return this; }
    public StatementBuilder as(String alias) throws Exception { return this; }
    public StatementBuilder as(Class<?> type) { return this; }
    public StatementBuilder on() { return this; }
    public StatementBuilder innerJoin() { return this; }
    public StatementBuilder leftJoin() { return this; }
    public StatementBuilder rightJoin() { return this; }
    public StatementBuilder where() { return this; }
    public StatementBuilder with() throws Exception { return this; }
    public StatementBuilder $() { return this; }
    public StatementBuilder $(Number value) { return this; }
    public StatementBuilder $(Integer value) { return this; }
    public StatementBuilder $(Double value) { return this; }
    public StatementBuilder $(String value) throws Exception { return this; }
    public StatementBuilder $(BaseFunction function) { return this; }
    public StatementBuilder $(String dataSetAlias, String columnName) { return this; }
    public StatementBuilder $(DataSet dataSet) throws Exception { return this; }
    public StatementBuilder endSubQuery() throws Exception { return this; }
    public boolean isSubQuery() { return false; }
    public StatementBuilder Plus() { return this; }
    public StatementBuilder Concatenate() { return this; }
    public StatementBuilder Minus() { return this; }
    public StatementBuilder Equals() { return this; }
    public StatementBuilder MultiplyBy() { return this; }
    public StatementBuilder toPowerOf() { return this; }
    public StatementBuilder And() { return this; }
    public StatementBuilder Or() { return this; }
    public StatementBuilder binaryOperator(OperatorType operatorType) { return this; }
    public StatementBuilder cast() { return this; }
    public StatementBuilder bind() throws Exception { return this; }
    public StatementBuilder count() { return this; }
    public StatementBuilder aggregateFunction(String tokenText) throws Exception { return this; }
    public void debugPrint() throws Exception { ; }
    public void closeDataSets() throws Exception { ; }
    public Expression getExpression() { return null; }
    public void toFile(String fileName) throws Exception { ; }
}