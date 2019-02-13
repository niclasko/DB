package org.nicsoft.DB.Data;

import org.nicsoft.DB.Query.Expression.Atom;
import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

public class DataSetColumnReference extends Atom {
	
	private DataSetColumn column;
	private String columnNameAlias;
	
	public DataSetColumnReference(DataSetColumn column, String columnNameAlias) {
		super(OperatorType.NONE, KeyWord.LITERAL);
		this.column = column;
		this.column.addReference(this);
		this.columnNameAlias = column.alias();
	}
	
	public DataSetColumn getDataSetColumn() {
		return this.column;
	}

	public void setDataSetColumn(DataSetColumn dataSetColumn) {
		this.column = dataSetColumn;
	}
	
	public Object value() {
		return this.column.value();
	}
	
	public String alias() {
		return this.columnNameAlias;
	}
	
	public String name() {
		return this.column.name();
	}
	
}