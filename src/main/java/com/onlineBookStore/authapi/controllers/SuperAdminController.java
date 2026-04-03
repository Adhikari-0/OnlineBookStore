package com.onlineBookStore.authapi.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.services.SuperAdminService;

@RequestMapping("/create")
@Controller
public class SuperAdminController {
	
	private final SuperAdminService superAdminService;
	
	public SuperAdminController(SuperAdminService superAdminService) {
		this.superAdminService = superAdminService;
	}
	
	@GetMapping("/listOfAdmin")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public String listOfAdmin(Model model) {
		 List<User> admins = superAdminService.getAllAdmins(); // fetch only ADMIN users
		    model.addAttribute("admins", admins);

		    return "admin-list"; // admin-list.html
	}

}
