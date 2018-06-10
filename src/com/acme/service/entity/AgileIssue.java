package com.acme.service.entity;

public class AgileIssue {
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
}
