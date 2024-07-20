package com.ngs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
	private String fullName;
	private String emailID;
	private String password;
	private long mobileNumber;
}
