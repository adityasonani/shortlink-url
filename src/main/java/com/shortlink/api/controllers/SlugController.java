package com.shortlink.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "http://localhost:4200")
public class SlugController {
	
	@Autowired
	SlugControllerService slugControllerService;
	
	@GetMapping("/")
	public String check() {
		return "OK";
	}
	
//	@PostMapping("/generate/{longurl}")
//	public UrlModel ShortUrlController(@PathVariable(value="longurl") String longUrl) {
//		return slugControllerService.ShortUrlControllerService(longUrl);
//	}
	
	@PostMapping("/generate")
	public ResponseEntity<UrlModel> ShortUrlController(@RequestBody LongUrlRequestModel body) {
		System.out.println("posted /generate withh body "+body.getLongUrl());
		return ResponseEntity.status(200).body(slugControllerService.ShortUrlControllerService(body));
	}
	
	@RequestMapping("/get/{slug}")
	public String redirectToLongUrlController(@PathVariable(value="slug") String shortUrl) {
		return slugControllerService.redirectToLongUrlControllerService(shortUrl);
	}
}
