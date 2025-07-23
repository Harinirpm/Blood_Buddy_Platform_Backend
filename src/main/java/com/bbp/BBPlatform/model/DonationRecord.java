package com.bbp.BBPlatform.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DonationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int donationId;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private DonarsEntity donor;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private BloodRequest request;

    private int donatedUnits;
    private LocalDateTime donatedAt;
    // Getters & Setters
}