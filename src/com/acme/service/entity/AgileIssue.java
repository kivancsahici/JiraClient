package com.acme.service.entity;

public class AgileIssue implements Comparable<AgileIssue>{
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Fields getFields() {
		return fields;
	}
	public void setFields(Fields fields) {
		this.fields = fields;
	}
	private String key;
	private Fields fields;
	@Override
	public int compareTo(AgileIssue o) {
		// TODO Auto-generated method stub
		int id = this.getFields().getStatus().getStatusCategory().getId();
		int thatId = o.getFields().getStatus().getStatusCategory().getId();
		if(id == thatId)  
			return 0;  
		else if(id > thatId)  
			return 1;  
		else  
			return -1;  
	}
}
