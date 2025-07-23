package com.bbp.BBPlatform.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "blood_requests")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    private String bloodGroup;         
    private int quantityInUnits;      
    private String requiredLocation;  
    private String pincode;
    private String urgencyLevel;       
    private LocalDate neededByDate;
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospital;
    private boolean isFulfilled = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
