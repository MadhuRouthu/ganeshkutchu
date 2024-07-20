package com.ngs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPwdDto {
private String email;
private String otp;
private String password;
private String confirmPassword;
}
