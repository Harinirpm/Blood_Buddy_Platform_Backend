package com.bbp.BBPlatform.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbp.BBPlatform.model.BloodRequest;
import com.bbp.BBPlatform.model.DonarsEntity;
import com.bbp.BBPlatform.model.DonationRecord;
import com.bbp.BBPlatform.repository.BloodRequestRepository;
import com.bbp.BBPlatform.repository.DonarsRepository;
import com.bbp.BBPlatform.repository.DonationRecordRepository;
import com.bbp.BBPlatform.repository.HospitalRepository;
import com.bbp.BBPlatform.service.BloodRequestService;
import com.bbp.BBPlatform.service.DonarsEntityService;
import com.bbp.BBPlatform.service.DonationRecordService;
import com.bbp.BBPlatform.service.EmailService;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

	private final DonarsEntityService donarService;
	private final BloodRequestRepository bloodRequestRepo;
	 @Autowired
	    private DonationRecordService donationService;
	    @Autowired
	    private DonarsRepository donorRepo;
	    @Autowired
	    private DonationRecordRepository donationRecordRepo;
	    @Autowired
	    private EmailService emailService;
	
	@Autowired
	public HospitalController(DonarsEntityService donarService,BloodRequestRepository bloodRequestRepo) {
		this.donarService = donarService;
		this.bloodRequestRepo = bloodRequestRepo;
	}
	//fetching donars based on location
	 @PreAuthorize("hasAnyRole('ADMIN','HOSPITAL')") 
	 @GetMapping("/match-donors/{bloodGroup}/{pincode}")
	    public ResponseEntity<List<DonarsEntity>> matchDonors(
	            @PathVariable String bloodGroup,
	            @PathVariable String pincode) {

	        List<DonarsEntity> matched = donarService.findMatchingDonors(bloodGroup, pincode);
	        return ResponseEntity.ok(matched);
	    }

	 @PutMapping("/donate/confirm-request/{requestId}")
	 @PreAuthorize("hasRole('HOSPITAL')")
	 public ResponseEntity<String> confirmDonation(
			 @PathVariable(name = "requestId") int requestId,
			 @RequestParam(name = "donatedUnits") int donatedUnits,
			 @RequestParam(name = "donorId") int donorId) {

	     BloodRequest request = bloodRequestRepo.findById(requestId)
	             .orElseThrow(() -> new RuntimeException("Blood request not found with ID: " + requestId));

	     DonarsEntity donor = donorRepo.findById(donorId)
	             .orElseThrow(() -> new RuntimeException("Donor not found with ID: " + donorId));

	     if (donatedUnits <= 0 || donatedUnits > request.getQuantityInUnits()) {
	         return ResponseEntity.badRequest().body("Invalid donation units.");
	     }

	     // Prevent duplicate donation confirmation
	     if (donationRecordRepo.existsByDonorAndRequest(donor, request) &&
	         donationRecordRepo.findByDonorAndRequest(donor, request).getDonatedUnits() > 0) {
	         return ResponseEntity.badRequest().body("Donor has already donated for this request.");
	     }

	     // Update quantity
	     int updatedQuantity = request.getQuantityInUnits() - donatedUnits;
	     request.setQuantityInUnits(updatedQuantity);
	     if (updatedQuantity == 0) {
	         request.setFulfilled(true);
	         emailService.sendEmail(
	                 request.getHospital().getEmail(),
	                 "Blood Request Fulfilled",
	                 "Hello " + request.getHospital().getHospitalName() + ",\n\n" +
	                 "Your blood request ID " + request.getRequestId() + " has been fulfilled. " +
	                 "Thank you for using our platform.\n\nRegards,\nBloodBuddy Team"
	             );
	     }
	     
	     request.setUpdatedAt(LocalDateTime.now());
	     bloodRequestRepo.save(request);

	     // Update the donation record
	     DonationRecord record = donationRecordRepo.findByDonorAndRequest(donor, request);
	     record.setDonatedUnits(donatedUnits);
	     record.setDonatedAt(LocalDateTime.now());
	     donationRecordRepo.save(record);
	     return ResponseEntity.ok("Donation confirmed. Remaining units: " + updatedQuantity);
	 }

}
