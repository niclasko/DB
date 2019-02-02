package org.nicsoft.DB.Data;

import org.nicsoft.DB.Query.Expression.Atom;
import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

import java.util.Vector;

public class DataSetColumn extends Atom {
	
	private DataSet dataSet;
	private String name;
	private Vector<DataSetColumnReference> dataSetColumnReferences;

	public DataSetColumn() {
		this(null, null);
	}
	
	public DataSetColumn(DataSet dataSet, String name) {
		super(OperatorType.NONE, KeyWord.LITERAL);
		this.dataSet = dataSet;
		this.name = name;
		this.dataSetColumnReferences = new Vector<DataSetColumnReference>();
	}

	public void addReference(DataSetColumnReference dataSetColumnReference) {
		this.dataSetColumnReferences.add(dataSetColumnReference);
	}

	public Vector<DataSetColumnReference> dataSetColumnReferences() {
		return this.dataSetColumnReferences;
	}
	
	public String name() {
		return this.name;
	}
	
	public String alias() {
		return this.name + (this.referenceCount() > 1 ? this.referenceCount() : "");
	}

	public int referenceCount() {
		return this.dataSetColumnReferences.size();
	}
	
	public Object value() {
		return null;
	}
	
	public int currentRowIndex() {
		return this.dataSet.currentRowIndex();
	}

	public DataSet getDataSet() {
		return this.dataSet;
	}
	
}