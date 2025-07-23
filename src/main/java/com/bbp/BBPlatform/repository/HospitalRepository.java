package com.bbp.BBPlatform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbp.BBPlatform.model.HospitalEntity;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity,Integer>{
	Optional<HospitalEntity> findByEmail(String email);
	List<HospitalEntity> findByPincode(String pincode);
	List<HospitalEntity> findByIsVerifiedFalse();
}
