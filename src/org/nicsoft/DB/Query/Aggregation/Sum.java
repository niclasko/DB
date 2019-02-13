package org.nicsoft.DB.Query.Aggregation;

import org.nicsoft.DB.Query.Expression.Expression;
import org.nicsoft.DB.Query.Expression.Plus;
import org.nicsoft.DB.Query.Expression._Object;
import org.nicsoft.DB.Query.Select;

public class Sum extends Aggregate {

    private Plus plus;
    private _Object lhs;
    private _Object rhs;

    public Sum(Select select, Expression parentExpression) {
        super(select, parentExpression);
        this.reducer = this.select.groupBy().getReducer();
        this.reducerEntry =
            this.reducer.claimReducerEntries(
                1,
                null
            );
        this.plus = new Plus();
        this.lhs = new _Object(null);
        this.rhs = new _Object(null);
        this.plus.setLeafNodes(this.lhs, this.rhs);

        this.reduceProcessor = new InitialReduceProcessor(this);

    }

    public void reduce() {
        this.reduceProcessor.run(super.value());
    }

    public void run(Object value) {
        this.lhs.setValue(
            this.reducer.get(
                this.reducerEntry.reducerEntryIndex()
            )
        );
        this.rhs.setValue(
            value
        );
        this.reducer.set(
            this.reducerEntry.reducerEntryIndex(),
            this.plus.value()
        );
    }

    public Object value() {
        return this.reducer.get(this.reducerEntry.reducerEntryIndex());
    }

}
