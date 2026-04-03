package com.onlineBookStore.authapi.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.onlineBookStore.authapi.dtos.RegisterUserDto;
import com.onlineBookStore.authapi.services.UserService;

import jakarta.validation.Valid;

@RequestMapping("/create")
@Controller
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public String createAdminForm() {
		return "createAdmin";
	}

	@PostMapping("/admin")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public String createAdministrator(@Valid @ModelAttribute RegisterUserDto registerUserDto, BindingResult result,
			Model model) {

		// Validation errors
		if (result.hasErrors()) {
			return "create-admin";
		}

		try {
			userService.createAdministrator(registerUserDto);
			model.addAttribute("success", "Admin created successfully");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "createAdmin";
	}

}
