package org.nicsoft.DB.Query;

import org.nicsoft.DB.Data.DataSet;

/**
 * Created by nkjalloh on 14/11/2016.
 */
public class Join {

    private From from;
    private DataSet leftDataSet;
    private DataSet rightDataSet;
    private On on;

    public Join(From from) {
        this.from = from;
        this.on = new On(this.from);
    }

    public Join leftDataSet(DataSet leftDataSet) {
        this.leftDataSet = leftDataSet;
        return this;
    }

    public Join rightDataSet(DataSet rightDataSet) {
        this.rightDataSet = rightDataSet;
        return this;
    }

    public From from() {
        return this.from;
    }

    public On on() {
        return this.on;
    }

}
