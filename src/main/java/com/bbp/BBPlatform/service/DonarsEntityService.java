package com.bbp.BBPlatform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bbp.BBPlatform.exception.IneligibleDonorException;
import com.bbp.BBPlatform.model.DonarsEntity;
import com.bbp.BBPlatform.model.UserEntity;
import com.bbp.BBPlatform.repository.DonarsRepository;
import com.bbp.BBPlatform.repository.UserEntityRepository;

@Service
public class DonarsEntityService {
	private final DonarsRepository donarRepo;
	private final UserEntityRepository userRepo;
	@Autowired
	UserEntityService userService;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	 @Autowired
	public DonarsEntityService(DonarsRepository donarRepo,UserEntityRepository userRepo) {
		this.donarRepo = donarRepo;
		this.userRepo = userRepo;
	}
	 public void displayDonar(DonarsEntity donar) {
		 System.out.println("======= Donor Details =======");
		    System.out.println("Name: " + donar.getName());
		    System.out.println("Gender: " + donar.getGender());
		    System.out.println("Date of Birth: " + donar.getDateOfBirth());
		    System.out.println("Blood Group: " + donar.getBloodGroup());
		    System.out.println("Email: " + donar.getEmail());
		    System.out.println("Phone No: " + donar.getPhoneNo());
		    System.out.println("Address: " + donar.getAddress());
		    System.out.println("City: " + donar.getCity());
		    System.out.println("State: " + donar.getState());
		    System.out.println("Pincode: " + donar.getPincode());
		    System.out.println("Role: " + donar.getRole());
		    
		    // Health Information
		    System.out.println("Weight: " + donar.getWeight());
		    System.out.println("Last Donation Date: " + donar.getLastDonationDate());
		    System.out.println("Has Chronic Disease: " + donar.isHasChronicDisease());
		    System.out.println("Is on Medication: " + donar.isOnMedication());
		    System.out.println("Had Recent Surgery: " + donar.isHadRecentSurgery());
		    System.out.println("Had Covid Recently: " + donar.isHadCovidRecently());
		    System.out.println("Alcohol/Smoking Habits: " + donar.isAlcoholOrSmokingHabits());
		    System.out.println("Has Tattoo: " + donar.isHasTattoo());

		    // ID Proofs
		    System.out.println("Government ID Type: " + donar.getGovernmentIdType());
		    System.out.println("Government ID Number: " + donar.getGovernmentIdNumber());
		    System.out.println("Profile Photo URL: " + donar.getProfilePhotoUrl());
		    System.out.println("Government Proof URL: " + donar.getGovernmentProofUrl());
		    System.out.println("Blood Group Proof: " + donar.getBloodGroupProof());
		    
		    // Status
		    System.out.println("Is Verified: " + donar.isVerified());
		    System.out.println("Agreed to Terms: " + donar.isTermAndConditions());
		    
		    // Timestamps
		    System.out.println("Created At: " + donar.getCreatedAt());
		    System.out.println("Updated At: " + donar.getUpdatedAt());
		    System.out.println("==============================");
		}
	 
	 //create new "Donar"
	 public DonarsEntity addDonar(DonarsEntity donar) {
		 //check donar has tattoo, if he/she has prevent from saving the data
		 if(donar.isHasTattoo()) {
			 Optional<DonarsEntity> existingDonar = donarRepo.findByEmail(donar.getEmail());
			 existingDonar.ifPresent(d -> donarRepo.delete(d));
			 throw new IneligibleDonorException("You can't login here because you have a tattoo.");
		 }
		// Mark as not verified initially
		 donar.setVerified(false);
		 //set encoded password
		 donar.setPassword(passwordEncoder.encode(donar.getPassword()));
		 if(donar.getLastDonationDate()==null || donar.getLastDonationDate().trim().isEmpty()) {
			 donar.setLastDonationDate("N/A");
		 }
		 //setRole
		 donar.setRole("donar");
		 //adding donar data in users table 
//		 userService.addUser(new UserEntity(donar.getName(),donar.getPassword(),donar.getEmail(),donar.getRole()));
		 LocalDateTime now = LocalDateTime.now();
		 donar.setCreatedAt(now);
		 donar.setUpdatedAt(now);
		 
		 //displayDonar details
		 displayDonar(donar);
		 return donarRepo.save(donar);
	 }
	 
	 
     //after admin approval the donar will be stored in users DB.
	 public DonarsEntity approveDonar(int donarId) {
		    DonarsEntity donar = donarRepo.findById(donarId)
		        .orElseThrow(() -> new RuntimeException("Donar not found"));
		    System.out.println("Approving donor: " + donar.getEmail());
		    donar.setVerified(true);
		    donar.setUpdatedAt(LocalDateTime.now());
		    // Now add to user table (for login access)
		    userService.addUser(new UserEntity(
		        donar.getName(), donar.getPassword(), donar.getEmail(), donar.getRole()
		    ));
		    return donarRepo.save(donar);
		}
	 
	 
       //find donar based on blood group and pincode
	 public List<DonarsEntity> findMatchingDonors(String bloodGroup, String pincode) {
	        return donarRepo.findByBloodGroupAndPincodeAndIsVerifiedTrue(bloodGroup, pincode);
	    }

	 
}
