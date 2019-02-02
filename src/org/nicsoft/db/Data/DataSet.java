package org.nicsoft.DB.Data;



import org.nicsoft.DB.Data.Join.DataSetColumnLookupManager;
import org.nicsoft.DB.Query.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.Collection;

public class DataSet extends StatementBuilder implements IDataSetReader {

	private static final String DATASET_ALIAS_PREFIX = "DS";
	private static int dataSetCount = 0;
	
	// Data set name & alias
	private String name;
	private String alias;

	protected int rowIndex;

	// Available columns in dataset
	protected HashMap<String, Integer> columnNameIndexes;
	// Sorted (in added order) map collection of columns by name
	protected LinkedHashMap<String, DataSetColumn> columns;
	// Collection of (possibly repeated) columns for lookup by index
	private Vector<DataSetColumnReference> columnReferences;

	// For joining
	private DataSetColumnLookupManager lookupManager;

	public DataSet() {
		this(null);
	}

	public DataSet(String name) {
		
		this.name = (name == null ? DataSet.generateDataSetName() : name);
		this.alias = this.name;

		this.rowIndex = 0;

		this.columnNameIndexes = new HashMap<String, Integer>();
		this.columns = new LinkedHashMap<String, DataSetColumn>();
		this.columnReferences = new Vector<DataSetColumnReference>();

		this.lookupManager = new DataSetColumnLookupManager(this);
		
	}

	public String name() {
		return this.name;
	}
	
	public String alias() {
		return this.alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public StatementBuilder as(String alias) throws Exception {
		return null;
	}

	public GroupBy groupBy() {
		return null;
	}

	public StatementBuilder bind() throws Exception {
		return null;
	}
	
	public int recordCount() {
		return -1;
	}
	
	public int columnCount() {
		return this.columnReferences.size();
	}

	public void addAllColumns() {
		;
	}

	public void addColumn(DataSetColumn column) {
        this.columns.put(
            column.name(),
            column
        );
    }

	public DataSetColumnReference addColumn(String name) throws Exception {

		if(!this.hasColumn(name)) {
			throw new Exception("Column \"" + name + "\" does not exist.");
		}

		DataSetColumn column = this.columnFactory(name);
		
		DataSetColumnReference columnReference =
			new DataSetColumnReference(
				column,
				column.alias()
			);
		
		this.columnReferences.add(columnReference);

		columnReference.alias(
			column.name() +
				(column.referenceCount() > 0 ? column.referenceCount() : "")
		);

        return columnReference;
		
	}

	public boolean hasColumn(String name) {
        return this.columnNameIndexes.containsKey(name);
    }

    public DataSetColumn getColumn(String name) {
        return this.columns.get(name);
    }

    public DataSetColumn columnFactory(String name) throws Exception {
        return null;
    }
	
	// For joins
	public void addLookup(String columnName) {
		if(this.columns.containsKey(columnName)) {
			this.lookupManager.addLookup(
				this.columns.get(columnName)
			);
		}
	}
	
	// Read next record
	public boolean nextRecord() throws Exception {
		return true;
	}

    // Move to first record
	public void reset() {
        ;
    }

	public int currentRowIndex() {
		return this.rowIndex;
	}
	
	public Vector<DataSetColumnReference> dataSetColumnReferences() {
		return this.columnReferences;
	}

	public Collection<DataSetColumn> columns() {
		return this.columns.values();
	}

	public void close() throws Exception { ; }

	public ColumnProjection columnProjection() {
		return null;
	}

	public static String generateDataSetName() {
		return DataSet.DATASET_ALIAS_PREFIX + DataSet.dataSetCount++;
	}

	public static void main(String args[]) {
		DataSet ds = new DataSet("test");
	}
	
}