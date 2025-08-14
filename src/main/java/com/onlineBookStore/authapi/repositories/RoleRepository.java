package com.onlineBookStore.authapi.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.onlineBookStore.authapi.entities.Role;
import com.onlineBookStore.authapi.entities.RoleEnum;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	Optional<Role> findByName(RoleEnum name);

}
