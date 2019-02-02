package org.nicsoft.DB.Data.JDBC;

import org.nicsoft.DB.Data.DataSetColumn;

public class JDBCDataSetColumn extends DataSetColumn {
	
	private JDBCDataSet jdbcDataSet;
	private int columnIndex;
	
	public JDBCDataSetColumn(JDBCDataSet dataSet, String name, int columnIndex) {
		super(dataSet, name);
		this.jdbcDataSet = dataSet;
		this.columnIndex = columnIndex;
	}
	
	public Object value() {
		try {
			if(this.jdbcDataSet.jdbcResultSet().isBeforeFirst()) {
				this.jdbcDataSet.nextRecord();
			}
			return this.jdbcDataSet.jdbcResultSet().getObject(this.columnIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}