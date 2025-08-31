package com.onlineBookStore.authapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlineBookStore.authapi.dtos.LoginUserDto;
import com.onlineBookStore.authapi.dtos.RegisterUserDto;
import com.onlineBookStore.authapi.entities.RoleEnum;
import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.services.AuthenticationService;
import com.onlineBookStore.authapi.services.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {
	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public String register(RegisterUserDto registerUserDto, HttpServletResponse response, Model model) {
		try {
			User registeredUser = authenticationService.signup(registerUserDto);

			LoginUserDto loginUserDto = new LoginUserDto();
			loginUserDto.setEmail(registerUserDto.getEmail());
			loginUserDto.setPassword(registerUserDto.getPassword());
			User authenticatedUser = authenticationService.authenticate(loginUserDto);

			String jwtToken = jwtService.generateToken(authenticatedUser);

			Cookie cookie = new Cookie("OnlineBookStore", jwtToken);
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60);
			response.addCookie(cookie);

			return "redirect:/book";

		} catch (Exception e) {
			model.addAttribute("error", "Error during creating user");
			return "Login-Register";
		}

	}

	@GetMapping("/signuplogin")
	public String authenticateForm() {
		return "Login-Register";
	}

	@PostMapping("/login")
	public String authenticate(LoginUserDto loginUserDto, HttpServletResponse response, Model model) {
		try {
			User authenticatedUser = authenticationService.authenticate(loginUserDto);

			String jwtToken = jwtService.generateToken(authenticatedUser);

			Cookie cookie = new Cookie("OnlineBookStore", jwtToken);
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60);
			response.addCookie(cookie);

			if (RoleEnum.SUPER_ADMIN == authenticatedUser.getRole().getName()) {
				return "redirect:/create";
			} else if (RoleEnum.ADMIN == authenticatedUser.getRole().getName()) {
				return "redirect:/admin";
			} else {
				return "redirect:/book";
			}

		} catch (Exception e) {
			model.addAttribute("error", "Invalid username or password");
			return "Login-Register";
		}
	}
}
