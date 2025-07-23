package com.bbp.BBPlatform.controller;

import java.security.Principal;
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
import com.bbp.BBPlatform.model.HospitalEntity;
import com.bbp.BBPlatform.repository.BloodRequestRepository;
import com.bbp.BBPlatform.repository.DonarsRepository;
import com.bbp.BBPlatform.repository.DonationRecordRepository;
import com.bbp.BBPlatform.service.DonarsEntityService;
import com.bbp.BBPlatform.service.DonationRecordService;
import com.bbp.BBPlatform.service.HospitalService;

@RestController
@RequestMapping("/donar")
public class DonarsController {
	
	private final DonarsEntityService donarService;
	private final HospitalService hospitalService;
	private final BloodRequestRepository bloodRequestRepo;
	@Autowired
    private DonationRecordRepository donationRecordRepo;
	 @Autowired
	    private DonationRecordService donationService;
	    @Autowired
	    private DonarsRepository donorRepo;
	
	@Autowired
	public DonarsController(DonarsEntityService donarService,HospitalService hospitalService,BloodRequestRepository bloodRequestRepo) {
		this.donarService = donarService;
		this.hospitalService = hospitalService;
		this.bloodRequestRepo = bloodRequestRepo;
	}
	//pass donar email
	    @GetMapping("/nearby-hospitals/{email}")
	    @PreAuthorize("hasAnyRole('DONAR', 'ADMIN')")
	    public ResponseEntity<List<HospitalEntity>> getNearbyHospitals(@PathVariable String email) {
	        List<HospitalEntity> nearbyHospitals = hospitalService.getNearbyHospitalsByDonor(email);
	        return ResponseEntity.ok(nearbyHospitals);
	    }
	    
	    @PutMapping("/donate/{requestId}")
	    @PreAuthorize("hasRole('DONAR')")
	    public ResponseEntity<String> raiseDonationIntent(
	    		@PathVariable(name = "requestId") int requestId,
	            Principal principal) {

	        DonarsEntity donor = donorRepo.findByEmail(principal.getName())
	                .orElseThrow(() -> new RuntimeException("Donor not found"));

	        BloodRequest request = bloodRequestRepo.findById(requestId)
	                .orElseThrow(() -> new RuntimeException("Request not found"));

	        // Optional: prevent duplicate intent raise
	        if (donationRecordRepo.existsByDonorAndRequest(donor, request)) {
	            return ResponseEntity.badRequest().body("You’ve already raised intent to donate for this request.");
	        }

	        // Just create an intent record with 0 units for now
	        DonationRecord intent = new DonationRecord();
	        intent.setDonor(donor);
	        intent.setRequest(request);
	        intent.setDonatedUnits(0); // quantity not donated yet
	        intent.setDonatedAt(null); // will be updated later on confirmation
	        donationRecordRepo.save(intent);

	        return ResponseEntity.ok("Intent to donate has been raised. Please visit the hospital to complete donation.");
	    }

}
