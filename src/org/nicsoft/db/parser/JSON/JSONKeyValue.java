package org.nicsoft.DB.Parser.JSON;

public class JSONKeyValue {
	
	private String key;
	private Object value;
	
	public JSONKeyValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getJSONFormattedKey() {
		
		return
			JSONBuilder.quote() +
			JSONBuilder.jsonEscape(key) +
			JSONBuilder.quote() + ": ";
		
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	private void append(StringBuilder stringBuilder, Integer value) {
		
		stringBuilder.append(
			this.value.toString()
		);
		
	}
	
	private void append(StringBuilder stringBuilder, Double value) {
		
		stringBuilder.append(
			this.value.toString()
		);
		
	}
	
	private void append(StringBuilder stringBuilder, Boolean value) {
		
		stringBuilder.append(
			this.value.toString()
		);
		
	}
	
	private void append(StringBuilder stringBuilder, Long value) {
		
		stringBuilder.append(
			this.value.toString()
		);
		
	}
	
	private void append(StringBuilder stringBuilder, Object value) {
		
		this.append(
			stringBuilder,
			this.value.toString()
		);
		
	}
	
	private void append(StringBuilder stringBuilder, String value) {
		
		stringBuilder.append(
			JSONBuilder.quote() +
			JSONBuilder.jsonEscape(
				this.value.toString()
			) +
			JSONBuilder.quote()
		);
		
	}
	
	public void toString(StringBuilder stringBuilder) {
		
		if(	this.value != null &&
			this.value.getClass() != null &&
			this.value.getClass().getSuperclass() != null &&
			this.value.getClass().getSuperclass().equals(JSONDataStructure.class)	) {
			
			((JSONDataStructure)this.value).toString(
				stringBuilder
			);
			
		} else {
			
			if(this.value != null) {
				
				if(this.value instanceof Double) {
					
					this.append(
						stringBuilder,
						(Double)this.value
					);
					
				} else if(this.value instanceof Integer) {
					
					this.append(
						stringBuilder,
						(Integer)this.value
					);
					
				} else {
					
					this.append(
						stringBuilder,
						this.value
					);
					
				}
				
			} else if(this.value == null) {
				
				stringBuilder.append(
					"null"
				);
				
			}
			
			
		}
		
	}
	
}