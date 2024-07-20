package com.ngs.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
	private Long registrationid;
	private boolean active;
	private String otp;
	private LocalDateTime otpGeneratedTime;
	private String fullName;
	private String emailID;
	private String password;
	private long mobileNumber;
}
