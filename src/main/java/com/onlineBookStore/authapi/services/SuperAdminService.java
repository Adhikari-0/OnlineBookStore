package com.onlineBookStore.authapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlineBookStore.authapi.dtos.RegisterUserDto;
import com.onlineBookStore.authapi.entities.Role;
import com.onlineBookStore.authapi.entities.RoleEnum;
import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.exceptions.UserAlreadyExistsException;
import com.onlineBookStore.authapi.repositories.RoleRepository;
import com.onlineBookStore.authapi.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class SuperAdminService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SuperAdminService(UserRepository userRepository,RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
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
	
	public Page<User> getAdmins(int page, String keyword) {
	    return userRepository.findByRole_NameAndFullNameContainingIgnoreCase(
	            RoleEnum.ADMIN,
	            keyword,
	            PageRequest.of(page, 5)
	    );
	}

	@Transactional
	public void deleteAdmin(int id) {
	    userRepository.deleteAdminById(id);
	}

	public User getUserById(int id) {
	    return userRepository.findById(id).orElseThrow();
	}

}
