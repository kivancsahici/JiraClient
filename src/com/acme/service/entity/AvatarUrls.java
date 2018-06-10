package com.acme.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvatarUrls {
	public String getX4848() {
		return x4848;
	}

	public void setX4848(String x4848) {
		this.x4848 = x4848;
	}

    @JsonProperty("48x48")
	private String x4848;
}

