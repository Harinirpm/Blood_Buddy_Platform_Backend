package com.bbp.BBPlatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbp.BBPlatform.model.BloodRequest;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Integer> {
    List<BloodRequest> findByIsFulfilledFalse();
}
