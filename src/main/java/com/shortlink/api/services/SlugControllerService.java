package com.shortlink.api.services;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shortlink.api.model.LongUrlRequestModel;
import com.shortlink.api.model.UrlModel;
import com.shortlink.api.repository.UrlRepository;

@Service
public class SlugControllerService {
	
	@Autowired
	UrlRepository urlRepository;
	
	public UrlModel ShortUrlControllerService(LongUrlRequestModel body) {
		if (urlRepository.existsByLongUrl(body.getLongUrl())) {
			return urlRepository.findByLongUrl(body.getLongUrl());
		}
		
		//Base-62
		String code="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int min=0, max=59;
		String shortUrl = "";
		
		do {
			shortUrl = "";
			for (int i=0; i<4; i++) {
				int randomIndex=ThreadLocalRandom.current().nextInt(min, max + 1);
				shortUrl+=code.charAt(randomIndex);
			}
		} while (urlRepository.existsByShortUrl(shortUrl));
		
		UrlModel urlModel = new UrlModel();
		urlModel.setLongUrl(body.getLongUrl());
		urlModel.setShortUrl(shortUrl);
		urlRepository.save(urlModel);
		return urlModel;
	}
	
	public String redirectToLongUrlControllerService(String shortUrl) {
		String redirect = "/";
		if (urlRepository.existsByShortUrl(shortUrl)) {
			UrlModel urlModel = urlRepository.findByShortUrl(shortUrl);
			redirect = urlModel.getLongUrl();
		} 
		return redirect;
	}

	@Override
	public String toString() {
		return "SlugControllerService [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
