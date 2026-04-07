package com.onlineBookStore.authapi.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.onlineBookStore.authapi.entities.RoleEnum;
import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SuperAdminService {
	
	private final UserRepository userRepository;
	public SuperAdminService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public List<User> getAllAdmins() {
	    return userRepository.findByRole_Name(RoleEnum.ADMIN);
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
