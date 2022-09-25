package com.shortlink.api.services;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shortlink.api.model.Counter;
import com.shortlink.api.model.LongUrlRequestModel;
import com.shortlink.api.model.UrlModel;
import com.shortlink.api.repository.CounterRepository;
import com.shortlink.api.repository.UrlRepository;

@Service
public class SlugControllerService {

	Logger log = LoggerFactory.getLogger(SlugControllerService.class);

	@Autowired
	UrlRepository urlRepository;

	@Autowired
	CounterRepository counterRepository;

	static Long COUNTER;
	String elements = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public String longToShort(String url) {
		try {
			COUNTER = counterRepository.findAll().get(0).getCount();
		} catch (Exception e) {
			COUNTER = 100000000L;
		}
		log.info("Counter is:: " + COUNTER);
		String shorturl = base10ToBase62(COUNTER);
		Long curr = COUNTER;
		counterRepository.deleteAll();
		Counter counter = new Counter();
		counter.setCount(curr + 1);
		counterRepository.save(counter);
		return "/" + shorturl;
	}

	public String shortToLong(String url) {
		String longUrl = urlRepository.findByShortUrl(url).getLongUrl();
		return longUrl;
	}

	public String base10ToBase62(long n) {
		StringBuilder sb = new StringBuilder();
		while (n != 0) {
			sb.insert(0, elements.charAt((int) (n % 62)));
			n /= 62;
		}
		while (sb.length() < 5) {
			sb.insert(0, '0');
		}
		return sb.toString();
	}

	public UrlModel ShortUrlControllerServiceNew(LongUrlRequestModel body) {
		if (urlRepository.existsByLongUrl(body.getLongUrl())) {
			return urlRepository.findByLongUrl(body.getLongUrl());
		}
		String shortUrl = longToShort(body.getLongUrl());
		UrlModel urlModel = new UrlModel();
		urlModel.setLongUrl(body.getLongUrl());
		urlModel.setShortUrl(shortUrl.substring(1));
		urlRepository.save(urlModel);
		log.info("Longtoshort :: " + shortUrl);
		return urlModel;
	}

	public String redirectToLongUrlControllerServiceNew(String shortUrl) {
		String redirect = "/";
		if (urlRepository.existsByShortUrl(shortUrl)) {
			UrlModel urlModel = urlRepository.findByShortUrl(shortUrl);
			redirect = urlModel.getLongUrl();
		}
		log.info("Inside redirectToLongUrlControllerServiceNew() :: " + redirect);
		return redirect;
	}

	public UrlModel ShortUrlControllerService(LongUrlRequestModel body) {
		if (urlRepository.existsByLongUrl(body.getLongUrl())) {
			return urlRepository.findByLongUrl(body.getLongUrl());
		}

		// Base-62
		String code = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int min = 0, max = 59;
		String shortUrl = "";

		do {
			shortUrl = "";
			for (int i = 0; i < 4; i++) {
				int randomIndex = ThreadLocalRandom.current().nextInt(min, max + 1);
				shortUrl += code.charAt(randomIndex);
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
		log.info("Redirect :: " + redirect);
		return redirect;
	}

	@Override
	public String toString() {
		return "SlugControllerService [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
