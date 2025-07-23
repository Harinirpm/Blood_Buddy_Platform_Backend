package com.bbp.BBPlatform.model;

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
@Table(name = "hospitals", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "licenseNumber")
})
public class HospitalEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int hospitalId;

	    // Basic Info
	    @Column(nullable = false)
	    private String hospitalName;
	    @Column(nullable = false, unique = true)
	    private String email;
	    @Column(nullable = false, unique = true)
	    private String phoneNo;
	    // Address Info
	    @Column(nullable = false)
	    private String address;
	    @Column(nullable = false)
	    private String city;
	    @Column(nullable = false)
	    private String state;
	    @Column(nullable = false)
	    private String pincode;
	    // Authentication
	    @Column(nullable = false)
	    private String password;

	    @Column(nullable = false)
	    private String role = "hospital";
	    @Column(nullable = false)
	    private boolean isVerified = false;
	    @Column(nullable = false)
	    private boolean termAndConditions;
	    // Hospital Credentials
	    @Column(nullable = false, unique = true)
	    private String licenseNumber;
	    @Column(nullable = false)
	    private String licenseDocumentUrl;
	    @Column(nullable = false)
	    private String hospitalLogoUrl;
	    // Blood-related data (optional - if you store hospital's stock)
	    
	    private int neededOPositive;
	    private int neededONegative;
	    private int neededAPositive;
	    private int neededANegative;
	    private int neededBPositive;
	    private int neededBNegative;
	    private int neededABPositive;
	    private int neededABNegative;

	    // Timestamp
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;

}
