package org.nicsoft.DB.Output;

import java.io.PrintWriter;

public class CSVWriter extends DataWriter {
	
	private static String BLANK = "";
	private static char QUOTE = '"';
	
	boolean extraInfoCalled;
	
	public CSVWriter(PrintWriter out) {
		super(out, DataWriterType.CSV);
		this.extraInfoCalled = false;
	}
	
	public void headerEntry(String entry, int entryId) {
		this.entry(entry, entryId);
	}
	
	public void entry(String entry, int entryId) {
		out.print((entryId > 0 ? DataWriter.FIELD_SEPARATOR : CSVWriter.BLANK));
		out.print(CSVWriter.QUOTE);
		
		for(int i=0; i<entry.length();i++) {
			if(entry.charAt(i) == CSVWriter.QUOTE) {
				out.print(CSVWriter.QUOTE);
			}
			out.print(entry.charAt(i));
		}
		
		out.print(CSVWriter.QUOTE);
	}
	
	public void entry(double entry, int entryId) {
		out.print((entryId > 0 ? DataWriter.FIELD_SEPARATOR : CSVWriter.BLANK));
		out.print(representNull(entry));
	}
	
	public void entry(int entry, int entryId) {
		out.print((entryId > 0 ? DataWriter.FIELD_SEPARATOR : CSVWriter.BLANK));
		out.print(entry);
	}
	
	public void nullEntry(int entryId) {
		out.print((entryId > 0 ? DataWriter.FIELD_SEPARATOR : CSVWriter.BLANK));
	}
	
	public void newRow() {
		super.newRow();
		out.print("\n");
		out.flush();
	}

	public void finish() {
		this.newRow();
	}
}