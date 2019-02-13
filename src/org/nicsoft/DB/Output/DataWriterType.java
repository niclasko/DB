package org.nicsoft.DB.Output;

public enum DataWriterType {
	CSV(0),
	JSON_TABULAR(1),
	JSON(2),
	PERSISTED_RESULTSET(3);
	
	public static final int SIZE = DataWriterType.values().length;
	
	private int id;

	DataWriterType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}