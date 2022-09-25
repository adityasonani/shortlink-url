package com.shortlink.api.controllers;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shortlink.api.model.LongUrlRequestModel;
import com.shortlink.api.model.UrlModel;
import com.shortlink.api.services.SlugControllerService;


@RestController
@CrossOrigin
public class SlugController {
	
	Logger log = LoggerFactory.getLogger(SlugController.class);
	
	@Autowired
	SlugControllerService slugControllerService;
	
	@GetMapping("/")
	public ResponseEntity<?> checkURL() throws URISyntaxException {
		URI uri = new URI("https://shortlink-app.herokuapp.com/");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, MOVED_PERMANENTLY);
	}
	
	@GetMapping("/check")
	public ResponseEntity<String> checkApp() {
		log.info("Healthy");
		return ResponseEntity.status(200).body("OK");
	}
	
	@PostMapping("/generate")
	public ResponseEntity<UrlModel> ShortUrlController(@RequestBody LongUrlRequestModel body) {
		return ResponseEntity.status(200).body(slugControllerService.ShortUrlControllerServiceNew(body));
	}
	
	@GetMapping("/{slug}")
	public ResponseEntity<?> redirectToLongUrlController(@PathVariable(value="slug") String shortUrl) throws URISyntaxException {
		log.info("Inside /slug :: shortUrl :: "+shortUrl);
		URI uri = new URI(slugControllerService.redirectToLongUrlControllerServiceNew(shortUrl));
        HttpHeaders httpHeaders = new HttpHeaders();
        log.info("uri :: "+uri);
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, MOVED_PERMANENTLY);
	}
}
