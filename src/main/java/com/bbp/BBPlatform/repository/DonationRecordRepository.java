package com.bbp.BBPlatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbp.BBPlatform.model.BloodRequest;
import com.bbp.BBPlatform.model.DonarsEntity;
import com.bbp.BBPlatform.model.DonationRecord;

@Repository
public interface DonationRecordRepository extends JpaRepository<DonationRecord, Integer> {
    boolean existsByDonorAndRequest(DonarsEntity donor, BloodRequest request);
  
    DonationRecord findByDonorAndRequest(DonarsEntity donor, BloodRequest request);

}