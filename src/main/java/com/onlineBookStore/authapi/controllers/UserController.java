package com.onlineBookStore.authapi.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('USER')")
@RestController
public class UserController {
	
}
