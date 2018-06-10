package com.acme.service.entity;

public class Assignee {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AvatarUrls getAvatarUrls() {
		return avatarUrls;
	}
	public void setAvatarUrls(AvatarUrls avatarUrls) {
		this.avatarUrls = avatarUrls;
	}
	private String name;
	private AvatarUrls avatarUrls;
}
