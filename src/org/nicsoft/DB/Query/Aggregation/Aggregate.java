package org.nicsoft.DB.Query.Aggregation;

import org.nicsoft.DB.Query.Select;
import org.nicsoft.DB.Query.Expression.Expression;

public class Aggregate extends Expression implements IReduceProcessor {

    protected Select select;
    protected Reducer reducer;
    protected ReducerEntry reducerEntry;
    protected IReduceProcessor reduceProcessor;

    public Aggregate(Select select, Expression parentExpression) {
        super(select, select.columnProjection());
        this.parentExpression = parentExpression;
        this.select = select;
        this.select.groupBy().addAggregate(this);
        this.parentExpression.addAtom(this);
        this.reduceProcessor = this;
        this.setWithinAggregate(true);
    }

    public void setInitialValue(Object initialValue) {
        this.reducerEntry.setInitialValue(initialValue);
    }

    public void reduce() { ; }

    public void run(Object value) { ; }

    public void setReduceProcessor(IReduceProcessor reduceProcessor) {
        this.reduceProcessor = reduceProcessor;
    }

    public Object value() { return super.value(); }

    protected class InitialReduceProcessor implements IReduceProcessor {
        private Aggregate aggregate;
        public InitialReduceProcessor(Aggregate aggregate) {
            this.aggregate = aggregate;
        }
        public void run(Object value) {
            aggregate.setInitialValue(
                ValueInitializer.initialValue(value)
            );
            aggregate.setReduceProcessor(aggregate);
            aggregate.reduce();
        }
    }

}
