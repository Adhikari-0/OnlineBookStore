package com.onlineBookStore.authapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.entities.RoleEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	//Find user by email (for login + duplicate check)
	Optional<User> findByEmail(String email);
	
	//Get all users by role (ADMIN list)
	List<User> findByRole_Name(RoleEnum roleName);
	
	//Check if email exists
	boolean existsByEmail(String email);
}
