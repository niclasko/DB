package org.nicsoft.DB.Parser.JSON;

public class JSONDataStructure {
	
	protected JSONKeyValue lastEntry;
	
	public JSONDataStructure() {
		this.lastEntry = null;
	}
	
	public void add(int value) {
		;
	}
	
	public void add(double value) {
		;
	}
	
	public void add(Object value) {
		;
	}
	
	public void addKey(String key) {
		;
	}
	
	public void add(String key, Object value) {
		;
	}
	
	protected void setLastEntryValue(Object value) {
		if(this.lastEntry != null) {
			this.lastEntry.setValue(value);
		}
	}
	
	public JSONKeyValue get(String key) {
		return null;
	}
	
	public JSONKeyValue get(int index) {
		return null;
	}
	
	public JSONKeyValue[] entries() {
		return null;
	}
	
	public JSONKeyValue lastEntry() {
		
		return this.lastEntry;
		
	}
	
	public void print() {
		System.out.println(this.toString());
	}
	
	public String toString() {
		
		StringBuilder stringBuilder =
			new StringBuilder();
			
		this.toString(stringBuilder);
			
		return stringBuilder.toString();
		
	}
	
	public void toString(StringBuilder stringBuilder) {
		;
	}
	
	public static String[] entriesToStringArray(JSONKeyValue[] keyValues) {
			
		String[] entries =
			new String[keyValues.length];
			
		for(int i=0; i<keyValues.length; i++) {
			
			entries[i] =
				keyValues[i].getValue().toString();
			
		}
		
		return entries;
		
	}
	
}