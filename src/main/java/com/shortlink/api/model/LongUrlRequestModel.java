package com.shortlink.api.model;

public class LongUrlRequestModel {
	private String longUrl;

	public LongUrlRequestModel() {
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public LongUrlRequestModel(String longUrl) {
		super();
		this.longUrl = longUrl;
	}

	@Override
	public String toString() {
		return "LongUrlRequestModel [longUrl=" + longUrl + "]";
	}

}
