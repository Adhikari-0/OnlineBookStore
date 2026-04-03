package com.onlineBookStore.authapi.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.onlineBookStore.authapi.entities.RoleEnum;
import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.repositories.UserRepository;

@Service
public class SuperAdminService {
	
	private final UserRepository userRepository;
	public SuperAdminService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public List<User> getAllAdmins() {
	    return userRepository.findByRole_Name(RoleEnum.ADMIN);
	}

}
