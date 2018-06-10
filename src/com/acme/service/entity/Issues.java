package com.acme.service.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Issues {
	public List<AgileIssue> getIssues() {
		return issues;
	}

	public void setIssues(List<AgileIssue> issues) {
		this.issues = issues;
	}

	private List<AgileIssue> issues;
}
