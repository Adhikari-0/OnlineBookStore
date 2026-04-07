package com.onlineBookStore.authapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlineBookStore.authapi.entities.User;
import com.onlineBookStore.authapi.entities.RoleEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	// Find user by email (for login + duplicate check)
	Optional<User> findByEmail(String email);

	// Get all users by role (ADMIN list)
	List<User> findByRole_Name(RoleEnum roleName);

	// Check if email exists
	boolean existsByEmail(String email);

	@Modifying
	@Query("DELETE FROM User u WHERE u.id = :id")
	void deleteAdminById(int id);
	
	// Find by Role name and FullName containing keyword (ignore case)
    Page<User> findByRole_NameAndFullNameContainingIgnoreCase(RoleEnum role, String fullName, Pageable pageable);
}
