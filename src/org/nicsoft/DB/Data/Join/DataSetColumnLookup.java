package org.nicsoft.DB.Data.Join;

import org.nicsoft.DB.Data.DataSetColumn;

public class DataSetColumnLookup {
	
	private DataSetColumn column;
	private LookupStorage lookupStorage;
	
	public DataSetColumnLookup(DataSetColumn column) {
		
		this.column = column;
		this.lookupStorage = new LookupStorage();
		
	}
	
	public void addValueToIndex() {
		this.lookupStorage.write(
			column.value(),
			column.currentRowIndex()
		);
	}
	
}