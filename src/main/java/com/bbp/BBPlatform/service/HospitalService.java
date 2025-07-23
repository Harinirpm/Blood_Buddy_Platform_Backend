package com.bbp.BBPlatform.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bbp.BBPlatform.model.DonarsEntity;
import com.bbp.BBPlatform.model.HospitalEntity;
import com.bbp.BBPlatform.model.UserEntity;
import com.bbp.BBPlatform.repository.DonarsRepository;
import com.bbp.BBPlatform.repository.HospitalRepository;

@Service
public class HospitalService {
	private final HospitalRepository hospitalRepo;
	private final DonarsRepository donarRepo;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	
	@Autowired
	UserEntityService userService;
	
	@Autowired
	public HospitalService(HospitalRepository hospitalRepo,DonarsRepository donarRepo) {
		this.hospitalRepo = hospitalRepo;
		this.donarRepo = donarRepo;
	}
	
	public HospitalEntity addHospital(HospitalEntity hospital) {
	    hospital.setVerified(false);
	    hospital.setPassword(passwordEncoder.encode(hospital.getPassword()));
	    hospital.setRole("hospital");
	    LocalDateTime now = LocalDateTime.now();
	    hospital.setCreatedAt(now);
	    hospital.setUpdatedAt(now);
	    return hospitalRepo.save(hospital);
	}
	
	//hospital approved by admin after verification
	public HospitalEntity approveHospital(int hospitalId) {
	    HospitalEntity hospital = hospitalRepo.findById(hospitalId)
	        .orElseThrow(() -> new RuntimeException("Hospital not found"));
	    hospital.setVerified(true);
	    hospital.setUpdatedAt(LocalDateTime.now());
	    // Add to user login table
	    userService.addUser(new UserEntity(
	        hospital.getHospitalName(),
	        hospital.getPassword(),
	        hospital.getEmail(),
	        hospital.getRole()
	    ));
	    return hospitalRepo.save(hospital);
	}
	
	
    //find hospitals nearby the donars
	public List<HospitalEntity> getNearbyHospitalsByDonor(String donorEmail) {
	    DonarsEntity donor = donarRepo.findByEmail(donorEmail)
	            .orElseThrow(() -> new RuntimeException("Donor not found"));
	    String donorPincode = donor.getPincode();
	    return hospitalRepo.findByPincode(donorPincode);
	}


}
