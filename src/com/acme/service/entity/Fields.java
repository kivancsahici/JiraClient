package com.acme.service.entity;

public class Fields {
	public Assignee getAssignee() {
		return assignee;
	}
	public void setAssignee(Assignee assignee) {
		this.assignee = assignee;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	private Assignee assignee;
	private Status status;
	private String summary;
}
