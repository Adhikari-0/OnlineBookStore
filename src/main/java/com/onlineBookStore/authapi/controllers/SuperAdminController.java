package com.onlineBookStore.authapi.controllers;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlineBookStore.authapi.dtos.RegisterUserDto;
import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.services.SuperAdminService;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('SUPER_ADMIN')")
@Controller
@RequestMapping("/SuperAdmin")
public class SuperAdminController {

	private final SuperAdminService superAdminService;

	public SuperAdminController(SuperAdminService superAdminService) {
		this.superAdminService = superAdminService;
	}
	@GetMapping
	public String createAdminForm() {
		return "createAdmin";
	}
	@PostMapping("/create")
	public String createAdministrator(@Valid @ModelAttribute RegisterUserDto registerUserDto, BindingResult result,
			Model model) {

		// Validation errors
		if (result.hasErrors()) {
			return "create-admin";
		}

		try {
			superAdminService.createAdministrator(registerUserDto);
			model.addAttribute("success", "Admin created successfully");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "createAdmin";
	}

	// List with pagination + search
    @GetMapping("/admins")
    public String getAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            Model model
    ) {
    	System.out.println(page);
    	System.out.println(keyword);
        Page<User> adminPage = superAdminService.getAdmins(page, keyword);

        model.addAttribute("admins", adminPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", adminPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "admin-list";
    }

	// Delete admin
	@GetMapping("/delete/{id}")
	public String deleteAdmin(@PathVariable int id) {
		superAdminService.deleteAdmin(id);
		return "redirect:/SuperAdmin/admins";
	}
}
