package org.nicsoft.DB.Data;

import org.nicsoft.DB.Data.JDBC.JDBCDataSetColumn;
import org.nicsoft.DB.Logging.Logger;

import java.sql.SQLException;

/**
 * Created by nkjalloh on 05/01/2017.
 */
public class DummyDataSet extends DataSet {

    public DummyDataSet(String name) {
        super(name);
        this.rowIndex = 0;
    }

    public DummyDataSet() {
        this(null);
    }

    public int recordCount() {
        return 1;
    }

    // Read next record
    public boolean nextRecord() {
        return (this.rowIndex++ < 1);
    }

    // Move to first record
    public void reset() {
        this.rowIndex = 0;
    }

}
