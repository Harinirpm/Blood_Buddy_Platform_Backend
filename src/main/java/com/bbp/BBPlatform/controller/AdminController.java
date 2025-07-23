package com.bbp.BBPlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbp.BBPlatform.model.DonarsEntity;
import com.bbp.BBPlatform.model.HospitalEntity;
import com.bbp.BBPlatform.repository.DonarsRepository;
import com.bbp.BBPlatform.repository.HospitalRepository;
import com.bbp.BBPlatform.service.DonarsEntityService;
import com.bbp.BBPlatform.service.HospitalService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private final DonarsEntityService donarService;
	private final DonarsRepository donarRepo;
	private final HospitalService hospitalService;
	private final HospitalRepository hospitalRepo;
	
	@Autowired
	public AdminController(DonarsEntityService donarService,
			DonarsRepository donarRepo, HospitalService hospitalService,
			HospitalRepository hospitalRepo) 
	{
		this.donarService = donarService;
		this.donarRepo = donarRepo;
		this.hospitalService = hospitalService;
		this.hospitalRepo = hospitalRepo;
	}
	
	//fetch unverified donars details
	@GetMapping("/unverifiedDonars")
	public List<DonarsEntity> getUnverifiedDonars() {
	    return donarRepo.findByIsVerifiedFalse();
	}

	//donar approval by admin
	@PutMapping("/approveDonar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DonarsEntity> approveDonar (@PathVariable int id){
		DonarsEntity approvedDonar = donarService.approveDonar(id);
		return new ResponseEntity<>(approvedDonar, HttpStatus.OK);
	}
	// hospital approved by admin
	@PutMapping("/approveHospital/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HospitalEntity> approveHospital(@PathVariable int id) {
	    HospitalEntity approved = hospitalService.approveHospital(id);
	    return new ResponseEntity<>(approved, HttpStatus.OK);
	}
	// Fetch all unverified hospitals
	 @GetMapping("/unverifiedHospitals")
	 @PreAuthorize("hasRole('ADMIN')")
	 public List<HospitalEntity> getUnverifiedHospitals() {
	    return hospitalRepo.findByIsVerifiedFalse();
	 }
}
