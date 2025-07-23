package com.bbp.BBPlatform.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbp.BBPlatform.dto.MultiHospitalBloodRequestDto;
import com.bbp.BBPlatform.model.BloodRequest;
import com.bbp.BBPlatform.model.HospitalEntity;
import com.bbp.BBPlatform.repository.BloodRequestRepository;
import com.bbp.BBPlatform.repository.HospitalRepository;

@Service
public class BloodRequestService {
	private final HospitalRepository hospitalRepo;
	private final BloodRequestRepository bloodRequestRepo;
	
	@Autowired
	public BloodRequestService(HospitalRepository hospitalRepo, BloodRequestRepository bloodRequestRepo) {
		this.hospitalRepo = hospitalRepo;
		this.bloodRequestRepo = bloodRequestRepo;
	}
	//this methode is to store Each request is stored separately per hospital.
	public List<BloodRequest> processMultiRequest(MultiHospitalBloodRequestDto dto){
		List<BloodRequest> savedRequests = new ArrayList<>();
		
		//logic to store seperately
		for(MultiHospitalBloodRequestDto.HospitalRequest hospitalReq : dto.getHospitalRequests()) {
			Optional<HospitalEntity> hospitalOpt = hospitalRepo.findById(hospitalReq.getHospitalId());
			if(hospitalOpt.isEmpty()) {
				continue;
			}
			HospitalEntity hospital = hospitalOpt.get();
			BloodRequest request = new BloodRequest();
			
			request.setBloodGroup(dto.getBloodGroup());
			request.setRequiredLocation(dto.getRequiredLocation());
			request.setPincode(dto.getPincode());
			request.setUrgencyLevel(dto.getUrgencyLevel());
			request.setNeededByDate(dto.getNeededByDate());
			request.setQuantityInUnits(hospitalReq.getQuantityInUnits());
			request.setHospital(hospital);
			request.setFulfilled(false);
			request.setCreatedAt(LocalDateTime.now());
			request.setUpdatedAt(LocalDateTime.now());
			
			savedRequests.add(bloodRequestRepo.save(request));
		}
		return savedRequests;
	}
}
