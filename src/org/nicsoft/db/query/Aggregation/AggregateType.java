package org.nicsoft.DB.Query.Aggregation;

import org.nicsoft.DB.Query.Expression.Expression;
import org.nicsoft.DB.Query.Select;

import java.util.HashMap;

public enum AggregateType {

    COUNT("COUNT") {
        @Override
        public Aggregate create(Select select, Expression parentExpression) {
            return new Count(select, parentExpression);
        }
    },
    SUM("SUM") {
        @Override
        public Aggregate create(Select select, Expression parentExpression) {
            return new Sum(select, parentExpression);
        }
    };

    private static final HashMap<String, AggregateType> displayNameMap;

    static {

        displayNameMap = new HashMap<String, AggregateType>();

        for (AggregateType at : AggregateType.values()) {
            displayNameMap.put(at.displayName(), at);
        }
    }

    public static AggregateType find(String displayName) throws Exception {

        if(!displayNameMap.containsKey(displayName.toUpperCase())) {
            throw new Exception("Function \"" + displayName + "\" is not implemented.");
        }

        AggregateType functionType =
            displayNameMap.get(
                displayName.toUpperCase()
            );

        return functionType;
    }

    private String displayName;

    AggregateType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return this.displayName;
    }

    public abstract Aggregate create(Select select, Expression parentExpression);

}
