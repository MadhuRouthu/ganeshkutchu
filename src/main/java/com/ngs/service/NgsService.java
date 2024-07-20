package com.ngs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.ngs.dto.LoginDto;
import com.ngs.dto.LoginResponse;
import com.ngs.dto.RegisterDto;
import com.ngs.model.ProjectDetails;
import com.ngs.model.Register;
import com.ngs.model.Resume;

public interface NgsService {
public boolean registerCandidateDetails(Register candidateRegister);
public boolean saveITSkills(String skills,String email);
public boolean saveEducationDetails(String jsonData,Register idbyEmail);
public boolean candidateRegister(Register register);
public boolean register(RegisterDto registerDto);
public boolean verifyAccount(String emailID, String otp);
public boolean regenerateOtp(String email);
public boolean login(LoginDto loginDto);
public LoginResponse viewProfile(String email);
public boolean generateOtpforResetPwd(String email);
public boolean resetOtpValidate(String otp,String email);
public boolean resetPassword(String password,String email);
public boolean saveProjectDetails(String details, String email);
public Register getIdbyEmail(String email);
public boolean savePersonalDetails(String candidateDetails,Register register);
public boolean saveProfile(Register idbyEmail, MultipartFile file);
public boolean saveResume(MultipartFile file, Register idbyEmail);
public Optional<Resume> getResume(String email);
public List<Object[]> getCandidateList();
public List<ProjectDetails> getExperienceList();
LoginResponse loginResponse(LoginDto loginDto);
public boolean loginTATeam(Long id);
}
