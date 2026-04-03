package com.onlineBookStore.authapi.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlineBookStore.authapi.dtos.LoginUserDto;
import com.onlineBookStore.authapi.dtos.RegisterUserDto;
import com.onlineBookStore.authapi.entities.Role;
import com.onlineBookStore.authapi.entities.RoleEnum;
import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.exceptions.UserAlreadyExistsException;
import com.onlineBookStore.authapi.repositories.RoleRepository;
import com.onlineBookStore.authapi.repositories.UserRepository;

@Service
public class AuthenticationService {
	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	public User signup(RegisterUserDto input) {
		 if (userRepository.existsByEmail(input.getEmail())) {
	            throw new UserAlreadyExistsException("User already exists with this email");
	        }
		 Role optionalRole = roleRepository.findByName(RoleEnum.USER)
	                .orElseThrow(() -> new RuntimeException("Role not found"));
		 
			User user = new User();
			user.setFullName(input.getFullName());
			user.setEmail(input.getEmail());
			user.setPassword(passwordEncoder.encode(input.getPassword()));
			user.setRole(optionalRole);

			try {
	            return userRepository.save(user);
	        } catch (org.springframework.dao.DataIntegrityViolationException e) {
	            throw new UserAlreadyExistsException("User already exists with this email");
	        }
	}

	public User authenticate(LoginUserDto input) {
		System.out.println(input.getEmail() + " " + input.getPassword());
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}
	public User getCurrentUser() {
	    return (User) SecurityContextHolder
	            .getContext()
	            .getAuthentication()
	            .getPrincipal();
	}
}