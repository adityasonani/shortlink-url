package com.shortlink.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shortlink.api.model.UrlModel;

public interface UrlRepository extends JpaRepository<UrlModel, Long>{
	
	boolean existsByShortUrl(String shortUrl);
	boolean existsByLongUrl(String longUrl);
	UrlModel findByLongUrl(String longUrl);
	UrlModel findByShortUrl(String shortUrl);
	
}
