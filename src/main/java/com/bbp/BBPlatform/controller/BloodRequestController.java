package com.bbp.BBPlatform.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbp.BBPlatform.dto.MultiHospitalBloodRequestDto;
import com.bbp.BBPlatform.model.BloodRequest;
import com.bbp.BBPlatform.model.DonarsEntity;
import com.bbp.BBPlatform.repository.BloodRequestRepository;
import com.bbp.BBPlatform.repository.DonarsRepository;
import com.bbp.BBPlatform.repository.DonationRecordRepository;
import com.bbp.BBPlatform.repository.HospitalRepository;
import com.bbp.BBPlatform.service.BloodRequestService;
import com.bbp.BBPlatform.service.DonationRecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hospital")
public class BloodRequestController {
    private final BloodRequestRepository bloodRequestRepo;
    private final HospitalRepository hospitalRepo;
    private final BloodRequestService bloodRequestService;
    @Autowired
    private DonationRecordService donationService;
    @Autowired
    private DonarsRepository donorRepo;
    @Autowired
    private DonationRecordRepository donationRecordRepo;
    @Autowired
    public BloodRequestController(BloodRequestRepository bloodRequestRepo, HospitalRepository hospitalRepo,BloodRequestService bloodRequestService) {
        this.bloodRequestRepo = bloodRequestRepo;
        this.hospitalRepo = hospitalRepo;
        this.bloodRequestService = bloodRequestService;
    }

    //adding new blood request from hospital side
    @PostMapping("/addBloodRequest")
    @PreAuthorize("hasAnyRole('HOSPITAL', 'ADMIN')")
    public ResponseEntity<?> addRequestsToMultipleHospitals(
    		@Valid @RequestBody MultiHospitalBloodRequestDto dto){
    	
    	List<BloodRequest> savedRequests = bloodRequestService.processMultiRequest(dto);

        if (savedRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No valid hospital IDs provided.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequests);
    }
   
}
