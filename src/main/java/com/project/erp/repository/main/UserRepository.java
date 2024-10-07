package com.project.erp.repository.main;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erp.entity.main.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	User findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	Optional<User> findById(UUID id);

	Optional<User> findByUsernameOrEmail(String username, String email);



	

}
