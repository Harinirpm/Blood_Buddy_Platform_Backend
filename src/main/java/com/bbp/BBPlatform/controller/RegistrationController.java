package com.bbp.BBPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbp.BBPlatform.model.DonarsEntity;
import com.bbp.BBPlatform.model.HospitalEntity;
import com.bbp.BBPlatform.service.DonarsEntityService;
import com.bbp.BBPlatform.service.HospitalService;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final DonarsEntityService donarService;
	private final HospitalService hospitalService;
	@Autowired
	public RegistrationController(DonarsEntityService donarService,HospitalService hospitalService) {
		this.donarService = donarService;
		this.hospitalService = hospitalService;
	}
	//add new donar
	@PostMapping("/addDonar")
	public ResponseEntity<DonarsEntity> registerDonar(@RequestBody DonarsEntity donar){
		DonarsEntity saveDonar = donarService.addDonar(donar);
		return new ResponseEntity<>(saveDonar, HttpStatus.OK);
	}
	//add new hospital
	@PostMapping("/addHospital")
	public ResponseEntity<HospitalEntity> registerHospital(@RequestBody HospitalEntity hospital){
		HospitalEntity savedHospital = hospitalService.addHospital(hospital);
	    return new ResponseEntity<>(savedHospital, HttpStatus.CREATED);
	}
	
	
	
	
}
