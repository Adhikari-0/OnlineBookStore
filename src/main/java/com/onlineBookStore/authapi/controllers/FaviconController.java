package com.onlineBookStore.authapi.controllers;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {
	
	@GetMapping("favicon.ico")
	public ResponseEntity<Resource> getFavicon() throws IOException {
	    ClassPathResource imgFile = new ClassPathResource("static/favicon.ico");
	    return ResponseEntity
	            .ok()
	            .contentType(MediaType.parseMediaType("image/x-icon"))
	            .body(imgFile);
	}

}
