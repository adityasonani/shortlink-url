package com.shortlink.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="urldb")
public class UrlModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int url_id;
	@Column(name="long_url", length = 2048)
	private String longUrl;
	@Column(name="short_url")
	private String shortUrl;
	
	public UrlModel() {
	}

	public UrlModel(int url_id, String longUrl, String shortUrl) {
		super();
		this.url_id = url_id;
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	@Override
	public String toString() {
		return "UrlModel [url_id=" + url_id + ", longUrl=" + longUrl + ", shortUrl=" + shortUrl + "]";
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public int getUrl_id() {
		return url_id;
	}

	public void setUrl_id(int url_id) {
		this.url_id = url_id;
	}

}
