package org.nicsoft.DB.Parser.JSON;

import java.util.Vector;

public class JSONArray extends JSONDataStructure {
	
	private Vector<JSONKeyValue> entries;
	
	public JSONArray() {
		
		this.entries =
			new Vector<JSONKeyValue>();
		
	}
	
	public void add(int value) {
		
		this.add(new Integer(value));
		
	}
	
	public void add(double value) {
		
		this.add(new Double(value));
		
	}
	
	public void add(Object value) {
		
		this.lastEntry =
			new JSONKeyValue(
				null,
				value
			);
		
		this.entries.add(
			this.lastEntry
		);
		
	}
	
	public JSONKeyValue get(int index) {
		return this.entries.get(index);
	}
	
	public JSONKeyValue[] entries() {
		
		return
			this.entries.toArray(
				new JSONKeyValue[this.entries.size()]
			);
		
	}
	
	public void toString(StringBuilder stringBuilder) {
		
		int i = 0;
		
		stringBuilder.append("[");
		
		for(JSONKeyValue keyValue : this.entries()) {
			
			if(i++ > 0) {
				stringBuilder.append(", ");
			}
			
			keyValue.toString(stringBuilder);
			
		}
		
		stringBuilder.append("]");
		
	}
	
}