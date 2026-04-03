package com.onlineBookStore.authapi.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlineBookStore.authapi.dtos.RegisterUserDto;
import com.onlineBookStore.authapi.entities.Role;
import com.onlineBookStore.authapi.entities.RoleEnum;
import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.exceptions.UserAlreadyExistsException;
import com.onlineBookStore.authapi.repositories.RoleRepository;
import com.onlineBookStore.authapi.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();

		userRepository.findAll().forEach(users::add);

		return users;
	}

	public User createAdministrator(RegisterUserDto input) {

		if (userRepository.findByEmail(input.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("User already exists with this email");
		}

		Role role = roleRepository.findByName(RoleEnum.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));

		User user = new User();
		user.setFullName(input.getFullName());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setRole(role);

		return userRepository.save(user);
	}
	public void listOfAdminUser() {
		
	}
}