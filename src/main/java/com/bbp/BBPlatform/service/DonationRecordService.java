package com.bbp.BBPlatform.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bbp.BBPlatform.model.BloodRequest;
import com.bbp.BBPlatform.model.DonarsEntity;
import com.bbp.BBPlatform.model.DonationRecord;
import com.bbp.BBPlatform.repository.BloodRequestRepository;
import com.bbp.BBPlatform.repository.DonationRecordRepository;

@Service
public class DonationRecordService {
	    @Autowired
	    private BloodRequestRepository bloodRequestRepo;
	    @Autowired
	    private DonationRecordRepository donationRecordRepo;
	    @Autowired
	    private EmailService emailService;

	    public ResponseEntity<String> donateToRequest(int requestId, int donatedUnits, DonarsEntity donor) {
	        BloodRequest request = bloodRequestRepo.findById(requestId)
	                .orElseThrow(() -> new RuntimeException("Request not found"));

	        if (request.isFulfilled()) {
	            return ResponseEntity.badRequest().body("Request already fulfilled.");
	        }
	        if (donatedUnits <= 0 || donatedUnits > request.getQuantityInUnits()) {
	            return ResponseEntity.badRequest().body("Invalid donation amount.");
	        }
	        if (donationRecordRepo.existsByDonorAndRequest(donor, request)) {
	            return ResponseEntity.badRequest().body("You have already donated to this request.");
	        }

	        // Update Request
	        int newQuantity = request.getQuantityInUnits() - donatedUnits;
	        request.setQuantityInUnits(newQuantity);
	        if (newQuantity == 0) {
	            request.setFulfilled(true);
	            emailService.sendFulfillmentEmail(
	                    request.getHospital().getEmail(),
	                    "Request ID: " + request.getRequestId() + ", Blood Group: " + request.getBloodGroup()
	            );
	        }
	        request.setUpdatedAt(LocalDateTime.now());
	        bloodRequestRepo.save(request);

	        // Save donation record
	        DonationRecord record = new DonationRecord();
	        record.setDonor(donor);
	        record.setRequest(request);
	        record.setDonatedUnits(donatedUnits);
	        record.setDonatedAt(LocalDateTime.now());
	        donationRecordRepo.save(record);

	        return ResponseEntity.ok("Thank you for your donation! Remaining units: " + newQuantity);
	    }
	}
