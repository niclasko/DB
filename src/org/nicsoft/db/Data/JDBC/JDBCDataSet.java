package org.nicsoft.DB.Data.JDBC;

import org.nicsoft.DB.Data.DataSet;
import org.nicsoft.DB.Data.DataSetColumn;
import org.nicsoft.DB.Data.DataSetColumnReference;
import org.nicsoft.DB.Data.JDBC.data.DataSetFactory;
import org.nicsoft.DB.Logging.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JDBCDataSet extends DataSet {
	
	private ResultSet jdbcResultSet;
	private ResultSetMetaData jdbcResultSetMetaData;
	
	public JDBCDataSet(String name, ResultSet jdbcResultSet) throws SQLException {
		
		super(name);
		this.jdbcResultSet = jdbcResultSet;
		this.jdbcResultSetMetaData = jdbcResultSet.getMetaData();
		
		for(int i=0; i<this.jdbcResultSetMetaData.getColumnCount(); i++) {
			this.columnNameIndexes.put(
				this.jdbcResultSetMetaData.getColumnName(i+1),
				(i+1)
			);
		}
	}
	
	public void addAllColumns() {
		try {
			for(int i=0; i<this.jdbcResultSetMetaData.getColumnCount(); i++) {
				this.columnFactory(this.jdbcResultSetMetaData.getColumnName(i+1));
			}
		} catch (SQLException e) {
			Logger.log(e);
		}
	}

    public DataSetColumn columnFactory(String name) {

        DataSetColumn column = null;

        if(this.columnNameIndexes.containsKey(name)) {

            column = this.getColumn(name);

            if(column == null) {
                column =
                    new JDBCDataSetColumn(
                        this,
                        name,
                        this.columnNameIndexes.get(name)
                    );
                this.addColumn(column);
            }

        }

        return column;
    }
	
	// Read next record
	public boolean nextRecord() {
		try {
			return jdbcResultSet.next();
		} catch(SQLException e) {
			Logger.log(e);
		}
		return false;
	}

	public void reset() {
        try {
            jdbcResultSet.beforeFirst();
        } catch(SQLException e) {
            Logger.log(e);
        }
    }

    public void close() throws Exception {
		if(this.jdbcResultSet != null) {
			this.jdbcResultSet.close();
		}
	}
	
	public ResultSet jdbcResultSet() {
		return this.jdbcResultSet;
	}
	
	public static void main(String args[]) {
		
		try {
			
			DataSet dataSet =
				DataSetFactory.getExcelDataSet(
					"./src/org/nicsoft/DB/Data/data/company_addresses.xlsx",
					"et"
				);
			
			dataSet.addAllColumns();

			dataSet.reset();
			
			for(DataSetColumnReference column : dataSet.dataSetColumnReferences()) {
				System.out.print(column.alias() + ",");
			}
			System.out.println();

			while(dataSet.nextRecord()) {
				for(DataSetColumnReference column : dataSet.dataSetColumnReferences()) {
					System.out.print(column.value() + ",");
				}
				System.out.println();
			}
			
		} catch (Exception e) {
			Logger.log(e);
		}
		
	}
	
}