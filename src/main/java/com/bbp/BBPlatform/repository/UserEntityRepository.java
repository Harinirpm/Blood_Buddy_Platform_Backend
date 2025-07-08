package com.bbp.BBPlatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbp.BBPlatform.model.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Integer>{
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByName(String name);
	Optional<UserEntity> findByRole(String role);
	Optional<UserEntity> findByEmailAndPassword(String email, String password);
	
}
