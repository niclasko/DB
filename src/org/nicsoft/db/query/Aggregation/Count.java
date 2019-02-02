package org.nicsoft.DB.Query.Aggregation;

import org.nicsoft.DB.Query.Expression.Expression;
import org.nicsoft.DB.Query.Select;

public class Count extends Sum {

    public Count(Select select, Expression parentExpression) {
        super(select, parentExpression);
        this.$(1);
        this.finish();
    }

}
