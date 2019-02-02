package org.nicsoft.DB.Query.Aggregation;

import org.nicsoft.DB.Data.DataSetColumn;

public class GroupByDataSetColumn extends DataSetColumn {

    private Object value;

    public void setValue(Object value) {
        this.value = value;
    }

    public Object value() {
        return this.value;
    }

}
