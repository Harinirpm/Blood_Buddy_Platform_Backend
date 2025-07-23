package com.bbp.BBPlatform.dto;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class MultiHospitalBloodRequestDto {
	@NotBlank
	private String bloodGroup;
	@NotBlank
	private String requiredLocation;
	@NotBlank
	private String pincode;
	@NotBlank
	private String urgencyLevel;
	
	@NotNull
	@FutureOrPresent(message="Date must be today or future")
	private LocalDate neededByDate;
	
	@NotEmpty
	private List<@Valid HospitalRequest> hospitalRequests;
	
	@Data
	public static class HospitalRequest {
		@Min(value = 1, message = "Hospital ID must be valid")
		private int hospitalId;
		@Min(value = 1, message = "Quantity atleast must be 1 unit")
		private int quantityInUnits;
	}
}
