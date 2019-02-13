package org.nicsoft.DB.Query;

import org.nicsoft.DB.Query.Expression.Expression;

public class On extends Expression {

    private From from;

    public On(From from) {
        super(from.getSelect(), from.getSelect().columnProjection());
        this.from = from;
    }

    public void buildExpression() {
        this.finish();
    }

}
