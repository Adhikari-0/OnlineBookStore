package com.onlineBookStore.authapi.services;

import org.springframework.stereotype.Service;

import com.onlineBookStore.authapi.dtos.LoginUserDto;
import com.onlineBookStore.authapi.entities.RoleEnum;
import com.onlineBookStore.authapi.entities.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthHelperService {

	private final JwtService jwtService;
	private final AuthenticationService authenticationService;

	public AuthHelperService(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	public String authenticateAndRedirect(String email, String password, HttpServletResponse response) {
		// 1️⃣ Authenticate
		User authenticatedUser = authenticationService.authenticate(new LoginUserDto());

		// 2️⃣ Generate JWT
		String jwtToken = jwtService.generateToken(authenticatedUser);

		// 3️⃣ Store JWT in cookie
		Cookie cookie = new Cookie("OnlineBookStore", jwtToken);
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60);
		response.addCookie(cookie);

		// 4️⃣ Redirect by role
		return (RoleEnum.ADMIN == authenticatedUser.getRole().getName()) ? "redirect:/admin" : "redirect:/book";
	}
}
