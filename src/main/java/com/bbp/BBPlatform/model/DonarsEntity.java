package com.bbp.BBPlatform.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "donars", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"email","phone_no","governmentIdNumber","governmentProofUrl"})
})
public class DonarsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int donarId;
	private String name;
	private String gender;
	@Column(nullable = false)
	private LocalDate  dateOfBirth;
	@Column(nullable = false)
	private String bloodGroup;
	@Column(unique=true, nullable=false)
	private String email;
	@Column(unique = true, nullable=false)
	private String phoneNo;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private String state;
	@Column(nullable = false)
	private String pincode;
	private String role;
	
	//health info
	private double weight;
	@Column(nullable = true)
	private String lastDonationDate;
	@Column(nullable = false)
	private boolean hasChronicDisease = false; //long term disease
	@Column(nullable = false)
	private boolean isOnMedication = false; 
	@Column(nullable = false)
	private boolean hadRecentSurgery = false;
	@Column(nullable = false)
	private boolean hadCovidRecently=false;
	@Column(nullable = false)
	private boolean alcoholOrSmokingHabits = false;
	@Column(nullable = false)
	private boolean hasTattoo = false;
	
	//proofs
	@Column(nullable = false)
	private String governmentIdType;
	@Column(unique = true, nullable = false)
	private String governmentIdNumber;
	@Column(nullable = false)
	private String profilePhotoUrl;
	@Column(unique = true, nullable = false)
	private String governmentProofUrl;
	@Column(unique = true, nullable = false)
	private String bloodGroupProof;
	@Column(nullable = false)
	private boolean isVerified = false;
	
	// security and timeStamp
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private boolean termAndConditions;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	public DonarsEntity(String name,String gender, LocalDate  dateOfBirth, String bloodGroup, 
			String email, String phoneNo, String address, String city, String state, String pincode,
			String role, double weight, String lastDonationDate,
			String governmentIdType,String governmentIdNumber,String profilePhotoUrl,
			String governmentProofUrl,String bloodGroupProof,boolean isVerified,String password,
			boolean termAndConditions, LocalDateTime createdAt,LocalDateTime updatedAt) {
		this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.bloodGroup = bloodGroup;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.role = role;
        this.weight = weight;
        this.lastDonationDate = lastDonationDate;
        this.governmentIdType = governmentIdType;
        this.governmentIdNumber = governmentIdNumber;
        this.profilePhotoUrl = profilePhotoUrl;
        this.governmentProofUrl = governmentProofUrl;
        this.bloodGroupProof = bloodGroupProof;
        this.isVerified = isVerified;
        this.password = password;
        this.termAndConditions = termAndConditions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
	}
	
	
}
