package com.bbp.BBPlatform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbp.BBPlatform.model.DonarsEntity;

@Repository
public interface DonarsRepository extends JpaRepository<DonarsEntity,Integer>{
	
    Optional<DonarsEntity> findByEmail(String email);
    List<DonarsEntity> findByIsVerifiedFalse();
    List<DonarsEntity> findByBloodGroupAndPincodeAndIsVerifiedTrue(String bloodGroup, String pincode);
    List<DonarsEntity> findByBloodGroupAndCityAndIsVerifiedTrue(String bloodGroup, String city);

}
