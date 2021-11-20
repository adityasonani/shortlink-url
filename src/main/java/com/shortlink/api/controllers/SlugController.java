package com.shortlink.api.controllers;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shortlink.api.model.LongUrlRequestModel;
import com.shortlink.api.model.UrlModel;
import com.shortlink.api.services.SlugControllerService;


@RestController
@CrossOrigin
public class SlugController {
	
	@Autowired
	SlugControllerService slugControllerService;
	
	@GetMapping("/")
	public String check() {
		return "Thanks for checking ShortLink. Try creating a new ShortLink URL";
	}
	
//	@PostMapping("/generate/{longurl}")
//	public UrlModel ShortUrlController(@PathVariable(value="longurl") String longUrl) {
//		return slugControllerService.ShortUrlControllerService(longUrl);
//	}
	
	@PostMapping("/generate")
	public ResponseEntity<UrlModel> ShortUrlController(@RequestBody LongUrlRequestModel body) {
//		System.out.println("posted /generate withh body "+body.getLongUrl());
		return ResponseEntity.status(200).body(slugControllerService.ShortUrlControllerService(body));
	}
	
	@GetMapping("/{slug}")
	public ResponseEntity<?> redirectToLongUrlController(@PathVariable(value="slug") String shortUrl) throws URISyntaxException {
		URI uri = new URI(slugControllerService.redirectToLongUrlControllerService(shortUrl));
		System.out.println(uri);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, MOVED_PERMANENTLY);

//		return slugControllerService.redirectToLongUrlControllerService(shortUrl);
	}
}
