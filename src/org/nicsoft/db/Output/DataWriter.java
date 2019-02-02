package org.nicsoft.DB.Output;

import java.io.PrintWriter;

public class DataWriter {
	public static final String FIELD_SEPARATOR = ",";
	protected PrintWriter out;
	private DataWriterType dataWriterType;
	protected int recordCount;
	
	public DataWriter(PrintWriter out, DataWriterType dataWriterType) {
		this.out = out;
		this.dataWriterType = dataWriterType;
		this.recordCount = 0;
	}
	
	public int getRecordCount() {
		return this.recordCount;
	}
	
	public DataWriterType getDataWriterType() {
		return this.dataWriterType;
	}
	
	public void init() {
		;
	}
	
	public void extraInfo(String key, String value) {
		;
	}
	
	public void finish() {
		out.flush();
	}
	
	public void headerEntry(String entry, int entryId) {
		;
	}
	
	public void entry(String entry, int entryId) {
		;
	}
	
	public void entry(double entry, int entryId) {
		;
	}
	
	public void entry(int entry, int entryId) {
		;
	}
	
	public void nullEntry(int entryId) {
		;
	}
	
	public void newRow() {
		this.recordCount++;
	}
	
	public void beginRow() {
		;
	}
	
	public void endRow() {
		;
	}
	
	public String representNull(double entry) {
		return (!Double.isNaN(entry) ? entry + "" : "null");
	}
}