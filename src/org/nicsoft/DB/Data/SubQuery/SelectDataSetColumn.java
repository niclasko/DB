package org.nicsoft.DB.Data.SubQuery;

import org.nicsoft.DB.Data.DataSetColumn;
import org.nicsoft.DB.Query.Expression.Expression;
import org.nicsoft.DB.Query.Select;

public class SelectDataSetColumn extends DataSetColumn {

    private Expression expression;

    public SelectDataSetColumn(Select select, String name) throws Exception {
        super(select, name);
        this.expression = select.columnProjection().findColumn(name);
        if(this.expression == null) {
            throw new Exception("Column \"" + name + "\" is not defined.");
        }
    }

    public SelectDataSetColumn(Select select, Expression expression) {
        super(select, expression.alias());
        this.expression = expression;
    }

    public Object value() {
        return this.expression.value();
    }

}
