package org.nicsoft.DB.Parser.JSON;

import java.util.HashMap;

public class JSONAssociativeArray extends JSONDataStructure {
	
	private HashMap<String, JSONKeyValue> entries;
	
	public JSONAssociativeArray() {
		
		this.entries =
			new HashMap<String, JSONKeyValue>();
		
	}
	
	public void addKey(String key) {
		
		this.lastEntry =
			new JSONKeyValue(
				key,
				null
			);
		
		this.entries.put(
			key,
			this.lastEntry
		);
		
	}
	
	public void add(String key, Object value) {
		
		this.lastEntry =
			new JSONKeyValue(
				key,
				value
			);
		
		this.entries.put(
			key,
			this.lastEntry
		);
		
	}
	
	public void add(Object value) {
		
		this.setLastEntryValue(
			value
		);
		
	}
	
	public void add(int value) {
		
		this.setLastEntryValue(
			value
		);
		
	}
	
	public void add(double value) {
		
		this.setLastEntryValue(
			value
		);
		
	}
	
	public JSONKeyValue get(String key) {
		
		if(this.entries.containsKey(key)) {
			return this.entries.get(key);
		}
		
		
		return null;
		
	}
	
	public JSONKeyValue[] entries() {
		
		return
			this.entries.values().toArray(
				new JSONKeyValue[this.entries.size()]
			);
		
	}
	
	public JSONKeyValue lastEntry() {
		
		return this.lastEntry;
		
	}
	
	public void toString(StringBuilder stringBuilder) {
		
		int i = 0;
		
		stringBuilder.append("{");
		
		for(JSONKeyValue keyValue : this.entries()) {
			
			if(i++ > 0) {
				stringBuilder.append(", ");
			}
			
			stringBuilder.append(
				keyValue.getJSONFormattedKey()
			);
			
			keyValue.toString(stringBuilder);
			
			
		}
		
		stringBuilder.append("}");
		
	}
	
}