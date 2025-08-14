package com.onlineBookStore.authapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBookStore.authapi.dtos.RegisterUserDto;
import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.services.UserService;


// Help to create admin user in the database
@RequestMapping("/admins")
@RestController
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
		User createdAdmin = userService.createAdministrator(registerUserDto);

		return ResponseEntity.ok(createdAdmin);
	}

}
