package org.nicsoft.DB.Data.Join;

import org.nicsoft.DB.Data.DataSet;
import org.nicsoft.DB.Data.DataSetColumn;

import java.util.HashMap;

public class DataSetColumnLookupManager {
	
	private DataSet dataSet;
	private HashMap<String, DataSetColumnLookup> columnLookups;
	
	public DataSetColumnLookupManager(DataSet dataSet) {
		this.dataSet = dataSet;
		this.columnLookups = new HashMap<String, DataSetColumnLookup>();
	}
	
	public void addLookup(DataSetColumn column) {
		if(!this.columnLookups.containsKey(column.name())) {
			this.columnLookups.put(
				column.name(),
				new DataSetColumnLookup(column)
			);
		}
	}
	
}